package com.comvee.cdms.app.doctorapp.model.app;

import com.comvee.cdms.common.wrapper.PageResult;

public class ListDialogueLatestForDoctorModel<E> {
    private Long timestamp;
    private PageResult<E> pageResult;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public PageResult<E> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<E> pageResult) {
        this.pageResult = pageResult;
    }
}
