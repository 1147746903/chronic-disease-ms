package com.comvee.cdms.virtualward.model.vo;

import com.comvee.cdms.virtualward.model.po.TransferApplyListPO;

public class TransferApplyListVO extends TransferApplyListPO {

    private String applyDoctorName;
    private String outHospitalDate;

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }
}
