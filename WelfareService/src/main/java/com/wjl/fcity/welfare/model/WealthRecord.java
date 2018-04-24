package com.wjl.fcity.welfare.model;

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
    private long userId;
    /**
     * 财富变化值
     */
    private BigDecimal changeWealth;
    /**
     * 状态[0: "未收取", 1: "已收取", 2: "已过期"]
     */
    private Integer status;
    /**
     * 虚拟币来源
     * 1：标签结算 2：人脸识别 3: 个人资料 4：手机运营商 5：信用卡认证 6:：网银认证 7：淘宝认证
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
