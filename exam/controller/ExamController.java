package com.yf.exam.modules.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yf.exam.core.api.ApiRest;
import com.yf.exam.core.api.controller.BaseController;
import com.yf.exam.core.api.dto.BaseIdReqDTO;
import com.yf.exam.core.api.dto.BaseIdsReqDTO;
import com.yf.exam.core.api.dto.BaseStateReqDTO;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.modules.exam.dto.ExamDTO;
import com.yf.exam.modules.exam.dto.request.ExamSaveReqDTO;
import com.yf.exam.modules.exam.dto.response.ExamOnlineRespDTO;
import com.yf.exam.modules.exam.dto.response.ExamReviewRespDTO;
import com.yf.exam.modules.exam.entity.Exam;
import com.yf.exam.modules.exam.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
* <p>
* 考试控制器
* </p>
*/
@Api(tags={"考试"})
@RestController
@RequestMapping("/exam/api/exam/exam")
public class ExamController extends BaseController {

    @Autowired
    private ExamService baseService;

    /**
    * 添加或修改考试
    * 需要超级管理员角色
    */
    @RequiresRoles("sa")
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest save(@RequestBody ExamSaveReqDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
    * 批量删除考试
    * 需要超级管理员角色
    */
    @RequiresRoles("sa")
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = { RequestMethod.POST})
    public ApiRest edit(@RequestBody BaseIdsReqDTO reqDTO) {
        baseService.removeByIds(reqDTO.getIds());
        return super.success();
    }

    /**
    * 查询考试详情（含题库、部门等扩展信息）
    */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/detail", method = { RequestMethod.POST})
    public ApiRest<ExamSaveReqDTO> find(@RequestBody BaseIdReqDTO reqDTO) {
        ExamSaveReqDTO dto = baseService.findDetail(reqDTO.getId());
        return super.success(dto);
    }

    /**
     * 批量修改考试状态（如发布、下架等）
     * 需要超级管理员角色
     */
    @RequiresRoles("sa")
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/state", method = { RequestMethod.POST})
    public ApiRest state(@RequestBody BaseStateReqDTO reqDTO) {
        QueryWrapper<Exam> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Exam::getId, reqDTO.getIds());
        Exam exam = new Exam();
        exam.setState(reqDTO.getState());
        exam.setUpdateTime(new Date());
        baseService.update(exam, wrapper);
        return super.success();
    }

    /**
     * 分页查询在线考试（考生视角，不需要管理员权限）
     */
    @ApiOperation(value = "考试视角")
    @RequestMapping(value = "/online-paging", method = { RequestMethod.POST})
    public ApiRest<IPage<ExamOnlineRespDTO>> myPaging(@RequestBody PagingReqDTO<ExamDTO> reqDTO) {
        IPage<ExamOnlineRespDTO> page = baseService.onlinePaging(reqDTO);
        return super.success(page);
    }

    /**
    * 分页查询考试（管理员视角）
    * 需要超级管理员角色
    */
    @RequiresRoles("sa")
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public ApiRest<IPage<ExamDTO>> paging(@RequestBody PagingReqDTO<ExamDTO> reqDTO) {
        IPage<ExamDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

    /**
     * 分页查询待阅试卷（教师/管理员批阅页面）
     * 需要超级管理员角色
     */
    @RequiresRoles("sa")
    @ApiOperation(value = "待阅试卷")
    @RequestMapping(value = "/review-paging", method = { RequestMethod.POST})
    public ApiRest<IPage<ExamReviewRespDTO>> reviewPaging(@RequestBody PagingReqDTO<ExamDTO> reqDTO) {
        IPage<ExamReviewRespDTO> page = baseService.reviewPaging(reqDTO);
        return super.success(page);
    }
}