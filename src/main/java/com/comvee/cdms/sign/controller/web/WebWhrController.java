package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddWhrDTO;
import com.comvee.cdms.sign.dto.ListWhrDTO;
import com.comvee.cdms.sign.service.WhrService;
import com.comvee.cdms.sign.vo.WhrVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/whr")
@RequiresUser
public class WebWhrController {

    @Autowired
    private WhrService whrService;

    /**
     * @api {post}/web/whr/addWhr.do 添加腰臀比
     * @author 林雨堆
     * @time 2018/09/12
     * @apiName addWhr
     * @apiGroup web-whr
     * @apiVersion 4.0.3
     * @apiParam {String} waist 腰围
     * @apiParam {String} hip 臀围
     * @apiParam {String} whr 腰臀比
     * @apiParam {String} recordDt 记录时间
     * @apiParam {String} memberId 患者编号
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/whr/addWhr.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addWhr")
    public Result addWhr(@Validated AddWhrDTO addWhrDTO, String memberId){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addWhrDTO.setMemberId(memberId);
        addWhrDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addWhrDTO.setOperatorId(doctorSessionBO.getDoctorId());
        String sid = this.whrService.addWhr(addWhrDTO);
        return Result.ok(sid);
    }

    /**
     * @api {post}/web/whr/pageMemberWhr.do 分页加载患者腰臀比记录列表
     * @author 林雨堆
     * @time 2018/09/11
     * @apiName pageMemberWhr
     * @apiGroup web-whr
     * @apiVersion 4.0.3
     * @apiParam {String} startDt 开始时间（可选）
     * @apiParam {String} endDt 结束时间（可选）
     * @apiParam {String} memberId 患者编号（必填）
     * @apiParam {Integer} origin 来源
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/whr/pageMemberWhr.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("pageMemberWhr")
    public Result pageMemberWhr(@Validated ListWhrDTO dto, PageRequest pager){
        PageResult<WhrVO> list = this.whrService.listWhr(dto,pager);
        return Result.ok(list);
    }

}
