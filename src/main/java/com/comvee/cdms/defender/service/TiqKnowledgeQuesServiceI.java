/**
 * @File name:   KnowledgeQuesServiceI.java  文章课后习题 service层接口
 * @Create on:  2018-7-28 16:52:22
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.service;


import com.comvee.cdms.defender.model.KnowledgeQuesModel;

import java.util.List;

public interface TiqKnowledgeQuesServiceI {


	/**
	 * @TODO 根据id获取文章课后习题表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	KnowledgeQuesModel loadKnowledgeQuesById(Long sid) ;
	
	 /**
	 * @TODO 获取文章课后习题分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	List<KnowledgeQuesModel> loadKnowledgeQues(Long knowledgeId) ;
	
	/**
	 * @TODO  统计文章课后习题记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	long countKnowledgeQues() ;
	
	/**
	 * @TODO  添加文章课后习题记录
	 * @param KnowledgeQuesModel 文章课后习题　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	void addKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel) ;
	
	/**
	 * @TODO  修改文章课后习题记录
	 * @param KnowledgeQuesModel 文章课后习题　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	void modifyKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel) ;
	
	/**
	 * @TODO  删除文章课后习题记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:52:22
	 */
	void delKnowledgeQues(Long sid) ;
	
	/**
	 * 批量添加课后习题
	 * @param ids
	 * @param knowledgeId
	 */
	void batchAddKnowledgeQues(List<Long> ids, Long knowledgeId);
	
}
