/**
 * @File name:   CourseChapterServiceImpl.java  课程章节 service层接口实现类
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.CourseChapterMapper;
import com.comvee.cdms.defender.model.CourseChapterModel;
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


@Service("courseChapterService")
public class CourseChapterServiceImpl  implements CourseChapterServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseChapterServiceImpl.class);
	@Autowired
	private CourseChapterMapper courseChapterMapper;


	@Override
	public CourseChapterModel loadCourseChapterById(Long sid){
	    return this.courseChapterMapper.loadCourseChapterById(sid);
	}
	
	@Override
	public PageResult<CourseChapterModel> loadCourseChapter(PageRequest pager, Long courseId, String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId",courseId);
		map.put("param", param);
		List<CourseChapterModel> list = this.courseChapterMapper.loadCourseChapter(map);
		return new PageResult<>(list);
	}
	
	@Override
	public long countCourseChapter(){
		return this.courseChapterMapper.countCourseChapter();
	}
	
	@Override
	public void addCourseChapter(CourseChapterModel courseChapterModel){
		courseChapterModel.setSid(DaoHelper.getSeq());
		int sort = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId",courseChapterModel.getCourseId());
		List list =  this.courseChapterMapper.loadCourseChapter(map);
		if(list!=null ){
			sort = list.size()+1;
		}
		courseChapterModel.setSort(sort);
		this.courseChapterMapper.addCourseChapter(courseChapterModel);
	}
	
	@Override	
	public void modifyCourseChapter(CourseChapterModel courseChapterModel){
		this.courseChapterMapper.modifyCourseChapter(courseChapterModel);
	}
	
	@Override	
	public void delCourseChapter(Long  sid){
		this.courseChapterMapper.delCourseChapter(sid);
	}


}
