package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财富记录表
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "wealth_record")
public class WealthRecord {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @Column(nullable = false)
    private long userId;
    /**
     * 财富变化值
     */
    @Column(nullable = false)
    private BigDecimal changeWealth;
    /**
     * 状态[0: "未收取", 1: "已收取", 2: "已过期"]
     */
    @Column(nullable = false)
    private Integer status;
    /**
     * 虚拟币来源
     * 1：标签结算 2：人脸识别 3: 个人资料 4：手机运营商 5：信用卡认证 6:：网银认证 7：淘宝认证
     */
    @Column(nullable = false)
    private Integer type;
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
