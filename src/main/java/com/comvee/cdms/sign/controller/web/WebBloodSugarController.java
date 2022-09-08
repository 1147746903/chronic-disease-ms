package com.comvee.cdms.sign.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.vo.DepartmentVO;
import com.comvee.cdms.exportexcle.tool.ExcelUtil;
import com.comvee.cdms.machine.common.exception.DiabeteslsException;
import com.comvee.cdms.machine.common.utils.ResponseTool;
import com.comvee.cdms.machine.model.SebyParamLogModel;
import com.comvee.cdms.machine.model.ThpMemberMachine;
import com.comvee.cdms.machine.po.BedMachinePO;
import com.comvee.cdms.machine.service.MemberMachineService;
import com.comvee.cdms.member.dto.ListMemberBloodStaticsDTO;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.comvee.cdms.member.vo.*;
import com.comvee.cdms.sign.constant.BloodSugarCodeEnum;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.constant.SignLock;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.vo.*;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/web/bloodSugar")
public class WebBloodSugarController {

    /**
     * 日志常量
     * @author nzq
     * @date
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebBloodSugarController.class);

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private MemberMachineService memberMachineService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;


    /**
     * 加载患者血糖记录
     * @param listMemberBloodSugarDTO
     * @return
     */
    @RequestMapping("listMemberBloodSugar")
    public Result listMemberBloodSugar(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        List<BloodSugarPO> list = this.bloodSugarService.listBloodSugar(listBloodSugarDTO);
        return new Result(list);
    }

    /**
     * @api {post}/web/bloodSugar/listBloodSugarPage.do 加载患者血糖记录
     * @author 林雨堆
     * @time 2020/03/23
     * @apiName listBloodSugarPage 加载患者血糖记录
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} paramCode 血糖时间点code
     * @apiParam {String} codeDt 1、今日  2、三天  3、七天 4、一个月（30天）
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.203:8080/intelligent-prescription/web/bloodSugar/listBloodSugarPage.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listBloodSugarPage")
    public Result listBloodSugarPage(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO,PageRequest page){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        listBloodSugarDTO.setLoadAll(1);
        List<Map<String ,Object>> pageResult = this.bloodSugarService.listBloodSugarPage(listBloodSugarDTO, page);
        return new Result(pageResult);
    }

    /**
     * 血糖平均值、最高值、最低值
     * @param countBloodSugarDTO
     */
    @RequestMapping("loadBloodAvgMaxMin")
    public Result loadBloodAvgMaxMin(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object>  bloodAvgMaxMinVO = this.bloodSugarService.loadBloodAvgMaxMin(countBloodSugarDTO);
        return new Result(bloodAvgMaxMinVO);
    }


    /**
     * 血糖测量总次数、偏高、正常、偏低
     * @param countBloodSugarDTO
     */
    @RequestMapping("loadBloodNumHigLow")
    public Result loadBloodNumHigLow(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object> bloodNumHigLowVO = this.bloodSugarService.loadBloodNumHigLow2(countBloodSugarDTO);
        return new Result(bloodNumHigLowVO);
    }

    /**
     * 血糖趋势图
     * @param countBloodSugarDTO
     */
    @RequestMapping("findBloodNormalByStageDay")
    public Result findBloodNormalByStageDay(@Validated CountBloodSugarDTO countBloodSugarDTO){
        Map<String, Object> bloodNormalByStageDay = this.bloodSugarService.findBloodNormalByStageDay(countBloodSugarDTO);
        return new Result(bloodNormalByStageDay);
    }

    /**
     * @api {post}/web/bloodSugar/addBloodSugar.do 新增血糖记录
     * @author 王宇晨
     * @time 2020/03/24
     * @apiName addBloodSugar 新增血糖记录
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} paramCode 血糖时间点code 必填
     * @apiParam {String} paramValue 血糖值 必填
     * @apiParam {String} recordDt 记录时间 必填
     * @apiParam {String} origin 来源 必填
     * @apiParam {String} memberId 患者编号 必填
     * @apiParam {Integer} inHos 1住院 0日常 必填
     * @apiParam {Integer} operatorType 操作者类型 1 用户 2 医护 必填
     * @apiParam {String} remark 备注
     * @apiSampleRequest  http://192.168.7.203:8080/web/bloodSugar/addBloodSugar.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addBloodSugar")
    public Result addBloodSugar(@Validated AddBloodSugarServiceDTO addBloodSugarServiceDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addBloodSugarServiceDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addBloodSugarServiceDTO.setOrigin(addBloodSugarServiceDTO.getOrigin());
        String sid = "";
        SignLock.WEB_ADD_BLOOD_SUGAR_LOCK.lock();
        try{
            sid = this.bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
        }finally {
            SignLock.WEB_ADD_BLOOD_SUGAR_LOCK.unlock();
        }
        return new Result(sid);
    }

    /**
     * 获取血糖记录
     * @param sid
     * @return
     */
    @RequestMapping("getBloodSugar")
    public Result getBloodSugar(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        BloodSugarPO bloodSugarPO = this.bloodSugarService.getBloodSugar(sid);
        return new Result(bloodSugarPO);
    }

    /**
     * 601
     * 分页获取血糖记录列表
     * @param sidStr
     * @return
     */
    @RequestMapping("pageBloodSugar")
    public Result pageBloodSugar(String sidStr,PageRequest pager){
        ValidateTool.checkParamIsNull(sidStr, "sidStr");
        PageResult<BloodSugarPO> pageResult = this.bloodSugarService.pageBloodSugar(sidStr,pager);
        return new Result(pageResult);
    }

