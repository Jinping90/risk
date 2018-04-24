package com.wjl.fcity.bank.controller;

import com.wjl.fcity.bank.common.enums.*;
import com.wjl.fcity.bank.entity.request.MoXieResultReq;
import com.wjl.fcity.bank.entity.vo.Response;
import com.wjl.fcity.bank.service.UserAuthRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 银行认证
 *
 * @author czl
 */
@Slf4j
@RestController
@RequestMapping("/bank")
public class BankController {

    @Resource
    private UserAuthRecordService userAuthRecordService;

    /**
     * 网银认证回调
     *
     * @param moXieResult 请求参数
     */
    @PostMapping(value = "/bankAuthCallback")
    public Response bankAuthCallback(@RequestBody MoXieResultReq moXieResult) {
        Long start = System.currentTimeMillis();
        log.info("网银认证回调开始，用户userId={}", moXieResult.getUserId());

        //处理网银认证回调
        userAuthRecordService.dealWithAuthCallback(moXieResult, AuthCategoryEnum.BANK_AUTH,
                AuthItemEnum.ONLINE_BANKING, ThirdTypeEnum.ONLINE_BANKING, ChangeCreditValueEnum.ONLINE_BANKING);
        log.info("网银认证回调结束，用户userId={}，耗时{}毫秒", moXieResult.getUserId(),System.currentTimeMillis() - start);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

    /**
     * 信用卡邮箱认证回调
     *
     * @param moXieResult 请求参数
     */
    @PostMapping(value = "/creditEmailAuthCallback")
    public Response creditEmailAuthCallback(@RequestBody MoXieResultReq moXieResult) {
        Long start = System.currentTimeMillis();
        log.info("信用卡邮箱认证回调开始，用户userId={}", moXieResult.getUserId());

        //处理信用卡邮箱认证回调
        userAuthRecordService.dealWithAuthCallback(moXieResult, AuthCategoryEnum.BANK_AUTH,
                AuthItemEnum.CREDIT_EMAIL_AUTH, ThirdTypeEnum.CREDIT_EMAIL, ChangeCreditValueEnum.CREDIT_EMAIL_AUTH);
        log.info("信用卡邮箱认证回调结束，用户userId={}，耗时{}毫秒", moXieResult.getUserId(),System.currentTimeMillis() - start);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

}
