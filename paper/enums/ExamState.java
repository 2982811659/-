package com.yf.exam.modules.paper.enums;


/**
 * 考试状态
 */
public interface ExamState {


    /**
     * 考试中
     */
    Integer ENABLE = 0;

    /**
     * 待阅卷
     */
    Integer DISABLED = 1;

    /**
     * 已完成
     */
    Integer READY_START = 2;

    /**
     * 已结束
     */
    Integer OVERDUE = 3;

    
}
