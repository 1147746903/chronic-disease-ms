package com.comvee.cdms.clinic.mapper;

import com.comvee.cdms.clinic.dto.GetClinicInspectDTO;
import com.comvee.cdms.clinic.dto.ListClinicInspectDTO;
import com.comvee.cdms.clinic.po.ClinicInspectPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/8/12 10:16
 */
public interface ClinicInspectMapper {
    /**
     * 获取临床检查列表
     * @return
     */
    List<ClinicInspectPO> listClinicInspect(ListClinicInspectDTO listClinicInspectDTO);

    /**
     * 根据临床检查id获取检查详情
     * @param sid
     * @return
     */
    ClinicInspectPO getClinicInspectById(@Param("sid") String sid);

    /**
     * 添加临床检查
     * @param clinicInspectPO
     */
    void addClinicInspect(ClinicInspectPO clinicInspectPO);

    /**
     * 修改临床检查信息
     * @param clinicInspectPO
     */
    void modifyClinicInspect(ClinicInspectPO clinicInspectPO);

    /**
     * 根据id删除临床检查信息
     * @param sid
     */
    void deleteClinicInspect(@Param("sid") String sid);

    /**
     * 获取患者最新的临床检查信息
     * @param getClinicInspectDTO
     * @return
     */
    ClinicInspectPO getNewClinicInspect(GetClinicInspectDTO getClinicInspectDTO);


    /**
     * 根据参数获取患者的临床检查信息
     * @param listClinicInspectDTO
     * @return
     */
    List<ClinicInspectPO> listClinicInspectByParam(ListClinicInspectDTO listClinicInspectDTO);
}
