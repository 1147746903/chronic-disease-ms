package com.comvee.cdms.app.doctorapp.model.app;

import java.util.Map;

public class MemberModel {
    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 患者姓名
     * member_name
     */
    private String memberName;

    /**
     * 患者姓名拼音
     * member_name_py
     */
    private String memberNamePy;

    /**
     * 头像地址
     * pic_url
     */
    private String picUrl;

    /**
     * 手机号
     * mobile_phone
     */
    private String mobilePhone;

    /**
     * 性别 1男2女
     * sex
     */
    private Integer sex;

    /**
     * 出生日期
     * birthday
     */
    private String birthday;
    
    private String age;
    
    /**
     * 身份证号码
     * id_card
     */
    private String idCard;

    /**
     * 职业
     * profession
     */
    private String profession;

    /**
     * 糖尿病类型：1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)	当糖尿病诊断选项选“确诊有”时出现糖尿病类型选项	是
     * diabetes_type
     */
    private String diabetesType;

    /**
     * 确诊日期
     * diabetes_date
     */
    private String diabetesDate;
    
    private String hasSuggerTime;


    /**
     * 插入时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 修改时间
     * modify_dt
     */
    private String modifyDt;

    /**
     * 身高
     * height
     */
    private String height;

    /**
     * 体重
     * weight
     */
    private String weight;

    /**
     * bmi
     * bmi
     */
    private String bmi;
    
    private String bmiLevel;


    /**
     * 收缩压
     * blood_pressure
     */
    private String sbpPressure;
    /**
     * 舒张压
     * blood_pressure
     */
    private String dbpPressure;



    /**
     * 国家
     * country
     */
    private String country;

    /**
     * 详细地址
     * address
     */
    private String address;

    /**
     * 高血压:1有、2无
     * essential_hyp
     */
    private String essentialHyp;

    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     * chd
     */
    private String chd;

    /**
     * 是否有糖尿病肾病:确诊有SB01、确诊无SB02、未确诊  未评估SB03疑似SB04
     * nephropathy
     */
    private String nephropathy;

    /**
     * 劳动强度 RCHDQD01：轻体力劳动 RCHDQD02：中体力劳动 RCHDQD03:重体力劳动 RCHDQD04:非劳动/卧床
     * labour_intensity
     */
    private String labourIntensity;

    /**
     * 心率
     * hreat_rate
     */
    private String hreatRate;

    /**
     * 患者详细信息
     * member_info
     */
    private String memberInfo;
    
    /**
     * 将血糖正常异常统计 放入map
     */
    private Map<String , Object> bloodNumStaticsMap;
    
    /**空腹低血糖*/
    private String lowEmpty;
    
    /**空腹高血糖*/
    private String highEmpty;
    
    /**非空腹低血糖*/
    private String lowFull;
    
    /**非空腹低血糖*/
    private String highFull;    
    
    /***/
    private String highHemoglobin;
    
    /***/
    private String lowHemoglobin; 
    
    /**正常的糖化值*/
    private String hemoglobin;
    
    /**管理处方数*/
    private String eohNum;
    
    /**随访数量*/
    private String followNum;
    
    /**获取患者血糖监测方案*/
//    private MemberMonitorPlanPO schemeName;
    private String schemeName;

    /**患者用药设置方案*/
    private Object memberDrug;

    /**
     * 付费状态
     */
    private String priceFlag;

    /**
     * 分层标识: 1平稳层、2中危层、3高危层
     */
    private Integer layer;
    private Integer inHos;

