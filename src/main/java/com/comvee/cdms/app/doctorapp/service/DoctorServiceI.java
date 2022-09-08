package com.comvee.cdms.app.doctorapp.service;


import com.comvee.cdms.app.doctorapp.model.app.HomePageRespModel;
import com.comvee.cdms.app.doctorapp.model.app.MobileVersionModel;
import com.comvee.cdms.common.wrapper.MobileRequest;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import com.comvee.cdms.user.dto.PasswordDTO;

public interface DoctorServiceI {

	/**
	 * 工作台首页统计项聚合
	 * @param doctorId
	 * @param visitType 1住院 2门诊居家
	 * @return
	 * @throws Exception
	 */
	public HomePageRespModel statisticsHomeNumberes(Integer visitType);
	
	/**
	 * 点击血糖异常情况打开的列表页
	 * @param payStatus 付费状态
	 * @param startDt
	 * @param endDt
	 */
	public void statisticsMemberAbnormalsNew(String payStatus ,String startDt ,String endDt );
	
	/**
	 * 修改密码
	 * @param dto 		PasswordDTO
	 * @param uid       String
	 * @param verification
	 */
	public void changePwd(PasswordDTO dto,String uid);
	
	/**
	 * 通过doctorId 获取医生信息
	 * @param doctorId       String
	 */
	public DoctorPO loadDoctorInfo(String doctorId);
	
	/**
	 * 修改医生信息
	 * @param updateDoctorDTO       UpdateDoctorDTO
	 */
	public void updateDoctor(UpdateDoctorDTO updateDoctorDTO);
	
	/**
	 * 展示主动添加医生的患者列表
	 * @param pr       		 PagerModel
	 * @param doctorId       String
	 */
	PageResult<DoctorMemberApplyPO> showDoctorPatientList(PageRequest pr, String doctorId);
	
    MobileVersionModel getMobileNewstVersion(MobileRequest mr);
}
