package com.comvee.cdms.department.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.department.mapper.DepartmentMapper;
import com.comvee.cdms.department.model.MemberBedVO;
import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentAndBedDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.department.service.DepartmentService;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.machine.mapper.ThpBedMachineMapper;
import com.comvee.cdms.machine.po.BedMachinePO;
import com.comvee.cdms.machine.service.MemberMachineServiceI;
import com.comvee.cdms.member.constant.MemberCheckinInfoConstant;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.mapper.MemberCheckinInfoMapper;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DoctorCacheServiceI doctorCacheService;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private MemberCheckinInfoMapper memberCheckinInfoMapper;

    @Autowired
    private ThpBedMachineMapper thpBedMachineMapper;

    @Autowired
    @Qualifier("memberMachineServiceNew")
    private MemberMachineServiceI memberMachineServiceNew;
    @Override
    public String addDepartment(AddDepartmentDTO addDepartmentDTO) {
        //一个医院只能有一个虚拟病区,添加过后不能再次被添加.
        if (addDepartmentDTO.getIsVirtual() == 2){
            DepartmentPO departmentPO = this.departmentMapper.getDepartmentByIsVirtual(addDepartmentDTO.getHospitalId(), addDepartmentDTO.getIsVirtual());
            if (departmentPO != null && !"".equals(departmentPO)){
                throw new BusinessException("该科室已存在,请勿重复添加!");
            }
        }
        return this.doctorCacheService.addDepartment(addDepartmentDTO);
    }

    @Override
    public PageResult<DepartmentPO> listDeparmentByHospitalId(String hospitalId, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DepartmentPO> list = this.departmentMapper.listDepartment(hospitalId);
        return new PageResult<>(list);
    }

    @Override
    public PageResult<DepartmentPO> listAllDepartmentAndVirtual(String hospitalId, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DepartmentPO> list = this.departmentMapper.listAllDepartmentAndVirtual(hospitalId);
        return new PageResult<>(list);
    }

    @Override
    public DepartmentPO getVirtualWardDepartment(ListMemberDTO listMemberDTO) {
        DepartmentPO po = this.departmentMapper.getDepartmentByIsVirtual(listMemberDTO.getHospitalId(),2);
        return po;
    }

    @Override
    public void updateDepartment(UpdateDepartmentDTO updateDepartmentDTO) {
        DepartmentPO old = getDepartmentById(updateDepartmentDTO.getDepartmentId());
        if(old == null){
            throw new BusinessException("修改的科室不存在，请确认");
        }
        this.doctorCacheService.updateDepartment(updateDepartmentDTO);
        if(!StringUtils.isBlank(updateDepartmentDTO.getDepartmentName())){
            if(!old.getDepartmentName().equals(updateDepartmentDTO.getDepartmentName())){
                updateDoctorRedundancy(updateDepartmentDTO);
            }
        }
    }

    @Override
    public void deleteDepartment(String departmentId) {
        List<MemberCheckinInfoPO> list = this.memberCheckinInfoMapper.listMemberCheckinInfo(departmentId ,MemberCheckinInfoConstant.CHECKIN_STATUS_YES);
        if(list != null && !list.isEmpty()){
            throw new BusinessException("请先对该科室患者办理出院");
        }
        this.doctorCacheService.deleteDepartment(departmentId);
    }

    @Override
    public List<DepartmentPO> listDepartmentByHospitalId(String hospitalId) {
        return this.departmentMapper.listDepartment(hospitalId);
    }

    @Override
    public List<DepartmentPO> listDoctorManageDepartment(String doctorId) {
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        List<DepartmentPO> result = null;
        JSONObject dataAuth = null;
        String type = "1";
        try{
            dataAuth = JSON.parseObject(doctorPO.getDataAuth());
            type = dataAuth.getString("type");
        }catch (Exception e){}

        // 3 科室权限 2 全院  1 个人
        if("3".equals(type)){
            String idListString = dataAuth.getString("list");
            List<String> idList = new ArrayList<>(Arrays.asList(idListString.split(",")));
            result = this.departmentMapper.listDepartmentById(idList);
            //加上自身的科室
            if(idList.indexOf(doctorPO.getDepartId()) < 0){
                DepartmentPO departmentPO = new DepartmentPO();
                departmentPO.setDepartmentId(doctorPO.getDepartId());
                DepartmentPO department =  this.departmentMapper.listDepartmentByDepartId(doctorPO.getDepartId());
                departmentPO.setDepartmentName(department.getDepartmentName());
                departmentPO.setIsVirtual(department.getIsVirtual());
                result.add(departmentPO);
            }
        }else if("2".equals(type)){
            result = this.departmentMapper.listDepartment(doctorPO.getHospitalId());
        }else{
            DepartmentPO departmentPO = new DepartmentPO();
            departmentPO.setDepartmentId(doctorPO.getDepartId());
            DepartmentPO department =  this.departmentMapper.listDepartmentByDepartId(doctorPO.getDepartId());
            departmentPO.setDepartmentName(department.getDepartmentName());
            departmentPO.setIsVirtual(department.getIsVirtual());
            result = Collections.singletonList(departmentPO);
        }
        return result;
    }

    @Override
    public void updateDepartmentAndBed(UpdateDepartmentAndBedDTO updateDepartmentAndBedDTO,String bindFlag) {
        try {

        }finally {

        }
        //修改科室信息
        UpdateDepartmentDTO updateDepartmentDTO = new UpdateDepartmentDTO();
        BeanUtils.copyProperties(updateDepartmentDTO ,updateDepartmentAndBedDTO);
        this.departmentMapper.updateDepartment(updateDepartmentDTO);

        deleteBedHandler(updateDepartmentAndBedDTO.getDeleteBedData());
        updateBedHandler(updateDepartmentAndBedDTO.getUpdateBedData());
        addBedHandler(updateDepartmentAndBedDTO);
    }

    @Override
    public MemberBedVO selectDepartmentAndBed(String sid) {
        //1：查找住院表的床号，
        MemberCheckinInfoPO memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoById(sid);
        MemberBedVO memberBedVO = new MemberBedVO();
        memberBedVO.setBedNo(memberCheckinInfoPO.getBedNo());
        BedMachinePO bedMachinePO = this.thpBedMachineMapper.selectByBedId(sid);
        if (bedMachinePO != null){
            memberBedVO.setMachineSn(bedMachinePO.getMachineSn());
        }

        return memberBedVO;
    }

    @Override
    public DepartmentPO getDepartmentById(String departmentId) {
        return this.departmentMapper.listDepartmentByDepartId(departmentId);
    }

    @Override
    public void addVirtualWard(AddDepartmentDTO addDepartmentDTO) {
        //查询该医院是否有虚拟病区科室
        DepartmentPO departmentPO = this.departmentMapper.getDepartmentByIsVirtual(addDepartmentDTO.getHospitalId(),2);
        if (departmentPO == null || "".equals(departmentPO)) {
            String departmentId = DaoHelper.getSeq();
            departmentPO = new DepartmentPO();
            BeanUtils.copyProperties(departmentPO, addDepartmentDTO);
            departmentPO.setDepartmentId(departmentId);
            this.departmentMapper.addDepartment(departmentPO);
        }
    }

    private void deleteBedHandler(String deleteBedData){
        if(StringUtils.isBlank(deleteBedData)){
            return;
        }
        List<String> deleteBedIdList = new ArrayList<>(Arrays.asList(deleteBedData.split(",")));
        if(deleteBedIdList == null || deleteBedIdList.isEmpty()){
            return;
        }
        MemberCheckinInfoPO memberCheckinInfoPO = null;
        for(String id : deleteBedIdList){
            BedMachinePO bedMachinePO =  this.thpBedMachineMapper.selectByBedId(id);
            if (bedMachinePO != null){
                //解绑设备
                this.thpBedMachineMapper.unBedBindEquipment(id);
            }
            memberCheckinInfoPO = this.memberCheckinInfoMapper.getMemberCheckinInfoById(id);
            if(memberCheckinInfoPO != null && memberCheckinInfoPO.getCheckinStatus() == MemberCheckinInfoConstant.CHECKIN_STATUS_YES){
                throw new BusinessException("病床" + memberCheckinInfoPO.getBedNo() + "有入住的患者，不可删除");
            }
            if(memberCheckinInfoPO != null){
                memberCheckinInfoPO.setIsValid(0);
                this.memberCheckinInfoMapper.updateMemberCheckinInfo(memberCheckinInfoPO);
            }
        }
    }

    private void updateBedHandler(String updateBedData){
        if(StringUtils.isBlank(updateBedData)){
            return;
        }
        JSONObject updateData = JSON.parseObject(updateBedData);
        MemberCheckinInfoPO update = null;
        List<String> bedNo = new ArrayList<>();
        for(JSONObject.Entry entry : updateData.entrySet()){
            update = new MemberCheckinInfoPO();
            update.setSid(entry.getKey().toString());
            update.setBedNo(entry.getValue().toString());
            if (bedNo.indexOf(entry.getValue().toString())<0){
                bedNo.add(entry.getValue().toString());
                this.memberCheckinInfoMapper.updateMemberCheckinInfo(update);
            }else{
                throw new BusinessException("床号" + entry.getValue().toString() + "已存在，请确认");
            }
        }

    }

    private void addBedHandler(UpdateDepartmentAndBedDTO updateDepartmentAndBedDTO){
        if(StringUtils.isBlank(updateDepartmentAndBedDTO.getAddBedData())){
            return;
        }
        List<DepartmentPO> departmentList = this.departmentMapper.listDepartmentById(Collections.singletonList(updateDepartmentAndBedDTO.getDepartmentId()));
        if(departmentList == null || departmentList.isEmpty()){
            throw new BusinessException("科室不存在");
        }
        String departmentName = departmentList.get(0).getDepartmentName();
        List<String> addBedData = JSON.parseArray(updateDepartmentAndBedDTO.getAddBedData() ,String.class);
        MemberCheckinInfoPO add = null;
        for(String bedNo : addBedData){
            add = this.memberCheckinInfoMapper.getMemberCheckinInfoByBedNo(updateDepartmentAndBedDTO.getDepartmentId() ,bedNo);
            if(add != null){
                throw new BusinessException("床号" + bedNo + "已存在，请确认");
            }
            add = new MemberCheckinInfoPO();
            add.setSid(DaoHelper.getSeq());
            add.setBedNo(bedNo);
            add.setMemberId(Constant.DEFAULT_FOREIGN_ID);
            add.setCheckinStatus(MemberCheckinInfoConstant.CHECKIN_STATUS_NO);
            add.setBedId(Constant.DEFAULT_FOREIGN_ID);
            add.setDepartmentId(updateDepartmentAndBedDTO.getDepartmentId());
            add.setDepartmentName(departmentName);
            add.setHospitalId(updateDepartmentAndBedDTO.getHospitalId());
            add.setRoomId(Constant.DEFAULT_FOREIGN_ID);
            add.setRoomNo(Constant.DEFAULT_FOREIGN_ID);
            this.memberCheckinInfoMapper.addMemberCheckinInfo(add);
        }
    }

    /**
     * 修改医生冗余
     * @param updateDepartment
     */
    private void updateDoctorRedundancy(UpdateDepartmentDTO updateDepartment){
        List<DoctorPO> doctorList = this.doctorService.listDoctorByDepartId(updateDepartment.getDepartmentId());
        if(doctorList == null || doctorList.isEmpty()){
            return;
        }
        UpdateDoctorDTO updateDoctorDTO = null;
        for(DoctorPO doctor : doctorList){
            updateDoctorDTO = new UpdateDoctorDTO();
            updateDoctorDTO.setDoctorId(doctor.getDoctorId());
            updateDoctorDTO.setDepartName(updateDepartment.getDepartmentName());
            this.doctorCacheService.updateDoctor(updateDoctorDTO);
        }
    }
}
