package com.comvee.cdms.other.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.other.bo.DoctorPushTokenBO;
import com.comvee.cdms.other.po.DoctorPushCachePO;
import com.comvee.cdms.other.po.DoctorPushSetPO;
import com.comvee.cdms.other.po.DoctorPushTokenPO;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public interface DoctorPushService {

    /**
     * 获取医生推送设置
     * @param doctorId
     * @return
     */
    DoctorPushSetPO getDoctorPushSet(String doctorId);

    /**
     * 修改医生推送设置
     * @param doctorPushSetPO
     */
    void updateDoctorPushSet(DoctorPushSetPO doctorPushSetPO);

    /**
     * 新增推送token
     * @param doctorPushTokenBO
     */
    void addDoctorPushToken(DoctorPushTokenBO doctorPushTokenBO);

    /**
     * 新增推送缓存记录
     * @param doctorPushCachePO
     */
    void addDoctorPushCache(DoctorPushCachePO doctorPushCachePO);

    /**
     * 加载待推送列表
     * @param pr
     * @param sendStatus
     * @return
     */
    PageResult<DoctorPushCachePO> listDoctorPushCache(PageRequest pr,Integer sendStatus);

    /**
     * 获取推送token
     * @param doctorId
     * @return
     */
    DoctorPushTokenPO getDoctorPushToken(String doctorId);

    /**
     * 修改推送记录
     * @param doctorPushCachePO
     */
    void updateDoctorPushCache(DoctorPushCachePO doctorPushCachePO);

    /**
     * 根据医生id删除token
     * @param doctorId
     */
    void deleteDoctorPushTokenByDoctorId(String doctorId);

    /**
     * 移动过期推送缓存
     */
    void moveExpireDoctorPushCache();
}
