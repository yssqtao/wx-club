package com.yssq.auth.domain.convert;

import com.yssq.auth.domain.bo.AuthRoleBO;
import com.yssq.auth.infra.basic.entity.AuthRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色bo转换器
 */
@Mapper
public interface AuthRoleBOConverter {

    AuthRoleBOConverter INSTANCE = Mappers.getMapper(AuthRoleBOConverter.class);

    AuthRole ConvertBO2PO(AuthRoleBO authRoleBO);

}
