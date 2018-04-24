package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * 二要素
 *
 * @author xuhaihong
 * @date 2018-03-30 12:02
 **/
@Data
public class TwoElementAuth {

    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    private String name;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 0: 亲,认证成功(收费), 1: 亲，认证信息不一致(收费), 2: 亲,认证信息不存在(不收费),
     * 3: 亲,认证成功无照片(收费),9: 亲,其他异常(不收费), 业务失败错误码: 错误信息(不收费)
     */
    private String code;
    /**
     * 认证结果描述
     */
    private String describe;
    /**
     * 商户订单号
     */
    private String transId;
    /**
     * 交易流水号
     */
    private String tradeNo;
    /**
     * 是否收费 Y: 收费, N: 不收费
     */
    private String fee;
    private Date gmtCreated;
    private Date gmtModified;

}
