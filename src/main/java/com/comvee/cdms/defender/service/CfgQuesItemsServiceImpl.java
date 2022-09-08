/**
 * @File name:   CfgQuesItemsServiceImpl.java  题目选项 service层接口实现类
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

import com.comvee.cdms.defender.mapper.CfgQuesItemsMapper;
import com.comvee.cdms.defender.model.CfgQuesItemsModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("cfgQuesItemsService")
public class CfgQuesItemsServiceImpl  implements CfgQuesItemsServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CfgQuesItemsServiceImpl.class);
	@Autowired
	private CfgQuesItemsMapper cfgQuesItemsMapper;


	@Override
	public CfgQuesItemsModel loadCfgQuesItemsById(Long sid){
	    return this.cfgQuesItemsMapper.loadCfgQuesItemsById(sid);
	}
	
	@Override
	public PageResult<CfgQuesItemsModel> loadCfgQuesItems(PageRequest pager, Long qid){
		if(pager != null){
			PageHelper.startPage(pager.getPage(), pager.getRows());
		}
		List<CfgQuesItemsModel> list = this.cfgQuesItemsMapper.loadCfgQuesItems(qid);
		return new PageResult<>(list);
	}
	
	@Override
	public long countCfgQuesItems(){
		return this.cfgQuesItemsMapper.countCfgQuesItems();
	}
	
	@Override
	public void addCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel){
		cfgQuesItemsModel.setSid(DaoHelper.getSeq());
		this.cfgQuesItemsMapper.addCfgQuesItems(cfgQuesItemsModel);
	}
	
	@Override	
	public void modifyCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel){
		this.cfgQuesItemsMapper.modifyCfgQuesItems(cfgQuesItemsModel);
	}
	
	@Override	
	public void delCfgQuesItems(Long  sid){
		this.cfgQuesItemsMapper.delCfgQuesItems(sid);
	}


}
