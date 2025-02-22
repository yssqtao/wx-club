package com.yssq.subject.application.controller;

import com.yssq.subject.infra.rpc.UserInfo;
import com.yssq.subject.infra.basic.service.SubjectEsService;
import com.yssq.subject.infra.rpc.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("subject/category/")
@Slf4j
public class TestFeignController {

    @Resource
    private UserRpc userRpc;

    @Resource
    private SubjectEsService subjectEsService;

    @GetMapping("testFeign")
    public void testFeign() {
        //TODO 前端请求，第二处（如果是微服务之间的调用，此时调用方法，下一步需要经过Feign拦截器）
        UserInfo userInfo = userRpc.getUserInfo("10001");
        log.info("testFeign.userInfo:{}", userInfo);
    }

//    @GetMapping("testCreateIndex")
//    public void testCreateIndex(){
//        subjectEsService.createIndex();
//    }
}
