package com.comvee.cdms.insulinpump.controller.web;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.insulinpump.model.param.AddInsulinPumpNurseRecordHttpParam;
import com.comvee.cdms.insulinpump.model.param.AddInsulinPumpObserveRecordParam;
import com.comvee.cdms.insulinpump.model.param.UpdateInsulinPumpNurseRecordHttpParam;
import com.comvee.cdms.insulinpump.model.param.UpdateInsulinPumpObserveRecordParam;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpObserveRecordPO;
import com.comvee.cdms.insulinpump.service.InsulinPumpNurseService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/insulinPumpNurse")
public class WebInsulinPumpNurseController {

    @Autowired
    private InsulinPumpNurseService insulinPumpNurseService;

    /**
     * @api {post}/web/insulinPumpNurse/addInsulinPumpNurseRecord.do  添加护理记录
     * @author suyz
     * @time 2020/07/23
     * @apiName addInsulinPumpNurseRecord 添加护理记录
     * @apiGroup web:insulinPump
     * @apiParam {String} memberId 患者id
     * @apiParam {String} hospitalNo 住院流水号
     * @apiParam {String} sign 签名
     * @apiParam {String} model 型号
     * @apiParam {String} shiftOverDt 交班时间
     * @apiParam {String} pumpUpDt 上泵时间
     * @apiParam {String} pumpDownDt 下泵时间
     * @apiParam {String} electricityQuantity 电量
     * @apiParam {String} consumables 耗材
     * @apiParam {String} dataJson 数据json
     * @apiParam {String} virtualWardId 虚拟病区id
     * {
     *     "pfqk": "皮肤情况 1-6",
     *     "ftqk": "敷贴情况 1-2",
     *     "glhl": "管路护理 1 - 4",
     *     "bqgc": "病情观察及措施"
     * }
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("addInsulinPumpNurseRecord")
    public Result addInsulinPumpNurseRecord(@Validated AddInsulinPumpNurseRecordHttpParam add){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        InsulinPumpNurseRecordPO po = new InsulinPumpNurseRecordPO();
        BeanUtils.copyProperties(po ,add);
        po.setOperatorId(doctorSessionBO.getDoctorId());
        String result = this.insulinPumpNurseService.addInsulinPumpNurseRecord(po);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/insulinPumpNurse/listInsulinPumpNurseRecord.do  加载护理记录列表
     * @author suyz
     * @time 2020/07/23
     * @apiName listInsulinPumpNurseRecord 加载护理记录列表
     * @apiGroup web:insulinPump
     * @apiParam {String} memberId 患者id
     * @apiParam {String} virtualWardId 虚拟病区id
     * @apiSuccess {list} obj 根对象
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.hospitalNo 住院号
     * @apiSuccess {String} obj.sign 签名
     * @apiSuccess {String} obj.model 型号
     * @apiSuccess {String} obj.shiftOverDt 交班时间
     * @apiSuccess {String} obj.pumpUpDt 上泵时间
     * @apiSuccess {String} obj.pumpDownDt 下泵时间
     * @apiSuccess {String} obj.electricityQuantity 电量
     * @apiSuccess {String} obj.consumables 耗材
     * @apiSuccess {String} obj.dataJson 数据json
     * @apiSuccess {String} obj.sid 记录主键
     * @apiSuccess {String} obj.insertDt 插入时间
     */
    @RequestMapping("listInsulinPumpNurseRecord")
    public Result listInsulinPumpNurseRecord(String memberId ,String virtualWardId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        ValidateTool.checkParameterIsNull("virtualWardId" ,virtualWardId);
        List<InsulinPumpNurseRecordPO> result = this.insulinPumpNurseService.listInsulinPumpNurseRecord(memberId ,virtualWardId);
        return Result.ok(result);
    }


