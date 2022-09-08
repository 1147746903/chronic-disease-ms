package com.comvee.cdms.clinicaldiagnosis.controller.web;

import com.comvee.cdms.app.doctorapp.service.DrugsMemberServiceI;
import com.comvee.cdms.clinicaldiagnosis.constant.YzOperation;
import com.comvee.cdms.clinicaldiagnosis.dto.*;
import com.comvee.cdms.clinicaldiagnosis.po.YzPO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzAndDrugVO;
import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzExecuteLogVO;
import com.comvee.cdms.clinicaldiagnosis.vo.YzVO;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.drugs.vo.DrugsMemberVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/web/yz")
public class YzController {

	@Autowired
	@Qualifier("yzService")
	private YzServiceI yzService;

	@Autowired
	private DrugsMemberServiceI drugsMemberService;

	/**
	 * @api {post}/web/yz/listDrugRecordByMemberId.do 根据患者id，获取用药分页记录
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName listDrugRecordByMemberId 根据患者id，获取用药分页记录
	 * @apiGroup web-yz
	 * @apiVersion 4.0.0
	 * @apiParam {String} page 页码
	 * @apiParam {String} row 页数
	 * @apiParam {String} memberId 患者编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/yz/listDrugRecordByMemberId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listDrugRecordByMemberId")
	public Result listDrugRecordByMemberId(String page, String row, String memberId) {
		
		PageResult<MemberYzAndDrugVO> memberYzAndDrugList = this.yzService.listDrugRecordByMemberId(page,row,memberId);
		Result result = new Result(memberYzAndDrugList);
		return result;
	}

	/**
	 * @api {post}/web/yz/listYzRecordByMemberId.do 获取医嘱分页记录
	 * @author 林雨堆
	 * @time 2020/03/19
	 * @apiName listYzRecordByMemberId 获取医嘱分页记录
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} page 页码
	 * @apiParam {String} row 页数
	 * @apiParam {String} memberId 患者编号 必填
	 * @apiParam {Integer} yzItemType 医嘱项目类型 2 护理 3 检验检查 4 其他
	 * @apiParam {Integer} yzType 医嘱类型 1 长期医嘱 2 临时医嘱
	 * @apiParam {String} startDt 开始时间
	 * @apiParam {String} endDt 结束时间
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/yz/listYzRecordByMemberId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listYzRecordByMemberId")
	public Result listYzRecordByMemberId(String page, String row, ListYzRecordDTO dto) {

		PageResult<MemberYzAndDrugVO> memberYzAndDrugList = this.yzService.listYzRecordByMemberId(page,row,dto);
		Result result = new Result(memberYzAndDrugList);
		return result;
	}

	/**
	 * @api {post}/web/yz/listYzDrugNewByMemberId.do 根据患者id，获取最新用药记录
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName listYzDrugNewByMemberId 根据患者id，获取最新用药记录
	 * @apiGroup web-yz
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/yz/listYzDrugNewByMemberId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listYzDrugNewByMemberId")
	public Result listYzDrugNewByMemberId(String memberId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("memberId", memberId);
		List<YzVO> yzList = this.yzService.listYzDrugNewByMemberId(paramMap);
		Result result = new Result(yzList);
		return result;
	}

	/**
	 * @api {post}/web/yz/listDrugYzOfOutHospitalMember.do 获取出院患者的用药医嘱
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName listDrugYzOfOutHospitalMember 获取出院患者的用药医嘱
	 * @apiGroup web-yz
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/yz/listDrugYzOfOutHospitalMember.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listDrugYzOfOutHospitalMember")
	public Result listDrugYzOfOutHospitalMember(String memberId) {
		List<YzVO> yzList = this.yzService.listDrugYzOfOutHospitalMember(memberId);
		Result result = new Result(yzList);
		return result;
	}

	/**
	 * @api {post}/web/yz/listYzDrugNearlyTwoMonths.do 获取近两个月的用药记录-废弃
	 * @author 林雨堆
	 * @time 2018/07/23
	 * @apiName listYzDrugNearlyTwoMonths 获取近两个月的用药记录
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {String} drugType 1-降糖药，2-降脂药，3-降压药，4-胰岛素，5-常用药
	 * @apiParam {Integer} dtype 用药记录来源1 通用 2南京鼓楼
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/yz/listYzDrugNearlyTwoMonths.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@Deprecated
	@RequestMapping("listYzDrugNearlyTwoMonths")
	public Result listYzDrugNearlyTwoMonths(String memberId,Integer dType,String drugType) {
		ValidateTool.checkParamIsNull(memberId, "memberId");
		List<DrugsMemberVO> list = this.drugsMemberService.listDrugsNearlyTwoMonthsOfMember(memberId,DateHelper.plusDate(DateHelper.getToday(),-60)
				, DateHelper.getToday(),dType,drugType);
		Result result = new Result(list);
		return result;
	}

	/**
	 * @api {post}/web/yz/addMemberDrugYz.do 添加患者用药记录
	 * @author 林雨堆
	 * @time 2018/07/23
	 * @apiName addMemberDrugYz 添加患者用药记录
	 * @apiGroup web-yz
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {String} visitNo 就诊号 (可选，院外患者不填)
	 * @apiParam {String} priorityCode 主医嘱类型 (可选，默认1 ，1用药 2护理，3检验检查，4其他)
	 * @apiParam {JSONArray} drugJson 用药数组JSON (必填)
	 * JSONArray结构:
	 * [
	 *   {
	 *		"type":"",//必填，用药类型 1 长期 2 临时
	 *		"drugName":"",//必填，药品名称
	 *		"drugCode":"",//必填，药品编号
	 *		"drugDose":"",//必填，药品剂量（服用剂量）
	 *		"drugFreqCode":"",//必填，频次代码
	 *		"price":"",//必填，药品价格
	 *		"startDt":"",//必填，开始时间
	 *		"stopDt":"",//可选，结束时间
	 *		"drugDoseUnit":"",//可选，药品剂量单位
	 *		"drugDoseUnitDesc":"",//可选，药品剂量单位中文（例如：片，颗，粒）
	 *		"drugFreqDesc":"",//可选，频次说明
	 *		"drugSpecification":"",//可选，药品规格
	 *		"drugDurationCode":"",//可选，疗程代码
	 *		"drugDurationDesc":"",//可选，疗程说明
	 *		"emergency":"",//可选，默认N, 是否紧急 Y 是 N不是
	 *		"skinTest":"",//可选，默认N, 是否需要皮试 Y 是 N不是
	 *		"usePlanJson":  //可选，用药计划
	 *						//usePlanJson结构：
	 *						 "{ "cycle" : "2" , //循环类型
	 *                       "dayTime" : 2 , //日期类型
	 *                       "drugType" : "1" , //药品类型
	 *                       "drugName" : "优降糖" , //药品名称
	 *                       "endDt" : "2019-01-21" ,//结束日期
	 *                       "remark" : "" ,//备注
	 *                       "startDt" : "2019-01-07" ,//开始日期
	 *                       "timeList" :[{ "num" : "1" ,
	 *                                   "timeCode" : "breakfast" ,
	 *                                   "timeNodes" : "1" },
	 *                                   {...},...], //时间点
	 *                      "unit" : "片"//单位 }"
	 *   },{...},...
	 * ]
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/yz/addMemberDrugYz.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("addMemberDrugYz")
	public Result addMemberDrugYz(String memberId,String visitNo,String priorityCode,String drugJson){
		DoctorSessionBO doctor = SessionTool.getWebSession();
		this.yzService.addMemberDrugYz(doctor,memberId,visitNo,priorityCode,drugJson);
		Result result = new Result();
		result.setMessage("添加成功");
		return result;
	}
	
	/**
	 * @api {post}/web/yz/loadMemberDrugsList.do 分页加载患者用药方案列表
	 * @author 林雨堆
	 * @time 2020/03/19
	 * @apiName 分页加载患者用药方案列表
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {String} doctorId 操作者编号(可选)
	 * @apiParam {String} drugType 1-降糖药，2-降脂药，3-降压药，4-胰岛素，5-常用药(可选)
	 * @apiParam {String} teamId 团队编号(可选)
	 * @apiParam {Integer} page 页码 (可选 默认1)
	 * @apiParam {Integer} rows 页数 (可选 默认10)
	 * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/yz/loadMemberDrugsList.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("/loadMemberDrugsList")
	public Result loadMemberDrugsList(String memberId,String doctorId, String teamId,Integer dType,
									  PageRequest page,String drugType,
									  String origin){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		//结果是分页
		PageResult<DrugsMemberVO> pageResult = this.drugsMemberService.listDrugsMemberPage(memberId, null,null,
				dType, page,drugType,origin);
		return Result.ok(pageResult);
	}

	/**
	 * @api {post}/web/yz/addYz.do 新增医嘱
	 * @author suyz
	 * @time 2020/03/19
	 * @apiName addYz
	 * @apiGroup web:yz
	 * @apiParam {String} visitNo 住院号
	 * @apiParam {String} memberId 患者id
	 * @apiParam {String} yzItemType 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他
	 * @apiParam {String} yzType 医嘱类型  1 长期医嘱 2 临时医嘱
	 * @apiParam {String} yzItemName 医嘱项目名称
	 * @apiParam {String} yzItemCode 医嘱项目code
	 * @apiParam {String} drugDose 剂量
	 * @apiParam {String} drugDoseUnit 剂量单位
	 * @apiParam {String} drugDoseUnitDesc 剂量单位描述
	 * @apiParam {String} drugFreqCode 频次
	 * @apiParam {String} drugFreqDesc 频次说明
	 * @apiParam {String} startDt 医嘱开始时间
	 * @apiParam {String} useDrugWay 使用方式  1 其他 2 皮下 3 静脉
	 * @apiParam {String} yzExplain 医嘱说明
	 * @apiParam {String} extData 扩展数据 json格式 （用于存储时间表格）
	 * {
	 *     "baseTotal": "每日总量",
	 *     "base": {
	 *         "00:00-04:00": 5.2,
	 *         "04:00-09:00": 3.2
	 *         ……
	 *     },
	 *     "add": {
	 *         "beforeBreakfast": "早餐前追加",
	 *         "beforeLunch": "午餐前追加",
	 *         "beforeDinner": "晚餐前追加"
	 *     }
	 * }
	 * @apiParam {String} recordOrigin 来源  1 his  2 其他  3 虚拟病区
	 * @apiParam {String} foreignId 业务外键（当来源是虚拟病区的时候，传虚拟病区记录主键）
	 * @apiSuccess {Object} data.obj 医嘱id
	 */
	@RequestMapping("addYz")
	public Result addYz(@Validated AddYzHttpDTO addYzHttpDTO){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		AddYzDTO addYzDTO = new AddYzDTO();
		BeanUtils.copyProperties(addYzDTO ,addYzHttpDTO);
		addYzDTO.setDoctorId(doctorSessionBO.getDoctorId());
		addYzDTO.setDoctorName(doctorSessionBO.getDoctorName());
		addYzDTO.setDepartmentId(doctorSessionBO.getDepartId());
		addYzDTO.setHospitalId(doctorSessionBO.getHospitalId());
		addYzDTO.setDoctorCode(doctorSessionBO.getDoctorId());
		String yzId = this.yzService.addYz(addYzDTO);
		return Result.ok(yzId);
	}

