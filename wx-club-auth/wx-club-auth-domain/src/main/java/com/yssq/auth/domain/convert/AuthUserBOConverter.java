package com.yssq.auth.domain.convert;

import com.yssq.auth.domain.bo.AuthUserBO;
import com.yssq.auth.infra.basic.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthUserBOConverter {
    AuthUserBOConverter INSTANCE = Mappers.getMapper(AuthUserBOConverter.class);

    //BO -> PO
    AuthUser ConvertBO2PO(AuthUserBO authUserBO);

    //POList -> DBOList
    List<AuthUserBO> ConvertPOList2BOList(List<AuthUser> authUserList);

    //BO -> PO
    AuthUserBO ConvertPO2BO(AuthUser authUser);

}
