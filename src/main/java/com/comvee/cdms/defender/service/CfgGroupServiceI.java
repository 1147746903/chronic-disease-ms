/**
 * @File name:   CfgGroupServiceI.java  分组场景 service层接口
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

import com.comvee.cdms.defender.model.CfgGroupModel;
import com.comvee.cdms.defender.model.PageRequestModel;

public interface CfgGroupServiceI {


	/**
	 * @TODO 根据id获取分组场景表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	CfgGroupModel loadCfgGroupById(Long sid) ;
	
	 /**
	 * @TODO 获取分组场景分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgGroupModel> loadCfgGroup(PageRequestModel pager,String type,String param) ;
	
	 /**
	 * @TODO 获取分组场景分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	List<CfgGroupModel> loadCfgGroup(String type) ;
	
	/**
	 * @TODO  统计分组场景记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	long countCfgGroup() ;
	
	/**
	 * @TODO  添加分组场景记录
	 * @param CfgGroupModel 分组场景　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void addCfgGroup(CfgGroupModel cfgGroupModel) ;
	
	/**
	 * @TODO  修改分组场景记录
	 * @param CfgGroupModel 分组场景　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void modifyCfgGroup(CfgGroupModel cfgGroupModel) ;
	
	/**
	 * @TODO  删除分组场景记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-25 15:24:49
	 */
	void delCfgGroup(Long  sid) ;
	
}
