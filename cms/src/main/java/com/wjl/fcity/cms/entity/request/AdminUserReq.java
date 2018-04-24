package com.wjl.fcity.cms.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员用户请求体
 *
 * @author czl
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserReq extends PageReq {
    /**
     * 管理员名称
     */
    private String adminName;
    /**
     * 新密码
     */
    private String newPassword;
}
