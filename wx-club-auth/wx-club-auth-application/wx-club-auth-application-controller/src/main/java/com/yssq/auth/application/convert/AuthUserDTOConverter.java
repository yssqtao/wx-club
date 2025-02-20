package com.yssq.auth.application.convert;

import com.yssq.auth.application.dto.AuthUserDTO;
import com.yssq.auth.domain.bo.AuthUserBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthUserDTOConverter {
    AuthUserDTOConverter INSTANCE = Mappers.getMapper(AuthUserDTOConverter.class);

    //DTO -> BO
    AuthUserBO ConvertDTO2BO(AuthUserDTO authUserDTO);

    //BOList -> DTOList
    List<AuthUserDTO> ConvertBOList2DTOList(List<AuthUserBO> authUserBOList);

    //BO -> DTO
    AuthUserDTO ConvertBO2DTO(AuthUserBO authUserBO);


}
