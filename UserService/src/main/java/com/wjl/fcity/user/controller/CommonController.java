package com.wjl.fcity.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.enums.ThirdTypeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.common.properties.AppProperties;
import com.wjl.fcity.user.common.util.IpUtil;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.model.ConfigAppVersionDO;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.constant.QueueDictConstant;
import com.wjl.fcity.user.model.vo.TencentCloudBean;
import com.wjl.fcity.user.model.vo.TongDunBean;
import com.wjl.fcity.user.request.SendVerifyCodeReq;
import com.wjl.fcity.user.request.TongDunReq;
import com.wjl.fcity.user.sender.MqSender;
import com.wjl.fcity.user.service.CommonService;
import com.wjl.fcity.user.service.ConfigAppVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 公共
 *
 * @author shengju
 */
@Slf4j
@RestController
public class CommonController {

    @Resource
    private ConfigAppVersionService configAppVersionService;

    @Resource
    private CommonService commonService;
    @Resource
    private MqSender mqSender;
    @Resource
    private AppProperties appProperties;


    @PostMapping("/user/sendVerifyCode")
    public Response sendVCode(@RequestBody SendVerifyCodeReq sendVerifyCodeReq) throws UnsupportedEncodingException {
        String mobile = sendVerifyCodeReq.getMobile();
        Integer type = sendVerifyCodeReq.getType();
        log.info(String.format("请求发送验证码的手机号为:%s", mobile));
        if (StringUtil.isBlank(mobile)) {
            log.error("发送短信验证码的手机号为空");
            return new Response<>(CodeEnum.AUTH_ILLEGAL, "请输入正确的手机号");
        }
        boolean flag = (null == type || !(type >= 1 && type <= 6));
        if (flag) {
            log.error("发送短信验证码的类型未选择，或不符合规范");
            return new Response<>(CodeEnum.AUTH_ILLEGAL, "验证码类型错误");
        }
        return new Response<>(CodeEnum.SUCCESS, commonService.sendVCode(sendVerifyCodeReq));
    }

    /**
     * 获取app版本信息
     *
     * @return Response
     */
    @PostMapping("/user/checkAppVersion")
    public Response checkAppVersion() {
        ConfigAppVersionDO configAppVersionDO = configAppVersionService.getAppVersion();
        return new Response<>(CodeEnum.SUCCESS, configAppVersionDO);
    }

    /**
     * 获取同盾数据
     *
     * @param request request
     * @return Response
     */
    @PostMapping("/user/tongDun/saveReport")
    public Response saveTongDunReport(HttpServletRequest request, @RequestBody TongDunReq tongDunReq) {
        if (tongDunReq == null) {
            throw new BaseException(CodeEnum.PARAMETER_MISTAKE);
        }
        Long userId = tongDunReq.getUserId();
        final String ip = IpUtil.getIp(request);
        TongDunBean tongDunBean = commonService.saveTongDunReport(userId);
        if (tongDunBean != null) {
            tongDunBean.setBlackBox(tongDunReq.getBlackBox());
            tongDunBean.setIpAddress(ip);
            tongDunBean.setUserId(userId);
            String identification = appProperties.getIdentification();
            tongDunBean.setIdentification(identification);
        } else {
            throw new BaseException(CodeEnum.PARAMETER_MISTAKE);
        }

        try {
            mqSender.send(QueueDictConstant.QUEUENAME_TONGDUN, JSONObject.toJSONString(tongDunBean));
        } catch (Exception e) {
            log.error("消息队列添加失败:" + e.getMessage(), e);
            return new Response<>(CodeEnum.SYS_UNKNOWN, null);
        }
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 腾讯云报告保存接口
     *
     * @param request request
     * @return Response
     */
    @PostMapping("/user/txy/saveReport")
    public Response saveTxyReport(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        final String ip = IpUtil.getIp(request);
        TencentCloudBean tencentCloudBean = commonService.saveTxyReport(Long.valueOf(userId));
        tencentCloudBean.setIp(ip);
        tencentCloudBean.setUserId(Long.valueOf(userId));
        String identification = appProperties.getIdentification();
        tencentCloudBean.setIdentification(identification);
        try {
            log.info("腾讯云参数:" + tencentCloudBean);
            mqSender.send(QueueDictConstant.QUEUENAME_TENCENTCLOUD, JSONObject.toJSONString(tencentCloudBean));
        } catch (Exception e) {
            log.error("消息队列添加失败:" + e.getMessage(), e);
            return new Response<>(CodeEnum.SYS_UNKNOWN, null);
        }
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 同盾回调
     *
     * @param response response
     * @return response
     */
    @PostMapping("/user/tongDunCallback")
    public Response tongDunCallback(@RequestBody Response response) {
        String success = "1001";
        String fail = "1002";

        if (success.equals(response.getCode())) {
            Map map = (Map) response.getData();
            String userId = map.get("userId").toString();
            String verifyId = map.get("verifyId").toString();
            String message = "message";
            if (map.get(message) == null) {
                message = "";
            } else {
                message = map.get("message").toString();
            }

            if (userId != null && verifyId != null && !("".equals(userId)) && !("".equals(verifyId))) {
                commonService.addThirdReport(ThirdTypeEnum.TONG_DUN.getCode(), userId, verifyId, message);
            }
        }

        if (fail.equals(response.getCode())) {
            Map map = (Map) response.getData();
            String userId = map.get("userId").toString();
            log.info("同盾保存数据失败-userId:" + userId);
            commonService.addThirdReport(ThirdTypeEnum.TONG_DUN.getCode(), userId, null, "保存失败");
        }
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 腾讯云回调
     *
     * @param response response
     * @return Response
     */
    @PostMapping("/user/tencentCloudCallback")
    public Response tencentCloudCallback(@RequestBody Response response) {
        String success = "1001";
        String fail = "1002";

        if (success.equals(response.getCode())) {
            Map map = (Map) response.getData();
            String userId = map.get("userId").toString();
            String verifyId = map.get("verifyId").toString();
            String message = "message";
            if (map.get(message) == null) {
                message = "";
            } else {
                message = map.get("message").toString();
            }

            if (userId != null && verifyId != null && !("".equals(userId)) && !("".equals(verifyId))) {
                commonService.addThirdReport(ThirdTypeEnum.TENCENT_CLOUD.getCode(), userId, verifyId, message);
            }
        }

        if (fail.equals(response.getCode())) {
            Map map = (Map) response.getData();
            String userId = map.get("userId").toString();
            log.info("腾讯云保存数据失败-userId:" + userId);
            commonService.addThirdReport(ThirdTypeEnum.TENCENT_CLOUD.getCode(), userId, null, "保存失败");
        }
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 获取滚动条信息
     *
     * @return response
     */
    @PostMapping("/user/getScrollBarList")
    public Response getScrollBarList() {
        return new Response<>(CodeEnum.SUCCESS, commonService.getScrollBarList());
    }
}
