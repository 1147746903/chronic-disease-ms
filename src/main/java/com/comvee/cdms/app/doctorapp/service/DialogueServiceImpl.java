package com.comvee.cdms.app.doctorapp.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.model.app.DialogueLatestForDoctorModel;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.dto.ListDialogueLatestMapperDTO;
import com.comvee.cdms.dialogue.dto.ListDialogueServiceDTO;
import com.comvee.cdms.dialogue.dto.UnReadDialogueDTO;
import com.comvee.cdms.dialogue.mapper.DialogueMapper;
import com.comvee.cdms.dialogue.po.DialogueLatestPO;
import com.comvee.cdms.dialogue.po.TeamDialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.MemberCurrentDiffLevelVO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.follow.mapper.FollowMapper;
import com.comvee.cdms.follow.po.MainFollowPO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.dto.ListDoctorMemberDTO;
import com.comvee.cdms.member.mapper.MemberApplyMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("dialogueAppService")
public class DialogueServiceImpl implements DialogueServiceI{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FollowMapper followMapper;
	
	@Autowired
	private MemberApplyMapper memberApplyMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private DialogueService dialogueService;
	
	@Autowired
	private DialogueMapper dialogueMapper;

	@Autowired
	private MemberService memberService;


	@Autowired
	private DifferentLevelsService differentLevelsService;

	@Autowired
	private DoctorServiceI doctorService;

	//加载最新的对话后  会有未读的小红点 第一次加载不用刷新时间
	@Override
	public Map<String,Object> doctorHomePage(PageRequest page, String doctorId) {
		//1加载对话消息列表
        PageHelper.startPage(page.getPage(), page.getRows());
        
        ListDialogueLatestMapperDTO listDialogueLatestMapperDTO = new ListDialogueLatestMapperDTO();
        listDialogueLatestMapperDTO.setDoctorId(doctorId);
        listDialogueLatestMapperDTO.setBeDelete(0);
        List<DialogueLatestPO> dialogueList = dialogueMapper.listDialogueLatest(listDialogueLatestMapperDTO);//取到消息列表

        PageResult<DialogueLatestPO> pageResult = new PageResult(dialogueList);
        String returnDate = "";
        if(dialogueList.size()>0) {
        	returnDate = dialogueList.get(0).getLatestDt();
        }
//        if(dialogueList != null) {
//        	if(dialogueList.get(0).getDoctorTimestamp()>dialogueList.get(0).getPatientTimestamp()) {
//        		returnDate = dialogueList.get(0).getDoctorTimestamp();
//        	}else {
//        		returnDate = dialogueList.get(0).getPatientTimestamp();
//        	}
//        }
        
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("returnDate", returnDate);
        resultMap.put("pager", pageResult);        
        return resultMap;
	}	
	
	@Override
	public Map<String, Object> refreshHomePage(PageRequest page, List<String> doctorList, Long returnDate) {
//        PageHelper.startPage(page.getPage(), page.getRows());
        ListDialogueLatestMapperDTO listDialogueLatestMapperDTO = new ListDialogueLatestMapperDTO();
		listDialogueLatestMapperDTO.setDoctorList(doctorList);
        listDialogueLatestMapperDTO.setDoctorRefreshTime(returnDate);
        List<DialogueLatestPO> dialogueList = dialogueMapper.listDialogueLatest(listDialogueLatestMapperDTO);//取到消息列表

        if(dialogueList.size()>0) {
        	returnDate = dialogueList.get(0).getDoctorTimestamp();

        }
        
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("returnDate", returnDate);
        resultMap.put("pager", dialogueList);        
        return resultMap;
	}	
	
	@Override
	public void deleteHomeNews(String doctorId, String memberId) {
		this.dialogueService.deleteDialogueOfDoctorMember(doctorId, memberId);
	}
	
	@Override
	public void removeHomeCount(String doctorId, String memberId) {
		UnReadDialogueDTO unReadDialogueDTO = new UnReadDialogueDTO();
		unReadDialogueDTO.setDoctorId(doctorId);
		unReadDialogueDTO.setMemberId(memberId);
		this.dialogueService.clearUnread(memberId, doctorId, 2);
	}	
	
	@Override
	public void docMemberDialogue(PageRequest page, String memberId, String lastDate) {

	}
	
