package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author hbj
 * @description
 * @date 2021/5/10 15:34
 */
public class MemberLifeTypeDTO {
    private String sid;
    @NotBlank(message = "患者id(memberId)，不可为空")
    private String memberId;
    private String recordDt;
    private String modifyDt;
    private String isValid;
    private String content;

    @NotBlank(message = "开始时间（recordDtStart:yyyy-MM-dd），不可为空")
    private String recordDtStart;
    @NotBlank(message = "结束时间（recordDtEnd:yyyy-MM-dd），不可为空")
    private String recordDtEnd;

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

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordDtStart() {
        return recordDtStart;
    }

    public void setRecordDtStart(String recordDtStart) {
        this.recordDtStart = recordDtStart;
    }

    public String getRecordDtEnd() {
        return recordDtEnd;
    }

    public void setRecordDtEnd(String recordDtEnd) {
        this.recordDtEnd = recordDtEnd;
    }


}
