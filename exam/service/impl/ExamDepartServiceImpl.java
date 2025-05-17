package com.yf.exam.modules.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.exam.core.exception.ServiceException;
import com.yf.exam.modules.exam.entity.ExamDepart;
import com.yf.exam.modules.exam.mapper.ExamDepartMapper;
import com.yf.exam.modules.exam.service.ExamDepartService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 考试部门业务实现类
* </p>
*
*/
@Service
public class ExamDepartServiceImpl extends ServiceImpl<ExamDepartMapper, ExamDepart> implements ExamDepartService {

    /**
     * 批量保存考试与部门的关联关系。
     * 先删除本场考试的旧关联，再保存新关联。
     * @param examId 考试ID
     * @param departs 部门ID列表
     */
    @Override
    public void saveAll(String examId, List<String> departs) {

        // 1. 先删除本考试原有的全部部门关联
        QueryWrapper<ExamDepart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamDepart::getExamId, examId);
        this.remove(wrapper);

        // 2. 新部门列表不能为空，否则抛出异常
        if(CollectionUtils.isEmpty(departs)){
            throw new ServiceException(1, "请至少选择选择一个部门！！");
        }

        // 3. 组装新的关联对象列表
        List<ExamDepart> list = new ArrayList<>();
        for(String id: departs){
            ExamDepart depart = new ExamDepart();
            depart.setDepartId(id);   // 部门ID
            depart.setExamId(examId); // 考试ID
            list.add(depart);
        }

        // 4. 批量保存到数据库
        this.saveBatch(list);
    }

    /**
     * 查询某场考试关联的所有部门ID
     * @param examId 考试ID
     * @return 部门ID列表
     */
    @Override
    public List<String> listByExam(String examId) {
        // 查找所有 examId 对应的 ExamDepart 数据
        QueryWrapper<ExamDepart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ExamDepart::getExamId, examId);
        List<ExamDepart> list = this.list(wrapper);

        // 提取部门ID
        List<String> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(ExamDepart item: list){
                ids.add(item.getDepartId());
            }
        }

        return ids;
    }
}