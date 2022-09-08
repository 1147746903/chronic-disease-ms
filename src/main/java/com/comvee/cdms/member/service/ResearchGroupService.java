package com.comvee.cdms.member.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.dto.AddResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupMemberDTO;
import com.comvee.cdms.member.dto.UpdateResearchGroupDTO;
import com.comvee.cdms.member.po.ResearchGroupPO;
import com.comvee.cdms.member.vo.ResearchGroupMemberListVO;

import java.util.List;

/***
 * 课题组管理
 */
public interface ResearchGroupService {

    /**
     * 加载课题组列表
     * @param doctorId
     * @param pr
     * @return
     */
    PageResult<ResearchGroupPO> listResearchGroup(ListResearchGroupDTO dto ,PageRequest pr);

    /**
     * 新增课题组
     * @param add
     * @return
     */
    String addResearchGroup(AddResearchGroupDTO add);

    /**
     * 修改课题组
     * @param update
     */
    void updateResearchGroup(UpdateResearchGroupDTO update);

    /**
     * 删除课题组
     * @param groupId
     */
    void deleteResearchGroup(String groupId);

    /**
     * 加载课题组成员
     * @param dto
     * @return
     */
    List<ResearchGroupMemberListVO> listResearchGroupMember(ListResearchGroupMemberDTO dto);

    /**
     * 新增课题组成员
     * @param groupId
     * @param memberId
     * @param doctorId
     */
    String addResearchGroupMember(String groupId ,String memberId ,String doctorId);

    /**
     * 删除课题组成员
     * @param sid
     */
    void deleteResearchGroupMember(String sid);

    /**
     * 判断患者是否在课题组中
     * @param memberId
     * @param hospitalId
     * @return
     */
    boolean checkMemberExistsInResearchGroup(String memberId ,String hospitalId);
}
