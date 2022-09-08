package com.comvee.cdms.healthrecord.controller.web;

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.healthrecord.model.dto.ListHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordPO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordStatsVO;
import com.comvee.cdms.healthrecord.service.HealthRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * 健康记录controller
 */
@RestController
@RequestMapping("/web/healthRecord")
public class WebHealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @RequestMapping("/listHealthRecord")
    public Result listHealthRecord(ListHealthRecordDTO dto , PageRequest pr){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        dto.setHospitalId(doctorSession.getHospitalId());
        //查已同步的则同时查同步中的
        if (null != dto.getJwSyncStatus() && dto.getJwSyncStatus() == 1){
            dto.setJwSyncStatusList(Arrays.asList(1,2));
            dto.setJwSyncStatus(null);
        }
        PageResult result = this.healthRecordService.listHealthRecord(dto ,pr);
        return Result.ok(result);
    }

    @RequestMapping("/getHealthRecordStats")
    public Result getHealthRecordStats(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        HealthRecordStatsVO result = this.healthRecordService.getHealthRecordStats(doctorSession.getHospitalId());
        return Result.ok(result);
    }

    @RequestMapping("/getHealthRecordDetail")
    public Result getHealthRecordDetail(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        Map<String ,Object> result = this.healthRecordService.getHealthRecordDetail(id);
        return Result.ok(result);
    }

    /**
     * 更新健康记录
     * @param sid
     * @param jwSyncStatus 基卫同步状态  0 未同步 1 已同步 2 申请同步
     * @return
     */
    @RequestMapping("/updateHealthRecord")
    public Result getHealthRecordDetail(String sid,Integer jwSyncStatus){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        ValidateTool.checkParameterIsNull("jwSyncStatus" ,jwSyncStatus);
        HealthRecordPO healthRecordPO = new HealthRecordPO();
        healthRecordPO.setSid(sid);
        healthRecordPO.setJwSyncStatus(jwSyncStatus);
        String code = "-1";
        if (this.healthRecordService.updateHealthRecord(healthRecordPO)){
            code = "1";
        }
        return Result.ok(code);
    }
}
