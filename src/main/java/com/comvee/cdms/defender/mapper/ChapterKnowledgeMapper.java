/**
 * @File name:   ChapterKnowledgeMapper.java  章节文章关联表 mapper层接口
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.mapper;

import com.comvee.cdms.defender.model.ChapterKnowledgeModel;
import com.comvee.cdms.defender.model.KnowledgeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterKnowledgeMapper {

	/**
	 * @TODO 根据id获取章节文章关联表表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	ChapterKnowledgeModel loadChapterKnowledgeById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取章节文章关联表分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	List<ChapterKnowledgeModel> loadChapterKnowledge() ;
	
	/**
	 * 章节id
	 * @param chapterId
	 * @return
	 */
	List<KnowledgeModel> loadKnowledgeRelation(@Param("chapterId") Long chapterId);
	
	/**
	 * @TODO  统计章节文章关联表记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	long countChapterKnowledge() ;
	

	/**
	 * @TODO  添加章节文章关联表记录
	 * @param ChapterKnowledgeModel 章节文章关联表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void addChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel) ;
	
	/**
	 * @TODO  修改章节文章关联表记录
	 * @param ChapterKnowledgeModel 章节文章关联表　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void modifyChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel) ;
	
	/**
	 * @TODO  删除章节文章关联表记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void delChapterKnowledge(@Param("sid") Long sid) ;
	
	/**
	 * @TODO  删除章节文章关联表记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void delByChapterId(@Param("chapterId") Long chapterId) ;
	
}
