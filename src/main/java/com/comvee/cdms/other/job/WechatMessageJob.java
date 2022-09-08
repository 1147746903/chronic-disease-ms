package com.comvee.cdms.other.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.MessageTool;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.tool.ScreeningTool;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.foot.constant.FootConstant;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.bo.MessageHandlerResultBO;
import com.comvee.cdms.other.constant.DoctorPushConstant;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.po.DoctorPushSetPO;
import com.comvee.cdms.other.po.WechatMessagePO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.other.tool.WechatMessageTool;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.user.cfg.UserJoinConstant;
import com.comvee.cdms.user.dto.GetWechatJoinMapperDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserJoinInfoService;
import com.comvee.cdms.wechat.api.WechatMessageApi;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatMessageTemplate;
import com.comvee.cdms.wechat.exception.WechatApiException;
import com.comvee.cdms.wechat.message.TemplateData;
import com.google.common.base.Joiner;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author: suyz
 * @date: 2018/12/4
 */
@Component
public class WechatMessageJob{

    private final static Logger log = LoggerFactory.getLogger(WechatMessageJob.class);

    private final static int PAGE_ROWS = 50;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private UserJoinInfoService userJoinInfoService;

    @Autowired
    private DoctorServiceI doctorServiceI;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorPushService doctorPushService;

    @XxlJob("wechatMessageJob")
    public ReturnT<String> execute(String param) throws Exception {
        PageResult<WechatMessagePO> pageResult = null;
        int page = 1;
        do{
            pageResult = this.wechatMessageService.listWechatMessage(page, PAGE_ROWS);
            startHandler(pageResult.getRows());
            page ++ ;
        } while(pageResult.getTotalPages() > (page -1));
        return ReturnT.SUCCESS;
    }


    /**
     * 开始处理数据
     * @param memberList
     */
    private void startHandler(List<WechatMessagePO> list){
        if(list == null || list.size() == 0){
            return;
        }
        List<MessageHandlerResultBO> resultList = new ArrayList<>();
        list.forEach(x -> {
            resultList.add(messageHandler(x));
        });
        this.wechatMessageService.updateMessageHandlerResult(resultList);
    }

