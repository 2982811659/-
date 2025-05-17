package com.yf.exam.modules.paper.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 试卷请求类
* </p>
*
*/
@Data
@ApiModel(value="试卷", description="试卷")
public class PaperListReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required=true)
    private String userId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", required=true)
    private String departId;

    /**
     * 考试ID（规则ID）
     */
    @ApiModelProperty(value = "规则ID", required=true)
    private String examId;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", required=true)
    private String realName;

    /**
     * 试卷状态
     */
    @ApiModelProperty(value = "试卷状态", required=true)
    private Integer state;
}
