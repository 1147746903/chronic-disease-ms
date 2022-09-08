/**
 * @File name:   CourseMapper.java  课程表 mapper层接口
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.mapper;

import java.util.List;
import java.util.Map;

import com.comvee.cdms.defender.model.CourseClassifyModel;
import com.comvee.cdms.defender.wechat.po.CourseSearchHistoryPO;
import com.comvee.cdms.defender.wechat.po.MemberRecommendCoursePO;
import com.comvee.cdms.defender.wechat.vo.ListCourseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import com.comvee.cdms.defender.model.CfgCourseRecommendRuleModel;
import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.model.PatientRecommendModel;

public interface CourseMapper {

	/**
	 * @TODO 根据id获取课程表表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	CourseModel loadCourseById(@Param("sid") Long sid) ;
	
	 /**
	 * @param param 
	 * @TODO 获取课程表分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	List<CourseModel> loadCourse(Map<String, Object> param) ;

	List<CourseModel> loadAllCourse() ;

	List<CourseModel> loadCourseByPid(Map<String, Object> param) ;
	
	/**
	 * @TODO  统计课程表记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	long countCourse() ;
	

	/**
	 * @TODO  添加课程表记录
	 * @param CourseModel 课程表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void addCourse(CourseModel courseModel) ;
	
	/**
	 * @TODO  修改课程表记录
	 * @param CourseModel 课程表　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void modifyCourse(CourseModel courseModel) ;
	
	/**
	 * @TODO  删除课程表记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void delCourse(@Param("sid") Long  sid) ;
	
	/**
	 * 获取课程推荐规则配置
	 * @return
	 */
	List<CfgCourseRecommendRuleModel> loadCourseRecommendRule();
	
	/**
	 * 根据课程id获取课程
	 * @param param
	 * @return
	 */
	List<CourseModel> loadCourseByCourseIds(Map<String, Object> param);
	
	/**
	 * 用户推荐课程
	 * @param param
	 * @return
	 */
	PatientRecommendModel loadPatientRecommend(Map<String, Object> param);
	
	/**
	 * 已选最多课程排序
	 * @return
	 */
	@Cacheable(value="cache_patient",key="'hotCourse'")
	List<CourseModel> listHotCourse();

	List<CourseModel> loadCourseByClassifyCode(@Param("classifyCode") String classifyCode);

	/**
	 *  添加管理处方 课程关联
	 * @param model
	 */
	void addCourseClassify(CourseClassifyModel model);

	/**
	 * 修改管理处方 课程关联
	 * @param model
	 */
	void updateCourseClassify(CourseClassifyModel model);

	/**
	 * 根据课程id查询关联信息
	 * @param courseId
	 * @return
	 */
	CourseClassifyModel getCourseClassifyByCourseId(@Param("courseId") String courseId);

	/**
	 * 根据课程信息删除关联信息
	 * @param courseId
	 */
	void delCourseClassifyByCourseId(@Param("courseId") String courseId);

	/**
	 * 根据关键词搜索课程
	 * @param keyword
	 * @return
	 */
	List<ListCourseVO> searchCourse(@Param("keyword") String keyword);

	List<ListCourseVO> listCourseById(@Param("list") List<String> list);

	List<ListCourseVO> searchCourseByName(@Param("keyword") String keyword, @Param("memberId") String memberId);

	List<ListCourseVO> searchCourseByMemo(@Param("keyword") String keyword, @Param("memberId") String memberId);


	/**
	 * 添加搜索历史记录
	 * @param courseSearchHistoryPO
	 * @return
	 */
	int addCourseSearchHistory(CourseSearchHistoryPO courseSearchHistoryPO);

	/**
	 * 清空历史搜索记录
	 * @param memberId
	 * @return
	 */
	int delCourseSearchHistory(@Param("memberId") String memberId);


	/**
	 * 获取历史搜索记录
	 * @param memberId
	 * @return
	 */
	List<String> loadCourseSearchHistory(@Param("memberId") String memberId);

	/**
	 *获取最新添加的不是已选的限定数量课程id
	 * @param param limit memberId
	 * @return
	 */
	List<String> loadCourseLimit(Map<String, Object> param);

	//最新添加的10条课程id
	List<String> loadCourseNew();


	/**
	 * 根据课程id获取课程列表vo
	 * @param list
	 * @return
	 */
	List<ListCourseVO> listCourseVoByIds(@Param("list") List<String> list,@Param("memberId") String memberId);

	List<ListCourseVO> listCourseVoByMemberId(@Param("memberId") String memberId);


	/**
	 * 批量添加推荐课程
	 * @param list
	 */
	void batchAddMemberRecommendCourses(List<MemberRecommendCoursePO> list);

	/**
	 * 根据id批量失效
	 * @param list
	 */
	void batchDelMemberRecommendCourse(@Param("list") List<String> list);

	/**
	 * 获取患者当前推荐课程vo
	 * @param
	 */
	List<ListCourseVO> loadMemberRecommendCourses(@Param("memberId") String memberId);
	/**
	 * 获取患者当前推荐课程po
	 * @param
	 */
	List<MemberRecommendCoursePO> loadMemberRecommendCoursePOs(@Param("memberId") String memberId);

	/**
	 * 所有已生成推荐课程的用户列表
	 * @return
	 */
	List<String> loadExistRecommendCourseMembers();
}
