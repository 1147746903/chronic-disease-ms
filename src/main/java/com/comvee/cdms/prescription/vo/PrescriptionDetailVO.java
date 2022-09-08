package com.comvee.cdms.prescription.vo;

public class PrescriptionDetailVO<T> {

    /**
     * 模块主键
     */
    private String moduleSid;
    /**
     * 模块名称（控制目标，监测方案，饮食治疗，运动治疗，知识教育）
     */
    private String moduleName;
    /**
     * 模块对应的编码 1 控制目标，2 监测方案，3 饮食治疗，4 运动治疗，5 知识教育
     */
    private Integer moduleType;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState;
    
    /**
     * 模块对象
     */
    private T module;

    private String doctorId;

    private Integer eduCycle; //学习周期

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getModuleSid() {
        return moduleSid;
    }

    public void setModuleSid(String moduleSid) {
        this.moduleSid = moduleSid;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

	public Integer getSaveState() {
		return saveState;
	}

	public void setSaveState(Integer saveState) {
		this.saveState = saveState;
	}

    public Integer getEduCycle() {
        return eduCycle;
    }

    public void setEduCycle(Integer eduCycle) {
        this.eduCycle = eduCycle;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailVO{" +
                "moduleSid='" + moduleSid + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleType=" + moduleType +
                ", saveState=" + saveState +
                ", module=" + module +
                ", doctorId='" + doctorId + '\'' +
                ", eduCycle='" + eduCycle + '\'' +
                '}';
    }
}
