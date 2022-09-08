package com.comvee.cdms.referral.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.referral.dto.*;
import com.comvee.cdms.referral.vo.H5ListReferralApplyVO;
import com.comvee.cdms.referral.vo.ReferralApplyVO;
import com.comvee.cdms.referral.vo.ReferralSuggestVO;

import java.util.Map;

/**
 * @author 李左河
 * @date 2018/3/28 9:49.
 */
public interface ReferralApplyServiceI {
    /**
     * 新增转诊记录
     * @author 李左河
     * @date 2018/3/28 9:51
     * @param referralApplyDTO
     * @return map
     *
     */
    Map<String,Object> insertReferralApplyWithLock(AddReferralApplyDTO referralApplyDTO);

    /**
     * 根据主键获取随访详情
     * @param sid
     * @return
     */
    ReferralApplyVO getReferralById(String sid);

    /**
     * 修改转诊
     * @param po
     */
    void modifyReferral(ModifyReferralApplyDTO po);

    /**
     * 接收转诊
     * @param sid
     * @param memberId
     * @param doctor
     */
    void receiveReferralWithLock(String sid, String memberId, DoctorSessionBO doctor);

    /**
     * 获取分页转诊记录
     * @param dto
     * @param pager
     * @return
     */
    PageResult<ReferralApplyVO> listReferralPage(ListReferralApplyDTO dto, PageRequest pager);

    PageResult<H5ListReferralApplyVO> listReferralPageForH5(ListReferralApplyDTO dto, PageRequest pager);

    /**
     * 建议转诊患者分页列表
     * @param hospitalId
     * @param pager
     * @return
     */
    PageResult<ReferralSuggestVO> listSuggestReferral(String hospitalId, PageRequest pager);

    /**
     * 忽略建议转诊
     * @param sid
     */
    void hlSuggestReferralLog(String sid);

    /**
     * 修改建议转诊
     * @param po
     */
    void modifySuggestReferralLog(ModifyReferralSuggestDTO dto);

}