    /**
     * 消息处理
     * @param wechatMessagePO
     */
    private MessageHandlerResultBO messageHandler(WechatMessagePO wechatMessagePO){
        MessageHandlerResultBO messageHandlerResult = new MessageHandlerResultBO();
        messageHandlerResult.setSid(wechatMessagePO.getSid());
        String openId = null;
        if(WechatMessageConstant.USER_TYPE_PATIENT == wechatMessagePO.getUserType()){
            //获取openId
            openId = getOpenId(wechatMessagePO.getMemberId());
        }else if(WechatMessageConstant.USER_TYPE_DOCTOR == wechatMessagePO.getUserType()){
            DoctorPushSetPO pushSet = this.doctorPushService.getDoctorPushSet(wechatMessagePO.getMemberId());
            if(DoctorPushConstant.PUSH_STATUS_CLOSE == pushSet.getWxPush()){
                messageHandlerResult.setDealStatus(WechatMessageConstant.DEAL_STATUS_PUSH_CLOSED);
                return messageHandlerResult;
            }
            openId = getDoctorOpenId(wechatMessagePO.getMemberId());
        }
        if(StringUtils.isBlank(openId)){
            messageHandlerResult.setDealStatus(WechatMessageConstant.DEAL_STATUS_NOT_RELATE);
            return messageHandlerResult;
        }
        TemplateData templateData = new TemplateData();
        try{
            switch (wechatMessagePO.getDataType()){
                //咨询
                case WechatMessageConstant.DATA_TYPE_CONSULT:
                    consultHandler(wechatMessagePO, templateData);
                    break;
                    //血糖测量结果
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR:
                    bloodSugarHandler(wechatMessagePO, templateData);
                    break;
                    //血压
                case WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE:
                    bloodPressureHandler(wechatMessagePO, templateData);
                    break;
                    //bmi
                case WechatMessageConstant.DATA_TYPE_BMI:
                    bmiHandler(wechatMessagePO, templateData);
                    break;
                    //处方下发
                case WechatMessageConstant.DATA_TYPE_PRESCRIPTION:
                    prescriptionHandler(wechatMessagePO, templateData);
                    break;
                    //处方知识学习
                case WechatMessageConstant.DATA_TYPE_PRESCRIPTION_KNOWLEDGE:
                	//不需要再推旧的处方知识学习计划了
                    //knowledgeHandler(wechatMessagePO, templateData);
                    break;
                    //随访问卷
                case WechatMessageConstant.DATA_TYPE_FOLLOW_QUESTION:
                    followQuestionHandler(wechatMessagePO, templateData);
                    break;
                    //随访报告
                case WechatMessageConstant.DATA_TYPE_FOLLOW_REPORT:
                    followReportHandler(wechatMessagePO, templateData);
                    break;
                    //血糖测量提醒
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MONITOR:
                    monitorRemindHandler(wechatMessagePO, templateData);
                    break;
                    //复诊
                case WechatMessageConstant.DATA_TYPE_REVISIT:
                    revisitHandler(wechatMessagePO, templateData);
                    break;
                    //监测方案
                case WechatMessageConstant.DATA_TYPE_MONITOR_PLAN:
                    monitorPlanHandler(wechatMessagePO, templateData);
                    break;
                    //控制目标
                case WechatMessageConstant.DATA_TYPE_CONTROL_TARGET:
                    controlTargetHandler(wechatMessagePO, templateData);
                    break;
                    //未测量提醒
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_UN_MONITOR:
                    unMeasureHandler(wechatMessagePO, templateData);
                    break;
                    //套餐过期提醒
                case WechatMessageConstant.DATA_TYPE_PACKAGE_EXPIRE_REMIND:
                    expirePackageRemindHandler(wechatMessagePO, templateData);
                    break;
                    //套餐即将过期警告
                case WechatMessageConstant.DATA_TYPE_PACKAGE_EXPIRE_WARN:
                    expirePackageWarnHandler(wechatMessagePO, templateData);
                    break;
                    //新增套餐提醒
                case WechatMessageConstant.DATA_TYPE_ADD_PACKAGE:
                    addPackageHandler(wechatMessagePO, templateData);
                    break;
                    //筛查报告复诊提醒
                case WechatMessageConstant.DATA_TYPE_SCREENING_RETURN_VISIT_REMIND:
                    screeningReturnVisitRemindHandler(wechatMessagePO, templateData);
                    break;
                    //筛查知识学习推送
                case WechatMessageConstant.DATA_TYPE_SCREENING_KNOWLEDGE_PUSH:
                    screeningKnowledgePushHandler(wechatMessagePO, templateData);
                    break;
                    //筛查预约提醒
                case WechatMessageConstant.DATA_TYPE_SCREENING_ORDER_REMIND:
                    screeningOrderRemind(wechatMessagePO, templateData);
                    break;
                    //自定义随访
                case WechatMessageConstant.DATA_TYPE_NEW_CUSTOM_FOLLOW:
                    customFollowQuestionHandler(wechatMessagePO, templateData);
                    break;
                    //筛查足部处方下发提醒
                case WechatMessageConstant.DATA_TYPE_SCREENING_FOOT_PRESCRIPTION:
                    screeningFootPrescriptionHandler(wechatMessagePO, templateData);
                    break;
                    // 随访关怀下发消息处理
                case WechatMessageConstant.DATA_TYPE_FOLLOWUP_CARE:
                    followupCareHandler(wechatMessagePO, templateData);
                    break;
                    //糖化消息处理
                case WechatMessageConstant.DATA_TYPE_HBA1C:
                    hba1cHandler(wechatMessagePO, templateData);
                    break;
                    //控糖报告
                case WechatMessageConstant.DATA_TYPE_SUGAR_MONTH_REPORT:
                    sugarMonthReportHandler(wechatMessagePO, templateData);
                    break;
                    //随访总结报告(华西)
                case WechatMessageConstant.DATA_TYPE_FOLLOW_ZJ_REPORT:
                    followZjReportHandler(wechatMessagePO,templateData);
                    break;
                    //血压监测提醒
                case WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE_MONITOR:
                    hypMonitorRemindHandler(wechatMessagePO,templateData);
                    break;
                    //动态血糖绑定通知
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_BIND:
                    bloodSugarBindHandler(wechatMessagePO,templateData);
                    break;
                    //动态血糖解绑通知
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_UNBIND:
                    bloodSugarUnBindHandler(wechatMessagePO,templateData);
                    break;
                    //动态血糖分享绑定通知
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_SHARE_BIND:
                    bloodSugarShareBindHandler(wechatMessagePO,templateData);
                    break;
                    //知识学习推送（课程版）
                case WechatMessageConstant.DATA_TYPE_COURSE_REMIND:
                    knowledgePushHandler(wechatMessagePO ,templateData);
                    break;
                    //动态血糖报告生成通知
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_REPORT:
                    dynamicBloodSugarReportHandler(wechatMessagePO,templateData);
                    break;
                    //定点血糖仪绑定通知
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MACHINE_BIND:
                    fixedBloodSugarBindHandler(wechatMessagePO,templateData);
                    break;
                    //动态血糖统计医生建议推送
                case WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_DOCTOR_ADVICE:
                    dynamicBloodSugarDoctorAdviceHandler(wechatMessagePO,templateData);
                    break;
                    //动态血糖近期概况通知
                case WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_SURVEY_REMIND:
                    dynamicBloodSugarSurveyRemindHandler(wechatMessagePO,templateData);
                    break;
                    //检查提醒通知
                case WechatMessageConstant.DATA_TYPE_CHECK_REMIND:
                    checkRemindHandler(wechatMessagePO,templateData);
                    break;
                    //筛查报告
                case WechatMessageConstant.DATA_TYPE_SCREENING_REPORT:
                    screeningReportRemindHandler(wechatMessagePO,templateData);
                    break;
                    //课程报名成功提醒
                case WechatMessageConstant.DATA_TYPE_ADD_COURSE_SUCCESS:
                    addCourseSuccessRemindHandler(wechatMessagePO,templateData);
                    break;
                    //健康预警通知（给医生）
                case WechatMessageConstant.DATA_TYPE_HEALTH_WARN:
                    healthWarningNoticeToDoctorHandler(wechatMessagePO,templateData);
                    break;
                default:
                    break;
            }
            templateData.setTouser(openId);
            messageHandlerResult.setRequestBody(JSON.toJSONString(templateData));
            JSONObject jsonObject = WechatMessageApi.templateSend(WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO), templateData);
            messageHandlerResult.setDealStatus(WechatMessageConstant.DEAL_STATUS_SUCCESS);
            messageHandlerResult.setResponseData(jsonObject.toJSONString());
        }catch (WechatApiException e){
            messageHandlerResult.setDealStatus(WechatMessageConstant.DEAL_STATUS_FAIL);
            messageHandlerResult.setResponseData(e.getErrCode() + ";" + e.getErrMsg());
            messageHandlerResult.setRequestBody(JSON.toJSONString(templateData));
        }catch (Exception e){
            log.error("微信模板消息发送失败" ,e);
            messageHandlerResult.setDealStatus(WechatMessageConstant.DEAL_STATUS_FAIL);
            messageHandlerResult.setResponseData(e.getClass().getName());
            messageHandlerResult.setRequestBody(JSON.toJSONString(templateData));
        }
        return messageHandlerResult;
    }

    private String getTemplateId(String templateName){
        return WechatMessageTemplate.getTemplateId(templateName);
    }

    /**
     * 知识学习推送(课程版的)
     * @param wechatMessagePO
     * @param templateData
     */
    private void knowledgePushHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.LEARNING_PROGRESS_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first","今天有一个课程需要学习哦，快去学习吧！");
        templateDataItem.addItem("keyword1",jsonObject.getString("title"));
        templateDataItem.addItem("keyword2",jsonObject.getString("learnDate"));
        templateDataItem.addItem("remark", "点此开始学习>>");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}&knowledgeType={2}&articleId={3}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "knowledgePush" , jsonObject.getString("knowledgeType") ,jsonObject.getString("articleId"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 控糖报告推送
     * @param wechatMessagePO
     * @param templateData
     */
    private void sugarMonthReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，您收到一份月度控糖报告，请查看~");
        templateDataItem.addItem("keyword1", "月度控糖报告");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "sugarMonthReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }
    /**
     * 糖化消息处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void hba1cHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您的测量结果如下:");
        templateDataItem.addItem("keyword1", "糖化血红蛋白");
        templateDataItem.addItem("keyword2", jsonObject.getString("recordValue") + "%");
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("recordDt"));
        templateDataItem.addItem("remark", "建议:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=4";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 随访关怀下发消息处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void followupCareHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.CHRONIC_DISEASE_FOLLOWUP_REMIND_TEAMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，您收到一份随访关怀，请及时查看~");
        templateDataItem.addItem("keyword1", DateHelper.getToday());
        templateDataItem.addItem("keyword2", jsonObject.getString("senderInfo"));
        templateDataItem.addItem("remark", jsonObject.getString("careContent"));

        String pageUrl = Constant.PAGE_HOST + "/remind/mobile/remind.html?sid=" + wechatMessagePO.getForeignId();
        templateData.setUrl(pageUrl);
    }

    /**
     * 筛查足部处方下发提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningFootPrescriptionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        Integer footType = jsonObject.getInteger("footType");
        String keyword1 = "";
        String firstText = "";
        if (null != footType && footType == FootConstant.FOOT_TYPE_ZCJ){
            keyword1 = "健康管理报告";
            firstText = "您好，你收到一份健康管理报告，请查看~";
        }else {
            keyword1 = "糖尿病足管理处方报告";
            firstText = "您好，你收到一份糖尿病足管理处方报告，请查看~";
        }
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", firstText);
//        templateDataItem.addItem("keyword1", "健康管理报告");
        templateDataItem.addItem("keyword1", keyword1);
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", "");


        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
//        String path = "/pages/history/th-history?logId={0}&type={1}";
        String path = "/pages/history/th-history?logId={0}&type={1}&footType={2}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "footPrescription",footType);
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 自定义随访问卷处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void customFollowQuestionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getString("followName");
        templateDataItem.addItem("first", followName + "问卷下发提醒");
        templateDataItem.addItem("keyword1", followName + "问卷");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "及时完成问卷填写，有助于医生进一步了解你的病情，以便给你更针对性的管理方案哦");


        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, jsonObject.getString("followId"), "customFollow");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 筛查预约提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningOrderRemind(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_RETURN_VISIT_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "你收到一条筛查预约提醒消息~");
        templateDataItem.addItem("keyword1", jsonObject.getString("hospitalName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", MessageTool.format("明天就到你“{0}”筛查的预约时间了,请携带相关资料提前到院候诊~" , jsonObject.getString("screeningItem")));
    }

    /**
     * 筛查知识学习推送处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningKnowledgePushHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_KNOWLEDGE_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "为您推荐了条新的糖尿病知识，请查看");
        templateDataItem.addItem("keyword1", jsonObject.getString("titleName"));
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", jsonObject.getString("summaryText"));
        templateData.setUrl(Constant.PAGE_HOST + "/complication/mobile/page/know_detail.html?sid=" + wechatMessagePO.getForeignId());
    }

    /**
     * 筛查复诊提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningReturnVisitRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_RETURN_VISIT_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您收到一条复诊提醒消息，请查看~");
        templateDataItem.addItem("keyword1", "自行选择预约");
        templateDataItem.addItem("keyword2", "建议三天后");
        templateDataItem.addItem("remark", "根据糖尿病治疗指南并结合你的个人情况，建议您每个月进行一次相关检查~以便及时了解你的病情发展情况，点击详情查看复诊任务及复诊前注意事项~");
        templateData.setUrl(Constant.PAGE_HOST + "/complication/mobile/page/foot_return_visit_item.html");
    }

    /**
     * 套餐过期提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void expirePackageRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String packageName = jsonObject.getString("packageName");
//        templateData.setTemplate_id(WechatMessageTemplate.PACKAGE_EXPIRE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("尊敬的用户，你的{0}服务已到期", packageName));
        templateDataItem.addItem("keyword1", jsonObject.getString("memberPackageId"));
        templateDataItem.addItem("keyword2", packageName);
        templateDataItem.addItem("keyword3", jsonObject.getString("endDt"));
        templateDataItem.addItem("remark", "你的服务已到期，为了避免套餐到期后影响你的部分功能使用。建议你近期到院续费，以便我们能进行为你提供服务和建议。");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 套餐即将到期警告
     * @param wechatMessagePO
     * @param templateData
     */
    private void expirePackageWarnHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String packageName = jsonObject.getString("packageName");
        int expireDay = jsonObject.getInteger("expireDay");
//        templateData.setTemplate_id(WechatMessageTemplate.PACKAGE_EXPIRE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("尊敬的用户，你的{0}服务将于{1}天后到期", packageName, expireDay));
        templateDataItem.addItem("keyword1", jsonObject.getString("memberPackageId"));
        templateDataItem.addItem("keyword2", packageName);
        templateDataItem.addItem("keyword3", jsonObject.getString("endDt"));
        templateDataItem.addItem("remark", MessageTool.format("尊敬的用户，你的{0}服务将于{1}天后到期，为了避免套餐到期后影响你的部分功能使用。建议你近期到院续费，以便我们能进行为你提供服务和建议", packageName ,expireDay));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 新增套餐提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void addPackageHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String doctorName = getDoctorName(jsonObject.getString("doctorId"));
//        templateData.setTemplate_id(WechatMessageTemplate.NEW_PACKAGE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("恭喜你成功开通{0}医生服务", doctorName));
        templateDataItem.addItem("keyword1", jsonObject.getString("packageName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "接下来你将享受套餐的相关服务内容，希望我们的服务能给你带去满意的体验。");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }
    /**
     * 血糖未测提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void unMeasureHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        int days = jsonObject.getInteger("days");
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_REPLAY_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("您好，您已经连续{0}天未测试血糖了", days));
        templateDataItem.addItem("keyword1", "智能小助");
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", jsonObject.getString("content"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberSuagr";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 控制目标处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void controlTargetHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_REPLAY_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您收到一条血糖控制目标设置提醒，请查看");
        templateDataItem.addItem("keyword1", getDoctorName(jsonObject.getString("doctorId")));
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/monitor/monitor?initType=2";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * 监测方案处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void monitorPlanHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.MONITOR_PLAN_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();

        //小程序页面默认打开页面 1 血糖监测 3 血压监测
        Integer initType = 1;
        String firstText = "您好，医生为您设置了血糖监测方案，请查看";
        Integer illnessType = jsonObject.getInteger("illnessType");
        if(illnessType != null && MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION == illnessType){
            firstText = "您好，医生为您设置了血压监测方案，请查看";
            initType = 3;
        }
        templateDataItem.addItem("first", firstText);
        templateDataItem.addItem("keyword1", "-");
        templateDataItem.addItem("keyword2", jsonObject.getString("planName"));
        templateDataItem.addItem("keyword3", jsonObject.getString("applyExplain"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();

        String path = "/pages-3/monitor/monitor?initType=" + initType;
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 复诊提醒处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void revisitHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(jsonObject.getString("doctorId"));
        String hospitalName = "-";
        if(doctorPO != null){
            hospitalName = doctorPO.getHospitalName();
        }
//        templateData.setTemplate_id(WechatMessageTemplate.REVISIT_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，您收到一条复诊提醒消息");
        templateDataItem.addItem("keyword1", hospitalName);
        templateDataItem.addItem("keyword2", jsonObject.getString("revisitDate"));

        String remark = MessageTool.format(REVISIT_REMAKR_CONTENT, jsonObject.getInteger("day"), hospitalName);
        templateDataItem.addItem("remark", remark);

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/track/track";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    private final static String REVISIT_REMAKR_CONTENT = "{0}天后就到你回{1}复诊时间了，小助受医生嘱托特来提醒你哦";



    /**
     * 血糖测量提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void monitorRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.INTELLIGENT_NURSING_START_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "你好，根据医生为你制定的管理处方血糖监测方案，请你今天完成以下时段血糖监测任务");
        templateDataItem.addItem("keyword1", "血糖");
        templateDataItem.addItem("keyword2", "智能医助");
        templateDataItem.addItem("keyword3", getTimeCodeText(jsonObject.getString("timeCode")));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberSuagr?illnessType=" + MonitorPlanConstant.ILLNESS_TYPE_DIABETES;
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * 血压测量提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void hypMonitorRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.INTELLIGENT_NURSING_START_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "你好，根据医生为你制定的管理处方血压监测方案，请你今天完成以下时段血压监测任务");
        templateDataItem.addItem("keyword1", "血压");
        templateDataItem.addItem("keyword2", "智能医助");
        templateDataItem.addItem("keyword3", getHypTimeCodeText(jsonObject.getString("timeCode")));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberBloodPre?illnessType=" + MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION;
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 获取时间点文案
     * @param timeCode
     * @return
     */
    private String getTimeCodeText(String timeCode){
        if(StringUtils.isBlank(timeCode)){
            return "无";
        }
        List<String> codeList = Arrays.asList(timeCode.split(","));
        List<String> textList = new ArrayList<>();
        codeList.forEach( x -> {
            textList.add(TIME_CODE_TEXT.get(Integer.parseInt(x)));
        });
        return Joiner.on("、").skipNulls().join(textList);
    }

    private final static Map<Integer, String> TIME_CODE_TEXT = new HashMap<>();
    static {
        TIME_CODE_TEXT.put( 1, "凌晨");
        TIME_CODE_TEXT.put( 2, "空腹");
        TIME_CODE_TEXT.put( 3, "早餐后");
        TIME_CODE_TEXT.put( 4, "午餐前");
        TIME_CODE_TEXT.put( 5, "午餐后");
        TIME_CODE_TEXT.put( 6, "晚餐前");
        TIME_CODE_TEXT.put( 7, "晚餐后");
        TIME_CODE_TEXT.put( 8, "睡前");
    }

    /**
     * 获取高血压监测时段文案
     * @param timeCode
     * @return
     */
    private String getHypTimeCodeText(String timeCode){
        if(StringUtils.isBlank(timeCode)){
            return "无";
        }
        List<String> codeList = Arrays.asList(timeCode.split(","));
        LinkedHashSet<String> codeSet = new LinkedHashSet<>();
        for(String s : codeList){
            codeSet.add(HYP_TIME_CODE_TEXT.get(s.split("-")[1]));
        }
        return Joiner.on("、").join(codeSet);
    }

    private final static Map<String ,String> HYP_TIME_CODE_TEXT = new HashMap<>();
    static {
        HYP_TIME_CODE_TEXT.put("1" , "早起后");
        HYP_TIME_CODE_TEXT.put("2" , "早起后");
        HYP_TIME_CODE_TEXT.put("3" , "上午");
        HYP_TIME_CODE_TEXT.put("4" , "上午");
        HYP_TIME_CODE_TEXT.put("5" , "下午");
        HYP_TIME_CODE_TEXT.put("6" , "下午");
        HYP_TIME_CODE_TEXT.put("7" , "晚上");
        HYP_TIME_CODE_TEXT.put("8" , "晚上");
    }

    /**
     * 管理处方处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void prescriptionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，你收到一份管理处方报告，请查看");
        templateDataItem.addItem("keyword1", "管理处方");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type=eoh";
        path = MessageTool.format(path, jsonObject.getString("prescriptionId"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 随访问卷处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void followQuestionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.FOLLOW_UP_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getInteger("type") == FollowConstant.FOLLOW_TYPE_CUSTOM ? jsonObject.getString("followName") : followNameHandler(jsonObject.getInteger("type"));
//        templateDataItem.addItem("first", followName + "问卷下发提醒");
//        templateDataItem.addItem("keyword1", followName + "问卷");
        templateDataItem.addItem("first", followName + "下发提醒");
        templateDataItem.addItem("keyword1", followName);
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "及时完成问卷填写，有助于医生进一步了解你的病情，以便给你更针对性的管理方案哦");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String templateId = jsonObject.getString("templateId") == null ? "-1" : jsonObject.getString("templateId");
        String path = "/pages/history/th-history?logId={0}&type={1}&textType={2}&followtype={3}&doctorId={4}&memberId={5}&templateId={6}&ftype={7}";
            path = MessageTool.format(path, jsonObject.getString("followId"), "follow", 1, jsonObject.getString("type")
            ,jsonObject.getString("doctorId") ,jsonObject.getString("memberId"),templateId,jsonObject.getString("fType"));

        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 随访报告处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void followReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getInteger("type") == FollowConstant.FOLLOW_TYPE_CUSTOM ? jsonObject.getString("followName") : followNameHandler(jsonObject.getInteger("type"));
//        String followName = followNameHandler(jsonObject.getInteger("type"));
//        templateDataItem.addItem("first", followName + "问卷评估报告下发通知");
//        templateDataItem.addItem("keyword1", followName + "随访问卷评估报告");
        templateDataItem.addItem("first", followName + "报告下发通知");
        templateDataItem.addItem("keyword1", followName + "报告");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String templateId = jsonObject.getString("templateId") == null ? "-1" : jsonObject.getString("templateId");
        String path = "/pages/history/th-history?logId={0}&type={1}&textType={2}&followtype={3}&doctorId={4}&memberId={5}&templateId={6}&ftype={7}";
            path = MessageTool.format(path, jsonObject.getString("followId"), "follow", 2, jsonObject.getString("type")
                    ,jsonObject.getString("doctorId") ,jsonObject.getString("memberId"),templateId,jsonObject.getString("fType") );
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 随访总结报告处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void followZjReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first","您好，您收到一份随访总结报告，请查看~");
        templateDataItem.addItem("keyword1","随访总结报告");
        templateDataItem.addItem("keyword2",jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path,jsonObject.getString("reportId"),"followReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 随访名称处理
     * @param type
     * @return
     */
    private String followNameHandler(Integer type){
        String name = "";
        if(type == FollowConstant.FOLLOW_TYPE_FIRST){
            name = "糖尿病首诊";
        }else if(type == FollowConstant.FOLLOW_TYPE_DAY){
            name = "日常随访";
        }else if(type == FollowConstant.FOLLOW_TYPE_QUES){
            name = "自我行为评估";
        }else if(type == FollowConstant.FOLLOW_TYPE_QUES_FOLLOW){
            name = "糖尿病足随访表";
        }else if(type == FollowConstant.FOLLOW_TYPE_FOLLOW){
            name = "糖尿病随访表";
        }else if (type == FollowConstant.FOLLOW_TYPE_TWO_DIABETES){
            name = "2型糖尿病随访表";
        }else if (type == FollowConstant.FOLLOW_TYPE_HEALTH_ACCESS){
            name = "健康评估";
        }else if (type == FollowConstant.FOLLOW_TYPE_FIRST_GXY){
            name = "高血压首诊";
        }else if (type == FollowConstant.FOLLOW_TYPE_HYP_JW){
            name = "高血压随访";
        }else if (type == FollowConstant.FOLLOW_TYPE_TNB_ASSESS){
            name = "糖尿病风险评估";
        }
        return name;
    }

    /**
     * 血糖消息处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您的测量结果如下:");
        templateDataItem.addItem("keyword1", "血糖");
        templateDataItem.addItem("keyword2", jsonObject.getString("value") + "mmol/L");
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "建议:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 血压数据处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodPressureHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您的测量结果如下:");
        templateDataItem.addItem("keyword1", "血压");
        templateDataItem.addItem("keyword2", bloodPressureValueHandler(jsonObject));
        templateDataItem.addItem("keyword3", bloodPressureLevelTextHandler(jsonObject));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "建议:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=2";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 血压值处理
     * @param jsonObject
     * @return
     */
    private String bloodPressureValueHandler(JSONObject jsonObject){
        StringBuilder builder  = new StringBuilder();
        String dbp = jsonObject.getString("dbp");
        String sbp = jsonObject.getString("sbp");
        builder.append(sbp).append("/").append(dbp).append("mmHg");
        return builder.toString();
    }

    /**
     * 血压文案处理
     * @param jsonObject
     * @return
     */
    private String bloodPressureLevelTextHandler(JSONObject jsonObject){
        String text = "";
        Integer dbpLevel = jsonObject.getInteger("dbpLevel");
        Integer sbpLevel = jsonObject.getInteger("sbpLevel");
        if(SignConstant.SIGN_LEVEL_HIGH == dbpLevel
                || SignConstant.SIGN_LEVEL_HIGH == sbpLevel){
            text = "偏高";
        }else if(SignConstant.SIGN_LEVEL_LOW == dbpLevel
                || SignConstant.SIGN_LEVEL_LOW == sbpLevel){
            text = "偏低";
        }else{
            text = "正常";
        }
        return text;
    }

    /**
     * bmi数据处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void bmiHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您的测量结果如下:");
        templateDataItem.addItem("keyword1", "BMI");
        templateDataItem.addItem("keyword2", bmiValueHandler(jsonObject));
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "建议:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=3";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * bmi值处理
     * @param jsonObject
     * @return
     */
    private String bmiValueHandler(JSONObject jsonObject){
        StringBuilder builder = new StringBuilder();
        builder.append(jsonObject.getString("bmi"))
                .append(" (身高").append(jsonObject.getString("height"))
                .append("cm,").append("体重").append(jsonObject.getString("weight"))
                .append("kg)");
        return builder.toString();
    }

    /**
     * 获取体征状态文案
     * @param level
     * @return
     */
    private String getLevelText(Integer level){
        String text = "";
        if(SignConstant.SIGN_LEVEL_LOW == level){
            text = "偏低";
        }else if(SignConstant.SIGN_LEVEL_NORMAL == level){
            text = "正常";
        }else if(SignConstant.SIGN_LEVEL_HIGH == level){
            text = "偏高";
        }
        return text;
    }
    /**
     * 咨询消息处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void consultHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());

        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.DOCTOR_ADVICE_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
//        templateDataItem.addItem("first", consultTitle(jsonObject));
        templateDataItem.addItem("first", "您收到一条回复消息,请查看");
        templateDataItem.addItem("keyword1", getDoctorName(jsonObject.getString("doctorId")));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("keyword3", jsonObject.getString("content"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/message/message?doctorid={0}&memberId={1}";
        path = MessageTool.format(path, jsonObject.getString("doctorId"), wechatMessagePO.getMemberId());
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    private final static String CONSULT_TITLE = "您好，您收到一条{0}回复";

    private String consultTitle(JSONObject jsonObject){
        int textType = jsonObject.getInteger("textType");
        String title = "";
        if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT){
            title = MessageTool.format(CONSULT_TITLE, "文本");
        }else if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE){
            title = MessageTool.format(CONSULT_TITLE, "图片");
        }else if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE){
            title = MessageTool.format(CONSULT_TITLE, "语音");
        }
        return title;
    }

    /**
     * 获取openId
     * @param memberId
     * @return
     */
    private String getOpenId(String memberId){
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setForeignId(memberId);
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_GONG_ZHONG_HAO);
        UserWechatJoinPO userWechatJoinPO = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        return userWechatJoinPO == null ? null : userWechatJoinPO.getOpenId();
    }

    /**
     * 获取医生姓名
     * @param doctorId
     * @return
     */
    private String getDoctorName(String doctorId){
        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(doctorId);
        return doctorPO == null ? "未知" : doctorPO.getDoctorName();
    }

    /**
     * 动态血糖绑定通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "恭喜您，成功绑定动态血糖");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "动态血糖");
        templateDataItem.addItem("remark", "设备号："+jsonObject.getString("sensorNo"));

        //***********待修改***********
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history?initType=5&sensorNo={0}";
        path = MessageTool.format(path,jsonObject.getString("sensorNo"));
//        String path = "/pages/history/th-history?sensorId={0}&type={1}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"), "dy");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 动态血糖解绑通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarUnBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.UNBIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", getMemberName(jsonObject.getString("sendMemberId"))+"与动态血糖解绑成功");
        templateDataItem.addItem("keyword1", jsonObject.getString("sensorNo"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        //***********待修改***********
/*        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path,jsonObject.getString("reportId"),"followReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);*/
    }

    /**
     * 血糖分享绑定通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarShareBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "恭喜您，成功获得动态血糖数据分享，您可以通过微信公众号，接收对方的动态血糖记录");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "动态血糖分享");
        templateDataItem.addItem("remark", "对方姓名："+getMemberName(jsonObject.getString("sendMemberId"))+"，设备号："+jsonObject.getString("sensorNo"));

        //***********待修改***********
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
//        String path = "/pages/history/th-history?sensorId={0}&type={1}&monitorTime={2}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"),"dy" ,jsonObject.getString("monitorTime"));
//        String path = "/pages/history/history?initType=5&sensorNo={0}&monitorTime={1}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"), jsonObject.getString("monitorTime"));
        String path = "/pages-3/member/shareList/bindShare?scene={0}";
        path = MessageTool.format(path,jsonObject.getString("sid"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 动态血糖报告生成通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", jsonObject.getString("memberName") + "的动态血糖报告更新啦");
        templateDataItem.addItem("keyword1", "动态血糖报告");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        if(jsonObject.containsKey("machineEqRemind") && jsonObject.get("machineEqRemind") instanceof String
                && !StringUtils.isBlank((String) jsonObject.get("machineEqRemind")))
            templateDataItem.addItem("remark", (String) jsonObject.get("machineEqRemind"));
        else
            templateDataItem.addItem("remark", "点击进入小程序查看");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
//        String path = "/pages/history/th-history?sensorId={0}&type={1}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"),"dy");
        String path = "/pages/history/history?initType=5&sensorNo={0}";
        path = MessageTool.format(path,jsonObject.getString("sensorNo"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 获取患者姓名
     * @param doctorId
     * @return
     */
    private String getMemberName(String memberId){
        MemberPO member = this.memberService.getMemberById(memberId);
        return member == null ? "未知" : member.getMemberName();
    }

    /**
     * 定点血糖绑定通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void fixedBloodSugarBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "恭喜您，成功绑定血糖仪");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "血糖仪");
        templateDataItem.addItem("remark", "设备号："+ jsonObject.getString("machineSN"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 动态血糖-医生建议通知
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarDoctorAdviceHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_ADVICE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您收到一条新的医生建议，请及时查看");
        templateDataItem.addItem("keyword1", jsonObject.getString("doctorName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("keyword3", jsonObject.getString("memberName"));
        templateDataItem.addItem("remark", "点击查看");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
//        String path = "/pages/history/th-history?sensorId={0}&type={1}&origin=advice&startDt={2}&endDt={3}&statType={4}&monitorTime={5}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"),"dy" ,jsonObject.getString("startDt")
//                ,jsonObject.getString("endDt") ,jsonObject.getString("type") ,jsonObject.getString("monitorTime"));

        String path = "/pages/history/history?sensorId={0}&initType=5&origin=advice&startDt={1}&endDt={2}&statType={3}&monitorTime={4}";
        path = MessageTool.format(path,jsonObject.getString("sensorNo") ,jsonObject.getString("startDt")
                ,jsonObject.getString("endDt") ,jsonObject.getString("type") ,jsonObject.getString("monitorTime"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 动态血糖-近期情况概况通知（通知医生）
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarSurveyRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "患者" + jsonObject.getString("memberName") +"上传动态血糖数据提醒");
        templateDataItem.addItem("keyword1", "动态血糖报告");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", jsonObject.getString("text"));

        String url = Constant.PAGE_HOST + "/h5/index.html#/login?action={0}&memberId={1}&sensorNo={2}";
        url = MessageTool.format(url ,"dynamicBloodSugarRecentSurvey" ,jsonObject.getString("memberId") ,jsonObject.getString("sensorNo"));
        templateData.setUrl(url);
    }

    /**
     * 检查提醒通知处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void checkRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REVIEW_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，您的复查时间将至");
        templateDataItem.addItem("keyword1", jsonObject.getString("checkItem"));
        templateDataItem.addItem("keyword2", jsonObject.getString("reviewDt"));
    }

    /**
     * 筛查报告提醒处理
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningReportRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "您好，您的报告已生成。");
        templateDataItem.addItem("keyword1", ScreeningTool.getReportNameByType(jsonObject.getInteger("screeningType")));
        templateDataItem.addItem("keyword2", jsonObject.getString("reportDt").substring(0 ,10));
        templateDataItem.addItem("keyword3", "详细健康数据报告请点击查看");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "screeningReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * 课程报名成功提醒
     * @param wechatMessagePO
     * @param templateData
     */
    private void addCourseSuccessRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.SIGN_UP_SUCCESS_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "恭喜您，课程报名成功，快去学习吧!");
        templateDataItem.addItem("keyword1", jsonObject.getString("courseName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("datetime"));
        templateDataItem.addItem("keyword3", "点击开始学习>>");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-4/knowledge/knowledge?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_TAB));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * 健康预警通知（给医生）
     * @param wechatMessagePO
     * @param templateData
     */
    private void healthWarningNoticeToDoctorHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.HEALTH_WARNING_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "患者异常结果如下：");
        templateDataItem.addItem("keyword1", jsonObject.getString("warningTitle"));
        templateDataItem.addItem("keyword2", jsonObject.getString("warningContent"));
        templateDataItem.addItem("keyword3", jsonObject.getString("datetime"));
        templateDataItem.addItem("remark", jsonObject.getString("memberInfo"));
    }

    private String getDoctorOpenId(String doctorId){
        DoctorPO doctor = this.doctorServiceI.getDoctorById(doctorId);
        if(doctor == null){
            log.warn("获取医生openId失败，无法根据医生id找到匹配的医生数据！医生id:{}" ,doctorId);
            return null;
        }
        if(StringUtils.isBlank(doctor.getUnionId())){
            return null;
        }
        GetWechatJoinMapperDTO getWechatJoinMapperDTO = new GetWechatJoinMapperDTO();
        getWechatJoinMapperDTO.setUnionId(doctor.getUnionId());
        getWechatJoinMapperDTO.setJoinType(UserJoinConstant.JOIN_TYPE_GONG_ZHONG_HAO);
        UserWechatJoinPO userWechatJoin = this.userJoinInfoService.getUserWechatJoin(getWechatJoinMapperDTO);
        if(userWechatJoin == null){
            return null;
        }
        return userWechatJoin.getOpenId();
    }
}
