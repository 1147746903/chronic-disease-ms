package com.comvee.cdms.bloodmonitor.vo;

import java.util.List;

public class TodayTaskListVO {

    private String paramCode;
    private List<TodayTaskDetailListVO> memberList;

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public List<TodayTaskDetailListVO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<TodayTaskDetailListVO> memberList) {
        this.memberList = memberList;
    }
}
