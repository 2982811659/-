package com.yf.exam.modules.paper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yf.exam.core.api.ApiRest;
import com.yf.exam.core.api.controller.BaseController;
import com.yf.exam.core.api.dto.BaseIdReqDTO;
import com.yf.exam.core.api.dto.BaseIdRespDTO;
import com.yf.exam.core.api.dto.BaseIdsReqDTO;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.core.utils.BeanMapper;
import com.yf.exam.modules.paper.dto.PaperDTO;
import com.yf.exam.modules.paper.dto.ext.PaperQuDetailDTO;
import com.yf.exam.modules.paper.dto.request.PaperAnswerDTO;
import com.yf.exam.modules.paper.dto.request.PaperCreateReqDTO;
import com.yf.exam.modules.paper.dto.request.PaperListReqDTO;
import com.yf.exam.modules.paper.dto.request.PaperQuQueryDTO;
import com.yf.exam.modules.paper.dto.response.ExamDetailRespDTO;
import com.yf.exam.modules.paper.dto.response.ExamResultRespDTO;
import com.yf.exam.modules.paper.dto.response.PaperListRespDTO;
import com.yf.exam.modules.paper.entity.Paper;
import com.yf.exam.modules.paper.service.PaperService;
import com.yf.exam.modules.user.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 试卷控制器
* </p>
*
*/
@Api(tags={"试卷"})
@RestController
@RequestMapping("/exam/api/paper/paper")
public class PaperController extends BaseController {

    @Autowired
    private PaperService baseService;

    /**
     * 分页查找试卷
     * @param reqDTO 分页查询参数
     * @return 分页试卷列表
     */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public ApiRest<IPage<PaperListRespDTO>> paging(@RequestBody PagingReqDTO<PaperListReqDTO> reqDTO) {
        IPage<PaperListRespDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

    /**
     * 创建试卷
     * @param reqDTO 创建参数
     * @return 新试卷ID
     */
    @ApiOperation(value = "创建试卷")
    @RequestMapping(value = "/create-paper", method = { RequestMethod.POST})
    public ApiRest<BaseIdRespDTO> save(@RequestBody PaperCreateReqDTO reqDTO) {
        String paperId = baseService.createPaper(UserUtils.getUserId(), reqDTO.getExamId());
        return super.success(new BaseIdRespDTO(paperId));
    }

    /**
     * 试卷详情
     * @param reqDTO 试卷ID
     * @return 试卷详情
     */
    @ApiOperation(value = "试卷详情")
    @RequestMapping(value = "/paper-detail", method = { RequestMethod.POST})
    public ApiRest<ExamDetailRespDTO> paperDetail(@RequestBody BaseIdReqDTO reqDTO) {
        ExamDetailRespDTO respDTO = baseService.paperDetail(reqDTO.getId());
        return super.success(respDTO);
    }

    /**
     * 试题详情
     * @param reqDTO 试卷ID和试题ID
     * @return 试题详情
     */
    @ApiOperation(value = "试题详情")
    @RequestMapping(value = "/qu-detail", method = { RequestMethod.POST})
    public ApiRest<PaperQuDetailDTO> quDetail(@RequestBody PaperQuQueryDTO reqDTO) {
        PaperQuDetailDTO respDTO = baseService.findQuDetail(reqDTO.getPaperId(), reqDTO.getQuId());
        return super.success(respDTO);
    }

    /**
     * 填充答案
     * @param reqDTO 答案参数
     * @return 操作结果
     */
    @ApiOperation(value = "填充答案")
    @RequestMapping(value = "/fill-answer", method = { RequestMethod.POST})
    public ApiRest<PaperQuDetailDTO> fillAnswer(@RequestBody PaperAnswerDTO reqDTO) {
        baseService.fillAnswer(reqDTO);
        return super.success();
    }

    /**
     * 交卷操作
     * @param reqDTO 试卷ID
     * @return 操作结果
     */
    @ApiOperation(value = "交卷操作")
    @RequestMapping(value = "/hand-exam", method = { RequestMethod.POST})
    public ApiRest<PaperQuDetailDTO> handleExam(@RequestBody BaseIdReqDTO reqDTO) {
        baseService.handExam(reqDTO.getId());
        return super.success();
    }

    /**
     * 试卷成绩详情
     * @param reqDTO 试卷ID
     * @return 试卷成绩详情
     */
    @ApiOperation(value = "试卷详情")
    @RequestMapping(value = "/paper-result", method = { RequestMethod.POST})
    public ApiRest<ExamResultRespDTO> paperResult(@RequestBody BaseIdReqDTO reqDTO) {
        ExamResultRespDTO respDTO = baseService.paperResult(reqDTO.getId());
        return super.success(respDTO);
    }

    /**
     * 检测用户有没有中断的考试
     * @return 进行中试卷
     */
    @ApiOperation(value = "检测进行中的考试")
    @RequestMapping(value = "/check-process", method = { RequestMethod.POST})
    public ApiRest<PaperDTO> checkProcess() {
        PaperDTO dto = baseService.checkProcess(UserUtils.getUserId());
        return super.success(dto);
    }
}