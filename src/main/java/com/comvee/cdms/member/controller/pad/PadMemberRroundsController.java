package com.comvee.cdms.member.controller.pad;

import com.comvee.cdms.checkresult.dto.GetMemberCheckoutDTO;
import com.comvee.cdms.checkresult.po.CheckoutDetailPO;
import com.comvee.cdms.checkresult.service.CheckoutDetailServiceI;
import com.comvee.cdms.checkresult.service.InspectionDetailServiceI;
import com.comvee.cdms.checkresult.vo.MemberInspectionVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.MD5Util;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.dto.ListSelectedKeyNoteDTO;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import com.comvee.cdms.level.bo.MemberCurrentLeverBO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.mapper.MemberInHospitalLogMapper;
import com.comvee.cdms.member.po.MemberInHospitalLogPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.MemberInspectVO;
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
 * @Author linr
 * @Date 2022/3/3
 */
@RestController
@RequestMapping("/pad/rounds")
public class PadMemberRroundsController {

    @Autowired
    @Qualifier("checkoutDetailService")
    private CheckoutDetailServiceI checkoutDetailService;


    @Autowired
    @Qualifier("inspectionDetailService")
    private InspectionDetailServiceI inspectionDetailService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberLevelService memberLevelService;


    @Autowired
    MemberInHospitalLogMapper memberInHospitalLogMapper;


    private final String MEMBER_STATUS_IN_HOSPITAL = "1";//在住院
    private final String MEMBER_STATUS_NOT_IN_HOSPITAL = "0";//不在住院

    /**
     * 个人档案
     * @param memberId
     * @param doctorId
     * @param hospitalId
     * @param hide
     * @return
     */
    @RequestMapping("getMemberArchivesByMemberId")
    public Result getMemberArchivesByMemberId(String memberId, String doctorId, String hospitalId, Boolean hide) {
        ValidateTool.checkParamIsNull(memberId, "memberId");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        if (StringUtils.isBlank(doctorId)) {
            doctorId = doctorSessionBO.getDoctorId();
        }
        //患者档案信息
        Map<String, Object> reMap = this.memberService.getMemberArchivesByMemberId(memberId, doctorId, hospitalId, hide);
        //新分层分级（政和）
        MemberCurrentLeverBO memberCurrentLevel = memberLevelService.getMemberCurrentLevel(memberId);
        reMap.put("level",memberCurrentLevel);
        return new Result(reMap);
    }


    /**
     * 实验室检查(特别关注)
     * @param dto
     * @param pager
     * @return
     */
    @RequestMapping("listMemberLabCheckedItem")
    public Result pagerCheckoutDetailGroupByNameOfDoctor(@Validated GetMemberCheckoutDTO dto){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        dto.setHospitalId(doctorSessionBO.getHospitalId());

        Map<String, String> map = loadMemberInHospitalLogDate(dto.getMemberId(), doctorSessionBO.getHospitalId());
        String status = map.get("status");
        String startDt = map.get("startDt");
        String endDt = map.get("endDt");
        if (status.equals(MEMBER_STATUS_NOT_IN_HOSPITAL)){
            throw new BusinessException("患者未处于住院期间");
        }
        Map<String, Object> checkoutList = this.checkoutDetailService.loadMemberDefaultCheckoutList(dto);
        return new Result(checkoutList);
    }


    /**
     * 辅助检查
     * @param memberId
     * @param hospitalId
     * @return
     */
    @RequestMapping("listMemberSupportCheckedItem")
    public Result listInspectionReportNearlySixMonths(String memberId,String hospitalId){
        ValidateTool.checkParamIsNull(memberId ,"memberId");
        ValidateTool.checkParamIsNull(hospitalId ,"hospitalId");
        Map<String, String> map = loadMemberInHospitalLogDate(memberId, hospitalId);
        String status = map.get("status");

        if (status.equals(MEMBER_STATUS_NOT_IN_HOSPITAL)){
            throw new BusinessException("患者不处于住院期间");
        }
        String startDt = map.get("startDt");
        List<MemberInspectionVO> vos = this.inspectionDetailService.listInspectionReportNearlySixMonths(memberId,hospitalId,startDt);
        return new Result(vos);
    }

    /**
     * 检查列表（含动态血压）
     * @param memberId
     * @param pager
     * @return
     */
    @RequestMapping("pagerMemberInspect")
    public Result pagerMemberInspect(String memberId) {
        List<MemberInspectVO> memberInspectVOS = this.memberService.pagerMemberInspect2(memberId);
        return new Result(memberInspectVOS);
    }



    //获取患者入院出院时间
    private Map<String,String> loadMemberInHospitalLogDate(String memberId,String hospitalId){
        String inHospital = MEMBER_STATUS_NOT_IN_HOSPITAL;
        String nowDate = DateHelper.getNowDate();
        String inHospitalDate = "";
        String outHospitalDate = "";
        Map<String, String> map = new HashMap<>();
        List<MemberInHospitalLogPO> hospitalLogList = memberInHospitalLogMapper.getMemberInHospitalLogByMemberId(memberId, hospitalId, null);
        if (!hospitalLogList.isEmpty()){
            MemberInHospitalLogPO logPO = hospitalLogList.get(0);
            inHospitalDate = logPO.getInHospitalDate();
            outHospitalDate = logPO.getOutHospitalDate();
            //没有出院日期
            if (StringUtils.isBlank(outHospitalDate) && !StringUtils.isBlank(inHospitalDate)
            && DateHelper.dateAfter(nowDate,DateHelper.DATETIME_FORMAT,inHospitalDate,DateHelper.DATETIME_FORMAT)){
                inHospital = MEMBER_STATUS_IN_HOSPITAL;
                map.put("startDt",inHospitalDate);
            }
            if (!StringUtils.isBlank(outHospitalDate) && !StringUtils.isBlank(inHospitalDate)
                    && DateHelper.isEffectiveDateStr(nowDate,inHospitalDate,outHospitalDate)){
                inHospital = MEMBER_STATUS_IN_HOSPITAL;
                map.put("startDt",inHospitalDate);
                map.put("endDt",outHospitalDate);
            }
        }
        map.put("status",inHospital);
        return map;
    }

}
