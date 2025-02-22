package com.yssq.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.google.gson.Gson;
import com.yssq.auth.common.enums.AuthUserStatusEnum;
import com.yssq.auth.common.enums.IsDeletedFlagEnum;
import com.yssq.auth.domain.bo.AuthUserBO;
import com.yssq.auth.domain.constants.AuthConstant;
import com.yssq.auth.domain.convert.AuthUserBOConverter;
import com.yssq.auth.domain.redis.RedisUtil;
import com.yssq.auth.domain.service.AuthUserDomainService;
import com.yssq.auth.infra.basic.entity.*;
import com.yssq.auth.infra.basic.service.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息表(AuthUser)表服务实现类
 *
 * @author makejava
 * @since 2025-02-19 18:04:59
 */
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {
    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthPermissionService authPermissionService;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    private String salt = "zt";

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.ConvertBO2PO(authUserBO);
        //设置用户状态，在common模块中，定义枚举
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        Integer count = authUserService.insert(authUser);
        //TODO 建立角色关联
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        //根据role key查询角色信息，即查询普通角色的信息
        AuthRole roleResult = authRoleService.queryByCondition(authRole);
        //取到两个id，这是auth_user_role表中的两个关键信息
        Long roleId = roleResult.getId();
        Long userId = authUser.getId();
        //创建AuthUserRole对象，用于关联 user 和 role
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        //除了上述两个信息，还需要设置is_deleted字段名
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        //将关联关系插入到数据库中
        authUserRoleService.insert(authUserRole);

        //TODO 缓存用户角色到redis
        //生成key，格式为 auth.role.<username>
        String roleKey = redisUtil.buildKey(authRolePrefix, authUser.getUserName());
        //使用List是考虑到一个用户拥有多个角色的情况，比如既是普通用户，又是管理员
        List<AuthRole> roleList = new LinkedList<>();
        roleList.add(authRole);
        //如果只有一个角色，redisUtil.set(roleKey, new Gson().toJson(authRole));
        redisUtil.set(roleKey, new Gson().toJson(roleList));

        //TODO 查询角色权限，并将其缓存到redis
        AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(roleId);
        //根据roleId 查询角色权限关联表
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.
                queryByCondition(authRolePermission);
        //提取所有的 permissionId
        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        //缓存角色权限到 Redis
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authUser.getUserName());
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));

        return count > 0;
    }

    @Override
    public Boolean update(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.ConvertBO2PO(authUserBO);
        Integer count = authUserService.update(authUser);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setId(authUserBO.getId());
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authUserService.update(authUser);
        //有任何的更新，都要与缓存进行同步的修改
        return count > 0;
    }

    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setUserName(authUserBO.getUserName());
        List<AuthUser> userList = authUserService.queryByCondition(authUser);
        if (CollectionUtils.isEmpty(userList)) {
            return new AuthUserBO();
        }
        AuthUser user = userList.get(0);
        return AuthUserBOConverter.INSTANCE.ConvertPO2BO(user);
    }

    @Override
    public List<AuthUserBO> listUserInfoByIds(List<String> userNameList) {
        List<AuthUser> userList = authUserService.listUserInfoByIds(userNameList);
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return AuthUserBOConverter.INSTANCE.ConvertPOList2BOList(userList);
    }
}
