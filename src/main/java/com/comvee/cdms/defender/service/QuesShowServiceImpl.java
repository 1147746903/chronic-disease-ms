/**
 * @File name:   QuesShowServiceImpl.java  题目话术 service层接口实现类
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

import com.comvee.cdms.defender.mapper.QuesShowMapper;
import com.comvee.cdms.defender.model.QuesShowModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("quesShowService")
public class QuesShowServiceImpl  implements QuesShowServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(QuesShowServiceImpl.class);
	@Autowired
	private QuesShowMapper quesShowMapper;


	@Override
	public QuesShowModel loadQuesShowById(Long sid){
	    return this.quesShowMapper.loadQuesShowById(sid);
	}
	
	@Override
	public PageResult<QuesShowModel> loadQuesShow(PageRequest pager, Long qid){
		if(pager != null){
			PageHelper.startPage(pager.getPage(), pager.getRows());
		}
		List<QuesShowModel> list = this.quesShowMapper.loadQuesShow(qid);
		return new PageResult<>(list);
	}
	
	@Override
	public long countQuesShow(){
		return this.quesShowMapper.countQuesShow();
	}
	
	@Override
	public void addQuesShow(QuesShowModel quesShowModel){
		quesShowModel.setSid(DaoHelper.getSeq());
		this.quesShowMapper.addQuesShow(quesShowModel);
	}
	
	@Override	
	public void modifyQuesShow(QuesShowModel quesShowModel){
		this.quesShowMapper.modifyQuesShow(quesShowModel);
	}
	
	@Override	
	public void delQuesShow(Long  sid){
		this.quesShowMapper.delQuesShow(sid);
	}


}
