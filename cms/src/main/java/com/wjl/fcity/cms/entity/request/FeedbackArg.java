package com.wjl.fcity.cms.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 杨洋
 * @date 2018/4/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackArg extends PageReq {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String username;
    /**
     * 搜索关键字
     */
    private String keyWord;

    /**
     * 反馈开始时间.
     */
    private Long startTime;

    /**
     * 反馈结束时间.
     */
    private Long endTime;

    /**
     * 1 产品建议 2 问题反馈.
     */
    private Integer questionType;

}
