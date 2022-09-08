package com.comvee.cdms.consultation.service.impl;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.consultation.constant.RemoteConsultationConstant;
import com.comvee.cdms.consultation.constant.RemoteConsultationMessageConstant;
import com.comvee.cdms.consultation.mapper.RemoteConsultationMapper;
import com.comvee.cdms.consultation.model.param.*;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationVO;
import com.comvee.cdms.consultation.service.RemoteConsultationMessageService;
import com.comvee.cdms.consultation.service.RemoteConsultationService;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.po.DoctorMemberPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("remoteConsultationService")
public class RemoteConsultationServiceImpl implements RemoteConsultationService {

    @Autowired
    private RemoteConsultationMapper remoteConsultationMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RemoteConsultationMessageService remoteConsultationMessageService;

    @Override
    public String addRemoteConsultation(RemoteConsultationPO param) {
        GetRemoteConsultationMapperParam getParam = new GetRemoteConsultationMapperParam();
        getParam.setMemberId(param.getMemberId());
        getParam.setConsultationStatusList(new ArrayList<>(
                Arrays.asList(
                    RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM
                    ,RemoteConsultationConstant.CONSULTATION_STATUS_ING
                )));
        RemoteConsultationPO exist = this.remoteConsultationMapper.getRemoteConsultation(getParam);
        if(exist != null){
            throw new BusinessException("该患者正在进行远程会诊");
        }
        addRemoteConsultationParamDefaultHandler(param);
        this.remoteConsultationMapper.addRemoteConsultation(param);
        return param.getSid();
    }

    @Override
    public RemoteConsultationVO getRemoteConsultationFullInfo(String sid) {
        RemoteConsultationPO remoteConsultation = this.remoteConsultationMapper.getRemoteConsultationById(sid);
        if(remoteConsultation == null){
            return null;
        }
        return fullRemoteConsultation(remoteConsultation);
    }

