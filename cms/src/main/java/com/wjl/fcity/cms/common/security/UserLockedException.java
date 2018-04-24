package com.wjl.fcity.cms.common.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author czl
 */
public class UserLockedException extends AuthenticationException {

    /**
     * Constructs a <code>UsernameNotFoundException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    UserLockedException(String msg) {
        super(msg);
    }
}
