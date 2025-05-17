package com.yf.exam.modules.exam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 考试部门数据传输类
* </p>
*
*/
@Data
@ApiModel(value="考试部门", description="考试部门")
public class ExamDepartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "ID", required=true)
    private String id;

    /**
     * 考试ID
     */
    @ApiModelProperty(value = "考试ID", required=true)
    private String examId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", required=true)
    private String departId;
}