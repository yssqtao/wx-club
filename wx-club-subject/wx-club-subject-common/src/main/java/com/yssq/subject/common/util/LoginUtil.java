package com.yssq.subject.common.util;

import com.yssq.subject.common.context.LoginContextHolder;

/**
 * TODO 用户登录util
 * 应该放在common层，这样其他模块直接调用LoginUtil，拿到登录用户的信息
 */
public class LoginUtil {
    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}
