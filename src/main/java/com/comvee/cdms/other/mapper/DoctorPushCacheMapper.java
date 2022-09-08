package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.po.DoctorPushCachePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/4
 */
public interface DoctorPushCacheMapper {

    /**
     * 添加推送记录
     * @param doctorPushCachePO
     */
    void addDoctorPushCache(DoctorPushCachePO doctorPushCachePO);

    /**
     * 加载推送记录
     * @param sendStatus
     * @return
     */
    List<DoctorPushCachePO> listDoctorPushCache(@Param("sendStatus") Integer sendStatus);

    /**
     * 修改推送记录
     * @param doctorPushCachePO
     */
    void updateDoctorPushCache(DoctorPushCachePO doctorPushCachePO);

    /**
     * 新增推送缓存历史
     */
    void addDoctorPushCacheHistory();

    /**
     * 删除过期的推送缓存
     */
    void deleteExpireDoctorPushCache();
}
