package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.model.AdminRole;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.entity.vo.AdminRoleVO;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄骏毅
 */
@RestController
@RequestMapping(value = "/admin/role")
public class AdminRoleController {
    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 前端展示角色列表
     *
     * @param args 管理员用户请求体
     * @return Response
     */
    @PostMapping("/list")
    public Response list(@RequestBody AdminUserReq args) {
        List<AdminRoleVO> list = adminRoleService.listToVo(args);
        Integer count = adminRoleService.count(args);
        Map<String, Object> data = new HashMap<>(4);
        data.put("list", list);
        data.put("total", count);
        data.put("size", args.getSize());
        data.put("page", args.getPage());
        return new Response<>(CodeEnum.SUCCESS, data);

    }

    /**
     * 根据id查找角色
     *
     * @param role AdminRole
     * @return Response
     */
    @PostMapping("/info")
    public Response info(@RequestBody AdminRole role) {
        AdminRole adminRole = adminRoleService.findOne(role.getId());
        Map<String, Object> data = new HashMap<>(1);
        data.put("role", adminRole);
        return new Response<>(CodeEnum.SUCCESS, data);
    }

    /**
     * 保存角色
     *
     * @param role AdminRole
     * @return Response
     */
    @PostMapping("/save")
    public Response save(@RequestBody AdminRole role) {
        adminRoleService.save(role);
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 更新角色
     *
     * @param role AdminRole
     * @return Response
     */
    @PostMapping("/update")
    public Response update(@RequestBody AdminRole role) {
        adminRoleService.update(role);
        return new Response<>(CodeEnum.SUCCESS, null);
    }
}
