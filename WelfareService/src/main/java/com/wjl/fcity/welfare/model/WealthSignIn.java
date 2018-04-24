package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户签到记录
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "wealth_sign_in")
public class WealthSignIn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 签到时间
     */
    private Date createdAt;
}
