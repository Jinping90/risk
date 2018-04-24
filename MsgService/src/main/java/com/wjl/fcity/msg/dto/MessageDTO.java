package com.wjl.fcity.msg.dto;

import lombok.Data;

/**
 * message传参
 * @author xuhaihong
 * @date 2018-03-27 17:29
 **/
@Data
public class MessageDTO {

    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 每页显示数
     */
    private Integer size;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 消息id
     */
    private Long id;
    /**
     * 类型
     */
    private Integer type;
}
