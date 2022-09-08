package com.comvee.cdms.complication.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.dto.AddScreeningKnowledgeDTO;
import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgeDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgePO;

/**
 * @author: suyz
 * @date: 2019/3/7
 */
public interface ScreeningKnowledgeService {

    /**
     * 获取筛查知识文章内容
     * @param sid
     * @param serialNumber
     * @return
     */
    ScreeningKnowledgePO getScreeningKnowledge( String sid , Integer serialNumber );

    /**
     * 获取患者筛查知识学习情况
     * @param memberId
     * @return
     */
    ScreeningMemberKnowledgePO getScreeningMemberKnowledgeByMemberId(String memberId);

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
    ScreeningKnowledgePO getNextScreeningKnowledge(Integer serialNumber);

    /**
     * 加载需要筛查知识推送的患者
     * @param page
     * @param rows
     * @return
     */
    PageResult<ScreeningMemberKnowledgePO> listScreeningKnowledgePushMember(int page ,int rows);

    /**
     * 修改患者筛查知识学习情况
     * @param screeningMemberKnowledgePO
     */
    void updateScreeningMemberKnowledge(ScreeningMemberKnowledgePO screeningMemberKnowledgePO);

    /**
     * 加载筛查知识文章列表
     * @param pr
     * @param keyword
     * @return
     */
    PageResult<ScreeningKnowledgePO> listScreeningKnowledge(PageRequest pr ,String keyword);

    /**
     * 新增筛查知识学习
     * @param screeningKnowledgePO
     * @return
     */
    String addScreeningKnowledge(AddScreeningKnowledgeDTO screeningKnowledgePO);

    /**
     * 修改筛查知识学习
     * @param screeningKnowledgePO
     */
    void updateScreeningKnowledge(ScreeningKnowledgePO screeningKnowledgePO);
}
