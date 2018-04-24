package com.wjl.fcity.user.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户认证信息
 *
 * @author shengju
 */
@Data
public class UserAuthReportVO {
    /**
     * 认证项目
     */
    private Integer authItem;
    /**
     * 认证状态
     */
    private Integer status;
    /**
     * 认证过期时间
     */
    private Date validityDate;
    /**
     * 认证成功后会加多少信用值
     */
    private Integer addCreditValue;
}
