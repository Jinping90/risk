package com.wjl.fcity.user.model;

import lombok.Data;


/**
 * 用于生成加密token
 *
 * @author shengju
 */
@Data
public class Token {

    private Long id;

    private String mobile;

    private String username;

    private Long loginTime;
}
