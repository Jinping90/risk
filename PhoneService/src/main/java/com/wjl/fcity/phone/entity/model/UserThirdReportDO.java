package com.wjl.fcity.phone.entity.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户第三方报告信息表
 *
 * @author czl
 */
@Data
public class UserThirdReportDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 第三方类型[1: 同盾, 2：腾讯云, 3：银行卡, 4：信用卡, 5：运营商, 6：淘宝, 7：支付宝]
     */
    private Integer thirdType;
    /**
     * 账单获取ID
     */
    private String billId;
    /**
     * 报告获取ID
     */
    private String reportId;
    /**
     * 描述
     */
    private String message;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
