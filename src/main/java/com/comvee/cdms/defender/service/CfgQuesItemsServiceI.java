/**
 * @File name:   CfgQuesItemsServiceI.java  题目选项 service层接口
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

import com.comvee.cdms.defender.model.CfgQuesItemsModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;


public interface CfgQuesItemsServiceI {


	/**
	 * @TODO 根据id获取题目选项表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgQuesItemsModel loadCfgQuesItemsById(Long sid) ;
	
	 /**
	 * @TODO 获取题目选项分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	PageResult<CfgQuesItemsModel> loadCfgQuesItems(PageRequest pager, Long qid) ;
	
	/**
	 * @TODO  统计题目选项记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countCfgQuesItems() ;
	
	/**
	 * @TODO  添加题目选项记录
	 * @param CfgQuesItemsModel 题目选项　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel) ;
	
	/**
	 * @TODO  修改题目选项记录
	 * @param CfgQuesItemsModel 题目选项　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel) ;
	
	/**
	 * @TODO  删除题目选项记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delCfgQuesItems(Long sid) ;
	
}
