package com.yf.exam.modules.paper.dto.request;

import com.yf.exam.core.api.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="试卷创建请求类", description="试卷创建请求类")
public class PaperCreateReqDTO extends BaseDTO {

    /**
     * 用户ID，服务端自动填充，前端无需传递
     */
    @JsonIgnore
    private String userId;

    /**
     * 考试ID，关联要创建试卷的考试
     */
    @ApiModelProperty(value = "考试ID", required=true)
    private String examId;
}