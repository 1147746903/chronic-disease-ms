package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyBloodManagementDTO;
import com.comvee.cdms.dybloodsugar.po.DyBloodManagementPO;
import com.comvee.cdms.statistics.dto.HospitalDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyBloodManagementMapper {

    void addHospitalNameAndEquipmentNo(DyBloodManagementPO dyBloodManagementPO);

    /**
     * 获取设备绑定记录(模糊查询)
     * @param dto
     * @return
     */
    List<DyBloodManagementPO> getBloodManagementByHospitalIdAndEquipment(DyBloodManagementDTO dto);

    /**
     * 修改设备绑定
     * @param managementPO
     */
    void updateBloodManagement(DyBloodManagementPO managementPO);

    /**
     * 获取设备绑定记录
     * @param dto
     * @return
     */
    DyBloodManagementPO getBloodManagement(DyBloodManagementDTO dto);

    /**
     * 返回绑定数据
     * @param sid
     * @return
     */
    DyBloodManagementPO getManagementBySid(@Param("sid") String sid);

    /**
     * 删除绑定数据
     * @param sid
     */
    void deleteManagementBySid(@Param("sid") String sid);

    /**
     * 获取全部的医院
     * @return
     */
    List<DyBloodManagementPO> listHospitals();

    /**
     * 获取动态血糖管理
     * @param innerCode
     * @return
     */
    DyBloodManagementPO getDyBloodManagementPO(String innerCode);

    Long countAllWhiteBoxByHos(HospitalDataDTO dto);


    Long countHandheldDeviceByHos(HospitalDataDTO dto);
}
