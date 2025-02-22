package com.yssq.auth.application.controller;

import com.alibaba.fastjson.JSON;
import com.yssq.auth.application.convert.AuthPermissionDTOConverter;
import com.yssq.auth.application.dto.AuthPermissionDTO;
import com.yssq.auth.common.entity.Result;
import com.yssq.auth.domain.bo.AuthPermissionBO;
import com.yssq.auth.domain.service.AuthPermissionDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission/")
@Slf4j
public class PermissionController {

    @Resource
    private AuthPermissionDomainService authPermissionDomainService;

    /**
     * 新增权限
     */
    @RequestMapping("add")
    public Result<Boolean> add(@RequestBody AuthPermissionDTO authPermissionDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("PermissionController.add.dto:{}", JSON.toJSONString(authPermissionDTO));
            }
            Assert.notNull(authPermissionDTO.getName(), "权限名称不能为空");
            Assert.notNull(authPermissionDTO.getParentId(), "权限父id不能为空");
            AuthPermissionBO permissionBO = AuthPermissionDTOConverter.INSTANCE.ConvertDTO2BO(authPermissionDTO);
            return Result.ok(authPermissionDomainService.add(permissionBO));
        } catch (Exception e) {
            log.error("PermissionController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增权限失败");
        }
    }

    /**
     * 修改权限
     */
    @RequestMapping("update")
    public Result<Boolean> update(@RequestBody AuthPermissionDTO authPermissionDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("PermissionController.update.dto:{}", JSON.toJSONString(authPermissionDTO));
            }
            Assert.notNull(authPermissionDTO.getId(), "权限id不能为空");
            AuthPermissionBO permissionBO = AuthPermissionDTOConverter.INSTANCE.ConvertDTO2BO(authPermissionDTO);
            return Result.ok(authPermissionDomainService.update(permissionBO));
        } catch (Exception e) {
            log.error("PermissionController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新权限信息失败");
        }
    }

    /**
     * 删除权限
     */
    @RequestMapping("delete")
    public Result<Boolean> delete(@RequestBody AuthPermissionDTO authPermissionDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("PermissionController.delete.dto:{}", JSON.toJSONString(authPermissionDTO));
            }
            Assert.notNull(authPermissionDTO.getId(), "权限id不能为空");
            AuthPermissionBO permissionBO = AuthPermissionDTOConverter.INSTANCE.ConvertDTO2BO(authPermissionDTO);
            return Result.ok(authPermissionDomainService.delete(permissionBO));
        } catch (Exception e) {
            log.error("PermissionController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除权限信息失败");
        }
    }

    /**
     * 查询用户权限
     */
    @RequestMapping("getPermission")
    public Result<Boolean> getPermission(String userName) {
        try {
            log.info("PermissionController.getPermission.userName:{}", userName);
            Assert.notNull(userName, "用户id不能为空");
            return Result.ok(authPermissionDomainService.getPermission(userName));
        } catch (Exception e) {
            log.error("PermissionController.getPermission.error:{}", e.getMessage(), e);
            return Result.fail("查询用户权限信息失败");
        }
    }

}