	/**
	 * @api {post}/web/yz/listMemberYz.do 加载患者医嘱列表
	 * @author suyz
	 * @time 2020/03/19
	 * @apiName listMemberYz
	 * @apiGroup web:yz
	 * @apiParam {String} memberId 患者id
	 * @apiParam {String} yzType 医嘱类型 1 长 2 临
	 * @apiParam {String} yzItemTypeString 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他 多个用,隔开
	 * @apiParam {String} startDt 开始时间
	 * @apiParam {String} endDt 结束时间
	 * @apiParam {String} visitNo 住院号
	 * @apiParam {number} page 页数
	 * @apiParam {number} rows 条数
	 * @apiSuccess {Object} data.obj
	 * @apiSuccess {List} data.obj.rows
	 * @apiSuccess {string} data.obj.rows.sid 主键
	 * @apiSuccess {string} data.obj.rows.visitNo 住院号
	 * @apiSuccess {string} data.obj.rows.memberId 患者id
	 * @apiSuccess {string} data.obj.rows.yzId 医嘱id
	 * @apiSuccess {string} data.obj.rows.yzGroupId 医嘱组id
	 * @apiSuccess {string} data.obj.rows.yzItemType 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他 5 胰岛素泵
	 * @apiSuccess {string} data.obj.rows.yzType  医嘱类型 1 长期医嘱 2 临时医嘱
	 * @apiSuccess {string} data.obj.rows.yzItemName  医嘱项目名称
	 * @apiSuccess {string} data.obj.rows.yzItemCode 医嘱项目代码
	 * @apiSuccess {string} data.obj.rows.drugDose 剂量
	 * @apiSuccess {string} data.obj.rows.drugDoseUnit 剂量单位
	 * @apiSuccess {string} data.obj.rows.drugDoseUnitDesc 剂量单位描述
	 * @apiSuccess {string} data.obj.rows.drugFreqCode 频次code
	 * @apiSuccess {string} data.obj.rows.drugFreqDesc 频次描述
	 * @apiSuccess {string} data.obj.rows.drugSpecification 规格
	 * @apiSuccess {string} data.obj.rows.doctorCode 医生代码
	 * @apiSuccess {string} data.obj.rows.doctorName  医生名称
	 * @apiSuccess {string} data.obj.rows.recordOrigin  1 院内（his），2 其他（手动记录）  3 虚拟病区
	 * @apiSuccess {string} data.obj.rows.doctorId 医生id
	 * @apiSuccess {string} data.obj.rows.useDrugWay 用药方式 1 其他 2 皮下注射 2 静脉注射
	 * @apiSuccess {string} data.obj.rows.yzStatus 医嘱状态 1 新开 2 已下发 3 执行中 4 已执行完 5 已停止 6 已作废
	 * @apiSuccess {string} data.obj.rows.startDt 开始时间
	 * @apiSuccess {string} data.obj.rows.stopDt 停止时间
	 * @apiSuccess {string} data.obj.rows.yzExplain 医嘱说明
	 * @apiSuccess {string} data.obj.rows.usePlanJson
	 * @apiSuccess {string} data.obj.rows.extData 扩展数据， json格式
	 * @apiSuccess {string} data.obj.rows.checkerId 校对人id
	 * @apiSuccess {string} data.obj.rows.checkerName 校对人姓名
	 * @apiSuccess {string} data.obj.rows.lastExecuteTime 最后执行时间
	 * @apiSuccess {string} data.obj.rows.checkDt 校对时间
	 * @apiSuccess {string} data.obj.rows.foreignId 业务外键
	 */
	@RequestMapping("listMemberYz")
	public Result listMemberYz(PageRequest pr ,@Validated ListMemberYzDTO listMemberYzDTO){
		PageResult<MemberYzListVO> pageResult = this.yzService.listMemberYz(pr ,listMemberYzDTO);
		return Result.ok(pageResult);
	}

