package com.comvee.cdms.knowledge.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.knowledge.po.WikiPO;

/**
 * @author: suyz
 * @date: 2018/11/3
 */
public interface WikiService {

    /**
     * 加载知识学习列表
     * @param pr
     * @param pid
     * @return
     */
    PageResult<WikiPO> listWiki(PageRequest pr, String pid);

    /**
     * 新增点击数
     * @param sid
     */
    void updateWikiClick(String sid ,String memberId);
}
