package com.comvee.cdms.clinicaldiagnosis.service;


import com.comvee.cdms.clinicaldiagnosis.constant.YzOperation;
import com.comvee.cdms.clinicaldiagnosis.dto.AddYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.ListYzRecordDTO;
import com.comvee.cdms.clinicaldiagnosis.po.YzPO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzAndDrugVO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzExecuteLogVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 李左河
 *
 */
public interface YzServiceI {

	/**
	 * 获取医嘱分页总记录数
	 * @param map
	 * @return
	 * 李左河
	 */
	Long getYzByPageCount(Map<String, Object> map);

	/**
	 * 获取医嘱分页数据
	 * @param map
	 * @return
	 */
	List<YzVO> listYzByPage(Map<String, Object> map);

	/**
	 * 保存医嘱
	 * @param yzPO
	 */
	void saveYz(AddYzDTO yzPO);

	/**
	 * 通过医嘱id，获取对象
	 * @param yzId
	 * @return
	 * 李左河
	 */
	YzVO getYzByYzId(String yzId);

	/**
	 * 通过memberId，获取医嘱信息
	 * @param memberId
	 * @return
	 */
	List<YzVO> getYzByMemberId(String memberId);

	/**
	 * 根据条件获取医嘱项
	 * @param paramMap
	 * @return
	 */
	List<YzVO> listYzByParams(Map<String, Object> paramMap);

	/**
	 * 根据主键获取医嘱
	 * @param sid
	 * @return
	 */
	YzVO getYzById(String sid);

	/**
	 * 根据患者id，获取用药分页记录
	 * @param page
	 * @param row
	 * @param memberId
	 * @return
	 * 李左河
	 */
	PageResult<MemberYzAndDrugVO> listDrugRecordByMemberId(String page, String row, String memberId);

	/**
	 * 根据患者id，获取医嘱分页记录
	 * @param page
	 * @param row
	 * @param dto
	 * @return
	 * 李左河
	 */
	PageResult<MemberYzAndDrugVO> listYzRecordByMemberId(String page, String row, ListYzRecordDTO dto);

	/**
	 *  根据患者id，获取最新用药记录
	 * @param paramMap
	 * @return
	 * 李左河
	 */
	List<YzVO> listYzDrugNewByMemberId(Map<String, Object> paramMap);

	/**
	 * 获取出院患者的用药医嘱
	 * @param memberId
	 * @return
	 */
	List<YzVO> listDrugYzOfOutHospitalMember(String memberId);

	/**
	 * 获取近两个月的用药记录
	 * @param paramMap
	 * @return
	 */
    List<YzVO> listYzDrugNearlyTwoMonths(Map<String, Object> paramMap);

	/**
	 * 添加患者用药医嘱
	 * @param drugJson
	 */
	void addMemberDrugYz(DoctorSessionBO doctor,String memberId, String visitNo, String priorityCode, String drugJson);

	/**
	 * 新开医嘱
	 * @param addYzDTO
	 */
	String addYz(AddYzDTO addYzDTO);

	/**
	 * 加载患者医嘱记录列表（分页）
	 * @param pr
	 * @param listMemberYzDTO
	 * @return
	 */
	PageResult<MemberYzListVO> listMemberYz(PageRequest pr ,ListMemberYzDTO listMemberYzDTO);

	/**
	 * 获取执行记录
	 * @param yzId
	 * @return
	 */
	YzExecuteLogVO getYzExecuteLogByYzId(String yzId);

	/**
	 *加载患者医嘱记录列表
	 * @param listMemberYzDTO
	 * @return
	 */
	List<MemberYzListVO> listMemberYz(ListMemberYzDTO listMemberYzDTO);

	/**
	 * 操作医嘱
	 * @param yzId
	 * @param yzOperation
	 * @param operatorId
	 */
	void operateYz(List<String> yzIdList ,YzOperation yzOperation ,String operatorId);

	/**
	 * 修改医嘱
	 * @param yzPO
	 */
	void updateYz(YzPO yzPO);

	/**
	 * 查询住院的患者医嘱
	 * @param dto
	 * @return
	 */
	List listMemberYzByMemberCheckInfo(ListMemberYzDTO dto);

	/**
	 * 查询患者未停止的泵剂量长期医嘱
	 * @param memberId
	 */
	List listYzByMemberIdAndTypeAndItemType(String memberId,List<Integer> yzStatus ,Integer yzItemType,Integer yzType,String yzItemCode,String hospitalId);

	/**
	 * 获取患者泵剂量医嘱
	 * @param memberId
	 * @return
	 */
	List<YzPO> getYzByMemberIdByYzItemType(String memberId,String date);

	/**
	 * 根据患者和医院获取泵剂量医嘱
	 * @param memberId
	 * @param hospitalId
	 */
	List<YzPO> listYzByMemberIdAndHospital(String memberId, String hospitalId,String inHospitalDt,String outHospitalDt,Integer yzItemType,List<Integer> yzStatus);

	List<YzPO> listInsulinPumpDoctorAdvice(String memberId, String hospitalId,Integer yzItemType);
}
