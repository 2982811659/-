package com.yf.exam.modules.paper.dto.response;

import com.yf.exam.modules.paper.dto.PaperDTO;
import com.yf.exam.modules.paper.dto.PaperQuDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Calendar;
import java.util.List;
@Data
@ApiModel(value="考试详情", description="考试详情")
public class ExamDetailRespDTO extends PaperDTO {

    /**
     * 单选题列表
     */
    @ApiModelProperty(value = "单选题列表", required=true)
    private List<PaperQuDTO> radioList;

    /**
     * 多选题列表
     */
    @ApiModelProperty(value = "多选题列表", required=true)
    private List<PaperQuDTO> multiList;

    /**
     * 判断题列表
     */
    @ApiModelProperty(value = "判断题", required=true)
    private List<PaperQuDTO> judgeList;

    /**
     * 获取剩余考试结束秒数
     * 以考试创建时间为基准，加上总时长，计算距离当前时刻的剩余秒数
     * @return 剩余秒数
     */
    @ApiModelProperty(value = "剩余结束秒数", required=true)
    public Long getLeftSeconds() {
        // 结束时间 = 创建时间 + 总时长
        Calendar cl = Calendar.getInstance();
        cl.setTime(this.getCreateTime());
        cl.add(Calendar.MINUTE, getTotalTime());

        return (cl.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }
}