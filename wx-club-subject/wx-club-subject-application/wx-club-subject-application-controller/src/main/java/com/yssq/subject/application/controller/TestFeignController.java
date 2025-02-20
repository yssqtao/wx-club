package com.yssq.subject.application.controller;

import com.yssq.subject.infra.entity.UserInfo;
import com.yssq.subject.infra.rpc.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 刷题分类controller
 */
@RestController
@RequestMapping("subject/category/")
@Slf4j
public class TestFeignController {

    @Resource
    private UserRpc userRpc;

    @GetMapping("testFeign")
    public void testFeign() {
        //TODO 前端请求，第二处
        UserInfo userInfo = userRpc.getUserInfo("10001");
        log.info("testFeign.userInfo:{}", userInfo);
    }

}
