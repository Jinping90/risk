package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user_id_card")
public class UserIdCard {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 出生日期
     */
    private Integer birthday;
    /**
     * 身份证号
     */
    private String cardNo;
    /**
     * 民族
     */
    private String nation;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 地址
     */
    private String address;
    /**
     * 发证机关
     */
    private String agency;
    /**
     * 有效期
     */
    private Integer validDateBegin;
    /**
     * 有效期
     */
    private String validDateEnd;
    /**
     * 正面照
     */
    private String frontImg;
    /**
     * 反面照
     */
    private String backImg;
    /**
     * 公安库网纹照
     */
    private String dbImg;
    /**
     * 0.未认证;1.验证中;2.通过;3.不通过
     */
    private Integer status;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Transient
    private String facePhoto;
}