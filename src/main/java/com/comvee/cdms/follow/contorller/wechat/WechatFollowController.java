package com.comvee.cdms.follow.contorller.wechat;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.cfg.FollowCustomConstant;
import com.comvee.cdms.follow.cfg.FollowLock;
import com.comvee.cdms.follow.dto.FollowReportDTO;
import com.comvee.cdms.follow.dto.ListFollowReportDTO;
import com.comvee.cdms.follow.dto.OutFollowDTO;
import com.comvee.cdms.follow.model.FollowDTO;
import com.comvee.cdms.follow.model.FollowListModel;
import com.comvee.cdms.follow.model.HealthAccessModel;
import com.comvee.cdms.follow.po.*;
import com.comvee.cdms.follow.service.FollowCustomService;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.prescription.dto.DoctorDTO;
import com.comvee.cdms.user.tool.SessionTool;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
@RestController
@RequestMapping("/wechat/follow")
public class WechatFollowController {

    @Autowired
    private FollowServiceI followService;

    @Autowired
    private FollowCustomService followCustomService;
    
    @Autowired
    private MemberService memberService;    

    /**
     * 加载随访列表
     * @param pr
     * @return
     */
    @RequestMapping("listFollow")
    public Result listFollow(PageRequest pr){
        MemberPO memberPO = SessionTool.getWechatSession();
        List<Integer> typeList = new ArrayList<>();
        typeList.add(FollowConstant.FOLLOW_TYPE_FIRST);
        typeList.add(FollowConstant.FOLLOW_TYPE_DAY);
        typeList.add(FollowConstant.FOLLOW_TYPE_QUES);
        typeList.add(FollowConstant.FOLLOW_TYPE_CUSTOM);
        typeList.add(FollowConstant.FOLLOW_TYPE_TWO_DIABETES);
        typeList.add(FollowConstant.FOLLOW_TYPE_HEALTH_ACCESS);
        typeList.add(FollowConstant.FOLLOW_TYPE_FIRST_GXY);
        typeList.add(FollowConstant.FOLLOW_TYPE_QUES_FOLLOW);
        typeList.add(FollowConstant.FOLLOW_TYPE_FOLLOW);
        typeList.add(FollowConstant.FOLLOW_TYPE_HYP_JW);
        typeList.add(FollowConstant.FOLLOW_TYPE_TNB_ASSESS);
//        PageResult pageResult = this.followService.listFollowOfPager(memberPO.getMemberId(), null,null, typeList, pr, true);
        PageResult<FollowListModel> pageResult = this.followService.listMemberFollow(pr, memberPO.getMemberId(),typeList);

        return new Result(pageResult);
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
        return new Result(this.followService.getFollowById(followId,type));
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
//        follow.setFillFormBy(FollowCustomConstant.FILL_PERSON_PATIENT);
        follow.setOrigin("4");
        FollowLock.MODIFY_FOLLOW_LOCK.lock();
        try{
            this.followService.modifyFollow(follow);
        }finally {
            FollowLock.MODIFY_FOLLOW_LOCK.unlock();
        }
        return new Result("修改成功");
    }

    /**
     * 生成行为问卷建议
     * @param followBody
     * @return
     */
    @RequestMapping("outQuesFollow")
    public Result outQuesFollow(String followBody, String doctorId){
        MemberPO memberPO = SessionTool.getWechatSession();
        Map<String, Object> re = this.followService.outQuesFollow(followBody, memberPO.getMemberId(), doctorId);
        return new Result(re);
    }

    /**
     * g根据id获取自定义随访
     * @param followId
     * @return
     */
    @RequestMapping("/getCustomFollowById")
    public Result getCustomFollowById(String followId){
        ValidateTool.checkParamIsNull(followId ,"followId");
        FollowCustomPO followCustomPO = this.followCustomService.getFollowCustomById(followId);
        return Result.ok(followCustomPO);
    }

    /**
     * 提交随访
     * @param followId
     * @param dataJson
     * @return
     */
    @RequestMapping("/commitCustomFollow")
    public Result commitCustomFollow(String followId ,String dataJson){
        ValidateTool.checkParamIsNull(followId ,"followId");
        ValidateTool.checkParamIsNull(dataJson ,"dataJson");
        this.followCustomService.commitFollow(followId ,dataJson , FollowCustomConstant.FILL_PERSON_PATIENT);
        return Result.ok();
    }


