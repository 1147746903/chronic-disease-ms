package com.comvee.cdms.clinic.service.impl;

import com.comvee.cdms.clinic.constant.ClinicInspectConstant;
import com.comvee.cdms.clinic.dto.AddClinicInspectDTO;
import com.comvee.cdms.clinic.dto.GetClinicInspectDTO;
import com.comvee.cdms.clinic.dto.ListClinicInspectDTO;
import com.comvee.cdms.clinic.dto.UpdateClinicInspectDTO;
import com.comvee.cdms.clinic.mapper.ClinicInspectMapper;
import com.comvee.cdms.clinic.mapper.ClinicRecordMapper;
import com.comvee.cdms.clinic.po.ClinicInspectPO;
import com.comvee.cdms.clinic.service.ClinicInspectService;
import com.comvee.cdms.clinic.vo.ClinicInspectVO;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wyc
 * @date 2019/8/12 10:25
 */
@Service("clinicInspectService")
public class ClinicInspectServiceImpl implements ClinicInspectService {

    @Autowired
    private ClinicInspectMapper clinicInspectMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DoctorServiceI doctorService;
    @Autowired
    private ClinicRecordMapper clinicRecordMapper;

    @Override
    public PageResult<ClinicInspectVO> listClinicInspect(PageRequest page, ListClinicInspectDTO listClinicInspectDTO) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<ClinicInspectPO> pos = this.clinicInspectMapper.listClinicInspect(listClinicInspectDTO);
        PageResult<ClinicInspectPO> listResult = new PageResult<>(pos);
        List<ClinicInspectVO> vos = new ArrayList<>();
        if (pos != null && pos.size() > 0){
            pos.forEach(x->{
                ClinicInspectVO inspectVO = new ClinicInspectVO();
                BeanUtils.copyProperties(inspectVO,x);
                //根据患者id获取患者信息
                MemberPO member = this.memberService.getMemberById(x.getMemberId());
                if (member != null){
                    //获取患者的姓名和年龄
                    inspectVO.setMemberName(member.getMemberName());
                    inspectVO.setMemberAge(DateHelper.getAge(member.getBirthday()));
                    if (x.getBaseJson() != null && !StringUtils.isBlank(x.getBaseJson().toString())){
                        Map<String, Object> map = JsonSerializer.jsonToMap(x.getBaseJson());
                        if (map.get("age") != null && !StringUtils.isBlank(map.get("age").toString())){
                            inspectVO.setMemberAge(Integer.parseInt(map.get("age").toString()));
                        }
                        if (map.get("member_real_name") != null && !StringUtils.isBlank(map.get("member_real_name").toString())){
                            inspectVO.setMemberName(map.get("member_real_name").toString());
                        }
                    }
                }
                vos.add(inspectVO);
            });
        }
        PageResult<ClinicInspectVO> pageResult = new PageResult<>(vos);
        pageResult.setTotalPages(listResult.getTotalPages());
        pageResult.setTotalRows(listResult.getTotalRows());
        pageResult.setPageNum(listResult.getPageNum());
        pageResult.setPageSize(listResult.getPageSize());
        return pageResult;
    }

    @Override
    public ClinicInspectPO getClinicInspectById(String sid) {
        return this.clinicInspectMapper.getClinicInspectById(sid);
    }

    @Override
    public String addClinicInspect(AddClinicInspectDTO clinicInspectDTO) {
        ListClinicInspectDTO dto = new ListClinicInspectDTO();
        dto.setMemberId(clinicInspectDTO.getMemberId());
        dto.setHospitalId(clinicInspectDTO.getHospitalId());
        dto.setCheckDt(clinicInspectDTO.getCheckDt());
        List<ClinicInspectPO> pos = this.clinicInspectMapper.listClinicInspectByParam(dto);
        if (pos != null && pos.size() >0){
            throw new BusinessException("患者在该日期已经存在检查信息");
        }
        ClinicInspectPO po = new ClinicInspectPO();
        BeanUtils.copyProperties(po,clinicInspectDTO);
        String sid = DaoHelper.getSeq();
        po.setSid(sid);
        po.setSaveStatus(ClinicInspectConstant.CLINIC_SAVE_STATUS_NO);
        //获取责任医生姓名
        DoctorPO doctor = this.doctorService.getDoctorById(clinicInspectDTO.getDoctorId());
        if (null != doctor){
            po.setDoctorName(doctor.getDoctorName());
        }
        this.clinicInspectMapper.addClinicInspect(po);
        return sid;
    }

    @Override
    public void modifyClinicInspect(UpdateClinicInspectDTO inspectDTO) {
        ClinicInspectPO po = new ClinicInspectPO();
        BeanUtils.copyProperties(po,inspectDTO);
        this.clinicInspectMapper.modifyClinicInspect(po);
    }

    @Override
    public void deleteClinicInspect(String sid) {
        //删除临床信息主表
        this.clinicInspectMapper.deleteClinicInspect(sid);
        //删除检查信息记录表
        this.clinicRecordMapper.delClinicRecordByClinicId(sid);
    }

    @Override
    public ClinicInspectPO getNewClinicInspect(GetClinicInspectDTO getClinicInspectDTO) {
        return this.clinicInspectMapper.getNewClinicInspect(getClinicInspectDTO);
    }
}
