package com.comvee.cdms.follow.mapper;

import com.comvee.cdms.follow.po.FollowupCarePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/4/15
 */
public interface FollowupCareMapper {

    /**
     * 添加随访关怀
     * @param followupCarePO
     */
    void addFollowupCare(FollowupCarePO followupCarePO);

    /**
     * 修改随访关怀
     * @param followupCarePO
     */
    void updateFollowupCare(FollowupCarePO followupCarePO);

    /**
     * 加载患者的随访关怀
     * @param memberId
     * @param doctorId
     * @return
     */
    List<FollowupCarePO> listMemberFollowupCare(@Param("memberId")String memberId ,@Param("doctorId") String doctorId);

    /**
     * 加载待发送的随访关怀
     * @return
     */
    List<FollowupCarePO> listToBeSendFollowupCare();

    /**
     * 根据id获取随访关怀
     * @param sid
     * @return
     */
    FollowupCarePO getFollowupCareById(@Param("sid") String sid);

}
