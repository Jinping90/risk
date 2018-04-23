package com.wjl.fcity.loan.gateway.constant;

import com.google.common.collect.ImmutableSet;

/**
 * 5秒钟内不能够重复请求的url
 * @author shengju
 */
public class ImmutableConfig {

    /**
     * 5秒钟内不能够重复请求的url
     */
    public static ImmutableSet<String> SecondNotCanRepeatRequestUrlSet = null;

    static {
        ImmutableSet.Builder<String> builder = new ImmutableSet.Builder<>();
        builder.add("/Api-App/user/user/verifyCodeLogin");
        builder.add("/Api-App/auth/pay-confirm");
        builder.add("/Api-App/auth/verify-fail-confirm");
        builder.add("/Api-App/auth/repayment");
        builder.add("/Api-App/auth/repayment-confirm");
        SecondNotCanRepeatRequestUrlSet = builder.build();
    }

}
