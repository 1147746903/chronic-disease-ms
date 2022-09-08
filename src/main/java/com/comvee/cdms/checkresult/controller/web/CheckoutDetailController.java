package com.comvee.cdms.checkresult.controller.web;

import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.checkresult.dto.*;
import com.comvee.cdms.checkresult.po.DataSyncTaskPO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.po.CheckoutPO;
import com.comvee.cdms.checkresult.po.InspectionPO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.service.InspectionDetailServiceI;
import com.comvee.cdms.checkresult.service.InspectionServiceI;
import com.comvee.cdms.checkresult.vo.CheckoutAbnormalVO;
import com.comvee.cdms.checkresult.vo.CheckoutDetailVO;
import com.comvee.cdms.checkresult.vo.MemberInspectionVO;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.ListSelectedKeyNoteDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
@RestController
@RequestMapping("/web/checkResult")
public class CheckoutDetailController {

	@Autowired
	@Qualifier("checkoutDetailService")
	private CheckoutDetailServiceI checkoutDetailService;

	@Autowired
	@Qualifier("inspectionDetailService")
	private InspectionDetailServiceI inspectionDetailService;

	@Autowired
	private CheckoutServiceI checkoutService;

	@Autowired
	private DoctorServiceI doctorService;

	@Autowired
	private InspectionServiceI inspectionService;

	/**
	 * @api {post}/web/checkResult/getCheckoutDetail.do 获取检验详细信息
	 * @author 林雨堆
	 * @time 2020/03/19
	 * @apiName getCheckoutDetail 获取检验详细信息
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} checkoutId 检验编号 必填
	 * @apiParam {String} memberId 患者编号 必填
	 * @apiParam {String} hospitalId 医院编号 必填
	 * @apiParam {String} reportDate 报告时间
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/getCheckoutDetail.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("getCheckoutDetail")
	public Result getCheckoutDetail(String checkoutId, String memberId, String reportDate,String hospitalId) {
		Map<String,Object> map = this.checkoutDetailService.getCheckoutDetailAll(checkoutId,memberId,reportDate,hospitalId);
		Result result = new Result("0","成功",true);
		result.setObj(map);
		return result;
	}

	/**
	 * @api {post}/web/checkResult/listCheckoutDetailByCheckoutId.do  根据检验id，获取检验详情列表
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName listCheckoutDetailByCheckoutId 根据检验id，获取检验详情列表
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} checkoutId 检验编号
	 * @apiParam {String} hospitalId 医院编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/listCheckoutDetailByCheckoutId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listCheckoutDetailByCheckoutId")
	public Result listCheckoutDetailByCheckoutId(String checkoutId,String hospitalId) {
		List<CheckoutDetailVO> checkoutList= this.checkoutDetailService.listCheckoutDetailByCheckoutId(checkoutId,hospitalId);
		Result result = new Result(checkoutList);
		return result;
	}

	/**
	 * @api {post}/web/checkResult/getInspectionDetail.do  获取详细检查数据
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName getInspectionDetail 获取详细检查数据
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} inspectId 检验编号
	 * @apiParam {String} memberId 患者编号
	 * @apiParam {String} hospitalId 医院编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/getInspectionDetail.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("getInspectionDetail")
	public Result getInspectionDetail(String inspectId,String memberId,String hospitalId) {

		Map<String,Object> map = this.inspectionDetailService.getInspectionDetailAll(inspectId,memberId,hospitalId);
		Result result = new Result("0","成功",true);
		result.setObj(map);
		return result;
	}

	/**
	 * @api {post}/web/checkResult/listCheckoutAbnormalListByMemberId.do  根据患者id，获取检验异常列表
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName listCheckoutAbnormalListByMemberId 根据患者id，获取检验异常列表
	 * @apiGroup web-checkResult
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/listCheckoutAbnormalListByMemberId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listCheckoutAbnormalListByMemberId")
	public Result listCheckoutAbnormalListByMemberId(String memberId) {
		List<CheckoutAbnormalVO> vos = this.inspectionDetailService.listCheckoutAbnormalListByMemberId(memberId);
		Result result = new Result(vos);
		return result;
	}


	/**
	 * @api {post}/web/checkResult/listCheckoutDetailNearlySixMonths.do 实验室检验项(获取患者近六个月的检验详情列表)
	 * @author 林雨堆
	 * @time 2020/03/18
	 * @apiName listCheckoutDetailNearlySixMonths 实验室检验项(获取患者近六个月的检验详情列表)
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {Integer} type 类型 (必填 类型 ：1 重要检验指标 2 异常检验指标)
	 * @apiParam {String} doctorId 医护人员编号 (默认当前登录医护人员编号)
	 * @apiParam {String} hospitalId 住院编号 (默认当前登录医护人员医院编号)
	 * @apiParam {Integer} inHos 关注项目类型 ( 1 住院关注项目 0 居家门诊关注项目-默认)
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/checkResult/listCheckoutDetailNearlySixMonths.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listCheckoutDetailNearlySixMonths")
	public Result listCheckoutDetailNearlySixMonths(@Validated ListCheckoutDetailNearlySixMonthsDTO dto) {
		DoctorSessionBO doctorSession = SessionTool.getWebSession();
		if(StringUtils.isBlank(dto.getDoctorId())){
			dto.setDoctorId(doctorSession.getDoctorId());
			if(StringUtils.isBlank(dto.getHospitalId())){
				dto.setHospitalId(doctorSession.getHospitalId());
			}
		} else if(StringUtils.isBlank(dto.getHospitalId())) {
			DoctorPO doctorModel = this.doctorService.getDoctorById(dto.getDoctorId());
			dto.setHospitalId(doctorModel.getHospitalId());
		}
		//默认居家门诊特别关注项目
		if(dto.getInHos()==null){
			dto.setInHos(0);
		}
		List<CheckoutDetailVO> detailVOS = this.checkoutDetailService.listCheckoutDetailNearlySixMonths(dto);
		Result result = new Result(detailVOS);
		return result;
	}

	/**
	 * @api {post}/web/checkResult/listInspectionPageByMemberId.do 获取检查分页信息
	 * @author 林雨堆
	 * @time 2018/07/23
	 * @apiName listInspectionPageByMemberId 获取检查分页信息
	 * @apiGroup web-checkResult
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/listInspectionPageByMemberId.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listInspectionPageByMemberId")
	public Result listInspectionPageByMemberId(String memberId,PageRequest pr) {
		PageResult<InspectionPO> inspectionPage = this.inspectionDetailService.listInspectionPageByMemberId(memberId,pr);
		Result result = new Result(inspectionPage);
		return result;
	}

	/**
	 * @api {post}/web/checkResult/listCheckoutPageByMemberId.do 获取检验分页信息
	 * @author 林雨堆
	 * @time 2018/08/13
	 * @apiName listCheckoutPageByMemberId 获取检验分页信息
	 * @apiGroup web-checkResult
	 * @apiVersion 4.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/listCheckoutPageByMemberId.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listCheckoutPageByMemberId")
	public Result listCheckoutPageByMemberId(String memberId,PageRequest pr) {
		PageResult<CheckoutPO> checkoutPage = this.inspectionDetailService.listCheckoutPageByMemberId(memberId,pr);
		Result result = new Result(checkoutPage);
		return result;
	}

	/**
	 * 添加检验
	 * @param dto
	 * @return
	 */
	@RequestMapping("saveCheckout")
	public Result saveCheckout(@Validated AddCheckoutDTO dto){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		DoctorPO doctorPO = new DoctorPO();
		BeanUtils.copyProperties(doctorPO,doctorSessionBO);
		this.checkoutDetailService.addCheckout(dto,doctorPO);
		return Result.ok("添加成功");
	}

