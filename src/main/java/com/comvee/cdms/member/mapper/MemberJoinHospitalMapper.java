package com.comvee.cdms.member.mapper;

import com.comvee.cdms.app.doctorapp.model.app.H5MemberJoinHospitalVO;
import com.comvee.cdms.member.dto.GetMemberJoinHospitalDTO;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.comvee.cdms.member.vo.ListFollowPlatformMemberVO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.member.vo.MemberJoinStatisticsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberJoinHospitalMapper {

    void addMemberJoinHospital(MemberJoinHospitalPO add);

    int updateMemberJoinHospital(MemberJoinHospitalPO po);

    MemberJoinHospitalPO getMemberJoinHospitalBySid(String sid);

    MemberJoinHospitalPO getMemberJoinHospital(GetMemberJoinHospitalDTO get);

    List<MemberJoinHospitalPO> listMemberJoinHospital(ListMemberJoinHospitalDTO list);

    List<ListFollowPlatformMemberVO> listCommitteeMemberJoinHospitalFollow(ListMemberJoinHospitalDTO list);

    List<MemberJoinHospitalVO> listMemberJoinHospitalV2(ListMemberJoinHospitalDTO list);

    List<MemberJoinHospitalVO> listMemberJoinHospitalForFresh(Map<String, Object> param);

    List<MemberJoinHospitalVO> listGluMemberJoinHospital(Map<String, Object> param);

    List<H5MemberJoinHospitalVO> listMemberJoinHospitalH5(ListMemberJoinHospitalDTO list);

    MemberJoinStatisticsVO memberJoinStatistics(String hospitalId);

    List<String> listAllManageMemberDistinct();

    List<ListFollowPlatformMemberVO> searchJoinHospitalMember(ListMemberJoinHospitalDTO dto);

    List<MemberJoinHospitalVO> listMemberForFresh(Map<String, Object> param);

}
