/**
 * @File name:   ClientEventServiceImpl.java  用户行为日志埋点 service层接口实现类
 * @Create on:  2018-9-18 14:20:31
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.wechat.mapper.ClientEventMapper;
import com.comvee.cdms.defender.wechat.model.ClientEventModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.github.pagehelper.PageHelper;


@Service("clientEventService")
public class ClientEventServiceImpl  implements ClientEventServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientEventServiceImpl.class);
	@Autowired
	private ClientEventMapper clientEventMapper;


	@Override
	public ClientEventModel loadClientEventById(Long sid){
	    return this.clientEventMapper.loadClientEventById(sid);
	}
	
	@Override
	public List<ClientEventModel> loadClientEvent(PageRequestModel pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		return this.clientEventMapper.loadClientEvent();
	}
	
	@Override
	public long countClientEvent(){
		return this.clientEventMapper.countClientEvent();
	}
	
	@Override
	public void addClientEvent(ClientEventModel clientEventModel){
		if(clientEventModel == null || clientEventModel.getName() == null || clientEventModel.getTm() == null
				|| clientEventModel.getUrl() == null || clientEventModel.getSimuid() == null){
			return ;
		}
		clientEventModel.setSid(DaoHelper.idSeq());
		this.clientEventMapper.addClientEvent(clientEventModel);
	}
	
	@Override	
	public void modifyClientEvent(ClientEventModel clientEventModel){
		this.clientEventMapper.modifyClientEvent(clientEventModel);
	}
	
	@Override	
	public void delClientEvent(Long  sid){
		this.clientEventMapper.delClientEvent(sid);
	}


}
