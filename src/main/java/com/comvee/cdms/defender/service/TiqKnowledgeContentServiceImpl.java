/**
 * @File name:   KnowledgeContentServiceImpl.java  文章内容正文 service层接口实现类
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

import com.comvee.cdms.defender.mapper.TiqKnowledgeContentMapper;
import com.comvee.cdms.defender.model.KnowledgeContentModel;
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


@Service("tiqKnowledgeContentService")
public class TiqKnowledgeContentServiceImpl implements TiqKnowledgeContentServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(TiqKnowledgeContentServiceImpl.class);
	@Autowired
	private TiqKnowledgeContentMapper tiqKnowledgeContentMapper;


	@Override
	public KnowledgeContentModel loadKnowledgeContentById(Long sid){
	    return this.tiqKnowledgeContentMapper.loadKnowledgeContentById(sid);
	}
	
	@Override
	public PageResult<KnowledgeContentModel> loadKnowledgeContent(PageRequest pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<KnowledgeContentModel> list = this.tiqKnowledgeContentMapper.loadKnowledgeContent(null);
		return new PageResult<>(list);
	}
	
	@Override
	public List<KnowledgeContentModel> loadContentByKnowledgeId(Long knowledgeId) {
		return this.tiqKnowledgeContentMapper.loadKnowledgeContent(knowledgeId);
	}
	
	@Override
	public long countKnowledgeContent(){
		return this.tiqKnowledgeContentMapper.countKnowledgeContent();
	}
	
	@Override
	public void addKnowledgeContent(KnowledgeContentModel knowledgeContentModel){
		knowledgeContentModel.setSid(DaoHelper.getSeq());
		Integer maxSort = this.tiqKnowledgeContentMapper.maxContentSort(Long.parseLong(knowledgeContentModel.getKnowledgeId()));
		knowledgeContentModel.setSort(maxSort.intValue() + 1);
		this.tiqKnowledgeContentMapper.addKnowledgeContent(knowledgeContentModel);
	}
	
	
	/**
	 * @TODO  添加文章内容正文记录
	 * @param KnowledgeContentModel 文章内容正文　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 18:59:05
	 */
	public void addKnowledgeContent(List<KnowledgeContentModel> list,Long knowledgeId ) {
		Integer maxSort = this.tiqKnowledgeContentMapper.maxContentSort(knowledgeId);
		for (int i= 0 ;i < list.size();i++) {
			KnowledgeContentModel knowledgeContentModel = list.get(i);
			knowledgeContentModel.setSid(DaoHelper.getSeq());
			knowledgeContentModel.setSort(maxSort.intValue() + 1 + i );
			this.tiqKnowledgeContentMapper.addKnowledgeContent(knowledgeContentModel);
		}
	}
	
	@Override	
	public void modifyKnowledgeContent(KnowledgeContentModel knowledgeContentModel){
		this.tiqKnowledgeContentMapper.modifyKnowledgeContent(knowledgeContentModel);
	}
	
	@Override	
	public void delKnowledgeContent(Long  sid){
		this.tiqKnowledgeContentMapper.delKnowledgeContent(sid);
	}

	@Override
	public void sortKnowledgeContent(String ids) {
		String[] split = ids.split(",");
		for (int i = 0; i < split.length; i++) {

			this.tiqKnowledgeContentMapper.sortKnowledgeContent(split[i],i+"");
		}
	}

}
