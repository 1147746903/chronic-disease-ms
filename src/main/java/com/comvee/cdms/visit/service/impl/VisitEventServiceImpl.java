package com.comvee.cdms.visit.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.MessageTool;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.constant.ScreeningConstant;
import com.comvee.cdms.department.mapper.DepartmentMapper;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.hospital.mapper.HospitalMapper;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.visit.constant.VisitEventEnum;
import com.comvee.cdms.visit.dto.AddVistiEventDTO;
import com.comvee.cdms.visit.dto.GetListVisitEventDTO;
import com.comvee.cdms.visit.mapper.VisitEventMapper;
import com.comvee.cdms.visit.po.VisitEventPO;
import com.comvee.cdms.visit.service.VisitEventService;
import com.comvee.cdms.visit.vo.ListVisitEventVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author linr
 * @Date 2022/2/25
 */
@Service
public class VisitEventServiceImpl implements VisitEventService {

    @Autowired
    private VisitEventMapper visitEventMapper;

    @Autowired
    private MemberMapper memberMapper;

    private final static String DEFAULT_FOREIGN_ID = "-1";
    private final static String DEFAULT_BLANK_OPERATOR_NAME = "----";

    @Override
    public void addVisitEvent(AddVistiEventDTO addDTO) {

        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setMemberId(addDTO.getMemberId());
        MemberPO member = memberMapper.getMember(getMemberDTO);
        if (null == member){
           throw new BusinessException("患者不存在");
        }
        String memberName = member.getMemberName();
        VisitEventPO visitEventPO = new VisitEventPO();
        BeanUtils.copyProperties(visitEventPO,addDTO);
        visitEventPO.setSid(DaoHelper.getSeq());
        visitEventPO.setEventDesc(VisitEventEnum.getDesc(visitEventPO.getEventType()));
        if (StringUtils.isBlank(addDTO.getOperatorId())) {
            visitEventPO.setOperatorId(DEFAULT_FOREIGN_ID);
            visitEventPO.setOperatorName(DEFAULT_BLANK_OPERATOR_NAME);
        }
        visitEventPO.setForeignId(StringUtils.isBlank(addDTO.getForeignId())?DEFAULT_FOREIGN_ID:addDTO.getForeignId());
        //处理detail
        eventDetailHandler(visitEventPO,addDTO,memberName);
        visitEventMapper.insert(visitEventPO);
    }


    private void eventDetailHandler(VisitEventPO visitEventPO,AddVistiEventDTO addDTO,String memberName){
        String eventDetail = "";
        Integer eventType = addDTO.getEventType();
        String hospitalName = addDTO.getHospitalName();
        String departmentName = addDTO.getDepartmentName();
        //患者建档
        if (eventType == VisitEventEnum.MEMBER_JOIN_HOSPITAL.getEventType()) {
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_JOIN_HOSPITAL.getEventDetail(),
                    memberName, hospitalName);
        }
        //定期检查改变
        else if (eventType == VisitEventEnum.MEMBER_TIME_CHECK_CHANGE.getEventType()){
            String reviewDt = addDTO.getReCheckDt();
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_TIME_CHECK_CHANGE.getEventDetail(),
                        memberName,hospitalName,addDTO.getParamName(),reviewDt);
        }///转诊信息
        else if (eventType == VisitEventEnum.MEMBER_REFERRAL_MESSAGE.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_REFERRAL_MESSAGE.getEventDetail(),
                    memberName,hospitalName,addDTO.getReferralHospitalName());
        }//完成并发症筛查
        else if (eventType == VisitEventEnum.MEMBER_FINISH_SCREEN.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_FINISH_SCREEN.getEventDetail(),
                    memberName,hospitalName,addDTO.getParamName());
        }//中医评估
        else if (eventType == VisitEventEnum.MEMBER_TCM_ASSESS.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_TCM_ASSESS.getEventDetail(),
                    memberName,hospitalName);
        }//患者住院
        else if (eventType == VisitEventEnum.MEMBER_IN_HOSPITAL.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_IN_HOSPITAL.getEventDetail(),
                    memberName,hospitalName,departmentName);
        }//患者出院
        else if (eventType == VisitEventEnum.MEMBER_OUT_HOSPITAL.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_OUT_HOSPITAL.getEventDetail(),
                    memberName,hospitalName,departmentName);
        }//门诊就诊
        else if (eventType == VisitEventEnum.MEMBER_OUTPATIENT_TREATMENT.getEventType()){
            eventDetail = MessageTool.format(VisitEventEnum.MEMBER_OUTPATIENT_TREATMENT.getEventDetail(),
                    memberName,hospitalName);
        }
        visitEventPO.setEventDetail(eventDetail);
    }

    @Override
    public PageResult pageVisitEventList(GetListVisitEventDTO getListVisitEventDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ListVisitEventVO> listVisitEventVOS = visitEventMapper.listVisitEvent(getListVisitEventDTO);
        return new PageResult(listVisitEventVOS);
    }
}
