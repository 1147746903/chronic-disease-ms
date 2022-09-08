package com.comvee.cdms.member.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.member.dto.AddInHospitalMemberDTO;
import com.comvee.cdms.member.dto.ChangeHospitalBedDTO;
import com.comvee.cdms.member.dto.ListMemberDTO;
import com.comvee.cdms.member.dto.ReInHospitalMemberDTO;
import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import com.comvee.cdms.member.vo.InHospitalBedMemberVO;
import com.comvee.cdms.member.vo.ListInHospitalLogVO;
import com.comvee.cdms.member.vo.OutHospitalListMemberVO;

import java.util.List;

public interface InHospitalMemberService {

    /**
     * 住院病床列表（带患者信息）
     * @param listMemberDTO
     * @param pager
     * @return
     */
    PageResult<InHospitalBedMemberVO> listHospitalBedWithMember(ListMemberDTO listMemberDTO, PageRequest pager);
    //出院
    PageResult<OutHospitalListMemberVO> listHospitalOutMember(ListMemberDTO listMemberDTO, PageRequest pager);


     void listMemberDTOAuthHandler(ListMemberDTO listMemberDTO);
    /**
     * 添加住院病人
     * @param addInHospitalMemberDTO
     * @param doctorPO
     */
    void addInHospitalMember(AddInHospitalMemberDTO addInHospitalMemberDTO , DoctorPO doctorPO);

    /**
     * 更换病床
     * @param changeHospitalBedDTO
     */
    void changeHospitalBed(ChangeHospitalBedDTO changeHospitalBedDTO);

    /**
     * 出院办理
     * @param sid
     * @param outHospitalDate
     */
    void outHospital(String sid ,String outHospitalDate,String doctorId);

    /**
     * 根据科室加载病床列表
     * @param departmentId
     * @param checkinStatus
     * @return
     */
    List<MemberCheckinInfoPO> listHospitalBed(String departmentId ,Integer checkinStatus);

    /**
     * 重新入院
     * @param reInHospitalMemberDTO
     * @param doctorPO
     */
    void reInHospital(ReInHospitalMemberDTO reInHospitalMemberDTO , DoctorPO doctorPO);

    /**
     * 患者住院记录
     * @param memberId
     */
    List<ListInHospitalLogVO> getMemberHospitalLog(String memberId);
}
