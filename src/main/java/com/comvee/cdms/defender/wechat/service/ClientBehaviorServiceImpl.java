/**
 * @File name:   ClientBehaviorServiceImpl.java  页面访问日志表 service层接口实现类
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

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.wechat.mapper.ClientBehaviorMapper;
import com.comvee.cdms.defender.wechat.model.ClientBehaviorModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("clientBehaviorService")
public class ClientBehaviorServiceImpl  implements ClientBehaviorServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientBehaviorServiceImpl.class);
	@Autowired
	private ClientBehaviorMapper clientBehaviorMapper;


	@Override
	public ClientBehaviorModel loadClientBehaviorById(Long sid){
	    return this.clientBehaviorMapper.loadClientBehaviorById(sid);
	}
	
	@Override
	public List<ClientBehaviorModel> loadClientBehavior(PageRequestModel pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		return this.clientBehaviorMapper.loadClientBehavior();
	}
	
	@Override
	public long countClientBehavior(){
		return this.clientBehaviorMapper.countClientBehavior();
	}
	
	@Override
	public void addClientBehavior(ClientBehaviorModel clientBehaviorModel){
		if(clientBehaviorModel.getPageId() == null || clientBehaviorModel.getVisitTm() == null){
			return;
		}
		//获取上一条记录信息
		ClientBehaviorModel preBehavior = this.clientBehaviorMapper.loadPrePage(clientBehaviorModel.getSimuid(), clientBehaviorModel.getCookieId());
		if(preBehavior != null && preBehavior.getSid() != null){
			LOGGER.info(JSON.toJSONString(preBehavior));
			//计算时间
			Long time = (clientBehaviorModel.getVisitTm() - preBehavior.getVisitTm());
			preBehavior.setTime(time.intValue());
			this.clientBehaviorMapper.modifyClientBehavior(preBehavior);
		}
		
		clientBehaviorModel.setTime(0);
		clientBehaviorModel.setSid(DaoHelper.idSeq());
		this.clientBehaviorMapper.addClientBehavior(clientBehaviorModel);
	}
	
	
	@Override
	public void addClientBehaviorTime(ClientBehaviorModel clientBehaviorModel){
		if(clientBehaviorModel.getPageId() == null || clientBehaviorModel.getVisitTm() == null){
			return;
		}
		clientBehaviorModel.setSid(DaoHelper.idSeq());
		this.clientBehaviorMapper.addClientBehavior(clientBehaviorModel);
	}
	
	@Override	
	public void modifyClientBehavior(ClientBehaviorModel clientBehaviorModel){
		this.clientBehaviorMapper.modifyClientBehavior(clientBehaviorModel);
	}
	
	@Override	
	public void delClientBehavior(Long  sid){
		this.clientBehaviorMapper.delClientBehavior(sid);
	}


}
