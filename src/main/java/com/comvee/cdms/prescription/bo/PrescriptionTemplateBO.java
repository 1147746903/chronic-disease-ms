package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/15 15:13.
 */
public class PrescriptionTemplateBO {
    @Override
    public String toString() {
        return "PrescriptionTemplateBO{}";
    }

    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 管理处方id
     */
    private String prescriptionId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
