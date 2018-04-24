package com.wjl.fcity.park.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 杨洋
 * @date 2018/4/2
 */
@Data
public class LabelDTO {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 标签,用","分隔,例:"1,22,3"
     */
    private String labels;
    /**
     * 称呼(通讯录留存)
     */
    private String nickName;
    /**
     * 创建时间
     */
    private Date gmtCreated;
}
