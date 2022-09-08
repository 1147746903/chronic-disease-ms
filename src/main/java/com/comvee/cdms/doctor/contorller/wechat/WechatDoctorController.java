package com.comvee.cdms.doctor.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.doctor.dto.ListDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorServiceRemindPO;
import com.comvee.cdms.doctor.po.DoctorServiceTimePO;
import com.comvee.cdms.doctor.po.MemberDoctorListPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DoctorAndApplyInfoVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
@RestController
@RequestMapping("/wechat/doctor")
public class WechatDoctorController {

    @Autowired
    private DoctorServiceI doctorServiceI;

    /**
     * 加载医生列表
     * @param pr
     * @param listDoctorDTO
     * @return
     */
    @RequestMapping("listDoctor")
    public Result listDoctor(PageRequest pr, ListDoctorDTO listDoctorDTO) {
        PageResult<DoctorPO> result = this.doctorServiceI.listDoctor(pr , listDoctorDTO);
        return new Result(result);
    }

    /**
     * 根据id获取医生
     * @param doctorId
     * @return
     */
    @RequestMapping("getDoctorById")
    public Result getDoctorById(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(doctorId);
        return new Result(doctorPO);
    }

    /**
     * 获取医生和绑定信息
     * @param doctorId
     * @return
     */
    @RequestMapping("getDoctorAndApplyInfo")
    public Result getDoctorAndApplyInfo(String doctorId){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        MemberPO memberPO = SessionTool.getWechatSession();
        DoctorAndApplyInfoVO doctorAndApplyInfoVO = this.doctorServiceI.getDoctorAndApplyInfo(memberPO.getMemberId(), doctorId);
        return new Result(doctorAndApplyInfoVO);
    }

    /**
     * 加载患者的医生列表
     * @return
     */
    @RequestMapping("listMyDoctor")
    public Result listMyDoctor(){
        MemberPO memberPO = SessionTool.getWechatSession();
        List<MemberDoctorListPO> list = this.doctorServiceI.listMemberDoctor(memberPO.getMemberId());
        return new Result(list);
    }

    /**
     * 设置主要照顾医师
     * @param doctorId
     * @return
     */
    @RequestMapping("setAttendDoctor")
    public Result setAttendDoctor(String doctorId){
        MemberPO memberPO = SessionTool.getWechatSession();
        this.doctorServiceI.setAttendDoctor(memberPO.getMemberId(), doctorId);
        return new Result("");
    }

    /**
     * 加载医生服务时间
     * @param doctorId
     * @return
     */
    @RequestMapping("listDoctorServiceTime")
    public Result listDoctorServiceTime(String doctorId, String weekCode, Integer openStatus){
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        List<DoctorServiceTimePO> list = this.doctorServiceI.listDoctorServiceTime(doctorId, weekCode, openStatus);
        return Result.ok(list);
    }

    /**
     * 获取医生服务时间外提示信息
     * @param doctorId
     * @return
     */
    @RequestMapping("/getDoctorServiceRemind")
    public Result getDoctorServiceRemind(String doctorId){
        DoctorServiceRemindPO remind = this.doctorServiceI.getDoctorServiceRemindByDoctorId(doctorId, DoctorConstant.OPEN_STATUS_YES);
        return Result.ok(remind);
    }
}
