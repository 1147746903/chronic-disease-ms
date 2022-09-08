package com.comvee.cdms.records.mapper;

import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugsRecordMapper {
    void addDrugsRecord(DrugsRecordPO drugsRecordPO);

    void updateDrugsRecord(DrugsRecordPO drugsRecordPO);

    void deleteDrugsRecord(DrugsRecordDTO dto);

    List<DrugsDepotPO> getDrugsByName(String drugsName);

    DrugsRecordPO getDrugsById(String sid);

    List<DrugsRecordPO> listRecentDrugsRecord(DrugsRecordDTO dto);

    List<DrugsRecordPO> listDrugsRecord(DrugsRecordDTO dto);
}
