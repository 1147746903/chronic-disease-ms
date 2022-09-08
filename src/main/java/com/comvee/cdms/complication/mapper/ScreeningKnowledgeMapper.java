package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgeDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/7
 */
public interface ScreeningKnowledgeMapper {

    /**
     * 获取筛查知识文章内容
     * @param sid
     * @param serialNumber
     * @return
     */
    ScreeningKnowledgePO getScreeningKnowledge(@Param("sid") String sid , @Param("serialNumber")Integer serialNumber );

    /**
     * 获取患者筛查知识学习情况
     * @param memberId
     * @return
     */
    ScreeningMemberKnowledgePO getScreeningMemberKnowledgeByMemberId(@Param("memberId") String memberId);

    /**
     * 添加患者筛查知识学习情况
     * @param screeningMemberKnowledgePO
     */
    void addScreeningMemberKnowledge(ScreeningMemberKnowledgePO screeningMemberKnowledgePO);

    /**
     * 添加筛查知识学习情况详情
     * @param screeningMemberKnowledgeDetailPO
     */
    void addScreeningMemberKnowledgeDetail(ScreeningMemberKnowledgeDetailPO screeningMemberKnowledgeDetailPO);

    /**
     * 根据序号获取下一篇文章
     * @param serialNumber
     * @return
     */
    ScreeningKnowledgePO getNextScreeningKnowledge(@Param("serialNumber")Integer serialNumber);

    /**
     * 加载需要筛查知识推送的患者
     * @return
     */
    List<ScreeningMemberKnowledgePO> listScreeningKnowledgePushMember();

    /**
     * 修改患者筛查知识学习情况
     * @param screeningMemberKnowledgePO
     */
    void updateScreeningMemberKnowledge(ScreeningMemberKnowledgePO screeningMemberKnowledgePO);

    /**
     * 加载筛查知识学习库
     * @param keyword
     * @return
     */
    List<ScreeningKnowledgePO> listScreeningKnowledge(@Param("keyword") String keyword);

    /**
     * 新增筛查知识学习
     * @param screeningKnowledgePO
     */
    void addScreeningKnowledge(ScreeningKnowledgePO screeningKnowledgePO);

    /**
     * 修改筛查知识学习
     * @param screeningKnowledgePO
     */
    void updateScreeningKnowledge(ScreeningKnowledgePO screeningKnowledgePO);


}
