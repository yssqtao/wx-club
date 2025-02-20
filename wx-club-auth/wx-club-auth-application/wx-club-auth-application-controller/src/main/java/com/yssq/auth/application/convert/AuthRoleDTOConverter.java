package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthRoleDTO;
import com.yssq.auth.domain.bo.AuthRoleBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色dto转换器
 */
@Mapper
public interface AuthRoleDTOConverter {

    AuthRoleDTOConverter INSTANCE = Mappers.getMapper(AuthRoleDTOConverter.class);

    AuthRoleBO ConvertDTO2BO(AuthRoleDTO authRoleDTO);

}
