/**
 * @File name:   KnowledgeContentMapper.java  文章内容正文 mapper层接口
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

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.model.KnowledgeContentModel;

public interface TiqKnowledgeContentMapper {

	/**
	 * @TODO 根据id获取文章内容正文表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	KnowledgeContentModel loadKnowledgeContentById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取文章内容正文分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	List<KnowledgeContentModel> loadKnowledgeContent(@Param("knowledgeId") Long knowledgeId) ;

	List<KnowledgeContentModel> loadKnowledgeContentByType(List<String> list) ;

	/**
	 * @TODO  统计文章内容正文记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	long countKnowledgeContent() ;
	

	/**
	 * @TODO  添加文章内容正文记录
	 * @param KnowledgeContentModel 文章内容正文　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void addKnowledgeContent(KnowledgeContentModel knowledgeContentModel) ;
	
	/**
	 * @TODO  修改文章内容正文记录
	 * @param KnowledgeContentModel 文章内容正文　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void modifyKnowledgeContent(KnowledgeContentModel knowledgeContentModel) ;
	
	/**
	 * @TODO  删除文章内容正文记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void delKnowledgeContent(@Param("sid") Long  sid) ;
	
	/**
	 * @TODO  当前最大排序
	 * @param sid 主键id
	 * @return 
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	Integer maxContentSort(@Param("knowledgeId") Long  knowledgeId) ;

	List<KnowledgeContentModel> loadKnowledgeContentByKnowledgeId(@Param("knowledgeId") String id);

	/**
	 * 排序
	 */
	void sortKnowledgeContent(@Param("sid") String sid,@Param("sort") String sort);
}
