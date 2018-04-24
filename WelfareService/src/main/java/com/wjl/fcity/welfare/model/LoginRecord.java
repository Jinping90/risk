package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 登入记录表
 * @author czl
 */
@Data
@Entity
@Table(name = "login_record")
public class LoginRecord {
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
     * 登入设备Id
     */
    private String deviceId;
    /**
     * 登入时间
     */
    private Date loginTime;


}
