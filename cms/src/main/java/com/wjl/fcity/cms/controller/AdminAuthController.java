package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.vo.AdminApiVO;
import com.wjl.fcity.cms.entity.vo.AdminMenuVO;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 *
 * @author 黄骏毅
 */
@RestController
@RequestMapping(value = "/admin/auth")
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/list")
    public Response list() {
        List<AdminMenuVO> menus = adminAuthService.menus();
        List<AdminApiVO> apis = adminAuthService.apis();
        Map<String, Object> data = new HashMap<>(2);
        data.put("menus", menus);
        data.put("apis", apis);
        return new Response<>(CodeEnum.SUCCESS, data);

    }
}
