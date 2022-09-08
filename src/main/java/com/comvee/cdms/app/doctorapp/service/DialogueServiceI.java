package com.comvee.cdms.app.doctorapp.service;

import com.comvee.cdms.app.doctorapp.model.app.DialogueLatestForDoctorModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.dialogue.dto.ListDialogueLatestMapperDTO;
import com.comvee.cdms.dialogue.dto.ListDialogueServiceDTO;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;

import java.util.List;
import java.util.Map;

public interface DialogueServiceI {
	
	/**
	 * 加载消息页
	 * @param page
	 * @param doctorId
	 * @return
	 */
	public Map<String,Object> doctorHomePage(PageRequest page,String doctorId);
	
	/**
	 * 刷新消息页
	 * @param page
	 * @param doctorId
	 * @param returnDate
	 * @return
	 */
	public Map<String,Object> refreshHomePage(PageRequest page, List<String> doctorList , Long returnDate);

	public void docMemberDialogue(PageRequest page, String memberId,String lastDate);
	
	/**
	 * 加载 医生对话消息列表里面的 通知
	 * @param doctorId
	 * @return
	 */
	public Map<String,Object> loadLatestNotification(String doctorId);
	
	/**
	 * 删除对话
	 * @param doctorId
	 * @param memberId
	 */
	public void deleteHomeNews(String doctorId , String memberId);
	
	/**
	 * 清除消息小红点
	 * @param doctorId
	 * @param memberId
	 */
	public void removeHomeCount(String doctorId , String memberId);
	
	/**
	 * 加载医生最新消息列表
	 */
	public ListDialogueLatestVO<DialogueLatestForDoctorModel> listDialogueLatest(PageRequest page ,ListDialogueLatestMapperDTO listDialogueLatestMapperDTO);
	
	/**
	 * 加载医生对话
	 * @param pageRequestModel
	 * @param listDialogueServiceDTO
	 * @return
	 */
    Map<String,Object> listDialogue(PageRequest pageRequestModel, ListDialogueServiceDTO listDialogueServiceDTO);

	/**
	 * 医生撤回消息
	 * @param sid
	 * @param doctorId
	 * @param memberId
	 */
	public AddDialogueReturnVO recallDocDialogue(String sid, String memberId, String doctorId);

}
