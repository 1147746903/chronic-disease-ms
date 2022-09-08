package com.comvee.cdms.member.model;

/**
 * 
 * @author 李左河
 *
 */
public class MemberArchivesModel {
    /**
     * member_id
     */
    private String memberId;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * modify_dt
     */
    private String modifyDt;

    /**
     * 急性并发症
     * acute_complication
     */
    private String acuteComplication;

    /**
     * 糖尿病现状
     * diabetes_status
     */
    private String diabetesStatus;

    /**
     * 日常习惯
     * daily_habits
     */
    private String dailyHabits;

    /**
     * 糖尿病并发症
     * diabetes_complication
     */
    private String diabetesComplication;

    /**
     * 低血糖
     * hypoglycemia
     */
    private String hypoglycemia;

    /**
     * 自我血糖监测
     * smbg
     */
    private String smbg;

    /**
     * 当前情况
     * current_status
     */
    private String currentStatus;

    /**
     * 教育情况
     * education_situation
     */
    private String educationSituation;
    
    /** 基本信息 **/
    private String basic;

    /**
     * 患病后体征情况
     */
    private String signJson;
    /**
     * 过敏史
     */
    private String allergicHistory;

    /**
     * 患者目前血糖情况
     */
    private String treatmentJson;
    /**
     * 既往史
     */
    private String pastHistory;
    /**
     * 家族史
     */
    private String familyHistory;
    
    /**女性月经生育史*/
    private String womenHistory;
    
    /**问卷*/
    private String questionJson;
    
    private String pid;

    private String archivesJson;

    public String getPastHistory() {
		return pastHistory;
	}

	public void setPastHistory(String pastHistory) {
		this.pastHistory = pastHistory;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getAllergicHistory() {
		return allergicHistory;
	}

	public void setAllergicHistory(String allergicHistory) {
		this.allergicHistory = allergicHistory;
	}

	public String getSignJson() {
        return signJson;
    }

    public void setSignJson(String signJson) {
        this.signJson = signJson;
    }

    public String getTreatmentJson() {
        return treatmentJson;
    }

    public void setTreatmentJson(String treatmentJson) {
        this.treatmentJson = treatmentJson;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getAcuteComplication() {
        return acuteComplication;
    }

    public void setAcuteComplication(String acuteComplication) {
        this.acuteComplication = acuteComplication;
    }

    public String getDiabetesStatus() {
        return diabetesStatus;
    }

    public void setDiabetesStatus(String diabetesStatus) {
        this.diabetesStatus = diabetesStatus;
    }

    public String getDailyHabits() {
        return dailyHabits;
    }

    public void setDailyHabits(String dailyHabits) {
        this.dailyHabits = dailyHabits;
    }

    public String getDiabetesComplication() {
        return diabetesComplication;
    }

    public void setDiabetesComplication(String diabetesComplication) {
        this.diabetesComplication = diabetesComplication;
    }

    public String getHypoglycemia() {
        return hypoglycemia;
    }

    public void setHypoglycemia(String hypoglycemia) {
        this.hypoglycemia = hypoglycemia;
    }

    public String getSmbg() {
        return smbg;
    }

    public void setSmbg(String smbg) {
        this.smbg = smbg;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getEducationSituation() {
        return educationSituation;
    }

    public void setEducationSituation(String educationSituation) {
        this.educationSituation = educationSituation;
    }

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getWomenHistory() {
		return womenHistory;
	}

	public void setWomenHistory(String womenHistory) {
		this.womenHistory = womenHistory;
	}

	public String getQuestionJson() {
		return questionJson;
	}

	public void setQuestionJson(String questionJson) {
		this.questionJson = questionJson;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }
}