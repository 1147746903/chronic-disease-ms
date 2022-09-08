package com.comvee.cdms.app.doctorapp.model.app;

public class PrescriptionModel {

    /**
     * 主键唯一标示
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 更新时间
     */
    private String modifyDt;
    /**
     * 是否有效数据 1有效 0无效
     */
    private Integer isValid;
    /**
     * 专科医生id
     */
    private String doctorId;
    /**
     * 管理处方状态(1待制定 2保存草稿 3已完成)
     */
    private Integer schedule;
    /**
     * 选择的模块 1控制目标，2监测方案，3饮食，4运动，5知识学习
     */
    private String module;
    /**
     * 学习周期
     */
    private Integer eduCycle;
    /**
     * 0:糖尿病（非妊娠）自我管理处方 1:妊娠糖尿病自我管理处方
     */
    private Integer eohType;
    /**
     * 下发状态 1已下发 0未下发
     */
    private Integer handDown;
    /**
     * 版本 1 默认版本（第一代）2 新版本（20180524）
     */
    private Integer version;
    /**
     * 医院编号
     */
    private String hospitalId;
    /**
     * 患者基本信息
     */
    private String memberInfoJson;
    
    private String typeName;
    
    private String url;
    
    private String moduleName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getEduCycle() {
        return eduCycle;
    }

    public void setEduCycle(Integer eduCycle) {
        this.eduCycle = eduCycle;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
    }

    public Integer getHandDown() {
        return handDown;
    }

    public void setHandDown(Integer handDown) {
        this.handDown = handDown;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMemberInfoJson() {
        return memberInfoJson;
    }

    public void setMemberInfoJson(String memberInfoJson) {
        this.memberInfoJson = memberInfoJson;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
    
}
