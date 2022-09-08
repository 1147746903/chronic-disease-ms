package com.comvee.cdms.dybloodpressure.controller.web;


import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodpressure.dto.AddDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.dto.AddDyBloodPressureReportDTO;
import com.comvee.cdms.dybloodpressure.dto.GetDyBloodPressureDiaryDTO;
import com.comvee.cdms.dybloodpressure.dto.GetDynBloodPressureDataDTO;
import com.comvee.cdms.dybloodpressure.po.DyBloodPressureReportPO;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import com.comvee.cdms.dybloodpressure.vo.DyBloodPressureDiaryVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/10/19
 */
@RestController
@RequestMapping("/web/dy/bloodPressure")
public class WebDyBloodPressureController {

    @Autowired
    private DyBloodPressureService dyBloodPressureService;

    /**
     * 动态血压监测统计表（解析图表）
     * @param getDTO
     * @return
     */
    @RequestMapping("getDynBloodPressureData")
    public Result getDynBloodPressureData(@Valid GetDynBloodPressureDataDTO getDTO){
        Map<String, Object> dynBloodPressureData = this.dyBloodPressureService.getDynBloodPressureData(getDTO);
        return Result.ok(dynBloodPressureData);
    }


    /**
     * 动态血压监测血压数据/相关图（血压list）
     * @param getDTO
     * @param
     * @return
     */
    @RequestMapping("/listDayBloodPressure")
    public Result listDayBloodPressure(@Valid GetDynBloodPressureDataDTO getDTO){
        Map<String, Object> map = this.dyBloodPressureService.listDayBloodPressurePage(getDTO);
        return Result.ok(map);
    }


    /**
     * 保存记一记
     * @param addDto
     * @return
     */
    @RequestMapping("/addDiary")
    public Result addDiary(@Valid AddDyBloodPressureDiaryDTO addDto){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        addDto.setOperationId(webSession.getDoctorId());
        dyBloodPressureService.addUpdateBpDiary(addDto,1);
        return Result.ok();
    }

    /**
     * 获取记一记
     * @param getDyBloodPressureDiaryDTO
     * @return
     */
    @RequestMapping("/getDiary")
    public Result getDiary(@Valid GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO){
        DyBloodPressureDiaryVO dyBloodPressureDiaryPO = dyBloodPressureService.getDyBloodPressureDiaryPO(getDyBloodPressureDiaryDTO);
        return Result.ok(dyBloodPressureDiaryPO);
    }


    /**
     * 保存报告医生评估
     * @param addDto
     * @return
     */
    @RequestMapping("/addReport")
    public Result addReport(@Valid AddDyBloodPressureReportDTO addDto){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        addDto.setOperationId(webSession.getDoctorId());
        dyBloodPressureService.addUpdateBpReport(addDto);
        return Result.ok();
    }


    /**
     * 获取报告
     * @param getDyBloodPressureDiaryDTO
     * @return
     */
    @RequestMapping("/getReport")
    public Result getReport(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO){
        DyBloodPressureReportPO reportPO = dyBloodPressureService.getDyBloodPressureReportPO(getDyBloodPressureDiaryDTO);
        return Result.ok(reportPO);
    }


    /**
     * 分页加载血压历史记录
     * @param
     * @param pr
     * @return
     */
    @RequestMapping("pageDayBloodPressureList")
    public Result pageDayBloodPressureList(GetDyBloodPressureDiaryDTO getDyBloodPressureDiaryDTO, PageRequest pr){
        ValidateTool.checkParamIsNull(getDyBloodPressureDiaryDTO.getMemberId(),"memberId");
        PageResult pageResult = dyBloodPressureService.pageDayBloodPressureList(getDyBloodPressureDiaryDTO, pr);
        return Result.ok(pageResult);
    }


}
