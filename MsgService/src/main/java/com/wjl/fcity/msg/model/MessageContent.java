package com.wjl.fcity.msg.model;

import com.wjl.fcity.msg.common.config.Invisible;
import lombok.Data;

import java.util.Date;

/**
 * 用户消息 公告表
 *
 * @author czl
 */
@Data
public class MessageContent {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 类型 [1: 公告, 2: 个人消息]
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 添加时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;
    @Invisible
    private String status;
}
