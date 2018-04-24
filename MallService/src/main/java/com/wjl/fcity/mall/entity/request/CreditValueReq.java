package com.wjl.fcity.mall.entity.request;

import com.wjl.fcity.mall.common.enums.AuthItemEnum;
import com.wjl.fcity.mall.common.enums.ChangeCreditValueEnum;
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

    public CreditValueReq(Long userId, AuthItemEnum authItem, ChangeCreditValueEnum changeCreditValueEnum){
        this.userId = userId;
        this.authItem = authItem.getCode();
        this.changeCreditValueEnum = changeCreditValueEnum.getCode();
    }
}