    //
    /**
     * 获取回显信息
     * @param memberId
     * @param doctorId
     * @return
     */
    @RequestMapping("getCustomerEchoInfo")
    public Result getCustomerEchoInfo(String doctorId,String memberId,String followId){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        ValidateTool.checkParamIsNull(doctorId ,"doctorId");
        Map<String, Object> map = this.followService.getCustomerEchoInfo(doctorId, memberId,followId);
        return new Result(map);
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

    ///////
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
     * http://192.168.7.120:9080/intelligent-prescription/wechat/follow/getFollowReportByFollowId.do?followId=191030193808100211
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
     * http://192.168.7.120:9080/intelligent-prescription/wechat/follow/listFollowReport.do?memberId=191014173904100219&rows=10&page=1&reportType=6
     */
    @RequestMapping("/listFollowReport")
    public Result listFollowReport(ListFollowReportDTO listFollowReportDTO, PageRequest page){
        PageResult<FollowReportDTO> result = this.followService.listFollowReport(listFollowReportDTO, page);
        return Result.ok(result);
    }

    /**
     * 根据随访报告id查询报告信息
     * @param sid
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/wechat/follow/getFollowReportById.do?sid=191030193811100213
     */
    @RequestMapping("/getFollowReportById")
    public Result getFollowReportById(String sid){
        ValidateTool.checkParamIsNull(sid ,"sid");
        FollowReportPO reportPO = this.followService.getFollowReportById(sid);
        return Result.ok(reportPO);
    }

    /**
     * @api {post} /web/follow/insertFollow.do 添加患者首诊/随访
     * @author 林雨堆
     * @time 20191028给健康评估的时候添加
     * @apiName insertFollow
     * @apiGroup wechat.follow
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
//        ValidateTool.checkParamIsNull(follow.getDoctorId(), "医生id");
    	 ValidateTool.checkParamIsNull(follow.getMemberId(), "memberid");
//        DoctorSessionBO doctor = SessionTool.getWebSession();
//        DoctorDTO doctorDTO = new DoctorDTO();
//        BeanUtils.copyProperties(doctorDTO,doctor);
        
	      follow.setLeaderId(follow.getMemberId());//下发人是该患者
//	      follow.setDoctorId(follow.getDoctorId());
//	      follow.setDoctorName(follow.getDoctorName());//   
	      follow.setDoctorId("-1");
	      follow.setDoctorName("-1");
    	follow.setOrigin("4");
        follow.setHospitalId(follow.getHospitalId());
        Map<String,String> result = new HashMap<String,String>(1);
        result.put("logId",this.followService.insertFollowWithLock(follow));
        return new Result(result);
    }
    
    @RequestMapping("/getHealthAccessResult")
    public Result getHealthAccessResult(String memberInfo , String followInfo){

        Map map = this.followService.getHealthAccessResult(memberInfo , followInfo);
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
     *获取健康评估患者信息
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
     * 获取患者个人信息和 患者档案
     * @param memberId
     * @return memberArchivesPO
     */
    @RequestMapping("getMemberArchivesByMemberId")
    public Result getMemberArchivesByMemberId(String doctorId,Boolean hide){
    	MemberPO memberPO = SessionTool.getWechatSession();
        //患者档案信息
        Map<String, Object> reMap = this.memberService.getMemberArchivesByMemberId(memberPO.getMemberId(),doctorId,"",hide);
        return new Result(reMap);
    } 
    
    /**
     * 
     * @param memberId
     * @param memberName
     * @param doctorId
     * @param followType
     * @param pager
     */
    @RequestMapping("listFollowOfPageForHAcess")
    public Result listFollowOfPageForHAcess(String memberId,String memberName, String doctorId, Integer followType,Boolean deal , String fillFormBy,PageRequest pager){
        MemberPO memberPO = SessionTool.getWechatSession();
//    	String memberid = "190903183655100612";
        PageHelper.startPage(pager.getPage(), pager.getRows());
        doctorId = "-1";
        List<FollowListModel> list = this.followService.wechatListFollow(memberPO.getMemberId(), memberName, doctorId, followType, deal  , fillFormBy);
        PageResult<FollowListModel> result = new PageResult<FollowListModel>(list);
    	    	    	    	
        return new Result(result);
//        return new Result(this.followService.listFollowOfPager(memberid,memberName,doctorId,followType,pager,deal));

    }

    /** v5.1.5
     * 糖尿病风险评估
     * @param followBody
     * @return
     */
    @RequestMapping("outFirstFollow")
    public Result outFirstFollow(OutFollowDTO followDTO){
        MemberPO member = SessionTool.getWechatSession();
        if(StringUtils.isBlank(followDTO.getMemberId())){
            followDTO.setMemberId(member.getMemberId());
        }
        ValidateTool.checkParameterIsNull("随访类型type",followDTO.getType());
        Map<String, Object> re = this.followService.outFirstFollow(followDTO);
        return new Result(re);
    }

    /** v4.2.1
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
}
