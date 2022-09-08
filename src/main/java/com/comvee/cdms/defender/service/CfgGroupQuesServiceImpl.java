/**
 * @File name:   CfgGroupQuesServiceImpl.java  场景题目配置 service层接口实现类
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

import com.comvee.cdms.defender.dto.SortGroupQuesDto;
import com.comvee.cdms.defender.mapper.CfgGroupQuesMapper;
import com.comvee.cdms.defender.mapper.QuesRuleMapper;
import com.comvee.cdms.defender.mapper.QuesSuggestMapper;
import com.comvee.cdms.defender.model.CfgGroupQuesModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.github.pagehelper.PageHelper;


@Service("cfgGroupQuesService")
public class CfgGroupQuesServiceImpl  implements CfgGroupQuesServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CfgGroupQuesServiceImpl.class);
	@Autowired
	private CfgGroupQuesMapper cfgGroupQuesMapper;
	
	@Autowired
	private QuesRuleMapper quesRuleMapper;
	
	
	@Autowired
	private QuesSuggestMapper quesSuggestMapper;

	@Override
	public CfgGroupQuesModel loadCfgGroupQuesById(Long tid){
	    return this.cfgGroupQuesMapper.loadCfgGroupQuesById(tid);
	}
	
	@Override
	public List<CfgGroupQuesModel> loadCfgGroupQues(PageRequestModel pager,Long gid){
		if(pager != null){
			PageHelper.startPage(pager.getPage(), pager.getRows());
		}
		return this.cfgGroupQuesMapper.loadCfgGroupQues(gid);
	}
	
	@Override
	public List<CfgGroupQuesModel> loadCfgGroupQues(Long gid){
		return this.cfgGroupQuesMapper.loadCfgGroupQues(gid);
	}
	
	@Override
	public long countCfgGroupQues(){
		return this.cfgGroupQuesMapper.countCfgGroupQues();
	}
	
	@Override
	public void addCfgGroupQues(CfgGroupQuesModel cfgGroupQuesModel){
		cfgGroupQuesModel.setTid(DaoHelper.idSeq());
		Long sort = this.cfgGroupQuesMapper.loadGroupQuesMaxSort(cfgGroupQuesModel.getGroupId());
		cfgGroupQuesModel.setSort(sort.intValue());
		this.cfgGroupQuesMapper.addCfgGroupQues(cfgGroupQuesModel);
	}
	
	@Override	
	public void modifyCfgGroupQues(CfgGroupQuesModel cfgGroupQuesModel){
		this.cfgGroupQuesMapper.modifyCfgGroupQues(cfgGroupQuesModel);
	}
	
	@Override	
	public void delCfgGroupQues(Long  tid){
		//删除跳转
		this.quesRuleMapper.delQuesRuleByTid(tid);
		//删除建议
		this.quesSuggestMapper.delQuesSuggestByTid(tid);
		this.cfgGroupQuesMapper.delCfgGroupQues(tid);
	}

	@Override
	public boolean sortCfgGroupQues(List<CfgGroupQuesModel> list){
		for(CfgGroupQuesModel cfgGroupQuesModel: list){
			SortGroupQuesDto sortGroupQuesDto = new SortGroupQuesDto();
			sortGroupQuesDto.setGroupId(cfgGroupQuesModel.getGroupId());
			sortGroupQuesDto.setSort(cfgGroupQuesModel.getSort());
			sortGroupQuesDto.setQid(cfgGroupQuesModel.getQid());
			
			this.cfgGroupQuesMapper.sortCfgGroupQues(sortGroupQuesDto);
		 }
		 return true;
	}
	
	@Override	
	public boolean batchCfgGroupQues(List<CfgGroupQuesModel> list){
		for (CfgGroupQuesModel bean:list) {
			CfgGroupQuesModel model = this.cfgGroupQuesMapper.loadGroupQuesByQid(bean.getQid(), bean.getGroupId());
			if(model != null && model.getTid() != null){//已存在就不添加
			} else {
				 this.cfgGroupQuesMapper.addCfgGroupQues(bean);
			}
		}
		return true;
	}

}
