package com.comvee.cdms.insulinpump.service;

import com.comvee.cdms.clinicaldiagnosis.vo.MemberYzListVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpDayUsageVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpRecordVO;
import com.comvee.cdms.insulinpump.model.vo.InsulinPumpUsageVO;

import java.util.List;

public interface InsulinPumpService {

    /**
     * 获取胰岛素泵使用情况
     * @param memberId
     * @param hospitalNo
     * @return
     */
    InsulinPumpUsageVO getInsulinPumpUsage(String memberId ,String hospitalNo);

    /**
     * 每日使用情况处理
     * @param yzList
     * @return
     */
    List<InsulinPumpDayUsageVO> dayUsageListHandler(List<MemberYzListVO> yzList);

    /**
     * 获取患者住院期间的所有医嘱
     * @param memberId
     * @param hospitalId
     * @return
     */
    List<InsulinPumpRecordVO> listInsulinPumpRecord(String memberId, String hospitalId);
}
