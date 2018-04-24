package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.dto.AdminRoleMenuDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author czl
 */
public interface AdminRoleMenuMapper {

    /**
     * 获取AdminRoleMenuDto
     * @param roleId roleId
     * @return List<AdminRoleMenuDto>
     */
    @Select("SELECT " +
            "   t1.role_id, " +
            "   t2.id AS authId, " +
            "   t2. NAME AS authName, " +
            "   t2.type, " +
            "   t2.parent_id, " +
            "   t2.url, " +
            "   t2.icon, " +
            "   t2.sort " +
            "FROM " +
            "   admin_role_menu t1 " +
            "LEFT JOIN admin_menu t2 ON t1.role_id = #{roleId} " +
            "AND t1.menu_id = t2.id " +
            "WHERE " +
            "   t2.is_valid = 1")
    List<AdminRoleMenuDto> getByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存权限点
     *
     * @param roleId 角色id
     * @param menuId  menuId
     * @return int
     */
    @Insert("INSERT INTO `admin_role_menu` (`role_id`, `menu_id`, `gmt_created`)VALUES (#{roleId}, #{menuId}, NOW()) ")
    int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 根据角色Id删除权限点
     *
     * @param roleId 角色id
     */
    @Delete("DELETE FROM admin_role_menu WHERE `role_id` = #{roleId} ")
    void delete(@Param("roleId") Long roleId);
}