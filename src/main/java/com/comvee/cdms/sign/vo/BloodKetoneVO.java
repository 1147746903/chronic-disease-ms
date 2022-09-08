package com.comvee.cdms.sign.vo;

import java.util.Map;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/1/21 15:10
 **/
public class BloodKetoneVO {
    /**
     * 性别
     */
    private String sex;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 是否关注状态
     */
    private String concernStatus;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 住院表主键id
     */
    private String bedId;
    /**
     *  病床号
     */
    private String bedNo;
    /**
     *  血糖值列表
     */
    private String paramListJson;
    /**
     * 用餐时间
     */
    private String paramDt;

    private String isValid;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 监测方案详情---（1-2;3-7;5-3;7-0;7-2;7-3;8-0;8-2;8-3;8-4;8-5;8-7;8-8）
     */
    private String smbgScheme;
    /**
     *  血糖值列表
     */
    private String sugarListJson;
    /**
     * 处理血糖列表中最新血糖数据
     */
    private Map<String,Object> newSugarMap;
    /**
     * 患者名称
     */
    private String memberName;
    /**
     * 监测类型    1 长期  2临时 0 没有
     */
    private Integer planType;
    /**
     * 登记号
     */
    private String patPatientId;
    /**
     * 医嘱项目代码
     */
    private String yzItemCode;
    /**
     * 医嘱项目名称
     */
    private String yzItemName;

    /**
     * 医嘱状态 1 新开 2 已下发 3 执行中 4 已执行完 5 已停止 6 已作废 7 已校验
     */
    private Integer yzStatus;
    /**
     * 血酮参考范围（mmol/L）
     */
    private String referenceRange;
    /**
     * 最后一次的血酮数据
     */
    private String lastDetectKetoneData;


    public String getLastDetectKetoneData() {
        return lastDetectKetoneData;
    }

    public void setLastDetectKetoneData(String lastDetectKetoneData) {
        this.lastDetectKetoneData = lastDetectKetoneData;
    }

    public String getReferenceRange() {
        return referenceRange;
    }

    public void setReferenceRange(String referenceRange) {
        this.referenceRange = referenceRange;
    }

    public String getYzItemCode() {
        return yzItemCode;
    }

    public void setYzItemCode(String yzItemCode) {
        this.yzItemCode = yzItemCode;
    }

    public String getYzItemName() {
        return yzItemName;
    }

    public void setYzItemName(String yzItemName) {
        this.yzItemName = yzItemName;
    }

    public Integer getYzStatus() {
        return yzStatus;
    }

    public void setYzStatus(Integer yzStatus) {
        this.yzStatus = yzStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public String getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(String concernStatus) {
        this.concernStatus = concernStatus;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getParamListJson() {
        return paramListJson;
    }

    public void setParamListJson(String paramListJson) {
        this.paramListJson = paramListJson;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSmbgScheme() {
        return smbgScheme;
    }

    public void setSmbgScheme(String smbgScheme) {
        this.smbgScheme = smbgScheme;
    }

    public String getSugarListJson() {
        return sugarListJson;
    }

    public void setSugarListJson(String sugarListJson) {
        this.sugarListJson = sugarListJson;
    }

    public Map<String, Object> getNewSugarMap() {
        return newSugarMap;
    }

    public void setNewSugarMap(Map<String, Object> newSugarMap) {
        this.newSugarMap = newSugarMap;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getPatPatientId() {
        return patPatientId;
    }

    public void setPatPatientId(String patPatientId) {
        this.patPatientId = patPatientId;
    }
}