    /**
     * @api {post}/web/insulinPumpNurse/updateInsulinPumpNurseRecord.do  修改护理记录
     * @author suyz
     * @time 2020/07/23
     * @apiName updateInsulinPumpNurseRecord 修改护理记录
     * @apiGroup web:insulinPump
     * @apiParam {String} sid 记录主键
     * @apiParam {String} sign 签名
     * @apiParam {String} model 型号
     * @apiParam {String} shiftOverDt 交班时间
     * @apiParam {String} pumpUpDt 上泵时间
     * @apiParam {String} pumpDownDt 下泵时间
     * @apiParam {String} electricityQuantity 电量
     * @apiParam {String} consumables 耗材
     * @apiParam {String} dataJson 数据json
     * {
     *     "pfqk": "皮肤情况 1-6",
     *     "ftqk": "敷贴情况 1-2",
     *     "glhl": "管路护理 1 - 4",
     *     "bqgc": "病情观察及措施"
     * }
     * @apiSuccess {struct} obj 根对象
     */
    @RequestMapping("updateInsulinPumpNurseRecord")
    public Result updateInsulinPumpNurseRecord(@Validated UpdateInsulinPumpNurseRecordHttpParam update){
        InsulinPumpNurseRecordPO po = new InsulinPumpNurseRecordPO();
        BeanUtils.copyProperties(po ,update);
        this.insulinPumpNurseService.updateInsulinPumpNurseRecord(po);
        return Result.ok();
    }

