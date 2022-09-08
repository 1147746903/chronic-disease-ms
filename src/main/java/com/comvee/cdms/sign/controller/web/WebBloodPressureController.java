package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@RestController
@RequestMapping("/web/bloodPressure")
@RequiresUser
public class WebBloodPressureController {

    private final Logger logger = LoggerFactory.getLogger(WebBloodPressureController.class);

    @Autowired
    private BloodPressureService bloodPressureService;

    /**
     * 添加血压
     * @param addBloodPressureDTO
     * @return
     */
    @RequestMapping("addBloodPressure")
    public Result addBloodPressure(@Validated AddBloodPressureDTO addBloodPressureDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddBloodPressureServiceDTO addBloodPressureServiceDTO = new AddBloodPressureServiceDTO();
        BeanUtils.copyProperties(addBloodPressureServiceDTO, addBloodPressureDTO);
        addBloodPressureServiceDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addBloodPressureServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addBloodPressureServiceDTO.setOrigin(addBloodPressureDTO.getOrigin());
        String sid = this.bloodPressureService.addBloodPressure(addBloodPressureServiceDTO);
        return new Result(sid);
    }

    // endregion


    /**
     * 加载患者血压记录
     * @param listMemberBloodPressureDTO
     * @return
     */
    @RequestMapping("listMemberBloodPressure")
    public Result listMemberBloodPressure(@Validated ListMemberBloodPressureDTO listMemberBloodPressureDTO){
        ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
        BeanUtils.copyProperties(listBloodPressureDTO, listMemberBloodPressureDTO);
        List<BloodPressurePO> list = this.bloodPressureService.listBloodPressure(listBloodPressureDTO);
        return new Result(list);
    }

    /**
     * @api {post}/web/bloodPressure/listBloodPressurePage.do 分页加载患者血压记录
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listBloodPressurePage 分页加载患者血压记录
     * @apiGroup web-bloodPressure
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} codeDt 1、今日  2、三天  3、七天 4、一个月（30天）
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/bloodPressure/listBloodPressurePage.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listBloodPressurePage")
    public Result listBloodPressurePage(@Validated ListMemberBloodPressureDTO listMemberBloodPressureDTO,PageRequest page){
        ListBloodPressureDTO listBloodPressureDTO = new ListBloodPressureDTO();
        BeanUtils.copyProperties(listBloodPressureDTO, listMemberBloodPressureDTO);
        PageResult<BloodPressurePO> list = this.bloodPressureService.listBloodPressurePage(listBloodPressureDTO,page);
        return new Result(list);
    }

    /**
     * 低压峰值 舒张压
     * @param countBloodSugarDTO
     * @return
     */
    @RequestMapping("loadBloodPressureDbpMax")
    public Result loadBloodPressureDbpMax(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object> map = this.bloodPressureService.loadBloodPressureDbpMax(countBloodSugarDTO);
        return new Result(map);
    }

    /**
     * 高压峰值 收缩压
     * @param countBloodSugarDTO
     * @return
     */
    @RequestMapping("loadBloodPressureSbpMax")
    public Result loadBloodPressureSbpMax(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object> map = this.bloodPressureService.loadBloodPressureSbpMax(countBloodSugarDTO);
        return new Result(map);
    }


    /**
     * 血糖平均值、最高值、最低值
     * @param countBloodSugarDTO
     */
    @RequestMapping("loadBloodPressureCount")
    public Result loadBloodPressureCount(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object>  map = this.bloodPressureService.loadBloodPressureCount(countBloodSugarDTO);
        return new Result(map);
    }


    /**
     * 获取血压记录
     * @param sid
     * @return
     */
    @RequestMapping("getBloodPressure")
    public Result getBloodPressure(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        BloodPressurePO bloodPressure = this.bloodPressureService.getBloodPressure(sid);
        return new Result(bloodPressure);
    }

    /**
     * 获取血压建议
     * @param signId
     * @return
     */
    @RequestMapping("getBloodPressureSuggest")
    public Result getBloodPressureSuggest(String signId){
        ValidateTool.checkParamIsNull(signId, "signId");
        SignSuggestPO signSuggestPO = this.bloodPressureService.getBloodPressureSuggest(signId);
        return new Result(signSuggestPO);
    }

    /**
     * 新增血压建议
     * @param addSuggestDTO
     * @return
     */
    @RequestMapping("addBloodPressureSuggest")
    public Result addBloodPressureSuggest(AddSuggestDTO addSuggestDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
//        addSuggestDTO.setDoctorId(doctorSessionBO.getDoctorId());
        addSuggestDTO.setSenderId(doctorSessionBO.getDoctorId());
        String sid = this.bloodPressureService.addBloodPressureSuggest(addSuggestDTO);
        return new Result(sid);
    }
}
