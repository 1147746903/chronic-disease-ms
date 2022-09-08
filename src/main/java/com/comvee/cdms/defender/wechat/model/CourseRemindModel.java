package com.comvee.cdms.defender.wechat.model;


public class CourseRemindModel {
    private String sid;

    private String pid;//用户id

    private String insertDt;

    private String modifyDt;

    private String isValid;

    private String isOpen;//是否开启学习提醒 1是0否

    private String remindTime;//提醒时间  格式  时：分

    private Integer monday;//周一提醒

    private Integer tuesday;//周二提醒

    private Integer wednesday;//周三提醒

    private Integer thursday;//周四提醒

    private Integer friday;//周五提醒

    private Integer saturday;//周六提醒

    private Integer sunday;//周日提醒
    
    private String remindNum;//提醒次数（超过五次下发未学习干预）
    
    private String lastStudyDate;//最后学习时间（大于十五天下发未学习干预）
    
    private String studyStatus;//未学习干预状态1下发过0未下发（1的话无需再次发送未学习干预，直到重新学习时置为0）
    
    private String courserRemind;//是否开启新课程提醒 1是0否
    
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }
    
    
    
	public String getRemindNum() {
		return remindNum;
	}

	public void setRemindNum(String remindNum) {
		this.remindNum = remindNum;
	}

	public String getLastStudyDate() {
		return lastStudyDate;
	}

	public void setLastStudyDate(String lastStudyDate) {
		this.lastStudyDate = lastStudyDate;
	}
	
	
	
	public String getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(String studyStatus) {
		this.studyStatus = studyStatus;
	}
	
	
	
	public String getCourserRemind() {
		return courserRemind;
	}

	public void setCourserRemind(String courserRemind) {
		this.courserRemind = courserRemind;
	}

	@Override
	public String toString() {
		return "CourseRemindModel [sid=" + sid + ", pid=" + pid + ", insertDt=" + insertDt + ", modifyDt=" + modifyDt
				+ ", isValid=" + isValid + ", isOpen=" + isOpen + ", remindTime=" + remindTime + ", monday=" + monday
				+ ", tuesday=" + tuesday + ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday
				+ ", saturday=" + saturday + ", sunday=" + sunday + "]";
	}
    
    
}