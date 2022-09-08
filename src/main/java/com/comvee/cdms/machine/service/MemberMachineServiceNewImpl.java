package com.comvee.cdms.machine.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.machine.common.utils.Utils;
import com.comvee.cdms.machine.constant.MemberMachineConstant;
import com.comvee.cdms.machine.mapper.ThpBedMachineMapper;
import com.comvee.cdms.machine.mapper.ThpMemberMachineMapper;
import com.comvee.cdms.machine.model.BedMachineModel;
import com.comvee.cdms.machine.model.ThpMemberMachine;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author landd
 *
 */
@Service("memberMachineServiceNew")
public class MemberMachineServiceNewImpl implements MemberMachineServiceI {

	@Autowired
	private ThpMemberMachineMapper memberMachineMapper;

	@Autowired
	private WechatMessageService wechatMessageService;

	@Autowired
	private ThpBedMachineMapper thpBedMachineMapper;

//    @Autowired
//	@Qualifier("taslyInvoke")
//    private TaslyInvokeImpl taslyInvokeImpl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ThpMemberMachine> loadBindEquipmentByMemberId(ThpMemberMachine model) {
		return this.memberMachineMapper.selectByModel(model);
	}

	@Override
	public Result bindEquipment(ThpMemberMachine model){
		Result result = new Result(Constant.CONST_NUM_00,"绑定成功",true);
		List<ThpMemberMachine> list = this.memberMachineMapper.selectByModel(model);
		if(list!=null && list.size()>0) {
			result.setCode("-1");
			result.setMessage("该患者已绑定该设备");
			result.setSuccess(false);
			return result;
		}
		this.memberMachineMapper.bindEquipment(model);
		
		/*
		MemberPO memberModel = memberMapper.getMemberTslForeignIdByMemberId(model.getMemberId()+"");
        if(!ValidateTool.checkIsNull(memberModel)){
            throw new HealthException("0001","成员不存在","");
        }
        try {
			taslyInvokeImpl.bindingNotify(memberModel.getTslForeignId(), model.getMachineId(), model.getMachineType());
		} catch (Exception e) {
			logger.error("调用天士力绑定设备失败：" + e);
		}
		*/
		return result;
	}

	@Override
	public void unBindEquipment(ThpMemberMachine model) {
		this.memberMachineMapper.unBindEquipment(model);
		
		/*
		MemberPO memberModel = memberMapper.getMemberTslForeignIdByMemberId(model.getMemberId()+"");
		try {
			taslyInvokeImpl.unBundlingNotify(memberModel.getTslForeignId(), model.getMachineId(), model.getMachineType());;
		} catch (Exception e) {
			logger.error("调用天士力接绑设备失败：" + e);
		}
		*/
	}

	@Override
	public Long countMachineByMachineId(String machineId) {
		return this.memberMachineMapper.countMachineByMachineId(machineId);
	}

	@Override
	public Long countMachineToBedByMachineId(String machineId) {
		return this.thpBedMachineMapper.countMachineToBedByMachineId(machineId);

	}

	public Result bedBindEquipment(BedMachineModel model){
		Result result = new Result(Constant.CONST_NUM_00,"绑定成功",true);
		//根据传过来的参数查询设备号是否被绑定,被绑定不能再次被绑定。
		List<BedMachineModel> list = this.thpBedMachineMapper.selectByBedMachineModel(model);
		if(list!=null && list.size()>0) {
			result.setCode("-1");
			result.setMessage("该设备已被绑定");
			result.setSuccess(false);
			return result;
		}
		this.thpBedMachineMapper.bedBindEquipment(model);
		return result;
	}

	public void unBedBindEquipment(String bedId) {
		this.thpBedMachineMapper.unBedBindEquipment(bedId);
	}

	@Override
	public Result bindOrUnbindEquipment(ThpMemberMachine model, String bindFlag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("machineId", model.getMachineId());
		map.put("machineType", model.getMachineType());
		map.put("bindFlag", bindFlag);
		//查询设备是否绑定
		Result result = new Result();
		Long temp = countMachineByMachineId(model.getMachineId());
		if(temp>0 && bindFlag.equals("1")) {//设备已被绑定
			result.setMessage("该设备已被绑定,不能重复绑定");
			result.setSuccess(false);
		} else {
			if(StringUtils.isBlank(bindFlag)){
				result.setMessage("bindFlag参数不可为空");
				result.setSuccess(false);
			} else if(bindFlag.equals("1")) {//绑定
//					Long sid = Long.parseLong(DaoHelper.getSeq());
				String sid = DaoHelper.getSeq();
				model.setSid(sid);
				Result resultModel = bindEquipment(model);//调用本地的设备绑定
				if(!resultModel.getSuccess()) {
					result.setCode("0000");
					result.setMessage("该患者已绑定该设备!");
					result.setSuccess(false);
				} else {
					String url = Config.getValueByKey("thp_url");
					if(StringUtils.isBlank(url)){
						url = Config.getValueByKey("thp_url2")
								+ MemberMachineConstant.EOH_EQUIPMENT_URL2;
						url += "?" + urlArgStringHandler(model.getMachineId());
					} else {
						url += MemberMachineConstant.EOH_EQUIPMENT_URL;
						String argStr = "machineId=" + model.getMachineId();
						url += "?" + argStr;
					}
					logger.info("远程调用地址:" + url);
					// 调绑定设备接口
					String sres = Utils.getFromHttp(url, "utf-8");
					logger.info("绑定调用返回:" + sres);
					if (sres == null || sres.equals("")) {
						unBindEquipment(model);//调用本地的设备解绑
						result.setCode(MessageTool
								.getMessage("COMMON_SYSTEM_ERROR_CODE"));
						result.setMessage("绑定设备第三方服务器出错");
						result.setSuccess(false);
					} else {
						JSONObject resmap = JSON.parseObject(sres);
						String bindCode = resmap.getString("code");
						if ("0".equals(bindCode) || "-3".equals(bindCode)) {
							Map<String, String> retObj = new HashMap<String, String>();
							retObj.put("sid", sid);
							retObj.put("machineType", model.getMachineType());
							result.setCode("0000");
							result.setMessage("设备绑定成功");
							result.setObj(retObj);
							result.setSuccess(true);
							pushBindSuccessWechatMessage(model.getMemberId() , model.getMachineSn());
						} else if("-1".equals(bindCode)){
							unBindEquipment(model);//调用本地的设备解绑
							result.setCode("1111");
							result.setMessage("SN不存在");
							result.setSuccess(false);
						} else {
							unBindEquipment(model);//调用本地的设备解绑
							result.setCode("8888");
							result.setMessage(resmap.getString("msg"));
							result.setSuccess(false);
						}
					}
				}
			} else {//解绑
				String url = Config.getValueByKey("thp_url2") + MemberMachineConstant.EOH_EQUIPMENT_UNBIND_URL +"?" + urlArgStringHandler(model.getMachineId());
				String sres = Utils.getFromHttp(url, "utf-8");
				logger.info("解绑调用返回:" + sres);
				if (sres == null || sres.equals("")) {
					result.setCode(MessageTool
							.getMessage("COMMON_SYSTEM_ERROR_CODE"));
					result.setMessage("绑定设备第三方服务器出错");
					result.setSuccess(false);
				}else {
					JSONObject resmap = JSON.parseObject(sres);
					String bindCode = resmap.getString("code");
					if ("0".equals(bindCode) || "-3".equals(bindCode)) {
						unBindEquipment(model);//调用本地的设备解绑
						result.setCode("0000");
						result.setMessage("设备解绑成功");
						result.setObj("设备解绑成功");
						result.setSuccess(true);
					}else{
						result.setCode("8888");
						result.setMessage(resmap.getString("msg"));
						result.setSuccess(false);
					}
				}
			}
		}
		return result;
	}

	@Override
	public Result bedToBindOrUnbindEquipment(BedMachineModel model, String bindFlag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bedId", model.getBedId());
		map.put("machineType", model.getMachineType());
		map.put("bindFlag", bindFlag);
		//查询设备是否绑定
		Result result = new Result();
		Long temp = countMachineToBedByMachineId(model.getMachineId());
		if(temp>0 && bindFlag.equals("1")) { //设备已被绑定
			result.setMessage("该设备已被绑定,不能重复绑定");
			result.setSuccess(false);
		} else {
			if(StringUtils.isBlank(bindFlag)){
				result.setMessage("bindFlag参数不可为空");
				result.setSuccess(false);
			} else if(bindFlag.equals("1")) { //绑定
//					Long sid = Long.parseLong(DaoHelper.getSeq());
				String sid = DaoHelper.getSeq();
				model.setSid(sid);
				Result resultModel = bedBindEquipment(model);//调用本地的设备绑定
				if(!resultModel.getSuccess()) {
					result.setCode("0000");
					result.setMessage("该设备已被绑定!");
					result.setSuccess(false);
				} else {
					String url = Config.getValueByKey("thp_url");
					if(StringUtils.isBlank(url)){
						url = Config.getValueByKey("thp_url2")
								+ MemberMachineConstant.EOH_EQUIPMENT_URL2;
						url += "?" + urlArgStringHandler(model.getMachineId());
					} else {
						url += MemberMachineConstant.EOH_EQUIPMENT_URL;
						String argStr = "machineId=" + model.getMachineId();
						url += "?" + argStr;
					}
					logger.info("远程调用地址:" + url);
					// 调绑定设备接口
					String sres = Utils.getFromHttp(url, "utf-8");
					logger.info("绑定调用返回:" + sres);
					if (sres == null || sres.equals("")) {
						unBedBindEquipment(model.getBedId());//调用本地的设备解绑
						result.setCode(MessageTool
								.getMessage("COMMON_SYSTEM_ERROR_CODE"));
						result.setMessage("绑定设备第三方服务器出错");
						result.setSuccess(false);
					} else {
						JSONObject resmap = JSON.parseObject(sres);
						String bindCode = resmap.getString("code");
						if ("0".equals(bindCode) || "-3".equals(bindCode)) {
							Map<String, String> retObj = new HashMap<String, String>();
							retObj.put("sid", sid);
							retObj.put("machineType", model.getMachineType());
							result.setCode("0000");
							result.setMessage("设备绑定成功");
							result.setObj(retObj);
							result.setSuccess(true);
//							pushBedBindSuccessWechatMessage(model.getBedId() , model.getMachineSn());
						} else if("-1".equals(bindCode)){
							unBedBindEquipment(model.getBedId());//调用本地的设备解绑
							result.setCode("1111");
							result.setMessage("SN不存在");
							result.setSuccess(false);
						} else {
							unBedBindEquipment(model.getBedId());//调用本地的设备解绑
							result.setCode("8888");
							result.setMessage(resmap.getString("msg"));
							result.setSuccess(false);
						}
					}
				}
			} else {//解绑
				String url = Config.getValueByKey("thp_url2") + MemberMachineConstant.EOH_EQUIPMENT_UNBIND_URL +"?" + urlArgStringHandler(model.getMachineId());
				String sres = Utils.getFromHttp(url, "utf-8");
				logger.info("解绑调用返回:" + sres);
				if (sres == null || sres.equals("")) {
					result.setCode(MessageTool
							.getMessage("COMMON_SYSTEM_ERROR_CODE"));
					result.setMessage("绑定设备第三方服务器出错");
					result.setSuccess(false);
				}else {
					JSONObject resmap = JSON.parseObject(sres);
					String bindCode = resmap.getString("code");
					if ("0".equals(bindCode) || "-3".equals(bindCode)) {
						unBedBindEquipment(model.getBedId());//调用本地的设备解绑
						result.setCode("0000");
						result.setMessage("设备解绑成功");
						result.setObj("设备解绑成功");
						result.setSuccess(true);
					}else{
						result.setCode("8888");
						result.setMessage(resmap.getString("msg"));
						result.setSuccess(false);
					}
				}
			}
		}
		return result;
	}



	/**
	 * 参数串处理
	 * @param machineId
	 * @return
	 */
	private String urlArgStringHandler(String machineId){
		long times = System.currentTimeMillis();
		String acode = MD5Util.md5("platCode=HLJTRJ0012&innerCode="+machineId+"&machineType=02&timestamp="+times+"&comvee&comvee");
		String argStr = "innerCode="+machineId+"&platCode=HLJTRJ0012&machineType=02&timestamp="+times+"&acode="+acode;
		return argStr;
	}

	/**
	 * 推送微信消息 - 绑定成功
	 * @param memberId
	 * @param machineSN
	 */
	private void pushBindSuccessWechatMessage(String memberId ,String machineSN){
		JSONObject dataJson = new JSONObject();
		dataJson.put("date" , DateHelper.getNowDate());
		dataJson.put("machineSN" ,machineSN);
		AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
		addWechatMessageDTO.setMemberId(memberId);
		addWechatMessageDTO.setForeignId(machineSN);
		addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MACHINE_BIND);
		addWechatMessageDTO.setDataJson(dataJson.toJSONString());
		this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
	}

	/**
	 * 推送微信消息 - 床位绑定成功
	 * @param bedId
	 * @param machineSN
	 */
	private void pushBedBindSuccessWechatMessage(String bedId ,String machineSN){
		JSONObject dataJson = new JSONObject();
		dataJson.put("date" , DateHelper.getNowDate());
		dataJson.put("machineSN" ,machineSN);
		AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
		addWechatMessageDTO.setMemberId(bedId);
		addWechatMessageDTO.setForeignId(machineSN);
		addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MACHINE_BIND);
		addWechatMessageDTO.setDataJson(dataJson.toJSONString());
		this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
	}
}
