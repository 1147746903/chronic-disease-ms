/**
 * @File name:   QuesLableServiceImpl.java  题目分组 service层接口实现类
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

import com.comvee.cdms.defender.mapper.QuesLableMapper;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.model.QuesLableModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("quesLableService")
public class QuesLableServiceImpl  implements QuesLableServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(QuesLableServiceImpl.class);
	@Autowired
	private QuesLableMapper quesLableMapper;


	@Override
	public QuesLableModel loadQuesLableById(Long sid) {
		return this.quesLableMapper.loadQuesLableById(sid);
	}


	@Override
	public List<QuesLableModel> loadQuesLable(PageRequestModel pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		return this.quesLableMapper.loadQuesLable();
	}

	@Override
	public PageResult<QuesLableModel> loadQuesLable(PageRequest page){
		PageHelper.startPage(page.getPage(),page.getRows());
		List<QuesLableModel> result = this.quesLableMapper.loadQuesLable();
		return new PageResult<>(result);
	}

	@Override
	public Map<String,QuesLableModel> quesLable() {
		Map<String,QuesLableModel>  map = new HashMap<String,QuesLableModel>();
		List<QuesLableModel> list = this.quesLableMapper.loadQuesLable();
		for (QuesLableModel quesLableModel : list) {
			map.put(quesLableModel.getSid().toString(), quesLableModel);
		}
		return map;
	}
	
	@Override
	public long countQuesLable(){
		return this.quesLableMapper.countQuesLable();
	}
	
	@Override
	public void addQuesLable(QuesLableModel quesLableModel){
		quesLableModel.setSid(DaoHelper.getSeq());
		
		this.quesLableMapper.addQuesLable(quesLableModel);
	}
	
	@Override	
	public void modifyQuesLable(QuesLableModel quesLableModel){
		this.quesLableMapper.modifyQuesLable(quesLableModel);
	}
	
	@Override	
	public void delQuesLable(Long  sid){
		this.quesLableMapper.delQuesLable(sid);
	}


}
