package com.wjl.fcity.bank.entity.request;

import lombok.Data;

/**
 * 魔蝎类
 *
 * @author czl
 */
@Data
public class MoXieResultReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 魔蝎结果码 1：成功 2：失败 3：查询中
     */
    private String code;
    /**
     * 描述
     */
    private String message;
    /**
     * 账单获取ID
     */
    private String billId;
    /**
     * 报告获取ID
     */
    private String reportId;
}
