package com.comvee.cdms.records.mapper;

import com.comvee.cdms.dybloodsugar.po.DyRememberFoodPO;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietRecordMapper {
    void addDietRecord(DietRecordPO dietRecordPO);
    void modifyDietRecord(DietRecordPO dietRecordPO);
    DietRecordPO getDietRecordById(String sid);
    DyRememberFoodPO getFoodById(String sid);
    List<DietRecordPO> listDietRecord(DietRecordDTO dietRecordDTO);
    void deleteDietRecord(DietRecordDTO dietRecordDTO);
    String getEatingTime(DietRecordDTO dto);
    List<String> listRecordDt(DietRecordDTO dto);
    String getMainId(DietRecordDTO dto);
}
