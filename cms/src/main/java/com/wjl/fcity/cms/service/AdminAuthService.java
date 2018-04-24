package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;

import java.util.List;

/**
 * @author 黄骏毅
 */
public interface AdminAuthService {
    /**
     * 获取菜单权限
     *
     * @return List<AdminMenuVo>
     */
    List<AdminMenuVO> menus();

    /**
     * 获取api权限
     *
     * @return List<AdminApiVo>
     */
    List<AdminApiVO> apis();
}
