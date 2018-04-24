package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.request.AdminUserReq;

import java.util.List;

/**
 * @author czl
 */
public interface AdminUserService {
    /**
     * 通过登录名获取用户
     *
     * @param loginName 用户名称
     * @return AdminUser
     */
    AdminUser getByLoginName(String loginName);

    /**
     * 更新最新访问时间
     *
     * @param userId 用户id
     * @return int
     */
    int updateLastVisitTime(long userId);

    /**
     * 获取用户列表
     *
     * @param args 管理员用户请求体
     * @return List<AdminUser>
     */
    List<AdminUser> list(AdminUserReq args);

    /**
     * 获取用户数量
     *
     * @param args 管理员用户请求体
     * @return List<AdminUser>
     */
    Integer count(AdminUserReq args);

    /**
     * 根据id查找id
     *
     * @param id id
     * @return AdminUser
     */
    AdminUser findOne(Long id);

    /**
     * 保存用户
     *
     * @param user AdminUser
     * @return int
     */
    int save(AdminUser user);

    /**
     * 更新用户
     *
     * @param user AdminUser
     * @return int
     */
    int update(AdminUser user);

    /**
     * 修改密码
     *
     * @param adminUserId 管理员id
     * @param newPassword 新密码
     * @return int
     */
    void modifyPassword(Long adminUserId, String newPassword);

}
