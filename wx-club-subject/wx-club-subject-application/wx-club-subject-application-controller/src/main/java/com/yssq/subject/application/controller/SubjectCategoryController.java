package com.yssq.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.yssq.subject.application.convert.SubjectCategoryDTOConverter;
import com.yssq.subject.application.dto.SubjectCategoryDTO;
import com.yssq.subject.common.entity.Result;
import com.yssq.subject.common.util.LoginUtil;
import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.domain.service.SubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject/category/")
@Slf4j
//TODO 出现问题1：当引入log4j2依赖后，会与spring本身的日志加载功能冲突，此时使用插件在starter pom文件排除spring-boot-starter-logging
public class SubjectCategoryController {
    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 新增分类
     */
    @PostMapping("add")
    //TODO 出现问题2：不要忘记@RequestBody 注解
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            //TODO 添加判断，避免高并发下，JSON序列化导致性能下降
            if (log.isInfoEnabled()) {
                //TODO JSON序列化：记录对象的详细结构，如果不加，就直接输出subjectCategoryDTO
                log.info("SubjectCategoryController.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            String loginId = LoginUtil.getLoginId();
            log.info("当前登录id为：{}",loginId);
            Assert.notNull(subjectCategoryDTO.getCategoryName(), "分类名称不能为空");
            Assert.notNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.ConvertDTO2BO(subjectCategoryDTO);

            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 更新分类
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.ConvertDTO2BO(subjectCategoryDTO);

            Boolean result = subjectCategoryDomainService.ubpdate(subjectCategoryBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新分类失败");
        }
    }

    /**
     * 根据id删除分类
     * TODO 请求需要添加id，如http://localhost:3000/subject/category/delete/38
     */
    @PostMapping("delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        try {
            subjectCategoryDomainService.delete(id);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除分类失败");
        }
    }

    /**
     * 根据分类类型查询，subjectCategoryDTO.getCategoryType()
     * TODO 由于是根据分类类型查询，可能得到多条数据，因此需要使用List来封装
     */
    @PostMapping("queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            Assert.notNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.ConvertDTO2BO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.ConvertBOList2DTOList(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询分类失败");
        }
    }
}
