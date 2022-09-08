package com.comvee.cdms.insulinpump.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpRecordVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpUsageVO;
import com.comvee.cdms.insulinpump.service.InsulinPumpService;
import com.comvee.cdms.insulinpump.tool.InsulinPumpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/insulinPump")
public class WebInsulinPumpController {

    @Autowired
    private InsulinPumpService insulinPumpService;

    /**
     * @api {post}/web/insulinPump/getInsulinPumpUsage.do  获取患者的胰岛素泵使用情况
     * @author suyz
     * @time 2020/07/23
     * @apiName 获取患者的胰岛素泵使用情况
     * @apiGroup web:insulinPump
     * @apiParam {String} memberId 患者id
     * @apiParam {String} hospitalNo 住院流水号
     * @apiSuccess {struct} obj 根对象
     * @apiSuccess {String} obj.yz 医嘱对象
     * @apiSuccess {String} obj.yz.insulinName 胰岛素名称
     * @apiSuccess {String} obj.yz.yzItemName 医嘱项目名称
     * @apiSuccess {String} obj.yz.startDt 开始时间
     * @apiSuccess {String} obj.yz.stopDt 停止时间
     * @apiSuccess {String} obj.yz.extJson 扩展json
     * @apiSuccess {String} obj.yz.checkDt 校对时间
     * @apiSuccess {String} obj.yz.checkerName 校对者姓名
     * @apiSuccess {String} obj.yz.yzExplain 医嘱说明
     * @apiSuccess {list} obj.dayUsageList 每日使用情况
     * @apiSuccess {string} obj.dayUsageList.date 日期  yyyy-Mm-dd
     * @apiSuccess {string} obj.dayUsageList.dayTotal 每日总量
     * @apiSuccess {string} obj.dayUsageList.basalTotal 基础总量
     * @apiSuccess {string} obj.dayUsageList.addTotal 追加总量
     * @apiSuccess {struct} obj.dayUsageList.addDosage 追加的时间段用量
     * @apiSuccess {struct} obj.dayUsageList.baseDosage 基础时间段用量
     * @apiSuccess {struct} obj.dayUsageList.detailDosage 详细时间段用量
     */
    @RequestMapping("getInsulinPumpUsage")
    public Result getInsulinPumpUsage(String memberId ,String hospitalNo){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        ValidateTool.checkParameterIsNull("hospitalNo" ,hospitalNo);
        InsulinPumpUsageVO result = this.insulinPumpService.getInsulinPumpUsage(memberId ,hospitalNo);
        return Result.ok(result);
    }
    /**
     * @api {post}/web/insulinPump/getInsulinPumpEveryHourBasicQuantity.do  获取胰岛素泵每小时基础量表
     * @author suyz
     * @time 2020/07/23
     * @apiName 获取胰岛素泵每小时基础量表
     * @apiGroup web:insulinPump
     * @apiParam {number} total 总量
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("getInsulinPumpEveryHourBasicQuantity")
    public Result getInsulinPumpEveryHourBasicQuantity(Double total){
        ValidateTool.checkParameterIsNull("total" ,total);
        return Result.ok(InsulinPumpTool.getInsulinPumpEveryHourBasicQuantity(total));
    }


    /**
     * 患者所有住院期间胰岛素泵使用记录
     */
    @RequestMapping("listInsulinPumpRecord")
    public Result listInsulinPumpRecord(String memberId,String hospitalId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        ValidateTool.checkParameterIsNull("hospitalId" ,hospitalId);
        List<InsulinPumpRecordVO> recordVOList = this.insulinPumpService.listInsulinPumpRecord(memberId,hospitalId);
        return Result.ok(recordVOList);
    }
}
