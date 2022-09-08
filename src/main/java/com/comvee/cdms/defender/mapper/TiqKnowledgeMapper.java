/**
 * @File name:   KnowledgeMapper.java  知识点表 mapper层接口
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

import com.comvee.cdms.defender.model.KnowledgeModel;

public interface TiqKnowledgeMapper {

	/**
	 * @TODO 根据id获取知识点表表信息
	 * @param id　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	KnowledgeModel loadKnowledgeById(@Param("id") Long id) ;
	
	 /**
	 * @TODO 获取知识点表分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	List<KnowledgeModel> loadKnowledge(@Param("type")Integer type,@Param("param")String param) ;
	
	/**
	 * @TODO  统计知识点表记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	long countKnowledge() ;
	

	/**
	 * @TODO  添加知识点表记录
	 * @param KnowledgeModel 知识点表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void addKnowledge(KnowledgeModel knowledgeModel) ;
	
	/**
	 * @TODO  修改知识点表记录
	 * @param KnowledgeModel 知识点表　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void modifyKnowledge(KnowledgeModel knowledgeModel) ;
	
	/**
	 * @TODO  删除知识点表记录
	 * @param id 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void delKnowledge(@Param("id") Long  id) ;
	
	/**
	 * 获取篇章下的知识点
	 * @param chapterId
	 * @return
	 */
	List<KnowledgeModel> loadKnowledgeByChapterId(@Param("chapterId") String chapterId);
	
	/**
	 * 知识点详情
	 * @param 
	 * @return
	 */
	KnowledgeModel loadKnowledgeById(@Param("id") String knowledgeId);
	
}
