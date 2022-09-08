/**
 * @File name:   CfgQuesServiceI.java  题目配置 service层接口
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


import com.comvee.cdms.defender.model.CfgQuesModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;
import java.util.Map;

public interface CfgQuesServiceI {


	/**
	 * @TODO 根据id获取题目配置表信息
	 * @param qid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgQuesModel loadCfgQuesById(Long qid) ;
	
	 /**
	 * @TODO 获取题目配置分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	 PageResult<CfgQuesModel> loadCfgQues(PageRequest pager, Long label, String param) ;
	
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
	void delCfgQues(Long qid) ;
	
	/**
	 * @TODO 获取题库配置信息
	 * @return
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-2-2
	 * 请求样例
	 */
	Map<String,CfgQuesModel> loadCfgQues();
	
	
	/**
	 * @TODO 获取题库配置信息
	 * @return
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-2-2
	 * 请求样例
	 */
	List<CfgQuesModel> loadAllCfgQues();
	
	/**
	 * @TODO 批量删除题库
	 * @param qids
	 * @return
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-3-9
	 */
	boolean batchDelQues(List<String> qids);
	
	/**
	 * @TODO 移动分组
	 * @param quesList
	 * @param label 分组
	 * @return
	 * @throws HealthException
	 * @author zhengsw
	 * @date 2016-3-9
	 */
	boolean moveQuesLabel(List<String> qids, String label);
	
}
