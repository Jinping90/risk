package com.wjl.fcity.user.model.vo;

import lombok.Data;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 同盾认证Bean
 * @date 2018/4/2
 */
@Data
public class TongDunBean {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 项目标识
     */
    private String identification;

    /**
     *姓名
     */
    private String name;
    /**
     * 黑盒
     */
    private String blackBox;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 银行卡号
     */
    private String cardNo;
}
