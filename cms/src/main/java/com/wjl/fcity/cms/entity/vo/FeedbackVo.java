package com.wjl.fcity.cms.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author : Fy
 * @date : 2018-04-19 16:59
 */
@Data
public class FeedbackVo {

    /**
     * 用户的userId
     */
    private Long userId;

    /**
     * 用户的名字.
     */
    private String username;

    /**
     * 用户的手机号码.
     */
    private String mobile;

    /**
     * 用户的性别.
     */
    private Integer gender;

    /**
     * 用户反馈的类型.
     */
    private Integer questionType;

    /**
     * 用户反馈的内容.
     */
    private String questionContent;

    /**
     * 用户反馈的时间.
     */
    private Date gmtCreated;
}
