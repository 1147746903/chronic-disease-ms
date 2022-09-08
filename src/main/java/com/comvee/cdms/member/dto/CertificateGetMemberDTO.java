package com.comvee.cdms.member.dto;

/**
 * @Author linr
 * @Date 2022/4/13
 */
public class CertificateGetMemberDTO {

    private String hospitalId;
    private String idCard;//身份证
    private String visitNo;//就诊号
    private String hospitalNo;//住院号

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }
}
