package com.comvee.cdms.insulinpump.mapper;

import com.comvee.cdms.insulinpump.model.po.InsulinPumpNurseRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsulinPumpNurseRecordMapper {

    /**
     * 新增护理记录
     * @param i
     * @return
     */
    int addInsulinPumpNurseRecord(InsulinPumpNurseRecordPO i);

    /**
     * 修改护理记录
     * @param i
     * @return
     */
    int updateInsulinPumpNurseRecord(InsulinPumpNurseRecordPO i);

    /**
     * 根据id获取护理记录
     * @param sid
     * @return
     */
    InsulinPumpNurseRecordPO getInsulinPumpNurseRecordById(@Param("sid") String sid);

    /**
     * 加载护理记录列表
     * @param memberId
     * @param hospitalNo
     * @return
     */
    List<InsulinPumpNurseRecordPO> listInsulinPumpNurseRecord(@Param("memberId") String memberId ,@Param("virtualWardId") String virtualWardId);
}