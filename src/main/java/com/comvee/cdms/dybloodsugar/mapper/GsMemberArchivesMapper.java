package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.GsMemberArchivesPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GsMemberArchivesMapper {
    boolean hasMemberArchives(String memberId);
    void addMemberArchives(GsMemberArchivesPO po);
    void updateMemberArchives(GsMemberArchivesPO po);
    GsMemberArchivesPO getMemberArchivesById(String memberId);
}
