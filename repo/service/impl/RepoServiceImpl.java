package com.yf.exam.modules.repo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.core.utils.BeanMapper;
import com.yf.exam.modules.repo.dto.RepoDTO;
import com.yf.exam.modules.repo.dto.request.RepoReqDTO;
import com.yf.exam.modules.repo.dto.response.RepoRespDTO;
import com.yf.exam.modules.repo.entity.Repo;
import com.yf.exam.modules.repo.mapper.RepoMapper;
import com.yf.exam.modules.repo.service.RepoService;
import org.springframework.stereotype.Service;

/**
* <p>
* 语言设置 服务实现类
* </p>
*
*/
@Service
public class RepoServiceImpl extends ServiceImpl<RepoMapper, Repo> implements RepoService {

    @Override
    public IPage<RepoRespDTO> paging(PagingReqDTO<RepoReqDTO> reqDTO) {
        // 分页查询题库数据
        return baseMapper.paging(reqDTO.toPage(), reqDTO.getParams());
    }

    @Override
    public void save(RepoDTO reqDTO) {
        // 复制参数到实体
        Repo entity = new Repo();
        BeanMapper.copy(reqDTO, entity);
        // 保存或更新题库
        this.saveOrUpdate(entity);
    }
}