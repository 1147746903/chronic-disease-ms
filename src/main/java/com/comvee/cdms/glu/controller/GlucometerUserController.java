package com.comvee.cdms.glu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.app.doctorapp.service.BloodSugarServiceI;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.glu.dto.AddGluMemberDTO;
import com.comvee.cdms.glu.model.*;
import com.comvee.cdms.glu.service.GluServiceI;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DoctorDepartmentVO;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.machine.po.MachinePO;
import com.comvee.cdms.machine.service.MachineService;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.MemberDoctorConstant;
import com.comvee.cdms.member.constant.MemberLock;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberManageService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.qualitycontrol.model.param.AddQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.param.ListQualityControlParam;
import com.comvee.cdms.qualitycontrol.service.QualityControlService;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodKetoneServiceDTO;
import com.comvee.cdms.sign.dto.ListBloodKetoneDTO;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.dto.MemberBloodSugarDTO;
import com.comvee.cdms.sign.po.BloodKetonePO;
import com.comvee.cdms.sign.service.BloodKetoneService;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.vo.BloodSugarParamSettingVO;
import com.comvee.cdms.sign.vo.MemberBloodSugarVO;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**  
 * @author: nzq
 * @date???2020-07-28  
 * @version:v1.0
 */
@RestController
@RequestMapping("/glucometer")
public class GlucometerUserController {
	public static Logger logger = LoggerFactory.getLogger(GlucometerUserController.class);
    @Autowired
    @Qualifier("gluService")
    public GluServiceI gluService;
    
    @Autowired
    @Qualifier("memberService")
    public MemberService memberService;

    @Autowired
    private BloodSugarServiceI bloodSugarService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BloodSugarService bloodSugarServiceNew;

    @Autowired
    private BloodKetoneService bloodKetoneService;

    @Autowired
    private QualityControlService qualityControlService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private MemberMonitorPlanServiceI memberMonitorPlanService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private CommitteeService committeeService;

    @Autowired
    private MemberManageService memberManageService;

    /**
     * ??????
     * @author: nzq
     * @date???2020-07-28  
     */
    @RequestMapping("user/login")
    public Result login(@Validated MachineLoginDTO loginDTO){
    	UserNamePasswordToken userLoginTypeToken = new UserNamePasswordToken(loginDTO.getUserName()
                , loginDTO.getPassword(), AuthenticationType.DOCTOR_WEB);
        userLoginTypeToken.setUserType(LoginLogConstant.USER_TYPE_GLU);
        userLoginTypeToken.setHost(RequestTool.getIpAddr());
        Subject subject = SecurityUtils.getSubject();
        subject.login(userLoginTypeToken);
        subject.getSession().setTimeout(-1000L);
        DoctorSessionBO doctorPO = (DoctorSessionBO) subject.getPrincipal();
        machineInfoHandler(loginDTO);
        return Result.ok(doctorPO);
    }

