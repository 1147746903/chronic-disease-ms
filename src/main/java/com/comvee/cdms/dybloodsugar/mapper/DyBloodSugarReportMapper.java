package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyReportDTO;
import com.comvee.cdms.dybloodsugar.po.DyBloodSugarReportPO;

import java.util.List;

public interface DyBloodSugarReportMapper {

    void addReport(DyBloodSugarReportPO po);

    List<DyBloodSugarReportPO> getReportList(DyReportDTO dto);

    List<DyBloodSugarReportPO> getReportListByMember(DyReportDTO dto);

    DyBloodSugarReportPO getReportById(String id);

    void updateReport(DyBloodSugarReportPO po);

    void setReportValid(String sid);

    void setReportNotValid(String sid);
}
