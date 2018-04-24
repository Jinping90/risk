package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.entity.model.AdminRole;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.entity.vo.AdminRoleVO;
import com.wjl.fcity.cms.mapper.AdminRoleApiMapper;
import com.wjl.fcity.cms.mapper.AdminRoleMapper;
import com.wjl.fcity.cms.mapper.AdminRoleMenuMapper;
import com.wjl.fcity.cms.service.AdminRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色权限操作
 * @author 黄骏毅
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Resource
    private AdminRoleMapper adminRoleMapper;
    @Resource
    private AdminRoleApiMapper adminRoleApiMapper;
    @Resource
    private AdminRoleMenuMapper adminRoleMenuMapper;

    @Override
    public Integer count(AdminUserReq args) {
        return adminRoleMapper.count(args);
    }

    @Override
    public List<AdminRole> list(AdminUserReq args) {
        return adminRoleMapper.list(args);
    }

    @Override
    public List<AdminRoleVO> listToVo(AdminUserReq args) {
        List<AdminRoleVO> result = new ArrayList<>();
        List<AdminRole> list = list(args);
        if (list == null || list.isEmpty()) {
            return result;

        }
        for (AdminRole adminRole : list) {
            AdminRoleVO vo = new AdminRoleVO();
            BeanUtils.copyProperties(adminRole, vo);
            result.add(vo);
        }

        return result;

    }

    @Override
    public AdminRole findOne(Long id) {
        AdminRole role = adminRoleMapper.findOne(id);
        if (role != null) {
            role.setRoleMenus(adminRoleMenuMapper.getByRoleId(id));
            role.setRoleApis(adminRoleApiMapper.getByRoleId(id));
        }
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AdminRole role) {
        Date now = new Date();
        role.setGmtCreated(now);
        role.setGmtModified(now);
        adminRoleMapper.save(role);
        saveOrUpdateAuth(getAuthIds(role.getMenus()), getAuthIds(role.getApis()), role.getId(), 0L);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AdminRole role) {
        role.setGmtModified(new Date());
        adminRoleMapper.update(role);
        saveOrUpdateAuth(getAuthIds(role.getMenus()), getAuthIds(role.getApis()), role.getId(), 1L);
    }

    /**
     * @param menus 菜单权限id
     * @param apis  api权限id
     * @param id    角色id
     * @param type  0保存 1修改
     */
    private void saveOrUpdateAuth(Long[] menus, Long[] apis, Long id, Long type) {
        if (id == null) {
            return;
        }
        //保存操作
        if (new Long(0).equals(type)) {
            insertMenus(menus, id);
            insertApis(apis, id);
        }
        //修改操作
        else if (new Long(1).equals(type)) {
            //只有在新权限点不为空的时候做更新操作,防止误操作
            if (menus != null && menus.length > 0) {
                adminRoleMenuMapper.delete(id);
                insertMenus(menus, id);
            }
            //只有在新权限点不为空的时候做更新操作,防止误操作
            if (apis != null && apis.length > 0) {
                adminRoleApiMapper.delete(id);
                insertApis(apis, id);
            }
        }


    }

    private void insertMenus(Long[] menus, Long roleId) {
        if (menus == null || menus.length < 1 || roleId == null) {
            return;
        }
        for (Long menu : menus) {
            adminRoleMenuMapper.save(roleId, menu);
        }

    }

    private void insertApis(Long[] apis, Long roleId) {
        if (apis == null || apis.length < 1 || roleId == null) {
            return;
        }
        for (Long api : apis) {
            adminRoleApiMapper.save(roleId, api);
        }

    }


    private Long[] getAuthIds(String para) {
        Long[] result = null;
        if (StringUtils.isBlank(para)) {
            return null;
        }
        String[] strArrays = para.split(",");
        if (strArrays.length > 0) {
            result = new Long[strArrays.length];
            for (int i = 0; i < strArrays.length; i++) {
                result[i] = Long.valueOf(strArrays[i]);
            }

        }
        return result;
    }
}
