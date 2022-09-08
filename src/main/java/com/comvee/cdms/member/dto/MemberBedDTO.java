package com.comvee.cdms.member.dto;

public class MemberBedDTO {
	private static final long serialVersionUID = 6338237690532979416L;
	/**
	 * 用户id
	 */
	private String memberId;        
	/**
	 * 用户名称
	 */
    private String memberName;      
    /**
     *  头像缩略图
     */
    private String picPathS;       
    /**
     * 用户性别
     */
    private String sex;             
    /**
     * 用户生日
     */
    private String birthday;        
    /**
     * 用户身份证号码
     */
    private String idCard;          
    /**
     * 糖尿病类型
     */
    private String diabetesType;    
    /**
     * 确诊糖尿病时间
     */
    private String diabetesDate;    
    /**
     * 是否有效
     */
    private String isValid;         
    /**
     * 添加日期
     */
    private String insertDt;        
    /**
     * 修改日期
     */
    private String modifyDt;        
    /**
     * 入院时间
     */
    private String inHospitalDate;  
    /**
     * 出院时间
     */
    private String outHospitalDate; 
    /**
     * 病床id
     */
    private String bedId;           
    /**
     * 病床编号
     */
    private String bedNo;           
    /**
     * 是否入住 1入住 0未入住
     */
    private String isCheckin;       
    /**
     * 病房id
     */
    private String roomId;          
    /**
     * 成员血糖记录id
     */
    private String paramLogId;      
    /**
     * 血糖记录code
     */
    private String paramCode;       
    /**
     * 记录时间
     */
    private String recordTime;      
    /**
     * 异常等级
     */
    private String level;           
    /**
     * 血糖记录值
     */
    private String value;           
    /**
     * 空腹低
     */
    private String lowEmpty;        
    /**
     *  空腹高
     */
    private String highEmpty;      
    /**
     * 非空腹低
     */
    private String lowFull;         
    /**
     * 非空腹高
     */
    private String highFull;        
    /**
     * 检测状态 0-未检测 1-已检测 2-拒绝检测
     */
    private String status;     
    private String roomNo;          
    /**
     * 病房编号
     */
    private String departmentId;    
    /**
     * 科室id
     */
    /**
     * 科室名称
     */
    private String departmentName;  
    /**
     * 关注状态
     */
    private String concernStatus;   
    /**
     * 住院号
     */
    private String hospitalNo;      
    /**
     * 操作类型
     */
    private String optionType;      
    private int diabetesDt;
    private int age;
    /**
     * 患者头像地址
     */
    private String picUrl;   
    private String patientId;
    private String recodeId;
    
    public String getRecodeId() {
		return recodeId;
	}

	public void setRecodeId(String recodeId) {
		this.recodeId = recodeId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getIsCheckin() {
        return isCheckin;
    }

    public void setIsCheckin(String isCheckin) {
        this.isCheckin = isCheckin;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getParamLogId() {
        return paramLogId;
    }

    public void setParamLogId(String paramLogId) {
        this.paramLogId = paramLogId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLowEmpty() {
        return lowEmpty;
    }

    public void setLowEmpty(String lowEmpty) {
        this.lowEmpty = lowEmpty;
    }

    public String getHighEmpty() {
        return highEmpty;
    }

    public void setHighEmpty(String highEmpty) {
        this.highEmpty = highEmpty;
    }

    public String getLowFull() {
        return lowFull;
    }

    public void setLowFull(String lowFull) {
        this.lowFull = lowFull;
    }

    public String getHighFull() {
        return highFull;
    }

    public void setHighFull(String highFull) {
        this.highFull = highFull;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(String concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getPicPathS() {
        return picPathS;
    }

    public void setPicPathS(String picPathS) {
        this.picPathS = picPathS;
    }

    public void setDiabetesDt(int diabetesDt) {
        this.diabetesDt = diabetesDt;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDiabetesDt() {
        return diabetesDt;
    }

    public int getAge() {
        return age;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
