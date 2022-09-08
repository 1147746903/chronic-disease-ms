package com.comvee.cdms.questionnaire.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.po.MainFollowPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.questionnaire.constant.QuestionnaireConstant;
import com.comvee.cdms.questionnaire.mapper.QuestionnaireMapper;
import com.comvee.cdms.questionnaire.model.dto.*;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;
import com.comvee.cdms.questionnaire.service.QuestionnaireService;
import com.comvee.cdms.questionnaire.tool.AssessQuestionnaireTool;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/14
 */
@Service("questionnaireService")
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private FollowServiceI followService;
    @Autowired
    private DoctorServiceI doctorServiceI;

    @Override
    public PageResult<QuestionnairePO> listQuestionnaire(ListQuestionnaireDTO listQuestionnaireDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<QuestionnairePO> list = this.questionnaireMapper.listQuestionnaire(listQuestionnaireDTO);
        return new PageResult<>(list);
    }

    @Override
    public QuestionnairePO getQuestionnaireById(String sid) {
        return this.questionnaireMapper.getQuestionnaireById(sid);
    }

    @Override
    public String addQuestionnaire(AddQuestionnaireDTO addQuestionnaireDTO) {
        String sid = DaoHelper.getSeq();
        QuestionnairePO questionnairePO = new QuestionnairePO();
        BeanUtils.copyProperties(questionnairePO ,addQuestionnaireDTO);
        questionnairePO.setQuestionName(QuestionnaireConstant.questionnaireName(addQuestionnaireDTO.getQuestionType()));
        questionnairePO.setSid(sid);
        if (addQuestionnaireDTO.getCreateType() == 1){
            questionnairePO.setFollowContentId("-1");
        }
        this.questionnaireMapper.addQuestionnaire(questionnairePO);
        return sid;
    }

    @Override
    public void saveQuestionnaire(SaveQuestionnaireDTO saveQuestionnaireDTO) {
        QuestionnairePO questionnairePO = new QuestionnairePO();
        BeanUtils.copyProperties(questionnairePO ,saveQuestionnaireDTO);
        questionnairePO.setQuestionStatus(QuestionnaireConstant.QUESTIONNAIRE_STATUS_DRAFT);
        updateQuestionnaire(questionnairePO);
    }

    @Override
    public void commitQuestionnaire(CommitQuestionnaireDTO commitQuestionnaireDTO) {
        QuestionnairePO questionnairePO = new QuestionnairePO();
        BeanUtils.copyProperties(questionnairePO ,commitQuestionnaireDTO);
//        if (commitQuestionnaireDTO.getQuestionStatus() == 4){
//            questionnairePO.setQuestionStatus(2);  //自定义随访中的问卷用到
//        }else {
//            questionnairePO.setQuestionStatus(QuestionnaireConstant.QUESTIONNAIRE_STATUS_FINISH);
//        }
        questionnairePO.setQuestionStatus(QuestionnaireConstant.QUESTIONNAIRE_STATUS_FINISH);
        questionnairePO.setCommitDt(DateHelper.getNowDate());
        updateQuestionnaire(questionnairePO);
    }

    @Override
    public JSONObject assessQuestionnaire(AssessQuestionnaireDTO assessQuestionnaireDTO) {
        return AssessQuestionnaireTool.assessQuestionnaire(assessQuestionnaireDTO.getContentJson() , assessQuestionnaireDTO.getQuestionType());
    }

    @Override
    public void deleteQuestionnaire(String sid) {
        QuestionnairePO questionnairePO = getQuestionnaireById(sid);
        if(questionnairePO == null){
            throw new BusinessException("问卷不存在，请确认~");
        }
        if(QuestionnaireConstant.QUESTIONNAIRE_STATUS_FINISH == questionnairePO.getQuestionStatus()){
            throw new BusinessException("已完成的问卷不可删除");
        }
        this.questionnaireMapper.deleteQuestionnaire(sid);
    }

    @Override
    public void updateQuestionnaire(QuestionnairePO questionnairePO) {
        this.questionnaireMapper.updateQuestionnairePO(questionnairePO);
    }

    @Override
    public List<Map<String, Object>> getMemberQuesScore(ListQuestionnaireDTO listQuestionnaireDTO){
        List<Map<String, Object>> reList=new ArrayList<>();

        listQuestionnaireDTO.setQuestionStatus("3");
        for (int i=1; i<=5; i++){
            Map<String, Object> reMap=new HashMap<>();
            listQuestionnaireDTO.setQuestionType(i+"");
            QuestionnairePO questionnaire = this.questionnaireMapper.getQuestionnaire(listQuestionnaireDTO);
            String level="";
            String score="";
            String sid="";
            if(null!=questionnaire && !StringUtils.isBlank(questionnaire.getResultJson())) {
                Map<String, Object> quesMap = JsonSerializer.jsonToMap(questionnaire.getResultJson());
                if (i == 1 || i == 5) {
                    level = quesMap.get("assessLevel")!=null?quesMap.get("assessLevel").toString():null;
                    score = quesMap.get("standardScore")!=null?quesMap.get("standardScore").toString():null;
                }else if (i == 2) {
                    level = quesMap.get("assessLevel")!=null?quesMap.get("assessLevel").toString():null;
                    score = quesMap.get("avgScore")!=null?quesMap.get("avgScore").toString():null;
                }else if (i == 3) {
                    score = quesMap.get("totalScore")!=null?quesMap.get("totalScore").toString():null;
                }else if (i == 4) {
                    level = quesMap.get("riskLevel")!=null?quesMap.get("riskLevel").toString():null;
                }
                sid = questionnaire.getSid();
            }
            reMap.put("sid",sid);
            reMap.put("level",level);
            reMap.put("score",score);
            reMap.put("type",i);
            reList.add(reMap);
        }
        return reList;
    }

    @Override
    public List<QuestionnairePO> getQuestionnaireByFollowId(String followId) {
        return this.questionnaireMapper.getQuestionnaireByFollowId(followId);
    }


    @Override
    public List<QuestionnairePO> getQuestionnaire(String type, String doctorId,String memberId,String followId) {
        MainFollowPO follow = followService.getMainFollowByFidAndType(followId, null);
        List<QuestionnairePO> list = new ArrayList<>();
        if (!StringUtils.isBlank(type) && follow.getStatus() == 0){
            DoctorPO doctor = this.doctorServiceI.getDoctorById(doctorId);
            List<QuestionnairePO> questionnairePO = this.getQuestionnaireByFollowId(followId);
            for (QuestionnairePO po : questionnairePO) {
                //查询患者已有的对应类型的最新问卷
                ListQuestionnaireDTO dto1 = new ListQuestionnaireDTO();
                dto1.setMemberId(memberId);
//                dto1.setDoctorId(doctorId);
                dto1.setHospitalId(doctor.getHospitalId());
                dto1.setQuestionType(po.getQuestionType() + "");
                dto1.setQuestionStatus("3");
                QuestionnairePO po1 = this.getNewQuestionnaire(dto1);

                //如果患者原先有问卷,将问卷内容更新到自定义随访的问卷中
                if (po1 != null) {
                    SaveQuestionnaireDTO dto2 = new SaveQuestionnaireDTO();
                    dto2.setSid(po.getSid());
                    if (!StringUtils.isBlank(po1.getContentJson())) {
                        dto2.setContentJson(po1.getContentJson());
                    }
                    if (!StringUtils.isBlank(po1.getResultJson())) {
                        dto2.setResultJson(po1.getResultJson());
                    }
                    dto2.setQuestionStatus(2);
                    this.saveQuestionnaire(dto2);
                }
            }
            list = this.questionnaireMapper.getQuestionnaireByFollowId(followId);
        }else{
            list = this.questionnaireMapper.getQuestionnaireByFollowId(followId);
        }
        return list;
    }


    @Override
    public QuestionnairePO getNewQuestionnaire(ListQuestionnaireDTO questionnaireDTO) {
        return this.questionnaireMapper.getNewQuestionnaire(questionnaireDTO);
    }

    @Override
    public void updateQuestionnaireByFollowId(String followContentId) {
        this.questionnaireMapper.updateQuestionnaireByFollowId(followContentId);
    }
}
