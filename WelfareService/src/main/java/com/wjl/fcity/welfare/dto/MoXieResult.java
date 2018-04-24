package com.wjl.fcity.welfare.dto;

import lombok.Data;

/**
 * @author czl
 */
@Data
public class MoXieResult {
    private Long userId;
    /**
     * 魔蝎结果码 1：成功 2：失败 3：查询中
     */
    private String code;
    private String message;
    private Long billId;
    private Long reportId;
}
