package com.comvee.cdms.glu.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.glu.model.*;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;

import java.util.List;
import java.util.Map;

/**
 * @author: nzq
 * @time：2020-07-28  
 */
public interface GluServiceI {
	/**
	 * 加载床位列表
	 * @author: nzq
	 * @time：2020-07-28  
	 * @return Map<String,Object>
	 */
	Map<String,Object> listBedWithGroup(PageRequestModel pr, String concernStatus, DoctorSessionBO doctor ,String departmentId);
	
	/**
	 * 分页加载床位（刷新）
	 * @author: nzq
	 * @date 2020-07-28  
	 * @return Map<String,Object>
	 */
	Map<String,Object> listBedWithGroupForRefresh(PageRequestModel pr, String concernStatus, DoctorSessionBO doctor, String refreshTime ,String departmentId);
	
	/**
	 * 获取今日未测试任务数量
	 * @author nzq
	 * @date 2020-07-28  
	 * @param doctorId
	 * @return Map<String,Object>
	 */
	Map<String,Object> getMemberSmbgCount(String doctorId);
	
	/**
     * 获取机器版本号
     * @author nzq
     * @date 2020-07-28  
     * @return MachineVersionModel
     */
    MachineVersionModel getMachineVersionModel(GlucometerRequest gr);

    String getSrfDownLoadAddress(GlucometerRequest gr);

    /**
     * 加载血糖列表
     * @author nzq
     * @date 2020-07-28 
     * @param po MemberBloodSugarPO
     * @return Map<String,Object>
     */
    Map<String,Object> listMemberParamLog(ListBloodSugarDTO dto);

    /**
     * 刷新历史血糖
     * @author nzq
     * @date 2020-07-28 
     * @param po MemberBloodSugarPO
     * @return Map<String,Object>
     */
    Map<String,Object> refreshParamLogList(ListBloodSugarDTO dto);
    
    /**
     * 添加患者血糖记录
     * @author nzq
     * @date 2020-07-28
     * @param model MemberParamLogModel
     * @return ResultModel
     */
    ResultModel insertMemberBloodSugarLogWithLock(MemberParamLogModel model);

	/**
	 * 加载单点血糖仪接入者数据
	 * @return
	 */
	Map<String ,String> listGlucometerApplication();

	List<GluGroupVO> listGluMember(ListMemberDto dt);

	List<ListMemberVO> listGluMemberByKeyword(ListMemberDto dto);

	Map<String, Object> listMemberJoinHospitalForFresh(String hospitalId, PageRequest pr,String refreshTime);

	Map<String, Object> listMemberJoinHospital(String hospitalId, PageRequest pr);

	List<ListMemberVO> listGluManageMemberByKeyword(String hospitalId,String keyword);
}
