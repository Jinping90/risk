package com.wjl.fcity.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wjl.fcity.user.common.enums.*;
import com.wjl.fcity.user.common.util.HttpUtils;
import com.wjl.fcity.user.mapper.ConfigSysParamMapper;
import com.wjl.fcity.user.mapper.CreditValueRecordMapper;
import com.wjl.fcity.user.mapper.TwoElementsAuthMapper;
import com.wjl.fcity.user.model.CreditValueRecordDO;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.TwoElementAuth;
import com.wjl.fcity.user.model.UserAuthRecord;
import com.wjl.fcity.user.service.TwoElementsAuthService;
import com.wjl.fcity.user.service.UserAuthRecordService;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author xuhaihong
 * @date 2018-03-30 11:57
 **/
@Slf4j
@Service
public class TwoElementsAuthServiceImpl implements TwoElementsAuthService {

    @Resource
    private TwoElementsAuthMapper twoElementsAuthMapper;
    @Resource
    private UserAuthRecordService userAuthRecordService;

    @Resource
    private CreditValueRecordMapper creditValueRecordMapper;

    @Resource
    private ConfigSysParamMapper sysParamConfigMapper;

    @Resource
    private UserService userService;
    @Value("${spring.profiles.active}")
    private String configFile;

    /**
     * 保存二要素
     *
     * @param twoElementsAuth 二要素
     */
    @Override
    public void save(TwoElementAuth twoElementsAuth) {
        twoElementsAuthMapper.save(twoElementsAuth);
    }

    /**
     * 查询二要素
     *
     * @param twoElementsAuth 二要素
     * @return 二要素结果
     */
    @Override
    public TwoElementAuth getTwoElementAuth(TwoElementAuth twoElementsAuth) {
        return twoElementsAuthMapper.getTwoElementAuth(twoElementsAuth);
    }

    /**
     * 校验是否做过二要素验证
     *
     * @return 查询结果  false 没有验证 true表示验证完成
     */
    @Override
    public Boolean checkTwoElementAuthRecord(TwoElementAuth twoElementsAuth) {
        TwoElementAuth twoElementAuth = twoElementsAuthMapper.getTwoElementAuth(twoElementsAuth);
        return null != twoElementAuth;
    }

    /**
     * 校验是否做过二要素验证
     *
     * @return 查询结果
     */
    @Override
    public TwoElementAuth checkTwoElementAuthRecordByUserId(Long userId) {
        return twoElementsAuthMapper.getIfTwoElementAuth(userId);
    }


