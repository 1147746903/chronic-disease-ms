package com.comvee.cdms.visit.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.dto.GetListVisitEventDTO;
import com.comvee.cdms.visit.po.VisitEventPO;
import com.comvee.cdms.visit.service.VisitEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author linr
 * @Date 2022/2/25
 */
@RestController
@RequestMapping("/web/visitEvent")
public class WebVisitEventController {

    @Autowired
    private VisitEventService visitEventService;

    /**
     * 添加就诊事件
     * @param addDTO
     * @return
     */
    @RequestMapping("addVisitEvent")
    public Result addVisitEvent(@Validated AddVistiEventDTO addDTO){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        addDTO.setOperatorId(webSession.getDoctorId());
        addDTO.setOperatorName(webSession.getDoctorName());
        addDTO.setHospitalName(webSession.getHospitalName());
        addDTO.setDepartmentName(webSession.getDepartName());
        this.visitEventService.addVisitEvent(addDTO);
        return Result.ok();
    }

    /**
     * 分页加载就诊事件
     * @param getListVisitEventDTO
     * @param pr
     * @return
     */
    @RequestMapping("pageListVisitEvent")
    public Result pageListVisitEvent(@Validated GetListVisitEventDTO getListVisitEventDTO, PageRequest pr){
        PageResult pageResult = this.visitEventService.pageVisitEventList(getListVisitEventDTO, pr);
        return Result.ok(pageResult);
    }
}
