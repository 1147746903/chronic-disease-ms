/**
 * @File name:   QuesRuleMapper.java  题目跳转规则 mapper层接口
 * @Create on:  2018-7-25 15:24:49
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

import com.comvee.cdms.defender.model.QuesRuleModel;

public interface QuesRuleMapper {

	/**
	 * @TODO 根据id获取题目跳转规则表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	QuesRuleModel loadQuesRuleById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取题目跳转规则分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<QuesRuleModel> loadQuesRule(@Param("tid") Long tid) ;
	
	
	 /**
	 * @TODO 获取题目跳转规则分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<QuesRuleModel> initRuleLoad();
	
	/**
	 * @TODO  统计题目跳转规则记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countQuesRule() ;
	

	/**
	 * @TODO  添加题目跳转规则记录
	 * @param QuesRuleModel 题目跳转规则　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addQuesRule(QuesRuleModel quesRuleModel) ;
	
	/**
	 * @TODO  修改题目跳转规则记录
	 * @param QuesRuleModel 题目跳转规则　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyQuesRule(QuesRuleModel quesRuleModel) ;
	
	/**
	 * @TODO  删除题目跳转规则记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delQuesRule(@Param("sid") Long  sid) ;
	
	/**
	 * @TODO  删除题目跳转规则记录
	 * @param tid tid
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long delQuesRuleByTid(@Param("tid") Long  tid);
	
}
