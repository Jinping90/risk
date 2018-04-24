package com.wjl.fcity.welfare.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-28 21:27
 */
@Data
public class RankingDto {
    /**
     * 用户的username.
     */
    private String username;

    /**
     * 用户的信用值.
     */
    private Integer creditValue;

    /**
     * 用户拥有的财富.
     */
    private String totalWealth;

    /**
     * 用户的userId
     */
    @JsonIgnore
    private Long userId;
}
