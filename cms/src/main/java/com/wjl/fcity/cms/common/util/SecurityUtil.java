package com.wjl.fcity.cms.common.util;

import com.wjl.fcity.cms.entity.model.AdminUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author czl
 */
public class SecurityUtil {

    /**
     * 获取当前管理员用户信息
     *
     * @return 管理员信息
     */
    public static AdminUser getSecurityUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AdminUser adminUser;
        if (principal != null) {
            adminUser = (AdminUser) principal;
        } else {
            adminUser = null;
        }
        return adminUser;
    }
}
