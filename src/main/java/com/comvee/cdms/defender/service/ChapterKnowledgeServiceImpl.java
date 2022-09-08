/**
 * @File name:   ChapterKnowledgeServiceImpl.java  章节文章关联表 service层接口实现类
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

import com.comvee.cdms.defender.mapper.ChapterKnowledgeMapper;
import com.comvee.cdms.defender.model.ChapterKnowledgeModel;
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


@Service("chapterKnowledgeService")
public class ChapterKnowledgeServiceImpl  implements ChapterKnowledgeServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChapterKnowledgeServiceImpl.class);
	@Autowired
	private ChapterKnowledgeMapper chapterKnowledgeMapper;


	@Override
	public ChapterKnowledgeModel loadChapterKnowledgeById(Long sid){
	    return this.chapterKnowledgeMapper.loadChapterKnowledgeById(sid);
	}
	
	@Override
	public PageResult<ChapterKnowledgeModel> loadChapterKnowledge(PageRequest pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<ChapterKnowledgeModel> list = this.chapterKnowledgeMapper.loadChapterKnowledge();
		return new PageResult<>(list);
	}
	
	@Override
	 	
	/**
	 * 章节id
	 * @param chapterId
	 * @return
	 */
	public List<KnowledgeModel> loadKnowledgeRelation(Long chapterId){
		return this.chapterKnowledgeMapper.loadKnowledgeRelation(chapterId);
	}
	
	@Override
	public long countChapterKnowledge(){
		return this.chapterKnowledgeMapper.countChapterKnowledge();
	}
	
	@Override
	public void addChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel){
		chapterKnowledgeModel.setSid(DaoHelper.getSeq());
		this.chapterKnowledgeMapper.addChapterKnowledge(chapterKnowledgeModel);
	}
	
	@Override	
	public void modifyChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel){
		this.chapterKnowledgeMapper.modifyChapterKnowledge(chapterKnowledgeModel);
	}
	
	@Override	
	public void delChapterKnowledge(Long  sid){
		this.chapterKnowledgeMapper.delChapterKnowledge(sid);
	}

	@Override	
	public void batchAddChapterKnowledge(List<Long> ids ,Long chapterId){
		this.chapterKnowledgeMapper.delByChapterId(chapterId);
		for (int i=0;i< ids.size();i++) {
			ChapterKnowledgeModel chapterKnowledgeModel = new ChapterKnowledgeModel();
			chapterKnowledgeModel.setChapterId(chapterId+"");
			chapterKnowledgeModel.setSid(DaoHelper.getSeq());
			chapterKnowledgeModel.setSort(i+1);
			chapterKnowledgeModel.setKnowledgeId(ids.get(i)+"");
			this.chapterKnowledgeMapper.addChapterKnowledge(chapterKnowledgeModel);
		}
	}

}
