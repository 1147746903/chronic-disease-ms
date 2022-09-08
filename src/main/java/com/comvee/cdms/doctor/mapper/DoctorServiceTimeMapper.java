package com.comvee.cdms.doctor.mapper;

import com.comvee.cdms.doctor.po.DoctorServiceRemindPO;
import com.comvee.cdms.doctor.po.DoctorServiceTimePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/8
 */
public interface DoctorServiceTimeMapper {

    /**
     * 加载医生服务时间设置
     * @param doctorId
     * @return
     */
    List<DoctorServiceTimePO> listDoctorServiceTime(@Param("doctorId") String doctorId,@Param("weekCode") String weekCode,@Param("openStatus") Integer openStatus);

    /**
     * 新增医生服务时间设置
     * @param doctorServiceTimePO
     */
    void addDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO);

    /**
     * 删除医生服务时间设置
     * @param sid
     */
    void deleteDoctorServiceTime(@Param("sid") String sid);

    /**
     * 修改医生服务时间设置
     * @param doctorServiceTimePO
     */
    void updateDoctorServiceTime(DoctorServiceTimePO doctorServiceTimePO);

    /** v4.0.4
     * 根据id获取医生服务时间详细信息
     * @param sid
     * @return
     */
    DoctorServiceTimePO getDoctorServiceTimeById(@Param("sid")String sid);

    /** v4.0.4
     * 添加医生服务时间外提示信息
     * @param doctorServiceRemindPO
     */
    void insertDoctorServiceRemind(DoctorServiceRemindPO doctorServiceRemindPO);

    /** v4.0.4
     * 修改医生服务时间外提示信息
     * @param doctorServiceRemindPO
     */
    void modifyDoctorServiceRemind(DoctorServiceRemindPO doctorServiceRemindPO);

    /** v4.0.4
     * 通过医生id获取医生服务时间外提示信息
     * @param doctorId
     * @return
     */
    DoctorServiceRemindPO getDoctorServiceRemindByDoctorId(@Param("doctorId")String doctorId,@Param("openStatus") Integer openStatus);

}
