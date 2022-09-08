package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.cfg.GroupConstant;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.constant.MemberApplyConstant;
import com.comvee.cdms.member.dto.*;
import com.comvee.cdms.member.mapper.MemberApplyMapper;
import com.comvee.cdms.member.po.DoctorMemberApplyPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberApplyService;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/6
 */
@Service("memberApplyService")
public class MemberApplyServiceImpl implements MemberApplyService {

    @Autowired
    private MemberApplyMapper memberApplyMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorServiceI doctorService;

    @Override
    public DoctorMemberApplyPO getDoctorMemberApply(String memberId, String doctorId) {
        return this.memberApplyMapper.getDoctorMemberApply(memberId, doctorId);
    }

    @Override
    public void updateDoctorMemberApply(UpdateDoctorMemberApplyDTO updateDoctorMemberApplyDTO) {
        this.memberApplyMapper.updateDoctorMemberApply(updateDoctorMemberApplyDTO);
    }

    @Override
    public String addDoctorMemberApply(DoctorMemberApplyDTO doctorMemberApplyDTO) {
        DoctorMemberApplyPO doctorMemberApplyPO = getDoctorMemberApply(doctorMemberApplyDTO.getMemberId(), doctorMemberApplyDTO.getDoctorId());
        if(doctorMemberApplyPO != null){
            return doctorMemberApplyPO.getSid();
        }else{
            doctorMemberApplyPO = new DoctorMemberApplyPO();
            BeanUtils.copyProperties(doctorMemberApplyPO, doctorMemberApplyDTO);
            doctorMemberApplyPO.setApplyStatus(MemberApplyConstant.APPLY_STATUS_PASS);
            doctorMemberApplyPO.setSid(DaoHelper.getSeq());
            this.memberApplyMapper.addDoctorMemberApply(doctorMemberApplyPO);
            AddMemberDoctorRelateDTO addMemberDoctorRelateDTO = new AddMemberDoctorRelateDTO();
            BeanUtils.copyProperties(addMemberDoctorRelateDTO, doctorMemberApplyDTO);
            addMemberDoctorRelateDTO.setOperatorId(doctorMemberApplyDTO.getDoctorId());
            addMemberDoctorRelateDTO.setGroupId(GroupConstant.DEFAULT_GROUP_ID);
            addMemberDoctorRelateDTO.setRelationWay(doctorMemberApplyDTO.getRelationWay());
            this.memberService.addMemberDoctorRelate(addMemberDoctorRelateDTO);
            return doctorMemberApplyPO.getSid();
        }
    }

    /**
     * 分页申请记录列表
     * @param listDoctorMemberApplyDTO
     */
    @Override
    public PageResult<DoctorMemberApplyPO> listDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO, PageRequest page){


        List<String> doctorIdList = this.doctorService.listTeamId(listDoctorMemberApplyDTO.getDoctorId());
        if(null!=doctorIdList && doctorIdList.size()>0){
            listDoctorMemberApplyDTO.setDoctorIdList(doctorIdList);
        }
        PageHelper.startPage(page.getPage(),page.getRows());
        List<DoctorMemberApplyPO> list= this.memberApplyMapper.listDoctorMemberApply(listDoctorMemberApplyDTO);
        for (int i = 0; i <list.size() ; i++) {
            DoctorMemberApplyPO doctorMemberApplyPO = list.get(i);
            GetMemberDTO getMemberDTO=new GetMemberDTO();
            getMemberDTO.setMemberId(doctorMemberApplyPO.getMemberId());
            MemberPO member = memberService.getMember(getMemberDTO);
            if(null!=member){
                doctorMemberApplyPO.setMemberName(member.getMemberName());
                doctorMemberApplyPO.setSex(member.getSex()+"");
                doctorMemberApplyPO.setBirthday(member.getBirthday());
                doctorMemberApplyPO.setDiabetesType(member.getDiabetesType());
            }
        }
        PageResult<DoctorMemberApplyPO> tempPageResult = new PageResult<DoctorMemberApplyPO>(list);
        List<DoctorMemberApplyPO> rows = tempPageResult.getRows();
        if(null!=rows && rows.size()>0){
            for (int i = 0; i <rows.size() ; i++) {
                DoctorMemberApplyPO doctorMemberApplyPO = rows.get(i);
                DoctorPO doctor = doctorService.getDoctorById(doctorMemberApplyPO.getDoctorId());
                if(null!=doctor){
                    doctorMemberApplyPO.setDoctorName(doctor.getDoctorName());
                }
            }
        }

        return  tempPageResult;

    }

    /**
     * 统计申请数
     * @param listDoctorMemberApplyDTO
     */
    @Override
    public long countDoctorMemberApply(ListDoctorMemberApplyDTO listDoctorMemberApplyDTO){
        return this.memberApplyMapper.countDoctorMemberApply(listDoctorMemberApplyDTO);
    }


}
