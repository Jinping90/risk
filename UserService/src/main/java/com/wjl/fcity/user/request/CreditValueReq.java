package com.wjl.fcity.user.request;

import lombok.Data;

/**
 * 用户中心微服务，信用值改变记录请求体
 * @author czl
 */
@Data
public class CreditValueReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 认证项目
     */
    private Integer authItem;
    /**
     * 信用值修改类型
     */
    private Integer changeCreditValueEnum;
}
