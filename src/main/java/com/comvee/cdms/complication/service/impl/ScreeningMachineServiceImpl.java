package com.comvee.cdms.complication.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.mapper.ScreeningMachineMapper;
import com.comvee.cdms.complication.model.dto.ScreeningMachineDTO;
import com.comvee.cdms.complication.model.po.ScreeningMachinePO;
import com.comvee.cdms.complication.model.vo.ScreeningMachineVO;
import com.comvee.cdms.complication.service.ScreeningMachineService;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.hospital.service.HospitalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
@Service("screeningMachineService")
public class ScreeningMachineServiceImpl implements ScreeningMachineService {

    @Autowired
    private ScreeningMachineMapper screeningMachineMapper;

    @Autowired
    private DoctorServiceI doctorService;

    @Autowired
    private HospitalService hospitalService;

    @Override
    public void addScreeningMachine(ScreeningMachineDTO dto,String sid) {
        if (sid != null && !"".equals(sid)){
            //修改操作
            ScreeningMachinePO screeningMachinePO = this.screeningMachineMapper.getScreeningMachineBySid(sid);
            if (screeningMachinePO != null){
                ScreeningMachineVO screeningMachineVO = new ScreeningMachineVO();
                screeningMachineVO.setSid(sid);
                screeningMachineVO.setDoctorId(dto.getDoctorId());
                screeningMachineVO.setDoctorName(dto.getDoctorName());
                screeningMachineVO.setDescription(dto.getDescription());
                this.screeningMachineMapper.updateScreeningMachine(screeningMachineVO);
            }

        }else{
            //保存操作
            ScreeningMachinePO screeningMachinePO = getScreeningMachine(dto.getMachineSn() , null);
            if (screeningMachinePO != null){
                throw new BusinessException("该机器码已经被绑定!");
            }else{
                String secretKey = UUID.randomUUID().toString();
                String id = DaoHelper.getSeq();
                screeningMachinePO = new ScreeningMachinePO();
                screeningMachinePO.setSid(id);
                screeningMachinePO.setMachineSn(dto.getMachineSn());
                screeningMachinePO.setHospitalId(dto.getHospitalId());
                screeningMachinePO.setDoctorId(dto.getDoctorId());
                screeningMachinePO.setSecretKey(secretKey);
                screeningMachinePO.setHospitalName(dto.getHospitalName());
                screeningMachinePO.setDoctorName(dto.getDoctorName());
                screeningMachinePO.setDescription(dto.getDescription());
                this.screeningMachineMapper.addScreeningMachine(screeningMachinePO);
            }
        }

    }


    @Override
    public ScreeningMachinePO getScreeningMachine(String machineSn, String secretKey) {
        return this.screeningMachineMapper.getScreeningMachine(machineSn ,secretKey);
    }

    @Override
    @Cacheable(value = "session" ,key = "#secretKey" ,unless="#result == null")
    public DoctorSessionBO getDoctorBySecretKey(String secretKey) {
        ScreeningMachinePO screeningMachinePO = getScreeningMachine(null ,secretKey);
        if(screeningMachinePO == null){
            return null;
        }
        DoctorPO doctorPO = this.doctorService.getDoctorById(screeningMachinePO.getDoctorId());
        if(doctorPO == null){
            return null;
        }
        DoctorSessionBO doctorSessionBO = new DoctorSessionBO();
        BeanUtils.copyProperties(doctorSessionBO ,doctorPO);
        return doctorSessionBO;
    }

    @Override
    public PageResult<ScreeningMachineVO> listHospitalNameOrDoctorIdOrEquipmentNo(PageRequest pr, ScreeningMachineDTO dto) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<ScreeningMachinePO> dyBloodManagementPOS = this.screeningMachineMapper.listHospitalNameOrDoctorIdOrEquipmentNo(dto);
        PageResult<ScreeningMachinePO> poPageResult = new PageResult<>(dyBloodManagementPOS);
        PageResult<ScreeningMachineVO> pageResult = new PageResult<>();
        if (poPageResult.getRows() != null && poPageResult.getRows().size()>0){
            //处理返回的数据
            List<ScreeningMachineVO> screeningMachineVOS = new ArrayList<>(poPageResult.getRows().size());
            for (ScreeningMachinePO screeningMachinePO : poPageResult.getRows()){
                ScreeningMachineVO screeningMachineVO = new ScreeningMachineVO();
                screeningMachineVO.setSid(screeningMachinePO.getSid());
                screeningMachineVO.setHospitalName(screeningMachinePO.getHospitalName());
                screeningMachineVO.setDoctorName(screeningMachinePO.getDoctorName());
                screeningMachineVO.setMachineSn(screeningMachinePO.getMachineSn());
                screeningMachineVO.setDescription(screeningMachinePO.getDescription());
                screeningMachineVO.setDoctorId(screeningMachinePO.getDoctorId());
                screeningMachineVO.setHospitalId(screeningMachinePO.getHospitalId());
                screeningMachineVOS.add(screeningMachineVO);
            }
            pageResult.setTotalRows(poPageResult.getTotalRows());
            pageResult.setTotalPages(poPageResult.getTotalPages());
            pageResult.setPageNum(poPageResult.getPageNum());
            pageResult.setPageSize(poPageResult.getPageSize());
            pageResult.setRows(screeningMachineVOS);
        }
        return pageResult;
    }

}
