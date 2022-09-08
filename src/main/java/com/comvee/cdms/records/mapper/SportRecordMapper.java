package com.comvee.cdms.records.mapper;

import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.SportPO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportRecordMapper {
    void addSportRecord(SportRecordPO sportRecordPO);
    void updateSportRecord(SportRecordPO sportRecordPO);
    List<SportPO> listSport(SportRecordDTO sportRecordDTO);
    SportPO getSportById(String sid);
    void deleteSportRecord(SportRecordDTO dto);
    List<SportRecordPO> listRecentSportRecord(SportRecordDTO sportRecordDTO);
    List<SportRecordPO> listSportRecord(SportRecordDTO sportRecordDTO);
}
