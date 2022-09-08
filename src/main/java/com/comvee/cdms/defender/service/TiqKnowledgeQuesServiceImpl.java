/**
 * @File name:   KnowledgeQuesServiceImpl.java  文章课后习题 service层接口实现类
 * @Create on:  2018-7-28 16:52:22
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.TiqKnowledgeQuesMapper;
import com.comvee.cdms.defender.model.KnowledgeQuesModel;
import com.comvee.cdms.common.utils.DaoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tiqKnowledgeQuesService")
public class TiqKnowledgeQuesServiceImpl implements TiqKnowledgeQuesServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(TiqKnowledgeQuesServiceImpl.class);
	@Autowired
	private TiqKnowledgeQuesMapper tiqKnowledgeQuesMapper;


	@Override
	public KnowledgeQuesModel loadKnowledgeQuesById(Long sid){
	    return this.tiqKnowledgeQuesMapper.loadKnowledgeQuesById(sid);
	}
	
	@Override
	public List<KnowledgeQuesModel> loadKnowledgeQues(Long knowledgeId){
		return this.tiqKnowledgeQuesMapper.loadKnowledgeQues(knowledgeId);
	}
	
	@Override
	public long countKnowledgeQues(){
		return this.tiqKnowledgeQuesMapper.countKnowledgeQues();
	}
	
	@Override
	public void addKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel){
		knowledgeQuesModel.setSid(DaoHelper.getSeq());
		this.tiqKnowledgeQuesMapper.addKnowledgeQues(knowledgeQuesModel);
	}
	
	@Override	
	public void modifyKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel){
		this.tiqKnowledgeQuesMapper.modifyKnowledgeQues(knowledgeQuesModel);
	}
	
	@Override	
	public void delKnowledgeQues(Long  sid){
		this.tiqKnowledgeQuesMapper.delKnowledgeQues(sid);
	}

	/**
	 * 批量添加课后习题
	 * @param ids
	 * @param knowledgeId
	 */
	public void batchAddKnowledgeQues(List<Long> ids,Long knowledgeId){
		//删除现有
		this.tiqKnowledgeQuesMapper.delKnowledgeQuesByKnowledgeId(knowledgeId);
		for (Long id : ids) {
			KnowledgeQuesModel knowledgeQuesModel = new KnowledgeQuesModel();
			knowledgeQuesModel.setSid(DaoHelper.getSeq());
			knowledgeQuesModel.setKnowledgeId(knowledgeId.toString());
			knowledgeQuesModel.setQuesId(id.toString());
			this.tiqKnowledgeQuesMapper.addKnowledgeQues(knowledgeQuesModel);
		}
	}

}
