package com.comvee.cdms.complication.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.complication.model.dto.*;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import com.comvee.cdms.complication.model.po.ScreeningItemPO;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.model.po.ScreeningReportPO;
import com.comvee.cdms.complication.model.vo.ScreeningListVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatFinishVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatOrderVO;
import com.comvee.cdms.complication.model.vo.ScreeningStatVO;
import com.comvee.cdms.complication.service.ScreeningItemService;
import com.comvee.cdms.complication.service.ScreeningMachineService;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.tool.ScreeningExport;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.comvee.cdms.checkresult.constant.CheckConstant.INSPECTION_TYPE_CODE;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
@RestController
@RequestMapping("/web/screening")
public class WebScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private ScreeningMachineService screeningMachineService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScreeningItemService screeningItemService;

    /**
     * ?????????????????????
     * @param pr
     * @param listScreeningParam
     * @return
     */
    @RequestMapping("/listScreening")
    public Result listScreening(PageRequest pr, ListScreeningDTO listScreeningParam){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        listScreeningParam.setHospitalId(doctorSessionBO.getHospitalId());
        PageResult<ScreeningListPO> pageResult = this.screeningService.listScreening(pr ,listScreeningParam);
        return Result.ok(pageResult);
    }


    /**
     * ??????????????????
     * @return
     */
    @RequestMapping("/statScreening")
    public Result statScreening(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ScreeningStatVO screeningStatVO = this.screeningService.statScreening(doctorSessionBO);
        return Result.ok(screeningStatVO);
    }

    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("/statOrderScreening")
    public Result statOrderScreening(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ScreeningStatOrderVO statOrderVO = this.screeningService.statOrderScreening(doctorSessionBO);
        return Result.ok(statOrderVO);
    }

    /**
     * ???????????????????????????
     * @return
     */
    @RequestMapping("/statFinishScreening")
    public Result statFinishScreening(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ScreeningStatFinishVO screeningStatFinishVO = this.screeningService.statFinishScreening(doctorSessionBO);
        return Result.ok(screeningStatFinishVO);
    }

    /**
     * ??????id???????????????
     * @return
     */
    @RequestMapping("/getScreeningById")
    public Result getScreeningById(String screeningId,Boolean hide){
        ValidateTool.checkParamIsNull(screeningId, "screeningId");
        ScreeningListVO screeningVO = this.screeningService.getScreeningVOById(screeningId);
        if (null != screeningVO){
            if (null == hide || hide){
                //????????????
                screeningVO.setIdCard(ValidateTool.tuoMin(screeningVO.getIdCard(),4,4,"*"));
                screeningVO.setMobilePhone(ValidateTool.tuoMin(screeningVO.getMobilePhone(),3,4,"*"));
            }
        }
        return Result.ok(screeningVO);
    }

    /**
     * ????????????
     * @param screeningId
     * @param screeningType
     * @return
     */
    @RequestMapping("/getScreeningReport")
    public Result getScreeningReport(String screeningId ,Integer screeningType){
        ValidateTool.checkParamIsNull(screeningId , "screeningId");
        ValidateTool.checkParamIsNull(screeningType , "screeningType");
        ScreeningReportPO screeningReportPO = this.screeningService.getScreeningReport(screeningId ,screeningType);
        return Result.ok(screeningReportPO);
    }

    /**
     * ????????????????????????
     * @param pr
     * @param listScreeningDataParam
     * @return
     */
    @RequestMapping("/listScreeningData")
    public Result listScreeningData(PageRequest pr ,ListScreeningDataDTO listScreeningDataParam){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        doctorIdListHandler(doctorSessionBO.getDoctorId() ,listScreeningDataParam);
        PageResult pageResult = this.screeningService.listScreeningData(pr ,listScreeningDataParam);
        return Result.ok(pageResult);
    }

    /**
     * ?????????????????????id??????
     * @param doctorId
     * @param listScreeningDataParam
     */
    private void doctorIdListHandler(String doctorId ,ListScreeningDataDTO listScreeningDataParam){
        List<String> doctorList = this.doctorService.listMyDoctor(doctorId).stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
        listScreeningDataParam.setDoctorIdList(doctorList);
    }

    /**
     * ??????excel
     * @param pr
     * @param listScreeningDataParam
     * @return
     */
    @RequestMapping("/exportListScreeningData")
    public ResponseEntity<byte[]> exportListScreeningData(PageRequest pr , ListScreeningDataDTO listScreeningDataParam){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        doctorIdListHandler(doctorSessionBO.getDoctorId() ,listScreeningDataParam);
        PageResult<ScreeningDataPO> pageResult = this.screeningService.listScreeningData(pr ,listScreeningDataParam);
        byte[] bytes = ScreeningExport.of(pageResult.getRows()).export();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attchement;filename=" + getExportFileName());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes ,httpHeaders , HttpStatus.OK);
        return responseEntity;
    }

    /**
     * ?????????????????????
     * @return
     */
    private String getExportFileName(){
        try {
            return URLEncoder.encode("?????????????????????.xls" ,"utf-8") ;
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    /**
     * ????????????
     * @param screeningId
     * @return
     */
    @RequestMapping("/deleteScreening")
    public Result deleteScreening(String screeningId){
        ValidateTool.checkParamIsNull(screeningId, "screeningId");
        this.screeningService.deleteScreening(screeningId);
        return Result.ok();
    }

    /**
     * ???????????????????????????
     * @param idCard
     * @return
     */
    @RequestMapping("/listPatientScreeningReport")
    public Result listPatientScreeningReport(String memberId){
        ValidateTool.checkParamIsNull(memberId, "memberId");
        JSONObject jsonObject = this.screeningService.listPatientScreeningReport(memberId ,null);
        return Result.ok(jsonObject);
    }


    /**
     * ????????????
     * @param updateScreeningReportParam
     * @return
     */
    @RequestMapping("updateReport")
    public Result updateReport(UpdateScreeningReportParam updateScreeningReportParam){
        ValidateTool.checkParamIsNull(updateScreeningReportParam.getScreeningId() , "screeningId");
        ValidateTool.checkParamIsNull(updateScreeningReportParam.getScreeningType() , "screeningType");
        this.screeningService.updateScreeningReport(updateScreeningReportParam);
        return Result.ok();
    }

    /**
     * ???????????????????????????
     * @param idCard
     * @param screeningType
     * @param secretKey
     * @return
     */
    @RequestMapping("getLastScreeningReport")
    public Result getLastScreeningReport(String memberId ,Integer screeningType){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        ValidateTool.checkParamIsNull(screeningType ,"screeningType");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        ScreeningReportPO screeningReportPO = this.screeningService.getLastScreeningReport(memberId ,screeningType
                ,doctorSessionBO.getDoctorId());
        return Result.ok(screeningReportPO);
    }

    /**
     * @api {post}/web/screening/listScreeningReportNearlySixMonths.do ???????????????6?????????????????????????????????????????????-??????
     * @author ?????????
     * @time 2018/07/23
     * @apiName listScreeningReportNearlySixMonths ???????????????6?????????????????????????????????????????????
     * @apiGroup web-screening
     * @apiVersion 4.0.0
     * @apiParam {String} memberId ???????????? (??????)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/screening/listScreeningReportNearlySixMonths.do
     *
     * @apiSuccess {Object} data.obj ????????????
     * @apiSuccess {Object} data.msg ????????????
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code ???????????? 0??????
     */
    @Deprecated
    @RequestMapping("listScreeningReportNearlySixMonths")
    public Result listScreeningReportNearlySixMonths(String memberId,String hospitalId){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        Map<String,Object> resultMap = this.screeningService.listScreeningReportNearlySixMonths(memberId,null);
        return new Result(resultMap);
    }

    /**
     * @api {post}/web/screening/addScreening.do ????????????
     * @author ?????????
     * @time 2018/08/22
     * @apiName addScreening ????????????
     * @apiGroup web-screening
     * @apiVersion 4.0.0
     * @apiParam {String} memberId ???????????? (??????)
     * @apiParam {String} modules ??????????????? (??????????????????","??????)
     * @apiSampleRequest  http://192.168.7.25:9080/intelligent-prescription/web/screening/addScreening.do
     *
     * @apiSuccess {Object} data.obj ????????????
     * @apiSuccess {Object} data.msg ????????????
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code ???????????? 0??????
     */
    @RequestMapping("/addScreening")
    public Result addScreening(AddScreeningListDTO addScreeningListDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        MemberPO memberPO = this.memberService.getMemberById(addScreeningListDTO.getMemberId());
        if(memberPO == null){
            throw new BusinessException("???????????????????????????");
        }
//        ValidateTool.checkParamIsNull(addScreeningListDTO.getHospitalId(), "hospitalId");
        /**??????**/
        addScreeningListDTO.setScreeningId(DaoHelper.getSeq());
        addScreeningListDTO.setPatientName(memberPO.getMemberName());
        addScreeningListDTO.setIdCard(memberPO.getIdCard());
        addScreeningListDTO.setSex(memberPO.getSex());
        addScreeningListDTO.setApplyDt(DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT));
        addScreeningListDTO.setScreeningStatus(ScreeningConstant.SCREENING_STATUS_NOT_START);
        addScreeningListDTO.setOrderStatus(ScreeningConstant.ORDER_STATUS_NO);
        addScreeningListDTO.setOrderOrigin(ScreeningConstant.ORDER_ORIGIN_OTHER);
        //?????????
        addScreeningListDTO.setMachineSn("-1");
        String[] modules = addScreeningListDTO.getModules().split(",");
        Map<String,Integer> map = new HashMap<>(modules.length);
        for(String module : modules){
            map.put(module,ScreeningConstant.PRE_DEAL_STATUS_NO);
        }
        addScreeningListDTO.setModulesStatus(JSONObject.toJSONString(map));
        addScreeningListDTO.setDoctorId(doctorSessionBO.getDoctorId());
        /**??????end**/
        /**??????**/
        addScreeningListDTO.setBirthday(memberPO.getBirthday());
        addScreeningListDTO.setDiabetesType(memberPO.getDiabetesType());
        addScreeningListDTO.setMobilePhone(memberPO.getMobilePhone());
        addScreeningListDTO.setHospitalId(doctorSessionBO.getHospitalId());
        /**??????end**/
        this.screeningService.addScreeningList(addScreeningListDTO);
        return Result.ok();
    }

    /**
     * ????????????
     * @param screeningId
     * @return
     */
    @RequestMapping("startScreening")
    public Result startScreening(String screeningId){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        this.screeningService.startScreening(screeningId,doctorSessionBO.getDoctorId());
        return Result.ok();
    }

    /**
     * ????????????
     * @param screeningId
     * @param orderDate
     * @param orderTime
     * @param orderTimeCode
     * @return
     */
    @RequestMapping("orderScreening")
    public Result orderScreening(String screeningId ,String orderDate
            ,String orderTime ,Integer orderTimeCode ,String mobilePhone){
        this.screeningService.orderScreening(screeningId ,orderDate ,orderTime ,orderTimeCode ,mobilePhone);
        return Result.ok();
    }

    /**
     * ??????????????? (????????????)
     * @param screeningId ????????????
     * @param screeningType ??????????????????
     * @param stopReason ????????????
     * @param operatorName ???????????????
     * @return
     */
    @RequestMapping("cancelScreeningItem")
    public Result cancelScreeningItem(String screeningId ,Integer screeningType ,String stopReason ,String operatorName){
        ValidateTool.checkParamIsNull(screeningId , "screeningId");
        ValidateTool.checkParamIsNull(screeningType , "screeningType");
        ValidateTool.checkParamIsNull(stopReason , "stopReason");
        ValidateTool.checkParamIsNull(operatorName , "operatorName");
        this.screeningService.cancelScreeningItem(screeningId ,screeningType ,stopReason ,operatorName);
        return Result.ok();
    }

    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("listHospitalScreeningItem")
    public Result listHospitalScreeningItem(){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        List list = this.screeningItemService.listHospitalScreeningItemDict(doctorSession.getHospitalId());
        return Result.ok(list);
    }


    /**
     * ?????????????????????
     * @param screeningId
     * @param screeningType
     * @return
     */
    @RequestMapping("/getScreeningPre")
    public Result getScreeningPre(String screeningId ,Integer screeningType){
        ScreeningItemPO prePO = this.screeningService.getScreeningPre(null ,screeningId ,screeningType);
        return Result.ok(prePO);
    }


    /**
     * ????????????
     * @return
     */
    @RequestMapping("/eyesReadingFilm")
    public Result eyesReadingFilm(EyesReadingFilmParam eyesReadingFilmParam){
        ValidateTool.checkParamIsNull(eyesReadingFilmParam.getReportId() , "reportId");
        DoctorSessionBO doctor = SessionTool.getWebSession();
        eyesReadingFilmParam.setDoctorId(doctor.getDoctorId());
        this.screeningService.eyesReadingFilm(eyesReadingFilmParam);
        return Result.ok();
    }


    /**
     * ????????????ACR??????
     * @param dto
     * @return
     */
    @RequestMapping("inputACRReport")
    public Result inputACRReport(ACRDataInputDto dto){
        ValidateTool.checkParamIsNull(dto.getScreeningId() , "screeningId");
        ValidateTool.checkParamIsNull(dto.getAcr() , "acr");
        ValidateTool.checkParamIsNull(dto.getAlb() , "alb");
        ValidateTool.checkParamIsNull(dto.getCreat() , "create");
        screeningService.inputACRReport(dto);
        return Result.ok();
    }
    /**
     * ????????????Hba1c??????
     * @param dto
     * @return
     */
    @RequestMapping("inputHba1cReport")
    public Result inputHba1cReport(HbA1cDataInputDto dto){
        ValidateTool.checkParamIsNull(dto.getScreeningId() , "screeningId");
        ValidateTool.checkParamIsNull(dto.getHbA1c() , "HbA1c");
        screeningService.inputHba1cReport(dto);
        return Result.ok();
    }

    /**
     * ??????????????????
     */
    @RequestMapping("modifyScreenList")
    public Result ignoreScreen(ScreeningListPO screeningListPO){
        ValidateTool.checkParamIsNull(screeningListPO.getScreeningId() , "screeningId");
        screeningService.updateScreeningList(screeningListPO);
        return Result.ok();
    }

    /**
     * ??????????????????????????????????????????????????????
     * @param dto
     * @return
     */
    @RequestMapping("listMemberScreeningReport")
    public Result listMemberScreeningReport(@Validated ListMemberScreeningReportDTO dto ,PageRequest pr){
        PageResult<ScreeningReportPO> result = this.screeningService.listMemberScreeningReport(dto ,pr);
        return Result.ok(result);
    }

    @RequestMapping("confirmScreeningItem")
    public Result confirmScreening(String screeningId, int screeningType){
        ValidateTool.checkParamIsNull(screeningId, "screeningId");
        ValidateTool.checkParamIsNull(screeningType, "screeningType");
        if(!INSPECTION_TYPE_CODE.containsKey(screeningType)){
            throw new BusinessException("??????????????????");
        }
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        screeningService.confirmScreening(screeningId, screeningType ,doctorSession);
        return Result.ok();
    }
}
