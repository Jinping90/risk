package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user_contacts")
public class UserContacts {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 关系
     */
    private String relate;
    /**
     * 姓名(用户输入)
     */
    private String name;
    /**
     * 称呼(通讯录留存)
     */
    private String nickname;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态
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
}