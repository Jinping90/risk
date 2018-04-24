package com.wjl.fcity.user.model.vo;

import lombok.Data;

/**
 * 城市信息展示类
 *
 * @author czl
 */
@Data
public class CityInfoVO {
    /**
     * 城市编号
     */
    private Integer cityCode;
    /**
     * 城市名字
     */
    private String cityName;
    /**
     * 状态 [1: 未解锁, 2: 已解锁, 3: 维护中, 4: new, 5: 活动]
     */
    private Integer status;
    /**
     * 城市描述
     */
    private String descriptions;
    /**
     * 剩余可以得到的信用值
     */
    private Integer willGetCreditValue;
}
