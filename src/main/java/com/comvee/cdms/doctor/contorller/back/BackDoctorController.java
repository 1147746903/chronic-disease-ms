package com.comvee.cdms.doctor.contorller.back;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorHospitalBO;
import com.comvee.cdms.doctor.dto.AddDoctorAndAccountDTO;
import com.comvee.cdms.doctor.dto.ListDoctorDTO;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@RestController
@RequestMapping("/back/doctor")
@RequiresRoles("ADMIN")
public class BackDoctorController {

    private final static ReentrantLock LOCK = new ReentrantLock();

    /**
     * 二维码锁
     */
    private final static ReentrantLock QR_CODE_LOCK = new ReentrantLock();

    @Autowired
    private DoctorServiceI doctorService;

    /**
     * 加载医生列表
     * @param listDoctorDTO
     * @param pr
     * @return
     */
    @RequestMapping("listDoctor")
    public Result listDoctor(ListDoctorDTO listDoctorDTO, PageRequest pr){
        PageResult pageResult = this.doctorService.listDoctor(pr, listDoctorDTO);
        return Result.ok(pageResult);
    }

    /**
     * 添加医生&账号
     * @param addDoctorAndAccountDTO
     * @return
     */
    @RequestMapping("addDoctorAndAccount")
    public Result addDoctorAndAccount(@Validated AddDoctorAndAccountDTO addDoctorAndAccountDTO){
        String doctorId = "";
        LOCK.lock();
        try{
            doctorId = this.doctorService.addDoctorAndAccount(addDoctorAndAccountDTO);
        } finally {
            LOCK.unlock();
        }
        return Result.ok(doctorId);
    }

    /**
     * 修改医生信息
     * @return
     */
    @RequestMapping("updateDoctor")
    public Result updateDoctor(@Validated UpdateDoctorDTO updateDoctorDTO){
        this.doctorService.updateDoctor(updateDoctorDTO);
        return Result.ok();
    }

    /**
     * 加载团队医生列表
     * @param doctorId
     * @return
     */
    @RequestMapping("listTeamDoctor")
    public Result listTeamDoctor(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        List<DoctorPO> list = this.doctorService.listGroupDoctor(doctorId);
        return Result.ok(list);
    }

    /**
     * 添加医护团队关系
     * @param doctorId
     * @param foreignId
     * @return
     */
    @RequestMapping("addDoctorRelation")
    public Result addDoctorRelation(String doctorId, String foreignId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        ValidateTool.checkParamIsNull(foreignId, "foreignId");
        this.doctorService.addDoctorRelation(doctorId, foreignId);
        return Result.ok();
    }

    /**
     * 删除医护团队关系
     * @param doctorId
     * @param foreignId
     * @return
     */
    @RequestMapping("deleteDoctorRelation")
    public Result deleteDoctorRelation(String doctorId, String foreignId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        ValidateTool.checkParamIsNull(foreignId, "foreignId");
        LOCK.lock();
        try{
            this.doctorService.deleteDoctorRelation(doctorId, foreignId);
        }finally {
            LOCK.unlock();
        }
        return Result.ok();
    }

    /**
     * 刷新二维码
     * @param doctorId
     * @return
     */
    @RequestMapping("/refreshDoctorQrCode")
    public Result refreshDoctorQrCode(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        QR_CODE_LOCK.lock();
        try{
            String qrCodeUrl = this.doctorService.refreshDoctorQrCode(doctorId);
            return Result.ok(qrCodeUrl);
        } finally {
            QR_CODE_LOCK.unlock();
        }
    }

    /**
     * 加载医生列表及登录信息
     * @param listDoctorDTO
     * @param pr
     * @return
     */
    @RequestMapping("listDoctorAndLogin")
    public Result listDoctorAndLogin(ListDoctorDTO listDoctorDTO, PageRequest pr){
        PageResult pageResult = this.doctorService.listDoctorAndLogin(pr,listDoctorDTO);
        return Result.ok(pageResult);
    }

    /** v5.0.0
     * 获取医生信息
     * @return
     */
    @RequestMapping("getDoctorInfo")
    public Result getDoctorInfo(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        return new Result(doctorPO);
    }

    /** v5.1.1
     * 加载医生可切换医院列表
     * @param doctorId
     * @return
     */
    @RequestMapping("listDoctorHospitalByDoctorId")
    public Result listDoctorHospitalByDoctorId(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        List<DoctorHospitalBO> result = this.doctorService.listDoctorHospitalByDoctorId(doctorId);
        return Result.ok(result);
    }

    /**
     * 获取医生村社区关联信息
     */
    @RequestMapping("listDoctorCommittee")
    public Result listDoctorCommittee(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        Map<String, Object> map = this.doctorService.listDoctorCommittee(doctorId);
        return Result.ok(map);
    }
}


