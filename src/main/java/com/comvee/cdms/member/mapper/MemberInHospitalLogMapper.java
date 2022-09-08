package com.comvee.cdms.member.mapper;

import com.comvee.cdms.member.dto.ListInHospitalOrVirtualWardHasBloodSugarDataDTO;
import com.comvee.cdms.member.po.MemberInHospitalLogPO;
import com.comvee.cdms.member.vo.InHospitalMemberInfoWithBloodSugarVO;
import com.comvee.cdms.member.vo.ListInHospitalLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberInHospitalLogMapper {

    void addMemberInHospitalLog(MemberInHospitalLogPO logPO);

    int updateMemberInHospitalLog(MemberInHospitalLogPO logPO);

    MemberInHospitalLogPO getMemberInHospitalLogPOByAdmNo(@Param("admNo") String admNo);

    List<MemberInHospitalLogPO> getMemberInHospitalLogByMemberId(@Param("memberId") String memberId, @Param("hospitalId")String hospitalId,@Param("departmentId")String departmentId);

    MemberInHospitalLogPO getMemberInHospitalLogByTime(@Param("memberId")String memberId, @Param("hospitalId")String hospitalId, @Param("startDt")String startDt, @Param("endDt")String endDt);

    List<ListInHospitalLogVO> listMembersHospitalRecord(@Param("memberId") String memberId);

    MemberInHospitalLogPO getMemberInHospitalLog(MemberInHospitalLogPO logPO);

    List<InHospitalMemberInfoWithBloodSugarVO> listInHospitalOrVirtualWardHasBloodSugarData(ListInHospitalOrVirtualWardHasBloodSugarDataDTO dto);

}
