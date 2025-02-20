package com.yssq.auth.application.controller;

import com.alibaba.fastjson.JSON;
import com.yssq.auth.application.convert.AuthRolePermissionDTOConverter;
import com.yssq.auth.application.dto.AuthRolePermissionDTO;
import com.yssq.auth.common.entity.Result;
import com.yssq.auth.domain.bo.AuthRolePermissionBO;
import com.yssq.auth.domain.service.AuthRolePermissionDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rolePermission/")
@Slf4j
public class RolePermissionController {

    @Resource
    private AuthRolePermissionDomainService authRolePermissionDomainService;

    /**
     * 新增角色权限关联关系
     */
    @RequestMapping("add")
    public Result<Boolean> add(@RequestBody AuthRolePermissionDTO authRolePermissionDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("RolePermissionController.add.dto:{}", JSON.toJSONString(authRolePermissionDTO));
            }
            Assert.notNull(authRolePermissionDTO.getPermissionIdList(),"权限关联不能为空");
            Assert.notNull(authRolePermissionDTO.getRoleId(),"角色不能为空");
            AuthRolePermissionBO rolePermissionBO = AuthRolePermissionDTOConverter.INSTANCE.ConvertDTO2BO(authRolePermissionDTO);
            return Result.ok(authRolePermissionDomainService.add(rolePermissionBO));
        } catch (Exception e) {
            log.error("PermissionController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增角色权限失败");
        }
    }

}
