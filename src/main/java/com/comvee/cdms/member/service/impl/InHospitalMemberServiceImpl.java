package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.clinicaldiagnosis.constant.YzConstant;
import com.comvee.cdms.clinicaldiagnosis.mapper.YzMapper;
import com.comvee.cdms.clinicaldiagnosis.po.YzPO;
import com.comvee.cdms.clinicaldiagnosis.service.YzServiceI;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.doctor.vo.DoctorDepartVO;
import com.comvee.cdms.dybloodpressure.dto.GetMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.mapper.BpMemberMachineMapper;
import com.comvee.cdms.dybloodpressure.vo.ListMemberMachineBindVO;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.constant.*;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.mapper.MemberInHospitalLogMapper;
import com.comvee.cdms.member.mapper.MemberJoinHospitalMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.*;
import com.comvee.cdms.member.service.InHospitalMemberService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.InHospitalBedMemberVO;
import com.comvee.cdms.member.vo.InHospitalMemberVO;
import com.comvee.cdms.member.vo.ListInHospitalLogVO;
import com.comvee.cdms.member.vo.OutHospitalListMemberVO;
import com.comvee.cdms.sign.bo.BloodSugarInHosBO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.virtualward.constant.VirtualWardConstant;
import com.comvee.cdms.virtualward.mapper.VirtualWardTransferApplyMapper;
import com.comvee.cdms.virtualward.model.param.GetVirtualWardParam;
import com.comvee.cdms.virtualward.model.param.GetVirtualWardTransferApplyParam;
import com.comvee.cdms.virtualward.model.po.VirtualWardPO;
import com.comvee.cdms.virtualward.model.po.VirtualWardTransferApplyPO;
import com.comvee.cdms.virtualward.service.VirtualWardService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("inHospitalMemberService")
public class InHospitalMemberServiceImpl implements InHospitalMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;

    @Autowired
    private MemberInHospitalLogMapper memberInHospitalLogMapper;

    @Autowired
    private VirtualWardService virtualWardService;

    @Autowired
    private VirtualWardTransferApplyMapper virtualWardTransferApplyMapper;

    @Autowired
    private YzServiceI yzService;

    @Autowired
    private YzMapper yzMapper;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private BpMemberMachineMapper bpMemberMachineMapper;

    @Autowired
    private MemberJoinHospitalMapper memberJoinHospitalMapper;


    @Override
    public PageResult<InHospitalBedMemberVO> listHospitalBedWithMember(ListMemberDTO listMemberDTO, PageRequest pager) {
        String hospitalId = listMemberDTO.getHospitalId();
        listMemberDTOAuthHandler(listMemberDTO);
        PageHelper.startPage(pager.getPage(), pager.getRows());
        List<MemberListPO> list = this.memberMapper.listHospitalBedWithMember(listMemberDTO);
        PageResult<MemberListPO> memberListPOPageResult = new PageResult(list);
        this.memberService.setMemberDifferentLevels(list ,hospitalId);
        List<InHospitalMemberVO> voList = this.memberService.castInHospitalMemberVO(list);
        //处理图标
        List<InHospitalBedMemberVO> resultList = this.memberWithBedIconHandler(voList);
        PageResult result = memberListPOPageResult.createEmptyPageResult();
        //判断是否慢病管理
        memberJoinJudHandler(listMemberDTO.getHospitalId(),resultList);
        result.setRows(resultList);
        return result;
    }



    @Override
    public void listMemberDTOAuthHandler(ListMemberDTO listMemberDTO){
        List<String> groupIds = null;
        if(StringUtils.isBlank(listMemberDTO.getGroupId())){
            List<DoctorDepartVO> list = this.doctorService.listDoctorDepart(listMemberDTO);
            if(list!=null&&list.size()>0){
                groupIds = new ArrayList<>(list.size());
                for(DoctorDepartVO vo : list){
                    groupIds.add(vo.getDepartId());
                }
            }
            listMemberDTO.setGroupId(null);
        }
        listMemberDTO.setGroupIdList(groupIds);
    }

    @Override
    public PageResult<OutHospitalListMemberVO> listHospitalOutMember(ListMemberDTO listMemberDTO, PageRequest pager) {
        listMemberDTOAuthHandler(listMemberDTO);
        PageHelper.startPage(pager.getPage(),pager.getRows());
        List<MemberListPO> list = this.memberMapper.listHospitalOutMember(listMemberDTO);
        PageResult<MemberListPO> memberListPOPageResult = new PageResult(list);
        List<OutHospitalListMemberVO> outList = new ArrayList<>(list.size());
        list.forEach(e ->
                {
                    OutHospitalListMemberVO outVO = new OutHospitalListMemberVO();
                    BeanUtils.copyProperties(outVO,e);
                    BloodSugarInHosBO bo = this.bloodSugarService.getLatestBloodSugarInHos(e.getMemberId(),e.getInHospitalDt());
                    outVO.setParamTestNum(bo.getNum());
                    outVO.setDiseaseType(this.memberService.getDiseaseTypeOfMemberInfoCH(e));
                    if(!StringUtils.isBlank(e.getBirthday())){
                        outVO.setAge(DateHelper.getAge(e.getBirthday()));
                    }
                    outList.add(outVO);
                });
        PageResult result = memberListPOPageResult.createEmptyPageResult();
        result.setRows(outList);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void addInHospitalMember(AddInHospitalMemberDTO addInHospitalMemberDTO, DoctorPO doctorPO) {
        MemberLock.BED_RELATION_LOCK.lock();
        try{
            MemberPO memberPO = this.memberService.getMemberById(addInHospitalMemberDTO.getMemberId());
            if(memberPO == null){
                throw new BusinessException("患者不存在，请确认");
            }
            //查询患者是否已入住
            MemberCheckinInfoPO po = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(addInHospitalMemberDTO.getMemberId(), doctorPO.getHospitalId());
            if (po != null && !"".equals(po)){
                throw new BusinessException("该患者已存在，请勿重复添加");
            }
            MemberCheckinInfoPO memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoById(addInHospitalMemberDTO.getSid());
            if(memberCheckinInfoPO == null){
                throw new BusinessException("病床记录不存在，请确认");
            }
            if(MemberCheckinInfoConstant.CHECKIN_STATUS_YES == memberCheckinInfoPO.getCheckinStatus()){
                throw new BusinessException("病床已被入住，请确认");
            }

            hospitalCardNoHandler(addInHospitalMemberDTO.getMemberId() ,addInHospitalMemberDTO.getHospitalNo() ,doctorPO.getHospitalId());

            memberCheckinInfoPO.setMemberId(addInHospitalMemberDTO.getMemberId());
            memberCheckinInfoPO.setInHospitalDate(addInHospitalMemberDTO.getInHospitalDate());
            memberCheckinInfoPO.setOutHospitalDate(null);
            memberCheckinInfoPO.setDoctorZg(doctorPO.getDoctorName());
            memberCheckinInfoPO.setDoctorZgCode(doctorPO.getDoctorId());
            memberCheckinInfoPO.setCheckinStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_YES);
            memberCheckinInfoPO.setAdmNo(DaoHelper.getSeq());
            memberCheckinInfoPO.setHospitalNo(addInHospitalMemberDTO.getHospitalNo());
            this.memberCheckinInfoMapper.updateMemberCheckinInfo(memberCheckinInfoPO);

            //添加住院记录
            MemberInHospitalLogPO logPO = new MemberInHospitalLogPO();
            BeanUtils.copyProperties(logPO ,memberCheckinInfoPO);
            logPO.setSid(DaoHelper.getSeq());
            logPO.setMemberName(memberPO.getMemberName());
            this.memberInHospitalLogMapper.addMemberInHospitalLog(logPO);
        }finally {
            MemberLock.BED_RELATION_LOCK.unlock();
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void outHospital(String sid, String outHospitalDate,String doctorId) {
        MemberCheckinInfoPO info = this.memberCheckinInfoMapper.getMemberCheckinInfoById(sid);
        if(info == null){
            throw new BusinessException("操作的床位不存在，请确认");
        }
        if(MemberCheckinInfoConstant.CHECKIN_STATUS_YES != info.getCheckinStatus()){
            throw new BusinessException("操作的床位处于非入住状态");
        }
        outHospitalVirtualWardHandler(info,doctorId);
        outHospitalYzInsulinPump(info.getMemberId(),info.getHospitalId());
        info.setCheckinStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_NO);
        info.setMemberId(Constant.DEFAULT_FOREIGN_ID);
        this.memberCheckinInfoMapper.updateMemberCheckinInfo(info);

        //入院记录修改
        MemberInHospitalLogPO logPO = this.memberInHospitalLogMapper.getMemberInHospitalLogPOByAdmNo(info.getAdmNo());
        if(logPO == null){
            return;
        }
        int diff = (int) DateHelper.getDistanceTimes(outHospitalDate ,logPO.getInHospitalDate());
        logPO.setOutHospitalDate(outHospitalDate);
        logPO.setInStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_NO);
        logPO.setInHospitalDay(diff);
        logPO.setBedNo(info.getBedNo());
        this.memberInHospitalLogMapper.updateMemberInHospitalLog(logPO);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void reInHospital(ReInHospitalMemberDTO reInHospitalMemberDTO, DoctorPO doctorPO) {
        MemberLock.BED_RELATION_LOCK.lock();
        try{
        MemberPO memberPO = this.memberService.getMemberById(reInHospitalMemberDTO.getMemberId());
        if(memberPO == null){
            throw new BusinessException("患者不存在，请确认");
        }
        //查询患者是否已入住
        MemberCheckinInfoPO po = this.memberCheckinInfoMapper.getMemberCheckinInfoByMemberId(reInHospitalMemberDTO.getMemberId(), doctorPO.getHospitalId());
        if (po != null && !"".equals(po)){
            throw new BusinessException("该患者已存在，请勿重复添加");
        }
        MemberCheckinInfoPO memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoById(reInHospitalMemberDTO.getSid());
        if(memberCheckinInfoPO == null){
            throw new BusinessException("病床记录不存在，请确认");
        }
        if(MemberCheckinInfoConstant.CHECKIN_STATUS_YES == memberCheckinInfoPO.getCheckinStatus()){
            throw new BusinessException("病床已被入住，请确认");
        }

        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        BeanUtils.copyProperties(updateMemberDTO,reInHospitalMemberDTO);
        this.memberMapper.updateMember(updateMemberDTO);
        //添加or更新患者档案
        if(doctorPO!=null){
            Map<String,Object> archivesMap=new HashMap<>();
            Map<String,Object> anamnesis=new HashMap<>();
            anamnesis.put("essential_hyp",reInHospitalMemberDTO.getEssentialHyp());
            archivesMap.put("anamnesis",anamnesis);
            Map<String,Object> basic = new HashMap<>();
            basic.put("fat",reInHospitalMemberDTO.getFat());
            archivesMap.put("basic",basic);
            MemberArchivesDTO memberArchivesDTO=new MemberArchivesDTO();
            memberArchivesDTO.setMemberId(reInHospitalMemberDTO.getMemberId());
            memberArchivesDTO.setDoctorId(doctorPO.getDoctorId());
            memberArchivesDTO.setArchivesJson(JsonSerializer.objectToJson(archivesMap));
            this.memberService.updateMemberArchive(memberArchivesDTO);
        }
        //修改病床信息
        memberCheckinInfoPO.setMemberId(reInHospitalMemberDTO.getMemberId());
        memberCheckinInfoPO.setInHospitalDate(reInHospitalMemberDTO.getInHospitalDate());
        memberCheckinInfoPO.setDoctorZg(doctorPO.getDoctorName());
        memberCheckinInfoPO.setDoctorZgCode(doctorPO.getDoctorId());
        memberCheckinInfoPO.setCheckinStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_YES);
        memberCheckinInfoPO.setAdmNo(DaoHelper.getSeq());
        memberCheckinInfoPO.setHospitalNo(reInHospitalMemberDTO.getHospitalNo());
        this.memberCheckinInfoMapper.updateMemberCheckinInfo(memberCheckinInfoPO);
        //添加住院记录
        MemberInHospitalLogPO logPO = new MemberInHospitalLogPO();
        BeanUtils.copyProperties(logPO ,memberCheckinInfoPO);
        logPO.setSid(DaoHelper.getSeq());
        logPO.setMemberName(memberPO.getMemberName());
        logPO.setOutHospitalDate(null);
        this.memberInHospitalLogMapper.addMemberInHospitalLog(logPO);
        }finally {
            MemberLock.BED_RELATION_LOCK.unlock();
        }
    }

    @Override
    public List<ListInHospitalLogVO> getMemberHospitalLog(String memberId) {
        List<ListInHospitalLogVO> logs = this.memberInHospitalLogMapper.listMembersHospitalRecord(memberId);
        return logs;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void changeHospitalBed(ChangeHospitalBedDTO changeHospitalBedDTO) {
        MemberLock.BED_RELATION_LOCK.lock();
        try{
            MemberCheckinInfoPO newInfo = this.memberCheckinInfoMapper.getMemberCheckinInfoById(changeHospitalBedDTO.getNewSid());
            if(newInfo == null){
                throw new BusinessException("更换目标的病床不存在，请确认");
            }
            if(MemberCheckinInfoConstant.CHECKIN_STATUS_YES == newInfo.getCheckinStatus()){
                throw new BusinessException("更换目标床位非空闲状态，请确认");
            }

            MemberCheckinInfoPO old = this.memberCheckinInfoMapper.getMemberCheckinInfoById(changeHospitalBedDTO.getOldSid());
            if(old == null){
                throw new BusinessException("更换原病床不存在，请确认");
            }

            MemberCheckinInfoPO update = new MemberCheckinInfoPO();
            BeanUtils.copyProperties(update ,old);
            update.setSid(newInfo.getSid());
            update.setHospitalNo(changeHospitalBedDTO.getHospitalNo());
            update.setInHospitalDate(changeHospitalBedDTO.getInHospitalDate());
            update.setBedNo(newInfo.getBedNo());
            this.memberCheckinInfoMapper.updateMemberCheckinInfo(update);

            old.setMemberId(Constant.DEFAULT_FOREIGN_ID);
            old.setCheckinStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_NO);
            this.memberCheckinInfoMapper.updateMemberCheckinInfo(old);

            //入院记录修改
            MemberInHospitalLogPO logPO = this.memberInHospitalLogMapper.getMemberInHospitalLogPOByAdmNo(old.getAdmNo());
            if(logPO == null){
                return;
            }
            MemberInHospitalLogPO updateLog = new MemberInHospitalLogPO();
            BeanUtils.copyProperties(updateLog ,update);
            updateLog.setSid(logPO.getSid());
            this.memberInHospitalLogMapper.updateMemberInHospitalLog(updateLog);
        }finally {
            MemberLock.BED_RELATION_LOCK.unlock();
        }

    }



    @Override
    public List<MemberCheckinInfoPO> listHospitalBed(String departmentId, Integer checkinStatus) {
        return this.memberCheckinInfoMapper.listMemberCheckinInfo(departmentId ,checkinStatus);
    }



    /**
     * 出院患者的虚拟病区数据处理
     */
    private void outHospitalVirtualWardHandler(MemberCheckinInfoPO info,String doctorId){
        GetVirtualWardTransferApplyParam param = new GetVirtualWardTransferApplyParam();
        param.setApplyStatus(VirtualWardConstant.APPLY_STATUS_NOT_DEAL);
//        param.setApplyType(VirtualWardConstant.APPLY_TYPE_IN);
        param.setHospitalId(info.getHospitalId());
        param.setMemberId(info.getMemberId());
        VirtualWardTransferApplyPO apply = this.virtualWardTransferApplyMapper.getVirtualWardTransferApplyOne(param);
        if(apply != null){
            apply.setApplyStatus(VirtualWardConstant.APPLY_STATUS_DIRECT_OUT_HOSPITAL);
            apply.setAllowDate(DateHelper.getNowDate());
            this.virtualWardTransferApplyMapper.updateVirtualWardTransferApply(apply);
        }

        GetVirtualWardParam get = new GetVirtualWardParam();
        get.setMemberId(info.getMemberId());
        get.setHospitalId(info.getHospitalId());
        get.setTransferStatus(VirtualWardConstant.TRANSFER_STATUS_IN);
        VirtualWardPO virtualWardPO = this.virtualWardService.getVirtualWard(get);
        if(virtualWardPO != null){
            virtualWardPO.setOutDate(DateHelper.getNowDate());
            virtualWardPO.setTransferStatus(VirtualWardConstant.TRANSFER_STATUS_OUT_HOSPITAL);
            this.virtualWardService.updateVirtualWard(virtualWardPO);
        }

    }
    /**
     * 出院患者的胰岛素泵数据处理
     * @param memberId
     */
    private void outHospitalYzInsulinPump(String memberId,String hospitalId){
        //查询该医院该患者有没有长期泵剂量医嘱
        List<YzPO> list = this.yzService.listYzByMemberIdAndTypeAndItemType(memberId, Arrays.asList(YzConstant.YZ_STATUS_SEND, YzConstant.YZ_STATUS_EXECUTING, YzConstant.YZ_STATUS_EXECUTED), 1, YzConstant.LONG_ADVICE, YzConstant.YZ_ITEM_CODE_INSULIN_PUMP,hospitalId);
        if (list != null && list.size()>0){
            for (YzPO po : list){
                YzPO yzPO = new YzPO();
                yzPO.setYzId(po.getYzId());
                yzPO.setStopDate(DateHelper.getToday());
                yzPO.setStopTime(DateHelper.getNowDate().substring(11,19));
                yzPO.setStopDt(DateHelper.getNowDate());
                yzPO.setYzStatus(YzConstant.YZ_STATUS_STOP);
                this.yzMapper.updateYz(yzPO);
            }
        }

    }


    /**
     * 住院卡号处理
     * @param memberId
     * @param hospitalNo
     * @param hospitalId
     */
    private void hospitalCardNoHandler(String memberId ,String hospitalNo ,String hospitalId){
        MemberVisitCardDTO memberVisitCardDTO = new MemberVisitCardDTO();
        memberVisitCardDTO.setHospitalId(hospitalId);
        memberVisitCardDTO.setVisitNo(hospitalNo);
        memberVisitCardDTO.setCardType(MemberConstant.CARD_TYPE_IN_HOSPITAL);
        MemberVisitCardPO memberVisitCard = this.memberService.getMemberVisitCard(memberVisitCardDTO);
        if(memberVisitCard == null){
            AddMemberVisitCardDTO addMemberVisitCardDTO = new AddMemberVisitCardDTO();
            addMemberVisitCardDTO.setMemberId(memberId);
            addMemberVisitCardDTO.setVisitNo(hospitalNo);
            addMemberVisitCardDTO.setHospitalId(hospitalId);
            addMemberVisitCardDTO.setCardType(MemberConstant.CARD_TYPE_IN_HOSPITAL);
            this.memberService.addMemberVisitCard(addMemberVisitCardDTO);
        }else{
            if(!memberId.equals(memberVisitCard.getMemberId())){
                throw new BusinessException("住院卡号已存在，请确认");
            }
        }
    }

    /**
     * 住院患者信息图标处理
     * @param list
     */
    private List<InHospitalBedMemberVO> memberWithBedIconHandler(List<InHospitalMemberVO> list){
        List<InHospitalBedMemberVO> resultList = new ArrayList<>(list.size());
        Set<String> memberIds = new HashSet<>();
        list.forEach( x -> memberIds.add(x.getMemberId()));
        ListMemberLevelDTO levelDTO = new ListMemberLevelDTO();
        levelDTO.setMembers(new ArrayList<>(memberIds));
        levelDTO.setLevelType(1);
        levelDTO.setConfirm(1);
        levelDTO.setCode("2");
        Map<String, MemberLevelPO> result = memberLevelService.getMemberLeverMap(levelDTO);
        levelDTO.setLevelType(2);
        Map<String, MemberLevelPO> result2 = memberLevelService.getMemberLeverMap(levelDTO);
        for (InHospitalMemberVO vo : list) {
            InHospitalBedMemberVO bedVO = new InHospitalBedMemberVO();
            BeanUtils.copyProperties(bedVO,vo);
            List<DYMemberSensorPO> memberSensorPOList = memberMapper.getMemberSensorPOList(vo.getMemberId());
            if (null == memberSensorPOList || 0 == memberSensorPOList.size()){
                bedVO.setSensor(0);
            }else {
                bedVO.setSensor(1);//有探头
                DYMemberSensorPO po = memberSensorPOList.get(0);
                Date startDate = new Date(Long.parseLong(po.getMonitoringTime()));
                String startDt = DateHelper.getDate(startDate,DateHelper.DATETIME_FORMAT);
                String endDt = DateHelper.plusDate(startDt,14);
                String nowDate = DateHelper.getNowDate();
                if (DateHelper.dateAfter(nowDate,DateHelper.DATETIME_FORMAT,startDt,DateHelper.DATETIME_FORMAT)
                && DateHelper.dateAfter(endDt,DateHelper.DAY_FORMAT,nowDate,DateHelper.DATETIME_FORMAT)){
                    bedVO.setSensorValid(1);//探头在有效期内
                }else {
                    bedVO.setSensorValid(0);
                }
            }

            //动态血压设备
            GetMemberMachineDTO getMemberMachineDTO = new GetMemberMachineDTO();
            getMemberMachineDTO.setMemberId(vo.getMemberId());
            List<ListMemberMachineBindVO> listMemberMachineBindVOS = bpMemberMachineMapper.listVO(getMemberMachineDTO);
            if (null != listMemberMachineBindVOS && listMemberMachineBindVOS.size()>0 && listMemberMachineBindVOS.get(0).getIsValid().equals("1")){
                bedVO.setDynamicBloodPressureMachine(1);//有使用中的动态血压设备
            }else {
                bedVO.setDynamicBloodPressureMachine(0);
            }
            bedVO.setIsVirtual(vo.getIsVirtualWard());

            List<InsulinPumpNurseRecordPO> memberInsulinPumpList = memberMapper.getMemberInsulinPumpList(vo.getMemberId());
            if (null == memberInsulinPumpList || 0 == memberInsulinPumpList.size()){
                bedVO.setInsulinPump(0);
            }else {
                bedVO.setInsulinPump(1);
                if (StringUtils.isBlank(memberInsulinPumpList.get(0).getPumpDownDt()) ||
                        DateHelper.dateAfter(memberInsulinPumpList.get(0).getPumpDownDt(),DateHelper.DATETIME_FORMAT,
                        DateHelper.getNowDate(),DateHelper.DATETIME_FORMAT)){
                    bedVO.setInsulinPumpValid(1);//未过期
                }else {
                    bedVO.setInsulinPumpValid(0);
                }

            }
            bedVO.setIsVirtual(vo.getIsVirtualWard());
            if(result.containsKey(vo.getMemberId())){
                MemberLevelPO x = result.get(vo.getMemberId());
                bedVO.setHypLayer(x.getMemberLayer());
                bedVO.setHypLever(x.getMemberLevel());
            }
            if(result2.containsKey(vo.getMemberId())) {
                MemberLevelPO x = result2.get(vo.getMemberId());
                bedVO.setDiabetesLever(x.getMemberLevel());
            }
            resultList.add(bedVO);
        }
        return resultList;
    }

    /**
     * 慢病患者判断
     * @param hospitalId
     * @param resultList
     */
    private void memberJoinJudHandler(String hospitalId,List<InHospitalBedMemberVO> resultList){
        ListMemberJoinHospitalDTO listDTO = new ListMemberJoinHospitalDTO();
        listDTO.setHospitalId(hospitalId);
        List<MemberJoinHospitalPO> memberJoinHospitalPOList = memberJoinHospitalMapper.listMemberJoinHospital(listDTO);
        List<String> collect = memberJoinHospitalPOList.stream().map(MemberJoinHospitalPO::getMemberId).collect(Collectors.toList());
        for (InHospitalBedMemberVO vo : resultList) {
            vo.setIsJoin(0);
            if (collect.contains(vo.getMemberId())){
                //慢病标识
                vo.setIsJoin(1);
            }
        }
    }

}
