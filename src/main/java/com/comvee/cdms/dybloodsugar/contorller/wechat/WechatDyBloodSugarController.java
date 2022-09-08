package com.comvee.cdms.dybloodsugar.contorller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarRemarkDTO;
import com.comvee.cdms.dybloodsugar.dto.DyReportDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DynamicBloodSugarStatService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wechatapp/dy/bloodSugar")
public class WechatDyBloodSugarController {

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Autowired
    private DynamicBloodSugarStatService dynamicBloodSugarStatService;

    /**
     * @api {post}/wechatapp/dy/bloodSugar/addBloodSugarRemark.do 添加血糖备注
     * @author 林雨堆
     * @time 2019/04/19 17:00
     * @apName 添加血糖备注
     * @apiGroup glucometer-bloodSugar
     * @apiVersion 0.0.1
     * @apiParam {GlucometerRequest} mr 默认参数
     * @apiParam {String} bid  血糖记录编号（必填）
     * @apiParam {String} content  备注内容（必填）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 OK
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"添加成功",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("addBloodSugarRemark")
    public Result addBloodSugarRemark(@Validated DyBloodSugarRemarkDTO dto){
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setOperatorId(memberPO.getMemberId());
        dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_MEMBER);
        return Result.ok(this.dyBloodSugarService.addBloodSugarRemark(dto));
    }

    /**
     * @api {post}/wechatapp/dy/bloodSugar/deleteBloodSugarRemarkById.do 删除血糖备注
     * @author 林雨堆
     * @time 2019/04/19 17:00
     * @apName 删除血糖备注
     * @apiGroup glucometer-bloodSugar
     * @apiVersion 0.0.1
     * @apiParam {GlucometerRequest} mr 默认参数
     * @apiParam {String} id  备注记录编号（必填）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 Ok
     * {
     *     "obj":null,
     *     "code":"0",
     *     "msg":"删除成功",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("deleteBloodSugarRemarkById")
    public Result deleteBloodSugarRemarkById(String id){
        this.dyBloodSugarService.deleteBloodSugarRemarkById(id);
        return Result.ok();
    }

    /**
     * @api {post}/wechatapp/dy/bloodSugar/listBloodSugarRemarkByBid.do 获取血糖备注列表
     * @author 林雨堆
     * @time 2019/04/19 17:00
     * @apName 获取血糖备注列表
     * @apiGroup glucometer-bloodSugar
     * @apiVersion 0.0.1
     * @apiParam {GlucometerRequest} mr 默认参数
     * @apiParam {String} bid  血糖记录编号（必填）
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 0000 Ok
     * {
     *     [{
     *          "sid":"备注记录编号",
     *          "bid":"血糖记录编号",
     *          "content":"备注内容",
     *          "isValid":"是否有效数据 1有效，0无效",
     *          "modifyDt":"更新时间",
     *          "insertDt":"添加时间",
     *     },
     *     ...]
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"登录超时，请重新登录",
     *     "success":true
     * }
     * or
     * {
     *     "obj":null,
     *     "code":"-1",
     *     "msg":"系统错误",
     *     "success":false
     * }
     */
    @RequestMapping("listBloodSugarRemarkByBid")
    public Result listBloodSugarRemarkByBid(String bid){
        return Result.ok(this.dyBloodSugarService.listBloodSugarRemarkByBid(bid));
    }

    /**
     * 获取最新动态血糖记录
     * @param memberId
     * @param sensorNo
     * @return
     */
    @RequestMapping("getLatestDyBloodSugar")
    public Result getLatestDyBloodSugar(String sensorNo){
        ValidateTool.checkParameterIsNull("sensorNo" ,sensorNo);
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getLatestDyBloodSugar(sensorNo);
        return Result.ok(dyypBloodSugarPO);
    }

    /**
     * 获取报告封面信息
     * path /wechatapp/dy/bloodSugar/getBloodSugarReportCover.do
     * @param reportId
     * @return
     */
    @RequestMapping("getBloodSugarReportCover")
    public Result getBloodSugarReportCover(String reportId){
        ValidateTool.checkParameterIsNull("reportId" , reportId);
        MemberPO memberPO = SessionTool.getWechatSession();
        return Result.ok(dynamicBloodSugarStatService.getBloodSugarReportCover(reportId, memberPO.getMemberId()));
    }


    /**
     * 获取血糖报告列表
     * path /wechatapp/dy/bloodSugar/getBloodSugarReportList.do
     * @param dto
     * @return
     */
    @RequestMapping("/getBloodSugarReportList")
    public Result getBloodSugarReport(DyReportDTO dto, PageRequest pager){
        MemberPO memberPO = SessionTool.getWechatSession();
        dto.setMemberId(memberPO.getMemberId());
        return Result.ok(dynamicBloodSugarStatService.getBloodSugarReportListByMember(dto, pager));
    }
}
