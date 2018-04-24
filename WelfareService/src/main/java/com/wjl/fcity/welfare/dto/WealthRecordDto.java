package com.wjl.fcity.welfare.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-28 16:08
 */
@Data
public class WealthRecordDto {
    /**
     * 财富变化值.
     */
    private String changeWealth;

    /**
     * 创建时间.
     */
    private Date gmtCreated;

    /**
     * 流水id.
     */
    private Long id;

    public WealthRecordDto(String changeWealth, Date gmtCreated, Long id) {
        this.changeWealth = changeWealth;
        this.gmtCreated = gmtCreated;
        this.id = id;
    }
}
