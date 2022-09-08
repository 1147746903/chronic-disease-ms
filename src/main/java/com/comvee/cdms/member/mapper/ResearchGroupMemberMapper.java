package com.comvee.cdms.member.mapper;

import com.comvee.cdms.member.dto.ListResearchGroupMemberDTO;
import com.comvee.cdms.member.po.ResearchGroupMemberPO;
import com.comvee.cdms.member.vo.ResearchGroupMemberListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResearchGroupMemberMapper {

    void batchAddResearchGroupMember(@Param("list") List<ResearchGroupMemberPO> list);

    void batchDeleteResearchGroupMember(@Param("groupId") String groupId);

    List<ResearchGroupMemberListVO> listResearchGroupMember(ListResearchGroupMemberDTO dto);

    ResearchGroupMemberPO getResearchGroupMember(@Param("sid") String sid);

    int updateResearchGroupMember(ResearchGroupMemberPO update);

    List<ResearchGroupMemberPO> listResearchGroupMemberByGroupId(@Param("groupId") String groupId);

    int deleteResearchGroupMember(@Param("groupId") String groupId ,@Param("memberIdList") List<String> memberIdList);

    long countMemberInHospitalResearchGroup(@Param("memberId")String memberId ,@Param("hospitalId")String hospitalId);
}
