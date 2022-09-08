package com.comvee.cdms.knowledge.mapper;

import com.comvee.cdms.knowledge.po.WikiPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/3
 */
public interface WikiMapper {

    /**
     * 加载知识学习列表
     * @param pid
     * @return
     */
    List<WikiPO> listWiki(@Param("pid") String pid);

    /**
     * 新增点击数
     * @param sid
     */
    void updateWikiClick(@Param("sid") String sid);
}
