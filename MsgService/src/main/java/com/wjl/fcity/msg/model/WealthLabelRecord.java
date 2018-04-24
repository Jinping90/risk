package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * wealth标签记录表
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "wealth_label_record")
public class WealthLabelRecord {
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
     * 手机号码
     */
    @Column(nullable = false)
    private String mobile;
    /**
     * 称呼(通讯录留存)
     */
    @Column(nullable = false)
    private String nickName;
    /**
     * 标签类型
     */
    @Column(nullable = false)
    private Integer label;
    /**
     * 0:有效，1：过期
     */
    private int expire;
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
