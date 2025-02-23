/*
package com.yssq.subject.infra.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yssq.subject.common.context.LoginContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

//自定义填充公共字段，需要继承 BaseMapper

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 公共字段常量，修改更方便
    private static final String CREATE_TIME = "createBy";
    private static final String UPDATE_TIME = "updateTime";
    private static final String CREATE_USER = "createBy";
    private static final String UPDATE_USER = "updateBy";

    // 插入操作，自动填充

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("当前用户登录id: {}", LoginContextHolder.getLoginId());
        log.info("insert方法的公共字段自动填充...");

        // 获取当前登录用户 ID
        String loginId = getCurrentLoginId(); // 如果获取不到用户 ID，会抛出异常

        // 使用 strictInsertFill 填充字段
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        // 如果属性是 LocalDateTime 类型
        // this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, UPDATE_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, CREATE_USER, String.class, loginId);
        this.strictInsertFill(metaObject, UPDATE_USER, String.class, loginId);
    }

    //更新操作，自动填充

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("当前用户登录id: {}", LoginContextHolder.getLoginId());
        log.info("update方法的公共字段自动填充...");

        // 获取当前登录用户 ID
        String loginId = getCurrentLoginId(); // 如果获取不到用户 ID，会抛出异常

        // 使用 strictUpdateFill 填充字段
        this.strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
        this.strictUpdateFill(metaObject, UPDATE_USER, String.class, loginId);
    }

    //获取当前登录用户 ID

    private String getCurrentLoginId() {
        String loginId = LoginContextHolder.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            log.error("从 LoginContextHolder 获取登录用户 ID 失败！");
            throw new IllegalStateException("用户未登录，无法获取登录用户 ID！");
        }
        return loginId;
    }
}


*/
