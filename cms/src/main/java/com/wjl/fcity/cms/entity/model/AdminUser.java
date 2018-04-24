package com.wjl.fcity.cms.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wjl.fcity.cms.entity.dto.AdminRoleApiDto;
import com.wjl.fcity.cms.entity.dto.AdminRoleDto;
import com.wjl.fcity.cms.entity.dto.AdminRoleMenuDto;
import com.wjl.fcity.cms.entity.dto.AdminUserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author czl
 */
@Data
public class AdminUser implements UserDetails {
    private long id;
    private long roleId;
    private String loginName;
    @JsonIgnore
    private String password;
    private String realName;
    private String email;
    private String mobile;
    private int status;
    private Date lastVisitTime;
    @JsonIgnore
    private Date gmtCreated;
    @JsonIgnore
    private Date gmtModified;
    @JsonIgnore
    private AdminRoleDto role;
    @JsonIgnore
    private List<AdminRoleMenuDto> roleMenus;
    @JsonIgnore
    private List<AdminRoleApiDto> roleApis;

    @JsonIgnore
    private List<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private AdminUserDto adminUserDto;

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return loginName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setGrantedAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    public boolean isLocked() {
        return this.status == 2;
    }

    public AdminUserDto getAdminUserDto() {
        adminUserDto = new AdminUserDto();
        adminUserDto.setId(this.id);
        adminUserDto.setRoleId(this.roleId);
        adminUserDto.setLoginName(this.loginName);
        adminUserDto.setRealName(this.realName);
        adminUserDto.setEmail(this.email);
        adminUserDto.setMobile(this.mobile);
        adminUserDto.setRole(this.role);
        adminUserDto.setRoleMenus(this.roleMenus);
        adminUserDto.setRoleApis(this.roleApis);
        return adminUserDto;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                '}';
    }
}
