package com.yf.exam.modules.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.exam.core.exception.ServiceException;
import com.yf.exam.core.utils.BeanMapper;
import com.yf.exam.modules.exam.dto.ext.ExamRepoExtDTO;
import com.yf.exam.modules.exam.entity.ExamRepo;
import com.yf.exam.modules.exam.mapper.ExamRepoMapper;
import com.yf.exam.modules.exam.service.ExamRepoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* <p>
* 考试题库业务实现类
* </p>
*
*/
@Service
public class ExamRepoServiceImpl extends ServiceImpl<ExamRepoMapper, ExamRepo> implements ExamRepoService {

    /**
     * 批量保存某场考试对应的题库信息
     * 1. 先删除本场考试原有的题库关联
     * 2. 再批量插入新的题库关联
     *
     * @param examId 考试ID
     * @param list   题库列表（包含题库及其题型数量等扩展信息）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAll(String examId, List<ExamRepoExtDTO> list) {

        // 1. 先删除本场考试已有关联的题库信息
        QueryWrapper<ExamRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamRepo::getExamId, examId);
        this.remove(wrapper);

        // 2. 新的题库列表不能为空，否则抛出异常
        if(CollectionUtils.isEmpty(list)){
            throw new ServiceException(1, "必须选择题库！");
        }

        // 3. 将扩展DTO列表转为实体列表
        List<ExamRepo> repos = BeanMapper.mapList(list, ExamRepo.class);

        // 4. 补充每个题库关联的考试ID和唯一主键ID
        for(ExamRepo item: repos){
            item.setExamId(examId); // 绑定考试ID
            item.setId(IdWorker.getIdStr()); // 生成唯一主键
        }

        // 5. 批量保存到数据库
        this.saveBatch(repos);
    }

    /**
     * 根据考试ID查询其关联的题库扩展信息
     * @param examId 考试ID
     * @return 题库扩展信息列表
     */
    @Override
    public List<ExamRepoExtDTO> listByExam(String examId) {
        // 直接调用Mapper层自定义方法查询
        return baseMapper.listByExam(examId);
    }

    /**
     * 清空某场考试的题库关联（删除）
     * @param examId 考试ID
     */
    @Override
    public void clear(String examId) {
        // 删除指定考试ID下的全部题库关联
        QueryWrapper<ExamRepo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamRepo::getExamId, examId);
        this.remove(wrapper);
    }

}