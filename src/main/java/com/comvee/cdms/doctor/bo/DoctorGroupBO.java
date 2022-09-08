package com.comvee.cdms.doctor.bo;

/**
 * @author 李左河
 * @date 2018/7/25 16:43.
 */
public class DoctorGroupBO {
    @Override
    public String toString() {
        return "DoctorGroupBO{}";
    }

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 是否有效 1 有 0否
     */
    private Integer isValid;

    private String createId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }
}
