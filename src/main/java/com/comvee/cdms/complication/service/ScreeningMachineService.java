package com.comvee.cdms.complication.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.dto.ScreeningMachineDTO;
import com.comvee.cdms.complication.model.po.ScreeningMachinePO;
import com.comvee.cdms.complication.model.vo.ScreeningMachineVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningMachineService {

    /**
     * 添加筛查设备绑定记录
     * @param dto
     */
    void addScreeningMachine(ScreeningMachineDTO dto,String sid);

    /**
     * 查找筛查设备绑定记录
     * @param machineSn
     * @param secretKey
     * @return
     */
    ScreeningMachinePO getScreeningMachine(String machineSn , String secretKey);

    /**
     * 根据密钥获取医生信息
     * @param secretKey
     * @return
     */
    DoctorSessionBO getDoctorBySecretKey(String secretKey);

    /**
     * 根据医院、医生、机器码进行模糊搜索
     * @param pr
     * @param dto
     * @return
     */
    PageResult<ScreeningMachineVO> listHospitalNameOrDoctorIdOrEquipmentNo(PageRequest pr, ScreeningMachineDTO dto);
}
