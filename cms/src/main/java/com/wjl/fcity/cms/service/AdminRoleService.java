package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.model.AdminRole;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.entity.vo.AdminRoleVO;

import java.util.List;

/**
 * @author 黄骏毅
 */
public interface AdminRoleService {
    /**
     * 查询角色数量
     *
     * @param args 管理员用户请求体
     * @return Integer
     */
    Integer count(AdminUserReq args);

    /**
     * 查询角色列表
     *
     * @param args 管理员用户请求体
     * @return List<AdminRole>
     */
    List<AdminRole> list(AdminUserReq args);

    /**
     * 前端展示角色列表
     *
     * @param args 管理员用户请求体
     * @return List<AdminRoleVO>
     */
    List<AdminRoleVO> listToVo(AdminUserReq args);

    /**
     * 根据id查找角色
     *
     * @param id id
     * @return AdminRole
     */
    AdminRole findOne(Long id);

    /**
     * 保存角色
     *
     * @param role AdminRole
     */
    void save(AdminRole role);

    /**
     * 更新角色
     *
     * @param role AdminRole
     */
    void update(AdminRole role);
}
