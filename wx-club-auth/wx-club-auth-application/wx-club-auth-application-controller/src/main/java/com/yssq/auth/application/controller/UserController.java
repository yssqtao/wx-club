package com.yssq.auth.application.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.yssq.auth.application.convert.AuthUserDTOConverter;
import com.yssq.auth.application.dto.AuthUserDTO;
import com.yssq.auth.common.entity.Result;
import com.yssq.auth.domain.bo.AuthUserBO;
import com.yssq.auth.domain.service.AuthUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {
    @Resource
    private AuthUserDomainService authUserDomainService;

    /**
     * 用户注册
     */
    @PostMapping("register")
    public Result<Boolean> register(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.add.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Assert.notNull(authUserDTO.getUserName(), "用户名不能为空");
            Assert.notNull(authUserDTO.getEmail(), "邮件地址不能为空");
            Assert.notNull(authUserDTO.getPassword(), "密码不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.ConvertDTO2BO(authUserDTO);
            Boolean result = authUserDomainService.register(authUserBO);
            return Result.ok("新增用户成功");
        } catch (Exception e) {
            log.error("UserController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增用户失败");
        }
    }

    /**
     * 获取用户信息
     */
    @RequestMapping("getUserInfo")
    public Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.getUserInfo.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Assert.notNull(authUserDTO.getUserName(), "用户名不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.ConvertDTO2BO(authUserDTO);
            AuthUserBO authUserDomainServiceUserInfo = authUserDomainService.getUserInfo(authUserBO);
            AuthUserDTO userInfo = AuthUserDTOConverter.INSTANCE.ConvertBO2DTO(authUserDomainServiceUserInfo);
            return Result.ok(userInfo);
        } catch (Exception e) {
            log.error("UserController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新用户信息失败");
        }
    }

    /**
     * 批量获取用户信息
     */
    @RequestMapping("listByIds")
    public Result<List<AuthUserDTO>> listUserInfoByIds(@RequestBody List<String> userNameList) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.listUserInfoByIds.dto:{}", JSON.toJSONString(userNameList));
            }
            Assert.notNull(userNameList, "id集合不能为空");
            List<AuthUserBO> userInfos = authUserDomainService.listUserInfoByIds(userNameList);
            return Result.ok(AuthUserDTOConverter.INSTANCE.ConvertBOList2DTOList(userInfos));
        } catch (Exception e) {
            log.error("UserController.listUserInfoByIds.error:{}", e.getMessage(), e);
            return Result.fail("批量获取用户信息失败");
        }
    }
    /**
     * 更新用户信息
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.update.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Assert.notNull(authUserDTO.getUserName(), "用户名不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.ConvertDTO2BO(authUserDTO);
            return Result.ok(authUserDomainService.update(authUserBO));
        } catch (Exception e) {
            log.error("UserController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新用户信息失败");
        }
    }

    /**
     * 删除用户
     */
    @PostMapping("delete")
    public Result delete(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.delete.dto:{}", JSON.toJSONString(authUserDTO));
            }
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.ConvertDTO2BO(authUserDTO);
            return Result.ok(authUserDomainService.delete(authUserBO));
        } catch (Exception e) {
            log.error("UserController.update.error:{}", e.getMessage(), e);
            return Result.fail("删除用户信息失败");
        }
    }

    /**
     * 用户启用/禁用
     */
    @PostMapping("changeStatus")
    public Result<Boolean> changeStatus(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.changeStatus.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Assert.notNull(authUserDTO.getStatus(), "用户状态不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.ConvertDTO2BO(authUserDTO);
            return Result.ok(authUserDomainService.update(authUserBO));
        } catch (Exception e) {
            log.error("UserController.changeStatus.error:{}", e.getMessage(), e);
            return Result.fail("启用/禁用用户信息失败");
        }
    }






    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    /*@RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }*/

    // TODO 前后端分离的登录接口，测试响应中会包含
    @RequestMapping("doLogin")
    public SaResult doLogin() {
        // 第1步，先登录上
        StpUtil.login(10001);
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return SaResult.data(tokenInfo);
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
}
