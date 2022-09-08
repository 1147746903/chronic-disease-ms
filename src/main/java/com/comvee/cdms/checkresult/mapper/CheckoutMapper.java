package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface CheckoutMapper {

	/**
	 * 获取检验结果列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutPO> listCheckout(@Param("memberId") String memberId);

	/**
	 * 通过checkoutId，获取对象
	 * @param checkoutId
	 * @return
	 * 李左河
	 */
	CheckoutPO getCheckoutByCheckoutId(@Param("checkoutId") String checkoutId,@Param("hospitalId")String hospitalId);
	
	/**
	 * 保存checkoutPO
	 * @param checkoutPO
	 */
	void saveCheckout(CheckoutPO checkoutPO);

	/**
	 * 修改checkoutPO
	 * @param checkoutPO
	 */
	void modifyCheckoutByCheckoutId(CheckoutPO checkoutPO);

	/**
	 * 通过checkoutId，获取List<CheckoutDetailPO>
	 * @param checkoutId
	 * @return
	 */
	List<CheckoutDetailPO> getCheckoutDetailByCheckoutId(@Param("checkoutId") String checkoutId);

	/**
	 * 保存checkoutDetailPO
	 * @param checkoutDetailPO
	 */
	void saveCheckoutDetail(CheckoutDetailPO checkoutDetailPO);

	/**
	 * 通过checkoutCode和患者id，获取最新的检查结果
	 * @param paramMap
	 * @return
	 */
	String getNewestCheckoutResult(Map<String,Object> paramMap);

	/**
	 * 根据条件获取患者检验项目
	 * @param paramMap
	 * @return
	 */
    List<CheckoutPO> listCheckoutByParams(Map<String, Object> paramMap);

    /**
     * 根据检验参数，获取检验详情
     * @param checkoutIds
     * @return
     * 李左河
     */
	List<CheckoutDetailPO> listCheckoutDetailByParams(@Param("checkoutIds") List<String> checkoutIds,@Param("memberId") String memberId,@Param("hospitalId") String hospitalId);

	/**
	 * 根据患者id，获取最新的检验
	 * @param memberId
	 * @return
	 * 李左河
	 */
	CheckoutPO getCheckoutNew(String memberId);

	/**
	 * 根据患者id，获取近一年的最新检验列表
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<CheckoutPO> listCheckoutNew(String memberId);

	/**
	 * 根据患者id，获取近6个月的最新检验列表
	 * @param memberId
	 * @return
	 */
	List<CheckoutPO> listCheckoutNearlySixMonths(@Param("memberId") String memberId,@Param("hospitalId") String hospitalId);

	List<CheckoutPO> pagerMemberCheckoutWithNote(@Param("memberId") String memberId,
												 @Param("list") List<KeyNoteModel> list,
												 @Param("startDt") String startDt,
												 @Param("endDt") String endDt,
												 @Param("hospitalId") String hospitalId,
												 @Param("keyNoteModel")KeyNoteModel keyNoteModel);

	/**
	 * 新增或者修改检验单数据
	 * @param checkout
	 */
	void addOrUpdateCheckout(CheckoutPO checkout);
}
