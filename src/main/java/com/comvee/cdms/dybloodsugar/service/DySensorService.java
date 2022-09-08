package com.comvee.cdms.dybloodsugar.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.dto.ListDySensorDTO;
import com.comvee.cdms.dybloodsugar.po.DySensorDoctorPO;
import com.comvee.cdms.dybloodsugar.po.DySensorPO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface DySensorService {

    void saveDySensor(DySensorPO dySensorPO);
    void deleteDySensorById(String sid);
    DySensorPO getDySensorById(String sid);
    PageResult<DySensorPO> listDySensor(ListDySensorDTO listDySensorDTO, PageRequest pr);
    DySensorPO createDySensorQrCode(String sid);
    void exportDySensorQrCode(ListDySensorDTO listDySensorDTO, OutputStream output) throws IOException;
    List<DySensorDoctorPO> listDySensorDoctor();
    void saveDySensorDoctor(DySensorDoctorPO sensorDoctorPO);
    void deleteDySensorDoctorById(String sid);
}
