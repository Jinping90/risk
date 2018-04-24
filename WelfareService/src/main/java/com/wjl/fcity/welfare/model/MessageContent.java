package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户消息 公告表
 *
 * @author czl
 */
@Data
@Entity
@Table(name = "message_content")
public class MessageContent {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 类型 或者用户组
     */
    private String type;
    /**
     * 图片url
     */
    private String imageUrl;
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
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
