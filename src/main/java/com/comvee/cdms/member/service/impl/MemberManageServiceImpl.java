package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.app.doctorapp.model.app.H5MemberJoinHospitalVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.cfg.DoctorConstant;
import com.comvee.cdms.hospital.model.po.HospitalCommitteePO;
import com.comvee.cdms.hospital.model.po.HospitalPO;
import com.comvee.cdms.hospital.service.CommitteeService;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.level.dto.ListMemberLevelDTO;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.member.constant.MemberLock;
import com.comvee.cdms.member.constant.MemberSourceConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberJoinHospitalMapper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberManageService;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.member.vo.ListFollowPlatformMemberVO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.member.vo.MemberJoinStatisticsVO;
import com.comvee.cdms.other.constant.DictConstant;
import com.github.pagehelper.PageHelper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MemberManageServiceImpl implements MemberManageService {


    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberJoinHospitalMapper joinHospitalMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private CommitteeService committeeService;

    @Override
    public boolean hasMemberJoinHospital(String memberId, String hospitalId){
        GetMemberJoinHospitalDTO dto = new GetMemberJoinHospitalDTO();
        dto.setMemberId(memberId);
        dto.setHospitalId(hospitalId);
        MemberJoinHospitalPO po = joinHospitalMapper.getMemberJoinHospital(dto);
        return po != null;
    }

    @Override
    public MemberJoinHospitalPO getMemberJoinHospital(String memberId, String hospitalId) {
        GetMemberJoinHospitalDTO dto = new GetMemberJoinHospitalDTO();
        dto.setMemberId(memberId);
        dto.setHospitalId(hospitalId);
        MemberJoinHospitalPO po = joinHospitalMapper.getMemberJoinHospital(dto);
        return po;
    }

    @Override
    public void addMemberJoinHospital(MemberJoinHospitalPO po){
        joinHospitalMapper.addMemberJoinHospital(po);
    }

    @Override
    public PageResult<MemberJoinHospitalVO> listMemberJoinHospital(ListMemberJoinHospitalDTO dto, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        if(dto.getStatus() != null && 0 == dto.getStatus() ){
            dto.setStatus(null);
        }
        List<MemberJoinHospitalVO> list = joinHospitalMapper.listMemberJoinHospitalV2(dto);
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
        for (MemberJoinHospitalVO vo: list
        ) {
            if(!StringUtils.isBlank(vo.getBirthday())) {
                vo.setAge(DateHelper.getAge(vo.getBirthday()));
                vo.setIdCard(ValidateTool.tuoMin(vo.getIdCard(), 4, 4, "*"));
                vo.setMobilePhone(ValidateTool.tuoMin(vo.getMobilePhone(), 3, 4, "*"));
            }
            if(result.containsKey(vo.getMemberId())){
                MemberLevelPO x = result.get(vo.getMemberId());
                vo.setHypLayer(x.getMemberLayer());
                vo.setHypLever(x.getMemberLevel());
            }

            if(result2.containsKey(vo.getMemberId())) {
                MemberLevelPO x = result2.get(vo.getMemberId());
                vo.setDiabetesLever(x.getMemberLevel());
            }
        }
        return new PageResult<>(list);
    }



    @Override
    public PageResult<H5MemberJoinHospitalVO> listMemberJoinHospitalH5(ListMemberJoinHospitalDTO dto, PageRequest pr){
        List<HospitalCommitteePO> doctorIdCommiteeList = committeeService.listCommitteeByDoctorId(dto.getDoctorId());
        List<String> commiteeIdlist = doctorIdCommiteeList.stream().map(HospitalCommitteePO::getId).collect(Collectors.toList());
        if (StringUtils.isBlank(dto.getKeyword()) && null != dto.getDoctorLevel()
                && dto.getDoctorLevel().equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
            //搜索则搜本院 不搜索村医列表则默认展示关联社区
            dto.setCommitteeIdList(commiteeIdlist);
        }
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<H5MemberJoinHospitalVO> list = joinHospitalMapper.listMemberJoinHospitalH5(dto);
        for (H5MemberJoinHospitalVO vo: list) {
            if (!StringUtils.isBlank(vo.getBirthday())) {
                vo.setAge(DateHelper.getAge(vo.getBirthday()));
            }
            vo.setInCommittee(0);
            if (!commiteeIdlist.isEmpty() && !StringUtils.isBlank(vo.getCommitteeId())){
                if (commiteeIdlist.contains(vo.getCommitteeId())){
                    //所在社区已被管理
                    vo.setInCommittee(1);
                }
            }
        }
        return new PageResult<>(list);
    }

    @Override
    public void attentionMember(String[] members, String hospitalId) {
        for (String member: members
        ) {
            GetMemberJoinHospitalDTO dto = new GetMemberJoinHospitalDTO();
            dto.setMemberId(member);
            dto.setHospitalId(hospitalId);
            MemberJoinHospitalPO po = joinHospitalMapper.getMemberJoinHospital(dto);
            if(po != null) {
                po.setAttention(1);
                joinHospitalMapper.updateMemberJoinHospital(po);
            }
        }
    }

    @Override
    public void cancelAttentionMember(String[] members, String hospitalId) {
        for (String member: members
        ) {
            GetMemberJoinHospitalDTO dto = new GetMemberJoinHospitalDTO();
            dto.setMemberId(member);
            dto.setHospitalId(hospitalId);
            MemberJoinHospitalPO po = joinHospitalMapper.getMemberJoinHospital(dto);
            if(po != null) {
                po.setAttention(0);
                joinHospitalMapper.updateMemberJoinHospital(po);
            }
        }
    }

    @Override
    public MemberJoinStatisticsVO memberJoinStatistics(String hospitalId){
        MemberJoinStatisticsVO vo = joinHospitalMapper.memberJoinStatistics(hospitalId);
        //今日门诊患者数量
        //政和医院列表
        List<HospitalPO> hospitalPOS = hospitalService.listHospitalByAreaId(DictConstant.ZHENGHE_AREA_ID);
        List<String> hospitalIdList = hospitalPOS.stream().map(HospitalPO::getHospitalId).collect(Collectors.toList());
        ListMemberVisitDTO listMemberVisitDTO = new ListMemberVisitDTO();
        String today = DateHelper.getToday();
        String beginDay = DateHelper.plusDate(DateHelper.getToday(), -15);
        listMemberVisitDTO.setVisitStartDt(beginDay+DateHelper.DEFUALT_TIME_START);
        listMemberVisitDTO.setVisitEndDt(today+DateHelper.DEFUALT_TIME_END);
        listMemberVisitDTO.setHospitalIdList(hospitalIdList);
        listMemberVisitDTO.setJoinHospitalId(hospitalId);
        long outMemberNum = memberMapper.countMemberVisit(listMemberVisitDTO);
        vo.setAtHomeMember((int)outMemberNum);
        if(vo.getAllMember() != null && vo.getAllMember() != 0){
            vo.setInHosMemberRate(String.format("%.2f", vo.getInHosMember() * 1.0/ vo.getAllMember() * 100));
            vo.setDiabetesMemberRate(String.format("%.2f", vo.getDiabetesMember() * 1.0/ vo.getAllMember() * 100));
            vo.setHypMemberRate(String.format("%.2f", vo.getHypMember() * 1.0/ vo.getAllMember() * 100));
        }else {
            vo.setInHosMemberRate("0");
            vo.setDiabetesMemberRate("0");
            vo.setHypMemberRate("0");
        }
        return vo;
    }


    @Override
    public PageResult<String> listAllManageMemberDistinct(PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<String> list = this.joinHospitalMapper.listAllManageMemberDistinct();
        return new PageResult<>(list);
    }

    @Override
    public List<H5MemberJoinHospitalVO> listMemberRecentSearchH5(String doctorId, String hospitalId){
        Map<String, Long> members = h5MemberCache.getIfPresent(doctorId);
        Set<String> mList = new HashSet<>();
        long cur = System.currentTimeMillis();
        if(members != null) {
            for(String key: members.keySet()){
                Long value = members.get(key);
                if(value + 1000*60*60*24 > cur){
                    mList.add(key);
                }else {
                    members.remove(key);
                }
            }
            h5MemberCache.put(doctorId, members);
            ListMemberJoinHospitalDTO dto = new ListMemberJoinHospitalDTO();
            dto.setHospitalId(hospitalId);
            dto.setMembers(new ArrayList<>(mList));
            dto.setDoctorId(doctorId);
            return joinHospitalMapper.listMemberJoinHospitalH5(dto);
        }
        return null;
    }

    @Override
    public void addMemberToCache(String doctorId, String memberId){
        Map<String, Long> members = h5MemberCache.getIfPresent(doctorId);
        if(members == null){
            members = new HashMap<>();
        }
        members.put(memberId, System.currentTimeMillis());
        h5MemberCache.put(doctorId, members);
    }

    @Override
    public PageResult<MemberJoinHospitalVO> listMemberVisitRecord(ListMemberVisitDTO dto, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<MemberJoinHospitalVO> list = memberMapper.listMemberVisit(dto);
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
        for (MemberJoinHospitalVO vo: list
        ) {
            if(!StringUtils.isBlank(vo.getBirthday())) {
                vo.setAge(DateHelper.getAge(vo.getBirthday()));
                vo.setIdCard(ValidateTool.tuoMin(vo.getIdCard(), 4, 4, "*"));
                vo.setMobilePhone(ValidateTool.tuoMin(vo.getMobilePhone(), 3, 4, "*"));
            }
            if(result.containsKey(vo.getMemberId())){
                MemberLevelPO x = result.get(vo.getMemberId());
                vo.setHypLayer(x.getMemberLayer());
                vo.setHypLever(x.getMemberLevel());
            }

            if(result2.containsKey(vo.getMemberId())) {
                MemberLevelPO x = result2.get(vo.getMemberId());
                vo.setDiabetesLever(x.getMemberLevel());
            }
        }
        return new PageResult<>(list);
    }

    @Override
    @Transactional
    public String createMemberHospitalRelation(DoctorSessionBO doctorSessionBO, AddMemberDTO addMemberDTO) {
        MemberPO memberPO = memberService.getMemberByIdCard(addMemberDTO.getIdCard());
        String memberId;
        if (memberPO == null) {
            MemberLock.ADD_MEMBER_LOCK.lock();
            try {
                if (null == addMemberDTO.getMemberSource()){
                    addMemberDTO.setMemberSource(MemberSourceConstant.MEMBER_SOURCE_DOC_WEB);
                }
                memberId = this.memberService.addMember(addMemberDTO, doctorSessionBO);
            } finally {
                MemberLock.ADD_MEMBER_LOCK.unlock();
            }
        } else {
            memberId = memberPO.getMemberId();
            UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
            updateMemberDTO.setMemberId(memberId);
            updateMemberDTO.setBirthday(addMemberDTO.getBirthday());
            updateMemberDTO.setSex(addMemberDTO.getSex());
            updateMemberDTO.setMemberName(addMemberDTO.getMemberName());
            updateMemberDTO.setMobilePhone(addMemberDTO.getMobilePhone());
            updateMemberDTO.setVisitNo(addMemberDTO.getVisitNo());
            updateMemberDTO.setDiabetesType(addMemberDTO.getDiabetesType());
            updateMemberDTO.setSocialCard(addMemberDTO.getSocialCard());
            updateMemberDTO.setCommitteeId(addMemberDTO.getCommitteeId());
            updateMemberDTO.setCommitteeName(addMemberDTO.getCommitteeName());
            memberService.updateMember(updateMemberDTO);
            memberService.memberJoinHospitalHandler(doctorSessionBO, memberId);
            this.memberService.updateMemberVisitNo(doctorSessionBO.getHospitalId(), updateMemberDTO.getMemberId(), updateMemberDTO.getVisitNo());
        }
        return memberId;
    }

    @Override
    @Transactional
    public Boolean deleteMemberJoinHospital(String sid) {
        MemberJoinHospitalPO memberJoinHospitalBySid = joinHospitalMapper.getMemberJoinHospitalBySid(sid);
        if (null == memberJoinHospitalBySid){
            throw new BusinessException("患者管理关系不存在");
        }
        MemberJoinHospitalPO memberJoinHospitalPO = new MemberJoinHospitalPO();
        memberJoinHospitalPO.setSid(sid);
        memberJoinHospitalPO.setValid(0);
        return joinHospitalMapper.updateMemberJoinHospital(memberJoinHospitalPO) >0 ;
    }

    @Override
    public List<MemberJoinHospitalPO> listMemberJoinHospital(ListMemberJoinHospitalDTO param) {
        return this.joinHospitalMapper.listMemberJoinHospital(param);
    }

    @Override
    public PageResult<ListFollowPlatformMemberVO> pagerCommitteeMemberForFollowPlatform(DoctorSessionBO doctorSessionBO,ListMemberJoinHospitalDTO dto,PageRequest page) {
        List<String> committeeIdList = this.getDoctorCommitteeIdList(doctorSessionBO);
        dto.setCommitteeIdList(committeeIdList);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ListFollowPlatformMemberVO> listFollowPlatformMemberVOS = joinHospitalMapper.listCommitteeMemberJoinHospitalFollow(dto);
        listFollowPlatformMemberVOS.forEach(e->{
            if (!StringUtils.isBlank(e.getBirthday())){
                e.setAge(DateHelper.getAge(e.getBirthday()));
            }
        });

        return new PageResult<>(listFollowPlatformMemberVOS);
    }

    private List<String> getDoctorCommitteeIdList(DoctorSessionBO doctorSessionBO){
        String doctorId = doctorSessionBO.getDoctorId();
        Integer doctorLevel = doctorSessionBO.getDoctorLevel();
        if (null != doctorLevel  &&
                !doctorLevel.equals(DoctorConstant.DOCTOR_LEVEL_VILLAGE)){
            throw new BusinessException("当前账号不是村医,请确认");
        }
        List<HospitalCommitteePO> hospitalCommitteePOS = committeeService.listCommitteeByDoctorId(doctorId);
        if (hospitalCommitteePOS.size()==0){
            throw new BusinessException("当前账号未关联社区,请确认");

        }
        //社区id列表
        List<String>  committeeIdList = hospitalCommitteePOS.stream().map(HospitalCommitteePO::getId).collect(Collectors.toList());
        return committeeIdList;
    }

    @Override
    public PageResult<ListFollowPlatformMemberVO> searchJoinHospitalMember(ListMemberJoinHospitalDTO dto,PageRequest page) {
        dto.setMobilePhone(dto.getKeyword());
        PageHelper.startPage(page.getPage(), page.getRows());
        List<ListFollowPlatformMemberVO> listFollowPlatformMemberVOS = joinHospitalMapper.searchJoinHospitalMember(dto);
        listFollowPlatformMemberVOS.forEach(e->{
            if (!StringUtils.isBlank(e.getBirthday())){
                e.setAge(DateHelper.getAge(e.getBirthday()));
            }
        });
        return new PageResult<>(listFollowPlatformMemberVOS);
    }

    public static final Cache<String, Map<String, Long>> h5MemberCache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(5 , TimeUnit.DAYS)
            .build();
}
