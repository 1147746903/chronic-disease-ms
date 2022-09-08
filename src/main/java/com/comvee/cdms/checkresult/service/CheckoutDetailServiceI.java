package com.comvee.cdms.checkresult.service;

import com.comvee.cdms.checkresult.dto.*;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.vo.CheckoutDetailVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.dto.DoctorKeyNoteDTO;
import com.comvee.cdms.doctor.dto.ListSelectedKeyNoteDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface CheckoutDetailServiceI {

	/**
	 * 根据检验id和memberid，获取检验详细信息
	 * @author 李左河
	 * @date 2018年3月22日 上午9:42:36
	 * @param checkoutId
	 * @param memberId
	 * @param hospitalId
	 * @param reportDate
	 * @return
	 */
	Map<String, Object> getCheckoutDetailAll(String checkoutId, String memberId, String reportDate,String hospitalId);

	/**
	 * 根据检验id，获取检验详情
	 * @param checkoutId
	 * @return
	 * 李左河
	 */
	List<CheckoutDetailVO> listCheckoutDetailByCheckoutId(String checkoutId,String hospitalId);

	/**
	 * 血脂达标率统计数据源（根据code获取血脂列表）-医院
	 * @param dto
	 * @return
	 */
    List<CheckoutDetailPO> listFatByCodeOfStatistics(GetStatisticsDTO dto);

	/**
	 * 根据主表检验编号删除详情
	 * @param checkoutId
	 */
	void deleteCheckoutDetailByCheckoutId(String checkoutId,String hospitalId);

	/**
	 * 获取患者在院期间的（糖化血红蛋白HbA1C，低密度脂蛋白胆固醇LDL-C）检验
	 * @param memberId
	 * @return
	 */
    List<CheckoutDetailPO> listCheckoutDetailByMemberIdOfInHos(String memberId);

	/*************************************************邪恶的分割线******************************************************
	 * @version v6.0.0
	 * @author: linyd
	 * @date: 2020/03/16
	 *****************************************************************************************************************/
	/**
	 * 获取患者历史某种检验结果列表
	 * @param listCheckResultDTO
	 */
	List<CheckoutDetailVO> listMemberHistoryCheckResult(ListCheckResultDTO listCheckResultDTO);

	/**
	 * 获取患者近六个月的检验详情列表
	 * @param dto
	 *
	 */
	List<CheckoutDetailVO> listCheckoutDetailNearlySixMonths(ListCheckoutDetailNearlySixMonthsDTO dto);

	/**
	 * 封装历史检验图表数据(数据源必须为升序排序)
	 * @param checkoutDetailVOS
	 * @return
	 */
    List<CheckoutDetailVO> getCheckoutDetailChartList(List<CheckoutDetailVO> checkoutDetailVOS);

	/**
	 * 获取检验项目列表（根据名称分组&医生关注项目）
	 * @return
	 */
	PageResult<CheckoutDetailPO> pagerCheckoutDetailGroupByNameOfDoctor(ListSelectedKeyNoteDTO dto, PageRequest pager,String startDt,String endDt);

	/**
	 * 分页获取历史患者某项检验记录
	 * @param listCheckResultDTO
	 * @param pager
	 * @return
	 */
    PageResult<CheckoutDetailVO> pagerMemberHistoryCheckResult(ListCheckResultDTO listCheckResultDTO, PageRequest pager);

	/**
	 * 使清除存注解生效--同一个缓存区域只能在同一个类里面才会生效
	 * @param dto
	 */
    void saveDoctorKeyNotes(DoctorKeyNoteDTO dto);

	/**
	 * 使清除存注解生效--同一个缓存区域只能在同一个类里面才会生效
	 * @param dto
	 * @param doctorPO
	 */
	void addCheckout(AddCheckoutDTO dto, DoctorPO doctorPO);

	void addCheckoutBatch(BatchAddCheckoutDTO batchDto, DoctorPO doctorPO);


	/**
	 * 获取最新的检查项目
	 * @param getNewestCheckoutDetailDTO
	 * @return
	 */
	CheckoutDetailPO getNewestCheckoutDetail(GetNewestCheckoutDetailDTO getNewestCheckoutDetailDTO);

	/**
	 * 默认检查项数据
	 * @param memberId
	 * @param hospitalId
	 * @return
	 */
	Map<String,Object> loadMemberDefaultCheckoutList(GetMemberCheckoutDTO getMemberCheckoutDTO);

}