    /**
     * 601
     * @param sid
     * @param memberId
     * @param recordDt
     * @return
     */
    @RequestMapping("deleteBloodSugar")
    public Result deleteBloodSugar(String sid,String memberId,String recordDt){
        ValidateTool.checkParamIsNull(sid, "sid");
        ValidateTool.checkParamIsNull(memberId, "memberId");
        ValidateTool.checkParamIsNull(recordDt, "recordDt");
        Result result = new Result();
        if(this.bloodSugarService.deleteBloodSugar(sid,memberId,recordDt)){
            result.setCode("0");
            result.setMessage("删除成功");
        } else {
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取血糖建议
     * @param signId
     * @return
     */
    @RequestMapping("getBloodSugarSuggest")
    public Result getBloodSugarSuggest(String signId){
        ValidateTool.checkParamIsNull(signId, "signId");
        SignSuggestPO signSuggestPO = this.bloodSugarService.getBloodSugarSuggest(signId);
        return new Result(signSuggestPO);
    }

    /**
     * 新增血糖建议
     * @param addSuggestDTO
     * @return
     */
    @RequestMapping("addBloodSugarSuggest")
    public Result addBloodSugarSuggest(AddSuggestDTO addSuggestDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addSuggestDTO.setSenderId(doctorSessionBO.getDoctorId());
        String sid = this.bloodSugarService.addBloodSugarSuggest(addSuggestDTO);
        return new Result(sid);
    }

    /**
     * 7天内血糖（高、低）
     * @param listBloodSugarByDayDTO
     * @return
     */
    @RequestMapping("loadBloodSugarByDay")
    public Result loadBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listBloodSugarByDayDTO.setDoctorId(doctorSessionBO.getDoctorId());
        listBloodSugarByDayDTO.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<SugarMemberVO> re = this.bloodSugarService.loadBloodSugarByDay(listBloodSugarByDayDTO, page);
        return new Result(re);
    }

    /**
     * 时间段内没有血糖记录的患者(7天、30天)
     * @param listBloodSugarByDayDTO
     * @return
     */
    @RequestMapping("loadNotBloodSugarByDay")
    public Result loadNotBloodSugarByDay(ListBloodSugarByDayDTO listBloodSugarByDayDTO, PageRequest page){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listBloodSugarByDayDTO.setDoctorId(doctorSessionBO.getDoctorId());
        listBloodSugarByDayDTO.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<SugarMemberVO> re = this.bloodSugarService.loadNotBloodSugarByDay(listBloodSugarByDayDTO, page);
        return new Result(re);
    }

    /**
     * @api {post}/web/bloodSugar/listBloodSugarPageOfInHos.do 分页加载血糖记录（是否住院记录）
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listBloodSugarPageOfInHos
     * @apiGroup WEB-V6.0.0-C
     * @apiVersion 6.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} paramCode 血糖时间点code
     * @apiParam {String} codeDt 1、今日  2、三天  3、七天 4、一个月（30天）
     * @apiParam {Integer} inHos 1住院 0日常
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.2.40:8080/web/bloodSugar/listBloodSugarPageOfInHos.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listBloodSugarPageOfInHos")
    public Result listBloodSugarPageOfInHos(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO,PageRequest page){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        PageResult<Map<String,Object>> pageResult = this.bloodSugarService.listBloodSugarPageOfInHos(listBloodSugarDTO, page);
        return new Result(pageResult);
    }

    /**
     * @api {post}/web/bloodSugar/listRandomBloodSugarPageOfInHos.do 分页加载随机血糖列表（是否住院记录）
     * @author 林雨堆
     * @time 2018/07/19
     * @apiName listRandomBloodSugarPageOfInHos 分页加载随机血糖列表（是否住院记录）
     * @apiGroup web-bloodSugar
     * @apiVersion 4.0.0
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 结束时间
     * @apiParam {String} paramCode 血糖时间点code
     * @apiParam {String} codeDt 1、今日  2、三天  3、七天 4、一个月（30天）
     * @apiParam {Integer} inHos 1住院 0日常
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.2.40:8080/web/bloodSugar/listRandomBloodSugarPageOfInHos.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listRandomBloodSugarPageOfInHos")
    public Result listRandomBloodSugarPageOfInHos(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO, PageRequest page){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        PageResult<BloodSugarPO> pageResult = this.bloodSugarService.listRandomBloodSugarPageOfInHos(listBloodSugarDTO, page);
        return new Result(pageResult);
    }

    /**
     * 获取一周平均空腹血糖   所有餐后血糖
     * @param memberId
     * @return
     */
    @RequestMapping("/getAvgWeekBlood")
    public Result getAvgWeekBlood(String memberId){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        Map<String, Object> map = this.bloodSugarService.getAvgWeekBlood(memberId);
        return Result.ok(map);
    }

    /**
     * @api {post}/web/bloodSugar/listTodayBloodSugarOfMember.do 获取住院患者每日血糖
     * @author 王宇晨
     * @time 2020/03/24
     * @apiName listTodayBloodSugarOfMember 获取住院患者每日血糖
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号（科室编号串没有）
     * @apiParam {String} departmentIdStr 科室编号串，多个","隔开，获取指定科室患者
     * @apiParam {String} doctorId 医护人员编号（默认登录医生编号），（科室编号串没有，医院编号没有）获取医护人员可管理科室的患者
     * @apiParam {String} paramLevel 血糖等级 1 低血糖 3 正常血糖 5 高血糖
     *                                       1,2 偏低&偏高 1,3 偏低&正常 3,5 偏高&正常
     *                                       1,3,5 偏低&偏高&正常 "" 全部
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.203:8080/web/bloodSugar/listTodayBloodSugarOfMember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("listTodayBloodSugarOfMember")
    public Result listTodayBloodSugarOfMember(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page){
        if(StringUtils.isBlank(memberBloodSugarDTO.getDoctorId())){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            memberBloodSugarDTO.setDoctorId(doctorSessionBO.getDoctorId());
            memberBloodSugarDTO.setHospitalId(doctorSessionBO.getHospitalId());
        }
        PageResult<MemberBloodSugarVO> result = this.bloodSugarService.listTodayBloodSugarOfMember(memberBloodSugarDTO, page);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/bloodSugar/getTodayMemberBloodNum.do 获取住院每日血糖管理次数
     * @author 王宇晨
     * @time 2020/03/24
     * @apiName getTodayMemberBloodNum 获取住院每日血糖管理次数
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号
     * @apiParam {String} doctorId 医护人员编号（默认登录医生编号)，（医院编号没有）获取医护人员可管理科室的患者
     * @apiSampleRequest  http://192.168.7.203:8080/web/bloodSugar/getTodayMemberBloodNum.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/getTodayMemberBloodNum")
    public Result getTodayMemberBloodNum(TodayMemberBloodNumDTO dto){
        if(StringUtils.isBlank(dto.getDoctorId())){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            dto.setDoctorId(doctorSessionBO.getDoctorId());
        }
        dto.setJoinDocMember(false);
        TodayBloodNumVO result = this.bloodSugarService.getTodayMemberBloodNum(dto);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/bloodSugar/listMemberBloodByWhere.do 获取患者血糖记录(根据条件)
     * @author 王宇晨
     * @time 2020/03/24
     * @apiName listMemberBloodByWhere 获取患者血糖记录(根据条件)
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号（科室编号串没有）
     * @apiParam {String} departmentIdStr 科室编号串，多个","隔开，获取指定科室患者
     * @apiParam {String} doctorId 医护人员编号（默认登录医生编号)，（科室编号串没有,医院编号没有）获取医护人员可管理科室的患者
     * @apiParam {String} checkinStatus 入住状态 0-未入住 1-入住
     * @apiParam {String} paramCode 血糖code
     * @apiParam {String} memberId 患者编号
     * @apiParam {String} startDt 开始时间 (yyyy-MM-dd HH:mm:ss)
     * @apiParam {String} endDt 结束时间 (yyyy-MM-dd HH:mm:ss)
     * @apiParam {String} paramLevel 血糖等级 1偏低 3-正常  5-偏高
     * @apiParam {Boolean} isAbnormal 是否异常 true 是 false 否
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.203:8080/web/bloodSugar/listMemberBloodByWhere.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listMemberBloodByWhere")
    public Result listMemberBloodByWhere(TodayHosMemberBloodDTO todayHosMemberBloodDTO,PageRequest page){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if(StringUtils.isBlank(todayHosMemberBloodDTO.getDoctorId())){
            todayHosMemberBloodDTO.setDoctorId(doctorSessionBO.getDoctorId());
        }
        PageResult<TodayHosMemberBloodVO> result = this.bloodSugarService.listMemberBloodByWhere(todayHosMemberBloodDTO, page);
        return Result.ok(result);
    }

    /**
     * @api {post}/web/bloodSugar/listHighRiskParamLogOfMember.do 获取高危预警患者
     * @author 王宇晨
     * @time 2020/03/24
     * @apiName listHighRiskParamLogOfMember 获取高危预警患者
     * @apiGroup WEB-V6.0.0
     * @apiVersion 6.0.0
     * @apiParam {String} hospitalId 医院编号（科室编号串没有）
     * @apiParam {String} departmentIdStr 科室编号串，多个","隔开，获取指定科室患者
     * @apiParam {String} doctorId 医护人员编号（默认登录医生编号)，（科室编号串没有,医院编号没有）获取医护人员可管理科室的患者
     * @apiParam {Integer} checkinStatus 在院状态 0非在院 ，1在院
     * @apiParam {String} paramDt 记录日期 (yyyy-MM-dd)
     * @apiParam {Double} highValue 血糖高值 >16.7
     * @apiParam {Double} lowValue 血糖低值 <3.9
     * @apiParam {Integer} treeTimesHigh 是否连续3次偏高 1是 0否
     * @apiParam {Double} highRate 高血糖概率
     * @apiParam {Double} lowRate 低血糖概率
     * @apiParam {Double} standardValue 标准差值
     * @apiParam {Integer} bloodType 0异常 1偏低 2偏高
     * @apiParam {Integer} page 页码
     * @apiParam {Integer} rows 页数
     * @apiSampleRequest  http://192.168.7.203:8080/web/bloodSugar/listHighRiskParamLogOfMember.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listHighRiskParamLogOfMember")
    public Result listHighRiskParamLogOfMember(MemberParamValueDTO paramDTO, PageRequest page){
        if(StringUtils.isBlank(paramDTO.getDoctorId())){
            DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
            paramDTO.setDoctorId(doctorSessionBO.getDoctorId());
            paramDTO.setHospitalId(doctorSessionBO.getHospitalId());
        }
        paramDTO.setDealIsLook(true);
        PageResult<MemberParamValueVO> result = this.bloodSugarService.listHighRiskParamLogOfMember(paramDTO, page);
        return Result.ok(result);
    }

    /**
     * 高危血糖提醒
     * @return
     */
    @RequestMapping("/countHighRiskParamLogOfMember")
    public Result countHighRiskParamLogOfMember(){
        MemberParamValueDTO paramDTO = new MemberParamValueDTO();
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        paramDTO.setDoctorId(doctorSessionBO.getDoctorId());
        paramDTO.setHospitalId(doctorSessionBO.getHospitalId());
        Long result = this.bloodSugarService.countHighRiskParamLogOfMember(paramDTO);
        return Result.ok(result);
    }

    @RequestMapping("/addParamlogByMachine")
    public void addParamlogByMachine(HttpServletRequest request,
                                     HttpServletResponse response, SebyParamLogModel model, String id,
                                     String recordTime, String acode, String innerCode, String timestamp, String platCode, String producerCode) {
        Map properties = request.getParameterMap();
        LOGGER.info("BloodSugarContorller->addParamlogByMachine======="+request.getRequestURI()+ JSON.toJSONString(properties));

        /*
         *
         * String validCodeStr="id=" + id +"&platCode="+platCode+ "&innerCode="+ innerCode + "&paramCode=" + model.getParamCode()
                    + "&recordTime=" + recordTime + "&paramValue="+ model.getParamValue() + "&isValid=" + model.getIsValid()
                    + "&timestamp=" + timestamp +"&producerCode="+producerCode+ "&comvee&comvee";
         *
         */

        Map<String,Object> r = new HashMap<>(4);
        try {
            if(StringUtils.isBlank(innerCode)){
                r.put("success",false);
                r.put("code","-1");
                r.put("msg","参数innerCode不能为空");
                ResponseTool.responseJson(r, request, response);
                return;
            }
            ThpMemberMachine memberMachine = memberMachineService.queryByMachineCode(innerCode);
            if(memberMachine == null) {

                BedMachinePO bedMachinePO = this.memberMachineService.queryBedByMachineCode(innerCode);
                LOGGER.info("设备被床位绑定的信息:"+bedMachinePO);
                if (bedMachinePO == null){
                    //没有患者入住，直接返回。
                    r.put("success",false);
                    r.put("code","-1");
                    r.put("msg","设备未绑定");
                }else{
                    //不为空，则查询住院表这个床位的患者是谁。
                    MemberCheckinInfoPO memberCheckinInfoPO =  this.memberCheckinInfoMapper.getMemberCheckinInfoById(bedMachinePO.getBedId());
                    LOGGER.info("住院的患者信息:"+memberCheckinInfoPO);
                    //有患者入住，将上传的血糖记录，传给患者
                    AddBloodSugarServiceDTO paramLog = new AddBloodSugarServiceDTO();
                    paramLog.setParamCode(model.getParamCode());
                    paramLog.setParamValue(model.getParamValue());
                    paramLog.setInHos(0);
                    paramLog.setMemberId(memberCheckinInfoPO.getMemberId());
                    paramLog.setRecordDt(recordTime);
                    paramLog.setOperatorId(("0"));
                    paramLog.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
                    paramLog.setRemark(" ");
                    paramLog.setOrigin(SignConstant.ORIGIN_G_DEVICE);
                    LOGGER.info("将血糖通过床绑定的血糖仪上传到人:"+paramLog);
                    String resstr = this.bloodSugarService.addBloodSugar(paramLog);
                    if(!StringUtils.isBlank(resstr)) {
                        r.put("success",true);
                        r.put("code","0");
                        r.put("msg",resstr);
                    }else {
                        r.put("success",false);
                        r.put("code","-1");
                        r.put("msg","录入血糖失败");
                    }
                }
            }else {
                AddBloodSugarServiceDTO paramLog = new AddBloodSugarServiceDTO();
                paramLog.setParamCode(model.getParamCode());
                paramLog.setParamValue(model.getParamValue());
                paramLog.setInHos(0);
                paramLog.setMemberId(memberMachine.getMemberId().toString());
                paramLog.setRecordDt(recordTime);
                paramLog.setOperatorId(("0"));
                paramLog.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
                paramLog.setRemark(" ");
                paramLog.setOrigin(SignConstant.ORIGIN_G_DEVICE);
                String resstr = this.bloodSugarService.addBloodSugar(paramLog);
                if(!StringUtils.isBlank(resstr)) {
                    r.put("success",true);
                    r.put("code","0");
                    r.put("msg",resstr);
                }else {
                    r.put("success",false);
                    r.put("code","-1");
                    r.put("msg","录入血糖失败");
                }
            }
            ResponseTool.responseJson(r, request, response);
        } catch (DiabeteslsException e) {
            ResponseTool.exceptionReturnNew(e, request, response);
        }

    }

    /**
     * @api {post}/web/bloodsugar/listMemberBlood.do 条件查询患者血糖记录(血糖打印数据)
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName loadSensorBloodSugarChart 获取探头的血糖统计图表
     * @apiGroup web-sign-bloodsugar
     * @apiVersion 0.0.1
     * @apiParam {String} startDt  监测开始时间 格式"1970-01-01",默认为当天的时间（必填）
     * @apiParam {String} endDt  监测结束时间 格式"1970-01-01",默认为当天的时间（必填）
     * @apiParam {String} type 患者分类 1:默认取全部患者,2取住院患者,3取虚拟病区患者,默认为1.（必填）
     * @apiParam {Integer} paramLevel 血糖情况 1:低,5:高, 默认为空.（必填）
     * @apiParam {Integer} page 当前页数（必填）
     * @apiParam {Integer} rows 每页显示记录数（必填）
     * @apiSampleRequest  {post}/web/bloodsugar/listMemberBlood.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     * @apiSuccessExample {json} Success-Response:
     * {code: "0", message: "成功", obj: {bloodGlucoseRecordPrintingVOS: {pageNum: 1, pageSize: 0,…},…},…}
     * code: "0"
     * message: "成功"
     * obj: {bloodGlucoseRecordPrintingVOS: {pageNum: 1, pageSize: 0,…},…}
     * bloodGlucoseRecordPrintingVOS: {pageNum: 1, pageSize: 0,…}
     * pageNum: 1
     * pageSize: 0
     * rows: [{afterBreakfastSugar: "", afterDinnerSugar: "", afterLunchSugar: "", badNo: "01",…},…]
     * 0: {afterBreakfastSugar: "", afterDinnerSugar: "", afterLunchSugar: "", badNo: "01",…}
     * afterBreakfastSugar: "" //早餐后
     * afterDinnerSugar: "" 晚餐后
     * afterLunchSugar: "" 午餐后
     * badNo: "01" 床位号
     * beforeBreakfastSugar: "" 早餐前
     * beforeDinnerSugar: "" 晚餐前
     * beforeLunchSugar: "" 午餐前
     * beforeSleepSugar: "" 睡前
     * beforedawnSugar: "12.0" 凌晨
     * departmentId: "" 科室id
     * departmentName: "内分泌科"  //科室
     * memberName: "刘月思" 患者
     * paramLevel: 5 //血糖等级 1,2
     * randomSugar: "" 随机
     * recordDt: "2020-10-13" 记录日期
     * totalPages: 0
     * totalRows: 0
     * success: true
     */
    @RequestMapping("/listMemberBlood")
    public Result listMemberBlood(BloodGlucoseRecordPrintingDTO dto, PageRequest page) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationId(doctorSessionBO.getDoctorId());
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<BloodGlucoseRecordPrintingVO> bloodGlucoseRecordVO = this.bloodSugarService.listMemberBlood(dto,page);
        return Result.ok(bloodGlucoseRecordVO);
    }

