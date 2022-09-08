package com.comvee.cdms.checkresult.mapper;

import com.comvee.cdms.checkresult.dto.GetNewestCheckoutDetailDTO;
import com.comvee.cdms.checkresult.dto.ListCheckResultDTO;
import com.comvee.cdms.checkresult.dto.ListMemberNewestCheckoutDetailDTO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.doctor.model.KeyNoteModel;
import com.comvee.cdms.statistics.dto.GetStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface CheckoutDetailMapper {

	/**
	 * 根据checkoutId，获取对象信息
	 * @param checkoutId
	 * @return
	 */
	List<CheckoutDetailPO> listCheckoutDetail(@Param("checkoutId") String checkoutId,@Param("hospitalId")String hospitalId);

	/**
	 * 根据code，获取
	 * @param dto
	 * @return
	 */
    List<CheckoutDetailPO> listFatByCodeOfStatistics(GetStatisticsDTO dto);

	/**
	 * 根据对象获取
	 * @param checkoutDetailPO
	 * @return
	 */
	List<String> loadCheckoutDetailMemberIdList(Map<String, Object> param);

	/**
	 * 根据主表检验编号删除详情
	 * @param checkoutId
	 */
	void deleteCheckoutDetailByCheckoutId(@Param("checkoutId") String checkoutId,@Param("hospitalId")String hospitalId);

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
    List<CheckoutDetailPO> listMemberHistoryCheckResult(ListCheckResultDTO listCheckResultDTO);

	/**
	 * 获取患者历史某种检验结果列表(名称分组&医生查看数据设置）
	 * @param memberId
	 * @param list
	 * @return
	 */
    List<CheckoutDetailPO> listCheckoutDetailGroupByNameByCodeOrNames(@Param("memberId") String memberId,
																	  @Param("list") List<KeyNoteModel> list,
																	  @Param("startDt") String startDt,
																	  @Param("endDt") String endDt,
																	  @Param("hospitalId") String hospitalId,
																	  @Param("keyNoteModel")KeyNoteModel keyNoteModel);
	/**
	 * 获取患者历史某种检验结果列表
	 * @param memberId
	 * @param list
	 * @return
	 */
	List<CheckoutDetailPO> listCheckoutDetailByNameCode(@Param("memberId") String memberId,
																	  @Param("list") List<KeyNoteModel> list,
																	  @Param("startDt") String startDt,
																	  @Param("endDt") String endDt,
																	  @Param("hospitalId") String hospitalId);


	/**
	 * 获取最新的检查项目
	 * @param getNewestCheckoutDetailDTO
	 * @return
	 */
	CheckoutDetailPO getNewestCheckoutDetail(GetNewestCheckoutDetailDTO getNewestCheckoutDetailDTO);

	/**
	 * 加载患者最新的检查项目列表
	 * @param dto
	 * @return
	 */
	List<CheckoutDetailPO> listMemberNewestCheckoutDetail(ListMemberNewestCheckoutDetailDTO dto);

	int batchAddOrUpdateCheckoutDetail(@Param("list") List<CheckoutDetailPO> list);

}
