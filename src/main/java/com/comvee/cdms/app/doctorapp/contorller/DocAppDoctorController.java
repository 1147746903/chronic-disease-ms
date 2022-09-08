package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.AddDoctorServiceTimeDTO;
import com.comvee.cdms.doctor.dto.ListGroupsDTO;
import com.comvee.cdms.doctor.dto.UpdateDoctorServiceTimeDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorServiceTimePO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DoctorDepartVO;
import com.comvee.cdms.doctor.vo.DoctorGroupVO;
import com.comvee.cdms.doctor.vo.GroupsVO;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarRemarkDTO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.other.po.DoctorPushSetPO;
import com.comvee.cdms.other.service.DoctorPushService;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/docapp/doctor")
public class DocAppDoctorController {


	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DoctorPushService doctorPushService;

	@Autowired
	private DoctorServiceI doctorService;
	@Autowired
	private DyBloodSugarService dyBloodSugarService;
	@Autowired
	private CommitteeService committeeService;
	/**
	 * 获取推送设置
	 * @return
	 */
	@RequestMapping("/getDoctorPushSet")
	public Result getDoctorPushSet(){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		DoctorPushSetPO doctorPushSetPO = this.doctorPushService.getDoctorPushSet(doctorModel.getDoctorId());
		return Result.ok(doctorPushSetPO);
	}

	/**
	 * 修改推送设置
	 * @param signPush
	 * @param dialoguePush
	 * @return
	 */
	@RequestMapping("updateDoctorPushSet")
	public Result updateDoctorPushSet(Integer signPush, Integer dialoguePush){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(signPush, "signPush");
		ValidateTool.checkParamIsNull(dialoguePush, "dialoguePush");

		DoctorPushSetPO doctorPushSetPO = new DoctorPushSetPO();
		doctorPushSetPO.setDoctorId(doctorModel.getDoctorId());
		doctorPushSetPO.setDialoguePush(dialoguePush);
		doctorPushSetPO.setSignPush(signPush);
		this.doctorPushService.updateDoctorPushSet(doctorPushSetPO);
		return Result.ok();
	}

	/**
	 * 加载服务时间
	 * @param signPush
	 * @param dialoguePush
	 * @return
	 */
	@RequestMapping("listDoctorServiceTime")
	public Result listDoctorServiceTime(){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		List<DoctorServiceTimePO> list = this.doctorService.listDoctorServiceTime(doctorModel.getDoctorId(), null, null);
		return Result.ok(list);
	}

	/**
	 * 新增服务时间
	 * @param signPush
	 * @param dialoguePush
	 * @return
	 */
	@RequestMapping("addDoctorServiceTime")
	public Result addDoctorServiceTime(@Validated AddDoctorServiceTimeDTO addDoctorServiceTimeDTO){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
		BeanUtils.copyProperties(doctorServiceTimePO, addDoctorServiceTimeDTO);
		doctorServiceTimePO.setDoctorId(doctorModel.getDoctorId());
		String sid = this.doctorService.addDoctorServiceTime(doctorServiceTimePO);
		return Result.ok(sid);
	}

	/**
	 * 删除服务时间
	 * @param signPush
	 * @param dialoguePush
	 * @return
	 */
	@RequestMapping("deleteDoctorServiceTime")
	public Result deleteDoctorServiceTime(String sid){
		ValidateTool.checkParamIsNull(sid, "sid");
		this.doctorService.deleteDoctorServiceTime(sid);
		return Result.ok();
	}

	/**
	 * 修改服务时间
	 * @param signPush
	 * @param dialoguePush
	 * @return
	 */
	@RequestMapping("updateDoctorServiceTime")
	public Result updateDoctorServiceTime(@Validated UpdateDoctorServiceTimeDTO updateDoctorServiceTimeDTO){
		DoctorServiceTimePO doctorServiceTimePO = new DoctorServiceTimePO();
		BeanUtils.copyProperties(doctorServiceTimePO, updateDoctorServiceTimeDTO);
		this.doctorService.updateDoctorServiceTime(doctorServiceTimePO);
		return Result.ok();
	}


	/**
	 * 加载我的医生列表
	 * @return
	 */
	@RequestMapping("listMyDoctor")
	public Result listMyDoctor(){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		List<DoctorPO> doctorPOList = this.doctorService.listMyDoctor(doctorSessionBO.getDoctorId());
		return new Result(doctorPOList);
	}

	/**
	 * 加载分组
	 * @param doctorId
	 * @return
	 */
	@RequestMapping("listGroup")
	public Result listGroup(String doctorId,String keyWord){
		SessionTool.getWebSession();
		List<DoctorGroupVO> list = this.doctorService.listGroup(doctorId, keyWord ,true);
		return new Result(list);
	}

