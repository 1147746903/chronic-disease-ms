package com.comvee.cdms.member.po;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class MemberListPO {

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
    private String sex;

    /**
     * 出生日期
     * birthday
     */
    private String birthday;

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

    private String doctorId;
    private String groupId;
    private String label;
    private Integer concernStatus;

    /**
     * 1免费 2付费3过期
     * priceFlag
     */
    private String priceFlag;

    /**
     * 分层标识: 1平稳层、2中危层、3高危层
     */
    private String levelHx;

    /**
     * 分级标识: 1级、2级、3级
     */
    private String dsmeHx;

    /**
     * 是否是住院患者 1是 0否
     */
    private String inHos;
    /**
     * 分层标识: 1平稳层、2中危层、3高危层
     */
    private Integer layer;

    /**
     * 是否有糖尿病 1:是 2:否
     */
    private String isDiabetes;

    /**
     * 医院编号
     */
    private String hospitalId;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 入院时间
     */
    private String inHospitalDt;

    private String departmentId;

    private String departmentName;

    /**
     * 使用的设备，多种用，隔开
     */
    private String useMachine;

    private String sid;
    private Integer checkinStatus;

    private String doctorZg;
    private String doctorZgCode;

    private String fat;

    private String positionName;
    private String inHospitalDay;

    /**
     * 出院时间
     */
    private String outHospitalDt;
    private Integer isVirtual;

    private Integer diabetesLever;
    private Integer hypLayer;
    private Integer hypLever;

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getDoctorZg() {
        return doctorZg;
    }

    public void setDoctorZg(String doctorZg) {
        this.doctorZg = doctorZg;
    }

    public String getDoctorZgCode() {
        return doctorZgCode;
    }

    public void setDoctorZgCode(String doctorZgCode) {
        this.doctorZgCode = doctorZgCode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(Integer checkinStatus) {
        this.checkinStatus = checkinStatus;
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

    public String getInHospitalDt() {
        return inHospitalDt;
    }

    public void setInHospitalDt(String inHospitalDt) {
        this.inHospitalDt = inHospitalDt;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    /**
     *高血压类型 :原发性高血压:HYP_TYPE_001  继发性高血压:HYP_TYPE_002 其他:HYP_TYPE_003
     */
    private String hypType;

    /**
     * 高血压确诊日期
     * diabetes_date
     */
    private String hypDate;

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
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

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
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

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getNephropathy() {
        return nephropathy;
    }

    public void setNephropathy(String nephropathy) {
        this.nephropathy = nephropathy;
    }

    public String getLabourIntensity() {
        return labourIntensity;
    }

    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity;
    }

    public String getHreatRate() {
        return hreatRate;
    }

    public void setHreatRate(String hreatRate) {
        this.hreatRate = hreatRate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getLevelHx() {
        return levelHx;
    }

    public void setLevelHx(String levelHx) {
        this.levelHx = levelHx;
    }

    public String getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(String priceFlag) {
        this.priceFlag = priceFlag;
    }

    public String getDsmeHx() {
        return dsmeHx;
    }

    public void setDsmeHx(String dsmeHx) {
        this.dsmeHx = dsmeHx;
    }

    public String getInHos() {
        return inHos;
    }

    public void setInHos(String inHos) {
        this.inHos = inHos;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLayer() {
        return layer;
    }

    public String getHypType() {
        return hypType;
    }

    public void setHypType(String hypType) {
        this.hypType = hypType;
    }

    public String getHypDate() {
        return hypDate;
    }

    public void setHypDate(String hypDate) {
        this.hypDate = hypDate;
    }

    public String getUseMachine() {
        return useMachine;
    }

    public void setUseMachine(String useMachine) {
        this.useMachine = useMachine;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getOutHospitalDt() {
        return outHospitalDt;
    }

    public void setOutHospitalDt(String outHospitalDt) {
        this.outHospitalDt = outHospitalDt;
    }

    public String getInHospitalDay() {
        return inHospitalDay;
    }

    public void setInHospitalDay(String inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    @Override
    public String toString() {
        return "MemberListPO{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberNamePy='" + memberNamePy + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", idCard='" + idCard + '\'' +
                ", profession='" + profession + '\'' +
                ", diabetesType='" + diabetesType + '\'' +
                ", diabetesDate='" + diabetesDate + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", bmi='" + bmi + '\'' +
                ", sbpPressure='" + sbpPressure + '\'' +
                ", dbpPressure='" + dbpPressure + '\'' +
                ", essentialHyp='" + essentialHyp + '\'' +
                ", chd='" + chd + '\'' +
                ", nephropathy='" + nephropathy + '\'' +
                ", labourIntensity='" + labourIntensity + '\'' +
                ", hreatRate='" + hreatRate + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", label='" + label + '\'' +
                ", concernStatus=" + concernStatus +
                ", priceFlag='" + priceFlag + '\'' +
                ", levelHx='" + levelHx + '\'' +
                ", dsmeHx='" + dsmeHx + '\'' +
                ", inHos='" + inHos + '\'' +
                ", layer=" + layer +
                ", isDiabetes='" + isDiabetes + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", inHospitalDt='" + inHospitalDt + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", useMachine='" + useMachine + '\'' +
                ", sid='" + sid + '\'' +
                ", checkinStatus=" + checkinStatus +
                ", doctorZg='" + doctorZg + '\'' +
                ", doctorZgCode='" + doctorZgCode + '\'' +
                ", hypType='" + hypType + '\'' +
                ", hypDate='" + hypDate + '\'' +
                '}';
    }

    public Integer getDiabetesLever() {
        return diabetesLever;
    }

    public void setDiabetesLever(Integer diabetesLever) {
        this.diabetesLever = diabetesLever;
    }

    public Integer getHypLayer() {
        return hypLayer;
    }

    public void setHypLayer(Integer hypLayer) {
        this.hypLayer = hypLayer;
    }

    public Integer getHypLever() {
        return hypLever;
    }

    public void setHypLever(Integer hypLever) {
        this.hypLever = hypLever;
    }
}