	/**
	 * @api {post}/web/yz/getYzExecuteLog.do 获取医嘱执行记录
	 * @author suyz
	 * @time 2020/03/19
	 * @apiName getYzExecuteLog
	 * @apiGroup web:yz
	 * @apiParam {String} yzId 医嘱id
	 * @apiSuccess {Object} data.obj
	 * @apiSuccess {string} data.obj.sid 主键
	 * @apiSuccess {string} data.obj.visitNo 住院号
	 * @apiSuccess {string} data.obj.memberId 患者id
	 * @apiSuccess {string} data.obj.yzId 医嘱id
	 * @apiSuccess {string} data.obj.yzGroupId 医嘱组id
	 * @apiSuccess {string} data.obj.yzItemType 医嘱项目类型 1 用药 2 护理 3 检验检查 4 其他 5 胰岛素泵
	 * @apiSuccess {string} data.obj.yzType  医嘱类型 1 长期医嘱 2 临时医嘱
	 * @apiSuccess {string} data.obj.yzItemName  医嘱项目名称
	 * @apiSuccess {string} data.obj.yzItemCode 医嘱项目代码
	 * @apiSuccess {string} data.obj.drugDose 剂量
	 * @apiSuccess {string} data.obj.drugDoseUnit 剂量单位
	 * @apiSuccess {string} data.obj.drugDoseUnitDesc 剂量单位描述
	 * @apiSuccess {string} data.obj.drugFreqCode 频次code
	 * @apiSuccess {string} data.obj.drugFreqDesc 频次描述
	 * @apiSuccess {string} data.obj.drugSpecification 规格
	 * @apiSuccess {string} data.obj.doctorCode 医生代码
	 * @apiSuccess {string} data.obj.doctorName  医生名称
	 * @apiSuccess {string} data.obj.recordOrigin  1 院内（his），2 其他（手动记录）  3 虚拟病区
	 * @apiSuccess {string} data.obj.doctorId 医生id
	 * @apiSuccess {string} data.obj.useDrugWay 用药方式 1 其他 2 皮下注射 2 静脉注射
	 * @apiSuccess {string} data.obj.yzStatus 医嘱状态 1 未发送 2 已发送 3 已校对 4 在执行 5 已执行
	 * @apiSuccess {string} data.obj.startDt 开始时间
	 * @apiSuccess {string} data.obj.stopDt 停止时间
	 * @apiSuccess {string} data.obj.yzExplain 医嘱说明
	 * @apiSuccess {string} data.obj.usePlanJson
	 * @apiSuccess {string} data.obj.extData 扩展数据， json格式
	 * @apiSuccess {string} data.obj.checkerId 校对人id
	 * @apiSuccess {string} data.obj.checkerName 校对人姓名
	 * @apiSuccess {string} data.obj.lastExecuteTime 最后执行时间
	 * @apiSuccess {string} data.obj.checkDt 校对时间
	 * @apiSuccess {string} data.obj.foreignId 业务外键
	 * @apiSuccess {string} data.obj.departmentName 科室名称
	 * @apiSuccess {string} data.obj.bedNo 病床号
	 */
	@RequestMapping("getYzExecuteLog")
	public Result getYzExecuteLog(String yzId){
		ValidateTool.checkParameterIsNull("yzId" ,yzId);
		YzExecuteLogVO yzExecuteLogVO = this.yzService.getYzExecuteLogByYzId(yzId);
		return Result.ok(yzExecuteLogVO);
	}

