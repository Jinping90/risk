package com.wjl.fcity.welfare.common.enums;

import lombok.Getter;

/**
 * @author fy
 * @date 2018/1/22
 */
@Getter
public enum WealthRecordStatusEnum {
    /**
     * 虚拟币来源
     * 状态[0: 未收取, 1: 已收取, 2: 已过期]
     */
    UN_GATHER(0, "未收取"),
    GATHERED(1, "已收取"),
    OVERDUE(2, "已过期");

    private Integer status;
    private String msg;

    WealthRecordStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
