package com.wjl.fcity.coretask.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财富记录表(水滴)
 *
 * @author czl
 */
@Data
public class WealthRecordDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 财富变化值
     */
    private BigDecimal changeWealth;
    /**
     * 状态[0: 未收取, 1: 已收取, 2: 已过期]
     */
    private Integer status;
    /**
     * 水滴来源 [1: 结算]
     */
    private Integer type;
    /**
     * 生成水滴时的贡献值
     */
    private BigDecimal generateCreditValue;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