    /**
     * @api {post}/web/bloodsugar/listDepartment.do 获取科室(血糖打印数据)
     * @author 林雨堆
     * @time 2020/06/11 17:00
     * @apName listDepartment 获取科室
     * @apiGroup web-sign-bloodsugar
     * @apiVersion 0.0.1
     * @apiParam {String} type 患者分类 1:默认取全部患者,2取住院患者,3取虚拟病区患者,默认为1.（必填）
     * @apiSampleRequest  {post}/web/bloodsugar/listMemberBlood.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("/listDepartment")
    public Result listDepartment(BloodGlucoseRecordPrintingDTO dto) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setOperationId(doctorSessionBO.getDoctorId());
        dto.setHospitalId(doctorSessionBO.getHospitalId());
        List<DepartmentVO> doctorDepartmentVOS = this.bloodSugarService.listDepartment(dto);
        return Result.ok(doctorDepartmentVOS);
    }

    /**
     * 旧版
     * @param listMemberBloodSugarDTO
     * @param page
     * @return
     */
    @RequestMapping("listBloodSugarPageByInHos")
    public Result listBloodSugarPageByInHos(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO,PageRequest page){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        PageResult<Map<String,Object>> pageResult = this.bloodSugarService.listBloodSugarPageOfInHos(listBloodSugarDTO, page);
        return new Result(pageResult);
    }

