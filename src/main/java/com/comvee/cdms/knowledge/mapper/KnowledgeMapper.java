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
package com.comvee.cdms.knowledge.mapper;

import com.comvee.cdms.knowledge.model.*;
import com.comvee.cdms.knowledge.po.MemberArticleClicksPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KnowledgeMapper {

    /**
     * 获取知识点分页信息
     * @author 李左河
     * @date 2018年3月22日 上午9:55:17
     * @param model
     * @return
     */
     List<KnowledgeModel> loadKnowledge(KnowledgeModel model) ;

    
    /**
     * 获取知识点层级分类同一层级数据
     * @author 李左河
     * @date 2018年3月22日 上午9:55:29
     * @param pid
     * @return
     */
	 List<KnowledgeClassifyModel> loadKnowledgeClassify(@Param("pid") Long pid) ;
	
	/**
	 * 根据id获取用户画像特征表信息
	 * @author 李左河
	 * @date 2018年3月22日 上午9:55:37
	 * @param id
	 * @return
	 */
	 FeatureModel loadFeatureById(@Param("id") Long id) ;
	
	/**
	 * 根据id获取画像特征选项表信息
	 * @author 李左河
	 * @date 2018年3月22日 上午10:07:34
	 * @param id
	 * @return
	 */
	 FeatureItemModel loadFeatureItemById(@Param("id") Long id) ;
	
	/**
	 * 获取选项排序权重
	 * @TODO
	 * @return
	 * @
	 * @author zhengsw
	 * @date 2017-1-17
	 */
     List<FeatureItemModel> loadFeatureForSort() ;
    
     /**
      * 获取标签表分页信息
      * @author 李左河
      * @date 2018年3月22日 上午9:55:52
      * @param cfgTagModel
      * @return
      */
	 List<CfgTagModel> loadCfgTag(CfgTagModel cfgTagModel) ;

    
	 /**
	  * 获取关键字分页信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:56:13
	  * @param cfgKeywordModel
	  * @return
	  */
	 List<CfgKeywordModel> loadCfgKeyword(CfgKeywordModel cfgKeywordModel) ;
	
	
	 /**
	  * 根据id获取特片规则表信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:56:24
	  * @param id
	  * @return
	  */
	 CategoryRuleModel loadCategoryRuleById(@Param("id") Long id) ;
	
	 /**
	  * 获取特片规则分页信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:56:38
	  * @param categoryId
	  * @return
	  */
	 List<CategoryRuleModel> loadCategoryRule(@Param("categoryId") String categoryId) ;

    /**
     * 根据文章id获取文章正文信息
     * @author 李左河
     * @date 2018年3月22日 上午9:56:48
     * @param articleId
     * @return
     */
     ArticleTxtModel loadArticleTxtByArticleId(@Param("articleId") Long articleId)  ;
    
    


	/**
	 * 根据id获取文章表表信息
	 * @author 李左河
	 * @date 2018年3月22日 上午9:56:57
	 * @param id
	 * @return
	 */
	 ArticleModel loadArticleById(@Param("id") Long id) ;
	
	
	 /**
	  * 获取文章表分页信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:57:05
	  * @param page
	  * @param rows
	  * @param startDt
	  * @param endDt
	  * @param platform
	  * @param title
	  * @param barId
	  * @return
	  */
	 List<ArticleModel> loadArticle(@Param("page") int page, @Param("rows") int rows, @Param("startDt") String startDt, @Param("endDt") String endDt,
                                    @Param("platform") String platform, @Param("title") String title, @Param("barId") String barId) ;
	
	/**
	 * 统计文章表记录数
	 * @author 李左河
	 * @date 2018年3月22日 上午9:57:13
	 * @param startDt
	 * @param endDt
	 * @param platform
	 * @param title
	 * @param barId
	 * @return
	 */
	 long countArticle(@Param("startDt") String startDt, @Param("endDt") String endDt,
                       @Param("platform") String platform, @Param("title") String title, @Param("barId") String barId) ;
	
	
	 /**
	  * 根据文章id获取文章知识点关联表表信息
	  * @author 李左河
	  * @date 2018年3月22日 上午9:57:21
	  * @param knowledgeId
	  * @return
	  */
     List<ArticleModel> loadByKnowledgeId(@Param("knowledgeId") Long knowledgeId) ;
	
    
    /**
     * 根据文章id获取文章知识点 列表
     * @author 李左河
     * @date 2018年3月22日 上午9:57:30
     * @param articleId
     * @return
     */
     List<KnowledgeModel>  loadKnowledgeByAid(@Param("articleId") Long articleId) ;

	/**
	 * 获取文章的知识点列表
	 * @author 李左河
	 * @date 2018年3月22日 上午9:57:38
	 * @param articleIds
	 * @return
	 */
     List<ArticleKnowledgeModel> findArticleKnowledgeByIds(@Param("articleIds") List<Long> articleIds) ;

	/**
	 * 获取患者文章点击数据
	 * @param memberId
	 * @return
	 */
	MemberArticleClicksPO getMemberArticleClicks(@Param("memberId") String memberId);

	/**
	 * 新增患者文章点击数据
	 * @param memberArticleClicksPO
	 */
	void addMemberArticleClicks(MemberArticleClicksPO memberArticleClicksPO);

	/**
	 * 修改患者文章点击数据
	 * @param memberArticleClicksPO
	 */
	void updateMemberArticleClicks(MemberArticleClicksPO memberArticleClicksPO);
}
