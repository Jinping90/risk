package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.vo.TencentCloudBean;
import com.wjl.fcity.user.model.vo.TongDunBean;
import com.wjl.fcity.user.request.SendVerifyCodeReq;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 公共
 *
 * @author shengju
 */
public interface CommonService {
    /**
     * 发送验证码
     *
     * @param sendVerifyCodeReq 手机号 短信类型
     * @return data
     * @throws UnsupportedEncodingException 短信验证码生成异常
     */
    Map sendVCode(SendVerifyCodeReq sendVerifyCodeReq) throws UnsupportedEncodingException;

    /**
     * 获取同盾数据
     *
     * @param userId userid
     * @return TongdunBean
     */
    TongDunBean saveTongDunReport(Long userId);

    /**
     * 腾讯云报告保存接口
     *
     * @param userId userId
     * @return TencentCloudBean
     */
    TencentCloudBean saveTxyReport(Long userId);

    /**
     * 保存第三方报告
     *
     * @param type     报告类型
     * @param userId   userId
     * @param verifyId 报告id
     * @param message  报告信息
     */
    void addThirdReport(Integer type, String userId, String verifyId, String message);

    /**
     * 获取滚动条信息
     *
     * @return list
     */
    List getScrollBarList();
}
