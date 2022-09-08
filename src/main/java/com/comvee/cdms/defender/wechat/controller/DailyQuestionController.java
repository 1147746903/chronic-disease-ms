package com.comvee.cdms.defender.wechat.controller;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.defender.common.cfg.Constant;
import com.comvee.cdms.defender.wechat.bo.DailyQuestionBO;
import com.comvee.cdms.defender.wechat.dto.SubmitQuestionDTO;
import com.comvee.cdms.defender.wechat.mapper.DailyQuestionPushMapper;
import com.comvee.cdms.defender.wechat.service.DailyQuestionServiceI;
import com.comvee.cdms.member.po.MemberPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wechat/dailyQues")
public class DailyQuestionController {
    @Autowired
    private DailyQuestionServiceI dailyQuestionServiceI;

    @Autowired
    private DailyQuestionPushMapper dailyQuestionPushMapper;


    /**
     * 用户获取每日一答题目
     * @return
     */
    @RequestMapping("/loadDailyQuestion")
    public Result loadDailyQuestion(String memberId) {
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
            memberId=patient.getMemberId();
        }
        ValidateTool.checkParamIsNull(memberId,"memberId");
        if (memberId.equals("-1")){
            throw  new BusinessException("未登录");
        }
        DailyQuestionBO dailyQuestionBO = dailyQuestionServiceI.loadDailyQuestion(memberId);
        return Result.ok(dailyQuestionBO);
    }


    /**
     * 用户提交每日一答
     * @param submitQuestionDTO
     * @return
     */
    @RequestMapping("/submitDailyQuestion")
    public Result submitDailyQuestion(@Valid SubmitQuestionDTO submitQuestionDTO) {
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
            String memberId=patient.getMemberId();
            submitQuestionDTO.setMemberId(memberId);
        }
        ValidateTool.checkParamIsNull(submitQuestionDTO.getMemberId(),"memberId");
        if (submitQuestionDTO.getMemberId().equals("-1")){
            throw  new BusinessException("未登录");
        }
        String sid = dailyQuestionServiceI.submitMemberDailyQuestion(submitQuestionDTO);
        return Result.ok(sid);
    }

    /**
     * 获取每日一答结果
     * @param sid
     * @return
     */
    @RequestMapping("/resultDailyQuestion")
    public Result submitDailyQuestion(String sid) {
        ValidateTool.checkParamIsNull(sid,"sid");
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
        }
        Map<String, Object> map = dailyQuestionServiceI.loadDailyQuestionResultById(sid);
        return Result.ok(map);
    }


    /**
     * 获取每日一答记录页信息
     * @return
     */
    @RequestMapping("/loadDailyQuestionRecord")
    public Result loadDailyQuestionRecord(String memberId) {
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
            memberId=patient.getMemberId();
        }
        ValidateTool.checkParamIsNull(memberId,"memberId");
        if (memberId.equals("-1")){
            throw  new BusinessException("未登录");
        }
        Map<String, Object> map = dailyQuestionServiceI.loadDailyQuestionRecord(memberId);
        return Result.ok(map);
    }


}
