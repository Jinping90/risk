package com.wjl.fcity.msg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户Wealth信息表
 * @author  czl
 */
@Data
@Entity
@Table(name="wealth_info")
public class WealthInfo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    /**
     * 用户ID
     */
    @Column(nullable = false)
    private long userId;
    /**
     * 信用值
     */
    @Column(nullable = false)
    private int creditValue;
    /**
     * 财富
     */
    @Column(nullable = false)
    private BigDecimal totalWealth;
    /**
     * 连续签到天数
     */
    @JsonIgnore
    private Integer signInDays;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
