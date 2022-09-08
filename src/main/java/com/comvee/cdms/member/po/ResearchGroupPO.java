package com.comvee.cdms.member.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_research_group
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class ResearchGroupPO {
    /**
     * groupId
     */
    private String groupId;

    /**
     * 课题组名称
     * research_group_name
     */
    private String researchGroupName;

    /**
     * 成员人数
     * member_number
     */
    private Integer memberNumber;

    /**
     * 医院id
     * hospital_id
     */
    private String hospitalId;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * valid
     */
    private Integer valid;

    /**
     * 创建者id
     * creator_id
     */
    private String creatorId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.research_group_name
     *
     * @return the value of t_research_group.research_group_name
     *
     * @mbggenerated
     */
    public String getResearchGroupName() {
        return researchGroupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.research_group_name
     *
     * @param researchGroupName the value for t_research_group.research_group_name
     *
     * @mbggenerated
     */
    public void setResearchGroupName(String researchGroupName) {
        this.researchGroupName = researchGroupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.member_number
     *
     * @return the value of t_research_group.member_number
     *
     * @mbggenerated
     */
    public Integer getMemberNumber() {
        return memberNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.member_number
     *
     * @param memberNumber the value for t_research_group.member_number
     *
     * @mbggenerated
     */
    public void setMemberNumber(Integer memberNumber) {
        this.memberNumber = memberNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.hospital_id
     *
     * @return the value of t_research_group.hospital_id
     *
     * @mbggenerated
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.hospital_id
     *
     * @param hospitalId the value for t_research_group.hospital_id
     *
     * @mbggenerated
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.insert_dt
     *
     * @return the value of t_research_group.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.insert_dt
     *
     * @param insertDt the value for t_research_group.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.update_dt
     *
     * @return the value of t_research_group.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.update_dt
     *
     * @param updateDt the value for t_research_group.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.valid
     *
     * @return the value of t_research_group.valid
     *
     * @mbggenerated
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.valid
     *
     * @param valid the value for t_research_group.valid
     *
     * @mbggenerated
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_research_group.creator_id
     *
     * @return the value of t_research_group.creator_id
     *
     * @mbggenerated
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_research_group.creator_id
     *
     * @param creatorId the value for t_research_group.creator_id
     *
     * @mbggenerated
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}