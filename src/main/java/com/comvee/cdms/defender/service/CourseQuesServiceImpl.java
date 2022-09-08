/**
 * @File name:   CourseQuesServiceImpl.java  课程题目关联 service层接口实现类
 * @Create on:  2018-9-14 16:41:07
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.service;

import com.comvee.cdms.defender.mapper.CourseQuesMapper;
import com.comvee.cdms.defender.model.CourseQuesModel;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("courseQuesService")
public class CourseQuesServiceImpl  implements CourseQuesServiceI{
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseQuesServiceImpl.class);
	@Autowired
	private CourseQuesMapper courseQuesMapper;


	@Override
	public CourseQuesModel loadCourseQuesById(Long sid){
	    return this.courseQuesMapper.loadCourseQuesById(sid);
	}
	
	@Override
	public PageResult<CourseQuesModel> loadCourseQues(PageRequest pager){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<CourseQuesModel> list = this.courseQuesMapper.loadCourseQues(null);
		return new PageResult<>(list);
	}
	
	@Override
	public List<CourseQuesModel> loadCourseQues(Long courseId) {
		return this.courseQuesMapper.loadCourseQues(courseId);
	}
	
	@Override
	public long countCourseQues(){
		return this.courseQuesMapper.countCourseQues();
	}
	
	@Override
	public void addCourseQues(CourseQuesModel courseQuesModel){
		courseQuesModel.setSid(DaoHelper.getSeq());
		this.courseQuesMapper.addCourseQues(courseQuesModel);
	}
	
	@Override	
	public void modifyCourseQues(CourseQuesModel courseQuesModel){
		this.courseQuesMapper.modifyCourseQues(courseQuesModel);
	}
	
	@Override	
	public void delCourseQues(Long  sid){
		this.courseQuesMapper.delCourseQues(sid);
	}

	/**
	 * 批量添加课后习题
	 * @param ids
	 * @param knowledgeId
	 */
	public void batchAddCourseQues(List<Long> ids,Long courseId){
		//删除现有
		this.courseQuesMapper.delCourseQuesByCourseId(courseId);
		for (int i=0;i<ids.size();i++) {
			Long id = ids.get(i);
			CourseQuesModel courseQuesModel = new CourseQuesModel();
			courseQuesModel.setSid(DaoHelper.getSeq());
			courseQuesModel.setCourseId(courseId.toString());
			courseQuesModel.setQid(id.toString());
			courseQuesModel.setSort(i+1);
			this.courseQuesMapper.addCourseQues(courseQuesModel);
		}
	}

}
