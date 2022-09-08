package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.NormalSugerListResqModel;
import com.comvee.cdms.app.doctorapp.model.app.NormalSugerModel;
import com.comvee.cdms.app.doctorapp.model.app.SugarDetailListModel;
import com.comvee.cdms.app.doctorapp.vo.CountMemberVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.AddBloodSugarMapperDTO;
import com.comvee.cdms.sign.vo.BloodSugarParamSettingVO;

import java.util.List;
import java.util.Map;

public interface BloodSugarServiceI {

	 List<Map<String,Object>> statisticsMemberAbnormalsNew(String doctorId , String payStatus,String startDt , String endDt,String hospitalId);

	 PageResult<SugarDetailListModel> workbenchSugarDetailNew(PageRequest page , String doctorId , String payStatus,String startDt , String endDt , String type,String hospitalId);
	
	 PageResult<NormalSugerModel> workbenchSugarDetailByNormal(String doctorId ,String startDt , String endDt , PageRequest page
			, String payStatus ,Integer visitType ,String departmentId,String hospitalId);
	
	 PageResult<NormalSugerListResqModel> workbenchSugarDetailByNotMeasured(String doctorId ,String startDt , String endDt
			, PageRequest page, String payStatus ,Integer visitType ,String departmentId,String hospitalId);
	
	 Map<String , Object> getGraphsForParametersNew(String memberId,String startDt , String endDt);

	 boolean addMemberBloodRecord(AddBloodSugarMapperDTO addBloodSugarMapperDTO);

	/**
	 * 住院患者血糖异常患者详情
	 * @param departmentId
	 * @return
	 */
	List<Map<String,Object>> listInHospitalPatientAbnormalSugarDetail(String departmentId ,String startDt ,String endDt);

	CountMemberVO countWorkbenchSugarDetail(String startDt, String endDt);

	/**
	 * 获取血糖时段配置
	 * @param doctorId
	 * @return
	 */
	List<BloodSugarParamSettingVO> listBloodSugarParamSetting(String hospitalId);

}
