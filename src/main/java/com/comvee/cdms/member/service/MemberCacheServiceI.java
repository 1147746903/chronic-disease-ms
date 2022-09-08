package com.comvee.cdms.member.service;

import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberArchivesRecordPO;
import org.springframework.cache.annotation.CacheEvict;

public interface MemberCacheServiceI {
    /**
     * 根据id获取患者档案数据
     * @param memberId
     * @return
     */
//    @Cacheable(value = "memberArchives", key = "'memberArchives'+#memberId+#hospitalId")
    MemberArchivesPO getMemberArchives(String memberId, String hospitalId);

    /**
     * 修改患者档案
     * @param memberArchivesPO
     */
    @CacheEvict(value = "memberArchives", key = "'memberArchives'+#memberArchivesPO.memberId+#memberArchivesPO.hospitalId")
    void updateMemberArchivesById(MemberArchivesPO memberArchivesPO);

    /**
     * 新增患者档案
     * @param memberArchivesPO
     */
    @CacheEvict(value = "memberArchives", key = "'memberArchives'+#memberArchivesPO.memberId+#memberArchivesPO.hospitalId")
    void addMemberArchives(MemberArchivesPO memberArchivesPO);

    /**
     * 新增患者档案记录
     * @param memberArchivesRecordPO
     */
    @CacheEvict(value = "memberArchives", key = "'memberArchives'+#memberArchivesRecordPO.memberId+#memberArchivesRecordPO.hospitalId")
    void addMemberArchivesRecord(MemberArchivesRecordPO memberArchivesRecordPO);

}
