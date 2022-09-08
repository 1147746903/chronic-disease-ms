package com.comvee.cdms.checkresult.po;

import java.util.Date;

/**
 * 
 * @author 李左河
 *
 */
public class MemberCheckResultPO {
    private Long sid;
    private Long memberId;
    private String resultType;
    private String checkItem;
    private String checkExample;
    private String departmentId;
    private String departmentName;
    private Long userId;
    private String userName;
    private Date checkDt;
    private String pdfUrl;
    private Integer isValid;
    private Date insertDt;
    private Date modifyDt;
    private String textReport;
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public String getCheckExample() {
		return checkExample;
	}
	public void setCheckExample(String checkExample) {
		this.checkExample = checkExample;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCheckDt() {
		return checkDt;
	}
	public void setCheckDt(Date checkDt) {
		this.checkDt = checkDt;
	}
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Date getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(Date insertDt) {
		this.insertDt = insertDt;
	}
	public Date getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getTextReport() {
		return textReport;
	}
	public void setTextReport(String textReport) {
		this.textReport = textReport;
	}
    
}