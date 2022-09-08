package com.comvee.cdms.knowledge.tool;

/**
 * @File name:  ComponentCache.java  后台题目配置的内内容缓存
 * @Create on:  2016-02-01
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 **/

import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.knowledge.mapper.KnowledgeMapper;
import com.comvee.cdms.knowledge.model.CfgKeywordModel;
import com.comvee.cdms.knowledge.model.CfgTagModel;
import com.comvee.cdms.knowledge.model.KnowledgeModel;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



//@Component("componentCache")
public class ComponentCache {
    private static final Logger logger = LoggerFactory.getLogger(ComponentCache.class);
    
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    public final static String CFG_KEY_BAR="bar";
    public final static String CFG_KEY_TAG="tag";
    public final static String CFG_KEY_KEYWORD="keyword";
    public final static String CFG_KEY_KNOWLEDGE="knowledge";
    public  final static String CFG_KEY_KNOWLEDGE_CLASSIFY="knowledge_classify";

    /**
     * @TODO 问题配置信息缓存初始化
     * @author zhengsw
     * @date 2016-2-3
     */
    @PostConstruct
    public void init() throws Exception{
            
            /**
             * tag标签
             */
        	PageHelper.startPage(1,5000);
            List<CfgTagModel> listTag = this.knowledgeMapper.loadCfgTag(null);
            Map<String, CfgTagModel> tagMap = new HashMap<String, CfgTagModel>(listTag.size());
            for (CfgTagModel  tagModel :  listTag) {
                tagMap.put(tagModel.getId().toString(), tagModel);
            }
            InfoManager.getInstance().tagMap = tagMap;
            
            /**
             * keyword关键字
             */
            PageHelper.startPage(1,5000);
            List<CfgKeywordModel> keyWordList = this.knowledgeMapper.loadCfgKeyword(null);
            Map<String, CfgKeywordModel> keyWordMap = new HashMap<String, CfgKeywordModel>(keyWordList.size());
            for (CfgKeywordModel  keywordModel :  keyWordList) {
                keyWordMap.put(keywordModel.getId().toString(), keywordModel);
            }
            InfoManager.getInstance().keyWordMap = keyWordMap;
            
            
            PageHelper.startPage(1,5000);
            List<KnowledgeModel> knowledgeList =  this.knowledgeMapper.loadKnowledge(null);
            Map<String, KnowledgeModel> knowledgeMap = new HashMap<String, KnowledgeModel>(knowledgeList.size());
            for (KnowledgeModel  knowledgeModel :  knowledgeList) {
                knowledgeMap.put(knowledgeModel.getId().toString(), knowledgeModel);
            }
            InfoManager.getInstance().knowledgeMap = knowledgeMap;
            
            


    }

    /**
     * 问题配置信息缓存   重新加载
     * @author zhengsw
     * @date 2016-2-3
     * 请求样例
     */
    public void reload() throws Exception{
        InfoManager.getInstance().tagMap.clear();
        InfoManager.getInstance().keyWordMap.clear();
        InfoManager.getInstance().knowledgeMap.clear();
        init();
    }
    
    public void reload(String key){
        try {
            if(StringUtils.isBlank(key)){
                return ;
            }
            else if(CFG_KEY_TAG.equals(key)){
                /**
                 * tag标签
                 */
                PageHelper.startPage(1,5000);
                List<CfgTagModel> listTag = this.knowledgeMapper.loadCfgTag(null);
                Map<String, CfgTagModel> tagMap = new HashMap<String, CfgTagModel>(listTag.size());
                for (CfgTagModel  tagModel :  listTag) {
                    tagMap.put(tagModel.getId().toString(), tagModel);
                }
                InfoManager.getInstance().tagMap = tagMap;
                
            }
            else if(CFG_KEY_KEYWORD.equals(key)){
                /**
                 * keyword关键字
                 */
                PageHelper.startPage(1,5000);
                List<CfgKeywordModel> keyWordList = this.knowledgeMapper.loadCfgKeyword(null);
                Map<String, CfgKeywordModel> keyWordMap = new HashMap<String, CfgKeywordModel>(keyWordList.size());
                for (CfgKeywordModel  keywordModel :  keyWordList) {
                    keyWordMap.put(keywordModel.getId().toString(), keywordModel);
                }
                InfoManager.getInstance().keyWordMap = keyWordMap;
            }
            else if(CFG_KEY_KNOWLEDGE.equals(key)){
                PageHelper.startPage(1,5000);
            	List<KnowledgeModel> knowledgeList =  this.knowledgeMapper.loadKnowledge(null);
                Map<String, KnowledgeModel> knowledgeMap = new HashMap<String, KnowledgeModel>(knowledgeList.size());
                for (KnowledgeModel  knowledgeModel :  knowledgeList) {
                    knowledgeMap.put(knowledgeModel.getId().toString(), knowledgeModel);
                }
                InfoManager.getInstance().knowledgeMap = knowledgeMap;
            }else {
                return;
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void main(String[] args) {
        reload("knowledge");
    }
}
