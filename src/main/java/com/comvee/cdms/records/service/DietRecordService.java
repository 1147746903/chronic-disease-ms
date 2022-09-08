package com.comvee.cdms.records.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.dybloodsugar.po.DyRememberDietPO;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;

import java.util.List;

public interface DietRecordService {
    void addDietRecord(DietRecordPO dietRecordPO);
    void saveDietRecords(List<DietRecordPO> recordList);
    void modifyDietRecord(DietRecordPO dietRecordPO);
    boolean hasFoodItem(String sid);

    DietRecordPO getDietRecordById(String sid);

    List<DyRememberDietPO> listDietRecordByDayForMiniProgram(DietRecordDTO dietRecordDTO);

    List<DyRememberDietPO> listDietRecordByDayForDyBlood(DietRecordDTO dietRecordDTO);

    List<DietRecordPO> listDietRecord(DietRecordDTO dietRecordDTO);

    void deleteDietRecordByMainId(String recordMainId);

    JSONObject getDailyDietInfo(DietRecordDTO dto);

    String getMainId(DietRecordDTO dto);
}