    /**
     * 二要素认证
     * 保存二要素记录 更新认证记录 更新用户creditValue值 更新creditValue记录表
     *
     * @param userId       用户id
     * @param realName     姓名
     * @param idCardNo     身份证号
     * @param inviteUserId 邀请用户的userId
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response identify(Long userId, String realName, String idCardNo, Long inviteUserId) {

        //不传回图片参数
        String nophoto = "nophoto";
        Map<String, Object> param = Maps.newHashMap();
        param.put("name", realName);
        param.put("idCard", idCardNo);
        param.put("type", nophoto);
        //请求魔蝎三要素认证接口
        log.info("........请求魔蝎二要素认证接口........");
        String resp = HttpUtils.sendGet("https://api.creditm.cn/tags/v2/idCardAuth", paramsMapToStr(param), configFile);
        log.info(String.format("魔蝎二要素认证返回:%s", resp));
        JSONObject respJson = JSON.parseObject(resp);
        Boolean success = respJson.getBoolean("success");
        TwoElementAuth twoElementAuth = new TwoElementAuth();
        if (success) {
            //请求成功
            String respDataStr = respJson.getString("data");
            JSONObject respData = JSON.parseObject(respDataStr);
            String code = respData.getString("code");
            String describe = respData.getString("desc");
            String transId = respData.getString("trans_id");
            String tradeNo = respData.getString("trade_no");
            String fee = respData.getString("fee");
            UserAuthRecord userAuthRecord = new UserAuthRecord();
            Date createDate = new Date();
            switch (code) {
                case "0":
                    //认证成功
                    log.info(String.format("userId = %s认证成功", userId));
                    //添加二要素认证记录
                    twoElementAuth.setUserId(userId);
                    twoElementAuth.setName(realName);
                    twoElementAuth.setIdCardNo(idCardNo);
                    twoElementAuth.setCode(code);
                    twoElementAuth.setDescribe(describe);
                    twoElementAuth.setTransId(transId);
                    twoElementAuth.setTradeNo(tradeNo);
                    twoElementAuth.setFee(fee);
                    twoElementAuth.setGmtCreated(createDate);
                    twoElementsAuthMapper.save(twoElementAuth);
                    //更新用户认证记录
                    userAuthRecord.setUserId(userId);
                    userAuthRecord.setAuthCategory(AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode());
                    userAuthRecord.setAuthItem(AuthItemEnum.REAL_NAME.getCode());
                    userAuthRecord.setAuthDetail("实名认证");
                    userAuthRecord.setAuthScore(100);
                    userAuthRecord.setAuthNum(1);
                    userAuthRecord.setStatus(AuthStatusEnum.PASS.getCode());
                    userAuthRecord.setGmtCreated(createDate);
                    userAuthRecord.setGmtModified(createDate);
                    userAuthRecordService.insertUserAuthRecord(userAuthRecord);
                    //更新用户姓名
                    Integer gender = getGender(idCardNo);
                    userService.updateUserName(userId, realName, gender);

                    userService.updateCreditValue(userId, AuthItemEnum.REAL_NAME.getCode(), ChangeCreditValueEnum.TWO_ELEMENTS_AUTH.getCode());

                    //如果是被邀请用户二要素认证，用户和邀请人都增加信用值
                    if (inviteUserId != null) {
                        setCreditValue(userId, inviteUserId);
                    }

                    return new Response<>(CodeEnum.SUCCESS, null);
                case "1":
                    log.warn(String.format("userId = %s认证信息不一致", userId));
                    //添加二要素认证记录
                    twoElementAuth.setUserId(userId);
                    twoElementAuth.setName(realName);
                    twoElementAuth.setIdCardNo(idCardNo);
                    twoElementAuth.setCode(code);
                    twoElementAuth.setDescribe(describe);
                    twoElementAuth.setTransId(transId);
                    twoElementAuth.setTradeNo(tradeNo);
                    twoElementAuth.setFee(fee);
                    twoElementAuth.setGmtCreated(new Date());
                    twoElementsAuthMapper.save(twoElementAuth);

                    return new Response<>(CodeEnum.WRONG_CREDIT_CARD_ID, null);
                case "2":
                    log.warn(String.format("userId = %s认证信息不存在", userId));
                    //添加二要素认证记录
                    twoElementAuth.setUserId(userId);
                    twoElementAuth.setName(realName);
                    twoElementAuth.setIdCardNo(idCardNo);
                    twoElementAuth.setCode(code);
                    twoElementAuth.setDescribe(describe);
                    twoElementAuth.setTransId(transId);
                    twoElementAuth.setTradeNo(tradeNo);
                    twoElementAuth.setFee(fee);
                    twoElementAuth.setGmtCreated(new Date());
                    twoElementsAuthMapper.save(twoElementAuth);
                    return new Response<>(CodeEnum.TWO_FACTOR_AUTH_NOT_EXIST, null);
                case "3":
                    log.info(String.format("userId = %s认证成功无照片", userId));
                    //添加二要素认证记录
                    twoElementAuth.setUserId(userId);
                    twoElementAuth.setName(realName);
                    twoElementAuth.setIdCardNo(idCardNo);
                    twoElementAuth.setCode(code);
                    twoElementAuth.setDescribe(describe);
                    twoElementAuth.setTransId(transId);
                    twoElementAuth.setTradeNo(tradeNo);
                    twoElementAuth.setFee(fee);
                    twoElementAuth.setGmtCreated(new Date());
                    twoElementsAuthMapper.save(twoElementAuth);
                    //更新用户认证记录
                    userAuthRecord.setUserId(userId);
                    userAuthRecord.setAuthCategory(AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode());
                    userAuthRecord.setAuthItem(AuthItemEnum.REAL_NAME.getCode());
                    userAuthRecord.setAuthDetail("实名认证");
                    userAuthRecord.setAuthScore(100);
                    userAuthRecord.setAuthNum(1);
                    userAuthRecord.setStatus(AuthStatusEnum.PASS.getCode());
                    userAuthRecord.setGmtCreated(createDate);
                    userAuthRecord.setGmtModified(createDate);
                    userAuthRecordService.insertUserAuthRecord(userAuthRecord);

                    //更新用户姓名
                    Integer genders = getGender(idCardNo);
                    userService.updateUserName(userId, realName, genders);

                    userService.updateCreditValue(userId, AuthItemEnum.REAL_NAME.getCode(), ChangeCreditValueEnum.TWO_ELEMENTS_AUTH.getCode());
                    //如果是被邀请用户二要素认证，用户和邀请人都增加信用值
                    if (inviteUserId != null) {
                        setCreditValue(userId, inviteUserId);
                    }
                    return new Response<>(CodeEnum.SUCCESS, null);
                case "9":
                    log.error(String.format("userId = %s其他异常", userId));
                    //添加二要素认证记录
                    twoElementAuth.setUserId(userId);
                    twoElementAuth.setName(realName);
                    twoElementAuth.setIdCardNo(idCardNo);
                    twoElementAuth.setCode(code);
                    twoElementAuth.setDescribe(describe);
                    twoElementAuth.setTransId(transId);
                    twoElementAuth.setTradeNo(tradeNo);
                    twoElementAuth.setFee(fee);
                    twoElementAuth.setGmtCreated(new Date());
                    twoElementsAuthMapper.save(twoElementAuth);
                    return new Response<>(CodeEnum.TWO_FACTOR_AUTH_OTHER_EXCEPTION, null);
                default:
            }
            return new Response<>(CodeEnum.SUCCESS, null);
        } else {
            String errorCode = respJson.getString("errorCode");
            String errorMsg = respJson.getString("errorMsg");
            log.error(String.format("查询失败errorCode = %s,errorMsg = %s", errorCode, errorMsg));
            //添加二要素认证记录
            twoElementAuth.setUserId(userId);
            twoElementAuth.setName(realName);
            twoElementAuth.setIdCardNo(idCardNo);
            twoElementAuth.setCode(errorCode);
            twoElementAuth.setDescribe(errorMsg);
            twoElementAuth.setTransId("");
            twoElementAuth.setTradeNo("");
            twoElementAuth.setFee("N");
            twoElementAuth.setGmtCreated(new Date());
            twoElementsAuthMapper.save(twoElementAuth);

            String cardFormatIsWrong = "S1000";
            if (cardFormatIsWrong.equals(errorCode)) {
                return new Response<>(CodeEnum.TWO_FACTOR_CARD_FORMAT_IS_WRONG, "");
            }
            return new Response<>(CodeEnum.SYSTEM_BUSY, "");
        }

    }

    private String paramsMapToStr(Map<String, Object> params) {
        StringBuilder paramBuilder = new StringBuilder();
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> paramBuilder.append("&").append(k).append("=").append(v));
        }

        paramBuilder.deleteCharAt(0);
        return paramBuilder.toString();
    }


    /**
     * 根据用户的身份证号码判断用户的性别
     *
     * @param idCardNo idCardNo 用户的身份证号码
     * @return 用户性别
     */
    private static Integer getGender(String idCardNo) {

        //表示的是18位数的身份证号码
        Integer idCardNumberSixteenLength = 18;
        //15位身份证号码
        ///Integer idCardNumberFifteenLength = 15;

        //能被2除尽的偶数
        Integer evenNum = 2;

        //性别[0: 男, 1: 女]
        Integer gender;
        if (idCardNo.length() == idCardNumberSixteenLength) {
            //表示的身份证的第16位数字
            int sixteenNum = 16;

            if (Integer.parseInt(idCardNo.substring(sixteenNum).substring(0, 1)) % evenNum == 0) {
                gender = 1;
            } else {
                gender = 0;
            }
        } else {
            //表示的身份证的第14位数字
            int fourteenNum = 14;
            if (Integer.parseInt(idCardNo.substring(fourteenNum).substring(0, 1)) % evenNum == 0) {
                gender = 1;
            } else {
                gender = 0;
            }
        }
        return gender;
    }

