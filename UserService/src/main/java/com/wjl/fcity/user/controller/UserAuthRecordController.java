package com.wjl.fcity.user.controller;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.request.AuthRecordReq;
import com.wjl.fcity.user.request.CreditValueReq;
import com.wjl.fcity.user.service.UserAuthRecordService;
import com.wjl.fcity.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户认证记录处理
 *
 * @author czl
 */
@RestController
public class UserAuthRecordController {

    @Resource
    private UserAuthRecordService userAuthRecordService;
    @Resource
    private UserService userService;

    /**
     * 保存及修改认证纪录
     *
     * @param authRecordReq 认证记录接口调用请求体
     * @return 是否保存成功
     */
    @PostMapping("/micro/user/saveOrUpdateAuthRecord")
    public Response saveOrUpdateAuthRecord(@RequestBody AuthRecordReq authRecordReq) {
        Boolean isSaveSuccess = userAuthRecordService.saveOrUpdateAuthRecord(authRecordReq);
        return new Response<>(CodeEnum.SUCCESS, isSaveSuccess);
    }

    /**
     * 修改用户的信用值
     *
     * @param creditValueReq 信用值改变记录请求体
     */
    @PostMapping("/micro/user/updateCreditValue")
    public Response updateCreditValue(@RequestBody CreditValueReq creditValueReq) {
        userService.updateCreditValue(creditValueReq.getUserId(), creditValueReq.getAuthItem(), creditValueReq.getChangeCreditValueEnum());
        return new Response<>(CodeEnum.SUCCESS, "");
    }
}
