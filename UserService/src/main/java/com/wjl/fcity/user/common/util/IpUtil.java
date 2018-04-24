package com.wjl.fcity.user.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shengju
 */
public class IpUtil {

    public static String getIp(HttpServletRequest request) {
        String unknown = "unknown";
        String comma = ",";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
