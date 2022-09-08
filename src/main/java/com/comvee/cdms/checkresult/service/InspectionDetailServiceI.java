package com.comvee.cdms.checkresult.service;


import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.checkresult.vo.CheckoutAbnormalVO;
import com.comvee.cdms.checkresult.vo.InspectionDetailVO;
import com.comvee.cdms.checkresult.vo.MemberInspectionVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface InspectionDetailServiceI {

	/**
	 * 根据检查id（inspectId）和memberId，获取详细检查数据
	 * @param inspectId
	 * @param memberId
	 * @return
	 * 李左河
	 */
	Map<String, Object> getInspectionDetailAll(String inspectId, String memberId,String hospitalId);

	/**
	 * 获取患者近一年的检验异常列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutAbnormalVO> listCheckoutAbnormalListByMemberId(String memberId);

    PageResult<InspectionPO> listInspectionPageByMemberId(String memberId, PageRequest pr);

	PageResult<CheckoutPO> listCheckoutPageByMemberId(String memberId, PageRequest pr);

	/**
	 * 获取患者近6个月所有类型的检查
	 * @param memberId
	 * @param hospitalId
	 * @return
	 */
	List<MemberInspectionVO> listInspectionReportNearlySixMonths(String memberId, String hospitalId,String startDt);
}
