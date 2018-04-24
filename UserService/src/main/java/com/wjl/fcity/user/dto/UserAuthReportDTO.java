package com.wjl.fcity.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户认证信息
 *
 * @author czl
 */
@Data
public class UserAuthReportDTO {
    /**
     * 认证项目
     */
    private Integer authItem;
    /**
     * 认证状态
     */
    private Integer status;
    /**
     * 认证修改时间
     */
    private Date authTime;
    /**
     * 认证次数
     */
    private Integer authNum;
    /**
     * 认证过期天数
     */
    private Integer validityPeriod;
    /**
     * 首次认证增加的信用值
     */
    private Integer firstAddCreditValue;
    /**
     * 再次认证增加的信用值
     */
    private Integer againAddCreditValue;
}
