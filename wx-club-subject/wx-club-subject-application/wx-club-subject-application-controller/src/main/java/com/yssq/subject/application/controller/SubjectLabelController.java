package com.yssq.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.yssq.subject.application.convert.SubjectCategoryDTOConverter;
import com.yssq.subject.application.convert.SubjectLabelDTOConverter;
import com.yssq.subject.application.dto.SubjectCategoryDTO;
import com.yssq.subject.application.dto.SubjectLabelDTO;
import com.yssq.subject.common.entity.Result;
import com.yssq.subject.domain.bo.SubjectCategoryBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.domain.service.SubjectCategoryDomainService;
import com.yssq.subject.domain.service.SubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject/label/")
@Slf4j
public class SubjectLabelController {
    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;

    /**
     * 新增标签
     */
    @PostMapping("add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.add.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Assert.notNull(subjectLabelDTO.getLabelName(), "标签名称不能为空");
            Assert.notNull(subjectLabelDTO.getCategoryId(), "分类id不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.ConvertDTO2BO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增标签失败");
        }
    }

    /**
     * 根据id更新标签
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.add.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Assert.notNull(subjectLabelDTO.getId(), "标签的id不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.ConvertDTO2BO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新标签失败");
        }
    }

    /**
     * 根据id删除标签
     */
    @PostMapping("delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        try {
            subjectLabelDomainService.deleteById(id);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectLabelController.delete.error:{}", e.getMessage(), e);
            return Result.fail("更新标签失败");
        }
    }

    /**
     * 根据分类id查询标签，返回的是list
     */
    @PostMapping("queryLabelByCategoryId")
    public Result<List<SubjectLabelDTO>> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryLabelByCategoryId.dto:{}",
                        JSON.toJSONString(subjectLabelDTO));
            }
            Assert.notNull(subjectLabelDTO.getCategoryId(), "标签的分类ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.ConvertDTO2BO(subjectLabelDTO);
            List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> subjectLabelDTOList = SubjectLabelDTOConverter.INSTANCE.ConvertBOList2DTOList(subjectLabelBOList);
            return Result.ok(subjectLabelDTOList);
        } catch (Exception e) {
            log.error("SubjectLabelController.query.error:{}", e.getMessage(), e);
            return Result.fail("查询标签失败");
        }
    }
}
