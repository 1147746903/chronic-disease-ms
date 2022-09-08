/**
 * @File name:   KnowledgeServiceImpl.java  知识点表 service层接口实现类
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.TiqKnowledgeMapper;
import com.comvee.cdms.defender.model.KnowledgeModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tiqKnowledgeService")
public class TiqKnowledgeServiceImpl implements TiqKnowledgeServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(TiqKnowledgeServiceImpl.class);
	@Autowired
	private TiqKnowledgeMapper tiqKnowledgeMapper;


	@Override
	public KnowledgeModel loadKnowledgeById(Long id){
	    return this.tiqKnowledgeMapper.loadKnowledgeById(id);
	}
	
	@Override
	public PageResult<KnowledgeModel> loadKnowledge(PageRequest pager, Integer type, String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<KnowledgeModel> list = this.tiqKnowledgeMapper.loadKnowledge(type, param);
		return new PageResult<>(list);
	}
	
	@Override
	public List<KnowledgeModel> loadAllKnowledge() {
		return this.tiqKnowledgeMapper.loadKnowledge(null,null);
	}
	
	@Override
	public long countKnowledge(){
		return this.tiqKnowledgeMapper.countKnowledge();
	}
	
	@Override
	public void addKnowledge(KnowledgeModel knowledgeModel){
		knowledgeModel.setId(DaoHelper.getSeq());
		this.tiqKnowledgeMapper.addKnowledge(knowledgeModel);
	}
	
	@Override	
	public void modifyKnowledge(KnowledgeModel knowledgeModel){
		this.tiqKnowledgeMapper.modifyKnowledge(knowledgeModel);
	}
	
	@Override	
	public void delKnowledge(Long  id){
		this.tiqKnowledgeMapper.delKnowledge(id);
	}


}
