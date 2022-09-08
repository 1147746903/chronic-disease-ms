/**
 * @File name:   CfgQuesServiceImpl.java  题目配置 service层接口实现类
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

import com.comvee.cdms.defender.mapper.CfgQuesMapper;
import com.comvee.cdms.defender.model.CfgQuesModel;
import com.comvee.cdms.defender.model.PageRequestModel;
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


@Service("cfgQuesService")
public class CfgQuesServiceImpl  implements CfgQuesServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CfgQuesServiceImpl.class);
	@Autowired
	private CfgQuesMapper cfgQuesMapper;


	@Override
	public CfgQuesModel loadCfgQuesById(Long qid){
	    return this.cfgQuesMapper.loadCfgQuesById(qid);
	}

	@Override
	public PageResult<CfgQuesModel> loadCfgQues(PageRequest pager,Long label,String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<CfgQuesModel> list = this.cfgQuesMapper.loadCfgQues(label, param);
		return new PageResult<>(list);
	}

	@Override
	public long countCfgQues(){
		return this.cfgQuesMapper.countCfgQues();
	}
	
	@Override
	public void addCfgQues(CfgQuesModel cfgQuesModel){
		if(cfgQuesModel.getQid() == null){
			cfgQuesModel.setQid(DaoHelper.getSeq());
		}
		this.cfgQuesMapper.addCfgQues(cfgQuesModel);
	}
	
	@Override	
	public void modifyCfgQues(CfgQuesModel cfgQuesModel){
		this.cfgQuesMapper.modifyCfgQues(cfgQuesModel);
	}
	
	@Override	
	public void delCfgQues(Long  qid){
		this.cfgQuesMapper.delCfgQues(qid);
	}

	@Override
	public Map<String,CfgQuesModel> loadCfgQues() {
		 Map<String,CfgQuesModel> map = new HashMap<String,CfgQuesModel>();
		 List<CfgQuesModel> list  = this.cfgQuesMapper.loadCfgQues(null,null);
		 for (CfgQuesModel model : list) {
			map.put(model.getQid().toString(), model);
		 }
		 return map;
	}
	
	@Override
	public List<CfgQuesModel> loadAllCfgQues(){
		List<CfgQuesModel> list  = this.cfgQuesMapper.loadCfgQues(null,null);
		return list;
	}

	
	@Override	
	public boolean batchDelQues(List<String> qids){
		for (int i = 0; i < qids.size(); i++) {
			this.cfgQuesMapper.delCfgQues(Long.parseLong(qids.get(i)));
		}
		return true;
	}
	@Override	
	public boolean moveQuesLabel(List<String> qids,String label){
		for (int i = 0; i < qids.size(); i++) {
			CfgQuesModel cfgQuesModel = new CfgQuesModel();
			cfgQuesModel.setQid(qids.get(i));
			cfgQuesModel.setLabel(label);
			this.cfgQuesMapper.modifyCfgQues(cfgQuesModel);
		}
		return true;
	}
}
