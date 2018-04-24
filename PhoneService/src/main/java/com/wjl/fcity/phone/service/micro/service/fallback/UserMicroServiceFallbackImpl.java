package com.wjl.fcity.phone.service.micro.service.fallback;

import com.wjl.fcity.phone.common.enums.CodeEnum;
import com.wjl.fcity.phone.entity.request.AuthRecordReq;
import com.wjl.fcity.phone.entity.request.CreditValueReq;
import com.wjl.fcity.phone.entity.vo.Response;
import com.wjl.fcity.phone.service.micro.service.UserMicroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户中心微服务回调失败
 *@author czl
 *
 */
@Slf4j
@Service
public class UserMicroServiceFallbackImpl implements UserMicroService {

    @Override
    public Response saveOrUpdateAuthRecord(AuthRecordReq authRecordReq) {
        return new Response<>(CodeEnum.SUCCESS, Boolean.FALSE);
    }

    @Override
    public void updateCreditValue(CreditValueReq creditValueReq) {

    }
}
