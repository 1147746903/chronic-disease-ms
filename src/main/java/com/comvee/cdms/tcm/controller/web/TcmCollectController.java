package com.comvee.cdms.tcm.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.tcm.model.dto.TcmCollectQueDTO;
import com.comvee.cdms.tcm.model.dto.TcmCollectTaskDTO;
import com.comvee.cdms.tcm.model.po.TcmCollectDataPO;
import com.comvee.cdms.tcm.model.po.TcmCollectTaskPO;
import com.comvee.cdms.tcm.service.impl.TcmCollectService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中医controller
 */
@RestController
@RequestMapping("/web/tcmCollect")
@RequiresUser
public class TcmCollectController {

    @Autowired
    private TcmCollectService collectService;

    @RequestMapping("/createTcmCollectTask")
    public Result createTcmCollectTask(TcmCollectTaskDTO dto){
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        TcmCollectTaskPO po = new TcmCollectTaskPO();
        po.setMemberId(dto.getMemberId());
        po.setTaskType(dto.getTaskType());
        DoctorSessionBO doctor = SessionTool.getWebSession();
        po.setDoctorId(doctor.getDoctorId());
        return Result.ok(collectService.createTcmCollectTask(po));
    }


    @RequestMapping("/startTcmCollectTask")
    public Result startTcmCollectTask(String sid){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        collectService.startTcmCollectTask(sid);
        return Result.ok();
    }

    @RequestMapping("/finishTcmCollectTask")
    public Result finishTcmCollectTask(String sid){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        collectService.finishTcmCollectTask(sid);
        return Result.ok();
    }

    @RequestMapping("/listTcmCollectTask")
    public Result listTcmCollectTask(TcmCollectTaskDTO dto, PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return Result.ok(collectService.listTcmCollectTask(dto, pr));
    }

    @RequestMapping("/saveTcmCollectData")
    public Result saveTcmCollectData(TcmCollectDataPO data, Integer taskType){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        if(data.getQuestionnaireJson() == null){
            data.setQuestionnaireJson("");
        }
        collectService.savaTcmCollectData(data, taskType, doctor);
        return Result.ok();
    }

    @RequestMapping("/deleteTcmCollectTask")
    public Result deleteTcmCollectTask(String taskId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        collectService.deleteTcmCollectTask(taskId);
        return Result.ok();
    }

    @RequestMapping("/listTcmCollectQue")
    public Result listTcmCollectQue(TcmCollectQueDTO dto){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return Result.ok(collectService.listTcmCollectQue(dto));
    }

    @RequestMapping("/getTcmCollectDataByTaskId")
    public Result getTcmCollectDataByTaskId(String taskId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(taskId, "taskId");
        return Result.ok(collectService.getTcmCollectDataByTaskId(taskId));
    }

    @RequestMapping("/getLastTcmCollectData")
    public Result getLastTcmCollectData(String memberId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(memberId, "memberId");
        return Result.ok(collectService.getLastTcmCollectData(memberId));
    }


    @RequestMapping("/getTcmCollectReport")
   public  Result getTcmCollectReport(String taskId){
        ValidateTool.checkParamIsNull(taskId, "taskId");
        JSONObject obj = new JSONObject();
        obj.put("data", collectService.getTcmCollectDataByTaskId(taskId));
        obj.put("report", collectService.getTcmCollectReport(taskId));
        return Result.ok(obj);
    }



    @RequestMapping("/getLastConstitution")
    public Result getLastConstitution(String memberId){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        return Result.ok(collectService.getLastConstitution(memberId));
    }
}
