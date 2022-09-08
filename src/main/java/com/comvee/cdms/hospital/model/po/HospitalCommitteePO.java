package com.comvee.cdms.hospital.model.po;

import java.io.Serializable;
import java.util.Objects;

/**
 * 医院 - 居委会关联表(HospitalCommittee)实体类
 *
 * @author lr
 * @since 2022-07-12
 */
public class HospitalCommitteePO implements Serializable {
    private static final long serialVersionUID = -72503616676162360L;

    private String id;

    private String committeeName;

    private String sort;

    private String orgId;

    private String hospitalId;

    private String insertDt;

    private String updateDt;

    private Integer valid;

    public HospitalCommitteePO() {
    }

    public HospitalCommitteePO(String committeeName, String hospitalId) {
        this.committeeName = committeeName;
        this.hospitalId = hospitalId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HospitalCommitteePO that = (HospitalCommitteePO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
