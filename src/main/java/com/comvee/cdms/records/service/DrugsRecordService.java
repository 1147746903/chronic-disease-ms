package com.comvee.cdms.records.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.records.model.dto.DrugsRecordDTO;
import com.comvee.cdms.records.model.po.DrugsRecordPO;
import com.comvee.cdms.records.model.vo.DrugsRecordVO;

import java.util.List;

public interface DrugsRecordService {

    void saveDrugsRecords(List<DrugsRecordPO>  records);
    void saveDrugsRecord(DrugsRecordPO record);
    List<DrugsDepotPO> getDrugsByName(String drugName);

    void deleteDrugsRecord(DrugsRecordDTO dto);

    List<DrugsRecordPO> listRecentDrugsRecord(DrugsRecordDTO dto);

    List<DrugsRecordVO> listDrugsRecord(DrugsRecordDTO dto);

    DrugsRecordPO drugsJsonHandler(JSONObject obj);

    JSONObject getDailyDrugInfo(DrugsRecordDTO dto);
}
