package com.yf.exam.modules.qu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.exam.modules.qu.dto.QuDTO;
import com.yf.exam.modules.qu.dto.export.QuExportDTO;
import com.yf.exam.modules.qu.dto.request.QuQueryReqDTO;
import com.yf.exam.modules.qu.entity.Qu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 问题题目Mapper
* </p>
*/
public interface QuMapper extends BaseMapper<Qu> {

    /**
     * 随机抽取题库的数据
     * @param repoId    题库ID
     * @param quType    题目类型
     * @param excludes  要排除的ID列表
     * @param size      抽取数量
     * @return 题目列表
     */
    List<Qu> listByRandom(@Param("repoId") String repoId,
                          @Param("quType") Integer quType,
                          @Param("excludes") List<String> excludes,
                          @Param("size") Integer size);

    /**
     * 查找导出列表
     * @param query 导出查询条件
     * @return 导出题目列表
     */
    List<QuExportDTO> listForExport(@Param("query") QuQueryReqDTO query);

    /**
     * 分页查找
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页结果
     */
    IPage<QuDTO> paging(Page page, @Param("query") QuQueryReqDTO query);
}