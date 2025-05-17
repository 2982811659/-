package com.yf.exam.modules.paper.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="查找试卷题目详情请求类", description="查找试卷题目详情请求类")
public class PaperAnswerDTO extends PaperQuQueryDTO {

    /**
     * 客观题回答列表（如选择题的选项ID集合）
     */
    @ApiModelProperty(value = "回答列表", required=true)
    private List<String> answers;

    /**
     * 主观题答案内容（如简答题、论述题的作答文本）
     */
    @ApiModelProperty(value = "主观答案", required=true)
    private String answer;
}