package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthPermissionDTO;
import com.yssq.auth.domain.bo.AuthPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 权限dto转换器
 */
@Mapper
public interface AuthPermissionDTOConverter {

    AuthPermissionDTOConverter INSTANCE = Mappers.getMapper(AuthPermissionDTOConverter.class);

    AuthPermissionBO ConvertDTO2BO(AuthPermissionDTO authPermissionDTO);

}
