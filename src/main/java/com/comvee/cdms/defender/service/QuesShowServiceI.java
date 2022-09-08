/**
 * @File name:   QuesShowServiceI.java  题目话术 service层接口
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.service;


import com.comvee.cdms.defender.model.QuesShowModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;


public interface QuesShowServiceI {


	/**
	 * @TODO 根据id获取题目话术表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	QuesShowModel loadQuesShowById(Long sid) ;
	
	 /**
	 * @TODO 获取题目话术分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	PageResult<QuesShowModel> loadQuesShow(PageRequest pager, Long qid) ;
	
	/**
	 * @TODO  统计题目话术记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countQuesShow() ;
	
	/**
	 * @TODO  添加题目话术记录
	 * @param QuesShowModel 题目话术　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addQuesShow(QuesShowModel quesShowModel) ;
	
	/**
	 * @TODO  修改题目话术记录
	 * @param QuesShowModel 题目话术　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyQuesShow(QuesShowModel quesShowModel) ;
	
	/**
	 * @TODO  删除题目话术记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delQuesShow(Long sid) ;
	
}
