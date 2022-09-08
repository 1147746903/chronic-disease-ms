/**
 * @File name:   CfgGroupQuesServiceI.java  场景题目配置 service层接口
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

import java.util.List;

import com.comvee.cdms.defender.model.CfgGroupQuesModel;
import com.comvee.cdms.defender.model.PageRequestModel;

public interface CfgGroupQuesServiceI {


	/**
	 * @TODO 根据id获取场景题目配置表信息
	 * @param tid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgGroupQuesModel loadCfgGroupQuesById(Long tid) ;
	
	 /**
	 * @TODO 获取场景题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgGroupQuesModel> loadCfgGroupQues(PageRequestModel pager,Long gid) ;
	
	 /**
	 * @TODO 获取场景题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgGroupQuesModel> loadCfgGroupQues(Long gid) ;
	
	/**
	 * @TODO  统计场景题目配置记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countCfgGroupQues() ;
	
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
	void delCfgGroupQues(Long  tid) ;
	
	/**
	 * @TODO 题目排序
	 * @param  list 题目与分组关联列表
	 * @return
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-2-2
	 * 请求样例
	 */
	boolean sortCfgGroupQues(List<CfgGroupQuesModel> list);
	
	/**
	 * @TODO  批量添加题目与分组关联表记录
	 * @param 
	 * @return true 添加成功 false 添加失败
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-02-02
	 */
	boolean batchCfgGroupQues(List<CfgGroupQuesModel> list);
	
}
