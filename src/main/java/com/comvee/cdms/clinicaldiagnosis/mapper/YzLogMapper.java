package com.comvee.cdms.clinicaldiagnosis.mapper;

import com.comvee.cdms.clinicaldiagnosis.po.YzLogPO;
import org.apache.ibatis.annotations.Param;

public interface YzLogMapper {

    /**
     * 根据医嘱id获取执行记录
     * @param yzId
     * @return
     */
    YzLogPO getYzLogByYzId(@Param("yzId") String yzId);

    /**
     * 新增执行记录
     * @param yzLog
     */
    void addYzLog(YzLogPO yzLog);

    /**
     * 修改执行记录
     * @param yzLog
     * @return
     */
    int updateYzLog(YzLogPO yzLog);
}