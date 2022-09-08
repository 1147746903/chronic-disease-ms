package com.comvee.cdms.checkresult.service;

import com.comvee.cdms.checkresult.dto.AddInspectionDTO;
import com.comvee.cdms.checkresult.po.InspectionPO;

import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public interface InspectionServiceI {

	/**
	 * 获取检查结果列表 
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<InspectionPO> listInspection(String memberId);

	/**
	 * 通过sid获取对象
	 * @param inspectId
	 * @return
	 * 李左河
	 */
	InspectionPO getInspectionByInspectId(String inspectId,String hospitalId);

	/**
	 * 获取检查结果列表-医院
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<InspectionPO> listInspectionByHospital(String memberId,String hospitalId);

	/**
	 * 新增检查记录
	 * @param add
	 */
	String addInspection(AddInspectionDTO add);

	void modifyInspection(InspectionPO po);
}
