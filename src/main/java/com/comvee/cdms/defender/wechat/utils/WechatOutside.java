package com.comvee.cdms.defender.wechat.utils;

import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DigestTool;
import com.comvee.cdms.common.utils.RandomGenerator;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @File name:   WechatOutside.java   TODO调用丁香园微信接口
 * @Create on:   2018年8月2日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
@Component("wechatOutside")
public class WechatOutside {
	private static final Logger logger = LoggerFactory.getLogger(WechatOutside.class);
	
//	@Autowired
//	@Qualifier("patientService")
//	private PatientServiceI patientService;
	
	@Autowired
    private WechatMessageService wechatMessageService;
	
	
	public static String getPageId(String url,String simuid){
		return DigestTool.md5Hex(url);
	}
	
	public static String getCookieId(String url,String simuid){
		RandomGenerator rd = new RandomGenerator();
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("_").append(simuid).append(System.currentTimeMillis()+"").append(rd.nextLetter(6));
		return DigestTool.md5Hex(sb.toString());
	}
	
//	/**
//	 * 获取微信重定向地址
//	 * @TODO
//	 * @param url 要跳转的url
//	 * @return
//	 * @author zqx
//	 * @date 2018年8月2日
//	 */
//	public static String getTurnUrl(String url) {
//		if (url== null || url.equals("")) {
//			return "";
//		}
//		LOGGER.info("原url---》"+url);
//		try {
//			int j = url.indexOf("?");
//			if (j ==-1) {
//				int i = url.indexOf("?ticket");
//				if (i == -1) {
//					url+="?ticket=";
//				}else {
//					url = url.substring(0,i);
//					url+="?ticket=";
//				}
//			}else {
//				int i = url.indexOf("?ticket");
//				int m = url.indexOf("&ticket");
//				if (i == -1 && m >-1) {
//					url = url.substring(0,m);
//					url+="&ticket=";
//				}else if(i ==-1 && m == -1) {
//					url+="&ticket=";
//				}else {
//					url = url.substring(0,i);
//					url+="?ticket=";
//				}
//			}
//			url = Base64.encode(url);
//			url = url.replace('+', '-');
//			url = url.replace('/', '_');
//			url = url.replaceAll("=", "");
////			url = URLEncoder.encode(url, "utf-8");
//
//			LOGGER.info("加密后url---》"+url);
//			return DXYWECHATURL+TICKETURL+url;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(),e);
//			//ErrorMsgUtil.add(e.getMessage());
//		}
//		return "";
//	}
	
