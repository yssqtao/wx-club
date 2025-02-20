package com.yssq.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.yssq.subject.application.convert.SubjectAnswerDTOConverter;
import com.yssq.subject.application.convert.SubjectInfoDTOConverter;
import com.yssq.subject.application.convert.SubjectLabelDTOConverter;
import com.yssq.subject.application.dto.SubjectInfoDTO;
import com.yssq.subject.application.dto.SubjectLabelDTO;
import com.yssq.subject.common.entity.PageResult;
import com.yssq.subject.common.entity.Result;
import com.yssq.subject.domain.bo.SubjectAnswerBO;
import com.yssq.subject.domain.bo.SubjectInfoBO;
import com.yssq.subject.domain.bo.SubjectLabelBO;
import com.yssq.subject.domain.service.SubjectInfoDomainService;
import com.yssq.subject.domain.service.SubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/subject/")
@Slf4j
public class SubjectController {
    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;

    /**
     * 新增题目
     */
    @PostMapping("add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO){
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Assert.notNull(subjectInfoDTO.getSubjectParse(), "题目的名称不能为空");
            Assert.notNull(subjectInfoDTO.getSubjectDifficult(), "题目的难度不能为空");
            Assert.notNull(subjectInfoDTO.getSubjectType(), "题目的类型不能为空");
            Assert.notNull(subjectInfoDTO.getSubjectScore(), "题目的分数不能为空");
            Assert.notNull(subjectInfoDTO.getCategoryIds(), "题目的分类ID不能为空");
            Assert.notNull(subjectInfoDTO.getLabelIds(), "题目的标签ID不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.ConvertDTO2BO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOList =
                    SubjectAnswerDTOConverter.INSTANCE.ConvertDTOList2BOList(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOList);
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok("新增题目成功");
        } catch (Exception e) {
            log.error("SubjectController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增题目失败");
        }
    }

    /**
     * 查询题目列表
     */
    @PostMapping("querySubjectList")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPage.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            /**
             * TODO 根据前端请求示例，此处需要在DTO中新增两个参数CategoryId和LabelId
             */
            Assert.notNull(subjectInfoDTO.getCategoryId(),"分类id不能为空");
            Assert.notNull(subjectInfoDTO.getLabelId(),"标签id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.ConvertDTO2BO(subjectInfoDTO);
            subjectInfoBO.setPageNo(subjectInfoDTO.getPageNo());
            subjectInfoBO.setPageSize(subjectInfoDTO.getPageSize());
            //TODO 分页查询第四步，构建查询方法
            PageResult<SubjectInfoBO> pageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("SubjectController.add.error:{}", e.getMessage(), e);
            return Result.fail("分页查询题目失败");
        }
    }

    /**
     * 查询题目信息
     */
    @PostMapping("querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Assert.notNull(subjectInfoDTO.getId(),"题目id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.ConvertDTO2BO(subjectInfoDTO);
            SubjectInfoBO subjectInfoBOResult = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            SubjectInfoDTO subjectInfoDTOResult = SubjectInfoDTOConverter.INSTANCE.ConvertBO2DTO(subjectInfoBOResult);
            return Result.ok(subjectInfoDTOResult);
        } catch (Exception e) {
            log.error("SubjectController.querySubjectInfo.error:{}", e.getMessage(), e);
            return Result.fail("查询题目详情失败");
        }
    }
}
