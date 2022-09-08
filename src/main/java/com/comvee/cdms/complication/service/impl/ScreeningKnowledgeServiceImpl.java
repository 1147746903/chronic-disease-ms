package com.comvee.cdms.complication.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.mapper.ScreeningKnowledgeMapper;
import com.comvee.cdms.complication.model.dto.AddScreeningKnowledgeDTO;
import com.comvee.cdms.complication.model.po.ScreeningKnowledgePO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgeDetailPO;
import com.comvee.cdms.complication.model.po.ScreeningMemberKnowledgePO;
import com.comvee.cdms.complication.service.ScreeningKnowledgeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/7
 */
@Service("screeningKnowlegeService")
public class ScreeningKnowledgeServiceImpl implements ScreeningKnowledgeService {

    @Autowired
    private ScreeningKnowledgeMapper screeningKnowledgeMapper;

    @Override
    public ScreeningKnowledgePO getScreeningKnowledge(String sid, Integer serialNumber) {
        return this.screeningKnowledgeMapper.getScreeningKnowledge(sid ,serialNumber);
    }

    @Override
    public ScreeningMemberKnowledgePO getScreeningMemberKnowledgeByMemberId(String memberId) {
        return this.screeningKnowledgeMapper.getScreeningMemberKnowledgeByMemberId(memberId);
    }

    @Override
    public void addScreeningMemberKnowledge(ScreeningMemberKnowledgePO screeningMemberKnowledgePO) {
        screeningMemberKnowledgePO.setSid(DaoHelper.getSeq());
        this.screeningKnowledgeMapper.addScreeningMemberKnowledge(screeningMemberKnowledgePO);
    }

    @Override
    public void addScreeningMemberKnowledgeDetail(ScreeningMemberKnowledgeDetailPO screeningMemberKnowledgeDetailPO) {
        screeningMemberKnowledgeDetailPO.setSid(DaoHelper.getSeq());
        this.screeningKnowledgeMapper.addScreeningMemberKnowledgeDetail(screeningMemberKnowledgeDetailPO);
    }

    @Override
    public ScreeningKnowledgePO getNextScreeningKnowledge(Integer serialNumber) {
        return this.screeningKnowledgeMapper.getNextScreeningKnowledge(serialNumber);
    }

    @Override
    public PageResult<ScreeningMemberKnowledgePO> listScreeningKnowledgePushMember(int page, int rows) {
        PageHelper.startPage(page ,rows);
        List<ScreeningMemberKnowledgePO> list = this.screeningKnowledgeMapper.listScreeningKnowledgePushMember();
        return new PageResult<>(list);
    }

    @Override
    public void updateScreeningMemberKnowledge(ScreeningMemberKnowledgePO screeningMemberKnowledgePO) {
        this.screeningKnowledgeMapper.updateScreeningMemberKnowledge(screeningMemberKnowledgePO);
    }

    @Override
    public PageResult<ScreeningKnowledgePO> listScreeningKnowledge(PageRequest pr, String keyword) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningKnowledgePO> list = this.screeningKnowledgeMapper.listScreeningKnowledge(keyword);
        return new PageResult<>(list);
    }

    @Override
    public String addScreeningKnowledge(AddScreeningKnowledgeDTO screeningKnowledgePO) {
        ScreeningKnowledgePO screeningKnowledge = new ScreeningKnowledgePO();
        BeanUtils.copyProperties(screeningKnowledge ,screeningKnowledgePO);
        screeningKnowledge.setSid(DaoHelper.getSeq());
        this.screeningKnowledgeMapper.addScreeningKnowledge(screeningKnowledge);
        return screeningKnowledge.getSid();
    }

    @Override
    public void updateScreeningKnowledge(ScreeningKnowledgePO screeningKnowledgePO) {
        this.screeningKnowledgeMapper.updateScreeningKnowledge(screeningKnowledgePO);
    }
}