	@Override
	public Map<String,Object> loadLatestNotification(String doctorId) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		//最新消息加载 好友申请提示
		Long a = this.memberApplyMapper.countUntreatedApply("1",doctorId);//00未处理
		returnMap.put("applyNumber", a);
		DoctorMemberApplyPO applyPO = this.memberApplyMapper.getLastPatientRequest("1" , doctorId);//好友申请中 1未处理   只取一条
		if(applyPO !=null ) {
			returnMap.put("applyDt", applyPO.getApplyDt());
			GetMemberDTO getMemberDTO = new GetMemberDTO();
			getMemberDTO.setMemberId(applyPO.getMemberId());
			MemberPO memberInfo = this.memberMapper.getMember(getMemberDTO);
			if(memberInfo != null) {
				returnMap.put("memberName",memberInfo.getMemberName());
			}
		}
		
		return returnMap;
		
	}

	@Override
	public ListDialogueLatestVO<DialogueLatestForDoctorModel> listDialogueLatest(PageRequest page , ListDialogueLatestMapperDTO listDialogueLatestMapperDTO) {
		ListDialogueLatestVO<DialogueLatestForDoctorModel> resultList = new ListDialogueLatestVO<DialogueLatestForDoctorModel>();
		
		PageHelper.startPage(page.getPage(), page.getRows());
		List<DialogueLatestPO> list = this.dialogueMapper.listDialogueLatest(listDialogueLatestMapperDTO);

		if(list == null || list.size() == 0) {
            PageResult<DialogueLatestForDoctorModel> pageResult = (new PageResult()).createEmptyPageResult();
            resultList.setPageResult(pageResult);
            resultList.setTimestamp(System.currentTimeMillis());
            return resultList;
		}
		
		
		PageResult<DialogueLatestPO> poPageResult = new PageResult(list);
        List<DialogueLatestPO> rows = poPageResult.getRows();

        if(rows == null || rows.size() == 0){
            PageResult<DialogueLatestForDoctorModel> pageResult = poPageResult.createEmptyPageResult();
            resultList.setPageResult(pageResult);
            resultList.setTimestamp(System.currentTimeMillis());
            return resultList;
        }

        List<String> memberIdList = rows.stream().map(DialogueLatestPO::getMemberId).collect(Collectors.toList());
		String hospitalId = null;
        if(StringUtils.isBlank(listDialogueLatestMapperDTO.getDoctorId()) && listDialogueLatestMapperDTO.getDoctorList()!=null
				&&listDialogueLatestMapperDTO.getDoctorList().size()>0){
			hospitalId = getHospitalId(listDialogueLatestMapperDTO.getDoctorList().get(0));
		} else {
			hospitalId = getHospitalId(listDialogueLatestMapperDTO.getDoctorId());
		}
        String hid = hospitalId;

        //获取列表的患者信息
        List<MemberPO> memberList = this.memberMapper.listMemberByIdList(memberIdList);        
        Map<String, MemberPO> memberPOMap = memberList.stream().collect(Collectors.toMap(MemberPO::getMemberId, x -> x ,(a ,b) -> a));
        
        List<DialogueLatestForDoctorModel> resultModels = new ArrayList();
        rows.forEach(x -> {
        	DialogueLatestForDoctorModel resultModel = new DialogueLatestForDoctorModel();
            BeanUtils.copyProperties(resultModel, x);
            MemberPO memberPO = memberPOMap.get(x.getMemberId());
            if(memberPO != null){
            	resultModel.setPicUrl(memberPO.getPicUrl());
            	resultModel.setMemberName(memberPO.getMemberName());
            	resultModel.setSex(memberPO.getSex());
				MemberCurrentDiffLevelVO vo = differentLevelsService.getMemberCurrentDiffLevelResult(memberPO.getMemberId(),hid);
            	if(vo==null || vo.getLayer()==null){
					resultModel.setLayer(0);
				} else {
					resultModel.setLayer(vo.getLayer());
				}

            }
			ListDoctorMemberDTO listDoctorMemberDTO = new ListDoctorMemberDTO();
			listDoctorMemberDTO.setMemberId(x.getMemberId());
			listDoctorMemberDTO.setDoctorId(x.getDoctorId());
			DoctorMemberPO doctorMember = this.memberService.getDoctorMember(listDoctorMemberDTO);
			if(doctorMember != null){
				resultModel.setPriceFlag(doctorMember.getPriceFlag());
			}else{
				resultModel.setPriceFlag(String.valueOf(MemberDoctorConstant.PAY_STATUS_NO));
			}
            resultModels.add(resultModel);
        });        
        PageResult<DialogueLatestForDoctorModel> pageResult = poPageResult.createEmptyPageResult();
        pageResult.setRows(resultModels);
        resultList.setPageResult(pageResult);
        resultList.setTimestamp(System.currentTimeMillis());        
        
		return resultList;
	}

	/**
	 * web取来 方法进行改造
	 * msgType = 1 文本 又有  1.文本 2 图片 3 语音的 区别 
	 * 其他的为卡片格式 如随访 、 管理处方
	 */
	@Override
    public Map<String,Object> listDialogue(PageRequest pageRequest , ListDialogueServiceDTO listDialogueServiceDTO) {
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        Map<String,Object> queryMap = new HashMap<>(6);
        queryMap.put("doctorId", listDialogueServiceDTO.getDoctorId());
        queryMap.put("memberId", listDialogueServiceDTO.getMemberId());
        queryMap.put("upTimeStamp", listDialogueServiceDTO.getUpTimeStamp());
        queryMap.put("downTimeStamp", listDialogueServiceDTO.getDownTimeStamp());
        //可在医生端和医生web显示
        String[] showClients = {"1","3"};
        queryMap.put("showClient",showClients);
        queryMap.put("beDelete", listDialogueServiceDTO.getBeDelete());
		List<TeamDialoguePO> list = this.dialogueMapper.listTeamDialogue(queryMap);
        
        for (TeamDialoguePO dialoguePO : list) {
			//随访类型的时候
        	if(dialoguePO.getMsgType() == 4) {
				JSONObject jsStr = JSONObject.parseObject(dialoguePO.getDataStr());
				Integer followType = jsStr.getInteger("followType");
				Integer dealStatus = jsStr.getInteger("textType");
				String url = "";
/*				if(followType.equals(1)){
					if(dealStatus.equals(DialogueConstant.DIALOGUE_FOLLOW_TEXT_TYPE_REPORT)) {
						url = "/follow/mobile/follow_first_result.html?followId="+dialoguePO.getForeignId()+"&type=1&memberId="+dialoguePO.getMemberId()+"&doctorId="+dialoguePO.getDoctorId();
					}else {
						url = "/follow/mobile/follow_first.html?followId="+dialoguePO.getForeignId()+"&type=1&memberId="+dialoguePO.getMemberId()+"&doctorId="+dialoguePO.getDoctorId();
					}
				} else {
					if(dealStatus.equals(DialogueConstant.DIALOGUE_FOLLOW_TEXT_TYPE_REPORT)) {
						url = "/follow/mobile/follow_day_result.html?followId="+dialoguePO.getForeignId()+"&type=2&memberId="+dialoguePO.getMemberId()+"&doctorId="+dialoguePO.getDoctorId();
					}else {
						url = "/follow/mobile/follow_day.html?followId="+dialoguePO.getForeignId()+"&type=2&memberId="+dialoguePO.getMemberId()+"&doctorId="+dialoguePO.getDoctorId();
					}
				}*/

				Integer dStatus=1;
				MainFollowPO mainFollowPO = followMapper.getMainFollowByFidAndType(dialoguePO.getForeignId(), followType);
				if(null!=mainFollowPO && 1==mainFollowPO.getStatus()) {
					dStatus = 2;
				}
				//source 1患者 2医生
				if (!dialoguePO.getTemplateId().equals("-1")){
					url = "/follow/page_transfer.html?followId="+dialoguePO.getForeignId()+"&memberId="+dialoguePO.getMemberId()
							+"&doctorId="+dialoguePO.getDoctorId() + "&followType=" + followType + "&textType=" + dStatus + "&clientType=2" + "&templateId=" + dialoguePO.getTemplateId();
				}else{
					url = "/follow/page_transfer.html?followId="+dialoguePO.getForeignId()+"&memberId="+dialoguePO.getMemberId()
							+"&doctorId="+dialoguePO.getDoctorId() + "&followType=" + followType + "&textType=" + dStatus + "&clientType=2";
				}
				jsStr.put("url", url);
				//随访状态 1已处理，0未处理
				jsStr.put("dealStatus", dealStatus);
				dialoguePO.setDataStr(String.valueOf(jsStr));

        	}
		}
        
        PageResult<TeamDialoguePO> pageResult = new PageResult<>(list);
        Map<String,Object> map = new HashMap<>(2);
        map.put("pageResult", pageResult);
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

	/**
	 * 医生撤回消息
	 * @param sid
	 * @param doctorId
	 * @param memberId
	 */
	public AddDialogueReturnVO recallDocDialogue(String sid, String memberId, String doctorId){
		return  this.dialogueService.recallDocDialogue(sid, memberId, doctorId);
	}

	private String getHospitalId(String doctorId){
		if(StringUtils.isBlank(doctorId)){
			return null;
		}
		String hospitalId = null;
		DoctorPO doctorP = this.doctorService.loadDoctorInfo(doctorId);
		if(doctorP != null){
			hospitalId = doctorP.getHospitalId();
		}
		return hospitalId;
	}

}