	/**
	 * @api {post}/web/yz/operateYz.do 操作医嘱
	 * @author suyz
	 * @time 2020/03/19
	 * @apiName operateYz
	 * @apiGroup web:yz
	 * @apiParam {String} yzIdString 医嘱id ，多个用,隔开
	 * @apiParam {String} yzOperation 操作类型，值为：   //下发 HAND_DOWN, 取消下发 CANCEL_HAND_DOWN ，删除 DELETE作废 ABOLISH, 停止 STOP,校对 CHECK
	 * @apiSuccess {Object} data.obj
	 */
	@RequestMapping("operateYz")
	public Result operateYz(String yzIdString ,YzOperation yzOperation){
		ValidateTool.checkParameterIsNull("yzIdString" ,yzIdString);
		ValidateTool.checkParameterIsNull("yzOperation" ,yzOperation);
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		List<String> yzIdList = new ArrayList<>(Arrays.asList(yzIdString.split(",")));
		this.yzService.operateYz(yzIdList ,yzOperation ,doctorSessionBO.getDoctorId());
		return Result.ok();
	}

	/**
	 * @api {post}/web/yz/updateYz.do 修改医嘱
	 * @author suyz
	 * @time 2020/03/19
	 * @apiName updateYz
	 * @apiGroup web:yz
	 * @apiParam {String} yzId 医嘱id,必传，其他参数同添加
	 * @apiSuccess {Object} data.obj
	 */
	@RequestMapping("updateYz")
	public Result updateYz(@Validated UpdateYzDTO updateYzDTO){
		YzPO yzPO = new YzPO();
		BeanUtils.copyProperties(yzPO ,updateYzDTO);
		this.yzService.updateYz(yzPO);
		return Result.ok();
	}
}
