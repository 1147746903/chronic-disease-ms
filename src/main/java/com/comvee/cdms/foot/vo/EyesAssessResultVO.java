package com.comvee.cdms.foot.vo;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/6/13
 */
public class EyesAssessResultVO {

    /**
     * 随访建议
     */
    private String followAdvice;
    /**
     * 护理建议
     */
    private List<NurseAdviceVO> adviceList;

    /**
     * 糖网分级
     */
    private Integer eyesLevel;

    public Integer getEyesLevel() {
        return eyesLevel;
    }

    public void setEyesLevel(Integer eyesLevel) {
        this.eyesLevel = eyesLevel;
    }

    public String getFollowAdvice() {
        return followAdvice;
    }

    public void setFollowAdvice(String followAdvice) {
        this.followAdvice = followAdvice;
    }

    public List<NurseAdviceVO> getAdviceList() {
        return adviceList;
    }

    public void setAdviceList(List<NurseAdviceVO> adviceList) {
        this.adviceList = adviceList;
    }
}
