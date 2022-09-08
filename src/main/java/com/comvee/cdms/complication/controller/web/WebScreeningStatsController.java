package com.comvee.cdms.complication.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.model.vo.AssessResultStatsVO;
import com.comvee.cdms.complication.model.vo.PatientInfoStatsVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatsVO;
import com.comvee.cdms.complication.service.ScreeningStatsService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2019/6/19
 */
@RestController
@RequestMapping("/web/screening/stats")
public class WebScreeningStatsController {

    @Autowired
    private ScreeningStatsService screeningStatsService;

    /**
     * 已做abi筛查的人数
     * @return
     */
    @RequestMapping("/abiStats")
    public Result abiStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        long abi = this.screeningStatsService.abiStats(doctorSessionBO.getDoctorId());
        return Result.ok(abi);
    }

    /**
     * 已做vpt的人数
     * @return
     */
    @RequestMapping("/vptStats")
    public Result vptStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        long vpt = this.screeningStatsService.vptStats(doctorSessionBO.getDoctorId());
        return Result.ok(vpt);
    }

    /**
     * 筛查患者信息统计
     * @return
     */
    @RequestMapping("/screeningPatientInfoStats")
    public Result screeningPatientInfoStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PatientInfoStatsVO patientInfoStatsVO = this.screeningStatsService.screeningPatientInfoStats(doctorSessionBO.getDoctorId());
        return Result.ok(patientInfoStatsVO);
    }

    /**
     * 筛查情况统计
     * @return
     */
    @RequestMapping("/screeningStats")
    public Result screeningStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ScreeningStatsVO screeningStatsVO = this.screeningStatsService.screeningStats(doctorSessionBO.getDoctorId());
        return Result.ok(screeningStatsVO);
    }

    /**
     * 系统存在的全部患者数量
     * @return
     */
    @RequestMapping("/countAllPeople")
    public Result countAllPeople(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        long count = this.screeningStatsService.countAllPatient(doctorSessionBO.getDoctorId());
        return Result.ok(count);
    }

    /**
     * 统计足部Wagner分级
     * @return
     */
    @RequestMapping("/wagnerLevelStats")
    public Result wagnerLevelStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AssessResultStatsVO assessResultStatsVO = this.screeningStatsService.wagnerLevelStats(doctorSessionBO.getDoctorId());
        return Result.ok(assessResultStatsVO);
    }

    /**
     * 统计CKD的EFR分期
     * @return
     */
    @RequestMapping("/gfrStagesStats")
    public Result gfrStagesStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AssessResultStatsVO assessResultStatsVO = this.screeningStatsService.gfrStagesStats(doctorSessionBO.getDoctorId());
        return Result.ok(assessResultStatsVO);
    }

    /**
     * 糖尿病各慢性病发病率 统计
     * @return
     */
    @RequestMapping("chronicDiseaseStats")
    public Result chronicDiseaseStats(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        JSONObject jsonObject = this.screeningStatsService.chronicDiseaseStats(doctorSessionBO.getDoctorId());
        return Result.ok(jsonObject);
    }

    /**
     * 筛查饼状图数据
     * @return
     */
    @RequestMapping("screeningPieChartData")
    public Result screeningPieChartData(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        JSONObject jsonObject = this.screeningStatsService.screeningPieChartData(doctorSessionBO.getDoctorId());
        return Result.ok(jsonObject);
    }
}
