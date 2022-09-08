package com.comvee.cdms.level.service;

import com.comvee.cdms.level.dto.AccessDiabetesLevelDTO;
import com.comvee.cdms.level.dto.AddDiabetesLevelDTO;
import com.comvee.cdms.level.vo.AccessDiabetesLevelResultVO;
import com.comvee.cdms.level.vo.DiabetesLevelAssessDataVO;

public interface MemberDiabetesLevelService {

    DiabetesLevelAssessDataVO getMemberDiabetesLevelAssessData(String memberId);

    AccessDiabetesLevelResultVO accessDiabetesLevel(AccessDiabetesLevelDTO dto);

    String addDiabetesLevel(AddDiabetesLevelDTO add);
}
