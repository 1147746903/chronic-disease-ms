package com.comvee.cdms.dialogue.vo;

import com.comvee.cdms.common.wrapper.PageResult;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
public class ListDialogueLatestVO<E> {

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
