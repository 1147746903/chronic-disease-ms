package com.comvee.cdms.checkresult.service;

import com.comvee.cdms.checkresult.dto.AddCheckoutDTO;
import com.comvee.cdms.checkresult.dto.AddCheckoutWithDetailDTO;
import com.comvee.cdms.checkresult.dto.GetMemberCheckoutDTO;
import com.comvee.cdms.checkresult.dto.ModifyCheckoutDTO;
import com.comvee.cdms.checkresult.po.DataSyncTaskPO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.vo.CheckoutBloodFatVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface CheckoutServiceI {

	/**
	 * 获取检验结果列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutPO> listCheckout(String memberId);

	/**
	 * 通过sid，获取对象
	 * @param checkoutId
	 * @return
	 * 李左河
	 */
	CheckoutPO getCheckoutByCheckoutId(String checkoutId,String hospitalId);

	/**
	 * 通过checkoutCode，获取最新的检查结果
	 * @param paramMap
	 * @return
	 */
	String getNewestCheckoutResult(Map<String,Object> paramMap);

	/**
	 * 根据参数获取检验项目
	 * @param paramMap
	 * @return
	 */
	List<CheckoutPO> listCheckoutByParams(Map<String, Object> paramMap);

	/**
	 * 根据检验id列表，获取检验详情
	 * @param checkoutIds
	 * @return
	 * 李左河
	 */
	List<CheckoutDetailPO> listCheckoutDetailByParams(List<String> checkoutIds,String memberId,String hospitalId);

	/**
	 * 根据患者id，获取最新的检验
	 * @param memberId
	 * @return
	 * 李左河
	 */
	CheckoutPO getCheckoutNew(@Param("memberId") String memberId);

	/**
	 * 根据患者id，获取近一年的最新检验列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutPO> listCheckoutForNearlyYear(@Param("memberId") String memberId);

	/**
	 * 获取近6个月的最新检验列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutPO> listCheckoutNearlySixMonths(@Param("memberId") String memberId,@Param("hospitalId") String hospitalId);

	/**
	 * 添加检验
	 * @param dto
	 * @param doctorPO
	 */
    void addCheckout(AddCheckoutDTO dto, DoctorPO doctorPO);

	/**
	 * 删除检验
	 * @param checkoutId
	 */
	void deleteCheckout(String checkoutId);

	/**
	 * 修改检验信息（含详情）
	 * @param dto
	 */
	void modifyCheckout(ModifyCheckoutDTO dto);

	/**
	 * 获取患者最新的血脂检验结果
	 * @param memberId
	 * @return
	 */
	CheckoutBloodFatVO getMemberLatestBloodFat(String memberId);

	//检查列表一级列表
	PageResult<CheckoutPO> pagerMemberCheckoutWithNote(GetMemberCheckoutDTO dto,PageRequest pr);

	//检查列表二级列表
	PageResult<CheckoutDetailPO> pagerMemberCheckoutDetailByForeignId(DoctorSessionBO doctorSessionBO, String checkoutId,PageRequest pr);

	/**
	 * 添加检验
	 * @param add
	 * @return
	 */
	String addCheckout(AddCheckoutWithDetailDTO add);

	void resolveSyncMemberArchive(Map<String ,CheckoutDetailPO> detailMap ,String memberId);

	DataSyncTaskPO addDataSyncTask(DataSyncTaskPO dataSyncTaskPO);

	DataSyncTaskPO getDataSyncTask(DataSyncTaskPO dataSyncTaskPO);

	void updateDataSyncTask(DataSyncTaskPO dataSyncTaskPO);
}
