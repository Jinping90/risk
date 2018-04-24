package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 黄骏毅
 */
public interface AdminAuthMapper {
    /**
     * 获取菜单权限
     *
     * @return List<AdminMenuVo>
     */
    @Select("SELECT * FROM admin_menu WHERE is_valid=1 ORDER BY parent_id ,sort ")
    List<AdminMenuVO> menus();

    /**
     * 获取api权限
     *
     * @return List<AdminApiVo>
     */
    @Select("SELECT * FROM admin_api WHERE is_valid=1 ORDER BY parent_id ,sort")
    List<AdminApiVO> apis();
}
