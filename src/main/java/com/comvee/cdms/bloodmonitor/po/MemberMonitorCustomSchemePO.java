package com.comvee.cdms.bloodmonitor.po;

public class MemberMonitorCustomSchemePO {
    private String sid;
    private String templateName; //模板名称
    private String monitorType; //监测类型
    private String templateType; //模板类型
    private String doctorId; //医生id
    private String insertDt; //插入时间
    private String modifyDt; //更新时间
    private String recordDt; //记录时间
    private Integer isValid; //是否有效 1:有效 0:无效

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "MemberMonitorCustomSchemePO{" +
                "sid='" + sid + '\'' +
                ", templateName='" + templateName + '\'' +
                ", monitorType='" + monitorType + '\'' +
                ", templateType='" + templateType + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", recordDt='" + recordDt + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
