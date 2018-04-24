package com.wjl.fcity.coretask.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 水滴结算生成信息配置
 * @author czl
 */
@Data
public class ConfigGenerateWealthDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 生成水滴年份
     */
    private Integer putYear;
    /**
     * 生成水滴月份
     */
    private Integer putMonth;
    /**
     * 生成水滴日
     */
    private Integer putDay;
    /**
     * 假设存量用户
     */
    private Long assumeUser;
    /**
     * 月生成水滴数
     */
    private BigDecimal monthWealthCount;
    /**
     * 日生成水滴
     */
    private BigDecimal dayWealthCount;
    /**
     * 日人均获取得水滴
     */
    private BigDecimal dayAvgWealthCount;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
