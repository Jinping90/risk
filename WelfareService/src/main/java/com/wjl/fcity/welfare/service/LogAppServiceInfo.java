package com.wjl.fcity.welfare.service;

/**
 * @author czl
 */
public interface LogAppServiceInfo {

    /**
     * 保存app错误码日志记录
     * @param code 错误码
     * @param message 错误提示内容
     */
    void saveWrongCodeLog(String code, String message);
}
