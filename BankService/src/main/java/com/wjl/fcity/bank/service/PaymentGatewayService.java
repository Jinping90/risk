package com.wjl.fcity.bank.service;

import com.wjl.fcity.bank.entity.dto.BankCardBinDTO;

/**
 * 趣融支付网关Service
 *
 * @author TJP
 * @date 2018/1/8 11:29
 **/
public interface PaymentGatewayService {

    /**
     * 银行卡卡bin信息查询
     *
     * @param userId 用户ID
     * @param bankCardNo 银行卡号
     * @return 返回结果
     */
    BankCardBinDTO bankCardBinQuery(final Long userId, String bankCardNo);
}
