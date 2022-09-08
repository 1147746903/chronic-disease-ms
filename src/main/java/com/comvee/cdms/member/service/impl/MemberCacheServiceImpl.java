package com.comvee.cdms.member.service.impl;

import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberArchivesRecordPO;
import com.comvee.cdms.member.service.MemberCacheServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberCacheService")
public class MemberCacheServiceImpl implements MemberCacheServiceI {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberArchivesPO getMemberArchives(String memberId, String hospitalId) {
        return this.memberMapper.getMemberArchives(memberId,hospitalId);
    }

    @Override
    public void updateMemberArchivesById(MemberArchivesPO memberArchivesPO) {
        this.memberMapper.updateMemberArchivesById(memberArchivesPO);
    }

    @Override
    public void addMemberArchives(MemberArchivesPO memberArchivesPO) {
        memberArchivesPO.setSid(DaoHelper.getSeq());
        this.memberMapper.addMemberArchives(memberArchivesPO);
    }

    @Override
    public void addMemberArchivesRecord(MemberArchivesRecordPO memberArchivesRecordPO) {
        this.memberMapper.addMemberArchivesRecord(memberArchivesRecordPO);
    }
}
