package com.comvee.cdms.follow.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.po.FollowupCarePO;

/**
 * @author: suyz
 * @date: 2019/7/24
 */
public interface FollowupCareService {

    /**
     * 添加随访关怀
     * @param followupCarePO
     * @return
     */
    String addFollowupCare(FollowupCarePO followupCarePO);

    /**
     * 修改随访关怀
     * @param followupCarePO
     */
    void updateFollowupCare(FollowupCarePO followupCarePO);

    /**
     * 删除随访关怀
     * @param sid
     */
    void deleteFollowupCare(String sid);

    /**
     * 加载患者的随访关怀列表
     * @param pr
     * @param memberId
     * @param doctorId
     * @return
     */
    PageResult<FollowupCarePO> listMemberFollowupCare(PageRequest pr ,String memberId ,String doctorId);

    /**
     * 发送随访关怀到微信
     * @param followupCarePO
     */
    void sendFollowupCareToWechat(FollowupCarePO followupCarePO);

    /**
     * 加载待发送的随访关怀
     * @param page
     * @param rows
     * @return
     */
    PageResult<FollowupCarePO> listToBeSendFollowupCare(int page ,int rows);

    /**
     * 根据id获取随访关怀
     * @param sid
     * @return
     */
    FollowupCarePO getFollowupCareById(String sid);
}
