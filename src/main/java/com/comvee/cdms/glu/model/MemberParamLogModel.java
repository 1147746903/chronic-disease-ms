package com.comvee.cdms.glu.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author 李左河
 *
 */
public class MemberParamLogModel implements Serializable {
	private static final long serialVersionUID = -1235912431055734694L;
	/**
	 * 成员血糖记录id
	 */
	private String paramLogId;          
	/**
	 * 成员id
	 */
    private String memberId;

    /**
     * 身份证号码
     * id_card
     */
    private String idCard;
    /**
     * 血糖记录code
     */
    private String paramCode;           
    /**
     * 添加时间
     */
    private String insertDt;            
    /**
     * 血糖记录值
     */
    private String value;               
    /**
     *  是否有效
     */
    private String isValid;            
    /**
     * 记录时间
     */
    private String recordTime;          
    /**
     * 设备code
     */
    private String innerCode;           
    /**
     * 设备id
     */
    private String innerId;             
    /**
     * 批次id
     */
    private String batchId;             
    /**
     *  是否执行推送
     */
    private String processed;          
    /**
     * 执行推送时间
     */
    private String processedDt;         
    /**
     * 推送消息
     */
    private String processedMsg;        
    /**
     * 记录来源
     */
    private String recordOrigin;        
    /**
     * 异常等级
     */
    private String level;               
    /**
     * 血糖异常操作
     */
    private String paramOption;         
    /**
     * 修改时间
     */
    private String modifyDt;            
    /**
     * 备注
     */
    private String remark;              
    /**
     * 状态
     */
    private String status;              
    /**
     * 患者姓名
     */
    private String memberName;          
    /**
     * 科室名称
     */
    private String departmentName;      
    /**
     * 床号
     */
    private String bedNo;               
    /**
     * 空腹低
     */
    private String lowEmpty;            
    /**
     * 非空腹低
     */
    private String lowFull;             
    /**
     * 空腹高
     */
    private String highEmpty;           
    /**
     * 非空腹高
     */
    private String highFull;            
    /**
     *  异常建议
     */
    private String adviseContent;      
    /**
     *  记录日期
     */
    private String recordDt;           
    /**
     * 操作用户id
     */
    private String optionUserId;        
    /**
     * 操作用户名称
     */
    private String optionUserName;      
    /**
     * 部门编号
     */
    private String departmentId;        
    /**
     * 在院状态 1：在院 0 非在院
     */
    private Integer paramType;           


    /**
     * 统计数量存放变量
     */
    private Integer num;
    /**
     * 入院时间
     */
    private String inHospitalDate;
    /**筛选条件 start **/
    /**
     * 开始时间
     */
    private String startDt; 
    /**
     * 结束时间
     */
    private String endDt;  
    /**
     * 时间节点
     */
    private List<String> paramCodeList; 
    /**
     * 是否异常
     */
    private boolean isAbnormal;
    /**
     * 科室编号
     */
    private List<String> departmentIdList;
    /**
     * 是否偏高
     */
    private boolean highAbnormal;
    /**
     * 是否偏低
     */
    private boolean lowAbnormal;
    private List<String> memberIdList;
    /**
     * 降序排序
     */
    private boolean orderByDesc;
    /**
     * 根据患者编号排序
     */
    private boolean groupByMid;
    /**
     * 患者在院状态 1：在院 0 非在院
     */
    private Integer checkinStatus;
    /**
     * 医生编号
     */
    private String doctorId;
    /**
     * 大于某个值的血糖（16.7）
     */
    private Double highByValue;
    /**
     * 筛选条件
     */
    private String hospitalId;
    /****end***********/
    private Integer logCount;
    
    /**
     * 是否关联医患关系表
     */
    private boolean joinDocMember;
    
    /**
     * 血糖等级 1-极低 2-偏低 3-正常 4-偏高 5-很高
     */
    private String paramLevel;		 
    
    /**
     * 血糖值
     */
    private String paramValue;		 
    
    /**
     * 主键ID
     */
    private String sid;		
    
    /**
     *  血糖来源 1 小程序  2 医生web 3 医生端 4 HIS 5 随访 6 血糖仪  
     */
    private String origin;

    /**
     * 应用id
     */
    private String appId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getParamLevel() {
		return paramLevel;
	}

	public void setParamLevel(String paramLevel) {
		this.paramLevel = paramLevel;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getParamLogId() {
        return paramLogId;
    }

    public void setParamLogId(String paramLogId) {
        this.paramLogId = paramLogId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getInnerId() {
        return innerId;
    }

    public void setInnerId(String innerId) {
        this.innerId = innerId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getProcessedDt() {
        return processedDt;
    }

    public void setProcessedDt(String processedDt) {
        this.processedDt = processedDt;
    }

    public String getProcessedMsg() {
        return processedMsg;
    }

    public void setProcessedMsg(String processedMsg) {
        this.processedMsg = processedMsg;
    }

    public String getRecordOrigin() {
        return recordOrigin;
    }

    public void setRecordOrigin(String recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParamOption() {
        return paramOption;
    }

    public void setParamOption(String paramOption) {
        this.paramOption = paramOption;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getLowEmpty() {
        return lowEmpty;
    }

    public void setLowEmpty(String lowEmpty) {
        this.lowEmpty = lowEmpty;
    }

    public String getLowFull() {
        return lowFull;
    }

    public void setLowFull(String lowFull) {
        this.lowFull = lowFull;
    }

    public String getHighEmpty() {
        return highEmpty;
    }

    public void setHighEmpty(String highEmpty) {
        this.highEmpty = highEmpty;
    }

    public String getHighFull() {
        return highFull;
    }

    public void setHighFull(String highFull) {
        this.highFull = highFull;
    }

    public String getAdviseContent() {
        return adviseContent;
    }

    public void setAdviseContent(String adviseContent) {
        this.adviseContent = adviseContent;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getOptionUserId() {
        return optionUserId;
    }

    public void setOptionUserId(String optionUserId) {
        this.optionUserId = optionUserId;
    }

    public String getOptionUserName() {
        return optionUserName;
    }

    public void setOptionUserName(String optionUserName) {
        this.optionUserName = optionUserName;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean abnormal) {
        isAbnormal = abnormal;
    }


    public boolean isHighAbnormal() {
        return highAbnormal;
    }

    public void setHighAbnormal(boolean highAbnormal) {
        this.highAbnormal = highAbnormal;
    }

    public boolean isLowAbnormal() {
        return lowAbnormal;
    }

    public void setLowAbnormal(boolean lowAbnormal) {
        this.lowAbnormal = lowAbnormal;
    }


    public boolean isOrderByDesc() {
        return orderByDesc;
    }

    public void setOrderByDesc(boolean orderByDesc) {
        this.orderByDesc = orderByDesc;
    }

    public boolean isGroupByMid() {
        return groupByMid;
    }

    public void setGroupByMid(boolean groupByMid) {
        this.groupByMid = groupByMid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<String> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<String> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public Integer getCheckinStatus() {

        return checkinStatus;
    }

    public void setCheckinStatus(Integer checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Double getHighByValue() {
        return highByValue;
    }

    public void setHighByValue(Double highByValue) {
        this.highByValue = highByValue;
    }

    public Integer getLogCount() {
        return logCount;
    }

    public void setLogCount(Integer logCount) {
        this.logCount = logCount;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setJoinDocMember(boolean joinDocMember) {
        this.joinDocMember = joinDocMember;
    }

    public boolean isJoinDocMember() {
        return joinDocMember;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
