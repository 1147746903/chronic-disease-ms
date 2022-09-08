package com.comvee.cdms.dialogue.vo;

import com.comvee.cdms.common.wrapper.PageResult;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
public class ListDialogueLatestDoctorVO {

    private Long timestamp;
    private PageResult<DialogueLatestDoctorVO> pageResult;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public PageResult<DialogueLatestDoctorVO> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<DialogueLatestDoctorVO> pageResult) {
        this.pageResult = pageResult;
    }
}