    /**
     * 旧版
     * @param listMemberBloodSugarDTO
     * @param page
     * @return
     */
    @RequestMapping("listRandomBloodSugarPageByInHos")
    public Result listRandomBloodSugarPageByInHos(@Validated ListMemberBloodSugarDTO listMemberBloodSugarDTO, PageRequest page){
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        BeanUtils.copyProperties(listBloodSugarDTO, listMemberBloodSugarDTO);
        PageResult<BloodSugarPO> pageResult = this.bloodSugarService.listRandomBloodSugarPageOfInHos(listBloodSugarDTO, page);
        return new Result(pageResult);
    }

    /**
     * v8.1.0 全院血糖记录列表
     * @param listMemberBloodSugarHospitalDTO
     * @param page
     * @return
     */
    @RequestMapping("/listMemberBloodHospital")
    public Result listMemberBloodHospital(ListMemberBloodSugarHospitalDTO listMemberBloodSugarHospitalDTO,PageRequest page){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        if (StringUtils.isBlank(listMemberBloodSugarHospitalDTO.getParamCode())){
            return Result.ok(new PageResult<MemberSugarHospitalListVO>());
        }
        String[] split = listMemberBloodSugarHospitalDTO.getParamCode().split(",");
        List<String> paramCodeList = Arrays.asList(split);
        List<String> list = new ArrayList<>();
        for (String s : paramCodeList) {
            StringBuilder sb = new StringBuilder(s.toLowerCase()).append("_json");
            list.add(sb.toString());
        }
        listMemberBloodSugarHospitalDTO.setParamCode(null);
        listMemberBloodSugarHospitalDTO.setParamCodeList(list);
        listMemberBloodSugarHospitalDTO.setDoctorId(webSession.getDoctorId());
        listMemberBloodSugarHospitalDTO.setHospitalId(webSession.getHospitalId());
        PageResult<MemberSugarHospitalListVO>  resultList = this.bloodSugarService.listMemberBloodHospital(listMemberBloodSugarHospitalDTO, page);
        return Result.ok(resultList);
    }

