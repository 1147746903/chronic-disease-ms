package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.model.app.NormalSugerListResqModel;
import com.comvee.cdms.app.doctorapp.model.app.NormalSugerModel;
import com.comvee.cdms.app.doctorapp.model.app.SugarDetailListModel;
import com.comvee.cdms.app.doctorapp.service.BloodSugarServiceI;
import com.comvee.cdms.app.doctorapp.vo.CountMemberVO;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignLock;
import com.comvee.cdms.sign.dto.AddBloodSugarDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarMapperDTO;
import com.comvee.cdms.sign.dto.AddBloodSugarServiceDTO;
import com.comvee.cdms.sign.mapper.BloodSugarMapper;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.user.tool.SessionTool;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/docapp/bloodsugar")
public class DocAppBloodSugarController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BloodSugarServiceI bloodSugarService;
	
    @Autowired
    private MemberService memberService;	
    
    @Autowired
    private BloodSugarMapper bloodSugarMapper;

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private BloodSugarService bloodSugarServiceV2;
	/**
	 * 点击异常
	 * @param dateType
	 * @param payStatus
	 * @return
	 */
	@RequestMapping("/statisticsMemberAbnormalsNew")
	@ResponseBody
	public Result statisticsMemberAbnormalsNew( Integer dateType, String payStatus){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(dateType, "dateType");
		//DateUtil有做时间处理
		String endDt = DateHelper.getSystemDate();
		String startDt = DateHelper.getNeedStartDateTime(1);	//时间默认一周
		if(dateType != null && dateType > 1 && dateType < 8){
			startDt = DateHelper.getNeedStartDateTime(dateType);//dateType = 1 一周
		}
		logger.info("dateType = "+dateType);

		List<Map<String,Object>> resultModel = this.bloodSugarService.statisticsMemberAbnormalsNew(doctorModel.getDoctorId(), payStatus, startDt, endDt,doctorModel.getHospitalId());
		logger.info("[statisticsMemberAbnormalsNew]异常血糖统计列表结果数据：......."+ JSON.toJSON(resultModel));

		return Result.ok(resultModel);
	}	
	
	
	
	/**
	 * 点击血糖异常详细列表
     * @apiParam {Integer} dateType
     * @apiParam {Integer} type 2偏低 3空腹高危 4非空腹高危 6连续三次偏高 7未测血糖 
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 行数 
     * @apiParam {String} payStatus 1付费 0免费
	 * @return
	 */
	@RequestMapping("/workbenchSugarDetailNew")
	@ResponseBody
	public Result workbenchSugarDetailNew(Integer dateType,String type,
			PageRequest page,String payStatus){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();

		//DateUtil有做时间处理
		String startDt = DateHelper.getNeedStartDateTime(1);//血糖异常人数统计开始时间
		String endDt = DateHelper.getSystemDate();//血糖异常人数统计结束时间
		if(dateType != null && dateType > 1 && dateType < 8){
			startDt = DateHelper.getNeedStartDateTime(dateType);//dateType = 1 一周
		}

		PageResult<SugarDetailListModel> resultModel = this.bloodSugarService.workbenchSugarDetailNew(page, doctorModel.getDoctorId(), payStatus, startDt, endDt, type,doctorModel.getHospitalId());

		logger.info("workbenchSugarDetailNew结果数据：......."+JSON.toJSON(resultModel));
		return Result.ok(resultModel);
	}	
	
	
	/**
	 * 点击进入正常血糖患者列表
     * @apiParam {Integer} dateType
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 行数 
     * @apiParam {String} payStatus 1付费 0免费
     * @apiParam {String} thumb 1已点赞 0未点赞  null:已点赞和未点赞
	 * @apiParam {Integer} visitType 1 住院 2门诊居家 默认门诊居家
	 * @return
	 */
	@RequestMapping("/workbenchSugarDetailByNormal")
	@ResponseBody
	public Result workbenchSugarDetailByNormal(PageRequest page,
			Integer dateType,String payStatus ,Integer visitType){
		if(visitType == null){
			visitType = 2;
		}
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		//DateUtil有做时间处理
		//血糖异常人数统计开始时间
		String startDt = DateHelper.getNeedStartDateTime(1);
		//血糖异常人数统计结束时间
		String endDt = DateHelper.getSystemDate();
		if(dateType != null && dateType > 1 && dateType <= 8){
			startDt = DateHelper.getNeedStartDateTime(dateType);
		}
		//结果是分页
		PageResult<NormalSugerModel> result = this.bloodSugarService.workbenchSugarDetailByNormal(doctorModel.getDoctorId(), startDt, endDt, page
				, payStatus ,visitType ,doctorModel.getDepartId(),doctorModel.getHospitalId());
		return Result.ok(result);
	}	
	
	/**
	 * 点击进入未统计血糖患者列表
     * @apiParam {Integer} dateType
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 行数 
     * @apiParam {String} payStatus 1付费 0免费
     * @apiParam {String} remind 1已提醒 0未提醒 null:已提醒和未提醒
	 * @apiParam {Integer} visitType 1 住院 2门诊居家 默认门诊居家
	 * @return
	 */
	@RequestMapping("/workbenchSugarDetailByNotMeasured")
	@ResponseBody
	public Result workbenchSugarDetailByNotMeasured(PageRequest page,Integer dateType,
			String payStatus,String remind ,Integer visitType){
		if(visitType == null){
			visitType = 2;
		}
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		//DateUtil有做时间处理
		String startDt = DateHelper.getNeedStartDateTime(1);//血糖异常人数统计开始时间
		String endDt = DateHelper.getSystemDate();//血糖异常人数统计结束时间
		if(dateType != null && dateType > 1 && dateType <= 8){
			startDt = DateHelper.getNeedStartDateTime(dateType);
		}
		//结果是分页
		PageResult<NormalSugerListResqModel> result = this.bloodSugarService.workbenchSugarDetailByNotMeasured(doctorModel.getDoctorId(), startDt, endDt,
				page, payStatus ,visitType ,doctorModel.getDepartId(),doctorModel.getHospitalId());
//		logger.info("[workbenchSugarDetailByNotMeasured]点击进入未统计血糖患者列表:"+JSON.toJSON(result));
		return Result.ok(result);
	}


	/**
	 * @api {post}/docapp/bloodsugar/countWorkbenchSugarDetail.do 未测量血糖列表免费患者和付费患者的人数
	 * @apiAuthor wangt
	 * @apiTime 2020/03/23
	 * @apiName countWorkbenchSugarDetail 未测量血糖列表免费患者和付费患者的人数
	 * @apiGroup web-count
	 * @apiVersion 0.0.0
	 * @apiParam {Integer} dateType
	 * @apiParam {String} payStatus 0未付费 1已付费
	 * @apiSampleRequest http://192.168.2.44:8999/intelligent-prescription/docapp/bloodsugar/countWorkbenchSugarDetail.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 * @apiSuccessExample {json} Success-Response:
	 * {
	 * 	"code": "0",
	 * 	"message": "成功",
	 * 	"obj": {
	 * 		"undetected": 0, //未检测血糖的免费患者
	 * 		"paid": 0, //检测血糖的付费患者
	 * 		"undetectedO": 0, //正常血糖的免费患者
	 * 		"paidO": 6, //正常血糖的付费患者
	 *        },
	 * 	"success": true
	 * }
	 */
	@RequestMapping("/countWorkbenchSugarDetail")
	@ResponseBody
	public Result countWorkbenchSugarDetail(Integer dateType) {
		String startDt = DateHelper.getNeedStartDateTime(1);
		String endDt = DateHelper.getSystemDate();
		if(dateType != null && dateType > 1 && dateType <= 8){
			startDt = DateHelper.getNeedStartDateTime(dateType);
		}
		CountMemberVO countMemberVO = this.bloodSugarService.countWorkbenchSugarDetail(startDt,endDt);

		return Result.ok(countMemberVO);
	}

	/**
	 * 血糖记录 统计部分 app从列表里面取
     * @param {string} paramKey 血糖记录时间段  作废
     * @param {string} startDt 开始时间
     * @param {string} endDt 结束时间
     * @param {string} memberId 患者id
	 * @return
	 */
	@RequestMapping("/getGraphsForParametersNew")
	@ResponseBody
	public Result getGraphsForParametersNew(String memberId,String startDt,String endDt){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		Map<String , Object> map = this.bloodSugarService.getGraphsForParametersNew(memberId, startDt, endDt);
		logger.info("首页统计结果数据：......."+JSON.toJSON(map));
		return Result.ok(map);
	}


	/**
	 * 血糖记录 统计部分 app从列表里面取
	 * @param {string} startDt 开始时间
	 * @param {string} endDt 结束时间
	 * @param {string} memberId 患者id
	 * @return
	 */
	@RequestMapping("/getGraphsForParametersNewBloodSugar")
	@ResponseBody
	public Result getGraphsForParametersNewBloodSugar(String memberId,String startDt,String endDt){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		Map<String , Object> map = this.statisticsService.getGraphsForParametersNewBloodSugar(memberId,startDt,endDt);
		return Result.ok(map);
	}
	
	/**
	 * 
     * @param {string} memberId 患者id
     * @param {string} paramCode 时间段
     * @param {string} recordTime 记录时间
     * @param {string} value 血糖值
     * @param {string} memo 备注
	 * @return
	 */
	@RequestMapping("/addMemberBloodRecord")
	@ResponseBody
	public Result addMemberBloodRecord(String memberId,String paramCode
			,String recordTime,String value,String memo){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		//血糖等级判断
		int paramLevel = bloodSugarLevelHandler(memberId , value , paramCode);

		AddBloodSugarMapperDTO addBloodSugarMapperDTO = new AddBloodSugarMapperDTO();
		addBloodSugarMapperDTO.setSid(DaoHelper.getSeq());
		addBloodSugarMapperDTO.setParamCode(paramCode);
		addBloodSugarMapperDTO.setParamValue(value);
		addBloodSugarMapperDTO.setRecordDt(recordTime);
		addBloodSugarMapperDTO.setRemark(memo);
		addBloodSugarMapperDTO.setOrigin(3);
		addBloodSugarMapperDTO.setMemberId(memberId);
		addBloodSugarMapperDTO.setOperatorType(2);
		addBloodSugarMapperDTO.setOperatorId(doctorModel.getDoctorId());
		addBloodSugarMapperDTO.setParamLevel(paramLevel);
		this.bloodSugarService.addMemberBloodRecord(addBloodSugarMapperDTO);

		return Result.ok();
	}
	/**
	 * 新增血糖
	 * @param addBloodSugarDTO
	 * @return
	 */
	@RequestMapping("/addMemberBloodRecordV2")
	@ResponseBody
	public Result addBloodSguar(String memberId,String paramCode
			,String recordTime,String value,String memo){
		ValidateTool.checkParamIsNull(memberId, "batchId");
		ValidateTool.checkParamIsNull(paramCode, "paramCode");
		ValidateTool.checkParamIsNull(recordTime, "recordTime");
		ValidateTool.checkParamIsNull(value, "value");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		AddBloodSugarServiceDTO addBloodSugarServiceDTO = new AddBloodSugarServiceDTO();
		addBloodSugarServiceDTO.setParamCode(paramCode);
		addBloodSugarServiceDTO.setParamValue(value);
		addBloodSugarServiceDTO.setMemberId(memberId);
		addBloodSugarServiceDTO.setRecordDt(recordTime);
		addBloodSugarServiceDTO.setRemark(memo);
		addBloodSugarServiceDTO.setOperatorId(doctorModel.getDoctorId());
		addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
		addBloodSugarServiceDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_DOCTOR_WEHCAT);
		String id = this.bloodSugarServiceV2.addBloodSugar(addBloodSugarServiceDTO);
		return new Result(id);
	}
    /**
     * 血糖级别处理
     * @return
     */
    private int bloodSugarLevelHandler(String memberId , String paramValue , String paramCode){
        MemberControlTargetBO memberControlTargetBO = null;
        RangeBO rangeBO = this.memberService.getMemberRange(memberId);
        if(rangeBO == null){
            memberControlTargetBO = this.memberService.getMemberDefaultControlTarget(memberId);
        }else{
			memberControlTargetBO = new MemberControlTargetBO();
            BeanUtils.copyProperties(memberControlTargetBO, rangeBO);
        }
        int level = SignConstant.SIGN_LEVEL_NORMAL;
        Float value = Float.parseFloat(paramValue);
        switch (paramCode){
            // 凌晨3点
            case SignConstant.PARAM_CODE_3AM:
                if(value > memberControlTargetBO.getHighBeforedawn()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforedawn()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //早餐后
            case SignConstant.PARAM_CODE_AFTER_BREAKFAST:
                if(value > memberControlTargetBO.getHighAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //晚餐后
            case SignConstant.PARAM_CODE_AFTER_DINNER:
                if(value > memberControlTargetBO.getHighAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //午餐后
            case SignConstant.PARAM_CODE_AFTER_LUNCH:
                if(value > memberControlTargetBO.getHighAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowAfterMeal()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //早餐前
            case SignConstant.PARAM_CODE_BEFORE_BREAKFAST:
                if(value > memberControlTargetBO.getHighBeforeBreakfast()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforeBreakfast()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //凌晨
            case SignConstant.PARAM_CODE_BEFORE_DAWN:
                if(value > memberControlTargetBO.getHighBeforedawn()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforedawn()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //晚餐前
            case SignConstant.PARAM_CODE_BEFORE_DINNER:
                if(value > memberControlTargetBO.getHighBeforeMeal()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforeMeal()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //午餐前
            case SignConstant.PARAM_CODE_BEFORE_LUNCH:
                if(value > memberControlTargetBO.getHighBeforeMeal()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforeMeal()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //睡前
            case SignConstant.PARAM_CODE_BEFORE_SLEEP:
                if(value > memberControlTargetBO.getHighBeforeSleep()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowBeforeSleep()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            //随机血糖
            case SignConstant.PARAM_CODE_RANDOM_TIME:
                if(value > memberControlTargetBO.getHighRandomSugar()){
                    level = SignConstant.SIGN_LEVEL_HIGH;
                }else if(value < memberControlTargetBO.getLowRandomSugar()){
                    level = SignConstant.SIGN_LEVEL_LOW;
                }
                break;
            default:
                break;
        }
        return level;
    }	
    
    /**
     * 获取单条血糖
     * @param batchId
     * @return
     */
	@RequestMapping("/bloodAbnormalDetail")
	@ResponseBody
	public Result bloodAbnormalDetail(String batchId){
		ValidateTool.checkParamIsNull(batchId, "batchId");
		BloodSugarPO suger = this.bloodSugarMapper.getBloodSugar(batchId);;
		logger.info("结果数据：......."+JSON.toJSON(suger));
		return Result.ok(suger);
	}

	/**
	 * 住院患者血糖异常详情列表
	 * @return
	 */
	@RequestMapping("/listInHospitalPatientAbnormalSugarDetail")
	@ResponseBody
	public Result listInHospitalPatientAbnormalSugarDetail(){
		DoctorSessionBO doctorSession = SessionTool.getWebSession();
		String today = DateHelper.getToday();
		List list = this.bloodSugarService.listInHospitalPatientAbnormalSugarDetail(doctorSession.getDepartId()
				,today + " 00:00:00" , today + " 23:59:59");
		return Result.ok(list);
	}
}
