/**
 * @File name:   MediaServiceImpl.java  媒体资源 service层接口实现类
 * @Create on:  2018-7-30 15:15:57
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.MediaMapper;
import com.comvee.cdms.defender.model.MediaModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("mediaService")
public class MediaServiceImpl  implements MediaServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);
	@Autowired
	private MediaMapper mediaMapper;


	@Override
	public MediaModel loadMediaById(Long sid){
	    return this.mediaMapper.loadMediaById(sid);
	}
	
	@Override
	public PageResult<MediaModel> loadMedia(PageRequest pager, String param, Integer type){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<MediaModel> list = this.mediaMapper.loadMedia(param, type);
		return new PageResult<>(list);
	}
	
	@Override
	public long countMedia(){
		return this.mediaMapper.countMedia();
	}
	
	@Override
	public void addMedia(MediaModel mediaModel){
		MediaModel model = new MediaModel();
		model.setSid(DaoHelper.getSeq());
		model.setName(mediaModel.getName());
		model.setType(mediaModel.getType());
		model.setUrl(mediaModel.getUrl());
		if (model.getType() == 3){
			model.setMemo(mediaModel.getMemo());
		}
		this.mediaMapper.addMedia(model);
	}
	
	@Override	
	public void modifyMedia(MediaModel mediaModel){
		this.mediaMapper.modifyMedia(mediaModel);
	}
	
	@Override	
	public void delMedia(Long  sid){
		this.mediaMapper.delMedia(sid);
	}


}
