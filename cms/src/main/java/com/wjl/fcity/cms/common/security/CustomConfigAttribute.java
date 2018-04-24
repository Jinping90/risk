package com.wjl.fcity.cms.common.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @author czl
 */
public class CustomConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest httpServletRequest;

    CustomConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
