package com.wjl.fcity.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.common.properties.AppProperties;
import com.wjl.fcity.user.common.util.AesUtils;
import com.wjl.fcity.user.common.util.PhoneUtil;
import com.wjl.fcity.user.common.util.sms.SendSmsUtil;
import com.wjl.fcity.user.mapper.CommonMapper;
import com.wjl.fcity.user.mapper.UserMapper;
import com.wjl.fcity.user.model.SmsCodeData;
import com.wjl.fcity.user.model.vo.TencentCloudBean;
import com.wjl.fcity.user.model.vo.TongDunBean;
import com.wjl.fcity.user.po.CityConfigPO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.request.SendVerifyCodeReq;
import com.wjl.fcity.user.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 公共
 *
 * @author shengju
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private AppProperties appProperties;
    @Resource
    private CommonMapper commonMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 注册发送验证码的类型
     */
    @Value("${vCode.register}")
    private Integer register;


    /**
     * 登录
     */
    @Value("${vCode.login}")
    private Integer login;

    @Value("${vCode.resetPwd}")
    private Integer resetPwd;

    @Value("${vCode.findPwd}")
    private Integer findPwd;

    @Override
    public Map sendVCode(SendVerifyCodeReq sendVerifyCodeReq) throws UnsupportedEncodingException {
        String mobile = sendVerifyCodeReq.getMobile();
        Integer type = sendVerifyCodeReq.getType();
        if (!PhoneUtil.isMobileNO(mobile)) {
            log.error(String.format("输入的手机号不合法,手机号为:%s", mobile));
            throw new BaseException(CodeEnum.PHONE_ILLEGAL_NUMBER);
        }

        String vNoteMsg = null;

        // 如果type为验证码登录，判断用户是否存在
        if (login.equals(sendVerifyCodeReq.getType())) {
            UserPO existUser = userMapper.findUserByMobile(mobile);
            if (existUser == null) {
                log.info(String.format("手机号%s的用户未注册", mobile));
                throw new BaseException(CodeEnum.AUTH_NO_USER);
            }
            vNoteMsg = appProperties.getSmsTemplate().get("vNoteMsgLogin");

        }

        // 如果type为重设密码，判断用户是否设置过密码
        if (resetPwd.equals(sendVerifyCodeReq.getType()) || findPwd.equals(sendVerifyCodeReq.getType())) {
            UserPO existUser = userMapper.findUserByMobile(mobile);
            if (existUser == null) {
                log.info(String.format("手机号%s的用户未注册", mobile));
                throw new BaseException(CodeEnum.AUTH_NO_USER);
            }
            if (existUser.getPassword() == null) {
                log.info(String.format("手机号%s的用户还未设置密码,userId:%s", mobile, existUser.getId()));
                throw new BaseException(CodeEnum.AUTH_NO_SET);
            }
            if (findPwd.equals(sendVerifyCodeReq.getType())) {
                vNoteMsg = appProperties.getSmsTemplate().get("vNoteMsgFindPwd");
            }else {
                vNoteMsg = appProperties.getSmsTemplate().get("vNoteMsgResetPwd");
            }

        }

        // 如果type为注册，判断用户是否注册过
        if (register.equals(sendVerifyCodeReq.getType())) {
            UserPO existUser = userMapper.findUserByMobile(mobile);
            if (existUser != null) {
                log.info(String.format("手机号%s的用户已注册,userId:%s", mobile, existUser.getId()));
                throw new BaseException(CodeEnum.AUTH_MOBILE_EXIST);
            }
            vNoteMsg = appProperties.getSmsTemplate().get("vNoteMsgRegister");
        }

        // 发送验证码
        String vCode;
        // 发送验证码的短信内容
        String appName = appProperties.getNoteName();

        vNoteMsg = appName + vNoteMsg;
        vNoteMsg = new String(vNoteMsg.getBytes(), "utf-8");
        // String vNoteMsg = "【万家乐】您的验证码为%s，有效时间10分钟，请勿将验证码泄露给其他人，如有疑问或非本人操作请致电400-8515751。";
        vCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));


        log.info(String.format("手机号%s的短信验证码为:%s", mobile, vCode));

        String content = String.format(vNoteMsg, vCode);

        boolean result = SendSmsUtil.send(mobile, content);
        if (!result) {
            log.error(String.format("手机号%s的短信验证码发送失败", mobile));
            throw new BaseException(CodeEnum.PHONE_SEND_V_CODE_FAIL);
        }
        try {
            SmsCodeData sd = new SmsCodeData();
            sd.setVCode(vCode);
            sd.setPhone(mobile);
            sd.setTime(System.currentTimeMillis());
            sd.setFlag(type);
            String token = AesUtils.aesEncryptHexString(JSON.toJSONString(sd), appProperties.getCodeKey());

            Map<String, Object> data = new HashMap<>(2);
            data.put("verifyCode", vCode);
            data.put("noteToken", token);
            return data;
        } catch (Exception e) {
            log.error("注册短信加密失败,原因:", e);
            throw new BaseException(CodeEnum.SYS_UNKNOWN);
        }
    }

    @Override
    public TongDunBean saveTongDunReport(Long userId) {
        return commonMapper.getUserInfo(userId);
    }

    @Override
    public TencentCloudBean saveTxyReport(Long userId) {
        TongDunBean tongDunBean = commonMapper.getUserInfo(userId);
        TencentCloudBean tencentCloudBean = new TencentCloudBean();
        tencentCloudBean.setIdCard(tongDunBean.getIdCard());
        tencentCloudBean.setRealname(tongDunBean.getName());
        tencentCloudBean.setMobile(tongDunBean.getMobile());
        return tencentCloudBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addThirdReport(Integer type, String userId, String verifyId, String message) {
        commonMapper.addThirdReport(type, Long.valueOf(userId), verifyId, message, new Date());
        log.info("报告保存成功-userId:" + userId + "报告类型：" + type + "报告id:" + verifyId);
    }

    @Override
    public List getScrollBarList() {
        String[] surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈",
                "韩", "杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶",
                "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费",
                "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于",
                "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧",
                "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴",
                "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席",
                "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐",
                "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢",
                "是", "秘", "畅", "邝", "还", "宾", "闾", "辜", "纵", "侴", "司马", "上官", "诸葛", "东方"};


        List<CityConfigPO> cityConfigList = commonMapper.getCityConfig();


        List<String> resultList = new ArrayList<>(30);
        int thirty = 30;
        Random random = new Random();
        String serviceMsg;
        for (int a = 0; a < thirty; a++) {
            Integer count = random.nextInt(2);
            Integer code = random.nextInt(cityConfigList.size() + 1);
            if (code == 0) {
                serviceMsg = "注册成为城市居民";
            }else if ("公园".equals(cityConfigList.get(code - 1).getCityName())) {
                serviceMsg = "在公园和朋友聚会";
            }else if ("购物中心".equals(cityConfigList.get(code - 1).getCityName())) {
                serviceMsg = "到购物中心买买买";
            }else {
                serviceMsg = "到" + cityConfigList.get(code - 1).getCityName() + "办理了业务";
            }

            String message;
            if (count == 0) {
                message = random.nextInt(20) + 1 + "分钟前," + surname[random.nextInt(surname.length)] + "*" + serviceMsg;
            }else {
                message = random.nextInt(20) + 1 + "分钟前," + surname[random.nextInt(surname.length)] + "**" + serviceMsg;
            }


            resultList.add(message);
        }

        return resultList;
    }

}
