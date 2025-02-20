package com.yssq.auth.domain.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yssq.auth.domain.bo.AuthUserBO;
import com.yssq.auth.infra.basic.entity.AuthUser;

import java.util.List;

/**
 * 用户信息表(AuthUser)表服务接口
 *
 * @author makejava
 * @since 2025-02-19 18:04:58
 */
public interface AuthUserDomainService {

    Boolean register(AuthUserBO authUserBO);

    Boolean update(AuthUserBO authUserBO);

    Boolean delete(AuthUserBO authUserBO);

    AuthUserBO getUserInfo(AuthUserBO authUserBO);

    List<AuthUserBO> listUserInfoByIds(List<String> userNameList);
}
