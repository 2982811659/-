package com.yf.exam.modules.exam.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.core.enums.OpenType;
import com.yf.exam.core.exception.ServiceException;
import com.yf.exam.core.utils.BeanMapper;
import com.yf.exam.modules.exam.dto.ExamDTO;
import com.yf.exam.modules.exam.dto.ExamRepoDTO;
import com.yf.exam.modules.exam.dto.ext.ExamRepoExtDTO;
import com.yf.exam.modules.exam.dto.request.ExamSaveReqDTO;
import com.yf.exam.modules.exam.dto.response.ExamOnlineRespDTO;
import com.yf.exam.modules.exam.dto.response.ExamReviewRespDTO;
import com.yf.exam.modules.exam.entity.Exam;
import com.yf.exam.modules.exam.mapper.ExamMapper;
import com.yf.exam.modules.exam.service.ExamDepartService;
import com.yf.exam.modules.exam.service.ExamRepoService;
import com.yf.exam.modules.exam.service.ExamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 考试业务实现类
* </p>
*/
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    // 注入题库与部门相关服务
    @Autowired
    private ExamRepoService examRepoService;

    @Autowired
    private ExamDepartService examDepartService;

    /**
     * 保存考试信息，包括新增和更新
     */
    @Override
    public void save(ExamSaveReqDTO reqDTO) {

        // 1. 检查是否有ID，如果没有则生成一个新的ID
        String id = reqDTO.getId();
        if(StringUtils.isBlank(id)){
            id = IdWorker.getIdStr(); // MyBatis Plus 提供的ID生成器
        }

        // 2. 创建实体对象
        Exam entity = new Exam();

        // 3. 计算考试总分
        this.calcScore(reqDTO);

        // 4. 拷贝参数到实体对象
        BeanMapper.copy(reqDTO, entity);
        entity.setId(id);

        // 5. 设置考试状态
        // 如果没有时间限制且状态为2（可能代表“已发布”），则重置为0（未发布）
        if (reqDTO.getTimeLimit()!=null
                && !reqDTO.getTimeLimit()
                && reqDTO.getState()!=null
                && reqDTO.getState() == 2) {
            entity.setState(0);
        } else {
            entity.setState(reqDTO.getState());
        }

        // 6. 保存题库与试卷的关联关系，防止题库重复
        try {
            examRepoService.saveAll(id, reqDTO.getRepoList());
        }catch (DuplicateKeyException e){
            throw new ServiceException(1, "不能选择重复的题库！");
        }

        // 7. 如果是指定部门开放，保存考试与部门的关联关系
        if(OpenType.DEPT_OPEN.equals(reqDTO.getOpenType())){
            examDepartService.saveAll(id, reqDTO.getDepartIds());
        }

        // 8. 保存或更新考试实体
        this.saveOrUpdate(entity);

    }

    /**
     * 查询考试详细信息（用于编辑或详情页）
     */
    @Override
    public ExamSaveReqDTO findDetail(String id) {
        ExamSaveReqDTO respDTO = new ExamSaveReqDTO();
        // 1. 根据ID查询考试实体
        Exam exam = this.getById(id);
        // 2. 拷贝属性到返回DTO
        BeanMapper.copy(exam, respDTO);

        // 3. 查询考试关联的部门ID
        List<String> departIds = examDepartService.listByExam(id);
        respDTO.setDepartIds(departIds);

        // 4. 查询考试关联的题库信息
        List<ExamRepoExtDTO> repos = examRepoService.listByExam(id);
        respDTO.setRepoList(repos);

        return respDTO;
    }

    /**
     * 根据ID获取考试基础信息
     */
    @Override
    public ExamDTO findById(String id) {
        ExamDTO respDTO = new ExamDTO();
        Exam exam = this.getById(id);
        BeanMapper.copy(exam, respDTO);
        return respDTO;
    }

    /**
     * 分页查询考试列表
     */
    @Override
    public IPage<ExamDTO> paging(PagingReqDTO<ExamDTO> reqDTO) {
        // 创建分页对象
        Page page = new Page(reqDTO.getCurrent(), reqDTO.getSize());
        // 查询并返回分页数据
        IPage<ExamDTO> pageData = baseMapper.paging(page, reqDTO.getParams());
        return pageData;
    }

    /**
     * 分页查询在线考试（用户可参与的考试）
     */
    @Override
    public IPage<ExamOnlineRespDTO> onlinePaging(PagingReqDTO<ExamDTO> reqDTO) {
        // 创建分页对象
        Page page = new Page(reqDTO.getCurrent(), reqDTO.getSize());
        // 查询并返回数据
        IPage<ExamOnlineRespDTO> pageData = baseMapper.online(page, reqDTO.getParams());
        return pageData;
    }

    /**
     * 分页查询已提交待审核考试
     */
    @Override
    public IPage<ExamReviewRespDTO> reviewPaging(PagingReqDTO<ExamDTO> reqDTO) {
        // 创建分页对象
        Page page = new Page(reqDTO.getCurrent(), reqDTO.getSize());
        // 查询并返回数据
        IPage<ExamReviewRespDTO> pageData = baseMapper.reviewPaging(page, reqDTO.getParams());
        return pageData;
    }

    /**
     * 计算考试总分数，将结果写入请求DTO
     * @param reqDTO
     */
    private void calcScore(ExamSaveReqDTO reqDTO){

        int objScore = 0; // 客观题总分

        // 获取题库信息
        List<ExamRepoExtDTO> repoList = reqDTO.getRepoList();

        // 遍历每个题库，累加各题型分数
        for(ExamRepoDTO item: repoList){
            // 单选题
            if(item.getRadioCount()!=null
                    && item.getRadioCount()>0
                    && item.getRadioScore()!=null
                    && item.getRadioScore()>0){
                objScore+=item.getRadioCount()*item.getRadioScore();
            }
            // 多选题
            if(item.getMultiCount()!=null
                    && item.getMultiCount()>0
                    && item.getMultiScore()!=null
                    && item.getMultiScore()>0){
                objScore+=item.getMultiCount()*item.getMultiScore();
            }
            // 判断题
            if(item.getJudgeCount()!=null
                    && item.getJudgeCount()>0
                    && item.getJudgeScore()!=null
                    && item.getJudgeScore()>0){
                objScore+=item.getJudgeCount()*item.getJudgeScore();
            }
        }

        // 设置总分
        reqDTO.setTotalScore(objScore);
    }

}