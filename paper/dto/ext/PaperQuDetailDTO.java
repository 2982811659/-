package com.yf.exam.modules.paper.dto.ext;

import com.yf.exam.modules.paper.dto.PaperQuDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* <p>
* 试卷考题请求类
* </p>
*
*/
@Data
@ApiModel(value="试卷题目详情类", description="试卷题目详情类")
public class PaperQuDetailDTO extends PaperQuDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 题目图片（如有图片题目，存储图片URL）
     */
    @ApiModelProperty(value = "图片", required=true)
    private String image;

    /**
     * 题目内容（题干文本）
     */
    @ApiModelProperty(value = "题目内容", required=true)
    private String content;

    /**
     * 答案内容列表（每个选项的内容、图片等）
     */
    @ApiModelProperty(value = "答案内容", required=true)
    private List<PaperQuAnswerExtDTO> answerList;
}