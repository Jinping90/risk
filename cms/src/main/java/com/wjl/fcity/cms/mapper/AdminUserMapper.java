package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.mapper.provider.AdminUserProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author czl
 */
public interface AdminUserMapper {

    /**
     * 查询用户信息
     *
     * @param loginName 用户名
     * @return AdminUser
     */
    @Select("select * from admin_user where login_name = #{loginName};")
    AdminUser getByLoginName(String loginName);

    /**
     * 更新时间
     *
     * @param userId userId
     * @return int
     */
    @Update("update admin_user set last_visit_time = NOW() where id = #{userId}")
    int updateLastVisitTime(long userId);

    /**
     * 取用户列表
     *
     * @param args 管理员用户请求体
     * @return Integer
     */
    @SelectProvider(type = AdminUserProvider.class, method = "list")
    List<AdminUser> list(AdminUserReq args);

    /**
     * 获取用户数量
     *
     * @param args 管理员用户请求体
     * @return Integer
     */
    @SelectProvider(type = AdminUserProvider.class, method = "count")
    Integer count(AdminUserReq args);

    /**
     * 根据id查找用户
     *
     * @param id id
     * @return AdminUser
     */
    @Select("SELECT * FROM admin_user where id = #{id}")
    AdminUser findOne(Long id);

    /**
     * 保存用户
     *
     * @param role AdminUser
     * @return int
     */
    @InsertProvider(type = AdminUserProvider.class, method = "save")
    int save(AdminUser role);

    /**
     * 更新用户
     *
     * @param role AdminUser
     * @return int
     */
    @UpdateProvider(type = AdminUserProvider.class, method = "update")
    int update(AdminUser role);


    /**
     * 更改操作员密码
     *
     * @param adminUserId 操作员ID
     * @param newPassword 新密码
     * @return int
     */
    @Update("UPDATE admin_user AS au SET au.password = #{newPassword} WHERE au.id = #{adminUserId}")
    int modifyPassword(@Param("adminUserId") Long adminUserId, @Param("newPassword") String newPassword);

}