    /**
     * 如果是邀请用户实名认证后(二要素认证)增加用户和邀请人的的信用值
     *
     * @param useId        用户的userId
     * @param inviteUserId 邀请人的UserId
     */
    private void setCreditValue(Long useId, Long inviteUserId) {

        Date currentTime = new Date();
        String sysParamKeyMax = "invite_credit_value_limit";
        String sysParamKeyEvery = "invite_credit_value";
        String inviteCreditValueNew = "invite_credit_value_new";

        //得到配置表的值(Value)  20邀请人(注册后(实名认证后:信用值+20))
        String paramValueNew = sysParamConfigMapper.getSysParamConfig(inviteCreditValueNew);
        userService.updateCreditValueByInvite(useId, Integer.parseInt(paramValueNew));

        //添加算力记录 受邀
        CreditValueRecordDO creditValueRecordDO = new CreditValueRecordDO();
        creditValueRecordDO.setUserId(useId);
        creditValueRecordDO.setChangeCreditValue(Integer.valueOf(paramValueNew));
        //类型为10  受邀注册
        creditValueRecordDO.setType(10);
        creditValueRecordDO.setGmtCreated(currentTime);
        creditValueRecordMapper.insert(creditValueRecordDO);

        //查询配置表
        String paramValue = sysParamConfigMapper.getSysParamConfig(sysParamKeyMax);
        String paramValueEvery = sysParamConfigMapper.getSysParamConfig(sysParamKeyEvery);

        //邀请一个人十分 paramValue/10  得到最高奖励邀请人数
        Integer maxInvites = Integer.parseInt(paramValue) / Integer.parseInt(paramValueEvery);
        //查询邀请人已经邀请用户个数
        Integer totalInvited = userService.countInvites(inviteUserId);
        if (maxInvites < totalInvited) {
            log.info(String.format("用户userId= %d 邀请人数奖励达上限 邀请人数为 %d 最高为 %d", inviteUserId, totalInvited, maxInvites));
        } else {
            //添加算力记录
            creditValueRecordDO.setUserId(inviteUserId);
            creditValueRecordDO.setChangeCreditValue(Integer.valueOf(paramValueEvery));
            //类型为9  邀请好友
            creditValueRecordDO.setType(9);
            creditValueRecordDO.setGmtCreated(currentTime);
            creditValueRecordMapper.insert(creditValueRecordDO);
            //更新邀请人 credit_value值
            userService.updateCreditValueByInvite(inviteUserId, Integer.parseInt(paramValueEvery));
        }

    }

}
