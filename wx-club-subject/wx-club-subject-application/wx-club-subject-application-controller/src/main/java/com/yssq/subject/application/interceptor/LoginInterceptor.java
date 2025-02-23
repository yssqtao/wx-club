package com.yssq.subject.application.interceptor;

import com.yssq.subject.common.context.LoginContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * TODO 前端请求经过网关的全局拦截器，微服务此时通过自身的拦截器从header中拿到用户信息
     * 拿到用户信息之后，通过ThreadLocal保存信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每次访问微服务，查看请求路径是什么
        String requestURI = request.getRequestURI();
        System.out.println("拦截的请求路径: " + requestURI);

        String loginId = request.getHeader("loginId");
        if (StringUtils.isNotBlank(loginId)) {
            LoginContextHolder.set("loginId", loginId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LoginContextHolder.remove();
    }
}
