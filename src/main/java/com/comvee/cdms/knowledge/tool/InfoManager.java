/**
 * @File name:  InfoManager.java  配题相关内容缓存管理
 * @Create on:  2016-02-01
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 **/
package com.comvee.cdms.knowledge.tool;

import com.comvee.cdms.knowledge.model.CfgKeywordModel;
import com.comvee.cdms.knowledge.model.CfgTagModel;
import com.comvee.cdms.knowledge.model.KnowledgeModel;

import java.util.HashMap;
import java.util.Map;

public class InfoManager {
    private static InfoManager instance = null;

    public static synchronized InfoManager getInstance() {
        if (instance == null) {
            instance = new InfoManager();
        }
        return instance;
    }

    /**
     * 标签
     */
    public Map<String, CfgTagModel> tagMap = new HashMap<String, CfgTagModel>();
    
    /**
     * 关键字
     */
    public Map<String, CfgKeywordModel> keyWordMap = new HashMap<String, CfgKeywordModel>();
    
    /**
     * 知识点
     */
    public Map<String, KnowledgeModel> knowledgeMap = new HashMap<String, KnowledgeModel>();
    
//    /**
//     * 知识点分类
//     */
//    public Map<String, KnowledgeClassifyModel> knowledgeClassifyMap = new HashMap<String, KnowledgeClassifyModel>();



}