	/**
	 * @api {post}/docapp/doctor/listGroups.do 加载分组
	 * @author 林雨堆
	 * @time 2019/07/18
	 * @apName 加载分组
	 * @apiGroup web-doctor
	 * @apiVersion 4.0.0
	 * @apiParam {String} keyWord  关键字（可选）
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 0000 OK
	 * {
	 *     "obj":[
	 *      {
	 *      "items":[
	 *          {
	 *          "doctorId":"1",
	 *          "groupId":"181105185900104",
	 *          "groupName":"内分泌科",
	 *          "peopleNumber":10
	 *          },
	 *          {
	 *          "doctorId":"1",
	 *          "groupId":"517812E861921A665A9324275FDE8FE6",
	 *          "groupName":"虚拟病区",
	 *          "peopleNumber":9
	 *          },...
	 *      ],
	 *      "name":"住院患者",
	 *      "orderIndex":0,
	 *      "type":1
	 *     },
	 *     {
	 *      "items":[
	 *          {
	 *          "doctorId":"1",
	 *          "groupId":"0",
	 *          "groupName":"默认分组",
	 *          "peopleNumber":197
	 *          },
	 *          {
	 *          "doctorId":"1",
	 *          "groupId":"123",
	 *          "groupName":"vip组。",
	 *          "peopleNumber":18
	 *          },...
	 *      ],
	 *      "name":"门诊/居家患者",
	 *       "orderIndex":1,
	 *      "type":2
	 *      }
	 *     ],
	 *     "code":"0",
	 *     "msg":"",
	 *     "success":true
	 * }
	 */
	@RequestMapping("listGroups")
	public Result listGroups(ListGroupsDTO dto){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		dto.setDoctorId(doctorSessionBO.getDoctorId());
		dto.setEntityType(doctorSessionBO.getEntityType());
		dto.setCountPeopleNumber(true);
		List<GroupsVO> list = this.doctorService.listGroups(dto);
		return new Result(list);
	}

	/**
	 * @api {post}/docapp/doctor/listDoctorDepart.do 获取医护人员可管理的科室
	 * @author 林雨堆
	 * @time 2020/03/09
	 * @apiName listDoctorDepart 获取医护人员可管理的科室
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} hospitalId 医院编号 （医护人员编号和该编号必须有一个填写，两者都有该编号为主）
	 * @apiParam {String} doctorId 医护人员编号 （医院编号和该编号必须有一个填写，两者都有医院编号为主）
	 * @apiParam {String} keyWord 患者名称或患者名称拼音（含）
	 * @apiParam {String} concernStatus 是否关注
	 * @apiParam {String} type 管理病种 1:糖尿病 2:高血压
	 * @apiParam {String} monitor 是否监测 1是 0否
	 * @apiParam {String} paramLevel 血糖情况 1低血糖 3正常 5高血糖
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/doctor/listDoctorDepart.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listDoctorDepart")
	public Result listDoctorDepart(ListMemberDTO listMemberDTO){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		listMemberDTO.setDoctorId(doctorSessionBO.getDoctorId());
		List<DoctorDepartVO> doctorDepartVOList = this.doctorService.listDoctorDepart(listMemberDTO);
		return new Result(doctorDepartVOList);
	}

	/**
	 * @api {post}/docapp/doctor/addBloodSugarRememberRemark.do 添加血糖备注信息
	 * @author 林雨堆
	 * @time 2019/04/19 17:00
	 * @apName 添加血糖备注信息
	 * @apiGroup WEB-V6.3.0
	 * @apiVersion 0.0.1
	 * @apiParam {GlucometerRequest} mr 默认参数
	 * @apiParam {String} bid  血糖记录编号（必填）
	 * @apiParam {String} content  备注内容（必填）
	 * @apiParam {String} sid  点击列表的做修改的时候传值,其他操作不传值
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 0000 OK
	 * {
	 *     "obj":null,
	 *     "code":"0",
	 *     "msg":"添加成功",
	 *     "success":true
	 * }
	 * or
	 * {
	 *     "obj":null,
	 *     "code":"-1",
	 *     "msg":"登录超时，请重新登录",
	 *     "success":true
	 * }
	 * or
	 * {
	 *     "obj":null,
	 *     "code":"-1",
	 *     "msg":"系统错误",
	 *     "success":false
	 * }
	 */
	@RequestMapping("addBloodSugarRememberRemark")
	public Result addBloodSugarRememberRemark(@Validated DyBloodSugarRemarkDTO dto, String sid){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		dto.setOrigin(DynamicBloodSugarConstant.ORIGIN_DOCTOR);
		dto.setOperatorId(doctorSessionBO.getDoctorId());
		return Result.ok(this.dyBloodSugarService.addBloodSugarRememberRemark(dto,sid));
	}


	/**
	 * 获取医生村社区关联信息
	 */
	@RequestMapping("listDoctorCommittee")
	public Result listDoctorCommittee(String doctorId){
		ValidateTool.checkParamIsNull(doctorId, "doctorId");
		List<HospitalCommitteePO> doctorIdCommiteeList = committeeService.listCommitteeByDoctorId(doctorId);
		return Result.ok(doctorIdCommiteeList);
	}

}
