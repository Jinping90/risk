package com.wjl.fcity.bank.entity.dto;

import lombok.Data;

/**
 * 银行卡bin信息查询(包含信用卡校验)
 * @author TJP 2017-12-25
 **/
@Data
public class BankCardBinDTO {
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 银行卡code
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡类型(银行卡只支持借记卡)
     *  2 : 储蓄卡
     *  3 : 信用卡
     */
    private Integer cardType;
    /**
     * 是否支持该银行卡(如果是信用卡,默认返回状态为1)
     * 0 : 不支持
     * 1 : 支持
     */
    private Integer validStatus;
}
