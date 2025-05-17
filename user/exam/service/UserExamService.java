package com.yf.exam.modules.user.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.modules.user.exam.dto.request.UserExamReqDTO;
import com.yf.exam.modules.user.exam.dto.response.UserExamRespDTO;
import com.yf.exam.modules.user.exam.entity.UserExam;

/**
* <p>
* 考试记录业务类
* </p>
*
*/
public interface UserExamService extends IService<UserExam> {

    /**
    * 分页查询数据
    * @param reqDTO 分页请求参数
    * @return 分页结果
    */
    IPage<UserExamRespDTO> paging(PagingReqDTO<UserExamReqDTO> reqDTO);

    /**
     * 我的考试记录分页查询
     * @param reqDTO 分页请求参数
     * @return 分页结果
     */
    IPage<UserExamRespDTO> myPaging(PagingReqDTO<UserExamReqDTO> reqDTO);

    /**
     * 考试完成后加入成绩
     * @param userId 用户ID
     * @param examId 考试ID
     * @param score  得分
     * @param passed 是否通过
     */
    void joinResult(String userId, String examId, Integer score, boolean passed);
}