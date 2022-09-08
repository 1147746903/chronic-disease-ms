package com.comvee.cdms.defender.wechat.controller;

import com.comvee.cdms.defender.common.cfg.Constant;
import com.comvee.cdms.defender.wechat.dto.BarrierResultDTO;
import com.comvee.cdms.defender.wechat.dto.SubmitBarrierDTO;
import com.comvee.cdms.defender.wechat.po.MemberBarrierPO;
import com.comvee.cdms.defender.wechat.service.MemberBarrierServiceI;
import com.comvee.cdms.defender.wechat.vo.BarrierResultVO;
import com.comvee.cdms.defender.wechat.vo.ListBarrierQuesVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/12/1
 */
@RestController
@RequestMapping("/wechat/barrier")
public class MemberBarrierController {

    @Autowired
    private MemberBarrierServiceI memberBarrierServiceI;


    /**
     * 加载关卡列表
     * @param memberId
     * @return
     */
    @RequestMapping("/listMemberBarrier")
    public Result loadDailyQuestion(String memberId) {
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
            memberId=patient.getMemberId();
        }
        ValidateTool.checkParamIsNull(memberId,"memberId");
        if (memberId.equals("-1")){
            throw  new BusinessException("未登录");
        }
        List<MemberBarrierPO> barrierPOList = memberBarrierServiceI.listMemberBarrier(memberId);
        return Result.ok(barrierPOList);
    }

    /**
     * 点击关卡获取关卡题目
     * @param sid
     * @return
     */
    @RequestMapping("/detailBarrier")
    public Result loadBarrierBySid(String sid) {
        ValidateTool.checkParamIsNull(sid,"sid");
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
        }
        List<ListBarrierQuesVO> listBarrierQuesVOS = memberBarrierServiceI.loadBarrierBySid(sid);
        return Result.ok(listBarrierQuesVOS);
    }


    /**
     * 提交关卡答题
     * @param submitBarrierDTO
     * @return
     */
    @RequestMapping("/submitBarrier")
    public Result submitBarrier(@Valid SubmitBarrierDTO submitBarrierDTO) {
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
        }
        String batchId = memberBarrierServiceI.submitBarrierQues(submitBarrierDTO);
        return Result.ok(batchId);
    }

    /**
     * 闯关结果页
     * @param
     * @return
     */
    @RequestMapping("/resultBarrier")
    public Result resultBarrier(BarrierResultDTO barrierResultDTO) {
        ValidateTool.checkParamIsNull(barrierResultDTO.getSid(),"sid");
        ValidateTool.checkParamIsNull(barrierResultDTO.getScore(),"score");
        ValidateTool.checkParamIsNull(barrierResultDTO.getBatchId(),"batchId");
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
        }
        BarrierResultVO barrierResultVO = memberBarrierServiceI.resultBarrier(barrierResultDTO);
        return Result.ok(barrierResultVO);
    }

    /***
     * 获取错题集
     * @param sid
     * @return
     */
    @RequestMapping("/listBarrierError")
    public Result listBarrierError(String sid,String batchId) {
        ValidateTool.checkParamIsNull(sid,"sid");
        ValidateTool.checkParamIsNull(batchId,"batchId");
        if(Constant.RELEASE){
            MemberPO patient= ValidateTool.checkPatient();
        }
        List<Map<String, Object>> list = memberBarrierServiceI.listBarrierError(sid,batchId);
        return Result.ok(list);
    }

}
