/**
 * @File name:   KnowledgeServiceI.java  知识点 service层接口
 * @Create on:  2016-12-29 18:33:32
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/
package com.comvee.cdms.knowledge.service;

import com.comvee.cdms.knowledge.dto.IncrMemberArticleClicksDTO;
import com.comvee.cdms.knowledge.model.*;

import java.util.List;
import java.util.Map;

public interface KnowledgeServiceI {
	/**
	 * 根据文章id获取文章知识点关联表表信息
	 * @author 李左河
	 * @date 2018年3月22日 上午9:57:52
	 * @param knowledgeId
	 * @return
	 * @throws Exception
	 */
     List<ArticleModel> loadByKnowledgeId(Long knowledgeId);
	
    
    /**
     * 根据文章id获取文章知识点 列表
     * @author 李左河
     * @date 2018年3月22日 上午9:58:00
     * @param articleId
     * @return
     * @throws Exception
     */
     List<KnowledgeModel>  loadKnowledgeByAid(Long articleId);

	/**
	 * 获取文章的知识点列表
	 * @author 李左河
	 * @date 2018年3月22日 上午9:58:08
	 * @param articleIds
	 * @return
	 * @throws Exception
	 */
     List<ArticleKnowledgeModel> findArticleKnowledgeByIds(List<Long> articleIds);
    
    
    /**
     * 根据id获取文章表表信息
     * @author 李左河
     * @date 2018年3月22日 上午9:58:18
     * @param id
     * @return
     * @throws Exception
     */
	 ArticleModel loadArticleById(Long id);
	
	
	 /**
	  * 获取文章表分页信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:58:26
	  * @param page
	  * @param rows
	  * @param startDt
	  * @param endDt
	  * @param platform
	  * @param title
	  * @param barId
	  * @return
	  * @throws Exception
	  */
	 List<ArticleModel> loadArticle(int page, int rows, String startDt, String endDt,
                                          String platform, String title, String barId);
	
	/**
	 * 统计文章表记录数
	 * @author 李左河
	 * @date 2018年3月22日 上午9:58:34
	 * @param startDt
	 * @param endDt
	 * @param platform
	 * @param title
	 * @param barId
	 * @return
	 * @throws Exception
	 */
	 long countArticle(String startDt, String endDt,
                             String platform, String title, String barId);
    
	/**
	 * 根据文章id获取文章正文信息
	 * @author 李左河
	 * @date 2018年3月22日 上午9:58:44
	 * @param articleId
	 * @return
	 * @throws Exception
	 */
	ArticleTxtModel loadArticleTxtByArticleId(Long articleId);
    

	/**
	 * 生成规则文件
	 * @author 李左河
	 * @date 2018年3月22日 上午9:58:51
	 * @throws Exception
	 */
	void createRuleFile();
    
    /**
     * 处理数据
     * @author 李左河
     * @date 2018年3月22日 上午9:59:02
     * @param mapMemberFile
     * @param uid
     * @return
     * @throws Exception
     */
     List<ArticleModel> getSuggest(Map<String, String> mapMemberFile, String uid);
    
    
	/**
	 * 获取知识点层级分类同一层级数据
	 * @author 李左河
	 * @date 2018年3月22日 上午9:59:11
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	 List<KnowledgeClassifyModel> loadKnowledgeClassify(Long pid);
	
	


    /**
     * 获取知识点分页信息
     * @author 李左河
     * @date 2018年3月22日 上午9:59:18
     * @param page
     * @param rows
     * @param model
     * @return
     * @throws Exception
     */
     List<KnowledgeModel> loadKnowledge(int page, int rows, KnowledgeModel model);

	/**
	 * 生成知识学习计划
	 * @param archives
	 * @param memberId
	 * @param drugList
	 * @return
	 */
	Map<String,Object> createKnowledgePlan(String archives,String memberId,String drugList);

	/**
	 * 新增患者文章点击数
	 */
	void incrMemberArticleClick(IncrMemberArticleClicksDTO incrMemberArticleClicksDTO);
}
