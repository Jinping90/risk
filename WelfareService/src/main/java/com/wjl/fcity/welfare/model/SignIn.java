package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Fy
 * @implSpec : Created with IntelliJ IDEA.
 * @date : 2018-03-27 10:35
 * 签到表
 */
@Entity
@Table(name = "sign_in")
@Data
public class SignIn {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 添加时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
