package com.comvee.cdms.doctor.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.department.mapper.DepartmentMapper;
import com.comvee.cdms.department.model.dto.AddDepartmentDTO;
import com.comvee.cdms.department.model.dto.UpdateDepartmentDTO;
import com.comvee.cdms.department.model.po.DepartmentPO;
import com.comvee.cdms.doctor.dto.UpdateDoctorDTO;
import com.comvee.cdms.doctor.mapper.DoctorDepartmentMapper;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorDepartmentPO;
import com.comvee.cdms.doctor.po.DoctorGroupPO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.po.DoctorRelationPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("doctorCacheService")
public class DoctorCacheServiceImpl implements DoctorCacheServiceI {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorDepartmentMapper doctorDepartmentMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    @Cacheable(value = "doctorSource",key = "'docObj_'+#doctorId")
    public DoctorPO getDoctorById(String doctorId) {
        return this.doctorMapper.getDoctorById(doctorId);
    }

    @Override
    @Cacheable(value = "doctorSource",key = "'docObjs_'+#doctorId")
    public List<DoctorPO> listMyDoctor(String doctorId) {
        List<String> idList= this.getTeamIdList(doctorId);
        return this.doctorMapper.listDoctorInId(idList);
    }

    @Override
    @Cacheable(value = "doctorSource",key = "'docStrs_'+#doctorId")
    public List<String> listTeamId(String doctorId) {
        return this.getTeamIdList(doctorId);
    }

    /**
     * 从数据库获取团队编号
     * @param doctorId
     * @return
     */
    private List<String> getTeamIdList(String doctorId) {
        List<String> reList= new ArrayList<>();
        //获取医生权限  医生ID串
        List<DoctorRelationPO> relationPOS = this.doctorMapper.listDoctorRelation(doctorId, null);
        if(null!=relationPOS && relationPOS.size()>0){
            for (int i = 0; i < relationPOS.size(); i++) {
                reList.add(relationPOS.get(i).getForeignId());
            }
        }
        return reList;
    }

    @Override
    @Caching(evict={@CacheEvict(value = "doctorSource", key = "'docObjs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'docStrs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'teamObjs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'docObjs_' + #foreignId"),
            @CacheEvict(value = "doctorSource", key = "'docStrs_' + #foreignId"),
            @CacheEvict(value = "doctorSource", key = "'teamObjs_' + #foreignId")})
    public void addDoctorRelation(String doctorId, String foreignId) {
        List<DoctorRelationPO> list = this.doctorMapper.listDoctorRelation(doctorId, foreignId);
        if(list != null && list.size() > 0){
            throw new BusinessException("已存在的医护关系，请确认");
        }
        DoctorRelationPO doctorRelationPO = new DoctorRelationPO();
        doctorRelationPO.setDoctorId(doctorId);
        doctorRelationPO.setForeignId(foreignId);
        doctorRelationPO.setSid(DaoHelper.getSeq());
        this.doctorMapper.addDoctorRelation(doctorRelationPO);
    }

    @Override
    @Caching(evict={@CacheEvict(value = "doctorSource", key = "'docObjs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'docStrs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'teamObjs_' + #doctorId"),
            @CacheEvict(value = "doctorSource", key = "'docObjs_' + #foreignId"),
            @CacheEvict(value = "doctorSource", key = "'docStrs_' + #foreignId"),
            @CacheEvict(value = "doctorSource", key = "'teamObjs_' + #foreignId")})
    public void deleteDoctorRelation(String doctorId, String foreignId) {
        if (doctorId.equals(foreignId)){
            return;
        }
        this.doctorMapper.deleteDoctorRelation(doctorId, foreignId);
    }

    @Override
    @CacheEvict(value = "doctorSource",key = "'docObj_'+#updateDoctorDTO.doctorId")
    public void updateDoctor(UpdateDoctorDTO updateDoctorDTO){
        this.doctorMapper.updateDoctor(updateDoctorDTO);
    }

    @Override
//    @Cacheable(value = "doctorSource",key = "'teamObjs_'+#doctorId")
    public List<DoctorPO> listMyTeamDoctor(String doctorId) {
        List<DoctorRelationPO> relationPOS = this.doctorMapper.listDoctorRelation(null, doctorId);
        if(relationPOS == null || relationPOS.size() == 0){
            return null;
        }
        List<String> idList = new ArrayList<>();
        relationPOS.forEach(x -> idList.add(x.getDoctorId()));
        return this.doctorMapper.listDoctorInId(idList);
    }

    @Override
    @Cacheable(value = "doctorSource",key = "'docDeparts_'+#doctorId")
    public List<DoctorGroupPO> listDepartmentByDid(String doctorId) {
        return this.doctorMapper.listDepartmentByDid(doctorId);
    }

    @Override
    @CacheEvict(value = "doctorSource",key = "'docDeparts_'+#doctorId")
    public void addDoctorDepartment(DoctorDepartmentPO departmentPO) {
        this.doctorDepartmentMapper.addDoctorDepartment(departmentPO);
    }

    @Override
    @Cacheable(value = "doctorSource",key = "'hosDeparts_'+#hospitalId")
    public List<DoctorGroupPO> listDepartmentByHid(String hospitalId, String doctorId) {
        return this.doctorMapper.listDepartmentByHid(hospitalId,doctorId);
    }

    @Override
    @CacheEvict(value = "doctorSource",key = "'hosDeparts_'+#addDepartmentDTO.hospitalId")
    public String addDepartment(AddDepartmentDTO addDepartmentDTO) {
        String departmentId = DaoHelper.getSeq();
        DepartmentPO departmentPO = new DepartmentPO();
        BeanUtils.copyProperties(departmentPO, addDepartmentDTO);
        departmentPO.setDepartmentId(departmentId);
        this.departmentMapper.addDepartment(departmentPO);
        return departmentId;
    }

    @Override
    @CacheEvict(value = "doctorSource",allEntries = true)
    public void updateDepartment(UpdateDepartmentDTO updateDepartmentDTO) {
        this.departmentMapper.updateDepartment(updateDepartmentDTO);
    }

    @Override
    @CacheEvict(value = "doctorSource",allEntries = true)
    public void deleteDepartment(String departmentId) {
        this.departmentMapper.deleteDepartment(departmentId);
    }

    @Override
    @CacheEvict(value = "doctorSource",key = "'docDeparts_'+#doctorId")
    public void deleteDoctorDepartmentByDoctorId(String doctorId) {
        this.doctorDepartmentMapper.deleteDoctorDepartmentByDoctorId(doctorId);
    }
}
