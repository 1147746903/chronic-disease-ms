package com.comvee.cdms.prescription.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.defender.wechat.mapper.PatientCourseMapper;
import com.comvee.cdms.knowledge.mapper.MemberKnowledgeServiceMapper;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CoursePushJob{
	
	private final static Logger log = LoggerFactory.getLogger(CoursePushJob.class);
	
	@Autowired
	private PatientCourseMapper patientCourseMapper;
	
	@Autowired
	private MemberRemindService memberRemindService;
	
	@Autowired
	private MemberKnowledgeServiceMapper memberKnowledgeServiceMapper;

	@Autowired
	private WechatMessageService wechatMessageService;

	@XxlJob("coursePushJob1")
	public ReturnT<String> execute(String param) throws Exception {
		log.info("开始处理课程推送....");
		
		Map<String, Object> paramMap = new HashMap<>();

        List<Map<String, Object>> dataList = patientCourseMapper.listMemberKnowledgeCourseForLearne(paramMap);

		if(dataList != null && dataList.size() > 0) {
			dataList.forEach(item -> {
				String memberId = String.valueOf(item.get("member_id"));
				String id = String.valueOf(item.get("id"));
				String prescriptionId = String.valueOf(item.get("prescriptionId"));
				Map<String,String> jsonMap = new HashMap<>();
				jsonMap.put("patientCourseId", String.valueOf(item.get("patientCourseId")));
				jsonMap.put("memo", String.valueOf(item.get("memo")));
				jsonMap.put("title", String.valueOf(item.get("title")));
				jsonMap.put("knowledge", String.valueOf(item.get("knowledge")));
				jsonMap.put("article_id", String.valueOf(item.get("article_id")));
				jsonMap.put("prescriptionId", prescriptionId);
				jsonMap.put("knowledgeType", String.valueOf(item.get("knowledgeType")));

				String datetime = String.valueOf(item.get("plan_push_dt"));//DateHelper.getDate((Date)item.get("plan_push_dt"), DateHelper.DATETIME_FORMAT);
				jsonMap.put("datetime", datetime);
				
				//推送智能小助
				AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
				addMemberRemindDTO.setMemberId(memberId);
				addMemberRemindDTO.setForeignId(id);
				addMemberRemindDTO.setRemindType(8);
				addMemberRemindDTO.setRemindMessage("今日学习计划");
				addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(jsonMap));
				memberRemindService.addMemberRemind(addMemberRemindDTO);
				
				memberKnowledgeServiceMapper.modifyEohKnowledgeHasLearned(id);

				AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
				addWechatMessageDTO.setMemberId(memberId);
				addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_COURSE_REMIND);
				addWechatMessageDTO.setForeignId(prescriptionId);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("prescriptionId" , prescriptionId);
				jsonObject.put("knowledgeType" , item.get("knowledgeType"));
				jsonObject.put("patientCourseId" , item.get("patientCourseId"));
				jsonObject.put("title" , item.get("title"));
				jsonObject.put("knowledge" , item.get("knowledge"));
				jsonObject.put("learnDate" , item.get("learn_dt"));
				jsonObject.put("articleId" , item.get("article_id"));
				addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
				this.wechatMessageService.addWechatMessage(addWechatMessageDTO);

//				wechatTemplateMsgService.addKnowledgeTemplateMsgByPid(memberId, String.valueOf(item.get("title")), datetime, "/pages/knowledge/knowledge");
			});
		}
		//更改t_prescription_knowledge表的学习时间字段
		
		
		
		//公众号推送
		
		
		return ReturnT.SUCCESS;
	}

}
