package com.wjl.fcity.cms.common.security;

import org.springframework.security.core.GrantedAuthority;
/**
 * @author czl
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    private String permissionUrl;

    CustomGrantedAuthority(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    @Override
    public String getAuthority() {
        return this.permissionUrl;
    }
}
