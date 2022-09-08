package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.dto.GetInspectionDTO;
import com.comvee.cdms.checkresult.po.InspectionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
@Mapper
public interface InspectionMapper {

	/**
	 * 获取检查结果列表 
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<InspectionPO> listInspection(@Param("memberId") String memberId);

	/**
	 * 通过inspectId获取对象
	 * @param inspectId
	 * @return
	 * 李左河
	 */
	InspectionPO getInspectionByInspectId(@Param("inspectId") String inspectId);

	/**
	 * 通过inspectId，修改对象
	 * @param inspectionPO
	 */
	void modifyInspection(InspectionPO inspectionPO);

	/**
	 * 保存
	 * @param inspectionPO
	 * 李左河
	 */
	void addInspection(InspectionPO inspectionPO);

	/**
	 * 根据条件获取检查项目
	 * @param paramMap
	 * @return
	 */
	List<InspectionPO> listInspectionByParams(Map<String, Object> paramMap);

	InspectionPO getInspection(GetInspectionDTO getInspectionDTO);
}
