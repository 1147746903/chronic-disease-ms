package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.checkresult.constant.CheckConstant;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BmiPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@RestController
@RequestMapping("/web/bmi")
@RequiresUser
public class WebBmiController {

    @Autowired
    private BmiService bmiService;

    /**
     * 加载患者bmi记录
     * @param listMemberBmiDTO
     * @return
     */
    @RequestMapping("listMemberBmi")
    public Result listMemberBmi(@Validated ListMemberBmiDTO listMemberBmiDTO){
        ListBmiDTO listBmiDTO = new ListBmiDTO();
        BeanUtils.copyProperties(listBmiDTO, listMemberBmiDTO );
        List<BmiPO> list = this.bmiService.listBmi(listBmiDTO);
        return new Result(list);
    }

    /**
     * @api {post}/web/bmi/listMemberBmiPage.do 分页加载患者bmi记录
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listMemberBmiPage 分页加载患者bmi记录
     * @apiGroup web-bmi
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} codeDt 1、今日  2、三天  3、七天 4、一个月（30天）
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/bmi/listMemberBmiPage.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listMemberBmiPage")
    public Result listMemberBmiPage(@Validated ListMemberBmiDTO listMemberBmiDTO,PageRequest page){
        ListBmiDTO listBmiDTO = new ListBmiDTO();
        BeanUtils.copyProperties(listBmiDTO, listMemberBmiDTO );
        PageResult<BmiPO> list = this.bmiService.listBmiPage(listBmiDTO,page);
        return new Result(list);
    }

    /**
     * 获取bmi建议
     * @param signId
     * @return
     */
    @RequestMapping("getBmiSuggest")
    public Result getBmiSuggest(String signId){
        ValidateTool.checkParamIsNull(signId, "signId");
        SignSuggestPO signSuggestPO = this.bmiService.getBmiSuggest(signId);
        return new Result(signSuggestPO);
    }

    /**
     * 下发bmi建议
     * @param addSuggestDTO
     * @return
     */
    @RequestMapping("addBmiSuggest")
    public Result addBmiSuggest(@Validated AddSuggestDTO addSuggestDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addSuggestDTO.setDoctorId(addSuggestDTO.getDoctorId());
        addSuggestDTO.setSenderId(doctorSessionBO.getDoctorId());
        String sid = this.bmiService.addBmiSuggest(addSuggestDTO);
        return new Result(sid);
    }

    /**
     * @api {post}/web/bmi/addBmi.do 新增bmi
     * @author 林雨堆
     * @time 2018/09/11
     * @apiName addBmi
     * @apiGroup web-bmi
     * @apiVersion 4.0.3
     * @apiParam {Float} height 身高
     * @apiParam {Float} weight 体重
     * @apiParam {String} bmi bmi
     * @apiParam {String} recordDt 记录时间
     * @apiParam {String} memberId 患者编号
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/bmi/addBmi.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addBmi")
    public Result addBmi(@Validated AddBmiDTO addBmiDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddBmiServiceDTO addBmiServiceDTO = new AddBmiServiceDTO();
        BeanUtils.copyProperties(addBmiServiceDTO, addBmiDTO);
        addBmiServiceDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        if (null == addBmiServiceDTO.getOrigin()){
            addBmiServiceDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_SYS);
        }
        String id = this.bmiService.addBmi(addBmiServiceDTO);
        return new Result(id);
    }

}
