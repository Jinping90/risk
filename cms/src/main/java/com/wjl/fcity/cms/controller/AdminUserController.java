package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.common.util.Md5Util;
import com.wjl.fcity.cms.common.util.SecurityUtil;
import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.AdminUserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄骏毅
 */
@RestController
@RequestMapping(value = "/admin/user")
public class AdminUserController {
    @Resource
    private AdminUserService adminUserService;

    /**
     * 获取用户列表
     *
     * @param args 管理员用户请求体
     * @return Response
     */
    @PostMapping(value = "list")
    public Response list(@RequestBody AdminUserReq args) {
        List<AdminUser> list = adminUserService.list(args);
        Integer count = adminUserService.count(args);
        Map<String, Object> data = new HashMap<>(4);
        data.put("list", list);
        data.put("total", count);
        data.put("size", args.getSize());
        data.put("page", args.getPage());
        return new Response<>(CodeEnum.SUCCESS, data);

    }

    /**
     * 根据id查找用户
     *
     * @param role AdminUser
     * @return Response
     */
    @PostMapping("/info")
    public Response info(@RequestBody AdminUser role) {
        AdminUser adminUser = adminUserService.findOne(role.getId());
        Map<String, Object> data = new HashMap<>(1);
        data.put("user", adminUser);
        return new Response<>(CodeEnum.SUCCESS, data);
    }

    /**
     * 保存用户
     *
     * @param role AdminUser
     * @return Response
     */
    @PostMapping("/save")
    public Response save(@RequestBody AdminUser role) {
        adminUserService.save(role);
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 更新用户
     *
     * @param role AdminUser
     * @return Response
     */
    @PostMapping("/update")
    public Response update(@RequestBody AdminUser role) {
        adminUserService.update(role);
        return new Response<>(CodeEnum.SUCCESS, null);
    }

    /**
     * 修改密码
     *
     * @param args 管理员用户请求体
     * @return Response
     */
    @PostMapping("/modifyPassword")
    public Response modifyPassword(@RequestBody AdminUserReq args) {
        AdminUser adminUser = SecurityUtil.getSecurityUser();
        if (adminUser == null) {
            return new Response<>(CodeEnum.AUTH_NOT_LOGIN, null);
        }
        String newPassword = args.getNewPassword();
        Long adminUserId = adminUser.getId();
        if (StringUtils.isEmpty(newPassword)) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }

        adminUserService.modifyPassword(adminUserId, Md5Util.encode(newPassword));
        return new Response<>(CodeEnum.SUCCESS, null);

    }

}