    /**
     * ????????????
     * @return
     */
    @RequestMapping("user/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * ??????????????????
     * @author: nzq
     * @date???2020-07-28  
     */
    @RequestMapping("member/getBedListByPage")
    public ResultModel getBedListByPage(PageRequestModel pr, String concernStatus ,String departmentId){
    	DoctorSessionBO doctorModel = SessionTool.getWebSession();
        Map<String,Object> map = this.gluService.listBedWithGroup(pr, concernStatus, doctorModel ,departmentId);
        return new ResultModel(map);
    }
    
    /**
     * @Title getBedListByPageForRefresh ??????????????????????????????
     * @author nzq
     * @date 2020-07-28
     * @return
     */
    @RequestMapping("member/getBedListByPageForRefresh")
    public ResultModel getBedListByPageForRefresh(PageRequestModel pr, String concernStatus,GlucometerRequest gr,
                                                  String refreshTime ,String departmentId){
    	DoctorSessionBO doctorModel = SessionTool.getWebSession();
        Map<String,Object> map = this.gluService.listBedWithGroupForRefresh(pr, concernStatus, doctorModel,refreshTime ,departmentId);
        return new ResultModel(map);
    }
    
/*    *//**
     * ??????????????????????????????
     * @author nzq
     * @date 2020-07-28
     * @return
     *//*
    @RequestMapping("member/getMemberSmbgCount")
    public ResultModel getMemberSmbgCount(GlucometerRequest gr){
    	DoctorSessionBO doctorModel = SessionTool.getWebSession();
        Map<String,Object> m = this.gluService.getMemberSmbgCount(doctorModel.getDoctorId());
        return new ResultModel(m);
    }*/
    
    /**
     * ??????????????????????????????
     * @author nzq
     * @date 2020-07-28
     * @return ResultModel
     */
    @RequestMapping("member/getMemberRange")
    public ResultModel getMemberRange(String memberId){
        ValidateTool.checkParameterIsNull("memberId",memberId);
        RangeBO rangeModel = memberService.getMemberRange(memberId);
        return new ResultModel(rangeModel);
    }
    
    /**
     * ????????????
     * @author nzq
     * @date 2020-07-28
     * @return ResultModel
     */
    @RequestMapping("member/listMemberBySearch")
    public ResultModel listMemberBySearch(GlucometerRequest gr,String departmentName,String bedNo
            ,String memberName){
    	DoctorSessionBO doctorModel = SessionTool.getWebSession();
    	List<Map<String, Object>> list = this.memberService.listMemberBySearch(departmentName, bedNo, memberName, doctorModel.getDoctorId());
        return new ResultModel(list);
    }
    
    /**
     * ?????????????????????
     * @author nzq
     * @date 2020-07-28
     */
    @RequestMapping("application/getMachineVersionModel")
    public ResultModel getMachineVersionModel(GlucometerRequest gr){
        MachineVersionModel mvm = this.gluService.getMachineVersionModel(gr);
        return new ResultModel(mvm);
    }
    
    /**????????????...........................**/
    /**
     * ????????????
     * @author nzq
     * @date 2020-07-28
     */
    @RequestMapping("bloodSugar/addMemberParamLog")
    public ResultModel addMemberParamLog(MemberParamLogModel paramLog){
    	logger.info("glucometer addMemberParamLog paramLog:"+JSON.toJSONString(paramLog));
    	DoctorSessionBO doctor = SessionTool.getWebSession();
        paramLog.setDepartmentId(doctor.getDepartId());
        paramLog.setOptionUserName(doctor.getDoctorName());
        paramLog.setOptionUserId(doctor.getDoctorId());
        paramLog.setRecordOrigin(SignConstant.ORIGIN_G_DEVICE+"");

        if(StringUtils.isBlank(paramLog.getMemberId())){
            if(StringUtils.isBlank(paramLog.getIdCard())) {
                throw new BusinessException("999", "?????????????????????id????????????");
            }else {
                GetMemberDTO getMemberDTO = new GetMemberDTO();
                getMemberDTO.setIdCard(paramLog.getIdCard());
                MemberPO memberPO = memberService.getMember(getMemberDTO);
                if(memberPO == null){
                    ResultModel m = new ResultModel();
                    m.setCode("1001");
                    m.setMsg("?????????????????????????????????");
                    return m;
                }else {
                    paramLog.setMemberId(memberPO.getMemberId());
                }
            }
        }
        ResultModel resultModel = this.gluService.insertMemberBloodSugarLogWithLock(paramLog);
        return resultModel;
    }
    /**
     * ??????????????????
     * @author nzq
     * @date 2020-07-28
     */
    @RequestMapping("bloodSugar/getMemberParamLog")
    public ResultModel getMemberParamLog(ListBloodSugarDTO po){
        ValidateTool.checkParameterIsNull("memberId", po.getMemberId());
        Map<String,Object> m = this.gluService.listMemberParamLog(po);
        return new ResultModel(m);
    }
    
    /**
     * ??????????????????
     * @author nzq
     * @date 2020-07-28
     */
    @RequestMapping("bloodSugar/refreshParamLogList")
    public ResultModel refreshParamLogList(ListBloodSugarDTO po){
        Map<String,Object> m = this.gluService.refreshParamLogList(po);
        return new ResultModel(m);
    }

    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("/bloodSugar/getBloodSugarParamSetting")
    public Result getBloodSugarParamSetting(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        List<BloodSugarParamSettingVO> result = this.bloodSugarService.listBloodSugarParamSetting(doctorSession.getHospitalId());
        return Result.ok(result);
    }


    /**
     * ???????????????????????????
     * @author suyz
     * @date 2021-01-20
     */
    @RequestMapping("/doctor/listDoctorManageDepartments")
    public Result listDoctorManageDepartments(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        List<DepartmentPO> departmentList = this.departmentService.listDoctorManageDepartment(doctorSession.getDoctorId());
        List<DepartmentPO> result = null;
        if(departmentList != null && !departmentList.isEmpty()){
            result = departmentList.stream().filter(x -> 1 == x.getIsVirtual()).collect(Collectors.toList());
        }
        return Result.ok(result);
    }


    /**
     * ????????????
     * @param memberBloodSugarDTO
     * @param page
     * @return
     */
    @RequestMapping("/bloodSugar/listBloodSugarHistory")
    public Result listBloodSugarHistory(MemberBloodSugarDTO memberBloodSugarDTO, PageRequest page){
        ValidateTool.checkParameterIsNull("startDt" ,memberBloodSugarDTO.getStartDt());
        ValidateTool.checkParameterIsNull("endDt" ,memberBloodSugarDTO.getEndDt());
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        memberBloodSugarDTO.setDoctorId(doctorSessionBO.getDoctorId());
        memberBloodSugarDTO.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<MemberBloodSugarVO> result = this.bloodSugarServiceNew.listAllBloodSugarOfMember(memberBloodSugarDTO, page);
        return Result.ok(result);
    }

    /**
     * ?????????????????????????????????????????????,?????????????????????
     * @return
     */
    @RequestMapping("/bloodKetone/addBloodKetone")
    public Result addBloodKetone(AddBloodKetoneServiceDTO dto){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        ValidateTool.checkParamIsNull(dto.getParamValue(), "paramValue");
        ValidateTool.checkParamIsNull(dto.getRecordDt(), "recordDt");

        dto.setOperatorType(2);
        dto.setOperatorId(doctorSession.getDoctorId());
        dto.setOrigin(SignConstant.ORIGIN_G_DEVICE);
        String sid = bloodKetoneService.addBloodKetone(dto);
        return Result.ok(sid);
    }

    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("/bloodKetone/listBloodKetone")
    public Result listBloodKetone(ListBloodKetoneDTO listBloodKetoneDTO, PageRequest pr){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(listBloodKetoneDTO.getMemberId(), "memberId");
        List<BloodKetonePO> list =  bloodKetoneService.bloodKetoneList(listBloodKetoneDTO);
        return Result.ok(list);
    }

    /**
     * ??????????????????
     * @param pr
     * @return
     */
    @RequestMapping("/qualityControl/listTestPaper")
    public Result listTestPaper(PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        PageResult result = this.qualityControlService.listTestPaperValid(pr ,doctorSessionBO.getHospitalId());
        return Result.ok(result);
    }


    /**
     * ?????????????????????
     * @param pr
     * @return
     */
    @RequestMapping("/qualityControl/listLiquid")
    public Result listLiquid(PageRequest pr){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        PageResult result = this.qualityControlService.listLiquidValid(pr ,doctor.getHospitalId());
        return Result.ok(result);
    }

    /**
     * ??????????????????
     * @param param
     * @return
     */
    @RequestMapping("/qualityControl/addQualityControl")
    public Result addQualityControl(@Validated AddQualityControlParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setHospitalId(doctor.getHospitalId());
        param.setOperatorId(doctor.getDoctorId());
        int qualityControlResult = this.qualityControlService.addQualityControl(param);
        MachinePO machine = this.machineService.getMachine(null ,param.getMachineSn() ,param.getMachineType());
        int lockStatus = 0;
        if(machine != null){
            lockStatus = machine.getMachineStatus();
        }
        Map<String ,Integer> result = new HashMap<>();
        result.put("result" ,qualityControlResult);
        result.put("lockStatus" ,lockStatus);
        return Result.ok(result);
    }

    /**
     * ????????????????????????
     * @param param
     * @param pr
     * @return
     */
    @RequestMapping("/qualityControl/listQualityControl")
    public Result listQualityControl(PageRequest pr ,ListQualityControlParam param){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        param.setHospitalId(doctor.getHospitalId());
        PageResult result = this.qualityControlService.listQualityControl(pr ,param);
        return Result.ok(result);
    }

    /**
     * ????????????????????????
     * @param param
     * @return
     */
    @RequestMapping("/qualityControl/getMachineLockStatus")
    public Result getMachineLockStatus(String machineSn ,Integer machineType){
        ValidateTool.checkParameterIsNull("machineSn" ,machineSn);
        ValidateTool.checkParameterIsNull("machineType" ,machineType);
        MachinePO machine = this.machineService.getMachine(null ,machineSn ,machineType);
        if(machine == null){
            throw new BusinessException("??????????????????????????????");
        }
        return Result.ok(machine.getMachineStatus());
    }

    /**
     * ????????????????????????
     * @param param
     * @return
     */
    @RequestMapping("/bloodSugar/listTodayTask")
    public Result listTodayTask(String paramCode ,String departmentId){
        DoctorSessionBO doctor = SessionTool.getWebSession();
        Map result = this.memberMonitorPlanService.listTodayTask(doctor.getDoctorId() ,paramCode ,departmentId);
        return Result.ok(result);
    }


    /**
     * ???????????????????????????
     * @return
     */
    @RequestMapping("/doctor/listDoctorDepartments")
    public Result listDoctorDepartments(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        List<DoctorDepartmentVO> doctorDepartVOList = this.doctorService.listDoctorDepartment(doctorSessionBO.getDoctorId());
        return new Result(doctorDepartVOList);
    }

    /**
     * ??????????????????
     * @param loginDTO
     * @return
     */
    private String machineInfoHandler(MachineLoginDTO loginDTO){
        if(StringUtils.isBlank(loginDTO.getMachineSn()) || loginDTO.getMachineType() == null){
            return null;
        }
        return  this.machineService.addMachine(loginDTO.getMachineSn() ,loginDTO.getMachineType());
    }

    /**
     * ??????????????????
     * @return
     */
    @RequestMapping("/member/listGluMember")
    public Result listGluMember(ListMemberDto dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        if( dto.getLastTime()!= null){
            Date date = new Date(dto.getLastTime());
            dto.setBegin(DateHelper.getDate(date, DateHelper.DATETIME_FORMAT));
        }
        return Result.ok(gluService.listGluMember(dto));
    }

    @RequestMapping("/member/getMemberByKeyword")
    public Result getMemberByKeyword(ListMemberDto dto){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        dto.setDoctorId(webSession.getDoctorId());
        return  Result.ok(gluService.listGluMemberByKeyword(dto));
    }


    /**
     * ????????????
     * @param addMemberDTO
     * @return
     */
    @RequestMapping("/member/addMember")
    public Result addMemberDoctorRelate(AddMemberDTO addMemberDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(addMemberDTO.getMobilePhone(), "mobilePhone");
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMobilePhone(addMemberDTO.getMobilePhone());
        MemberPO memberPO = memberService.getMember(getMemberDTO);
        String memberId;
        if(memberPO == null){
            MemberLock.ADD_MEMBER_LOCK.lock();
            try{
                ValidateTool.checkParamIsNull(addMemberDTO.getMemberName(), "memberName");
                ValidateTool.checkParamIsNull(addMemberDTO.getBirthday(), "birthday");
                ValidateTool.checkParamIsNull(addMemberDTO.getSex(), "sex");
                memberId = this.memberService.addMember(addMemberDTO, doctorSessionBO);
            }finally {
                MemberLock.ADD_MEMBER_LOCK.unlock();
            }
        }else {
            memberId = memberPO.getMemberId();
        }
        AddMemberDoctorRelateDTO dto = new AddMemberDoctorRelateDTO();
        dto.setMemberId(memberId);
        dto.setDoctorId(doctorSessionBO.getDoctorId());
        dto.setOperatorId(doctorSessionBO.getDoctorId());
        dto.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_GLU); //???????????????????????? glu
        dto.setGroupId("0");//?????????????????????
        MemberLock.DOCTOR_MEMBER_RELATION_LOCK.lock();
        Result result = new Result();
        try{
            if(this.memberService.addMemberDoctorRelate(dto)){
                result.setCode("0");
                result.setSuccess(true);
                result.setMessage("????????????");
                result.setObj(memberId);
            } else {
                result.setCode("1002");
                result.setSuccess(false);
                result.setMessage("?????????????????????????????????????????????");
                result.setObj(null);
            }
        }finally {
            MemberLock.DOCTOR_MEMBER_RELATION_LOCK.unlock();
        }
        return result;
    }


