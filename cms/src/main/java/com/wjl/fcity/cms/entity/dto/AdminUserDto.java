package com.wjl.fcity.cms.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;
import lombok.Data;

import java.util.List;
import java.util.List;

/**
 * @author czl
 */
@Data
public class AdminUserDto {
    private long id;
    private long roleId;
    private String loginName;
    private String realName;
    private String email;
    private String mobile;

    private AdminRoleDto role;

    @JsonIgnore
    private List<AdminRoleMenuDto> roleMenus;

    @JsonIgnore
    private List<AdminRoleApiDto> roleApis;

    private List<AdminMenuVO> menus;
    private List<AdminApiVO> apis;

    public List<AdminMenuVO> getMenus() {
        menus = Lists.newArrayList();

        if (roleMenus == null || roleMenus.size() == 0) {
            return menus;
        }

        roleMenus.sort(new AdminRoleAuthDto.AuthComparator());

        for (AdminRoleMenuDto roleMenu : roleMenus) {
            if (roleMenu.getParentId() == 0) {
                menus.add(recursiveMenu(roleMenu, roleMenus));
            }
        }

        return menus;
    }

    private AdminMenuVO recursiveMenu(AdminRoleMenuDto parentRoleMenu, List<AdminRoleMenuDto> sourceData) {
        AdminMenuVO result = new AdminMenuVO();

        result.setId(parentRoleMenu.getAuthId());
        result.setName(parentRoleMenu.getAuthName());
        result.setType(parentRoleMenu.getType());
        result.setUrl(parentRoleMenu.getUrl());
        result.setIcon(parentRoleMenu.getIcon());
        result.setSort(parentRoleMenu.getSort());

        List<AdminMenuVO> subMenus = Lists.newArrayList();

        for (AdminRoleMenuDto tmp : sourceData) {
            if (tmp.getParentId() == parentRoleMenu.getAuthId()) {
                subMenus.add(recursiveMenu(tmp, sourceData));
            }
        }

        result.setSubMenus(subMenus);
        return result;
    }

    public List<AdminApiVO> getApis() {
        apis = Lists.newArrayList();

        if (roleApis == null || roleApis.size() == 0) {
            return apis;
        }

        roleApis.sort(new AdminRoleAuthDto.AuthComparator());

        for (AdminRoleApiDto roleApi : roleApis) {
            if (roleApi.getParentId() == 0) {
                apis.add(recursiveApi(roleApi, roleApis));
            }
        }

        return apis;
    }

    private AdminApiVO recursiveApi(AdminRoleApiDto parentRoleApi, List<AdminRoleApiDto> sourceData) {
        AdminApiVO result = new AdminApiVO();

        result.setId(parentRoleApi.getAuthId());
        result.setName(parentRoleApi.getAuthName());
        result.setType(parentRoleApi.getType());
        result.setUrl(parentRoleApi.getUrl());
        result.setSort(parentRoleApi.getSort());

        List<AdminApiVO> subApis = Lists.newArrayList();

        for (AdminRoleApiDto tmp : sourceData) {
            if (tmp.getParentId() == parentRoleApi.getAuthId()) {
                subApis.add(recursiveApi(tmp, sourceData));
            }
        }

        result.setSubApis(subApis);
        return result;
    }
}