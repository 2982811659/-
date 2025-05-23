package com.yf.exam.modules.exam.dto.request;

import com.yf.exam.modules.exam.dto.ExamDTO;
import com.yf.exam.modules.exam.dto.ext.ExamRepoExtDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* <p>
* 考试保存请求类
* </p>
*
*/
@Data
@ApiModel(value="考试保存请求类", description="考试保存请求类")
public class ExamSaveReqDTO extends ExamDTO {

    private static final long serialVersionUID = 1L;

    /** 
     * 题库列表（包含题库ID、每种题型的数量与分值、题库题目总量等信息）
     */
    @ApiModelProperty(value = "题库列表", required=true)
    private List<ExamRepoExtDTO> repoList;

    /**
     * 考试开放的部门ID列表
     */
    @ApiModelProperty(value = "考试部门列表", required=true)
    private List<String> departIds;

}
