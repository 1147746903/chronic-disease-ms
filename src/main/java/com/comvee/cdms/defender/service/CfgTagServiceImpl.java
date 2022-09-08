/**
 * @File name:   CfgTagServiceImpl.java  标签 service层接口实现类
 * @Create on:  2018-7-26 19:03:27
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.CfgTagMapper;
import com.comvee.cdms.defender.model.CfgTagModel;
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


@Service("cfgTagService")
public class CfgTagServiceImpl  implements CfgTagServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CfgTagServiceImpl.class);
	@Autowired
	private CfgTagMapper cfgTagMapper;


	@Override
	public CfgTagModel loadCfgTagById(Long sid){
	    return this.cfgTagMapper.loadCfgTagById(sid);
	}
	
	@Override
	public PageResult<CfgTagModel> loadCfgTag(PageRequest pager,String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<CfgTagModel> list = this.cfgTagMapper.loadCfgTag(param);
		return new PageResult<>(list);
	}
	
	@Override
	public List<CfgTagModel> loadCfgTag(){
		return this.cfgTagMapper.loadCfgTag(null);
	}
	
	@Override
	public long countCfgTag(){
		return this.cfgTagMapper.countCfgTag();
	}
	
	@Override
	public void addCfgTag(CfgTagModel cfgTagModel){
		cfgTagModel.setSid(DaoHelper.getSeq());
		this.cfgTagMapper.addCfgTag(cfgTagModel);
	}
	
	@Override	
	public void modifyCfgTag(CfgTagModel cfgTagModel){
		this.cfgTagMapper.modifyCfgTag(cfgTagModel);
	}
	
	@Override	
	public void delCfgTag(Long  sid){
		this.cfgTagMapper.delCfgTag(sid);
	}


}
