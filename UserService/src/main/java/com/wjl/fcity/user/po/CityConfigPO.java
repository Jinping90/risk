package com.wjl.fcity.user.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * 建筑信息
 *
 * @author shengju
 */
@Data
public class CityConfigPO {
    private Integer id;
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
    private String description;

    @JsonIgnore
    private Date gmtCreated;

    @JsonIgnore
    private Date gmtModified;
}
