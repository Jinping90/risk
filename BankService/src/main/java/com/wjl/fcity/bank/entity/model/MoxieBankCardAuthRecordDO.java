package com.wjl.fcity.bank.entity.model;

import lombok.Data;

import java.util.Date;

/**
 * 魔蝎银行卡三四要素查询记录表
 *
 * @author czl
 */
@Data
public class MoxieBankCardAuthRecordDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 信用卡卡号
     */
    private String cardNo;
    /**
     * 银行预留手机号
     */
    private String mobile;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡类型
     */
    private String cardType;
    /**
     * 认证结果码 0: 成功(收费), 1: 信息不一致(收费), 9: 其他异常(不收费), 错误码: 错误信息(不收费)
     */
    private String code;
    /**
     * 认证结果描述
     */
    private String describe;
    /**
     * 商户订单号
     */
    private String transId;
    /**
     * 交易流水号
     */
    private String tradeNo;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
