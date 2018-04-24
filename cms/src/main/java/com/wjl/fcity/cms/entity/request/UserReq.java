package com.wjl.fcity.cms.entity.request;

import lombok.Data;

/**
 * @author shengju
 */
@Data
public class UserReq extends PageReq {
    private String name;
    private String idCardNo;
    private String mobile;
    private Long userId;
}
