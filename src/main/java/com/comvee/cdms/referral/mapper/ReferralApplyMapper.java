package com.comvee.cdms.referral.mapper;

import com.comvee.cdms.referral.dto.ListReferralApplyDTO;
import com.comvee.cdms.referral.po.ReferralApplyPO;
import com.comvee.cdms.referral.po.ReferralSuggestPO;
import com.comvee.cdms.referral.vo.H5ListReferralApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李左河
 * @date 2018/3/28 9:52.
 */
public interface ReferralApplyMapper {
    /**
     * 新增转诊记录
     * @author 李左河
     * @date 2018/3/28 14:54
     * @param referralApplyPO
     * @return void
     *
     */
    void insertReferralApply(ReferralApplyPO referralApplyPO);

    /**
     * 根据条件获取随访列表
     * @param dto
     * @return
     */
    List<ReferralApplyPO> listReferralByWhere(ListReferralApplyDTO dto);

    List<ReferralApplyPO> listDistinctMemberReferralByWhere(ListReferralApplyDTO dto);

    List<H5ListReferralApplyVO> listReferralByWhereForH5(ListReferralApplyDTO dto);

    /**
     * 根据主键获取随访详情
     * @param sid
     * @return
     */
    ReferralApplyPO getReferralById(String sid);

    /**
     * 修改随访
     * @param po
     */
    void modifyReferral(ReferralApplyPO po);

    /**根据请求条件，获取最新转诊信息
     *
     * @author 李左河
     * @date 2018/4/12 9:44
     * @param paramMap
     * @return com.comvee.cdms.referral.po.ReferralApplyPO
     *
     */
    ReferralApplyPO getReferralApplyNew(Map<String, Object> paramMap);

    /**
     * 通过memberId,获取转诊记录
     * @author 李左河
     * @date 2018/4/20 12:58
     * @param memberId
     * @return com.comvee.cdms.referral.model.ReferralApplyModel
     *
     */
    ReferralApplyPO getReferralApplyByMemberId(@Param("memberId") String memberId);

    /**
     * 建议转诊列表
     * @param hospitalId
     * @return
     */
    List<ReferralSuggestPO> listSuggestReferralByWhere(@Param("hospitalId") String hospitalId, @Param("referralType") Integer referralType);

    /**
     * 添加建议转诊记录
     * @param po
     */
    void addSuggestReferralLog(ReferralSuggestPO po);

    /**
     * 修改建议转诊记录
     * @param po
     */
    void modifySuggestReferralLog(ReferralSuggestPO po);

    /**
     * 修改建议转诊记录根据医院和患者编号
     * @param po
     */
    void modifySuggestLogByMIdAndHId(ReferralSuggestPO po);

    /**
     * 批量添加建议转诊
     * @param inserts
     */
    void batchInsertSuggestReferral(@Param("inserts") List<ReferralSuggestPO> inserts);

    /**
     * 建议转诊列表
     * @param hospitalId
     * @param memberId
     * @return
     */
    List<ReferralSuggestPO> listSuggestReferralOfInHos(@Param("hospitalId") String hospitalId, @Param("memberId") String memberId);
}
