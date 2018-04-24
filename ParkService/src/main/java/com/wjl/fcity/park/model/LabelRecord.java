package com.wjl.fcity.park.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
@Data
public class LabelRecord {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标签id
     */
    private Long labelId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 称呼(通讯录留存)
     */
    private String nickName;
    /**
     * 状态,0:有效;1:过期
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
