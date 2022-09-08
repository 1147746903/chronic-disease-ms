package com.comvee.cdms.foot.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/6/13
 */
public class NephropathyAssessResultVO {

    /**
     * CKD的GFR分期
     */
    private Integer egfrStages;
    /**
     * 白蛋白尿分期
     */
    private Integer uacrStages;
    /**
     * 肾损伤程度
     */
    private String renalInjuryDegree;
    /**
     * 随访及转诊建议
     */
    private String referralAdvice;

    /**
     * 风险程度
     */
    private String riskDegree;

    /**
     * 护理建议
     */
    private List<NurseAdviceVO> adviceList = new ArrayList<>();

    public Integer getEgfrStages() {
        return egfrStages;
    }

    public void setEgfrStages(Integer egfrStages) {
        this.egfrStages = egfrStages;
    }

    public Integer getUacrStages() {
        return uacrStages;
    }

    public void setUacrStages(Integer uacrStages) {
        this.uacrStages = uacrStages;
    }

    public String getRenalInjuryDegree() {
        return renalInjuryDegree;
    }

    public void setRenalInjuryDegree(String renalInjuryDegree) {
        this.renalInjuryDegree = renalInjuryDegree;
    }

    public String getReferralAdvice() {
        return referralAdvice;
    }

    public void setReferralAdvice(String referralAdvice) {
        this.referralAdvice = referralAdvice;
    }

    public List<NurseAdviceVO> getAdviceList() {
        return adviceList;
    }

    public void setAdviceList(List<NurseAdviceVO> adviceList) {
        this.adviceList = adviceList;
    }

    public String getRiskDegree() {
        return riskDegree;
    }

    public void setRiskDegree(String riskDegree) {
        this.riskDegree = riskDegree;
    }
}
