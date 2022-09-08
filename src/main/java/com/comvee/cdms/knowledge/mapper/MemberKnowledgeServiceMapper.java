package com.comvee.cdms.knowledge.mapper;

import com.comvee.cdms.knowledge.po.EohKnowledgePO;
import com.comvee.cdms.knowledge.po.PrescriptionKnowledgePlanPO;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李左河
 * @date 2018/5/30 10:37.
 */
public interface MemberKnowledgeServiceMapper {

    /**
     * 获取需要生成知识模块计划的列表
     * @author 李左河
     * @date 2018/5/30 10:47
     * @param paramMap
     * @return java.util.List<com.comvee.cdms.eoh.model.EohKnowledgePO>
     *
     */
    List<EohKnowledgePO> listMemberKnowledge(Map<String, Object> paramMap);

    /**
     * 批量新增学习计划推送任务
     * @author 李左河
     * @date 2018/5/30 11:08
     * @param konwledgePlanList
     * @return void
     *
     */
    void batchAddKnowledgePlan(List<PrescriptionKnowledgePlanPO> konwledgePlanList);

    /**
     * 获取知识计划推送列表
     * @author 李左河
     * @date 2018/5/30 11:31
     * @param paramMap
     * @return java.util.List<com.comvee.cdms.eoh.model.EohKnowledgePlanPO>
     *
     */
    List<PrescriptionKnowledgePlanPO> listMemberKnowledgePlan(Map<String, Object> paramMap);

    /**
     * 知识计划已推送，修改状态
     * @author 李左河
     * @date 2018/5/30 11:52
     * @param sid
     * @return void
     *
     */
    void modifyEohKnowledgePlan(@Param("sid") String sid);

    /**
     * 加载昨日患者最新的管理处方
     * @param startTime
     * @param endTime
     * @return
     */
    List<PrescriptionPO> listMemberLatestPrescription(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 清除患者管理处方知识学习计划
     * @param memberId
     */
    void clearMemberKnowledgePlan(@Param("memberId") String memberId);
    
    /**
     * 修改只是学习的has_learned字段
     * @param sid
     */
    void modifyEohKnowledgeHasLearned(@Param("id") String id);
    
}
