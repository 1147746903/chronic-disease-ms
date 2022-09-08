/**
 * @File name:   CfgGroupServiceImpl.java  分组场景 service层接口实现类
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comvee.cdms.defender.dto.CfgGroupDto;
import com.comvee.cdms.defender.mapper.CfgGroupMapper;
import com.comvee.cdms.defender.model.CfgGroupModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.github.pagehelper.PageHelper;


@Service("cfgGroupService")
public class CfgGroupServiceImpl  implements CfgGroupServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CfgGroupServiceImpl.class);
	@Autowired
	private CfgGroupMapper cfgGroupMapper;


	@Override
	public CfgGroupModel loadCfgGroupById(Long sid){
	    return this.cfgGroupMapper.loadCfgGroupById(sid);
	}
	
	@Override
	public List<CfgGroupModel> loadCfgGroup(PageRequestModel pager,String type,String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		CfgGroupDto CfgGroupDto = new CfgGroupDto();
		CfgGroupDto.setType(type);
		CfgGroupDto.setParam(param);
		return this.cfgGroupMapper.loadCfgGroup(CfgGroupDto);
	}
	
	@Override
	public List<CfgGroupModel> loadCfgGroup(String type) {
		CfgGroupDto CfgGroupDto = new CfgGroupDto();
		CfgGroupDto.setType(type);
		return this.cfgGroupMapper.loadCfgGroup(CfgGroupDto);
	}
	
	@Override
	public long countCfgGroup(){
		return this.cfgGroupMapper.countCfgGroup();
	}
	
	@Override
	public void addCfgGroup(CfgGroupModel cfgGroupModel){
		cfgGroupModel.setSid(DaoHelper.idSeq());
		this.cfgGroupMapper.addCfgGroup(cfgGroupModel);
	}
	
	@Override	
	public void modifyCfgGroup(CfgGroupModel cfgGroupModel){
		this.cfgGroupMapper.modifyCfgGroup(cfgGroupModel);
	}
	
	@Override	
	public void delCfgGroup(Long  sid){
		this.cfgGroupMapper.delCfgGroup(sid);
	}


}