    /**
     * ????????????
     * @param memberId
     * @return
     */
    @RequestMapping("/member/removeMember")
    public Result removeMember(String memberId){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(memberId, "memberId");
        this.memberService.cancelRelation(memberId, webSession.getDoctorId());
        Result result =  new Result();
        result.setMessage("????????????");
        result.setCode("0");
        return result;
    }


    /**
     *
     * @param dto
     * @return
     */
    @RequestMapping("/member/updateMember")
    public Result updateMember(UpdateMemberDTO dto){
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        memberService.updateMember(dto);
        Result result =  new Result();
        result.setMessage("????????????");
        result.setCode("0");
        return result;
    }

    /**
     * ?????????????????????????????????
     * @param mobilePhone
     * @return
     */
    @RequestMapping("/member/getMemberByMobilePhone")
    public Result getMemberByMobilePhone(String mobilePhone){
        ValidateTool.checkParamIsNull(mobilePhone, "mobilePhone");
        PhoneUtil.isPhone(mobilePhone);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        GetMemberDTO dto = new GetMemberDTO();
        dto.setKeyword(mobilePhone);
        MemberPO member = memberService.getMember(dto);
        JSONObject obj = new JSONObject();
        ListMemberVO vo = new ListMemberVO();
        if(member != null) {
            BeanUtils.copyProperties(vo, member);
        }
        obj.put("member", vo);
        obj.put("registerFlag", member != null);
        boolean canRegister = false;
        String msg = null;
        if(member != null) {
            boolean exists = memberService.checkDoctorMemberRelationExists(member.getMemberId(), webSession.getDoctorId());
            obj.put("exists", exists);
            if(!exists) {
                try {
                    AddMemberDoctorRelateDTO addDto = new AddMemberDoctorRelateDTO();
                    addDto.setMemberId(member.getMemberId());
                    addDto.setDoctorId(webSession.getDoctorId());
                    addDto.setRelationWay(MemberDoctorConstant.MEMBER_DOCTOR_RELATION_GLU); //???????????????????????? glu
                    memberService.checkedMemberDoctorRelate(addDto);
                } catch (Exception ignored) {
                    canRegister = true;
                    msg = ignored.getMessage();
                }
            }
        }else {
            obj.put("exists", false);
        }
        obj.put("canRegister", canRegister);
        Result result = new Result(obj);
        result.setMessage(msg);
        return result;
    }


