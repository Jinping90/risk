package com.wjl.fcity.msg.dto;

import lombok.Data;

/**
 * @author xuhaihong
 * @create 2017-12-16 18:39
 **/
@Data
public class MessagePublicDto {

    private String tital;
    private String content;
    private Long addTime;
    private  String imageUrl;

}
