package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.vo.DrugsMemberVO;
import com.comvee.cdms.member.po.MemberDrugItemPO;

import java.util.List;

public interface DrugsMemberServiceI {

	/**
	 * 加载药品列表
	 * @param listDrugsDepotDTO
	 * @return
	 */
	PageResult<DrugsDepotPO> listDrugsDepotPageByHosForDefault(String hosId,String memberId,String type, String drugName, PageRequest page);


	/**
	 * 新增药品记录
	 * @param drugsDepotPO
	 * @return
	 */
	DrugsDepotPO addDrugsDepotOfDefault(String drugName,String unit,String doctorId);


	/**
	 * 加载患者用药列表
	 * @param memberId
	 * @return
	 */
	PageResult<DrugsMemberVO> listDrugsMemberPage(String memberId, String doctorId,
												  String teamId,Integer dType,
												  PageRequest page,String drugType,
												  String origin);


	/**
	 * 新增患者用药方案
	 * @param memberId
	 * @return
	 */
	void addDrugsMember(String memberId,String doctorId, String drugType,String schemeName,String drugJson,
						  String startDt,String endDt ,String teamId,String oldSchemeId);


	/**
	 * 获取患者最新一条用药方案
	 * @param memberId
	 */
	MemberDrugItemPO getDrugsMemberNew(String memberId, String doctorId);

	/**
	 * 获取患者用药
	 * @param
	 * @return
	 */
	MemberDrugItemPO getMemberDrugItem(String id);

	/**
	 * 患者近两个月用药
	 * @param memberId
	 * @param startDt
	 * @param endDt
	 * @return
	 */
    List<DrugsMemberVO> listDrugsNearlyTwoMonthsOfMember(String memberId, String startDt, String endDt,Integer dType,String drugType);
}
