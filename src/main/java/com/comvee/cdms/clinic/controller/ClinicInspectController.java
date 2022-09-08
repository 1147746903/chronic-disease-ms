package com.comvee.cdms.clinic.controller;

import com.comvee.cdms.clinic.dto.*;
import com.comvee.cdms.clinic.po.ClinicInspectPO;
import com.comvee.cdms.clinic.po.ClinicRecordPO;
import com.comvee.cdms.clinic.service.ClinicInspectService;
import com.comvee.cdms.clinic.service.ClinicRecordService;
import com.comvee.cdms.clinic.vo.ClinicInspectVO;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**  深圳足 临床检查
 * @author wyc
 * @date 2019/8/12 10:56
 */
@RestController
@RequestMapping("/web/clinic")
public class ClinicInspectController {

    @Autowired
    private ClinicInspectService clinicInspectService;

    @Autowired
    private ClinicRecordService clinicRecordService;

    /**
     * 加载临床检查列表
     * @param page
     * @param doctorId
     * @return
     */
    @RequestMapping("/listClinicInspect")
    public Result listClinicInspect(PageRequest page, @Validated ListClinicInspectDTO listClinicInspectDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listClinicInspectDTO.setDoctorId(null);
        if (StringUtils.isBlank(listClinicInspectDTO.getHospitalId())){
            listClinicInspectDTO.setHospitalId(doctorSessionBO.getHospitalId());
        }
        PageResult<ClinicInspectVO> result = this.clinicInspectService.listClinicInspect(page, listClinicInspectDTO);
        return Result.ok(result);
    }

    /**
     * 根据临床检查id获取检查详情
     * @param sid
     * @return
     */
    @RequestMapping("/getClinicInspectById")
    public Result getClinicInspectById(String sid){
        ValidateTool.checkParameterIsNull("sid",sid);
        ClinicInspectPO inspectPO = this.clinicInspectService.getClinicInspectById(sid);
        return Result.ok(inspectPO);
    }

    /**
     * 添加临床检查
     * @param clinicInspectDTO
     * @return
     */
    @RequestMapping("/addClinicInspect")
    public Result addClinicInspect(@Validated AddClinicInspectDTO clinicInspectDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        clinicInspectDTO.setCreatorId(doctorSessionBO.getDoctorId());
        clinicInspectDTO.setHospitalId(doctorSessionBO.getHospitalId());
        String sid = this.clinicInspectService.addClinicInspect(clinicInspectDTO);
        return Result.ok(sid);
    }

    /**
     * 修改临床检查信息
     * @param inspectDTO
     * @return
     */
    @RequestMapping("/modifyClinicInspect")
    public Result modifyClinicInspect(UpdateClinicInspectDTO inspectDTO){
        ValidateTool.checkParameterIsNull("sid",inspectDTO.getSid());
        this.clinicInspectService.modifyClinicInspect(inspectDTO);
        return Result.ok("");
    }

    /**
     * 根据id删除临床检查信息
     * @param sid
     * @return
     */
    @RequestMapping("/deleteClinicInspect")
    public Result deleteClinicInspect(String sid){
        ValidateTool.checkParameterIsNull("sid",sid);
        this.clinicInspectService.deleteClinicInspect(sid);
        return Result.ok("删除成功");
    }

    /**
     * 获取患者最新的临床检查信息
     * @return
     */
    @RequestMapping("/getNewClinicInspect")
    public Result getNewClinicInspect(@Validated GetClinicInspectDTO getClinicInspectDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        getClinicInspectDTO.setHospitalId(doctorSessionBO.getHospitalId());
        ClinicInspectPO clinicInspectPO = this.clinicInspectService.getNewClinicInspect(getClinicInspectDTO);
        return Result.ok(clinicInspectPO);
    }

    /**
     * 添加临床检查数据记录
     * v4.6.0
     * @param clinicRecordDTO
     * @return
     */
    @RequestMapping("/addClinicRecord")
    public Result addClinicRecord(@Validated AddClinicRecordDTO clinicRecordDTO){
        this.clinicRecordService.addClinicRecord(clinicRecordDTO);
        return Result.ok("");
    }

    /**
     * 查询临床检查下的对应检查数据的记录
     * v4.6.0
     * @param clinicId
     * @param clinicType
     * @return
     */
    @RequestMapping("/getClinicRecordByClinicIdAndType")
    public Result getClinicRecordByClinicIdAndType(PageRequest page,String clinicId, Integer clinicType){
        ValidateTool.checkParameterIsNull("clinicId",clinicId);
        ValidateTool.checkParameterIsNull("clinicType",clinicType);
        PageResult<ClinicRecordPO> record = this.clinicRecordService.getClinicRecordByClinicIdAndType(page, clinicId, clinicType);
        return Result.ok(record);
    }
}
