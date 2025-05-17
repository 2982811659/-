package com.yf.exam.modules.sys.depart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.exam.core.api.dto.PagingReqDTO;
import com.yf.exam.modules.sys.depart.dto.SysDepartDTO;
import com.yf.exam.modules.sys.depart.dto.response.SysDepartTreeDTO;
import com.yf.exam.modules.sys.depart.entity.SysDepart;

import java.util.List;

/**
* <p>
* 部门信息业务类
* </p>
*
*/
public interface SysDepartService extends IService<SysDepart> {

    /**
     * 保存
     * @param reqDTO 部门信息DTO
     */
    void save(SysDepartDTO reqDTO);

    /**
    * 分页查询数据
    * @param reqDTO 分页请求参数
    * @return 分页结果
    */
    IPage<SysDepartTreeDTO> paging(PagingReqDTO<SysDepartDTO> reqDTO);

    /**
     * 查找部门树结构
     * @return 树形结构列表
     */
    List<SysDepartTreeDTO> findTree();

    /**
     * 查找部门树
     * @param ids 需要查找的部门ID集合
     * @return 树形结构列表
     */
    List<SysDepartTreeDTO> findTree(List<String> ids);

    /**
     * 排序
     * @param id   部门ID
     * @param sort 排序方向（0=上移, 1=下移）
     */
    void sort(String id, Integer sort);

    /**
     * 获取某个部门ID下的所有子部门ID（包含自身）
     * @param id 部门ID
     * @return 所有子部门ID列表
     */
    List<String> listAllSubIds(String id);
}