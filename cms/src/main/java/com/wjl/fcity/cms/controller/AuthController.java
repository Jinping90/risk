package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.common.exception.BaseException;
import com.wjl.fcity.cms.common.util.Constants;
import com.wjl.fcity.cms.entity.dto.AdminUserDto;
import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping(value = "/login")
    @Transactional(rollbackFor = Exception.class)
    public AdminUserDto login(@AuthenticationPrincipal AdminUser adminUser) {
        if (adminUser == null) {
            throw new BaseException(CodeEnum.AUTH_ILLEGAL);
        }

        adminUserService.updateLastVisitTime(adminUser.getId());
        return adminUser.getAdminUserDto();
    }

    @PostMapping(value = "/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !Constants.AUTH_ANONYMOUS_USER.equals(auth.getPrincipal())) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new Response<>(CodeEnum.SUCCESS, "");
    }
}