	/**
	 * 批量添加检验
	 * @param
	 * @return
	 */
	@RequestMapping("batchSaveCheckout")
	public Result batchSaveCheckout(@Validated BatchAddCheckoutDTO batchDto){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		DoctorPO doctorPO = new DoctorPO();
		BeanUtils.copyProperties(doctorPO,doctorSessionBO);
		this.checkoutDetailService.addCheckoutBatch(batchDto,doctorPO);
		return Result.ok("添加成功");
	}

	/**
	 * 删除检验
	 * @param checkoutId
	 * @return
	 */
	@RequestMapping("deleteCheckout")
	public Result deleteCheckout(String checkoutId){
		this.checkoutService.deleteCheckout(checkoutId);
		return Result.ok("删除成功");
	}

	/**
	 * 修改检验信息
	 * @param dto
	 * @return
	 */
	@RequestMapping("modifyCheckout")
	public Result modifyCheckout(@Validated ModifyCheckoutDTO dto){
		this.checkoutService.modifyCheckout(dto);
		return Result.ok("修改成功");
	}
	/**
	 * @api {post}/web/checkResult/loadMemberHistoryCheckResult.do 获取患者某种检验结果图表&历史列表
	 * @author 林雨堆
	 * @time 2020/03/23
	 * @apiName loadMemberHistoryCheckResult 获取患者某种检验结果图表&历史列表
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 必填
	 * @apiParam {String} checkoutCode 检验代码 必填
	 * @apiParam {String} checkoutName 检验名称 必填
	 * @apiParam {String} hospitalId 医院编号 必填
	 * @apiParam {String} doctorId 医护人员编号
	 * @apiParam {String} startDt 开始时间
	 * @apiParam {String} endDt 结束时间
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/checkResult/loadMemberHistoryCheckResult.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("loadMemberHistoryCheckResult")
	public Result loadMemberHistoryCheckResult(@Validated ListCheckResultDTO listCheckResultDTO,PageRequest pager){
		List<CheckoutDetailVO> checkoutDetailVOS = this.checkoutDetailService.listMemberHistoryCheckResult(listCheckResultDTO);
		List<CheckoutDetailVO> checkoutDetailVOS1 = this.checkoutDetailService.getCheckoutDetailChartList(checkoutDetailVOS);
		PageResult<CheckoutDetailVO> pageResult = this.checkoutDetailService.pagerMemberHistoryCheckResult(listCheckResultDTO,pager);
		Map<String,Object> resultMap = new HashMap<>(2);
		resultMap.put("historyList",pageResult);
		resultMap.put("checkoutChart",checkoutDetailVOS1);
		return new Result(resultMap);
	}

	/**
	 * @api {post}/web/checkResult/listMemberHistoryCheckResult.do 获取患者历史某种检验结果列表
	 * @author 林雨堆
	 * @time 2020/03/16
	 * @apiName listMemberHistoryCheckResult 获取患者历史某种检验结果列表
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 必填
	 * @apiParam {String} checkoutCode 检验代码 必填
	 * @apiParam {String} checkoutName 检验名称 必填
	 * @apiParam {String} hospitalId 医院编号 必填
	 * @apiParam {String} doctorId 医护人员编号
	 * @apiParam {String} startDt 开始时间
	 * @apiParam {String} endDt 结束时间
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/checkResult/pagerMemberHistoryCheckResult.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("pagerMemberHistoryCheckResult")
	public Result pagerMemberHistoryCheckResult(@Validated ListCheckResultDTO listCheckResultDTO,PageRequest pager){
		PageResult<CheckoutDetailVO> pageResult = this.checkoutDetailService.pagerMemberHistoryCheckResult(listCheckResultDTO,pager);
		return new Result(pageResult);
	}

	/**
	 * @api {post}/web/checkResult/listInspectionReportNearlySixMonths.do 获取患者近6个月所有类型的检查
	 * @author 林雨堆
	 * @time 2020/03/19
	 * @apiName listInspectionReportNearlySixMonths 获取患者近6个月所有类型的检查
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 (必填)
	 * @apiParam {String} hospitalId 医院编号 (必填)
	 * @apiSampleRequest  http://192.168.1.12:9080/intelligent-prescription/web/checkResult/listInspectionReportNearlySixMonths.do
	 *
	 * @apiSuccess {Object} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listInspectionReportNearlySixMonths")
	public Result listInspectionReportNearlySixMonths(String memberId,String hospitalId){
		ValidateTool.checkParamIsNull(memberId ,"memberId");
		ValidateTool.checkParamIsNull(hospitalId ,"hospitalId");
		String startDt = DateHelper.plusDate(DateHelper.getNowDate(), 2, -6,DateHelper.DATETIME_FORMAT);
		List<MemberInspectionVO> vos = this.inspectionDetailService.listInspectionReportNearlySixMonths(memberId,hospitalId,startDt);
		return new Result(vos);
	}

	/**
	 * @api {post}/web/checkResult/pagerCheckoutDetailGroupByNameOfDoctor.do 分页获取检验项目列表（根据名称分组&医生关注项目）
	 * @author 林雨堆
	 * @time 2020/03/23
	 * @apiName pagerCheckoutDetailGroupByNameOfDoctor 分页获取检验项目列表（根据名称分组&医生关注项目）
	 * @apiGroup WEB-V6.0.0
	 * @apiVersion 6.0.0
	 * @apiParam {String} memberId 患者编号 必填
	 * @apiParam {String} hospitalId 医院编号 必填
	 * @apiParam {String} doctorId 医护人员编号
	 * @apiParam {Integer} inHos 是否住院关注项目 1是 0否 必填
	 * @apiParam {Integer} type 设置类型 1 特别关注设置 2 查看数据设置 可选默认1
	 * @apiParam {String} startDt 开始时间 可选
	 * @apiParam {String} endDt 结束时间 可选
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.2.12:9080/intelligent-prescription/web/checkResult/pagerCheckoutDetailGroupByNameOfDoctor.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("pagerCheckoutDetailGroupByNameOfDoctor")
	public Result pagerCheckoutDetailGroupByNameOfDoctor(@Validated ListSelectedKeyNoteDTO dto,PageRequest pager,String startDt,String endDt){
		ValidateTool.checkParamIsNull(dto.getMemberId(),"memberId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		dto.setDoctorId(doctorSessionBO.getDoctorId());
		dto.setHospitalId(doctorSessionBO.getHospitalId());
		dto.setCacheKey(MD5Util.md5("D"+doctorSessionBO.getDoctorId()+"H"+doctorSessionBO.getHospitalId()
				+"M"+dto.getMemberId())+"in"+dto.getInHos()+"type"+dto.getType()
				+"page"+pager.getPage()+"rows"+pager.getRows()+"sd"+startDt+"ed"+endDt);
		PageResult<CheckoutDetailPO> poPageResult = this.checkoutDetailService.pagerCheckoutDetailGroupByNameOfDoctor(dto,
				pager,startDt,endDt);
		return new Result(poPageResult);
	}


	/**\
	 * 检查一级列表
	 * @param dto
	 * @param pager
	 * @return
	 */
	@RequestMapping("pagerMemberCheckoutWithNote")
	public Result pagerMemberCheckoutWithNote(@Validated GetMemberCheckoutDTO dto, PageRequest pr){
		ValidateTool.checkParamIsNull(dto.getMemberId(),"memberId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		dto.setDoctorId(doctorSessionBO.getDoctorId());
		dto.setHospitalId(doctorSessionBO.getHospitalId());
		PageResult<CheckoutPO> checkoutPOPageResult = this.checkoutService.pagerMemberCheckoutWithNote(dto, pr);
		return new Result(checkoutPOPageResult);
	}
	/**\
	 * 检查二级列表
	 * @param dto
	 * @param pager
	 * @return
	 */
	@RequestMapping("pagerMemberCheckoutDetailByForeignId")
	public Result pagerMemberCheckoutWithNote(String checkoutId, PageRequest pr){
		ValidateTool.checkParamIsNull(checkoutId,"checkoutId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		PageResult<CheckoutDetailPO> checkoutDetailPOPageResult = this.checkoutService.pagerMemberCheckoutDetailByForeignId(doctorSessionBO, checkoutId, pr);
		return new Result(checkoutDetailPOPageResult);
	}


	/**
	 * @api {post}/web/checkResult/getInspectionDetailByInspectId.do  根据检查id，获取检查详细信息
	 * @author 林雨堆
	 * @time 2018/07/19
	 * @apiName getInspectionDetailByInspectId 根据检查id，获取检查详细信息
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} inspectId 检查编号
	 * @apiParam {String} hospitalId 检查编号
	 * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/checkResult/getInspectionDetailByInspectId.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("getInspectionDetailByInspectId")
	public Result getInspectionDetailByInspectId(String inspectId,String hospitalId) {
		InspectionPO vo = this.inspectionService.getInspectionByInspectId(inspectId ,hospitalId);
		return Result.ok(vo);
	}

	/**
	 * 添加数据同步任务
	 * @param memberId
     * type  1检验 2检查 3医嘱
	 * @return
	 */
	@RequestMapping("addDataSyncTask")
	public Result addDataSyncTask(String memberId,Integer type) {
		ValidateTool.checkParameterIsNull("memberId",memberId);
		ValidateTool.checkParameterIsNull("type",type);
		DoctorSessionBO webSession = SessionTool.getWebSession();
		DataSyncTaskPO taskPO = new DataSyncTaskPO();
		taskPO.setMemberId(memberId);
		taskPO.setDoctorId(webSession.getDoctorId());
		taskPO.setHospitalId(webSession.getHospitalId());
		taskPO.setStatus(CheckoutConstant.DATA_SYNC_TASK_IN);
        taskPO.setTaskType(type);
		DataSyncTaskPO dataSyncTaskPO = checkoutService.addDataSyncTask(taskPO);
		return Result.ok(dataSyncTaskPO);
	}

	/**
	 * 查询任务状态
	 * @param memberId
	 * @param type
	 * @return
	 */
	@RequestMapping("getDataSyncTask")
	public Result getDataSyncTask(String memberId,Integer type) {
		ValidateTool.checkParameterIsNull("memberId",memberId);
		ValidateTool.checkParameterIsNull("type",type);
		DoctorSessionBO webSession = SessionTool.getWebSession();
		DataSyncTaskPO getParam = new DataSyncTaskPO();
		getParam.setDoctorId(webSession.getDoctorId());
		getParam.setTaskType(type);
		getParam.setMemberId(memberId);
		DataSyncTaskPO dataSyncTask = checkoutService.getDataSyncTask(getParam);
		return Result.ok(dataSyncTask);
	}

	/**
	 * 修改检验检查任务同步任务
	 */
	@RequestMapping("modifyDataSyncTask")
	public Result modifyCheckSyncTask(String sid,Integer status) {
		ValidateTool.checkParameterIsNull("sid",sid);
		ValidateTool.checkParameterIsNull("status",status);
		DataSyncTaskPO taskPO = new DataSyncTaskPO();
		taskPO.setSid(sid);
		taskPO.setStatus(status);
		checkoutService.updateDataSyncTask(taskPO);
		return Result.ok();
	}
}
