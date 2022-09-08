package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.dto.AddSuggestDTO;
import com.comvee.cdms.sign.dto.ListHbalcDTO;
import com.comvee.cdms.sign.dto.ListMemberHbalcDTO;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/hba1c")
public class WebHba1cController {

    @Autowired
    private Hba1cService hba1cService;

    /**
     * 加载患者糖化记录列表
     * @param memberId
     * @param pr
     * @return
     */
    @RequestMapping("listMemberHba1c")
    public Result listMemberHba1c(PageRequest pr, String memberId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        PageResult pageResult = this.hba1cService.listMemberHba1c(pr ,memberId);
        return Result.ok(pageResult);
    }

    /**
     * @api {post}/web/hba1c/addHba1c.do 添加糖化
     * @author 林雨堆
     * @time 2018/09/11
     * @apiName addBmi
     * @apiGroup web-hba1c
     * @apiVersion 4.0.3
     * @apiParam {String} remark 备注
     * @apiParam {String} recordValue 记录的值
     * @apiParam {String} recordDt 记录时间
     * @apiParam {String} memberId 患者编号
     * @apiParam {Integer} origin 来源
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/hba1c/addHba1c.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addHba1c")
    public Result addHba1c(@Validated AddHba1cDTO addHba1cDTO, String memberId){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addHba1cDTO.setMemberId(memberId);
        addHba1cDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addHba1cDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addHba1cDTO.setHospitalId(doctorSessionBO.getHospitalId());
        addHba1cDTO.setHospitalName(doctorSessionBO.getHospitalName());
        String sid = this.hba1cService.addHba1c(addHba1cDTO);
        return Result.ok(sid);
    }

    /**
     * 加载患者糖化记录列表
     * @param startDt
     * @param endDt
     * @return
     */
/*    @RequestMapping("listMemberHba1c")
    public Result listMemberHba1c(String startDt ,String endDt,String memberId){
        List<Hba1cBO> list = this.hba1cService.listMemberHba1c(memberId ,startDt ,endDt);
        return Result.ok(list);
    }*/

    /**
     * @api {post}/web/hba1c/pageMemberHba1c.do 分页加载患者糖化记录列表
     * @author 林雨堆
     * @time 2018/09/11
     * @apiName pageMemberHba1c
     * @apiGroup web-hba1c
     * @apiVersion 4.0.3
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} memberId 患者编号
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/hba1c/pageMemberHba1c.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageMemberHba1c")
    public Result pageMemberHba1c(String startDt ,String endDt,String memberId,PageRequest pager){
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<Hba1cPO> list = this.hba1cService.listMemberHba1c(memberId ,startDt ,endDt);
        PageResult<Hba1cPO> poPageResult = new PageResult<>(list);
        return Result.ok(poPageResult);
    }

    /**
     *  加载患者糖化记录
     *  v 5.1.6
     * @param listMemberHbalcDTO
     * @return
     */
    @RequestMapping("/listMemberHba")
    public Result listMemberHba(ListMemberHbalcDTO listMemberHbalcDTO){
        ListHbalcDTO listHbalcDTO = new ListHbalcDTO();
        BeanUtils.copyProperties(listHbalcDTO, listMemberHbalcDTO );
        List<Hba1cPO> list = this.hba1cService.listMemberHba1cByParam(listHbalcDTO);
        return Result.ok(list);
    }

    /**
     * 新增糖化血红蛋白建议
     * @param addSuggestDTO
     * @return
     */
    @RequestMapping("addHab1cSuggest")
    public Result addHab1cSuggest(AddSuggestDTO addSuggestDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addSuggestDTO.setSenderId(doctorSessionBO.getDoctorId());
        String sid = this.hba1cService.addHba1cSugarSuggest(addSuggestDTO);
        return new Result(sid);
    }

    /**
     * 获取糖化血红蛋白建议
     * @param signId
     * @return
     */
    @RequestMapping("getHba1cSuggest")
    public Result getHba1cSuggest(String signId){
        ValidateTool.checkParamIsNull(signId, "signId");
        SignSuggestPO signSuggestPO = this.hba1cService.getHba1cSuggest(signId);
        return new Result(signSuggestPO);
    }


}
