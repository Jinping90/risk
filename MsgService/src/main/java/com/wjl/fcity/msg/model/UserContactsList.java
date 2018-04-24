package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 通讯录
 *
 * @author czl
 */
@Data
@Entity(name = "user_contacts_list")
public class UserContactsList {
    /**
     *
     */
    @Id
    @GeneratedValue
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
     * 手机号
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}