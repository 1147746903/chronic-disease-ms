package com.comvee.cdms.clinic.service;

import com.comvee.cdms.clinic.dto.AddClinicInspectDTO;
import com.comvee.cdms.clinic.dto.GetClinicInspectDTO;
import com.comvee.cdms.clinic.dto.ListClinicInspectDTO;
import com.comvee.cdms.clinic.dto.UpdateClinicInspectDTO;
import com.comvee.cdms.clinic.po.ClinicInspectPO;
import com.comvee.cdms.clinic.vo.ClinicInspectVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

/**
 * @author wyc
 * @date 2019/8/12 10:24
 */
public interface ClinicInspectService {

    /**
     * 获取临床检查列表
     * @return
     */
    PageResult<ClinicInspectVO> listClinicInspect(PageRequest page, ListClinicInspectDTO listClinicInspectDTO);

    /**
     * 根据临床检查id获取检查详情
     * @param sid
     * @return
     */
    ClinicInspectPO getClinicInspectById( String sid);

    /**
     * 添加临床检查
     * @param clinicInspectPO
     */
    String addClinicInspect(AddClinicInspectDTO clinicInspectDTO);

    /**
     * 修改临床检查信息
     * @param clinicInspectPO
     */
    void modifyClinicInspect(UpdateClinicInspectDTO inspectDTO);

    /**
     * 根据id删除临床检查信息
     * @param sid
     */
    void deleteClinicInspect( String sid);

    /**
     * 获取患者最新的临床检查信息
     * @param getClinicInspectDTO
     * @return
     */
    ClinicInspectPO getNewClinicInspect(GetClinicInspectDTO getClinicInspectDTO);

}
