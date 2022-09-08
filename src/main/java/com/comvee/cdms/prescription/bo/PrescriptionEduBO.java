package com.comvee.cdms.prescription.bo;

/**
 * @author 李左河
 * @date 2018/8/6 10:33.
 */
public class PrescriptionEduBO {
    @Override
    public String toString() {
        return "PrescriptionEduBO{}";
    }

    /**
     * 管理处方id
     */
    private String prescriptId;
    /**
     * 档案json
     */
    private String archivesJson;

    public String getPrescriptId() {
        return prescriptId;
    }

    public void setPrescriptId(String prescriptId) {
        this.prescriptId = prescriptId;
    }

    public String getArchivesJson() {
        return archivesJson;
    }

    public void setArchivesJson(String archivesJson) {
        this.archivesJson = archivesJson;
    }
}
