/**
 * @File name:   ClientEventMapper.java  用户行为日志埋点 mapper层接口
 * @Create on:  2018-9-18 14:20:31
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.wechat.model.ClientEventModel;

public interface ClientEventMapper {

	/**
	 * @TODO 根据id获取用户行为日志埋点表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	ClientEventModel loadClientEventById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取用户行为日志埋点分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	List<ClientEventModel> loadClientEvent() ;
	
	/**
	 * @TODO  统计用户行为日志埋点记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	long countClientEvent() ;
	

	/**
	 * @TODO  添加用户行为日志埋点记录
	 * @param ClientEventModel 用户行为日志埋点　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	void addClientEvent(ClientEventModel clientEventModel) ;
	
	/**
	 * @TODO  修改用户行为日志埋点记录
	 * @param ClientEventModel 用户行为日志埋点　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	void modifyClientEvent(ClientEventModel clientEventModel) ;
	
	/**
	 * @TODO  删除用户行为日志埋点记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-9-18 14:20:31
	 */
	void delClientEvent(@Param("sid") Long  sid) ;
	
}