	public static String getTurnUrl(String url) {
		//http://suyzpage.ngrok.xiaomiqiu.cn/wechat/wechat_oauth.html?appId={appId}&backUrl={backUrl}
		 try {
			url = URLEncoder.encode(url, "utf-8");
			String temp = Config.getValueByKey("digital_wechat_oauth");
			return temp+"?appId=10000&backUrl="+url;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}
	
	/*
	@SuppressWarnings("unchecked")
	public PatientModel loadPatientInfo(String ticket){
		String digitalUrl = Config.getValueByKey("digital_url");
		String url = digitalUrl + "/third/member/getMemberInfoByToken.do";
		Map<String,String> paramMap=new HashMap<String, String>();
		paramMap.put("token", ticket);
		String str = HttpClientInsideTool.httpInvoke(url, paramMap);
		MemberModel member = JSON.parseObject(str, MemberModel.class);
		
		System.out.println(str);
		PatientModel patientModel = new PatientModel();
		patientModel.setPid(member.getMemberId());
		patientModel.setPname(member.getMemberName());
		patientModel.setPicUrl(member.getPicUrl());
		patientModel.setSex(member.getSex()+"");
		return patientModel;
	}
	*/
	
	
	public void sendMessageTemplate(String pid,String dataType,String dataJson){
		try {
			/*
			String digitalUrl = Config.getValueByKey("digital_url");
			String url = digitalUrl + "/third/wechat/sendMessageTemplate.do";
			Map<String,String> paramMap=new HashMap<String, String>();
			paramMap.put("memberId", pid);
			paramMap.put("dataType", dataType);
			paramMap.put("dataJson", dataJson);
			HttpClientInsideTool.httpInvoke(url, paramMap);
			*/
			
			AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
			addWechatMessageDTO.setMemberId(pid);
			addWechatMessageDTO.setDataType(Integer.valueOf(dataType));
			addWechatMessageDTO.setDataJson(dataJson);
			addWechatMessageDTO.setForeignId(DaoHelper.getSeq());
			wechatMessageService.addWechatMessage(addWechatMessageDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	

	
//	@SuppressWarnings("unchecked")
//	public PatientModel loadPatientInfo(String ticket){
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("appId", APPID);
//		param.put("timestamp", System.currentTimeMillis()/1000);
//		param.put("nonce", SignTool.getRandomString(16));
//		param.put("ticket", ticket);
//		String sign = SignTool.getSignObj(param,WECHATAPPSIGNKEY);
//		param.put("sign", sign);
//		String res = "";
//		String simuid = "";
//		String nickname = "";
//		PatientModel patientModel = new PatientModel();
//        try {
//        	LOGGER.info("获取微信用户信息ticket["+ticket+"]参数--》"+JSON.toJSONString(param));
//            res = HttpUtil.get(DXYWECHATURL+INFOTURL, param);
//        	LOGGER.info("获取微信用户信息ticket["+ticket+"]结果--》"+JSON.toJSONString(res));
//            if (res == null || res == "") {
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            Map<String, Object> resDocument = (Map<String, Object>) JSON.parseObject(res, Map.class);
//            if (resDocument == null) {
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            if (!(Boolean)resDocument.get("success")){
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            if (resDocument.get("simuid") != null && !resDocument.get("simuid").equals("")) {
//				simuid = resDocument.get("simuid")+"";
//			}
//            if (resDocument.get("nickname") != null && !resDocument.get("nickname").equals("")) {
//				nickname = resDocument.get("nickname")+"";
//			}
//            
//            if (simuid != null && !simuid.equals("")) {
//            	patientModel = loadPatientBySimuid(simuid);
//            	if (resDocument.get("avatar") != null && patientModel != null) {
//					patientModel.setAvatar(resDocument.get("avatar")+"");
//					patientModel.setSimuid(simuid);
//					patientModel.setNickname(nickname);
//				}
//            	if (patientModel==null || patientModel.getStatus() == null ) {
//            		patientModel = new PatientModel();
//            		/***(帐号不存在的用户)扫码关注的用户***/
//            		patientModel.setSimuid(simuid);
//            		patientModel.setId(simuid);
//            		patientModel.setPatientType("-1");
//            		patientModel.setStatus("3");
//				} else if( patientModel !=null && patientModel.getStatus() != null && patientModel.getStatus().equals("5")){//出组用户
//					patientModel = new PatientModel();
//				}
//			}
//        } catch (WechatException e) {
//
//			if (e.getMessage().isEmpty()) {
//				LOGGER.error(e.getMsg());
//				//ErrorMsgUtil.add(e.getMsg());
//			}else {
//				LOGGER.error(e.getMessage(),e);
//				//ErrorMsgUtil.add(e.getMessage());
//			}
////        	LOGGER.error(e.getMessage()+";"+e.getMsg()+";获取微信用户信息失败；参数-->"+JSON.toJSONString(param));
//        	//throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//        	//LOGGER.error("结果---->"+e.getMessage());
//        }
//        return patientModel;
//	}
	
//	@SuppressWarnings("unchecked")
//	public FamilyModel loadFamilyInfo(String ticket,String shareId){
//		FamilyModel familyModel = null;
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("appId", APPID);
//		param.put("timestamp", System.currentTimeMillis()/1000);
//		param.put("nonce", SignTool.getRandomString(16));
//		param.put("ticket", ticket);
//		String sign = SignTool.getSignObj(param,WECHATAPPSIGNKEY);
//		param.put("sign", sign);
//		String res = "";
//		String simuid = "";
//        try {
//        	LOGGER.info("获取微信用户信息ticket["+ticket+"]参数--》"+JSON.toJSONString(param));
//            res = HttpUtil.get(DXYWECHATURL+INFOTURL, param);
//        	LOGGER.info("获取微信用户信息ticket["+ticket+"]结果--》"+JSON.toJSONString(res));
//            if (res == null || res == "") {
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            Map<String, Object> resDocument = (Map<String, Object>) JSON.parseObject(res, Map.class);
//            if (resDocument == null) {
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            if (!(Boolean)resDocument.get("success")){
//            	throw new WechatException("10001", "获取微信用户信息失败；参数-->"+JSON.toJSONString(param)+"；结果-->"+res, "");
//			}
//            if (resDocument.get("simuid") != null && !resDocument.get("simuid").equals("")) {
//				simuid = resDocument.get("simuid")+"";
//			}
//            if (simuid != null && !simuid.equals("")) {
//            	//判断亲属关系
//            	familyModel = this.patientService.loadFamilyInfo(shareId, simuid);
////            	if(FamilyModel == null || FamilyModel.getPid() == null){
////            		//patientModel = new PatientModel();
////            		return true;
////            	} else {
////            		return false;
////            	}
//			}
//        } catch (WechatException e) {
//
//			if (e.getMessage().isEmpty()) {
//				LOGGER.error(e.getMsg());
//				//ErrorMsgUtil.add(e.getMsg());
//			}else {
//				LOGGER.error(e.getMessage(),e);
//				//ErrorMsgUtil.add(e.getMessage());
//			}
//        }
//        return familyModel;
//	}

//	//@Cacheable(cacheNames = "cache_patient",key = "#simuid",unless="#result == null")
//	public PatientModel loadPatientBySimuid(String simuid){
//		PatientModel patientModel = null;
//		try {
//			//patientModel = this.patientService.loadPatientBySimuid(simuid);
//			//权限等级 lccp权限 --> 普通权限用户 (5freepen -->6普通 ) --> follow权限用户
//			//礼来用户优先级最高，如果存在礼用户权限的话，以礼来用户登录
//			patientModel = this.patientService.loadBySimuid(simuid, ConstantPatient.PATIENT_ROLE_1,null);
//			if(patientModel != null && patientModel.getId() != null && patientModel.getStatus()  !=null){
//				return patientModel;
//			}
//			
//			//freepen
//			patientModel = this.patientService.loadBySimuid(simuid, null ,"5");
//			if(patientModel != null && patientModel.getId() != null && patientModel.getStatus()  !=null){
//				return patientModel;
//			}
//			
//			//普通用户
//			patientModel = this.patientService.loadBySimuid(simuid, null ,"6");
//			if(patientModel != null && patientModel.getId() != null && patientModel.getStatus()  !=null){
//				return patientModel;
//			}
//			
//			//follow用户
//			patientModel = this.patientService.loadBySimuid(simuid, ConstantPatient.PATIENT_ROLE_3,null);
//			if(patientModel != null && patientModel.getId() != null && patientModel.getStatus()  !=null){
//				return patientModel;
//			}
//			
//			//用户不存在默认为follow用户
//			if(patientModel == null){
//				patientModel = new PatientModel();
//	    		/***(帐号不存在的用户)扫码关注的用户***/
//	    		patientModel.setSimuid(simuid);
//	    		patientModel.setId(simuid);
//	    		patientModel.setPatientType("-1");
//	    		patientModel.setStatus("3");
//			}
//			
//        } catch (WechatException e) {
//        	LOGGER.error(e.getMessage(),e);
//        }
//        return patientModel;
//	}
	
	public static void main(String[] args) {
		//String uString = "aHR0cDovL2xsdGVzdC5pemhhbmdrb25nLmNvbS8vZGVmZW5kZXIvaHRtbC9ub3ZpY2UtdGFzay9ub3ZpY2UtdGFzay5qc3A_dGlja2V0PQ";
		//System.out.println(Base64.decodeStr(uString));
	}
}
