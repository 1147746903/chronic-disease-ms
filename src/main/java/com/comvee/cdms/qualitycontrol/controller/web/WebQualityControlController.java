package com.comvee.cdms.qualitycontrol.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.qualitycontrol.model.bo.MaterialConfigBO;
import com.comvee.cdms.qualitycontrol.model.param.*;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlLiquidPO;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlTestPaperPO;
import com.comvee.cdms.qualitycontrol.service.QualityControlService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/web/qualityControl")
public class WebQualityControlController {

    @Autowired
    private QualityControlService qualityControlService;

    /**
     * 获取质控属性配置
     * @return
     */
    @RequestMapping("/getMaterialConfig")
    public Result getMaterialConfig(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List result = this.qualityControlService.getMaterialConfig(doctorSessionBO.getHospitalId());
        return Result.ok(result);
    }

    /**
     * 修改质控属性配置
     * @param configData
     * @return
     */
    @RequestMapping("/updateMaterialConfig")
    public Result updateMaterialConfig(String configData){
        ValidateTool.checkParameterIsNull("configData" ,configData);
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.qualityControlService.updateMaterialConfig(doctorSessionBO.getDoctorId()
                ,doctorSessionBO.getHospitalId() ,materialConfigDataHandler(configData));
        return Result.ok();
    }

    /**
     * 加载质控试纸
     * @param pr
     * @return
     */
    @RequestMapping("/listTestPaper")
    public Result listTestPaper(PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult result = this.qualityControlService.listTestPaper(pr ,doctorSessionBO.getHospitalId());
        return Result.ok(result);
    }

    /**
     * 添加质控试纸
     * @param param
     * @return
     */
    @RequestMapping("/addTestPaper")
    public Result addTestPaper(@Validated AddTestPaperParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        QualityControlTestPaperPO add = new QualityControlTestPaperPO();
        BeanUtils.copyProperties(add ,param);
        add.setHospitalId(doctor.getHospitalId());
        add.setOperatorId(doctor.getDoctorId());
        String id = this.qualityControlService.addTestPaper(add);
        return Result.ok(id);
    }

    /**
     * 获取质控试纸
     * @param id
     * @return
     */
    @RequestMapping("/getTestPaper")
    public Result getTestPaper(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        QualityControlTestPaperPO result = this.qualityControlService.getTestPaper(id);
        return Result.ok(result);
    }

    /**
     * 修改质控试纸
     * @param param
     * @return
     */
    @RequestMapping("/updateTestPaper")
    public Result updateTestPaper(@Validated UpdateTestPaperParam param){
        QualityControlTestPaperPO update = new QualityControlTestPaperPO();
        BeanUtils.copyProperties(update ,param);
        this.qualityControlService.updateTestPaper(update);
        return Result.ok();
    }

    /**
     * 删除质控试纸
     * @param id
     * @return
     */
    @RequestMapping("/deleteTestPaper")
    public Result deleteTestPaper(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        this.qualityControlService.deleteTestPaper(id);
        return Result.ok();
    }

    /**
     * 加载质控液列表
     * @param pr
     * @return
     */
    @RequestMapping("/listLiquid")
    public Result listLiquid(PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        PageResult result = this.qualityControlService.listLiquid(pr ,doctor.getHospitalId());
        return Result.ok(result);
    }

    /**
     * 新增质控液
     * @param param
     * @return
     */
    @RequestMapping("/addLiquid")
    public Result addLiquid(@Validated AddQualityControlLiquidParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        QualityControlLiquidPO add = new QualityControlLiquidPO();
        BeanUtils.copyProperties(add ,param);
        add.setHospitalId(doctor.getHospitalId());
        add.setOperatorId(doctor.getDoctorId());
        String result = this.qualityControlService.addLiquid(add);
        return Result.ok(result);
    }

    /**
     * 修改质控液
     * @param param
     * @return
     */
    @RequestMapping("/updateLiquid")
    public Result updateLiquid(@Validated UpdateQualityControlLiquidParam param){
        QualityControlLiquidPO update = new QualityControlLiquidPO();
        BeanUtils.copyProperties(update ,param);
        this.qualityControlService.updateLiquid(update);
        return Result.ok();
    }

    /**
     * 删除质控液
     * @param id
     * @return
     */
    @RequestMapping("/deleteLiquid")
    public Result deleteLiquid(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        this.qualityControlService.deleteLiquid(id);
        return Result.ok();
    }

    /**
     * 获取质控液
     * @param id
     * @return
     */
    @RequestMapping("/getLiquid")
    public Result getLiquid(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        QualityControlLiquidPO result = this.qualityControlService.getLiquid(id);
        return Result.ok(result);
    }

    /**
     * 加载质控列表
     * @param pr
     * @param param
     * @return
     */
    @RequestMapping("/listQualityControl")
    public Result listQualityControl(PageRequest pr ,ListQualityControlParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setHospitalId(doctor.getHospitalId());
        PageResult result = this.qualityControlService.listQualityControl(pr ,param);
        return Result.ok(result);
    }

    /**
     * 解锁设备
     * @param machineId
     * @return
     */
    @RequestMapping("/unlockMachine")
    public Result unlockMachine(String machineId){
        ValidateTool.checkParameterIsNull("machineId" ,machineId);
        this.qualityControlService.unlockMachine(machineId);
        return Result.ok();
    }

    /**
     * 质控品属性配置数据处理
     * @param configData
     * @return
     */
    private List<MaterialConfigBO> materialConfigDataHandler(String configData){
        List<MaterialConfigBO> result = null;
        try{
            result = JSON.parseArray(configData ,MaterialConfigBO.class);
        }catch (JSONException ex){
            throw new BusinessException("非法的质控品属性配置数据,请确认数据格式");
        }
        if(result == null || result.isEmpty()){
            throw new BusinessException("请录入正确的质控品属性配置数据");
        }
        Optional<MaterialConfigBO> optional = result.stream().filter(x -> x.getLevelCode() == null || StringUtils.isBlank(x.getLevelName())).findFirst();
        if(optional.isPresent()){
            throw new BusinessException("请录入正确的质控品属性配置数据");
        }
        return result;
    }
}
