package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;
import com.wjl.fcity.cms.mapper.AdminAuthMapper;
import com.wjl.fcity.cms.service.AdminAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单和api树形展示
 *
 * @author 黄骏毅
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminAuthMapper adminAuthMapper;

    @Override
    public List<AdminMenuVO> menus() {
        List<AdminMenuVO> result = new ArrayList<>();
        List<AdminMenuVO> menus = adminAuthMapper.menus();
        for (AdminMenuVO menu : menus) {
            if (menu.getParentId() == 0) {
                result.add(recursiveMenu(menu, menus));
            }
        }
        return result;
    }

    @Override
    public List<AdminApiVO> apis() {

        List<AdminApiVO> result = new ArrayList<>();
        List<AdminApiVO> menus = adminAuthMapper.apis();
        for (AdminApiVO api : menus) {
            if (api.getParentId() == 0) {
                result.add(recursiveApi(api, menus));
            }
        }
        return result;
    }


    private AdminMenuVO recursiveMenu(AdminMenuVO parent, List<AdminMenuVO> list) {
        ArrayList<AdminMenuVO> subMenus = new ArrayList<>();

        for (AdminMenuVO tmp : list) {
            if (tmp.getParentId().equals(parent.getId())) {
                subMenus.add(recursiveMenu(tmp, list));
            }
        }
        parent.setSubMenus(subMenus);
        return parent;

    }


    private AdminApiVO recursiveApi(AdminApiVO parent, List<AdminApiVO> list) {
        ArrayList<AdminApiVO> subApis = new ArrayList<>();

        for (AdminApiVO tmp : list) {
            if (tmp.getParentId().equals(parent.getId())) {
                subApis.add(recursiveApi(tmp, list));
            }
        }
        parent.setSubApis(subApis);
        return parent;

    }


}
