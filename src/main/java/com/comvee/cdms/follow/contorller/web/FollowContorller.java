package com.comvee.cdms.follow.contorller.web;

import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.cfg.FollowLock;
import com.comvee.cdms.follow.dto.*;
import com.comvee.cdms.follow.model.FollowDTO;
import com.comvee.cdms.follow.model.HealthAccessModel;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.follow.service.FollowSetServiceI;
import com.comvee.cdms.follow.vo.FollowRemindVO;
import com.comvee.cdms.knowledge.model.PagerModel;
import com.comvee.cdms.prescription.dto.DoctorDTO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 
 * @author 李左河
 *
 */
@RestController
@RequestMapping("web/follow/")
public class FollowContorller {

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private FollowSetServiceI followSetService;

    /**
     * @api {post} /web/follow/listFollowAndQuestionOfPage.do 分页随访和问卷列表
     * @author 林雨堆
     * @time 2019/08/18
     * @apiName listFollowAndQuestionOfPage
     * @apiGroup web.follow
     * @apiParam {String} memberId 患者编号（必填）
     * @apiParam {String} memberName 患者名称（可选）
     * @apiParam {String} doctorId 创建者编号（可选）
     * @apiParam {String} flag 标志（可选，'follow' 随访，'question' 问卷）
     * @apiParam {Integer} type 随访或问卷类型（需要参数"flag"有值才可生效，根据标志选择,可选）
     * @apiParam {Boolean} deal 处理状态（根据标志选择【随访{1 已处理 0 未处理},问卷{3 已处理 1，2 未处理}】可选）
     * @apiParam {String} page 页码（默认 1，可选）
     * @apiParam {String} rows 页数（默认 10，可选）
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listFollowAndQuestionOfPage")
    public Result listFollowAndQuestionOfPage(String flag,String memberId,String memberName, String doctorId
            , Integer type,Boolean deal,PageRequest pager ,String operatorId,String hospitalId,String authority){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.listFollowAndQuestion(flag,memberId,memberName,doctorId,type,pager,deal ,operatorId,hospitalId,authority));

    }

    /**
     * 分页获取患者随访列表
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param memberId
     * @param followType
     * @param pager
     * @return
     */
    @RequestMapping("listFollowOfPage")
    public Result listFollowOfPage(String memberId,String memberName, String doctorId, Integer followType,Boolean deal,PageRequest pager){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.listFollowOfPager(memberId,memberName,doctorId , CollectionUtil.singletonList(followType),pager,deal));

    }

    /**
     * 分页获取随访患者列表
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param pager
     * @param followType
     * @return
     */
    @RequestMapping("listFollowMember")
    public Result listFollowMember(PageRequest pager,Integer followType){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.listFollowMemberOfPage(doctor.getDoctorId(),doctor.getDoctorId(),followType,pager));
    }

    /**
     * @api {post} /web/follow/getFollowByLogId.do 获取 首诊/随访 信息
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @apiName getFollowByLogId
     * @apiGroup web.follow
     *
     * @apiParam {int} type 类型（必填）
     * @apiParam {String} sid 记录主键（必填）
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj 对象内容
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("getFollowByLogId")
    public Result getFollowByLogId(String followId,Integer type){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.getFollowById(followId,type));
    }

    /**
     * 更新患者随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param set
     * @return
     */
    @RequestMapping("modifyFollowSet")
    public Result modifyFollowSet(MemberFollowSetPO set){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.followService.modifyFollowSet(set);
        return new Result("修改成功");
    }

    /**
     * @api {post} /web/follow/modifyFollow.do 更新患者首诊/随访
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @apiName modifyFollow
     * @apiGroup web.follow
     *
     * @apiParam {String} nextDt 下次随访时间（可选）
     * @apiParam {String} sid 记录主键（必填）
     * @apiParan {int} dealStatus 状态 （必填 1完成 0未完成）
     * @apiParam {String} fillFormBy 随访填写对象（必填）
     * @apiParam {String} followInfo 非首诊随访信息JSON（可选）
     * @apiParam {String} archivesJson 档案信息（可选）
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("modifyFollow")
    public Result modifyFollow(FollowDTO follow){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        follow.setDoctorId(doctor.getDoctorId());
        follow.setHospitalId(doctor.getHospitalId());
        follow.setDoctorName(doctor.getDoctorName());
        follow.setOrigin("1");
        follow.setLeaderId(doctor.getDoctorId());
        FollowLock.MODIFY_FOLLOW_LOCK.lock();
        try{
            this.followService.modifyFollow(follow);
        }finally {
            FollowLock.MODIFY_FOLLOW_LOCK.unlock();
        }
        return new Result("修改成功");
    }


    /**
     * 问卷数据接口
     * @return
     */
    @RequestMapping("getQuestionnaire")
    public Result getQuestionnaire(String followtype){
        return new Result(this.followService.getQuestionnaire(followtype));
    }

    /**
     * @api {post} /web/follow/insertFollow.do 添加患者首诊/随访
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @apiName insertFollow
     * @apiGroup web.follow
     *
     * @apiParam {String} nextDt 下次随访时间（可选）
     * @apiParam {String} followDt 随访时间，创建时间（必填）
     * @apiParam {int} followType 随访类型（必填 1 首诊 2 日常随访）
     * @apiParam {String} memberName 患者名称（必填）
     * @apiParam {String} memberInfo 患者信息JSON（必填）
     * @apiParam {String} fillFormBy 随访填写对象（必填 1医生 2患者）
     * @apiParam {String} memberId 患者编号（必填）
     *
     * @apiSuccess {Object} data
     * @apiSuccess {Object} data.obj
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("insertFollow")
    public Result insertFollow(FollowDTO follow){
        ValidateTool.checkParamIsNull(follow.getDoctorId(), "医生id");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorDTO,doctor);

        follow.setLeaderId(follow.getDoctorId());
        follow.setDoctorId(doctor.getDoctorId());
        follow.setDoctorName(doctor.getDoctorName());
        follow.setHospitalId(doctor.getHospitalId());

        Map<String,String> result = new HashMap<String,String>(1);
        result.put("logId",this.followService.insertFollowWithLock(follow));
        return new Result(result);
    }
    /**
     * 添加随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param set
     * @return
     */
    @RequestMapping("insertMemberFollowSet")
    public Result insertMemberFollowSet(MemberFollowSetPO set){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        set.setLeaderId(doctor.getDoctorId());
        set.setDoctorId(doctor.getDoctorId());
        Map<String,String> result = new HashMap<String,String>(1);
        result.put("logId",this.followService.insertFollowSetWithLock(set));
        return new Result(result);
    }

    /**
     * 获取随访设置
     * @author 林雨堆
     * @time 2018/03/08 09:00
     * @param memberId
     * @return
     */
    @RequestMapping("getMemberFollowSetByDoc")
    public Result getMemberFollowSetByDoc(String memberId,String doctorId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)){
            doctorId = doctor.getDoctorId();
        }
        MemberFollowSetPO set = this.followService.getMemberFollowSetByDocNew(doctorId,memberId);
        return new Result(set);
    }

    /**
     * 获取出院随访患者列表根据间隔天数
     * @param outDays
     * @param pager
     * @return
     */
    @RequestMapping("listOutHosFollowMemberPageByDays")
    public Result listOutHosFollowMemberPageByDays(Integer outDays,Integer endDays, PagerModel pager){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        return new Result(this.followService.listOutHosFollowMemberPageByDays(doctor,outDays,endDays,pager));
    }

    /**
     * type生成首诊建议 生成2型糖尿病随访建议 (*********************4.2.1 记得修改type类型***********************)
     * @param followDTO
     * @return
     */
    @RequestMapping("outFirstFollow")
    public Result outFirstFollow(@Validated OutFollowDTO followDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Map<String, Object> re = this.followService.outFirstFollow(followDTO);
        return new Result(re);
    }

    /**
     * 生成行为问卷建议
     * @param followBody
     * @return
     */
    @RequestMapping("outQuesFollow")
    public Result outQuesFollow(String followBody,String memberId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Map<String, Object> re = this.followService.outQuesFollow(followBody,memberId,doctor.getDoctorId());
        return new Result(re);
    }

    /**
     * 华西医院患者 分层分级规则
     * @param followBody
     * @return
     */
    @RequestMapping("outHxFollow")
    public Result outHxFollow(String followBody, String levelStr, String memberId,String doctorId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        if(StringUtils.isBlank(doctorId)){
            doctorId= doctor.getDoctorId();
        }
        Map<String, Object> re = this.followService.outHxFollow(followBody, levelStr, memberId, doctor.getHospitalId());
        return new Result(re);
    }

    /**
     * 统计随访下发数量
     * @param countFollowDTO
     * @return
     */
    @RequestMapping("countFollow")
    public Result countFollow(CountFollowDTO countFollowDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        countFollowDTO.setDoctorId(doctor.getDoctorId());
        long re = this.followService.countFollow(countFollowDTO);
        return new Result(re);
    }

    /**
     * 随访提醒列表
     * @param listFollowRemindDTO
     * @return
     */
    @RequestMapping("listFollowRemindPage")
    public Result listFollowRemindPage(ListFollowRemindDTO listFollowRemindDTO, PageRequest page){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        listFollowRemindDTO.setDoctorId(doctor.getDoctorId());
        listFollowRemindDTO.setHospitalId(doctor.getHospitalId());
        PageResult<FollowRemindVO> re = this.followService.listFollowRemindPage(listFollowRemindDTO,page);
        return new Result(re);
    }

    /**
     * 医院随访提醒（工作台待办事项新）
     * @param listFollowRemindDTO
     * @param page
     * @return
     */
    @RequestMapping("pageFollowRemindList")
    public Result pageFollowRemindList(ListFollowRemindDTO listFollowRemindDTO, PageRequest page){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        listFollowRemindDTO.setDoctorId(doctor.getDoctorId());
        listFollowRemindDTO.setHospitalId(doctor.getHospitalId());
        listFollowRemindDTO.setNextDt(DateHelper.getToday());
        listFollowRemindDTO.setStatus(null);
        PageResult<FollowRemindVO> re = this.followService.listFollowRemindPage(listFollowRemindDTO,page);
        return new Result(re);
    }

    /**
     * 统计随访提醒数量
     * @param followRemindPO
     * @return
     */
    @RequestMapping("countFollowRemind")
    public Result countFollowRemind(ListFollowRemindDTO listFollowRemindDTO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        listFollowRemindDTO.setDoctorId(doctor.getDoctorId());
        long re = this.followService.countFollowRemind(listFollowRemindDTO);
        return new Result(re);
    }

    /**
     * 添加随访提醒
     * @param followRemindPO
     * @return
     */
    @RequestMapping("insertFollowRemind")
    public Result insertFollowRemind(FollowRemindPO followRemindPO){
        String doctorId = followRemindPO.getDoctorId();
        if (StringUtils.isBlank(doctorId)){
            DoctorSessionBO doctor = SessionTool.getWebSession();
            doctorId = doctor.getDoctorId();
        }
        followRemindPO.setDoctorId(doctorId);
        this.followService.insertFollowRemind(followRemindPO);
        return new Result("");
    }

    /**
     * 修改随访提醒
     * @param followRemindPO
     * @return
     */
    @RequestMapping("modifyFollowRemind")
    public Result modifyFollowRemind(FollowRemindPO followRemindPO){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.followService.modifyFollowRemind(followRemindPO);
        return new Result();
    }

    /**
     * 过滤有首诊的患者列表
     * @param memberIdString
     * @return
     */
    @RequestMapping("listHasFirstFollowMember")
    public Result listHasFirstFollowMember(String memberIdString){
        ValidateTool.checkParamIsNull(memberIdString, "memberIdString");
        List<String> memberList = Arrays.asList(memberIdString.split(","));
        List<String> resultList = this.followService.listHasFirstFollowMember(memberList);
        return new Result(resultList);
    }

    /**
     * 获取最新一条问卷记录
     * @param memberId
     * @param doctorId
     * @param followType
     * @return
     */
    @RequestMapping("getFollowQuesNewByType")
    public Result getFollowQuesNewByType(String memberId, String doctorId,Integer followType){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        ValidateTool.checkParamIsNull(doctorId, "doctorId");
        ValidateTool.checkParamIsNull(followType, "followType");
        Object follow = this.followService.getFollowQuesNewByType(memberId, doctorId, followType);
        return new Result(follow);
    }

    /**
     * 获取随访设置(注释的为老版本随访设置)
     * @author wangxy
     * @date 2019年4月10日 上午9:51:22
     * @param followSetDTO
     * @return
     */
