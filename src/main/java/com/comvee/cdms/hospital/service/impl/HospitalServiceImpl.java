package com.comvee.cdms.hospital.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.mapper.ScreeningMachineMapper;
import com.comvee.cdms.complication.model.vo.ScreeningMachineVO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.hospital.constant.HospitalConstant;
import com.comvee.cdms.hospital.mapper.HospitalMapper;
import com.comvee.cdms.hospital.mapper.HospitalRelatedMapper;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.hospital.model.bo.InHospitalLogBO;
import com.comvee.cdms.hospital.model.dto.*;
import com.comvee.cdms.hospital.model.po.*;
import com.comvee.cdms.hospital.model.vo.InHospitalLogVO;
import com.comvee.cdms.hospital.service.HospitalService;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.mapper.MemberJoinHospitalMapper;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private HospitalRelatedMapper hospitalRelatedMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private ScreeningMachineMapper screeningMachineMapper;

    @Autowired
    private DoctorCacheServiceI doctorCacheService;

    @Autowired
    private MemberJoinHospitalMapper memberJoinHospitalMapper;

    @Override
    public String addHospital(AddHospitalDTO addHospitalDTO) {
        String hospitalId = DaoHelper.getSeq();
        HospitalPO hospitalPO = new HospitalPO();
        BeanUtils.copyProperties(hospitalPO,addHospitalDTO);
        hospitalPO.setHospitalId(hospitalId);
        hospitalPO.setAreaId(StringUtils.isBlank(hospitalPO.getAreaId())?null:hospitalPO.getAreaId());
        hospitalPO.setLevel(addHospitalDTO.getLevel() == null ? 4 : addHospitalDTO.getLevel());  //默认为4 县级
        this.hospitalMapper.addHospital(hospitalPO);
        //关联上级医院
        if (!StringUtils.isBlank(addHospitalDTO.getUpHospitalId())){
            HospitalRelatedPO addUp = null;
            String[] upList = addHospitalDTO.getUpHospitalId().split(",");
            for (String upHospialId : upList) {
                addUp = new HospitalRelatedPO();
                addUp.setSid(DaoHelper.getSeq());
                addUp.setHospitalId(hospitalId);
                addUp.setUpHospitalId(upHospialId);
                this.hospitalRelatedMapper.addHospitalRelated(addUp);
            }
        }
        //关联下级医院
        if (!StringUtils.isBlank(addHospitalDTO.getDownHospitalId())){
            HospitalRelatedPO addDown = null;
            String[] downList = addHospitalDTO.getDownHospitalId().split(",");
            for (String downHospitalId : downList) {
                addDown = new HospitalRelatedPO();
                addDown.setSid(DaoHelper.getSeq());
                addDown.setHospitalId(downHospitalId);
                addDown.setUpHospitalId(hospitalId);
                this.hospitalRelatedMapper.addHospitalRelated(addDown);
            }
        }

        return hospitalId;
    }

    @Override
    public HospitalPO getHospital(String hospitalId) {
        return this.hospitalMapper.getHospital(hospitalId);
    }

    @Override
    public PageResult<HospitalPO> listHospital(ListHospitalDTO listHospitalDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<HospitalPO> list = this.hospitalMapper.listHospital(listHospitalDTO);
        for (HospitalPO hospitalPO : list) {
            ListMemberJoinHospitalDTO listMemberJoinHospitalDTO = new ListMemberJoinHospitalDTO();
            listMemberJoinHospitalDTO.setHospitalId(hospitalPO.getHospitalId());
            List<MemberJoinHospitalPO> memberJoinHospitalPOList = memberJoinHospitalMapper.listMemberJoinHospital(listMemberJoinHospitalDTO);
            hospitalPO.setMemberNumber(memberJoinHospitalPOList.size());
        }
        return new PageResult<>(list);
    }

    @Override
    public void updateHospital(UpdateHospitalDTO updateHospitalDTO) {
        HospitalPO old = getHospital(updateHospitalDTO.getHospitalId());
        if(old == null){
            throw new BusinessException("修改医院不存在，请确认");
        }
        updateHospitalDTO.setAreaId(StringUtils.isBlank(updateHospitalDTO.getAreaId())?null:updateHospitalDTO.getAreaId());
        if (null != updateHospitalDTO.getUpdateOrigin() && updateHospitalDTO.getUpdateOrigin() == 1){
            //查询出原来医院关联的下级医院id(用于更新医生关联的切换医院)
            List<String> oldList = this.hospitalRelatedMapper.listDownHospitalId(updateHospitalDTO.getHospitalId());
            //删除关联上,下级医院
            this.hospitalRelatedMapper.deleteHospitalRelated(updateHospitalDTO.getHospitalId());
            //添加关联上级医院
            if (!StringUtils.isBlank(updateHospitalDTO.getUpHospitalId())){
                HospitalRelatedPO addUp = null;
                String[] upList = updateHospitalDTO.getUpHospitalId().split(",");
                for (String upHospialId : upList) {
                    addUp = new HospitalRelatedPO();
                    addUp.setSid(DaoHelper.getSeq());
                    addUp.setHospitalId(updateHospitalDTO.getHospitalId());
                    addUp.setUpHospitalId(upHospialId);
                    this.hospitalRelatedMapper.addHospitalRelated(addUp);
                }
            }
            //查询当前医院有切换医院权限的医生id
            List<String> doctorIdList = this.doctorService.getDoctorIdsSwitch(updateHospitalDTO.getHospitalId());

            //添加关联下级医院
            if (!StringUtils.isBlank(updateHospitalDTO.getDownHospitalId())){
                HospitalRelatedPO addDown = null;
                String[] downList = updateHospitalDTO.getDownHospitalId().split(",");
                for (String downHospitalId : downList) {
                    addDown = new HospitalRelatedPO();
                    addDown.setSid(DaoHelper.getSeq());
                    addDown.setHospitalId(downHospitalId);
                    addDown.setUpHospitalId(updateHospitalDTO.getHospitalId());
                    this.hospitalRelatedMapper.addHospitalRelated(addDown);
                }

                //要删除的医院id集合
                List<String> deleteList = new ArrayList<>();
                List<String> newList = Arrays.asList(downList);
                if (oldList != null && oldList.size() > 0){
                    for (String s : oldList) {
                        if (!newList.contains(s)){
                            deleteList.add(s);
                        }
                    }
                }

                if (deleteList != null && doctorIdList != null && deleteList.size() > 0 && doctorIdList.size() > 0){
                    this.doctorService.deleteDoctorHospitalByParam(doctorIdList,deleteList);
                }
            }else {
                this.doctorService.deleteDoctorHospitalByParam(doctorIdList,null);
            }


        }
        this.hospitalMapper.updateHospital(updateHospitalDTO);

        //修改医生表冗余数据
        if(!StringUtils.isBlank(updateHospitalDTO.getHospitalName())){
            if(!updateHospitalDTO.getHospitalName().equals(old.getHospitalName())){
                updateDoctorRedundancy(updateHospitalDTO);
            }
        }
    }

    @Override
    public List<HospitalPO> listHospitalByProvinceAndCity(String provinceId, String cityId) {
        ValidateTool.checkParameterIsNull(provinceId,"省id不能为空");
        ValidateTool.checkParameterIsNull(cityId,"市id不能为空");
        return this.hospitalMapper.listHospitalByProvinceAndCity(provinceId,cityId);
    }

    @Override
    public CheckinInfoBO getCheckinInfoBOByMid(String memberId ,String hospitalId) {
        CheckinInfoPO po = this.hospitalMapper.getCheckinInfoByMid(memberId ,hospitalId);
        CheckinInfoBO bo = new CheckinInfoBO();
        if(po!=null){
            BeanUtils.copyProperties(bo,po);
            HospitalPO hospital = this.hospitalMapper.getHospital(po.getHospitalId());
            bo.setHospitalName(hospital.getHospitalName());
        }
        return bo;
    }

    @Override
    public void updateCheckinInfo(CheckinInfoDTO updDTO) {
        CheckinInfoPO checkinInfoPO = new CheckinInfoPO();
        BeanUtils.copyProperties(checkinInfoPO,updDTO);
        this.hospitalMapper.updateCheckinInfo(checkinInfoPO);
    }

    @Override
    public InHospitalLogBO getMemberInHospitalLogBOByMid(String memberId) {
        InHospitalLogPO po = this.hospitalMapper.getMemberInHospitalLogBOByMid(memberId);
        InHospitalLogBO bo = new InHospitalLogBO();
        BeanUtils.copyProperties(bo,po);
        return bo;
    }

    @Override
    public void updateInHospitalLog(InHospitalLogDTO updDTO) {
        InHospitalLogPO inHospitalLogPO = new InHospitalLogPO();
        BeanUtils.copyProperties(inHospitalLogPO,updDTO);
        this.hospitalMapper.updateInHospitalLog(inHospitalLogPO);
    }

    @Override
    public void addInHospitalLog(InHospitalLogDTO addDTO) {
        InHospitalLogPO inHospitalLogPO = new InHospitalLogPO();
        BeanUtils.copyProperties(inHospitalLogPO,addDTO);
        this.hospitalMapper.addInHospitalLog(inHospitalLogPO);
    }

    @Override
    public List<InHospitalLogVO> listMemberInHospitalLogByMid(String memberId) {
        return this.hospitalMapper.listMemberInHospitalLogByMid(memberId);
    }

    @Override
    public List<HospitalPO> listAllHospital(){
        ListHospitalDTO dto = new ListHospitalDTO();
        return this.hospitalMapper.listHospital(dto);
    }

    @Override
    public List<HospitalPO> listHospitalByAreaId(String areaId) {
        ListHospitalDTO dto = new ListHospitalDTO();
        dto.setAreaId(areaId);
        return this.hospitalMapper.listHospital(dto);
    }

    @Override
    public List<HospitalPO> listTownHospitalByAreaId(String areaId) {
        ListHospitalDTO dto = new ListHospitalDTO();
        dto.setAreaId(areaId);
        dto.setLevel(3);//乡镇
        return this.hospitalMapper.listHospital(dto);
    }

    @Override
    public HosOptionPO getHosOption(HosOptionDTO dto) {
        return this.hospitalMapper.getHosOption(dto);
    }

    @Override
    public List<CheckinInfoPO> listCheckinInfoOfV(String hospitalId,String unDepartName){
        return this.hospitalMapper.listCheckinInfoOfV(hospitalId,unDepartName);
    }

    @Override
    public List<String> getHospitalIdsByAreaId(String areaId) {
        return this.hospitalMapper.getHospitalIdsByAreaId(areaId);
    }


    /**
     * 修改医生冗余信息
     * @param updateHospitalDTO
     */
    private void updateDoctorRedundancy(UpdateHospitalDTO updateHospitalDTO){
        List<DoctorPO> doctorList = this.doctorService.listDoctorByHospitalId(updateHospitalDTO.getHospitalId());
        if(doctorList == null || doctorList.isEmpty()){
            return;
        }
        UpdateDoctorDTO updateDoctorDTO = null;
        for(DoctorPO doctor : doctorList){
            updateDoctorDTO = new UpdateDoctorDTO();
            updateDoctorDTO.setDoctorId(doctor.getDoctorId());
            updateDoctorDTO.setHospitalName(updateHospitalDTO.getHospitalName());
            this.doctorCacheService.updateDoctor(updateDoctorDTO);
        }
    }
}
