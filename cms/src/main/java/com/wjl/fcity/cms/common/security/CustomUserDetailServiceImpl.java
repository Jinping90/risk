package com.wjl.fcity.cms.common.security;

import com.google.common.collect.Lists;
import com.wjl.fcity.cms.entity.dto.AdminUserDto;
import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;
import com.wjl.fcity.cms.mapper.AdminUserMapper;
import com.wjl.fcity.cms.service.AdminUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.List;

/**
 * @author czl
 */
@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
    @Resource
    private AdminUserService adminUserService;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        AdminUser user = adminUserService.getByLoginName(loginName);

        if (user != null) {
            if (user.isLocked()) {
                throw new UserLockedException("");
            } else {
                AdminUserDto adminUserDto = user.getAdminUserDto();
                List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
                grantedAuthorities.addAll(getMenuGrantedAuthority(adminUserDto.getMenus()));
                grantedAuthorities.addAll(getApiGrantedAuthority(adminUserDto.getApis()));
                user.setGrantedAuthorities(grantedAuthorities);
                return user;
            }
        } else {
            throw new UsernameNotFoundException("");
        }
    }

    private List<GrantedAuthority> getMenuGrantedAuthority(List<AdminMenuVO> source) {
        List<GrantedAuthority> result = Lists.newArrayList();

        for (AdminMenuVO menu : source) {
            if (menu.getUrl() != null && !"".equals(menu.getUrl())) {
                GrantedAuthority grantedAuthority = new CustomGrantedAuthority(menu.getUrl());
                result.add(grantedAuthority);
            }

            if (menu.getSubMenus() != null && menu.getSubMenus().size() > 0) {
                List<GrantedAuthority> subAuths = getMenuGrantedAuthority(menu.getSubMenus());

                if (subAuths != null && subAuths.size() > 0) {
                    result.addAll(subAuths);
                }
            }
        }

        return result;
    }

    private List<GrantedAuthority> getApiGrantedAuthority(List<AdminApiVO> source) {
        List<GrantedAuthority> result = Lists.newArrayList();

        for (AdminApiVO api : source) {
            if (api.getUrl() != null && !"".equals(api.getUrl())) {
                GrantedAuthority grantedAuthority = new CustomGrantedAuthority(api.getUrl());
                result.add(grantedAuthority);
            }

            if (api.getSubApis() != null && api.getSubApis().size() > 0) {
                List<GrantedAuthority> subAuthList = getApiGrantedAuthority(api.getSubApis());

                if (subAuthList != null && subAuthList.size() > 0) {
                    result.addAll(subAuthList);
                }
            }
        }

        return result;
    }
}
