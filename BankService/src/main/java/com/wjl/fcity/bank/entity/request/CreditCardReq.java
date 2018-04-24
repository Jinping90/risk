package com.wjl.fcity.bank.entity.request;

import lombok.Data;

/**
 * 信用卡接口请求体
 *
 * @author czl
 */
@Data
public class CreditCardReq {
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 手机号码
     */
    private String phoneNum;
}
