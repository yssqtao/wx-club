package com.yssq.auth.domain.convert;

import com.yssq.auth.domain.bo.AuthPermissionBO;
import com.yssq.auth.infra.basic.entity.AuthPermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 权限bo转换器
 */
@Mapper
public interface AuthPermissionBOConverter {

    AuthPermissionBOConverter INSTANCE = Mappers.getMapper(AuthPermissionBOConverter.class);

    AuthPermission ConvertBO2PO(AuthPermissionBO authPermissionBO);

}
