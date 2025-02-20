package com.yssq.auth.domain.service;


import com.yssq.auth.domain.bo.AuthRoleBO;

/**
 * 角色领域service
 */
public interface AuthRoleDomainService {

    Boolean add(AuthRoleBO authRoleBO);

    Boolean update(AuthRoleBO authRoleBO);

    Boolean delete(AuthRoleBO authRoleBO);
}
