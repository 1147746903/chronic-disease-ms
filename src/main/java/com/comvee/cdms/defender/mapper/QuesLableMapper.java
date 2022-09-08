/**
 * @File name:   QuesLableMapper.java  题目分组 mapper层接口
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

import com.comvee.cdms.defender.model.QuesLableModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesLableMapper {

	/**
	 * @TODO 根据id获取题目分组表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	QuesLableModel loadQuesLableById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取题目分组分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<QuesLableModel> loadQuesLable() ;
	
	/**
	 * @TODO  统计题目分组记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countQuesLable() ;
	

	/**
	 * @TODO  添加题目分组记录
	 * @param QuesLableModel 题目分组　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addQuesLable(QuesLableModel quesLableModel) ;
	
	/**
	 * @TODO  修改题目分组记录
	 * @param QuesLableModel 题目分组　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyQuesLable(QuesLableModel quesLableModel) ;
	
	/**
	 * @TODO  删除题目分组记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delQuesLable(@Param("sid") Long sid) ;
	
}
