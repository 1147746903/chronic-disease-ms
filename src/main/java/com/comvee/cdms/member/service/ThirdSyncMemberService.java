package com.comvee.cdms.member.service;

/**
 * @author: suyz
 * @date: 2019/4/12
 */
public interface ThirdSyncMemberService {

    /**
     * 同步用户信息至第三方
     * @param memberId
     */
    void syncMemberInfo(String memberId);
}
