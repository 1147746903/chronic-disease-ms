package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.po.DoctorPushSetPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public interface DoctorPushMapper {

    /**
     * 获取医生推送设置
     * @param doctorId
     * @return
     */
    DoctorPushSetPO getDoctorPushSet(@Param("doctorId") String doctorId);

    /**
     * 修改医生推送设置
     * @param doctorPushSetPO
     */
    void addDoctorPushSet(DoctorPushSetPO doctorPushSetPO);
}
