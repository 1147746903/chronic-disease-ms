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
     * ??????????????????
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
     * ????????????
     * @param wechatMessagePO
     */
    private MessageHandlerResultBO messageHandler(WechatMessagePO wechatMessagePO){
        MessageHandlerResultBO messageHandlerResult = new MessageHandlerResultBO();
        messageHandlerResult.setSid(wechatMessagePO.getSid());
        String openId = null;
        if(WechatMessageConstant.USER_TYPE_PATIENT == wechatMessagePO.getUserType()){
            //??????openId
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
                //??????
                case WechatMessageConstant.DATA_TYPE_CONSULT:
                    consultHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR:
                    bloodSugarHandler(wechatMessagePO, templateData);
                    break;
                    //??????
                case WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE:
                    bloodPressureHandler(wechatMessagePO, templateData);
                    break;
                    //bmi
                case WechatMessageConstant.DATA_TYPE_BMI:
                    bmiHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_PRESCRIPTION:
                    prescriptionHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_PRESCRIPTION_KNOWLEDGE:
                	//????????????????????????????????????????????????
                    //knowledgeHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_FOLLOW_QUESTION:
                    followQuestionHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_FOLLOW_REPORT:
                    followReportHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MONITOR:
                    monitorRemindHandler(wechatMessagePO, templateData);
                    break;
                    //??????
                case WechatMessageConstant.DATA_TYPE_REVISIT:
                    revisitHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_MONITOR_PLAN:
                    monitorPlanHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_CONTROL_TARGET:
                    controlTargetHandler(wechatMessagePO, templateData);
                    break;
                    //???????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_UN_MONITOR:
                    unMeasureHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_PACKAGE_EXPIRE_REMIND:
                    expirePackageRemindHandler(wechatMessagePO, templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_PACKAGE_EXPIRE_WARN:
                    expirePackageWarnHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_ADD_PACKAGE:
                    addPackageHandler(wechatMessagePO, templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_SCREENING_RETURN_VISIT_REMIND:
                    screeningReturnVisitRemindHandler(wechatMessagePO, templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_SCREENING_KNOWLEDGE_PUSH:
                    screeningKnowledgePushHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_SCREENING_ORDER_REMIND:
                    screeningOrderRemind(wechatMessagePO, templateData);
                    break;
                    //???????????????
                case WechatMessageConstant.DATA_TYPE_NEW_CUSTOM_FOLLOW:
                    customFollowQuestionHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????????????????
                case WechatMessageConstant.DATA_TYPE_SCREENING_FOOT_PRESCRIPTION:
                    screeningFootPrescriptionHandler(wechatMessagePO, templateData);
                    break;
                    // ??????????????????????????????
                case WechatMessageConstant.DATA_TYPE_FOLLOWUP_CARE:
                    followupCareHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_HBA1C:
                    hba1cHandler(wechatMessagePO, templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_SUGAR_MONTH_REPORT:
                    sugarMonthReportHandler(wechatMessagePO, templateData);
                    break;
                    //??????????????????(??????)
                case WechatMessageConstant.DATA_TYPE_FOLLOW_ZJ_REPORT:
                    followZjReportHandler(wechatMessagePO,templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE_MONITOR:
                    hypMonitorRemindHandler(wechatMessagePO,templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_BIND:
                    bloodSugarBindHandler(wechatMessagePO,templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_UNBIND:
                    bloodSugarUnBindHandler(wechatMessagePO,templateData);
                    break;
                    //??????????????????????????????
                case WechatMessageConstant.DATA_TYPE_BLOODSUGAR_SHARE_BIND:
                    bloodSugarShareBindHandler(wechatMessagePO,templateData);
                    break;
                    //?????????????????????????????????
                case WechatMessageConstant.DATA_TYPE_COURSE_REMIND:
                    knowledgePushHandler(wechatMessagePO ,templateData);
                    break;
                    //??????????????????????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_REPORT:
                    dynamicBloodSugarReportHandler(wechatMessagePO,templateData);
                    break;
                    //???????????????????????????
                case WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MACHINE_BIND:
                    fixedBloodSugarBindHandler(wechatMessagePO,templateData);
                    break;
                    //????????????????????????????????????
                case WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_DOCTOR_ADVICE:
                    dynamicBloodSugarDoctorAdviceHandler(wechatMessagePO,templateData);
                    break;
                    //??????????????????????????????
                case WechatMessageConstant.DATA_TYPE_DYNAMIC_BLOOD_SUGAR_SURVEY_REMIND:
                    dynamicBloodSugarSurveyRemindHandler(wechatMessagePO,templateData);
                    break;
                    //??????????????????
                case WechatMessageConstant.DATA_TYPE_CHECK_REMIND:
                    checkRemindHandler(wechatMessagePO,templateData);
                    break;
                    //????????????
                case WechatMessageConstant.DATA_TYPE_SCREENING_REPORT:
                    screeningReportRemindHandler(wechatMessagePO,templateData);
                    break;
                    //????????????????????????
                case WechatMessageConstant.DATA_TYPE_ADD_COURSE_SUCCESS:
                    addCourseSuccessRemindHandler(wechatMessagePO,templateData);
                    break;
                    //?????????????????????????????????
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
            log.error("??????????????????????????????" ,e);
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
     * ??????????????????(????????????)
     * @param wechatMessagePO
     * @param templateData
     */
    private void knowledgePushHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.LEARNING_PROGRESS_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first","?????????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1",jsonObject.getString("title"));
        templateDataItem.addItem("keyword2",jsonObject.getString("learnDate"));
        templateDataItem.addItem("remark", "??????????????????>>");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}&knowledgeType={2}&articleId={3}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "knowledgePush" , jsonObject.getString("knowledgeType") ,jsonObject.getString("articleId"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void sugarMonthReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "??????????????????????????????????????????????????????~");
        templateDataItem.addItem("keyword1", "??????????????????");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "sugarMonthReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }
    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void hba1cHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????:");
        templateDataItem.addItem("keyword1", "??????????????????");
        templateDataItem.addItem("keyword2", jsonObject.getString("recordValue") + "%");
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("recordDt"));
        templateDataItem.addItem("remark", "??????:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=4";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void followupCareHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.CHRONIC_DISEASE_FOLLOWUP_REMIND_TEAMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "??????????????????????????????????????????????????????~");
        templateDataItem.addItem("keyword1", DateHelper.getToday());
        templateDataItem.addItem("keyword2", jsonObject.getString("senderInfo"));
        templateDataItem.addItem("remark", jsonObject.getString("careContent"));

        String pageUrl = Constant.PAGE_HOST + "/remind/mobile/remind.html?sid=" + wechatMessagePO.getForeignId();
        templateData.setUrl(pageUrl);
    }

    /**
     * ??????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningFootPrescriptionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        Integer footType = jsonObject.getInteger("footType");
        String keyword1 = "";
        String firstText = "";
        if (null != footType && footType == FootConstant.FOOT_TYPE_ZCJ){
            keyword1 = "??????????????????";
            firstText = "??????????????????????????????????????????????????????~";
        }else {
            keyword1 = "??????????????????????????????";
            firstText = "??????????????????????????????????????????????????????????????????~";
        }
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", firstText);
//        templateDataItem.addItem("keyword1", "??????????????????");
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
     * ???????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void customFollowQuestionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getString("followName");
        templateDataItem.addItem("first", followName + "??????????????????");
        templateDataItem.addItem("keyword1", followName + "??????");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");


        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, jsonObject.getString("followId"), "customFollow");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningOrderRemind(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_RETURN_VISIT_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "???????????????????????????????????????~");
        templateDataItem.addItem("keyword1", jsonObject.getString("hospitalName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", MessageTool.format("??????????????????{0}???????????????????????????,???????????????????????????????????????~" , jsonObject.getString("screeningItem")));
    }

    /**
     * ??????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningKnowledgePushHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_KNOWLEDGE_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "???????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("titleName"));
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", jsonObject.getString("summaryText"));
        templateData.setUrl(Constant.PAGE_HOST + "/complication/mobile/page/know_detail.html?sid=" + wechatMessagePO.getForeignId());
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningReturnVisitRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
//        templateData.setTemplate_id(WechatMessageTemplate.SCREENING_RETURN_VISIT_REMIND_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????????????????~");
        templateDataItem.addItem("keyword1", "??????????????????");
        templateDataItem.addItem("keyword2", "???????????????");
        templateDataItem.addItem("remark", "???????????????????????????????????????????????????????????????????????????????????????????????????~???????????????????????????????????????????????????????????????????????????????????????????????????~");
        templateData.setUrl(Constant.PAGE_HOST + "/complication/mobile/page/foot_return_visit_item.html");
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void expirePackageRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String packageName = jsonObject.getString("packageName");
//        templateData.setTemplate_id(WechatMessageTemplate.PACKAGE_EXPIRE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("????????????????????????{0}???????????????", packageName));
        templateDataItem.addItem("keyword1", jsonObject.getString("memberPackageId"));
        templateDataItem.addItem("keyword2", packageName);
        templateDataItem.addItem("keyword3", jsonObject.getString("endDt"));
        templateDataItem.addItem("remark", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void expirePackageWarnHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String packageName = jsonObject.getString("packageName");
        int expireDay = jsonObject.getInteger("expireDay");
//        templateData.setTemplate_id(WechatMessageTemplate.PACKAGE_EXPIRE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("????????????????????????{0}????????????{1}????????????", packageName, expireDay));
        templateDataItem.addItem("keyword1", jsonObject.getString("memberPackageId"));
        templateDataItem.addItem("keyword2", packageName);
        templateDataItem.addItem("keyword3", jsonObject.getString("endDt"));
        templateDataItem.addItem("remark", MessageTool.format("????????????????????????{0}????????????{1}?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", packageName ,expireDay));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void addPackageHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        String doctorName = getDoctorName(jsonObject.getString("doctorId"));
//        templateData.setTemplate_id(WechatMessageTemplate.NEW_PACKAGE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("?????????????????????{0}????????????", doctorName));
        templateDataItem.addItem("keyword1", jsonObject.getString("packageName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "?????????????????????????????????????????????????????????????????????????????????????????????????????????");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/service/serviceDetails?id=" + jsonObject.getString("memberPackageId");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }
    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void unMeasureHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        int days = jsonObject.getInteger("days");
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_REPLAY_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", MessageTool.format("????????????????????????{0}?????????????????????", days));
        templateDataItem.addItem("keyword1", "????????????");
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", jsonObject.getString("content"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberSuagr";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void controlTargetHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_REPLAY_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", getDoctorName(jsonObject.getString("doctorId")));
        templateDataItem.addItem("keyword2", DateHelper.getToday());
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/monitor/monitor?initType=2";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void monitorPlanHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.MONITOR_PLAN_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();

        //????????????????????????????????? 1 ???????????? 3 ????????????
        Integer initType = 1;
        String firstText = "????????????????????????????????????????????????????????????";
        Integer illnessType = jsonObject.getInteger("illnessType");
        if(illnessType != null && MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION == illnessType){
            firstText = "????????????????????????????????????????????????????????????";
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
     * ??????????????????
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
        templateDataItem.addItem("first", "??????????????????????????????????????????");
        templateDataItem.addItem("keyword1", hospitalName);
        templateDataItem.addItem("keyword2", jsonObject.getString("revisitDate"));

        String remark = MessageTool.format(REVISIT_REMAKR_CONTENT, jsonObject.getInteger("day"), hospitalName);
        templateDataItem.addItem("remark", remark);

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/track/track";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    private final static String REVISIT_REMAKR_CONTENT = "{0}??????????????????{1}?????????????????????????????????????????????????????????";



    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void monitorRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.INTELLIGENT_NURSING_START_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", "??????");
        templateDataItem.addItem("keyword2", "????????????");
        templateDataItem.addItem("keyword3", getTimeCodeText(jsonObject.getString("timeCode")));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberSuagr?illnessType=" + MonitorPlanConstant.ILLNESS_TYPE_DIABETES;
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void hypMonitorRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.INTELLIGENT_NURSING_START_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", "??????");
        templateDataItem.addItem("keyword2", "????????????");
        templateDataItem.addItem("keyword3", getHypTimeCodeText(jsonObject.getString("timeCode")));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-3/remember/rememberBloodPre?illnessType=" + MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION;
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ?????????????????????
     * @param timeCode
     * @return
     */
    private String getTimeCodeText(String timeCode){
        if(StringUtils.isBlank(timeCode)){
            return "???";
        }
        List<String> codeList = Arrays.asList(timeCode.split(","));
        List<String> textList = new ArrayList<>();
        codeList.forEach( x -> {
            textList.add(TIME_CODE_TEXT.get(Integer.parseInt(x)));
        });
        return Joiner.on("???").skipNulls().join(textList);
    }

    private final static Map<Integer, String> TIME_CODE_TEXT = new HashMap<>();
    static {
        TIME_CODE_TEXT.put( 1, "??????");
        TIME_CODE_TEXT.put( 2, "??????");
        TIME_CODE_TEXT.put( 3, "?????????");
        TIME_CODE_TEXT.put( 4, "?????????");
        TIME_CODE_TEXT.put( 5, "?????????");
        TIME_CODE_TEXT.put( 6, "?????????");
        TIME_CODE_TEXT.put( 7, "?????????");
        TIME_CODE_TEXT.put( 8, "??????");
    }

    /**
     * ?????????????????????????????????
     * @param timeCode
     * @return
     */
    private String getHypTimeCodeText(String timeCode){
        if(StringUtils.isBlank(timeCode)){
            return "???";
        }
        List<String> codeList = Arrays.asList(timeCode.split(","));
        LinkedHashSet<String> codeSet = new LinkedHashSet<>();
        for(String s : codeList){
            codeSet.add(HYP_TIME_CODE_TEXT.get(s.split("-")[1]));
        }
        return Joiner.on("???").join(codeSet);
    }

    private final static Map<String ,String> HYP_TIME_CODE_TEXT = new HashMap<>();
    static {
        HYP_TIME_CODE_TEXT.put("1" , "?????????");
        HYP_TIME_CODE_TEXT.put("2" , "?????????");
        HYP_TIME_CODE_TEXT.put("3" , "??????");
        HYP_TIME_CODE_TEXT.put("4" , "??????");
        HYP_TIME_CODE_TEXT.put("5" , "??????");
        HYP_TIME_CODE_TEXT.put("6" , "??????");
        HYP_TIME_CODE_TEXT.put("7" , "??????");
        HYP_TIME_CODE_TEXT.put("8" , "??????");
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void prescriptionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "??????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", "????????????");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type=eoh";
        path = MessageTool.format(path, jsonObject.getString("prescriptionId"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void followQuestionHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.FOLLOW_UP_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getInteger("type") == FollowConstant.FOLLOW_TYPE_CUSTOM ? jsonObject.getString("followName") : followNameHandler(jsonObject.getInteger("type"));
//        templateDataItem.addItem("first", followName + "??????????????????");
//        templateDataItem.addItem("keyword1", followName + "??????");
        templateDataItem.addItem("first", followName + "????????????");
        templateDataItem.addItem("keyword1", followName);
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String templateId = jsonObject.getString("templateId") == null ? "-1" : jsonObject.getString("templateId");
        String path = "/pages/history/th-history?logId={0}&type={1}&textType={2}&followtype={3}&doctorId={4}&memberId={5}&templateId={6}&ftype={7}";
            path = MessageTool.format(path, jsonObject.getString("followId"), "follow", 1, jsonObject.getString("type")
            ,jsonObject.getString("doctorId") ,jsonObject.getString("memberId"),templateId,jsonObject.getString("fType"));

        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void followReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        String followName = jsonObject.getInteger("type") == FollowConstant.FOLLOW_TYPE_CUSTOM ? jsonObject.getString("followName") : followNameHandler(jsonObject.getInteger("type"));
//        String followName = followNameHandler(jsonObject.getInteger("type"));
//        templateDataItem.addItem("first", followName + "??????????????????????????????");
//        templateDataItem.addItem("keyword1", followName + "????????????????????????");
        templateDataItem.addItem("first", followName + "??????????????????");
        templateDataItem.addItem("keyword1", followName + "??????");
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
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void followZjReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first","??????????????????????????????????????????????????????~");
        templateDataItem.addItem("keyword1","??????????????????");
        templateDataItem.addItem("keyword2",jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path,jsonObject.getString("reportId"),"followReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param type
     * @return
     */
    private String followNameHandler(Integer type){
        String name = "";
        if(type == FollowConstant.FOLLOW_TYPE_FIRST){
            name = "???????????????";
        }else if(type == FollowConstant.FOLLOW_TYPE_DAY){
            name = "????????????";
        }else if(type == FollowConstant.FOLLOW_TYPE_QUES){
            name = "??????????????????";
        }else if(type == FollowConstant.FOLLOW_TYPE_QUES_FOLLOW){
            name = "?????????????????????";
        }else if(type == FollowConstant.FOLLOW_TYPE_FOLLOW){
            name = "??????????????????";
        }else if (type == FollowConstant.FOLLOW_TYPE_TWO_DIABETES){
            name = "2?????????????????????";
        }else if (type == FollowConstant.FOLLOW_TYPE_HEALTH_ACCESS){
            name = "????????????";
        }else if (type == FollowConstant.FOLLOW_TYPE_FIRST_GXY){
            name = "???????????????";
        }else if (type == FollowConstant.FOLLOW_TYPE_HYP_JW){
            name = "???????????????";
        }else if (type == FollowConstant.FOLLOW_TYPE_TNB_ASSESS){
            name = "?????????????????????";
        }
        return name;
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????:");
        templateDataItem.addItem("keyword1", "??????");
        templateDataItem.addItem("keyword2", jsonObject.getString("value") + "mmol/L");
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "??????:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodPressureHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????:");
        templateDataItem.addItem("keyword1", "??????");
        templateDataItem.addItem("keyword2", bloodPressureValueHandler(jsonObject));
        templateDataItem.addItem("keyword3", bloodPressureLevelTextHandler(jsonObject));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "??????:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=2";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ???????????????
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
     * ??????????????????
     * @param jsonObject
     * @return
     */
    private String bloodPressureLevelTextHandler(JSONObject jsonObject){
        String text = "";
        Integer dbpLevel = jsonObject.getInteger("dbpLevel");
        Integer sbpLevel = jsonObject.getInteger("sbpLevel");
        if(SignConstant.SIGN_LEVEL_HIGH == dbpLevel
                || SignConstant.SIGN_LEVEL_HIGH == sbpLevel){
            text = "??????";
        }else if(SignConstant.SIGN_LEVEL_LOW == dbpLevel
                || SignConstant.SIGN_LEVEL_LOW == sbpLevel){
            text = "??????";
        }else{
            text = "??????";
        }
        return text;
    }

    /**
     * bmi????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bmiHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.MEASUREMENT_RESULT_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????:");
        templateDataItem.addItem("keyword1", "BMI");
        templateDataItem.addItem("keyword2", bmiValueHandler(jsonObject));
        templateDataItem.addItem("keyword3", getLevelText(jsonObject.getInteger("level")));
        templateDataItem.addItem("keyword4", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "??????:" + jsonObject.getString("suggest"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=3";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * bmi?????????
     * @param jsonObject
     * @return
     */
    private String bmiValueHandler(JSONObject jsonObject){
        StringBuilder builder = new StringBuilder();
        builder.append(jsonObject.getString("bmi"))
                .append(" (??????").append(jsonObject.getString("height"))
                .append("cm,").append("??????").append(jsonObject.getString("weight"))
                .append("kg)");
        return builder.toString();
    }

    /**
     * ????????????????????????
     * @param level
     * @return
     */
    private String getLevelText(Integer level){
        String text = "";
        if(SignConstant.SIGN_LEVEL_LOW == level){
            text = "??????";
        }else if(SignConstant.SIGN_LEVEL_NORMAL == level){
            text = "??????";
        }else if(SignConstant.SIGN_LEVEL_HIGH == level){
            text = "??????";
        }
        return text;
    }
    /**
     * ??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void consultHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());

        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.DOCTOR_ADVICE_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
//        templateDataItem.addItem("first", consultTitle(jsonObject));
        templateDataItem.addItem("first", "???????????????????????????,?????????");
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


    private final static String CONSULT_TITLE = "????????????????????????{0}??????";

    private String consultTitle(JSONObject jsonObject){
        int textType = jsonObject.getInteger("textType");
        String title = "";
        if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT){
            title = MessageTool.format(CONSULT_TITLE, "??????");
        }else if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE){
            title = MessageTool.format(CONSULT_TITLE, "??????");
        }else if(textType == DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE){
            title = MessageTool.format(CONSULT_TITLE, "??????");
        }
        return title;
    }

    /**
     * ??????openId
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
     * ??????????????????
     * @param doctorId
     * @return
     */
    private String getDoctorName(String doctorId){
        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(doctorId);
        return doctorPO == null ? "??????" : doctorPO.getDoctorName();
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "????????????");
        templateDataItem.addItem("remark", "????????????"+jsonObject.getString("sensorNo"));

        //***********?????????***********
        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history?initType=5&sensorNo={0}";
        path = MessageTool.format(path,jsonObject.getString("sensorNo"));
//        String path = "/pages/history/th-history?sensorId={0}&type={1}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"), "dy");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarUnBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.UNBIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", getMemberName(jsonObject.getString("sendMemberId"))+"???????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("sensorNo"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", "");

        //***********?????????***********
/*        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path,jsonObject.getString("reportId"),"followReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);*/
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void bloodSugarShareBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "??????????????????");
        templateDataItem.addItem("remark", "???????????????"+getMemberName(jsonObject.getString("sendMemberId"))+"???????????????"+jsonObject.getString("sensorNo"));

        //***********?????????***********
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
     * ??????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarReportHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", jsonObject.getString("memberName") + "??????????????????????????????");
        templateDataItem.addItem("keyword1", "??????????????????");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        if(jsonObject.containsKey("machineEqRemind") && jsonObject.get("machineEqRemind") instanceof String
                && !StringUtils.isBlank((String) jsonObject.get("machineEqRemind")))
            templateDataItem.addItem("remark", (String) jsonObject.get("machineEqRemind"));
        else
            templateDataItem.addItem("remark", "???????????????????????????");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
//        String path = "/pages/history/th-history?sensorId={0}&type={1}";
//        path = MessageTool.format(path,jsonObject.getString("sensorNo"),"dy");
        String path = "/pages/history/history?initType=5&sensorNo={0}";
        path = MessageTool.format(path,jsonObject.getString("sensorNo"));
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ??????????????????
     * @param doctorId
     * @return
     */
    private String getMemberName(String memberId){
        MemberPO member = this.memberService.getMemberById(memberId);
        return member == null ? "??????" : member.getMemberName();
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void fixedBloodSugarBindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.BIND_RESULT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("date"));
        templateDataItem.addItem("keyword2", "?????????");
        templateDataItem.addItem("remark", "????????????"+ jsonObject.getString("machineSN"));

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/history_page?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path,WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ????????????-??????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarDoctorAdviceHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.DOCTOR_ADVICE_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "???????????????????????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("doctorName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("keyword3", jsonObject.getString("memberName"));
        templateDataItem.addItem("remark", "????????????");

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
     * ????????????-??????????????????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void dynamicBloodSugarSurveyRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
//        templateData.setTemplate_id(WechatMessageTemplate.REPORT_TEMPLATE);
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "??????" + jsonObject.getString("memberName") +"??????????????????????????????");
        templateDataItem.addItem("keyword1", "??????????????????");
        templateDataItem.addItem("keyword2", jsonObject.getString("date"));
        templateDataItem.addItem("remark", jsonObject.getString("text"));

        String url = Constant.PAGE_HOST + "/h5/index.html#/login?action={0}&memberId={1}&sensorNo={2}";
        url = MessageTool.format(url ,"dynamicBloodSugarRecentSurvey" ,jsonObject.getString("memberId") ,jsonObject.getString("sensorNo"));
        templateData.setUrl(url);
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void checkRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REVIEW_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("checkItem"));
        templateDataItem.addItem("keyword2", jsonObject.getString("reviewDt"));
    }

    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void screeningReportRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.REPORT_GENERATION_NOTIFICATION));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "?????????????????????????????????");
        templateDataItem.addItem("keyword1", ScreeningTool.getReportNameByType(jsonObject.getInteger("screeningType")));
        templateDataItem.addItem("keyword2", jsonObject.getString("reportDt").substring(0 ,10));
        templateDataItem.addItem("keyword3", "???????????????????????????????????????");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages/history/th-history?logId={0}&type={1}";
        path = MessageTool.format(path, wechatMessagePO.getForeignId(), "screeningReport");
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_PAGE));
        templateData.setMiniprogram(miniProgramData);
    }


    /**
     * ????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void addCourseSuccessRemindHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.SIGN_UP_SUCCESS_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "????????????????????????????????????????????????!");
        templateDataItem.addItem("keyword1", jsonObject.getString("courseName"));
        templateDataItem.addItem("keyword2", jsonObject.getString("datetime"));
        templateDataItem.addItem("keyword3", "??????????????????>>");

        TemplateData.MiniProgramData miniProgramData = WechatMessageTool.defaultMiniProgramData();
        String path = "/pages-4/knowledge/knowledge?initType=1";
        miniProgramData.setPagepath(WechatMessageTool.miniProgramPathHandler(path, WechatMessageTool.JUMP_TYPE_TAB));
        templateData.setMiniprogram(miniProgramData);
    }

    /**
     * ?????????????????????????????????
     * @param wechatMessagePO
     * @param templateData
     */
    private void healthWarningNoticeToDoctorHandler(WechatMessagePO wechatMessagePO, TemplateData templateData){
        JSONObject jsonObject = JSONObject.parseObject(wechatMessagePO.getDataJson());
        templateData.setTemplate_id(getTemplateId(WechatMessageTemplate.HEALTH_WARNING_REMINDER));
        TemplateData.TemplateDataItem templateDataItem = templateData.getData();
        templateDataItem.addItem("first", "???????????????????????????");
        templateDataItem.addItem("keyword1", jsonObject.getString("warningTitle"));
        templateDataItem.addItem("keyword2", jsonObject.getString("warningContent"));
        templateDataItem.addItem("keyword3", jsonObject.getString("datetime"));
        templateDataItem.addItem("remark", jsonObject.getString("memberInfo"));
    }

    private String getDoctorOpenId(String doctorId){
        DoctorPO doctor = this.doctorServiceI.getDoctorById(doctorId);
        if(doctor == null){
            log.warn("????????????openId???????????????????????????id????????????????????????????????????id:{}" ,doctorId);
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
