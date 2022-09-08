package com.comvee.cdms.healthrecord.mapper;

import com.comvee.cdms.healthrecord.model.dto.CountHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.dto.CountHealthRecordDistinctDTO;
import com.comvee.cdms.healthrecord.model.dto.ListHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordDetailPO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordPO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordWithMemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthRecordMapper {

    List<HealthRecordWithMemberVO> listHealthRecord(ListHealthRecordDTO dto);

    List<HealthRecordDetailPO> listHealthRecordDetail(@Param("pid") String pid);

    Long countHealthRecord(CountHealthRecordDTO dto);

    Long countHealthRecordDistinctPeople(CountHealthRecordDistinctDTO dto);

    HealthRecordDetailPO getLastConstitutionHealthRecordDetail(String memberId);

    int updateHealthRecord(HealthRecordPO healthRecordPO);

    HealthRecordPO getHealthRecord(HealthRecordPO healthRecordPO);
}
