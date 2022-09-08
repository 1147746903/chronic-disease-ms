package com.comvee.cdms.member.dto;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class ListMemberDTO implements Serializable{

    private String groupId;

    @NotBlank(message = "医生编号不可为空")
    private String doctorId;
    /**
     * 患者名称或患者名称拼音（含）
     */
    private String keyWord;
    /**
     * 是否关注
     */
    private Integer concernStatus;
    /**
     * 管理病种 1:糖尿病 2:高血压
     */
    private Integer type;
    /**
     * 是否监测 1是 0否
     */
    private Integer monitor;
    /**
     * 血糖情况 1低血糖 3正常 5高血糖
     */
    private String paramLevel;
    /**
     * 医护人员编号串
     */
    private List<String> doctorIdList;
    /**
     * 医院编号
     */
    private String hospitalId;
    private List<String> groupIdList;

    /**
     * 是否使用动态血糖 1 有  0 没有
     */
    private Integer useDynamic;

    /**
     * 是否加载虚拟病区数据
     */
    private Integer virtualWardFlag = 0;

    private String memberId;

    private Integer virtualWardAuthority; //是否有虚拟病区权限 1:有  0:没有(最大的那个模块权限)

    private String inStartDt; //入院
    private String inEndDt;
    private String outStartDt;//住院
    private String outEndDt;
    private String checkinStatus;//入住状态 0-未入住 1-入住

    private Integer origin = 1;//1web2pad


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getVirtualWardFlag() {
        return virtualWardFlag;
    }

    public void setVirtualWardFlag(Integer virtualWardFlag) {
        this.virtualWardFlag = virtualWardFlag;
    }

    public Integer getMonitor() {
        return monitor;
    }

    public void setMonitor(Integer monitor) {
        this.monitor = monitor;
    }

    public String getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(String paramLevel) {
        this.paramLevel = paramLevel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public Integer getUseDynamic() {
        return useDynamic;
    }

    public void setUseDynamic(Integer useDynamic) {
        this.useDynamic = useDynamic;
    }

    public Integer getVirtualWardAuthority() {
        return virtualWardAuthority;
    }

    public void setVirtualWardAuthority(Integer virtualWardAuthority) {
        this.virtualWardAuthority = virtualWardAuthority;
    }

    public String getInStartDt() {
        return inStartDt;
    }

    public void setInStartDt(String inStartDt) {
        this.inStartDt = inStartDt;
    }

    public String getInEndDt() {
        return inEndDt;
    }

    public void setInEndDt(String inEndDt) {
        this.inEndDt = inEndDt;
    }

    public String getOutStartDt() {
        return outStartDt;
    }

    public void setOutStartDt(String outStartDt) {
        this.outStartDt = outStartDt;
    }

    public String getOutEndDt() {
        return outEndDt;
    }

    public void setOutEndDt(String outEndDt) {
        this.outEndDt = outEndDt;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}
