package com.yf.exam.modules.sys.depart.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 部门排序请求类
* </p>
*
*/
@Data
@ApiModel(value="部门排序请求类", description="部门排序请求类")
public class DepartSortReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    private String id;

    @ApiModelProperty(value = "排序，0下降，1上升")
    private Integer sort;
}
