package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.entity.dto.AdminRoleApiDto;
import com.wjl.fcity.cms.entity.dto.AdminRoleDto;
import com.wjl.fcity.cms.entity.dto.AdminRoleMenuDto;
import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.mapper.AdminRoleApiMapper;
import com.wjl.fcity.cms.mapper.AdminRoleMapper;
import com.wjl.fcity.cms.mapper.AdminRoleMenuMapper;
import com.wjl.fcity.cms.mapper.AdminUserMapper;
import com.wjl.fcity.cms.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author czl
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private AdminRoleMapper adminRoleMapper;
    @Resource
    private AdminRoleMenuMapper adminRoleMenuMapper;
    @Resource
    private AdminRoleApiMapper adminRoleApiMapper;

    @Override
    public AdminUser getByLoginName(String loginName) {
        AdminUser adminUser = adminUserMapper.getByLoginName(loginName);
        AdminRoleDto adminRoleDto = adminRoleMapper.getByRoleId(adminUser.getRoleId());
        List<AdminRoleMenuDto> adminRoleMenuDtoList = adminRoleMenuMapper.getByRoleId(adminUser.getRoleId());
        List<AdminRoleApiDto> adminRoleApiDtoList = adminRoleApiMapper.getByRoleId(adminUser.getRoleId());

        adminUser.setRole(adminRoleDto);
        adminUser.setRoleMenus(adminRoleMenuDtoList);
        adminUser.setRoleApis(adminRoleApiDtoList);
        return adminUser;
    }

    @Override
    public int updateLastVisitTime(long userId) {
        return adminUserMapper.updateLastVisitTime(userId);
    }

    @Override
    public List<AdminUser> list(AdminUserReq args) {
        return adminUserMapper.list(args);
    }

    @Override
    public Integer count(AdminUserReq args) {
        return adminUserMapper.count(args);
    }

    @Override
    public AdminUser findOne(Long id) {
        return adminUserMapper.findOne(id);
    }

    @Override
    public int save(AdminUser user) {
        Date now = new Date();
        //初始化密码（admin）
        user.setPassword("a0e6a4960e77b8d54be9abafacd3a167");
        user.setGmtCreated(now);
        user.setGmtModified(now);
        return adminUserMapper.save(user);
    }

    @Override
    public int update(AdminUser user) {
        user.setGmtModified(new Date());
        return adminUserMapper.update(user);
    }

    @Override
    public void modifyPassword(Long adminUserId, String newPassword) {
        adminUserMapper.modifyPassword(adminUserId, newPassword);
    }
}
