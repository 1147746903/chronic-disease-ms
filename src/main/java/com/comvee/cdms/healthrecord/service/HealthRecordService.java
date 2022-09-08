package com.comvee.cdms.healthrecord.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.healthrecord.model.dto.ListHealthRecordDTO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordDetailPO;
import com.comvee.cdms.healthrecord.model.po.HealthRecordPO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordStatsVO;
import com.comvee.cdms.healthrecord.model.vo.HealthRecordWithMemberVO;

import java.util.Map;

public interface HealthRecordService {

    PageResult<HealthRecordWithMemberVO> listHealthRecord(ListHealthRecordDTO dto , PageRequest pr);

    HealthRecordStatsVO getHealthRecordStats(String hospitalId);

    Map<String ,Object> getHealthRecordDetail(String id);

    JSONObject getLastConstitution(String memberId);

    Boolean updateHealthRecord(HealthRecordPO healthRecordPO);
}
