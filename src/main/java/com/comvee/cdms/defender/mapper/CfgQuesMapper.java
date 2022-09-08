/**
 * @File name:   CfgQuesMapper.java  题目配置 mapper层接口
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

import com.comvee.cdms.defender.model.CfgQuesModel;

public interface CfgQuesMapper {

	/**
	 * @TODO 根据id获取题目配置表信息
	 * @param qid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgQuesModel loadCfgQuesById(@Param("qid") Long qid) ;
	
	 /**
	 * @TODO 获取题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgQuesModel> loadCfgQues(@Param("label")Long label,@Param("param")String param) ;
	
	/**
	 * @TODO  统计题目配置记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countCfgQues() ;
	

	/**
	 * @TODO  添加题目配置记录
	 * @param CfgQuesModel 题目配置　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addCfgQues(CfgQuesModel cfgQuesModel) ;
	
	/**
	 * @TODO  修改题目配置记录
	 * @param CfgQuesModel 题目配置　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyCfgQues(CfgQuesModel cfgQuesModel) ;
	
	/**
	 * @TODO  删除题目配置记录
	 * @param qid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delCfgQues(@Param("qid") Long  qid) ;
	
	/**
	 * 
	 * @param 根据知识点id获取题目
	 * @return
	 */
	List<CfgQuesModel> loadQuesByKnowledgeId(@Param("knowledgeId")String id);
	
	/**
	 * 
	 * @param 根据课程id获取题目
	 * @return
	 */
	List<CfgQuesModel> loadQuesByCourseId(@Param("courseId") String courseId);
	
}
