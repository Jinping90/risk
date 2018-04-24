package com.wjl.fcity.mall.controller;

import com.wjl.fcity.mall.common.enums.*;
import com.wjl.fcity.mall.entity.request.MoXieResultReq;
import com.wjl.fcity.mall.entity.vo.Response;
import com.wjl.fcity.mall.service.UserAuthRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 购物认证
 *
 * @author czl
 */
@Slf4j
@RestController
@RequestMapping("/mall")
public class MallController {

    @Resource
    private UserAuthRecordService userAuthRecordService;

    /**
     * 淘宝认证回调
     *
     * @param moXieResult 请求参数
     */
    @PostMapping(value = "/taoBaoAuthCallback")
    public Response taoBaoAuthCallback(@RequestBody MoXieResultReq moXieResult) {
        Long start = System.currentTimeMillis();
        log.info("淘宝认证回调开始，用户userId={}", moXieResult.getUserId());

        //处理网银认证回调
        userAuthRecordService.dealWithAuthCallback(moXieResult, AuthCategoryEnum.SHOPPING_CENTRE,
                AuthItemEnum.TAO_BAO_AUTH, ThirdTypeEnum.TAO_BAO, ChangeCreditValueEnum.TAO_BAO_AUTH);
        log.info("淘宝认证回调结束，用户userId={}，耗时{}毫秒", moXieResult.getUserId(), System.currentTimeMillis() - start);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

    /**
     * 支付宝认证回调
     *
     * @param moXieResult 请求参数
     */
    @PostMapping(value = "/aliPayAuthCallback")
    public Response aliPayAuthCallback(@RequestBody MoXieResultReq moXieResult) {
        Long start = System.currentTimeMillis();
        log.info("支付宝认证回调开始，用户userId={}", moXieResult.getUserId());

        //处理信用卡邮箱认证回调
        userAuthRecordService.dealWithAuthCallback(moXieResult, AuthCategoryEnum.SHOPPING_CENTRE,
                AuthItemEnum.ALI_PAY_AUTH, ThirdTypeEnum.ALI_PAY, ChangeCreditValueEnum.ALI_PAY_AUTH);
        log.info("支付宝认证回调结束，用户userId={}，耗时{}毫秒", moXieResult.getUserId(), System.currentTimeMillis() - start);

        return new Response<>(CodeEnum.SUCCESS, "");
    }
}
