package com.wjl.fcity.user.model;

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
    private Date birthday;
    /**
     * 身份证号
     */
    private String idCardNo;
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
    private Date validDateBegin;
    /**
     * 有效期
     */
    private Date validDateEnd;
    /**
     * 正面照
     */
    private String frontImgUrl;
    /**
     * 反面照
     */
    private String backImgUrl;
    /**
     * 公安库网纹照
     */
    private String dbImgUrl;
    /**
     * 0.未认证;1.验证中;2.通过;3.不通过
     */
    private Integer status;
    /**
     * 添加时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;

}