/**
 * @File name:   CfgGroupQuesMapper.java  场景题目配置 mapper层接口
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

import com.comvee.cdms.defender.dto.SortGroupQuesDto;
import com.comvee.cdms.defender.model.CfgGroupQuesModel;

public interface CfgGroupQuesMapper {

	/**
	 * @TODO 根据id获取场景题目配置表信息
	 * @param tid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgGroupQuesModel loadCfgGroupQuesById(@Param("tid") Long tid) ;
	
	 /**
	 * @TODO 获取场景题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgGroupQuesModel> loadCfgGroupQues(@Param("gid") Long gid) ;
	
	
	 /**
	 * @TODO 获取场景题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgGroupQuesModel loadGroupQuesByQid(@Param("qid") Long qid,@Param("gid") Long gid) ;
	
	/**
	 * @TODO  统计场景题目配置记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	Long countCfgGroupQues() ;
	
	/**
	 * 获取组当前最大sort
	 * @param groupId
	 * @return
	 */
	Long loadGroupQuesMaxSort(@Param("groupId") Long groupId);

	/**
	 * @TODO  添加场景题目配置记录
	 * @param CfgGroupQuesModel 场景题目配置　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addCfgGroupQues(CfgGroupQuesModel cfgGroupQuesModel) ;
	
	/**
	 * @TODO  修改场景题目配置记录
	 * @param CfgGroupQuesModel 场景题目配置　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyCfgGroupQues(CfgGroupQuesModel cfgGroupQuesModel) ;
	
	/**
	 * @TODO  删除场景题目配置记录
	 * @param tid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delCfgGroupQues(@Param("tid") Long  tid) ;
	
	/**
	 * @TODO 题目排序
	 * @param  list 题目与分组关联列表
	 * @return
	 * @author zhengsw
	 * @date 2016-2-2
	 * 请求样例
	 */
	boolean sortCfgGroupQues(SortGroupQuesDto sortGroupQuesDto);
	
}
