package com.wjl.fcity.msg.dto;

import lombok.Data;

/**
 * @author xuhaihong
 * @create 2017-12-16 18:37
 **/
@Data
public class MessagePublicListDto {
    private  String tital ;
    private  String content;
    private  String status;
    private  Long id;
    private  Long addTime;

}
