package com.comvee.cdms.doctor.contorller.web;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.AddSettingDTO;
import com.comvee.cdms.doctor.po.DoctorServiceSettingPO;
import com.comvee.cdms.doctor.service.DoctorServiceSettingService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/doctor/setting")
public class WebDoctorServiceSettingController {

    @Autowired
    private DoctorServiceSettingService doctorServiceSettingService;

    /**
     * @api {post}/web/doctor/setting/addServiceSetting.do 设置高危血糖提醒(保存高危血糖提醒设置)
     * @author wangt
     * @time 2020/12/25
     * @apiName addServiceSetting 设置高危血糖提醒(保存高危血糖提醒设置)
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} isRemind 医护人员编号 必填
     * @apiParam {Integer} bloodSugarMax 是否是住院关注项目 0 否 1 是 必填
     * @apiParam {String} bloodSugarMin 重点关注项目id串 必填
     * @apiSampleRequest  http://192.168.2.12:8080/intelligent-prescription/web/doctor/addServiceSetting.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addServiceSetting")
    public Result addServiceSetting(@Validated AddSettingDTO addSettingDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addSettingDTO.setDoctorId(doctorSessionBO.getDoctorId());
        addSettingDTO.setHospitalId(doctorSessionBO.getHospitalId());
        this.doctorServiceSettingService.addServiceSetting(addSettingDTO);
        return new Result("");
    }

    /**
     * 获取设置高危血糖提醒
     * @return
     */
    @RequestMapping("getServiceSetting")
    public Result getServiceSetting(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddSettingDTO addSettingDTO = new AddSettingDTO();
        addSettingDTO.setDoctorId(doctorSessionBO.getDoctorId());
        DoctorServiceSettingPO po = this.doctorServiceSettingService.getServiceSetting(addSettingDTO);
        return new Result(po);
    }

}