/*    @RequestMapping("loadFollowSet")
    public Result loadFollowSet(FollowSetDTO followSetDTO){
        ValidateTool.checkParamIsNull(followSetDTO.getDoctorId(), "doctorId");
        FollowSetPO follow = this.followSetService.loadFollowSet(followSetDTO);
        return new Result(follow);
    }*/
    @RequestMapping("loadFollowSet")
    public Result loadFollowSet(@Validated FollowupSetDTO followupSetDTO){
        FollowupSetPO follow = this.followSetService.getFollowupSet(followupSetDTO);
        return new Result(follow);
    }

    /**
     * 修改随访设置(注释的为老版本随访设置)
     * @author wangxy
     * @date 2019年4月10日 上午9:51:22
     * @param followSetPO
     * @return
     */
/*    @RequestMapping("addOrEditFollowSet")
    public Result addOrEditFollowSet(FollowSetPO followSetPO){
        ValidateTool.checkParamIsNull(followSetPO.getSetUser(), "setUser");
        ValidateTool.checkParamIsNull(followSetPO.getFollowType(), "followType");
        ValidateTool.checkParamIsNull(followSetPO.getDoctorId(), "doctorId");
        this.followSetService.modifyFollowSet(followSetPO);
        return new Result(true);
    }*/
    @RequestMapping("addOrEditFollowSet")
    public Result addOrEditFollowSet(FollowupSetPO followupSetPO){
        ValidateTool.checkParamIsNull(followupSetPO.getFollowType(), "followType");
        ValidateTool.checkParamIsNull(followupSetPO.getDoctorId(), "doctorId");
        ValidateTool.checkParamIsNull(followupSetPO.getMemberId(), "memberId");
        DoctorSessionBO webSession = SessionTool.getWebSession();
        followupSetPO.setHospitalId(webSession.getHospitalId());
        this.followSetService.modifyFollowupSet(followupSetPO);
        return new Result(true);
    }

    //

    /**
     * 添加自定义随访模板
     * @param templateDTO
     * @return
     */
    @RequestMapping("saveFollowCustomerTemplate")
    public Result saveFollowCustomerTemplate(AddFollowCustomTemplateDTO templateDTO){
        ValidateTool.checkParamIsNull(templateDTO.getDoctorId() ,"doctorId");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        templateDTO.setHospitalId(doctor.getHospitalId());
        templateDTO.setCreatorId(doctor.getDoctorId());
        String res = this.followService.saveFollowCustomerTemplate(templateDTO);
        Result result = new Result(res);
        if("1001".equals(res)){
            result.setMessage("模版名称已经存在");
            result.setCode(res);
            result.setSuccess(false);
            result.setObj("模版名称已经存在");
        }
        return result;
    }

    /**
     * 根据模板id查询模板信息
     * @param sid
     * @return
     */
    @RequestMapping("getTemplateById")
    public Result getTemplateById(String sid) {
        ValidateTool.checkParameterIsNull(sid,"sid");
        FollowCustomerTemplatePO templatePO = this.followService.getTemplateById(sid);
        return new Result(templatePO);
    }

    /**
     * 加载自定义随访模板列表
     * @param doctorId
     * @param hospitalId
     * @return
     */
    @RequestMapping("listFollowCustomerTemplate")
    public Result listFollowCustomerTemplate(String doctorId) {
        ValidateTool.checkParamIsNull(doctorId ,"doctorId");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        List<FollowCustomerTemplatePO> list = this.followService.listFollowCustomerTemplate(doctorId, doctor.getHospitalId());
        return new Result(list);
    }

    /**
     *获取回显信息
     * @param memberId
     * @return
     */
    @RequestMapping("getCustomerEchoInfo")
    public Result getCustomerEchoInfo( String memberId,String followId) {
        ValidateTool.checkParameterIsNull("memberId",memberId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Map<String, Object> map = this.followService.getCustomerEchoInfo(doctor.getDoctorId(), memberId,followId);
        return new Result(map);
    }

    /**
     * 自动忽略到期的提前提醒
     * @param listFollowRemindDTO
     * @return
     */
    @RequestMapping("ignoreFollowRemind")
    public Result ignoreFollowRemind(){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        this.followService.ignoreFollowRemind(doctor.getDoctorId());
        return new Result();
    }

    /**
     * 加载自定义随访模板
     * @return
     */
   @RequestMapping("listOperateFollowTemplate")
   public Result listOperateFollowTemplate(String doctorId,PageRequest page,String keyword){
       ValidateTool.checkParamIsNull(doctorId ,"doctorId");
       PageResult<FollowCustomerTemplatePO> list = this.followService.listOperateFollowTemplate(page, doctorId,keyword);
       return new Result(list);
   }

    /**
     * 删除自定义随访模板
     * @param page
     * @return
     */
    @RequestMapping("deleteFollowTemplate")
    public Result deleteFollowTemplate(String ids){
        ValidateTool.checkParamIsNull(ids ,"ids");
        this.followService.deleteFollowTemplate(ids);
        return Result.ok("删除成功");
    }

    ////

    /**
     * 获取患者上次2型糖尿病随访信息
     * @param memberId
     * @param doctorId
     * @return
     */
    @RequestMapping("/getNewFollowDiabetes")
    public Result getNewFollowDiabetes(String memberId,String doctorId ,Integer followType){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        ValidateTool.checkParamIsNull(doctorId ,"doctorId");
        ValidateTool.checkParamIsNull(followType ,"followType");
        FollowDiabetesPO diabetesPO = this.followService.getNewFollowDiabetes(memberId, doctorId ,followType);
        return Result.ok(diabetesPO);
    }

    /**
     * 根据随访id获取报告详情
     * @param followId
     * @return
     */
    @RequestMapping("/getFollowReportByFollowId")
    public Result getFollowReportByFollowId(String followId){
        ValidateTool.checkParamIsNull(followId ,"followId");
        FollowReportPO reportPO = this.followService.getFollowReportByFollowId(followId);
        return Result.ok(reportPO);
    }

    /**
     * 加载随访总结列表
     * @param listFollowReportDTO
     * @param page
     * @return
     */
    @RequestMapping("/listFollowReport")
    public Result listFollowReport(ListFollowReportDTO listFollowReportDTO,PageRequest page){
        PageResult<FollowReportDTO> result = this.followService.listFollowReport(listFollowReportDTO, page);
        return Result.ok(result);
    }

    /**
     * 根据随访报告id查询报告信息
     * @param sid
     * @return
     */
    @RequestMapping("/getFollowReportById")
    public Result getFollowReportById(String sid){
        ValidateTool.checkParamIsNull(sid ,"sid");
        FollowReportPO reportPO = this.followService.getFollowReportById(sid);
        return Result.ok(reportPO);
    }
    /**
     * 生成华西随访总结报告
     * @return
     */
//    @RequestMapping("/createHxFollowZjReport")
//    public Result createHxFollowZjReport(){
////        this.followService.createHxFollowZjReport();
//        return Result.ok();
//    }

    /**
     * 高血压首诊分层分级评估
     * v5.0.0
     * @return
     */
    @RequestMapping("/outFirstGxyFollow")
    public Result outFirstGxyFollow(String dataJson){
        Map<String, Object> map = this.followService.outFirstGxyFollow(dataJson);
        return Result.ok(map);
    }
    
    @RequestMapping("/getPrintAccessDiaControl")
    public Result getPrintAccessDiaControl(String sid , String memberId){
    	ValidateTool.checkParamIsNull(sid ,"sid");
    	ValidateTool.checkParamIsNull(memberId ,"memberId");
    	HealthAccessModel result = this.followService.getPrintAccessDiaControl(sid, memberId);
        return Result.ok(result);
    }
    
    /**
     * 根据问卷内容获取评估建议
     * @param memberInfo
     * @param followInfo
     * @return
     */
    @RequestMapping("/getResultSuggest")
    public Result getResultSuggest(String memberInfo , String followInfo){
    	ValidateTool.checkParamIsNull(memberInfo ,"memberInfo");
    	ValidateTool.checkParamIsNull(followInfo ,"followInfo");
    	Map result = this.followService.getHealthAccessResult(memberInfo , followInfo);
        return Result.ok(result);
    }
    
    /**
     * 获取健康评估患者信息
     * @param memberId
     * @return
     */
    @RequestMapping("getHealthAccessMemberInfo")
    public Result getHealthAccessMemberInfo( String memberId,String followId) {
        ValidateTool.checkParameterIsNull("memberId",memberId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Map<String, Object> map = this.followService.getHealthAccessMemberInfo(doctor.getDoctorId(), memberId,followId);
        return new Result(map);
    }

    /**
     * 用药数据接口
     * @return
     */
    @RequestMapping("getDrugDict")
    public Result getDrugDict(){
        return new Result(this.followService.getDrugDICT());
    }


}
