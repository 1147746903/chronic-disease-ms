package com.comvee.cdms.dybloodsugar.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.DyBloodManagementDTO;
import com.comvee.cdms.dybloodsugar.po.DyBloodManagementPO;

import java.util.List;

public interface DyBloodManagementService {

    PageResult<DyBloodManagementPO> listHospitalNameAndEquipmentNo(PageRequest pr , DyBloodManagementDTO dto);

    Result addHospitalNameAndEquipmentNo(DyBloodManagementDTO dto);

    DyBloodManagementPO getManagementBySid(String sid);

    void deleteManagementBySid(String sid);

    List<DyBloodManagementPO> listHospital();

}
