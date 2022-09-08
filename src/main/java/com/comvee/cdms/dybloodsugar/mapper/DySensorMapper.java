package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.ListDySensorDTO;
import com.comvee.cdms.dybloodsugar.po.DySensorPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DySensorMapper {
    void addDySensor(DySensorPO dySensorPO);
    void updateDySensor(DySensorPO dySensorPO);
    void deleteDySensorById(String sid);
    DySensorPO getDySensorById(String sid);
    List<DySensorPO> listDySensor(ListDySensorDTO listDySensorDTO);
    boolean hasDySensor(String sensorNo);
}
