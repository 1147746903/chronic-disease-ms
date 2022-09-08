package com.comvee.cdms.machine.controller;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.machine.model.BedMachineModel;
import com.comvee.cdms.machine.model.ThpMemberMachine;
import com.comvee.cdms.machine.service.MemberMachineServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 
 * @author landd
 *
 */
@RestController
@RequestMapping("/web/memberMachine")
public class MemberMachineController {

	/**
     * 日志常量
     * @author nzq
     * @date
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberMachineController.class);
	
	@Autowired
	@Qualifier("memberMachineServiceNew")
	private MemberMachineServiceI memberMachineServiceNew;
	
	
	
	/**
	 * 判断SN编号是否存在
	 * @param request
	 * @param response
	 * @param sncode
	 */
	@RequestMapping("hasSNCode")
	public Result hasSNCode(HttpServletRequest request,
            HttpServletResponse response, String sncode) {
		try {
			Result result = new Result();
			result.setCode("0000");
			result.setMessage("SN验证服务已经关闭，请直接绑定验证！");
			result.setSuccess(true);
			return result;

			//sncode = "91724b5604be51962aa0ef7d56911b3d";
			/**
			 *
			 * url：
			 * http://192.168.5.213:8888/health/comveecrm/member/loadMachineByMachineId.do?machineId=91724b5604be51962aa0ef7d56911b3d
			 *
			 * url2:
			 * http://gsm.izhangkong.com/medicaldevice/machine/machineinfo?innerCode=id的md5&machineCode=imei码&platCode=HLJTRJ0012
			 *
			 * http://gsm.izhangkong.com/medicaldevice/machine/bind?innerCode=&platCode=HLJTRJ0012&machineType=02&timestamp=时间戳&acode=md5( "platCode="+platCode+"&innerCode="+innerCode+"&machineType="+machineType+"&timestamp="+timestamp+"&comvee&comvee")
			 *
			 * **/

			/*String url = Config.getValueByKey("thp_url");
			if(StringUtils.isBlank(url)){
				long times = System.currentTimeMillis();
				String acode = MD5Util.md5("platCode=HLJTRJ0011&innerCode="+sncode+"&machineType=02&timestamp="+times+"&comvee&comvee");
				url = Config.getValueByKey("thp_url2")
						+ MemberMachineConstant.EOH_EQUIPMENT_URL2;
				String argStr = "innerCode="+sncode+"&platCode=HLJTRJ0011&machineType=02&timestamp="+times+"&acode="+acode;
				url += "?" + argStr;
			} else {
				url += MemberMachineConstant.EOH_EQUIPMENT_URL;
				String argStr = "machineId=" + sncode;
				url += "?" + argStr;
			}
			LOGGER.info("远程调用地址:" + url);
			// 调绑定设备接口
			String sres = Utils.getFromHttp(url, "utf-8");
			LOGGER.info("远程调用返回:" + sres);
//			String sres = HttpClientInsideTool.httpInvokeToStr(url, map);
			Result result = new Result();
			if (sres == null || sres.equals("")) {
				result.setCode(MessageTool
						.getMessage("COMMON_SYSTEM_ERROR_CODE"));
				result.setMessage("查询SN设备内部服务器出错");
				result.setSuccess(false);
			} else {
				JSONObject resmap = JSON.parseObject(sres);
				String code = resmap.getString("code");
				
				if ("0".equals()) {
					result.setCode("0000");
					result.setMessage("验证SN正确");
					result.setObj(devMap);
					result.setSuccess(true);
				}
			}
			return result;*/
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			Result result = new Result();
			result.setMessage("系统错误");
			result.setSuccess(false);
			return result;
		}
	}
	
	/**
	 * 根据memberId查询已绑定的设备
	 * @param memberId
	 * @return
	 */
	@RequestMapping("listBindEquipmentByMemberId")
	public Result listBindEquipmentByMemberId(String memberId) {
		
		ThpMemberMachine model = new ThpMemberMachine();
		model.setMemberId(memberId);
		
		List<ThpMemberMachine> resList = this.memberMachineServiceNew.loadBindEquipmentByMemberId(model);
		Result result = new Result(resList);
		result.setCode("0000");
		return result;
	}

	/**
	 * 设备绑定、解绑
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("bindOrUnbindEquipment")
	public Result bindOrUnbindEquipment(@Validated ThpMemberMachine model, String bindFlag) {
		return this.memberMachineServiceNew.bindOrUnbindEquipment(model ,bindFlag);
	}

	/**
	 * @api {post}/web/memberMachine/bedToBindOrUnbindEquipment.do 床号:设备绑定、解绑
	 * @author wangt
	 * @time 2020/10/29 14:00
	 * @apName selectDepartmentAndBed 床号:设备绑定、解绑
	 * @apiGroup web-update-Bed
	 * @apiVersion 0.0.1
	 * @apiParam {String} machineSn  设备编号（必填）
	 * @apiParam {String} machineType  设备类型（必填，value：02）
	 * @apiParam {String} bedId  住院的主键id（必填）
	 * @apiSampleRequest  {post}/web/department/selectDepartmentAndBed.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("bedToBindOrUnbindEquipment")
	public Result bedToBindOrUnbindEquipment( BedMachineModel model, String bindFlag) {

		return this.memberMachineServiceNew.bedToBindOrUnbindEquipment(model ,bindFlag);
	}
	

}
