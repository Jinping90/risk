package com.wjl.fcity.msg.model;

import com.wjl.fcity.msg.common.config.Invisible;
import lombok.Data;

import java.util.Date;

/**
 * 用户消息发送状态表
 *
 * @author czl
 */
@Data
public class MessageStatus {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 消息id
     */
    private Long messageId;
    /**
     * 接受用户id
     */
    private Long userId;
    /**
     * 状态 0.未读，1.已读
     */
    private Integer status;
    /**
     * 类型  public 公告 private 消息
     */
    @Invisible
    private String types;
    /**
     * 添加时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
