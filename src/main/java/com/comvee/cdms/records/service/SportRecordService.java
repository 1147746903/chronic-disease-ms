package com.comvee.cdms.records.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.dybloodsugar.po.DyRememberSportPO;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.SportPO;
import com.comvee.cdms.records.model.po.SportRecordPO;

import java.util.List;

public interface SportRecordService {
    List<SportPO> listSport(SportRecordDTO sportRecordDTO);

    void deleteSportRecordById(String sid);

    void saveSportRecord(SportRecordPO sportRecordPO);

    void saveSportRecords(List<SportRecordPO> sports);

    List<SportRecordPO> listRecentSportRecord(SportRecordDTO sportRecordDTO);

    List<SportRecordPO> listSportRecord(SportRecordDTO sportRecordDTO);

    List<DyRememberSportPO> listSportRecordForDyBlood(SportRecordDTO dto);

    JSONObject getDailySportInfo(SportRecordDTO dto, String weight);
}
