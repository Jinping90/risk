package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户消息发送状态表
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "message_status")
public class MessageStatus {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 接受用户id
     */
    private String userId;
    /**
     * 状态 0.未读，1.已读
     */
    private String status;
    /**
     * 类型  public 公告 private 消息
     */
    private String types;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