    /**
     * @api {post}/web/insulinPumpNurse/getInsulinPumpNurseRecordById.do  根据id获取护理记录
     * @author suyz
     * @time 2020/07/23
     * @apiName getInsulinPumpNurseRecordById 根据id获取护理记录
     * @apiGroup web:insulinPump
     * @apiParam {String} sid 记录主键
     * @apiSuccess {list} obj 根对象
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.hospitalNo 住院流水号
     * @apiSuccess {String} obj.sign 签名
     * @apiSuccess {String} obj.model 型号
     * @apiSuccess {String} obj.shiftOverDt 交班时间
     * @apiSuccess {String} obj.pumpUpDt 上泵时间
     * @apiSuccess {String} obj.pumpDownDt 下泵时间
     * @apiSuccess {String} obj.electricityQuantity 电量
     * @apiSuccess {String} obj.consumables 耗材
     * @apiSuccess {String} obj.dataJson 数据json
     * @apiSuccess {String} obj.sid 记录主键
     * @apiSuccess {String} obj.insertDt 插入时间
     */
    @RequestMapping("getInsulinPumpNurseRecordById")
    public Result getInsulinPumpNurseRecordById(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        InsulinPumpNurseRecordPO result = this.insulinPumpNurseService.getInsulinPumpNurseRecordById(sid);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/insulinPumpNurse/addInsulinPumpObserveRecord.do  添加观察记录
     * @author suyz
     * @time 2020/07/23
     * @apiName addInsulinPumpObserveRecord 添加观察记录
     * @apiGroup web:insulinPump
     * @apiParam {String} memberId 患者id
     * @apiParam {String} hospitalNo 住院流水号
     * @apiParam {String} sign 签名
     * @apiParam {String} recordDt 日期 yyyy-MM-dd HH:mm:ss
     * @apiParam {String} diagnosis 诊断
     * @apiParam {String} dataJson 数据json
     * @apiParam {String} virtualWardId 虚拟病区id
     * {
     *     "jczl": "基础总量",
     *     "jcl": "基 础 量",
     *     "zjlzao": "追 加 量 -早",
     *     "zjlzhong": "追 加 量 -中午",
     *     "zjlwan": "追 加 量 -晚",
     *     "hljl": "护理记录"
     * }
     * @apiSuccess {string} obj 根对象
     */
    @RequestMapping("addInsulinPumpObserveRecord")
    public Result addInsulinPumpObserveRecord(@Validated AddInsulinPumpObserveRecordParam add){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        InsulinPumpObserveRecordPO po = new InsulinPumpObserveRecordPO();
        BeanUtils.copyProperties(po ,add);
        po.setOperatorId(doctorSessionBO.getDoctorId());
        String result = this.insulinPumpNurseService.addInsulinPumpObserveRecord(po);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/insulinPumpNurse/updateInsulinPumpObserveRecord.do  修改观察记录
     * @author suyz
     * @time 2020/07/23
     * @apiName updateInsulinPumpObserveRecord 修改观察记录
     * @apiGroup web:insulinPump
     * @apiParam {String} sid 记录主键
     * @apiParam {String} sign 签名
     * @apiParam {String} recordDt 日期 yyyy-MM-dd HH:mm:ss
     * @apiParam {String} diagnosis 诊断
     * @apiParam {String} dataJson 数据json
     * {
     *     "jczl": "基础总量",
     *     "jcl": "基 础 量",
     *     "zjlzao": "追 加 量 -早",
     *     "zjlzhong": "追 加 量 -中午",
     *     "zjlwan": "追 加 量 -晚",
     *     "hljl": "护理记录"
     * }
     * @apiSuccess {string} obj 根对象
     */
    @RequestMapping("updateInsulinPumpObserveRecord")
    public Result updateInsulinPumpObserveRecord(UpdateInsulinPumpObserveRecordParam update){
        InsulinPumpObserveRecordPO po = new InsulinPumpObserveRecordPO();
        BeanUtils.copyProperties(po ,update);
        this.insulinPumpNurseService.updateInsulinPumpObserveRecord(po);
        return Result.ok();
    }

    /**
     * @api {post}/web/insulinPumpNurse/getInsulinPumpObserveRecordById.do  根据主键获取观察记录
     * @author suyz
     * @time 2020/07/23
     * @apiName getInsulinPumpObserveRecordById 根据主键获取观察记录
     * @apiGroup web:insulinPump
     * @apiParam {String} sid 主键
     * @apiSuccess {string} obj 根对象
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.hospitalNo 住院号
     * @apiSuccess {String} obj.sign 签名
     * @apiSuccess {String} obj.recordDt 日期 yyyy-MM-dd HH:mm:ss
     * @apiSuccess {String} obj.diagnosis 诊断
     * @apiSuccess {String} obj.dataJson 数据json
     * @apiSuccess {String} obj.insertDt 插入时间
     * @apiSuccess {String} obj.sid 主键
     */
    @RequestMapping("getInsulinPumpObserveRecordById")
    public Result getInsulinPumpObserveRecordById(String sid){
        ValidateTool.checkParameterIsNull("sid" ,sid);
        InsulinPumpObserveRecordPO result = this.insulinPumpNurseService.getInsulinPumpObserveRecordById(sid);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/insulinPumpNurse/listInsulinPumpObserveRecord.do  加载观察记录列表
     * @author suyz
     * @time 2020/07/23
     * @apiName listInsulinPumpObserveRecord 加载观察记录列表
     * @apiGroup web:insulinPump
     * @apiParam {String} memberId 患者id
     * @apiParam {String} virtualWardId 虚拟病区id
     * @apiSuccess {list} obj 根对象
     * @apiSuccess {String} obj.memberId 患者id
     * @apiSuccess {String} obj.hospitalNo 住院号
     * @apiSuccess {String} obj.sign 签名
     * @apiSuccess {String} obj.recordDt 日期 yyyy-MM-dd HH:mm:ss
     * @apiSuccess {String} obj.diagnosis 诊断
     * @apiSuccess {String} obj.dataJson 数据json
     * @apiSuccess {String} obj.insertDt 插入时间
     * @apiSuccess {String} obj.sid 主键
     */
    @RequestMapping("listInsulinPumpObserveRecord")
    public Result listInsulinPumpObserveRecord(String memberId ,String virtualWardId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        ValidateTool.checkParameterIsNull("virtualWardId" ,virtualWardId);
        List<InsulinPumpObserveRecordPO> result = this.insulinPumpNurseService.listInsulinPumpObserveRecord(memberId ,virtualWardId);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/insulinPumpNurse/deleteInsulinPumpNurseRecordById.do  删除护理记录
     * @author suyz
     * @time 2020/07/23
     * @apiName deleteInsulinPumpNurseRecordById 加载观察记录列表
     * @apiGroup web:insulinPump
     * @apiParam {String} id 记录主键
     */
    @RequestMapping("deleteInsulinPumpNurseRecordById")
    public Result deleteInsulinPumpNurseRecordById(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        InsulinPumpNurseRecordPO update = new InsulinPumpNurseRecordPO();
        update.setSid(id);
        update.setValid(0);
        this.insulinPumpNurseService.updateInsulinPumpNurseRecord(update);
        return Result.ok();
    }

    /**
     * @api {post}/web/insulinPumpNurse/deleteInsulinPumpObserveRecordById.do  删除观察记录
     * @author suyz
     * @time 2020/07/23
     * @apiName deleteInsulinPumpObserveRecordById 删除观察记录
     * @apiGroup web:insulinPump
     * @apiParam {String} id 记录主键
     */
    @RequestMapping("deleteInsulinPumpObserveRecordById")
    public Result deleteInsulinPumpObserveRecordById(String id){
        ValidateTool.checkParameterIsNull("id" ,id);
        InsulinPumpObserveRecordPO update = new InsulinPumpObserveRecordPO();
        update.setSid(id);
        update.setValid(0);
        this.insulinPumpNurseService.updateInsulinPumpObserveRecord(update);
        return Result.ok();
    }
}
