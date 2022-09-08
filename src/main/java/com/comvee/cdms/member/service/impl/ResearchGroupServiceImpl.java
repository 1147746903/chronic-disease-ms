package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.dto.AddResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupDTO;
import com.comvee.cdms.member.dto.ListResearchGroupMemberDTO;
import com.comvee.cdms.member.dto.UpdateResearchGroupDTO;
import com.comvee.cdms.member.mapper.ResearchGroupMapper;
import com.comvee.cdms.member.mapper.ResearchGroupMemberMapper;
import com.comvee.cdms.member.po.ResearchGroupMemberPO;
import com.comvee.cdms.member.po.ResearchGroupPO;
import com.comvee.cdms.member.service.ResearchGroupService;
import com.comvee.cdms.member.vo.ResearchGroupMemberListVO;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("researchGroupService")
public class ResearchGroupServiceImpl implements ResearchGroupService {

    @Autowired
    private ResearchGroupMapper researchGroupMapper;

    @Autowired
    private ResearchGroupMemberMapper researchGroupMemberMapper;

    @Override
    public PageResult<ResearchGroupPO> listResearchGroup(ListResearchGroupDTO dto ,PageRequest pr){
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<ResearchGroupPO> list = this.researchGroupMapper.listResearchGroup(dto);
        return new PageResult<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addResearchGroup(AddResearchGroupDTO add) {
        String groupId = DaoHelper.getSeq();

        int memberNumber = batchAddResearchGroupMember(groupId ,add.getMemberIdStr() ,add.getDoctorId());

        ResearchGroupPO researchGroup = new ResearchGroupPO();
        researchGroup.setGroupId(groupId);
        researchGroup.setResearchGroupName(add.getResearchGroupName());
        researchGroup.setMemberNumber(memberNumber);
        researchGroup.setHospitalId(add.getHospitalId());
        researchGroup.setCreatorId(add.getDoctorId());
        this.researchGroupMapper.addResearchGroup(researchGroup);

        return groupId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateResearchGroup(UpdateResearchGroupDTO update) {
        ResearchGroupPO group = this.researchGroupMapper.getResearchGroup(update.getGroupId());
        if(group == null){
            throw new BusinessException("课题组不存在，请确认");
        }
        int memberNumber = researchGroupMemberHandler(update.getGroupId() ,update.getMemberIdStr() ,update.getDoctorId());

        group.setMemberNumber(memberNumber);
        group.setResearchGroupName(update.getResearchGroupName());
        this.researchGroupMapper.updateResearchGroup(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResearchGroup(String groupId) {
        ResearchGroupPO group = this.researchGroupMapper.getResearchGroup(groupId);
        if(group == null){
            throw new BusinessException("课题组不存在，无法删除");
        }
        group.setValid(0);
        this.researchGroupMapper.updateResearchGroup(group);

        this.researchGroupMemberMapper.batchDeleteResearchGroupMember(groupId);
    }

    @Override
    public List<ResearchGroupMemberListVO> listResearchGroupMember(ListResearchGroupMemberDTO dto) {
        return this.researchGroupMemberMapper.listResearchGroupMember(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addResearchGroupMember(String groupId, String memberId ,String doctorId) {
        ResearchGroupPO group = this.researchGroupMapper.getResearchGroup(groupId);
        if(group == null){
            throw new BusinessException("课题组不存在，请确认");
        }
        ResearchGroupMemberPO add = new ResearchGroupMemberPO();
        add.setSid(DaoHelper.getSeq());
        add.setResearchGroupId(groupId);
        add.setMemberId(memberId);
        add.setCreatorId(doctorId);
        this.researchGroupMemberMapper.batchAddResearchGroupMember(Collections.singletonList(add));

        afterUpdateResearchGroupMemberHandler(groupId);

        return add.getSid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResearchGroupMember(String sid) {
        ResearchGroupMemberPO researchGroupMember = this.researchGroupMemberMapper.getResearchGroupMember(sid);
        if(researchGroupMember == null){
            throw new BusinessException("课题组成员不存在，无法删除");
        }
        researchGroupMember.setValid(0);
        this.researchGroupMemberMapper.updateResearchGroupMember(researchGroupMember);

        afterUpdateResearchGroupMemberHandler(researchGroupMember.getResearchGroupId());
    }

    @Override
    public boolean checkMemberExistsInResearchGroup(String memberId, String hospitalId) {
        return this.researchGroupMemberMapper.countMemberInHospitalResearchGroup(memberId ,hospitalId) > 0;
    }

    /**
     * 批量新增课题组成员
     * @param groupId
     * @param memberIdStr
     * @param doctorId
     * @return
     */
    private int batchAddResearchGroupMember(String groupId ,String memberIdStr ,String doctorId){
        if(StringUtils.isBlank(memberIdStr)){
            return 0;
        }
        Set<String> idSet = new HashSet<>(Arrays.asList(memberIdStr.split(",")));
        List<ResearchGroupMemberPO> addList = new ArrayList<>();
        ResearchGroupMemberPO add = null;
        for(String id : idSet){
            add = new ResearchGroupMemberPO();
            add.setSid(DaoHelper.getSeq());
            add.setResearchGroupId(groupId);
            add.setMemberId(id);
            add.setCreatorId(doctorId);
            addList.add(add);
        }
        this.researchGroupMemberMapper.batchAddResearchGroupMember(addList);
        return addList.size();
    }

    /**
     * 修改课题组成员后处理
     * @param groupId
     */
    private void afterUpdateResearchGroupMemberHandler(String groupId){
        List list = this.researchGroupMemberMapper.listResearchGroupMemberByGroupId(groupId);

        ResearchGroupPO group = new ResearchGroupPO();
        group.setGroupId(groupId);
        group.setMemberNumber(list.size());
        this.researchGroupMapper.updateResearchGroup(group);
    }

    /**
     * 课题组成员处理
     * @param groupId
     * @param memberIdStr
     * @param doctorId
     * @return
     */
    private int researchGroupMemberHandler(String groupId ,String memberIdStr ,String doctorId){
        if(StringUtils.isBlank(memberIdStr)){
            this.researchGroupMemberMapper.batchDeleteResearchGroupMember(groupId);
            return 0;
        }
        List<ResearchGroupMemberPO> memberList = this.researchGroupMemberMapper.listResearchGroupMemberByGroupId(groupId);

        Set<String> localSet = memberList.stream().map(ResearchGroupMemberPO::getMemberId).collect(Collectors.toSet());
        Set<String> updateSet = new HashSet<>(Arrays.asList(memberIdStr.split(",")));
        updateSet.removeAll(localSet);
        batchAddResearchGroupMember(groupId ,Joiner.on(",").join(updateSet) ,doctorId);

        updateSet = new HashSet<>(Arrays.asList(memberIdStr.split(",")));
        localSet.removeAll(updateSet);
        if(!localSet.isEmpty()){
            this.researchGroupMemberMapper.deleteResearchGroupMember(groupId ,new ArrayList<>(localSet));
        }
        return this.researchGroupMemberMapper.listResearchGroupMemberByGroupId(groupId).size();
    }
}
