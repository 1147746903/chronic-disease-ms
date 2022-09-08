package com.comvee.cdms.clinic.service;

import com.comvee.cdms.clinic.dto.AddClinicRecordDTO;
import com.comvee.cdms.clinic.po.ClinicRecordPO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

/**
 * @author wyc
 * @date 2019/11/29 11:14
 */
public interface ClinicRecordService {

    /**
     * 添加筛查记录
     * v4.6.0
     * @param clinicRecordDTO
     */
    void addClinicRecord(AddClinicRecordDTO clinicRecordDTO);

    /**
     * 查询临床检查下的对应检查数据的记录
     * @param clinicId
     * @param clinicType
     * @return
     */
    PageResult<ClinicRecordPO> getClinicRecordByClinicIdAndType(PageRequest page, String clinicId, Integer clinicType);
}
