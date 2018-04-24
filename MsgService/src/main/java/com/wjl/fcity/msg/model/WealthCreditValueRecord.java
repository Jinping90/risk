package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信用值记录
 *
 * @author czl
 */
@Entity
@Data
@Table(name = "wealth_credit_value_record")
public class WealthCreditValueRecord {
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
    private Long userId;
    /**
     * 算力改变值
     */
    @Column(nullable = false)
    private Integer changeCreditValue;
    /**
     * 修改类型，1：连续登陆，2：人脸识别，3：个人资料，4：手机运营商认证，5：信用卡认证，6：网银认证，7：淘宝认证
     */
    @Column(nullable = false)
    private Integer type;
    /**
     * 算力修改时间
     */
    @Column(nullable = false)
    private Date createTime;
}
