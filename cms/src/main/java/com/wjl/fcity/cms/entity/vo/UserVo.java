package com.wjl.fcity.cms.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shengju
 */
@Data
public class UserVo {
    private Long id;
    private String name;
    private String mobile;
    private String idCardNo;
    private Integer creditValue;
    private BigDecimal waterDrop;
    private Date registerTime;
    private Integer authCount;
}
