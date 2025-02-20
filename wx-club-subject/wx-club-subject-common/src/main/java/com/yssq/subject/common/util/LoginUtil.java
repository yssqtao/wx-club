package com.yssq.subject.common.util;

import com.yssq.subject.common.context.LoginContextHolder;

/**
 * 用户登录util
 */
public class LoginUtil {
    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}
