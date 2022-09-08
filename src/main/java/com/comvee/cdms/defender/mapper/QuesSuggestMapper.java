/**
 * @File name:   QuesSuggestMapper.java  题目结果 mapper层接口
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

import com.comvee.cdms.defender.model.QuesSuggestModel;

public interface QuesSuggestMapper {

	/**
	 * @TODO 根据id获取题目结果表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	QuesSuggestModel loadQuesSuggestById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取题目结果分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<QuesSuggestModel> loadQuesSuggest(@Param("tid") Long tid) ;
	
	/**
	 * @TODO  统计题目结果记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countQuesSuggest() ;
	

	/**
	 * @TODO  添加题目结果记录
	 * @param QuesSuggestModel 题目结果　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addQuesSuggest(QuesSuggestModel quesSuggestModel) ;
	
	/**
	 * @TODO  修改题目结果记录
	 * @param QuesSuggestModel 题目结果　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyQuesSuggest(QuesSuggestModel quesSuggestModel) ;
	
	/**
	 * @TODO  删除题目结果记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delQuesSuggest(@Param("sid") Long  sid) ;
	
	/**
	 * @TODO  删除题目结果建议表记录
	 * @param tid 场景题目主键id
	 * @return true 删除成功 flase 删除失败
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-02-02
	 */
	 long delQuesSuggestByTid(@Param("tid")Long tid);
	
}
