/**
 * @File name:   CfgTagMapper.java  标签 mapper层接口
 * @Create on:  2018-7-26 19:03:27
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.mapper;

import com.comvee.cdms.defender.model.CfgTagModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CfgTagMapper {

	/**
	 * @TODO 根据id获取标签表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	CfgTagModel loadCfgTagById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取标签分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	List<CfgTagModel> loadCfgTag(@Param("param") String param) ;
	
	/**
	 * @TODO  统计标签记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	long countCfgTag() ;
	

	/**
	 * @TODO  添加标签记录
	 * @param CfgTagModel 标签　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	void addCfgTag(CfgTagModel cfgTagModel) ;
	
	/**
	 * @TODO  修改标签记录
	 * @param CfgTagModel 标签　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	void modifyCfgTag(CfgTagModel cfgTagModel) ;
	
	/**
	 * @TODO  删除标签记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-26 19:03:27
	 */
	void delCfgTag(@Param("sid") Long sid) ;
	
}
