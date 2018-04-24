package com.wjl.fcity.welfare.dto;

import lombok.Data;

/**
 * @author czl
 */
@Data
public class LoginUser {

    private Long id;

    private String mobile;

    private String username;


    private String password;

    private Long loginTime;
}
