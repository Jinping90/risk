package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * QBT二要素认证记录表
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "wealth_two_auth_record")
public class WealthTwoAuthRecord {
    /**
     * 主键
     */
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
     * 身份证号码
     */
    private String cardNo;
    /**
     * 认证状态 1：通过 2：不通过
     */
    private Integer status;
    /**
     * 认证时间
     */
    private Date authTime;
    /**
     * 错误码
     */
    private String errCode;
    /**
     * 错误描述
     */
    private String errMessage;
}
