package com.wjl.fcity.user.model.vo;

import lombok.Data;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/3
 */
@Data
public class TencentCloudBean {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 标识码
     */
    private String identification;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 银行卡号
     */
    private String cardNo;

}
