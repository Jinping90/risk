package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user_auth_report")
public class UserAuthReport {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 认证类别
     */
    private String category;
    /**
     * 认证项目
     */
    private String reportItem;
    /**
     * 结果
     */
    private String reportResult;
    /**
     * 详情
     */
    private String reportDetail;
    /**
     * 认证评分
     */
    private Integer reportScore;
    /**
     * 认证次数
     */
    private Integer authNum;
    /**
     * 0:未验证,1:验证中,2:通过,3:不通过,4:已过期
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer ordersNo;
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