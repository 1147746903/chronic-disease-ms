package com.comvee.cdms.member.mapper;

import com.comvee.cdms.member.dto.ListResearchGroupDTO;
import com.comvee.cdms.member.po.ResearchGroupPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResearchGroupMapper {

    void addResearchGroup(ResearchGroupPO add);

    int updateResearchGroup(ResearchGroupPO update);

    List<ResearchGroupPO> listResearchGroup(ListResearchGroupDTO dto);

    ResearchGroupPO getResearchGroup(@Param("groupId") String groupId);
}
