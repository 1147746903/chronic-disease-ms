/**
 * @File name:   ClientBehaviorServiceI.java  页面访问日志表 service层接口
 * @Create on:  2018-9-18 14:33:51
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.wechat.service;

import java.util.List;

import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.wechat.model.ClientBehaviorModel;

public interface ClientBehaviorServiceI {


	/**
	 * @TODO 根据id获取页面访问日志表表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	ClientBehaviorModel loadClientBehaviorById(Long sid) ;
	
	 /**
	 * @TODO 获取页面访问日志表分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	List<ClientBehaviorModel> loadClientBehavior(PageRequestModel pager) ;
	
	/**
	 * @TODO  统计页面访问日志表记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	long countClientBehavior() ;
	
	/**
	 * @TODO  添加页面访问日志表记录
	 * @param ClientBehaviorModel 页面访问日志表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	void addClientBehavior(ClientBehaviorModel clientBehaviorModel) ;
	
	/**
	 * @TODO  添加页面访问日志表记录
	 * @param ClientBehaviorModel 页面访问日志表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	void addClientBehaviorTime(ClientBehaviorModel clientBehaviorModel) ;
	
	/**
	 * @TODO  修改页面访问日志表记录
	 * @param ClientBehaviorModel 页面访问日志表　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	void modifyClientBehavior(ClientBehaviorModel clientBehaviorModel) ;
	
	/**
	 * @TODO  删除页面访问日志表记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:33:51
	 */
	void delClientBehavior(Long  sid) ;
	
}
