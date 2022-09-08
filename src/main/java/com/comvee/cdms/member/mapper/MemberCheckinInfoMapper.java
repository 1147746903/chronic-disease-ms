package com.comvee.cdms.member.mapper;

import com.comvee.cdms.member.po.MemberCheckinInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberCheckinInfoMapper {

    /**
     * 根据主键获取病床信息记录
     * @param sid
     * @return
     */
    MemberCheckinInfoPO getMemberCheckinInfoById(@Param("sid") String sid);

    /**
     * 修改病床信息记录
     * @param update
     * @return
     */
    int updateMemberCheckinInfo(MemberCheckinInfoPO update);

    /**
     * 根据科室加载病床列表
     * @param departmentId
     * @param checkinStatus
     * @return
     */
    List<MemberCheckinInfoPO> listMemberCheckinInfo(@Param("departmentId") String departmentId ,@Param("checkinStatus")Integer checkinStatus);

    /**
     * 新增病床记录
     * @param add
     */
    void addMemberCheckinInfo(MemberCheckinInfoPO add);

    /**
     * 根据床号获取病床信息
     * @param departmentId
     * @param bedNo
     * @return
     */
    MemberCheckinInfoPO getMemberCheckinInfoByBedNo(@Param("departmentId")String departmentId ,@Param("bedNo") String bedNo);


    MemberCheckinInfoPO getMemberCheckinInfoByMemberId(@Param("memberId") String memberId,@Param("hospitalId")String hospitalId);

    MemberCheckinInfoPO getMemberCheckinInfoByMemberId2(@Param("memberId") String memberId,@Param("hospitalId")String hospitalId);

    MemberCheckinInfoPO getMemberCheckinInfoByDepartmentId(@Param("departmentId")String departmentId);

    MemberCheckinInfoPO findMemberCheckinInfoByHospitalNo(@Param("hospitalNo") String hospitalNo ,@Param("hospitalId")String hospitalId);

}
