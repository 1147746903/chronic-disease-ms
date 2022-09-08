/**
 * @File name:   KnowledgeContentServiceI.java  文章内容正文 service层接口
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.service;


import com.comvee.cdms.defender.model.KnowledgeContentModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;

public interface TiqKnowledgeContentServiceI {


	/**
	 * @TODO 根据id获取文章内容正文表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	KnowledgeContentModel loadKnowledgeContentById(Long sid) ;
	
	 /**
	 * @TODO 获取文章内容正文分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	PageResult<KnowledgeContentModel> loadKnowledgeContent(PageRequest pager) ;
	
	 /**
	 * @TODO 获取文章内容正文分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	List<KnowledgeContentModel> loadContentByKnowledgeId(Long knowledgeId) ;
	
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
	void delKnowledgeContent(Long sid) ;
	
	/**
	 * @TODO  添加文章内容正文记录
	 * @param KnowledgeContentModel 文章内容正文　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	void addKnowledgeContent(List<KnowledgeContentModel> list, Long knowledgeId) ;
	/**
	 * 文章内容排序
	 * @param ids
	 */
	void sortKnowledgeContent(String ids);
	
}
