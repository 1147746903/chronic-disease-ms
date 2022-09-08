package com.comvee.cdms.member.service;

import com.comvee.cdms.app.doctorapp.model.app.H5MemberJoinHospitalVO;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.dto.AddMemberDTO;
import com.comvee.cdms.member.dto.ListMemberJoinHospitalDTO;
import com.comvee.cdms.member.dto.ListMemberVisitDTO;
import com.comvee.cdms.member.po.MemberJoinHospitalPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.vo.ListFollowPlatformMemberVO;
import com.comvee.cdms.member.vo.MemberJoinHospitalVO;
import com.comvee.cdms.member.vo.MemberJoinStatisticsVO;

import java.util.List;

public interface MemberManageService {

    boolean hasMemberJoinHospital(String memberId, String hospitalId);

    MemberJoinHospitalPO getMemberJoinHospital(String memberId, String hospitalId);

    void addMemberJoinHospital(MemberJoinHospitalPO po);

    PageResult<MemberJoinHospitalVO> listMemberJoinHospital(ListMemberJoinHospitalDTO dto, PageRequest pr);

    PageResult<H5MemberJoinHospitalVO> listMemberJoinHospitalH5(ListMemberJoinHospitalDTO dto, PageRequest pr);

    void attentionMember(String[] members, String hospitalId);

    void cancelAttentionMember(String[] members, String hospitalId);

    MemberJoinStatisticsVO memberJoinStatistics(String hospitalId);

    PageResult<String> listAllManageMemberDistinct(PageRequest pr);

    List<H5MemberJoinHospitalVO> listMemberRecentSearchH5(String doctorId, String hospitalId);

    void addMemberToCache(String doctorId, String memberId);

    PageResult<MemberJoinHospitalVO> listMemberVisitRecord(ListMemberVisitDTO dto, PageRequest pr);

    String createMemberHospitalRelation(DoctorSessionBO doctorSessionBO, AddMemberDTO addMemberDTO);

    /**
     * 删除绑定
     * @param memberId
     * @return
     */
    Boolean deleteMemberJoinHospital(String sid);

    List<MemberJoinHospitalPO> listMemberJoinHospital(ListMemberJoinHospitalDTO param);

    PageResult<ListFollowPlatformMemberVO> pagerCommitteeMemberForFollowPlatform(DoctorSessionBO doctorSessionBO, ListMemberJoinHospitalDTO dto,PageRequest page);

    PageResult<ListFollowPlatformMemberVO> searchJoinHospitalMember(ListMemberJoinHospitalDTO dto,PageRequest page);
}
