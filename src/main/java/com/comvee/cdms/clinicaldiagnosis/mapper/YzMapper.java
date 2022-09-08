package com.comvee.cdms.clinicaldiagnosis.mapper;

import com.comvee.cdms.clinicaldiagnosis.dto.ListMemberYzDTO;
import com.comvee.cdms.clinicaldiagnosis.dto.ListYzRecordDTO;
import com.comvee.cdms.clinicaldiagnosis.po.YzPO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzAndDrugVO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzExecuteLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 李左河
 *
 */
public interface YzMapper {

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
	 * 李左河
	 */
	List<YzPO> listYzByPage(Map<String, Object> map);

	/**
	 * 保存
	 * @param yzPO
	 */
	void saveYz(YzPO yzPO);

	/**
	 * 通过医嘱id，获取对象
	 * @param yzId
	 * @return
	 * 李左河
	 */
	YzPO getYzByYzId(String yzId);

	/**
	 * 通过memberId，获取医嘱信息
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<YzPO> getYzByMemberId(@Param("memberId") String memberId);

	/**
	 * 根据条件获取医嘱项
	 * @param paramMap
	 * @return
	 */
    List<YzPO> listYzByParams(Map<String, Object> paramMap);

	/**
	 * 根据主键获取医嘱
	 * @param sid
	 * @return
	 */
	YzPO getYzById(String sid);

	/**
	 * 根据患者id，获取用药记录
	 * @param memberId
	 * @return
	 * 李左河
	 */
	List<MemberYzAndDrugVO> listDrugRecordByMemberId(String memberId);

	/**
	 * 获取医嘱记录
	 * @param dto
	 * @return
	 * 李左河
	 */
	List<MemberYzAndDrugVO> listYzRecordByMemberId(ListYzRecordDTO dto);

	/**
	 * 根据患者id，获取最新用药记录
	 * @param paramMap
	 * @return
	 * 李左河
	 */
	List<YzPO> listYzDrugNewByMemberId(Map<String, Object> paramMap);

	/**
	 * 获取出院患者的用药医嘱
	 * @param memberId
	 * @return
	 */
	List<YzPO> listDrugYzOfOutHospitalMember(String memberId);

	/**
	 * 根据患者id，获取患者最近一次用药医嘱的日期
	 * @author 李左河
	 * @date 2018/7/23 12:46
	 * @param memberId
	 * @return java.lang.String
	 *
	 */
	String getYzStartDateNewByMemberId(@Param("memberId") String memberId);

	/**
	 * 获取近两个月的用药记录
	 * @param paramMap
	 * @return
	 */
	List<YzPO> listYzDrugNearlyTwoMonths(Map<String, Object> paramMap);

	/**
	 * 加载患者医嘱列表
	 * @param listMemberYzDTO
	 * @return
	 */
	List<MemberYzListVO> listMemberYz(ListMemberYzDTO listMemberYzDTO);

	/**
	 * 获取执行记录
	 * @param yzId
	 * @return
	 */
	YzExecuteLogVO getYzExecuteLogByYzId(@Param("yzId") String yzId);

	/**
	 * 修改医嘱
	 * @param yzPO
	 * @return
	 */
	int updateYz(YzPO yzPO);

	/**
	 * 查询住院患者的医嘱
	 * @param dto
	 */
	List<MemberYzListVO> listMemberYzByMemberCheckInfo(ListMemberYzDTO dto);

	/**
	 * 查询患者未停止的泵剂量长期医嘱
	 * @param memberId
	 */
	List<YzPO> listYzByMemberIdAndTypeAndItemType(@Param("memberId") String memberId,@Param("yzStatus")List<Integer> yzStatus,@Param("yzItemType")Integer yzItemType,@Param("yzType")Integer yzType,@Param("yzItemCode")String yzItemCode,@Param("hospitalId")String hospitalId);

	List<YzPO> getYzByMemberIdByYzItemType(@Param("memberId") String memberId,@Param("date") String date,@Param("yzStatus")List<Integer> yzStatus,@Param("yzItemType")Integer yzItemType);

	List<YzPO> listYzByMemberIdAndHospital(@Param("memberId") String memberId, @Param("hospitalId") String hospitalId,@Param("inHospitalDt") String inHospitalDt,@Param("outHospitalDt") String outHospitalDt,@Param("yzItemType")Integer yzItemType,@Param("yzStatus")List<Integer> yzStatus );

	/**
	 * 回去患者当前医院泵剂量有关的医嘱
	 * @param memberId
	 * @param hospitalId
	 * @param yzItemType
	 * @return
	 */
	List<YzPO> listInsulinPumpDoctorAdvice(@Param("memberId") String memberId, @Param("hospitalId") String hospitalId, @Param("yzItemType") Integer yzItemType);

	/**
	 * 获取患者所在医院的全部医嘱
	 */
	List<YzPO> getYzByMemberIdAndHospitalId(@Param("memberId") String memberId, @Param("hospitalId") String hospitalId);

}