    /*------------------??????????????????----------------*/
    /**
     * ??????????????????
     * @param pr
     * @param gr
     * @return
     */
    @RequestMapping("/member/getManageListByPage")
    public Result getManageListByPage(PageRequest pr){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        Map<String, Object> map = gluService.listMemberJoinHospital(doctorSessionBO.getHospitalId(), pr);
        return Result.ok(map);
    }

    /**
     * ??????????????????(??????)
     * @param pr
     * @param gr
     * @param refreshTime
     * @return
     */
    @RequestMapping("/member/getManageListByPageForRefresh")
    public Result getManageListByPageForRefresh(PageRequest pr,String refreshTime){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        Map<String, Object> map = gluService.listMemberJoinHospitalForFresh(doctorSessionBO.getHospitalId(), pr, refreshTime);
        return Result.ok(map);
    }


    /**
     * ????????????
     *
     * @param
     * @return
     */
    @RequestMapping("/member/addManageMember")
    public Result addManageMember(@Validated AddGluMemberDTO addGluMemberDTO) {
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddMemberDTO addMemberDTO = new AddMemberDTO();
        BeanUtils.copyProperties(addMemberDTO,addGluMemberDTO);
        addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_GLU);
        String diabetesType = addGluMemberDTO.getDiabetesType();
        addMemberDTO.setIsDiabetes("0");
        addMemberDTO.setDiabetesType(null);
        if (!diabetesType.equals("0")){
            addMemberDTO.setIsDiabetes("1");
            addMemberDTO.setDiabetesType(addGluMemberDTO.getDiabetesType());
        }
        String memberId = memberManageService.createMemberHospitalRelation(doctorSessionBO, addMemberDTO);
        return Result.ok(memberId);
    }


    /**
     * ?????????????????????????????????
     *
     * @param idCard
     * @return
     */
    @RequestMapping("/member/getMemberByIdCard")
    public Result getMemberByIdCard(String idCard) {
        ValidateTool.checkParamIsNull(idCard, "idCard");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(idCard);
        getMemberDTO.setHospitalId(doctorSessionBO.getHospitalId());
        MemberPO memberPO = this.memberService.getMember(getMemberDTO);
        return new Result(memberPO);
    }


    /**
     * ?????????????????????????????????????????????
     * @param keyword
     * @return
     */
    @RequestMapping("/member/getMangeMemberByKeyword")
    public Result getMangeMemberByKeyword(String keyword){
        ValidateTool.checkParameterIsNull("keyword",keyword);
        DoctorSessionBO webSession = SessionTool.getWebSession();
        List<ListMemberVO> listMemberVOS = gluService.listGluManageMemberByKeyword(webSession.getHospitalId(), keyword);
        return  Result.ok(listMemberVOS);
    }

    /**
     * ?????????????????????????????????
     */
    @RequestMapping("/listDoctorCommittee")
    public Result listDoctorCommittee(){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        List<HospitalCommitteePO> hospitalCommitteePOS = this.committeeService.listCommitteeByDoctorId(webSession.getDoctorId());
        return Result.ok(hospitalCommitteePOS);
    }



    /**
     * ???????????????????????????
     */
    @RequestMapping("/application/getSrfDownLoadAddress")
    public ResultModel getSrfDownLoadAddress(GlucometerRequest gr){
        String srfDownLoadAddress = this.gluService.getSrfDownLoadAddress(gr);
        ResultModel resultModel = new ResultModel();
        resultModel.setObj(srfDownLoadAddress);
        return resultModel;
    }




}
