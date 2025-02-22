package com.yssq.wxclub.gateway.auth;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限认证的配置器
 */
@Configuration
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器 
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    //查看前端访问的路径
                    System.out.println("-------- 前端访问path：" + SaHolder.getRequest().getRequestPath());
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    // 如果客户端请求的路径就是/auth/user/doLogin，则不会执行后面的逻辑
                    // SaRouter.match("/**", "/auth/user/doLogin", r -> StpUtil.checkLogin());

                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/auth/**", "/auth/user/doLogin", r -> StpUtil.checkLogin());
                    SaRouter.match("/oss/**", r -> StpUtil.checkLogin());
//                    SaRouter.match("/subject/subject/category/add", r -> StpUtil.checkPermission("subject:add"));
                    SaRouter.match("/subject/subject/category/add", r -> StpUtil.checkLogin());

                    SaRouter.match("/subject/**", r -> StpUtil.checkLogin());
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                /*.setError(e -> {
                    return SaResult.error(e.getMessage());
                })*/
                ;
    }
}
