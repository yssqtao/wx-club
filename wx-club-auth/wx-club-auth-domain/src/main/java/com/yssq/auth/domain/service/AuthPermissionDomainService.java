package com.yssq.auth.domain.service;

import com.yssq.auth.domain.bo.AuthPermissionBO;

import java.util.List;

/**
 * 角色领域service
 */
public interface AuthPermissionDomainService {

    Boolean add(AuthPermissionBO authPermissionBO);

    Boolean update(AuthPermissionBO authPermissionBO);

    Boolean delete(AuthPermissionBO authPermissionBO);

    List<String> getPermission(String userName);

}
