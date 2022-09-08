package com.comvee.cdms.doctor.vo;

public class NoticeVO {
    /**
     * 是否存在未读公告
     */
    private Boolean hasNewNotice;
    private int unReadCount;

    public Boolean getHasNewNotice() {
        return hasNewNotice;
    }

    public void setHasNewNotice(Boolean hasNewNotice) {
        this.hasNewNotice = hasNewNotice;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public int getUnReadCount() {
        return unReadCount;
    }
}