    @Override
    public PageResult<RemoteConsultationVO> listCurrentRemoteConsultation(ListCurrentRemoteConsultationParam param, PageRequest pr) {
        ListRemoteConsultationParam newParam = new ListRemoteConsultationParam();
        newParam.setOrDepartId(param.getDepartId());
        newParam.setSearchMemberName(param.getSearchMemberName());
        newParam.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_ING));
        return listRemoteConsultationVO(newParam ,pr);
    }

    @Override
    public void finishRemoteConsultation(FinishRemoteConsultationParam param) {
        RemoteConsultationPO local = this.remoteConsultationMapper.getRemoteConsultationById(param.getConsultationId());
        if(local == null){
            throw new BusinessException("会诊记录不存在");
        }
        if(RemoteConsultationConstant.CONSULTATION_STATUS_ING != local.getConsultationStatus()){
            throw new BusinessException("本次会诊记录无法进行结束操作");
        }
        local.setConsultationResult(param.getConsultationResult());
        local.setFinishDt(DateHelper.getNowDate());
        local.setFinishOperatorId(param.getDoctorId());
        local.setConsultationStatus(RemoteConsultationConstant.CONSULTATION_STATUS_FINISH);
        local.setFromRemindDt(DateHelper.getNowDate());
        local.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setSid(local.getSid());
        this.remoteConsultationMapper.updateRemoteConsultation(local ,where);
    }

    @Override
    public PageResult<RemoteConsultationVO> listApplyRemoteConsultation(String departId, PageRequest pr) {
        ListRemoteConsultationParam newParam = new ListRemoteConsultationParam();
        newParam.setToDepartId(departId);
        newParam.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM));
        return listRemoteConsultationVO(newParam ,pr);
    }

    @Override
    public PageResult<RemoteConsultationVO> listHistoryRemoteConsultation(ListHistoryRemoteConsultationParam param, PageRequest pr) {
        ListRemoteConsultationParam newParam = new ListRemoteConsultationParam();
        newParam.setOrDepartId(param.getDepartId());
        newParam.setSearchFromHospitalName(param.getSearchFromHospitalName());
        newParam.setSearchToHospitalName(param.getSearchToHospitalName());
        newParam.setSearchMemberName(param.getSearchMemberName());
        if(param.getConsultationStatus() != null){
            newParam.setConsultationStatusList(Collections.singletonList(param.getConsultationStatus()));
        }
        if(!StringUtils.isBlank(param.getMemberId())){
            newParam.setMemberIdList(Collections.singletonList(param.getMemberId()));
        }
        return listRemoteConsultationVO(newParam ,pr);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmRemoteConsultation(String consultationId, String doctorId) {
        RemoteConsultationPO local = this.remoteConsultationMapper.getRemoteConsultationById(consultationId);
        if(local == null){
            throw new BusinessException("会诊记录不存在");
        }
        if(RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM != local.getConsultationStatus()){
            throw new BusinessException("本次会诊记录无法进行接收操作");
        }

        local.setToDoctorId(doctorId);
        local.setConfirmDt(DateHelper.getNowDate());
        local.setConsultationStatus(RemoteConsultationConstant.CONSULTATION_STATUS_ING);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setSid(local.getSid());
        this.remoteConsultationMapper.updateRemoteConsultation(local ,where);

        //发送会诊原因
        SendRemoteConsultationMessageParam param = new SendRemoteConsultationMessageParam();
        param.setConsultationId(consultationId);
        param.setDoctorId(local.getFromDoctorId());
        param.setDepartId(local.getFromDepartId());
        param.setHospitalId(local.getFromHospitalId());
        param.setContentType(RemoteConsultationMessageConstant.CONTENT_TYPE_TEXT);
        param.setText(local.getConsultationReason());
        this.remoteConsultationMessageService.sendMessage(param);

    }

    @Override
    public PageResult<RemoteConsultationVO> listRemoteConsultationVO(ListRemoteConsultationParam param, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List list = this.remoteConsultationMapper.listRemoteConsultationVO(param);
        return new PageResult<>(list);
    }

    @Override
    public long getApplyRemindNumber(String departId) {
        CountRemoteConsultationParam param = new CountRemoteConsultationParam();
        param.setToDepartId(departId);
        param.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM));
        param.setToRemindDtCheck(true);
        param.setToRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        return this.remoteConsultationMapper.countRemoteConsultation(param);
    }

    @Override
    public long getFinishRemindNumber(String departId) {
        CountRemoteConsultationParam param = new CountRemoteConsultationParam();
        param.setFromDepartId(departId);
        param.setConsultationStatusList(new ArrayList<>(
                    Arrays.asList(
                            RemoteConsultationConstant.CONSULTATION_STATUS_FINISH,
                            RemoteConsultationConstant.CONSULTATION_STATUS_OVERDUE
                    )
                ));
        param.setFromRemindDtCheck(true);
        param.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        return this.remoteConsultationMapper.countRemoteConsultation(param);
    }

    @Override
    public void delayApplyRemind(String departId) {
        String remindDt = LocalDateTime.now().plusMinutes(5L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setToRemindDt(remindDt);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setToDepartId(departId);
        where.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_UN_CONFIRM));
        where.setToRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

    @Override
    public void readApplyRemind(String departId) {
        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setToRemindStatus(RemoteConsultationConstant.REMIND_STATUS_NO);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setToDepartId(departId);
        where.setToRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

    @Override
    public void delayFinishRemind(String departId) {
        String remindDt = LocalDateTime.now().plusMinutes(5L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setFromRemindDt(remindDt);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setFromDepartId(departId);
        where.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        where.setConsultationStatusList(new ArrayList<>(Arrays.asList(
                RemoteConsultationConstant.CONSULTATION_STATUS_OVERDUE
                ,RemoteConsultationConstant.CONSULTATION_STATUS_FINISH
        )));
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

    @Override
    public void readFinishRemind(String departId) {
        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_NO);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setFromDepartId(departId);
        where.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

    @Override
    public List<RemoteConsultationPO> listCurrentRemoteConsultationByMemberIdList(List<String> memberIdList) {
        ListRemoteConsultationParam param = new ListRemoteConsultationParam();
        param.setMemberIdList(memberIdList);
        param.setConsultationStatusList(Collections.singletonList(RemoteConsultationConstant.CONSULTATION_STATUS_ING));
        return this.remoteConsultationMapper.listRemoteConsultation(param);
    }

    @Override
    public Boolean checkOperatorMember(String memberId, String doctorId) {
        DoctorPO doctor = this.doctorService.getDoctorById(doctorId);
        CheckinInfoBO checkinInfo = this.hospitalService.getCheckinInfoBOByMid(memberId ,doctor.getHospitalId());
        if(checkinInfo != null && !StringUtils.isBlank(checkinInfo.getBedNo())){
            return true;
        }
        List<DoctorMemberPO> doctorMemberList = this.memberService.listDoctorMemberByDoctorId(memberId ,doctorId);
        if(doctorMemberList != null && !doctorMemberList.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 完善会诊记录信息
     * @param remoteConsultation
     * @return
     */
    private RemoteConsultationVO fullRemoteConsultation(RemoteConsultationPO remoteConsultation){
        RemoteConsultationVO result = new RemoteConsultationVO();
        BeanUtils.copyProperties(result ,remoteConsultation);
        //填充患者信息
        MemberPO member = this.memberService.getMemberById(remoteConsultation.getMemberId());
        if(member != null){
            result.setMemberName(member.getMemberName());
            result.setSex(member.getSex());
            result.setBirthday(member.getBirthday());
        }
        //填充会诊方信息
        HospitalPO hospital = this.hospitalService.getHospital(remoteConsultation.getToHospitalId());
        if(hospital != null){
            result.setToHospitalName(hospital.getHospitalName());
        }
        DepartmentPO department = this.departmentService.getDepartmentById(remoteConsultation.getToDepartId());
        if(department != null){
            result.setToDepartName(department.getDepartmentName());
        }
        if(!StringUtils.isBlank(remoteConsultation.getToDoctorId())){
            DoctorPO doctor = this.doctorService.getDoctorById(remoteConsultation.getToDoctorId());
            if(doctor != null){
                result.setToDoctorName(doctor.getDoctorName());
            }
        }

        //填充申请方信息
        DoctorPO doctor = this.doctorService.getDoctorById(remoteConsultation.getFromDoctorId());
        if(doctor != null){
            result.setFromDepartName(doctor.getDepartName());
            result.setFromHospitalName(doctor.getHospitalName());
            result.setFromDoctorName(doctor.getDoctorName());
        }
        return result;
    }

    /**
     * 添加远程会诊对象默认处理
     * @param param
     */
    private void addRemoteConsultationParamDefaultHandler(RemoteConsultationPO param){
        param.setSid(DaoHelper.getSeq());

        param.setFromRemindStatus(RemoteConsultationConstant.REMIND_STATUS_NO);
        param.setFromRemindDt(null);

        param.setToRemindStatus(RemoteConsultationConstant.REMIND_STATUS_YES);
        param.setToRemindDt(DateHelper.getNowDate());
        param.setToDoctorId(Constant.DEFAULT_FOREIGN_ID);
    }

}
