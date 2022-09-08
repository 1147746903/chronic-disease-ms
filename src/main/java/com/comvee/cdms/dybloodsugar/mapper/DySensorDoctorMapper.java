package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DySensorDoctorPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DySensorDoctorMapper {
    void addDySensorDoctor(DySensorDoctorPO sensorDoctorPO);
    void updateDySensorDoctor(DySensorDoctorPO sensorDoctorPO);
    void deleteDySensorDoctorById(String id);
    boolean hasDySensorDoctor(DySensorDoctorPO sensorDoctorPO);
    List<DySensorDoctorPO> listDySensorDoctor();
}
