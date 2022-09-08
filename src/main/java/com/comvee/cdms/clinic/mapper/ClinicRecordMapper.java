/*
*
* @author wyc
* @date 2019-11-29
*/
package com.comvee.cdms.clinic.mapper;


import com.comvee.cdms.clinic.dto.DelClinicRecordDTO;
import com.comvee.cdms.clinic.po.ClinicRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyc
 * @date 2019/11/20 11:16
 */
public interface ClinicRecordMapper {

    /**
     * 添加筛查记录
     * @param clinicRecordPO
     */
    void addClinicRecord(ClinicRecordPO clinicRecordPO);

    /**
     * 查询临床检查下的对应检查数据的记录
     * @param clinicId
     * @param clinicType
     * @return
     */
    List<ClinicRecordPO> getClinicRecordByClinicIdAndType(@Param("clinicId") String clinicId, @Param("clinicType") Integer clinicType);

    /**
     * 删除检查记录
     */
    void delClinicRecord(DelClinicRecordDTO recordDTO);

    /**
     * 根据筛查id删除对应的记录信息
     * @param clinicId
     */
    void delClinicRecordByClinicId(@Param("clinicId") String clinicId);
}