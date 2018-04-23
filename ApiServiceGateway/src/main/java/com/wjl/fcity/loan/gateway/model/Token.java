package com.wjl.fcity.loan.gateway.model;

import lombok.Data;

/**
 * Token信息
 * 
 * @author 秦瑞华
 *
 */
@Data
public class Token {
    private Long 	id;
    private String 	mobile;
    private String 	username;
    private Long 	loginTime;
}
