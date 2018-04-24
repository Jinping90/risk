package com.wjl.fcity.bank.entity.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户认证记录表
 *
 * @author czl
 */
@Data
public class UserAuthRecordDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 认证类别
     */
    private Integer authCategory;
    /**
     * 认证项目
     */
    private Integer authItem;
    /**
     * 结果信息
     */
    private String authDetail;
    /**
     * 认证评分
     */
    private Integer authScore;
    /**
     * 认证次数
     */
    private Integer authNum;
    /**
     * 1:验证中,2:通过,3:不通过,4:已过期
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