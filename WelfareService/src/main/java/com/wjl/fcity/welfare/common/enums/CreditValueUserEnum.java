package com.wjl.fcity.welfare.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @date : 2018-04-10 15:50
 */
@Getter
public enum CreditValueUserEnum {

    /**
     * 用户等级分类
     */
    ORDINARY_USER(0, "ordinaryUser"),
    COPPER_USER(200, "copperUser"),
    SILVER_USER(500, "silverUser"),
    GOLD_USER(600, "goldUser");

    private Integer score;
    private String msg;

    CreditValueUserEnum(Integer score, String msg) {
        this.score = score;
        this.msg = msg;
    }
}
