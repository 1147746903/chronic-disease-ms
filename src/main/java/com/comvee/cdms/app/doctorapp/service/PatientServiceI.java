package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.MemberModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.AddGroupMapperDTO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.dto.AddMemberDTO;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.dto.UpdateDoctorMemberDTO;
import com.comvee.cdms.member.po.MemberPO;

import java.util.Map;

public interface PatientServiceI {

	/**
	 * 加载分组列表
	 * @param doctorId
	 * @param page
	 * @return
	 * @
	 */
	 Map<String,Object> loadMemberListAndGroup(String doctorId ,PageRequest page);
	
	/**
	 * 刷新页面后获取 最新 添加 或者删除的患者
	 * @param doctorId
	 * @param page
	 * @param lastDate
	 * @param groupType
	 * @return
	 * @
	 */
	 Map<String,Object> refreshMemberListAndGroup(String doctorId ,PageRequest page , String lastDate, String groupType);
	
	/**
	 * 付费 - 套餐分组
	 * @param groupType
	 */
	 void getGroupInfoByType(String groupType , String doctorId);
	
	 Map<String, Object> loadDoctorGrouping(String doctorId);
	
	/**
	 * 添加医生患者分组大类
	 * @param groupName
	 */
	 AddGroupMapperDTO addDoctorGrouping(String doctorId , String groupName);
	
	 void deleteDoctorGrouping(String groupId);
	
	 void editDoctorGrouping(String groupName,String groupId);
	
	 MemberModel memberDetailNew(String memberId,String doctorId);
	/**
     * 申请添加医患关系的患者信息
     * @param idCard String
     * @return
     */
	MemberPO getDocPatientInfo(GetMemberDTO requestMember,String doctorId, String hospitalId);
	
	/**
     * 添加医患关系
     * @param doctorId String
     * @param memberId String
     * @return
     */
	void addDoctorMember(String memberId,String doctorId,Integer relationWay);
	
	
	/**
     * 患者注册并添加医患关系
     * @param doctorId String
     * @param member MemberPO
     * @return
     */
	void addMemberAndRelation(AddMemberDTO addMemberDTO,DoctorSessionBO doctorSessionBO);
	
	void editGroupMember(UpdateDoctorMemberDTO updateDoctorMemberDTO);
	
    /**
     * 添加控制目标
     * @param rangeBO
     */
    void addMemberRange(RangeBO rangeBO);	
}