    //住院信息
    private String departmentId;
    private String departmentName;
    private String hospitalNo;
    private String bedNo;

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(String priceFlag) {
        this.priceFlag = priceFlag;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.member_id
     *
     * @return the value of t_member.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.member_id
     *
     * @param memberId the value for t_member.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.member_name
     *
     * @return the value of t_member.member_name
     *
     * @mbggenerated
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.member_name
     *
     * @param memberName the value for t_member.member_name
     *
     * @mbggenerated
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.member_name_py
     *
     * @return the value of t_member.member_name_py
     *
     * @mbggenerated
     */
    public String getMemberNamePy() {
        return memberNamePy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.member_name_py
     *
     * @param memberNamePy the value for t_member.member_name_py
     *
     * @mbggenerated
     */
    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy == null ? null : memberNamePy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.pic_url
     *
     * @return the value of t_member.pic_url
     *
     * @mbggenerated
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.pic_url
     *
     * @param picUrl the value for t_member.pic_url
     *
     * @mbggenerated
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.mobile_phone
     *
     * @return the value of t_member.mobile_phone
     *
     * @mbggenerated
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.mobile_phone
     *
     * @param mobilePhone the value for t_member.mobile_phone
     *
     * @mbggenerated
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.birthday
     *
     * @return the value of t_member.birthday
     *
     * @mbggenerated
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.birthday
     *
     * @param birthday the value for t_member.birthday
     *
     * @mbggenerated
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.id_card
     *
     * @return the value of t_member.id_card
     *
     * @mbggenerated
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.id_card
     *
     * @param idCard the value for t_member.id_card
     *
     * @mbggenerated
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.profession
     *
     * @return the value of t_member.profession
     *
     * @mbggenerated
     */
    public String getProfession() {
        return profession;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.profession
     *
     * @param profession the value for t_member.profession
     *
     * @mbggenerated
     */
    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.diabetes_type
     *
     * @return the value of t_member.diabetes_type
     *
     * @mbggenerated
     */
    public String getDiabetesType() {
        return diabetesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.diabetes_type
     *
     * @param diabetesType the value for t_member.diabetes_type
     *
     * @mbggenerated
     */
    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType == null ? null : diabetesType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.diabetes_date
     *
     * @return the value of t_member.diabetes_date
     *
     * @mbggenerated
     */
    public String getDiabetesDate() {
        return diabetesDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.diabetes_date
     *
     * @param diabetesDate the value for t_member.diabetes_date
     *
     * @mbggenerated
     */
    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate == null ? null : diabetesDate.trim();
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.insert_dt
     *
     * @return the value of t_member.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.insert_dt
     *
     * @param insertDt the value for t_member.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.modify_dt
     *
     * @return the value of t_member.modify_dt
     *
     * @mbggenerated
     */
    public String getModifyDt() {
        return modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.modify_dt
     *
     * @param modifyDt the value for t_member.modify_dt
     *
     * @mbggenerated
     */
    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt == null ? null : modifyDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.height
     *
     * @return the value of t_member.height
     *
     * @mbggenerated
     */
    public String getHeight() {
        return height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.height
     *
     * @param height the value for t_member.height
     *
     * @mbggenerated
     */
    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.weight
     *
     * @return the value of t_member.weight
     *
     * @mbggenerated
     */
    public String getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.weight
     *
     * @param weight the value for t_member.weight
     *
     * @mbggenerated
     */
    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.bmi
     *
     * @return the value of t_member.bmi
     *
     * @mbggenerated
     */
    public String getBmi() {
        return bmi;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.bmi
     *
     * @param bmi the value for t_member.bmi
     *
     * @mbggenerated
     */
    public void setBmi(String bmi) {
        this.bmi = bmi == null ? null : bmi.trim();
    }

    public String getSbpPressure() {
        return sbpPressure;
    }

    public void setSbpPressure(String sbpPressure) {
        this.sbpPressure = sbpPressure;
    }

    public String getDbpPressure() {
        return dbpPressure;
    }

    public void setDbpPressure(String dbpPressure) {
        this.dbpPressure = dbpPressure;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.country
     *
     * @return the value of t_member.country
     *
     * @mbggenerated
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.country
     *
     * @param country the value for t_member.country
     *
     * @mbggenerated
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.address
     *
     * @return the value of t_member.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.address
     *
     * @param address the value for t_member.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.essential_hyp
     *
     * @return the value of t_member.essential_hyp
     *
     * @mbggenerated
     */
    public String getEssentialHyp() {
        return essentialHyp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.essential_hyp
     *
     * @param essentialHyp the value for t_member.essential_hyp
     *
     * @mbggenerated
     */
    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp == null ? null : essentialHyp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.chd
     *
     * @return the value of t_member.chd
     *
     * @mbggenerated
     */
    public String getChd() {
        return chd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.chd
     *
     * @param chd the value for t_member.chd
     *
     * @mbggenerated
     */
    public void setChd(String chd) {
        this.chd = chd == null ? null : chd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.nephropathy
     *
     * @return the value of t_member.nephropathy
     *
     * @mbggenerated
     */
    public String getNephropathy() {
        return nephropathy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.nephropathy
     *
     * @param nephropathy the value for t_member.nephropathy
     *
     * @mbggenerated
     */
    public void setNephropathy(String nephropathy) {
        this.nephropathy = nephropathy == null ? null : nephropathy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.labour_intensity
     *
     * @return the value of t_member.labour_intensity
     *
     * @mbggenerated
     */
    public String getLabourIntensity() {
        return labourIntensity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.labour_intensity
     *
     * @param labourIntensity the value for t_member.labour_intensity
     *
     * @mbggenerated
     */
    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity == null ? null : labourIntensity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.hreat_rate
     *
     * @return the value of t_member.hreat_rate
     *
     * @mbggenerated
     */
    public String getHreatRate() {
        return hreatRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.hreat_rate
     *
     * @param hreatRate the value for t_member.hreat_rate
     *
     * @mbggenerated
     */
    public void setHreatRate(String hreatRate) {
        this.hreatRate = hreatRate == null ? null : hreatRate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member.member_info
     *
     * @return the value of t_member.member_info
     *
     * @mbggenerated
     */
    public String getMemberInfo() {
        return memberInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member.member_info
     *
     * @param memberInfo the value for t_member.member_info
     *
     * @mbggenerated
     */
    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo == null ? null : memberInfo.trim();
    }

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHasSuggerTime() {
		return hasSuggerTime;
	}

	public void setHasSuggerTime(String hasSuggerTime) {
		this.hasSuggerTime = hasSuggerTime;
	}

	public Map<String, Object> getBloodNumStaticsMap() {
		return bloodNumStaticsMap;
	}

	public void setBloodNumStaticsMap(Map<String, Object> bloodNumStaticsMap) {
		this.bloodNumStaticsMap = bloodNumStaticsMap;
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

	public String getHighHemoglobin() {
		return highHemoglobin;
	}

	public void setHighHemoglobin(String highHemoglobin) {
		this.highHemoglobin = highHemoglobin;
	}

	public String getLowHemoglobin() {
		return lowHemoglobin;
	}

	public void setLowHemoglobin(String lowHemoglobin) {
		this.lowHemoglobin = lowHemoglobin;
	}

	public String getEohNum() {
		return eohNum;
	}

	public void setEohNum(String eohNum) {
		this.eohNum = eohNum;
	}

	public String getFollowNum() {
		return followNum;
	}

	public void setFollowNum(String followNum) {
		this.followNum = followNum;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(String hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	public String getBmiLevel() {
		return bmiLevel;
	}

	public void setBmiLevel(String bmiLevel) {
		this.bmiLevel = bmiLevel;
	}

    public Object getMemberDrug() {
        return memberDrug;
    }

    public void setMemberDrug(Object memberDrug) {
        this.memberDrug = memberDrug;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLayer() {
        return layer;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
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

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }
}
