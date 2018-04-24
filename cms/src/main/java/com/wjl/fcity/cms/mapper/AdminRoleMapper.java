package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.dto.AdminRoleDto;
import com.wjl.fcity.cms.entity.model.AdminRole;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.mapper.provider.AdminRoleProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author czl
 */
public interface AdminRoleMapper {

    /**
     * 获取角色
     *
     * @param roleId 角色id
     * @return AdminRoleDto
     */
    @Select("SELECT id, name FROM admin_role where id = #{roleId}")
    AdminRoleDto getByRoleId(long roleId);

    /**
     * 获取角色数量
     *
     * @param args 请求体
     * @return Integer
     */
    @SelectProvider(type = AdminRoleProvider.class, method = "count")
    Integer count(AdminUserReq args);

    /**
     * 获取角色列表
     *
     * @param args 请求体
     * @return List<AdminRole>
     */
    @SelectProvider(type = AdminRoleProvider.class, method = "list")
    List<AdminRole> list(AdminUserReq args);

    /**
     * 根据id查找角色
     *
     * @param id 角色id
     * @return AdminRole
     */
    @Select("SELECT * FROM admin_role where id = #{id}")
    AdminRole findOne(Long id);

    /**
     * 保存角色
     *
     * @param role AdminRole
     * @return int
     */
    @InsertProvider(type = AdminRoleProvider.class, method = "save")
    @Options(useGeneratedKeys = true)
    int save(AdminRole role);

    /**
     * 更新角色
     *
     * @param role AdminRole
     * @return int
     */
    @UpdateProvider(type = AdminRoleProvider.class, method = "update")
    int update(AdminRole role);


}
