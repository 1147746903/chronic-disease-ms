package com.comvee.cdms.follow.service;

import com.comvee.cdms.follow.model.FollowDTO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.prescription.po.PrescriptionKnowledgePO;
import com.comvee.cdms.prescription.vo.eduplan.KnowledgeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/12/30 11:40
 */
public interface FollowOfEduService {

    /** v 4.2.1
     * 首诊下发课程推荐
     * @param archives
     * @param memberId
     * @return
     */
    KnowledgeVO intelligentFollowOfEdu(String archivesJson, String memberId, RangeBO rangeBO,Integer eohType);

    /**
     * 保存首诊学习计划
     * @param followDTO
     */
    void saveFollowEdu(FollowDTO followDTO);

    /** 4.2.1
     * 根据首诊id，获取知识文章列表
     *
     */
    List<PrescriptionKnowledgePO> listFollowKnowledge( String followId);

}