    /**
     * 监测任务
     * @param dto
     * @param page
     * @return
     */
    @RequestMapping("/listBloodSugarTask")
    public Result listBloodSugarTask(MemberBloodSugarDTO dto,PageRequest page){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageResult<MemberBloodSugarVO> memberBloodSugarVOPageResult = bloodSugarService.listBloodSugarTask(dto, page);
        return Result.ok(memberBloodSugarVOPageResult);
    }


    /**
     * v8.1.0全院血糖记录导出excel
     * @param listMemberBloodSugarHospitalDTO
     * @param response
     */
    @RequestMapping("/bloodSugar/export")
    public void exportMemberBloodHospital(ListMemberBloodSugarHospitalDTO listMemberBloodSugarHospitalDTO, HttpServletResponse response){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        List<MemberSugarHospitalListVO> result = new ArrayList<>();
        if (!StringUtils.isBlank(listMemberBloodSugarHospitalDTO.getParamCode())){
            String[] split = listMemberBloodSugarHospitalDTO.getParamCode().split(",");
            List<String> paramCodeList = Arrays.asList(split);
            List<String> list = new ArrayList<>();
            for (String s : paramCodeList) {
                StringBuilder sb = new StringBuilder(s.toLowerCase()).append("_json");
                list.add(sb.toString());
            }
            listMemberBloodSugarHospitalDTO.setParamCode(null);
            listMemberBloodSugarHospitalDTO.setParamCodeList(list);
            listMemberBloodSugarHospitalDTO.setDoctorId(webSession.getDoctorId());
            listMemberBloodSugarHospitalDTO.setHospitalId(webSession.getHospitalId());
            PageRequest pageRequest = new PageRequest();
            pageRequest.setRows(999999);
            PageResult<MemberSugarHospitalListVO>  resultList = this.bloodSugarService.listMemberBloodHospital(listMemberBloodSugarHospitalDTO,pageRequest);
            result = resultList.getRows();
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (MemberSugarHospitalListVO vo : result) {
            Object[] obj = new Object[14];
            obj[0] = vo.getMemberName();
            obj[1] = vo.getHospitalNo();
            obj[2] = vo.getBedNo();
            obj[3] =vo.getDoctorName();
            obj[4] = vo.getRecordDt();
            obj[5] = bloodSugarExportValueHandler(vo.getBeforedawnList());
            obj[6] = bloodSugarExportValueHandler(vo.getBeforeBreakfastList());
            obj[7] = bloodSugarExportValueHandler(vo.getAfterBreakfastList());
            obj[8] = bloodSugarExportValueHandler(vo.getBeforeLunchList());
            obj[9] = bloodSugarExportValueHandler(vo.getAfterLunchList());
            obj[10] = bloodSugarExportValueHandler(vo.getBeforeDinnerList());
            obj[11] = bloodSugarExportValueHandler(vo.getAfterDinnerList());
            obj[12] = bloodSugarExportValueHandler(vo.getBeforeSleepList());
            obj[13] = bloodSugarExportValueHandler(vo.getRandomtimeList());
            dataList.add(obj);
        }
        String[] rowName = {"患者", "住院号", "床号", "责任医生", "监测日期",
                "凌晨", "空腹","早餐后", "午餐前", "午餐后", "晚餐前",
                "晚餐后", "睡前", "随机"};
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
            String fileName= new String("血糖记录.xls".getBytes("UTF-8"),"iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName,dataList,fileName,response);
        }catch(Exception e){
            LOGGER.error("",e);
        }
    }

    /**
     * 血糖任务列表  导出
     * @param memberBloodSugarDTO
     * @param response
     */
    @RequestMapping("/sugarTask/export")
    public void listMemberBloodHospital(MemberBloodSugarDTO memberBloodSugarDTO, HttpServletResponse response){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        memberBloodSugarDTO.setDoctorId(doctorSessionBO.getDoctorId());
        memberBloodSugarDTO.setHospitalId(doctorSessionBO.getHospitalId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(999999);
        //PageResult<MemberBloodSugarVO> resultList = this.bloodSugarService.listTodayBloodSugarOfMember(memberBloodSugarDTO, pageRequest);
        PageResult<MemberBloodSugarVO> resultList = bloodSugarService.listBloodSugarTask(memberBloodSugarDTO, pageRequest);
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (MemberBloodSugarVO vo : resultList.getRows()) {
            Object[] obj = new Object[13];
            obj[0] = vo.getMemberName();
            obj[1] = vo.getHospitalNo();
            obj[2] = vo.getDepartmentName();
            obj[3] =vo.getDoctorName();
            obj[4] =daySugarTaskHandler(vo,"1", BloodSugarCodeEnum.BEFOREDAWN.getCode());
            obj[5] =daySugarTaskHandler(vo,"2",BloodSugarCodeEnum.BEFOREBREAKFAST.getCode());
            obj[6] =daySugarTaskHandler(vo,"3",BloodSugarCodeEnum.AFTERBREAKFAST.getCode());
            obj[7] =daySugarTaskHandler(vo,"4",BloodSugarCodeEnum.BEFORELUNCH.getCode());
            obj[8] =daySugarTaskHandler(vo,"5",BloodSugarCodeEnum.AFTERLUNCH.getCode());
            obj[9] =daySugarTaskHandler(vo,"6",BloodSugarCodeEnum.BEFOREDINNER.getCode());
            obj[10] =daySugarTaskHandler(vo,"7",BloodSugarCodeEnum.AFTERDINNER.getCode());
            obj[11] =daySugarTaskHandler(vo,"8",BloodSugarCodeEnum.BEFORESLEEP.getCode());
            obj[12] =daySugarTaskHandler(vo,"9",BloodSugarCodeEnum.RANDOMTIME.getCode());
            dataList.add(obj);
        }
        String[] rowName = {"患者", "住院号", "科室", "责任医生",
                "凌晨", "空腹","早餐后", "午餐前", "午餐后", "晚餐前",
                "晚餐后", "睡前", "随机"};
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
            String fileName= new String("血糖任务列表.xls".getBytes("UTF-8"),"iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName,dataList,fileName,response);
        }catch(Exception e){
            LOGGER.error("",e);
        }
    }


    /**
     * v8.1.0动态血糖列表--住院
     * @param dto
     * @param page
     * @return
     */
    @RequestMapping("/dyInSugarDataList")
    public Result dynamicInList(@Valid ListMemberDTO dto, PageRequest page){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageResult<DySugarDataListVO> dySugarDataListVOPageResult = bloodSugarService.listMemberDySugarData(dto, page);
        return Result.ok(dySugarDataListVOPageResult);
    }

    /**
     * v8.1.0动态血糖列表--出院
     * @param
     * @param page
     * @return
     */
    @RequestMapping("/dyOutSugarDataList")
    public Result dynamicOutList(@Valid ListMemberDTO dto,PageRequest page){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageResult<DySugarDataListVO> dySugarDataListVOPageResult = bloodSugarService.listMemberDySugarDataOut(dto, page);
        return Result.ok(dySugarDataListVOPageResult);
    }


    /**
     * v8.1.0患者统计
     */
    @RequestMapping("/memberStatics")
    public Result memberStatics(ListMemberBloodStaticsDTO listMemberBloodStaticsDTO , PageRequest page){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(listMemberBloodStaticsDTO.getType(),"type");
        listMemberBloodStaticsDTO.setDoctorId(webSession.getDoctorId());
        listMemberBloodStaticsDTO.setHospitalId(webSession.getHospitalId());
        PageResult<ListMemberBloodStaticsVO> mapPageResult = bloodSugarService.listMemberStatics(listMemberBloodStaticsDTO,page);
        return Result.ok(mapPageResult);
    }


    /**
     * v8.1.0血糖预警列表
     */
    @RequestMapping("/bloodSugarWarnList")
    public Result bloodSugarWarn(SugarMonitorStaticsDTO dto ,PageRequest page){
        ValidateTool.checkParamIsNull(dto.getParamLevel(),"paramLevel");
        if (!dto.getParamLevel().equals(1) && !dto.getParamLevel().equals(5)){
            throw new BusinessException("paramLevel参数错误");
        }
        if (!StringUtils.isBlank(dto.getCode())){
            String[] split = dto.getCode().split(",");
            dto.setCodeList(Arrays.asList(split));
            dto.setCode(null);
        }else {
            return Result.ok(new PageResult<ListBloodSugarWarnVO>());
        }
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        Map<String, Object> map = bloodSugarService.bloodSugarWarnList(dto, page);
        return Result.ok(map);
    }


    /**
     * 预警状态处理
     * @param sid
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result ignoreOrHandleStatus(String sid,String status){
        ValidateTool.checkParamIsNull(sid,"sid");
        ValidateTool.checkParamIsNull(status,"status");
        boolean flag = bloodSugarService.handleBloodSugarBySid(sid, status);
        if (flag){
            return Result.ok();
        }
        return Result.ok("-1");
    }

    /**
     * 动态血糖列表导出--住院
     * @param
     * @param response
     */
    @RequestMapping("/dyInSugarDataList/export")
    public void exportDyInSugarDataList(@Valid ListMemberDTO dto, HttpServletResponse response){
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(999999);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageResult<DySugarDataListVO> pageResult = bloodSugarService.listMemberDySugarData(dto, pageRequest);
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (DySugarDataListVO vo : pageResult.getRows()) {
            Object[] obj = new Object[13];
            obj[0] = vo.getMemberName();
            obj[1] = vo.getHospitalNo();
            obj[2] = vo.getDoctorName();
            obj[3] = vo.getSensorNo();
            obj[4] = vo.getMonitorDt();
            obj[5] = vo.getAvgNum();
            obj[6] = vo.getGhb();
            obj[7] = vo.getCoefficientOfVariation();
            obj[8] = vo.getCustomLessThanRatio();
            obj[9] = vo.getAwiTimeRateOfLow();
            obj[10] = vo.getAwiTimeRateOfNormal();
            obj[11] = vo.getAwiTimeRateOfHigh();
            obj[12] = vo.getCustomGreaterThanRatio();
            dataList.add(obj);
        }
        String[] rowName = {"患者", "住院号", "责任医生","探头编号","监测时间", "平均血糖", "预估糖化",
                "<3.0mmol/L的时间占比", "低于目标范围的时间占比", "达标时间占比", "高于目标范围的时间占比", ">13.0mmol/L的时间占比"};
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
            String fileName= new String("动态血糖住院患者.xls".getBytes("UTF-8"),"iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName,dataList,fileName,response);
        }catch(Exception e){
            LOGGER.error("",e);
        }
    }



    /**
     * 动态血糖列表导出--出院
     * @param
     * @param response
     */
    @RequestMapping("/dyOutSugarDataList/export")
    public void exportMemberStaticsDataList(@Valid ListMemberDTO dto, HttpServletResponse response){
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(999999);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageResult<DySugarDataListVO> pageResult = bloodSugarService.listMemberDySugarDataOut(dto, pageRequest);
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (DySugarDataListVO vo : pageResult.getRows()) {
            Object[] obj = new Object[13];
            obj[0] = vo.getMemberName();
            obj[1] = vo.getDoctorName();
            obj[2] = vo.getOutHospitalDt();
            obj[3] = vo.getSensorNo();
            obj[4] = vo.getMonitorDt();
            obj[5] = vo.getAvgNum();
            obj[6] = vo.getGhb();
            obj[7] = vo.getCoefficientOfVariation();
            obj[8] = vo.getCustomLessThanRatio();
            obj[9] = vo.getAwiTimeRateOfLow();
            obj[10] = vo.getAwiTimeRateOfNormal();
            obj[11] = vo.getAwiTimeRateOfHigh();
            obj[12] = vo.getCustomGreaterThanRatio();
            dataList.add(obj);
        }
        String[] rowName = {"患者", "责任医生","出院时间","探头编号","监测时间", "平均血糖", "预估糖化",
                "<3.0mmol/L的时间占比", "低于目标范围的时间占比", "达标时间占比", "高于目标范围的时间占比", ">13.0mmol/L的时间占比"};
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
            String fileName= new String("动态血糖出院患者.xls".getBytes("UTF-8"),"iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName,dataList,fileName,response);
        }catch(Exception e){
            LOGGER.error("",e);
        }
    }



    /**
     * 患者统计列表导出
     * @param
     * @param response
     */
    @RequestMapping("/memberStatics/export")
    public void exportDyOutSugarDataList(ListMemberBloodStaticsDTO listMemberBloodStaticsDTO, HttpServletResponse response) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(999999);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(listMemberBloodStaticsDTO.getType(), "type");
        listMemberBloodStaticsDTO.setDoctorId(webSession.getDoctorId());
        listMemberBloodStaticsDTO.setHospitalId(webSession.getHospitalId());
        PageResult<ListMemberBloodStaticsVO> mapPageResult = bloodSugarService.listMemberStatics(listMemberBloodStaticsDTO, pageRequest);
        List<Object[]> dataList = new ArrayList<Object[]>();
        String[] rowName = null;
        String title = "";
        if (listMemberBloodStaticsDTO.getType() == 1){
            title = "住院患者.xls";
        }else if (listMemberBloodStaticsDTO.getType() == 2){
            title = "出院患者.xls";
        }else if (listMemberBloodStaticsDTO.getType() == 3){
            title = "所有患者.xls";
        }else if (listMemberBloodStaticsDTO.getType() == 4){
            title = "科室统计.xls";
        }
        if (listMemberBloodStaticsDTO.getType() == 4) {
            for (ListMemberBloodStaticsVO vo : mapPageResult.getRows()) {
                Object[] obj = new Object[7];
                obj[0] = vo.getDepartmentName();
                obj[1] = vo.getMemberNum();
                obj[2] = vo.getMonitorNum();
                obj[3] = vo.getHighSugarNum();
                obj[4] = vo.getLowSugarNum();
                obj[5] = vo.getNormalSugarNum();
                obj[6] = vo.getRate();
                dataList.add(obj);
            }
            rowName = new String[]{"科室", "患者人数", "监测次数", "高血糖次数", "低血糖次数", "正常血糖次数", "达标率"};
        } else {
            rowName = new String[]{"患者姓名", "科室", "责任医生", "监测次数", "高血糖次数", "低血糖次数", "正常血糖次数", "达标率"};
            for (ListMemberBloodStaticsVO vo : mapPageResult.getRows()) {
                Object[] obj = new Object[8];
                obj[0] = vo.getMemberName();
                obj[1] = vo.getDepartmentName();
                obj[2] = vo.getDoctorName();
                obj[3] = vo.getMonitorNum();
                obj[4] = vo.getHighSugarNum();
                obj[5] = vo.getLowSugarNum();
                obj[6] = vo.getNormalSugarNum();
                obj[7] = vo.getRate();
                dataList.add(obj);
            }
        }
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try {
            String fileName = new String(title.getBytes("UTF-8"), "iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName, dataList, fileName, response);
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }

    /**
     * 血糖预警列表导出
     * @param
     * @param
     */
    @RequestMapping("/bloodSugarWarnList/export")
    public void exportBloodSugarWarnList(SugarMonitorStaticsDTO dto, HttpServletResponse response){
        ValidateTool.checkParamIsNull(dto.getParamLevel(),"paramLevel");
        if (!dto.getParamLevel().equals(1) && !dto.getParamLevel().equals(5)){
            throw new BusinessException("paramLevel参数错误");
        }
        if (!StringUtils.isBlank(dto.getCode())){
            String[] split = dto.getCode().split(",");
            dto.setCodeList(Arrays.asList(split));
        }
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        dto.setHospitalId(webSession.getHospitalId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(999999);
        Map<String, Object> map = bloodSugarService.bloodSugarWarnList(dto, pageRequest);
        PageResult<ListBloodSugarWarnVO> pageResult = (PageResult)map.get("list");
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (ListBloodSugarWarnVO vo : pageResult.getRows()) {
            Object[] obj = new Object[10];
            obj[0] = vo.getDepartName();
            obj[1] = vo.getMemberName();
            obj[2] = vo.getHospitalNo();
            obj[3] = vo.getBedNo();
            obj[4] = vo.getParamValue();
            obj[5] = vo.getRecordDt();
            obj[6] = BloodSugarCodeEnum.getName(vo.getParamCode());
            obj[7] = vo.getRange();
            obj[8] = vo.getDoctorName();
            obj[9] = vo.getWarnStatus().equals("1")?"已处理":vo.getWarnStatus().equals("2")?"已忽略":"待处理";
            dataList.add(obj);
        }
        String[] rowName = {"科室", "患者姓名","住院号","床号","血糖值","监测时间","监测时段","控制目标","责任医生","状态"};
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
            String fileName= new String("血糖预警.xls".getBytes("UTF-8"),"iso-8859-1");    //生成word文件的文件名
            excelUtil.exportExcelHospitalBlood(rowName,dataList,fileName,response);
        }catch(Exception e){
            LOGGER.error("",e);
        }
    }



    //有值优先值，没值看有没有schema，有schema没值就空，没schemam也没值就--
    private String daySugarTaskHandler(MemberBloodSugarVO vo,String index,String code){
        Map<String, Object> map = vo.getMemberSugar();
        String result = "--";
        if (vo.getMemberSugar().get(code)==null) {
            if (!StringUtils.isBlank(vo.getTodaySchema())) {
                String[] split = vo.getTodaySchema().split(",");
                for (String s : split) {
                    if (s.equals(index)) {
                        result = "";
                    }
                }
            }
        }else {
//            String json = JSON.toJSONString(map.get(code));
//            if (!StringUtils.isBlank(json) && !json.equals("null")){
//                JSONObject jsonObject = JSON.parseObject(json);
//                Object paramValue = jsonObject.get("paramValue");
//                result = paramValue.toString();
//            }
            String json = JSON.toJSONString(map.get(code+"List"));
            List<Map> mapList = JSONArray.parseArray(json, Map.class);
            result = bloodSugarExportValueHandler(mapList);
        }
        return result;
    }

    private String bloodSugarExportValueHandler(List<Map> list){
        StringBuilder sb = new StringBuilder();
        if (null != list && list.size() !=0){
            for (Map map : list) {
                String paramValue = String.valueOf(map.get("paramValue"));
                String recordDt = String.valueOf(map.get("recordDt"));
                String strongCode = String.valueOf(map.get("strongCode"));
                Date date = DateHelper.getDate(recordDt, DateHelper.DATETIME_FORMAT);
                String time = "("+strongCode+")"+DateHelper.getDate(date, DateHelper.TIME_FORMAT);
                sb.append(time+";"+paramValue+"\r\n");
            }
            if (sb.subSequence(sb.length() - 2, sb.length()).equals("\r\n")){
                sb.setLength(sb.length() - 2);
            }
        }else {
            sb = new StringBuilder("--");
        }
        return sb.toString();
    }

}
