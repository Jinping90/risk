package com.wjl.fcity.bank.entity.request;

import com.wjl.fcity.bank.common.enums.AuthCategoryEnum;
import com.wjl.fcity.bank.common.enums.AuthItemEnum;
import com.wjl.fcity.bank.common.enums.AuthStatusEnum;
import lombok.Data;

/**
 * 用户中心微服务，认证记录接口调用请求体
 *
 * @author czl
 */
@Data
public class AuthRecordReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 认证类别
     */
    private Integer authCategory;
    /**
     * 认证项目
     */
    private Integer authItem;
    /**
     * 认证状态
     */
    private Integer authStatus;

    public AuthRecordReq(Long userId, AuthCategoryEnum authCategory, AuthItemEnum authItem, AuthStatusEnum authStatus) {
        this.userId = userId;
        this.authCategory = authCategory.getCode();
        this.authItem = authItem.getCode();
        this.authStatus = authStatus.getCode();
    }
}
