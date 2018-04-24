package com.wjl.fcity.bank.service.micro.service;


import com.wjl.fcity.bank.entity.request.AuthRecordReq;
import com.wjl.fcity.bank.entity.request.CreditValueReq;
import com.wjl.fcity.bank.entity.vo.Response;
import com.wjl.fcity.bank.service.micro.service.fallback.UserMicroServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户中心微服务
 *
 * @author czl
 */
@FeignClient(value = "User-Service", fallback = UserMicroServiceFallbackImpl.class)
public interface UserMicroService {

    /**
     * 保存及修改认证纪录
     *
     * @param authRecordReq 认证记录接口调用请求体
     * @return 是否保存成功
     */
    @RequestMapping(value = "/micro/user/saveOrUpdateAuthRecord", method = RequestMethod.POST)
    Response saveOrUpdateAuthRecord(@RequestBody AuthRecordReq authRecordReq);

    /**
     * 修改用户的信用值
     *
     * @param creditValueReq 信用值改变记录请求体
     */
    @RequestMapping(value = "/micro/user/updateCreditValue", method = RequestMethod.POST)
    void updateCreditValue(@RequestBody CreditValueReq creditValueReq);
}
