package com.yf.exam.modules.paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.exam.modules.paper.dto.PaperDTO;
import com.yf.exam.modules.paper.dto.request.PaperListReqDTO;
import com.yf.exam.modules.paper.dto.response.PaperListRespDTO;
import com.yf.exam.modules.paper.entity.Paper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 试卷Mapper
* </p>
*
*/
public interface PaperMapper extends BaseMapper<Paper> {

    /**
     * 查找试卷分页
     * 
     * @param page  分页参数（MyBatis-Plus提供的Page对象）
     * @param query 查询条件（PaperListReqDTO）
     * @return 分页结果（IPage<PaperListRespDTO>）
     */
    IPage<PaperListRespDTO> paging(Page page, @Param("query") PaperListReqDTO query);

    /**
     * 试卷列表查询
     * 
     * @param query 查询条件（PaperDTO）
     * @return 试卷列表
     */
    List<PaperListRespDTO> list(@Param("query") PaperDTO query);
}