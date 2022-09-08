package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.po.DoctorPushTokenPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: suyz
 * @date: 2019/1/4
 */
public interface DoctorPushTokenMapper {

    /**
     * 获取token数据
     * @param doctorId
     * @param pushToken
     * @return
     */
    DoctorPushTokenPO getDoctorPushToken(@Param("doctorId") String doctorId,@Param("pushToken") String pushToken);

    /**
     * 新增token记录
     * @param doctorPushTokenPO
     */
    void addDoctorPushToken(DoctorPushTokenPO doctorPushTokenPO);

    /**
     * 删除token记录
     * @param sid
     */
    void deleteDoctorPushTokenById(@Param("sid") String sid);

    /**
     * 修改token记录
     * @param doctorPushTokenPO
     */
    void updateDoctorPushToken(DoctorPushTokenPO doctorPushTokenPO);
}
