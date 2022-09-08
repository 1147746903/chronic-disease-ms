/**
 * @File name:   MediaMapper.java  媒体资源 mapper层接口
 * @Create on:  2018-7-30 15:15:57
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.mapper;

import com.comvee.cdms.defender.model.MediaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MediaMapper {

	/**
	 * @TODO 根据id获取媒体资源表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	MediaModel loadMediaById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取媒体资源分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	List<MediaModel> loadMedia(@Param("param") String param, @Param("type") Integer type) ;
	
	/**
	 * @TODO  统计媒体资源记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	long countMedia() ;
	

	/**
	 * @TODO  添加媒体资源记录
	 * @param MediaModel 媒体资源　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	void addMedia(MediaModel mediaModel) ;
	
	/**
	 * @TODO  修改媒体资源记录
	 * @param MediaModel 媒体资源　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	void modifyMedia(MediaModel mediaModel) ;
	
	/**
	 * @TODO  删除媒体资源记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-30 15:15:57
	 */
	void delMedia(@Param("sid") Long sid) ;
	
}
