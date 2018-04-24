package com.wjl.fcity.phone.controller;

import com.google.common.collect.Maps;
import com.wjl.fcity.phone.common.config.RiskManagementConfig;
import com.wjl.fcity.phone.common.enums.*;
import com.wjl.fcity.phone.common.util.HttpUtils;
import com.wjl.fcity.phone.entity.request.MoXieResultReq;
import com.wjl.fcity.phone.entity.vo.Response;
import com.wjl.fcity.phone.service.UserAuthRecordService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 手机运营商认证
 *
 * @author czl
 */
@Slf4j
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Resource
    private UserAuthRecordService userAuthRecordService;
    @Resource
    private RiskManagementConfig riskManagementConfig;

    /**
     * 手机运营商认证回调
     *
     * @param moXieResult 请求参数
     */
    @PostMapping(value = "/phoneAuthCallback")
    public Response phoneAuthCallback(@RequestBody MoXieResultReq moXieResult) {
        Long start = System.currentTimeMillis();
        log.info("手机运营商认证回调开始，用户userId={}", moXieResult.getUserId());

        //处理网银认证回调
        userAuthRecordService.dealWithAuthCallback(moXieResult, AuthCategoryEnum.PHONE_AUTH,
                AuthItemEnum.MOBIL_INFO, ThirdTypeEnum.MOBIL_INFO, ChangeCreditValueEnum.MOBIL_INFO);
        log.info("手机运营商认证回调结束，用户userId={}，耗时{}毫秒", moXieResult.getUserId(), System.currentTimeMillis() - start);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

    /**
     * 获取手机运营商认证报告
     *
     * @param request 请求参数
     */
    @PostMapping(value = "/getPhoneReport")
    public Response getPhoneReport(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        String response;

        try {
            Map<String, String> param = Maps.newHashMap();
            param.put("userId", userId);
            param.put("identification", riskManagementConfig.getIdentification());
            response = HttpUtils.sendPost(riskManagementConfig.getRiskIp() + riskManagementConfig.getGetModelMobileOperatorUrl(), JSONObject.fromObject(param).toString());
            log.info("请求风控获取手机运营商认证报告成功，用户ID={}", userId);
        } catch (IOException e) {
            log.error("请求风控获取手机运营商认证报告异常，用户ID={}", userId, e);
            return new Response<>(CodeEnum.SYS_NETWORK_ANOMALY, "");
        }

        response = JSONObject.fromObject(response).get("data").toString();

        return new Response<>(CodeEnum.SUCCESS, response);
    }
}
