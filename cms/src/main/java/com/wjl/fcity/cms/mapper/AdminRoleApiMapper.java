package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.dto.AdminRoleApiDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author czl
 */
public interface AdminRoleApiMapper {

    /**
     * 查询权限
     *
     * @param roleId 角色id
     * @return List<AdminRoleApiDto>
     */
    @Select("SELECT " +
            "   t1.role_id, " +
            "   t2.id AS authId, " +
            "   t2. NAME AS authName, " +
            "   t2.type, " +
            "   t2.parent_id, " +
            "   t2.url, " +
            "   t2.sort " +
            "FROM " +
            "   admin_role_api t1 " +
            "LEFT JOIN admin_api t2 ON t1.role_id = #{roleId} " +
            "AND t1.api_id = t2.id " +
            "WHERE " +
            "   t2.is_valid = 1;")
    List<AdminRoleApiDto> getByRoleId(long roleId);

    /**
     * 保存权限点
     *
     * @param roleId 角色id
     * @param apiId  apiId
     * @return int
     */
    @Insert("INSERT INTO `admin_role_api` ( " +
            "   `role_id`, " +
            "   `api_id`, " +
            "   `gmt_created` " +
            ") " +
            "VALUES " +
            "   (#{roleId}, #{apiId}, NOW());")
    int save(@Param("roleId") Long roleId, @Param("apiId") Long apiId);

    /**
     * 根据角色Id删除权限点
     *
     * @param roleId 角色id
     */
    @Delete("DELETE FROM admin_role_api WHERE `role_id` = #{roleId} ")
    void delete(@Param("roleId") Long roleId);
}
