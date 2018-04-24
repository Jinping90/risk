package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * 认证有效期配置表
 *
 * @author czl
 */
@Data
public class ConfigAuthParamDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 认证项目[1: 实名认证, 2: 身份证照片, 3: 人脸认证, 4: 信用卡认证, 5: 银行卡认证, 6: 信用卡邮箱, 7: 运营商认证, 8: 支付宝认证, 9: 淘宝认证
     */
    private Integer authItem;
    /**
     * 有效天数
     */
    private Integer validityPeriod;
    /**
     * 首次认证增加的信用值
     */
    private Integer firstAddCreditValue;
    /**
     * 认证过期减少的信用值
     */
    private Integer expiredMinusCreditValue;
    /**
     * 再次认证增加的信用值
     */
    private Integer againAddCreditValue;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
