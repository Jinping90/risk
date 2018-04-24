package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user_work_info")
public class UserWorkInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 工作岗位
     */
    private String jobTitle;
    /**
     * 工作职级
     */
    private Integer jobLevel;
    /**
     * 行业
     */
    private Integer industry;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 所在地
     */
    private String companyPlace;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司电话
     */
    private String companyTel;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}