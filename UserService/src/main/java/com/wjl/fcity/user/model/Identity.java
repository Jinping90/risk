package com.wjl.fcity.user.model;

import lombok.Data;

/**
 * @author : Fy
 * @date : 2018-04-01 9:55
 */
@Data
public class Identity {

    private boolean validity;

    private String phoneId;

    private String reason;
}
