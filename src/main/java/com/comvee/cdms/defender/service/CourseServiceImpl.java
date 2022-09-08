/**
 * @File name:   CourseServiceImpl.java  课程表 service层接口实现类
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.defender.common.cfg.ConstantCourse;
import com.comvee.cdms.defender.common.cfg.ConstantErrorMessage;
import com.comvee.cdms.defender.common.cfg.InterveneConstant;
import com.comvee.cdms.defender.common.exception.DiabetesMsException;
import com.comvee.cdms.defender.mapper.*;
import com.comvee.cdms.defender.model.*;
import com.comvee.cdms.defender.wechat.mapper.BarrierCourseQuesCfgMapper;
import com.comvee.cdms.defender.wechat.mapper.MemberCourseBarrierMapper;
import com.comvee.cdms.defender.wechat.mapper.PatientCourseMapper;
import com.comvee.cdms.defender.wechat.model.CourseRemindModel;
import com.comvee.cdms.defender.wechat.model.PatientChapterAnswerModel;
import com.comvee.cdms.defender.wechat.model.PatientChapterModel;
import com.comvee.cdms.defender.wechat.model.PatientCourseModel;
import com.comvee.cdms.defender.wechat.po.BarrierCourseQuesCfgPO;
import com.comvee.cdms.defender.wechat.po.CourseSearchHistoryPO;
import com.comvee.cdms.defender.wechat.po.MemberCourseBarrierPO;
import com.comvee.cdms.defender.wechat.po.MemberRecommendCoursePO;
import com.comvee.cdms.defender.wechat.vo.ListCourseVO;
import com.comvee.cdms.follow.cfg.FollowConstant;
import com.comvee.cdms.follow.po.MainFollowPO;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.knowledge.model.KnowledgePlanModel;
import com.comvee.cdms.knowledge.model.PageData;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.GetPrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionPO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service("courseService")
public class CourseServiceImpl  implements CourseServiceI {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseChapterMapper courseChapterMapper;

	@Autowired
	private PatientCourseMapper patientCourseMapper;

	@Autowired
	private TiqKnowledgeMapper knowledgeMapper;

	@Autowired
	private TiqKnowledgeContentMapper knowledgeContentMapper;

	@Autowired
	private CfgQuesMapper cfgQuesMapper;

	@Autowired
	private CfgQuesItemsMapper cfgQuesItemsMapper;

//	@Autowired
//	private PatientMapper patientMapper;

	@Autowired
	private MemberMapper memberMapper;

//	@Autowired
//	private DxyOutsideI dxyOutside;

	@Autowired
	private CourseQuesAnswerMapper courseQuesAnswerMapper;

	@Autowired
	private PatientCourseFilesAnswerMapper patientCourseFilesAnswerMapper;

	@Autowired
	private PrescriptionMapper prescriptionMapper;

	@Autowired
	private FollowServiceI followService;

	@Autowired
	private BarrierCourseQuesCfgMapper barrierCourseQuesCfgMapper;

	@Autowired
	private MemberCourseBarrierMapper memberCourseBarrierMapper;

	@Autowired
	private WechatMessageService wechatMessageService;

	@Override
	public CourseModel loadCourseById(Long sid) {
		return this.courseMapper.loadCourseById(sid);
	}

	@Override
	public Map<String,Object> detailCourse(String sid,String pid,String isPlan,String patientCourseId){
		Map<String,Object> map=new HashMap<String, Object>();
		CourseModel model=this.courseMapper.loadCourseById(Long.parseLong(sid));
		if(ValidateTool.checkIsNull(model)){
			if(!StringUtils.isBlank(patientCourseId)){
				model.setIsLearn(ConstantCourse.COURSE_CHECK_STATUS_1);
			}
			map.put("course", model);
		}else{
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_MESSAGE, "");
		}
		return map;
	}

	@Override
	public List<CourseModel> loadCourse(PageRequestModel pager, String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		//param
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param", param);
		return this.courseMapper.loadCourse(map);
	}

	@Override
	public PageResult<CourseModel> loadCourseBack(PageRequest pager, String param){
		PageHelper.startPage(pager.getPage(), pager.getRows());
		//param
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param", param);
		List<CourseModel> list = this.courseMapper.loadCourse(map);
		return new PageResult<>(list);
	}

	@Override
	public long countCourse(){
		return this.courseMapper.countCourse();
	}

	@Override
	public void addCourse(CourseModel courseModel){
		String courseId = DaoHelper.getSeq();
		courseModel.setSid(courseId);
		this.courseMapper.addCourse(courseModel);
		if (!StringUtils.isBlank(courseModel.getClassifyId())){
			//添加课程关联
			CourseClassifyModel model = new CourseClassifyModel();
			model.setClassifyId(courseModel.getClassifyId());
			model.setCourseId(courseId);
			this.courseMapper.addCourseClassify(model);
		}
	}

	@Override
	public void modifyCourse(CourseModel courseModel){
		this.courseMapper.modifyCourse(courseModel);
		CourseClassifyModel classifyModel = this.courseMapper.getCourseClassifyByCourseId(courseModel.getSid());
		if (null != classifyModel){
			if (StringUtils.isBlank(courseModel.getClassifyId())){  //如果字典分类id为空,就删除关联表中的信息
				this.courseMapper.delCourseClassifyByCourseId(courseModel.getSid());
			}else{
				//不为空修改
				CourseClassifyModel model = new CourseClassifyModel();
				model.setCourseId(courseModel.getSid());
				model.setClassifyId(courseModel.getClassifyId());
				this.courseMapper.updateCourseClassify(model);
			}

		}else{
			//新增
			if (!StringUtils.isBlank(courseModel.getClassifyId())){
				CourseClassifyModel model = new CourseClassifyModel();
				model.setCourseId(courseModel.getSid());
				model.setClassifyId(courseModel.getClassifyId());
				this.courseMapper.addCourseClassify(model);
			}
		}
	}

	@Override
	public void delCourse(Long  sid){
		this.courseMapper.delCourse(sid);
	}

	//加载课程
	@Override
	public List<CourseModel> loadCourse(PageRequestModel pager, String type, String pid) {
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		//PatientModel patient=this.patientMapper.loadPatientByPid(pid);

		GetMemberDTO getMemberDTO = new GetMemberDTO();
		getMemberDTO.setMemberId(pid);
		MemberPO member = memberMapper.getMember(getMemberDTO);

		if(!ValidateTool.checkIsNull(member)){
			throw new DiabetesMsException(ConstantErrorMessage.PATIENT_NOT_EXIST_ERROR_CODE, ConstantErrorMessage.PATIENT_NOT_EXIST_ERROR_MESSAGE, "");
		}
		param.put("showStatus", getShowStatus());
		//课程精选分类根据用户标签获取列表
		List<CourseModel> list=new ArrayList<CourseModel>();
		if(ConstantCourse.COURSE_TYPE_1.equals(type)){
			//list=this.loadCourseRecommend(pager,pid);
			List<ListCourseVO> courseVOS = loadRecommendCourseList(pid);
			for (ListCourseVO courseVO : courseVOS) {
				CourseModel courseModel = new CourseModel();
				BeanUtils.copyProperties(courseModel,courseVO);
				list.add(courseModel);
			}
		}else{
			PageHelper.startPage(pager.getPage(), pager.getRows());
			//根据课程分类获取列表
			param.put("courseType", type);
			//param.put("isPlan", "0");
			//param.put("type", 1);//选修课程
			list=this.courseMapper.loadCourseByPid(param);
		}
		return list;
	}

	//课程推荐
	private List<CourseModel> loadCourseRecommend(PageRequestModel pager,String pid) {
		List<CourseModel> list=loadCourseRecommend(pid);
		//如果推荐的内容全为空，则返回精选课程
		if(list.size()==0){
			PatientLabelModel patientLabel=null;//this.patientMapper.loadPatientLabel(pid);
			if(ValidateTool.checkIsNull(patientLabel)&&ValidateTool.checkIsNull(patientLabel.getLabel())){
				PageHelper.startPage(pager.getPage(), pager.getRows());
				Map<String,Object> param=new HashMap<String, Object>();
				param.put("pid", pid);
				param.put("apply_crowd", patientLabel.getLabel().split(","));
				list=this.courseMapper.loadCourse(param);
			}else{
				//用户无标签，则加载用户未选，其他用户已选或者已完成数量最多的十篇课程
				list=this.loadHotCourse(pid);
			}
		}
		return list;
	}

	//热门课程
	private List<CourseModel> loadHotCourse(String pid) {
		//课程按照已选或已学次数排序
		List<CourseModel> hotCourseList=this.courseMapper.listHotCourse();
		String[] coruseIds = new String[10];
		for(int i=0;i<10;i++){
			if(i<hotCourseList.size()){
				coruseIds[i]=hotCourseList.get(i).getSid();
			}
		}
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		param.put("coruseIds", coruseIds);
		List<CourseModel> list=this.courseMapper.loadCourseByCourseIds(param);
		List<CourseModel> list1=new ArrayList<CourseModel>();//过滤已学
		for(CourseModel course : list){
			if(course.getIsLearn()!=2){
				list1.add(course);
			}

		}
		return list1;
	}

	//根据规则获取推荐课程
	private List<CourseModel> loadCourseRecommend(String pid) {
		PatientLabelModel patientLabel=null;//this.patientMapper.loadPatientLabel(pid);
		List<CourseModel> list=new ArrayList<CourseModel>();
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		//20211119移除drools部分逻辑
/*		if(ValidateTool.checkIsNull(patientLabel)&&ValidateTool.checkIsNull(patientLabel.getLabel())){
			List<String> courses = DroolRuleTools.recommend(patientLabel.getLabel());
			if(ValidateTool.checkIsNull(courses)&&courses.size()>0){
				String[] coruseIds = new String[courses.size()];
				courses.toArray(coruseIds);
				param.put("coruseIds", coruseIds);
				list=this.courseMapper.loadCourseByCourseIds(param);
			}

		}*/
		if(list.size()<10){
			//标签课程数量不足，获取规则推荐的课程
			Map<String,Object> courseMap=new HashMap<String, Object>();
			//用一个map记录推荐课程id,避免重复
			for(CourseModel course : list){
				courseMap.put(course.getSid(), "1");
			}
			//从推荐表里获取数据
			PatientRecommendModel recommends=this.courseMapper.loadPatientRecommend(param);
			if(ValidateTool.checkIsNull(recommends)&&ValidateTool.checkIsNull(recommends.getRecommends())){
				String[] courseIds=recommends.getRecommends().split(",");
				param.put("coruseIds", courseIds);
				List<CourseModel> list1=this.courseMapper.loadCourseByCourseIds(param);
				if(ValidateTool.checkIsNull(list1)&&list1.size()>0){
					for(CourseModel course : list1){
						if(!ValidateTool.checkIsNull(courseMap.get(course.getSid()))){
							list.add(course);
						}
					}
				}
			}
		}
		//只取十条
		if(list.size()>10){
			list=list.subList(0, 10);
		}
		//课程排序，已选在后
		Collections.sort(list, (arg0, arg1) -> arg0.getIsLearn().compareTo(arg1.getIsLearn()));
		return list;

	}

	//patientType 0礼来患者 or 1非礼来患者
	private String getShowStatus() {
		return "1";

	}

	@Override
	public void addMemberCourse(String sid, String pid,String origin,String isPlan,Long planId) {
		//判断课程是否存在
		CourseModel model=this.courseMapper.loadCourseById(Long.parseLong(sid));
		if(!ValidateTool.checkIsNull(model)){
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_MESSAGE, "");
		}

		if("0".equals(isPlan)) {
			//判断患者是否已添加该课程
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("pid", pid);
			param.put("courseId", sid);
			param.put("isPlan", isPlan);
			synchronized (this) {
				//lock.lock();
				PatientCourseModel patientCourseModel=this.patientCourseMapper.getPatientCourseById(param);
				if(!ValidateTool.checkIsNull(patientCourseModel)){
					//添加课程
					String patientCourseId = this.addCourse(pid, sid, origin, isPlan, planId);
					//自动添加第一章为待学习
					addFirstChapterToLearn(sid,pid,patientCourseId);
					//模板消息提醒
					JSONObject dataJson = new JSONObject();
					dataJson.put("datetime" ,DateHelper.getTime());
					dataJson.put("courseName" ,model.getName());
					AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
					addWechatMessageDTO.setMemberId(pid);
					addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_ADD_COURSE_SUCCESS);
					addWechatMessageDTO.setDataJson(dataJson.toJSONString());
					addWechatMessageDTO.setUserType(WechatMessageConstant.USER_TYPE_PATIENT);
					addWechatMessageDTO.setForeignId(sid);
					this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
				}else{
					//lock.unlock();
					throw new DiabetesMsException(ConstantErrorMessage.COURSE_ADD_REPET_ERROR_CODE, ConstantErrorMessage.COURSE_ADD_REPET_ERROR_MESSAGE, "");
				}
			}
		}else {
			String patientCourseId = this.addCourse(pid, sid, origin, isPlan, planId);
			addFirstChapterToLearn(sid,pid,patientCourseId);

		}
	}

	public String addCourse(String pid,String courseId,String origin,String isPlan,Long planId){
		if(origin == null || (!origin.equals("0") &&  !origin.equals("1") ) ){
			origin ="0";
		}
		String patientCourseId = DaoHelper.getSeq();
		if("1".equals(isPlan)) {//学习计划的可以插入多条
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("sid", patientCourseId);
			param.put("pid", pid);
			param.put("courseId", courseId);
			param.put("origin", origin);
			param.put("isPlan", "1");
			param.put("planId", planId);
			this.patientCourseMapper.addPatientCourse(param);
		}else {
			//收藏的文章只能插入一条
			Map<String,Object> queryParam = new HashMap<>();
			queryParam.put("pid", pid);
			queryParam.put("courseId", courseId);
			queryParam.put("isPlan", "0");
			List<CourseModel> memberCourseList = patientCourseMapper.loadMemberCourse(queryParam);
			if(memberCourseList != null && memberCourseList.size() > 0) {

			}else {
				Map<String,Object> param=new HashMap<String, Object>();
				param.put("sid", patientCourseId);
				param.put("pid", pid);
				param.put("courseId", courseId);
				param.put("origin", origin);
				param.put("isPlan", "0");
				param.put("planId", planId);
				this.patientCourseMapper.addPatientCourse(param);
			}
		}
		return patientCourseId;
	}

	private void addFirstChapterToLearn(String courseId,String pid,String patientCourseId){
		//添加第一章为待学习
		Map<String, Object> param = new HashMap<>();
		param.put("courseId",courseId);
		List<CourseChapterModel> chapterList = courseChapterMapper.loadCourseChapter(param);
		if (chapterList != null && chapterList.size()>0){
			String chapterId = chapterList.get(0).getSid();
			PatientChapterModel patiChapterModel=new PatientChapterModel(pid, courseId, chapterId, ConstantCourse.CHAPTER_STATUS_TYPE_3, "0");
			patiChapterModel.setPatientCourseId(patientCourseId);
			Map<String,Object> chapterParam=new HashMap<String, Object>();
			chapterParam.put("pid", pid);
			chapterParam.put("chapterId", chapterId);
			chapterParam.put("courseId", courseId);
			chapterParam.put("patientCourseId", patientCourseId);
			PatientChapterModel pcModel=this.patientCourseMapper.getPatiChapter(param);
			if (pcModel == null){
				patiChapterModel.setSid(DaoHelper.getSeq());
				this.patientCourseMapper.addPatientChapter(patiChapterModel);
			}
		}
	}

	@Override
	public Map<String, Object> loadMemberCourse(String pid,String allFlag,String isPlan) {
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		param.put("status", ConstantCourse.COURSE_STATUS_1);
//		PatientModel patient=this.patientMapper.loadPatientByPid(pid);

		GetMemberDTO getMemberDTO = new GetMemberDTO();
		getMemberDTO.setMemberId(pid);
		MemberPO member = memberMapper.getMember(getMemberDTO);

		if(!ValidateTool.checkIsNull(member)){
			throw new DiabetesMsException(ConstantErrorMessage.PATIENT_NOT_EXIST_ERROR_CODE, ConstantErrorMessage.PATIENT_NOT_EXIST_ERROR_MESSAGE, "");
		}
		param.put("showStatus", getShowStatus());
		//获取未完成必修课程
		List<CourseModel> requiredCourse=new ArrayList<>();//this.patientCourseMapper.loadRequiredCourse(param);
		if(ValidateTool.checkIsNull(allFlag)&&"1".equals(allFlag)){
			param.put("isPlan", isPlan);
			//获取未完成选修课程
			List<CourseModel> optionalCourse=this.patientCourseMapper.loadOptionalCourse(param);
			requiredCourse.addAll(optionalCourse);
		}
		for(CourseModel model : requiredCourse){
			//获取课程已完成的章节数
			Map<String,Object> param1=new HashMap<String, Object>();
			param1.put("patientCourseId", model.getPatientCourseId());
			param1.put("pid", pid);
			param1.put("courseId", model.getSid());
			param1.put("status", ConstantCourse.CHAPTER_STATUS_TYPE_2);
			Long finishNum=this.patientCourseMapper.countCourseChapterNum(param1);
			model.setFinishChapter(Integer.parseInt(finishNum.toString()));
			model.setIsLearn(ConstantCourse.COURSE_CHECK_STATUS_1);
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("courseNum", requiredCourse.size());
		map.put("courseList", requiredCourse);
		return map;
	}

	@Override
	public Map<String, Object> loadCourseType(String pid,String flag) {
		Map<String,Object> map=new HashMap<String, Object>();
		//PatientModel patient=this.patientMapper.loadPatientByPid(pid);
		int tag = 0;
		if(!"2".equals(flag)){
			//查询患者标签是否有匹配的课程，匹配则展示课程精选
			//PatientModel patient=this.patientMapper.loadPatientByPid(pid);
			/*
			PatientLabelModel patientLabel=this.patientMapper.loadPatientLabel(pid);
			if(ValidateTool.checkIsNull(patientLabel)&&ValidateTool.checkIsNull(patientLabel.getLabel())){
				if(checkLabel(patientLabel.getLabel())){
					tag = 1;
				}
			}
			*/
			tag = 1;
		}
		List<Map<String,Object>> courseTypeList=new ArrayList<Map<String,Object>>();
		courseTypeList.addAll(ConstantCourse.courseTypeList);
		if (tag == 1) {
			courseTypeList=new ArrayList<Map<String,Object>>();
			courseTypeList.addAll(ConstantCourse.courseTypeListAll);

		}
		map.put("type",courseTypeList );
		return map;
	}

	//判断用户标签是否有对应的课程
	private boolean checkLabel(String label) {
		String[] labels=label.split(",");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("apply_crowd", labels);
		List<CourseModel> courseList=this.courseMapper.loadCourse(param);
		if(courseList.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> loadCourseByType(PageRequestModel pager, String type, String pid) {
		//获取所有课程
		List<CourseModel> courseModelList = this.loadCourse(pager,type,pid);
		PageResult<CourseModel> pageResult = new PageResult<CourseModel>(courseModelList);
		PageData<CourseModel> pageData = new PageData<CourseModel>(courseModelList,(int)pageResult.getTotalRows(),pageResult.getPageNum());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pager", pageData);
		return map;
	}

	@Override
	public Map<String, Object> loadMemberCourseRemind(String pid) {
		CourseRemindModel courseRemind=this.patientCourseMapper.getCourseRemindByPid(pid);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("courseRemind", courseRemind);
		return map;
	}

	@Override
	public void setMemberCourseRemind(CourseRemindModel courseRemindModel) {
		//启用提醒必须传入提醒时间
		if(ConstantCourse.COURSE_REMIND_TYPE_1.equals(courseRemindModel.getIsOpen())){
			ValidateTool.checkIsNull(courseRemindModel.getRemindTime());
		}
		if(ValidateTool.checkIsNull(courseRemindModel.getSid())){
			this.patientCourseMapper.updateCourseRemind(courseRemindModel);
		}else{
			CourseRemindModel courseRemind=this.patientCourseMapper.getCourseRemindByPid(courseRemindModel.getPid());
			if(ValidateTool.checkIsNull(courseRemind)){
				courseRemindModel.setSid(courseRemind.getSid());
				this.patientCourseMapper.updateCourseRemind(courseRemindModel);
			}else{
				courseRemindModel.setSid(DaoHelper.getSeq());
				this.patientCourseMapper.addCourseRemind(courseRemindModel);
			}

		}

	}

	@Override
	@Transactional
	public Map<String, Object> courseChapter(String pid, String sid,String patientCourseId) {

		checkFirstLockedChapterStatus(pid,sid,patientCourseId);
		//获取课程章节
//		List<CourseChapterModel> list=this.courseChapterMapper.listMemberCourseChapter(pid,sid);
		List<CourseChapterModel> list=this.courseChapterMapper.listMemberCourseChapterByPatientCourseId(sid,patientCourseId);
		/*
		//清空患者连续未学习次数
		CourseRemindModel courseRemind=this.patientCourseMapper.getCourseRemindByPid(pid);
		if(ValidateTool.checkIsNull(courseRemind)){
			courseRemind.setRemindNum("0");
			courseRemind.setStudyStatus("0");
			courseRemind.setLastStudyDate(DateHelper.getTime());
			this.patientCourseMapper.updateCourseRemind(courseRemind);
		}else{
			courseRemind=new CourseRemindModel();
			courseRemind.setSid(DaoHelper.getSeq());
			courseRemind.setPid(pid);
			courseRemind.setIsOpen("0");
			courseRemind.setRemindNum("0");
			courseRemind.setStudyStatus("0");
			courseRemind.setLastStudyDate(DateHelper.getTime());
			this.patientCourseMapper.addCourseRemind(courseRemind);
		}
		*/
		for(int i=0;i<list.size();i++){
			CourseChapterModel model=list.get(i);
			String status=model.getStatus();//学习状态

			if(ConstantCourse.CHAPTER_STATUS_TYPE_2.equals(status)){//学习完成
				model.setStatus(ConstantCourse.CHAPTER_STATUS_3);
				model.setChapterMemo("已学过");
			}else if(ConstantCourse.CHAPTER_STATUS_TYPE_1.equals(status)){//学习中
				model.setStatus(ConstantCourse.CHAPTER_STATUS_2);
			}else if(ConstantCourse.CHAPTER_STATUS_TYPE_3.equals(status)){
				model.setStatus(ConstantCourse.CHAPTER_STATUS_1);//未学习
			}else if(StringUtils.isBlank(status)){
				model.setStatus(ConstantCourse.CHAPTER_STATUS_4);//待解锁
			}
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	//患教改版之前版本添加课程不会自动添加解锁第一章节
	private void checkFirstLockedChapterStatus(String pid, String sid,String patientCourseId){
		synchronized (this) {
			boolean flag = false;
			String lockedChapterId = null;
			List<CourseChapterModel> beforeList = new ArrayList<>();//第一个待解锁章节前  学习中或者待学习
			List<CourseChapterModel> list = this.courseChapterMapper.listMemberCourseChapterByPatientCourseId(sid, patientCourseId);
			for (CourseChapterModel courseChapterModel : list) {
				String status = courseChapterModel.getStatus();
				if (StringUtils.isBlank(status)){
					lockedChapterId = courseChapterModel.getSid();//第一个待解锁章节
					break;
				}else if (status.equals(ConstantCourse.CHAPTER_STATUS_TYPE_1) || status.equals(ConstantCourse.CHAPTER_STATUS_TYPE_3)){
					beforeList.add(courseChapterModel);
				}
			}
			if (beforeList.size() == 0 && !StringUtils.isBlank(lockedChapterId)){
				PatientChapterModel patiChapterModel=new PatientChapterModel(pid, sid, lockedChapterId, ConstantCourse.CHAPTER_STATUS_TYPE_3, "0");
				patiChapterModel.setSid(DaoHelper.getSeq());
				patiChapterModel.setPatientCourseId(patientCourseId);
				Map<String, Object> map = new HashMap<>();
				map.put("pid",pid);
				map.put("chapterId",lockedChapterId);
				map.put("patientCourseId",patientCourseId);
				PatientChapterModel patiChapter = patientCourseMapper.getPatiChapter(map);
				//已存在就不添加
				if (null == patiChapter){
					this.patientCourseMapper.addPatientChapter(patiChapterModel);
				}
			}
		}
	}

	@Override
	public Map<String, Object> detailCourseChapter(String pid, String chapterId,String courseId,String patientCourseId) {
		//查询用户是否已添加该课程学习记录，没有则添加
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		param.put("chapterId", chapterId);
		param.put("courseId", courseId);
		param.put("patientCourseId", patientCourseId);
		synchronized (this) {
			PatientCourseModel patientCourseModel = this.patientCourseMapper.getPatientCourseById(param);
			if(!ValidateTool.checkIsNull(patientCourseModel)){
				this.addCourse(pid, courseId,"0","0",null);
			}
		}
		//获取课程详情
		CourseModel course=this.loadCourseById(Long.parseLong(courseId));
		if(!ValidateTool.checkIsNull(course)){
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_MESSAGE, "");
		}

		//查询用户是否已添加该章节学习记录，没有则添加
		PatientChapterModel patiChapterModel=this.patientCourseMapper.getPatiChapter(param);
		if(!ValidateTool.checkIsNull(patiChapterModel)){
			patiChapterModel=new PatientChapterModel(pid, courseId, chapterId, ConstantCourse.CHAPTER_STATUS_TYPE_1, "0");
			patiChapterModel.setSid(DaoHelper.getSeq());
			patiChapterModel.setPatientCourseId(patientCourseId);
			this.patientCourseMapper.addPatientChapter(patiChapterModel);
		}else if (patiChapterModel.getStatus().equals(ConstantCourse.CHAPTER_STATUS_TYPE_3)){
			patiChapterModel.setStatus(ConstantCourse.CHAPTER_STATUS_TYPE_1);
			this.patientCourseMapper.updatePatientChapter(patiChapterModel);
		}
		//获取章节详情
		CourseChapterModel courseChapterModel=this.courseChapterMapper.loadCourseChapterById(Long.parseLong(chapterId));
		if(!ValidateTool.checkIsNull(courseChapterModel)){
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_CHAPTER_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_CHAPTER_NOT_EXITS_ERROR_MESSAGE, "");
		}

		List<KnowledgeModel> knowledgeList=this.knowledgeMapper.loadKnowledgeByChapterId(chapterId);
		//组装知识点的内容
		for(KnowledgeModel knowledge : knowledgeList){
			String id=knowledge.getId();
			//查询知识点的内容
			List<KnowledgeContentModel> cotentList=this.knowledgeContentMapper.loadKnowledgeContentByKnowledgeId(id);
			for(KnowledgeContentModel contentModel : cotentList){
				if(contentModel.getType()==6){
					//视频格式需要转换格式
					String content=contentModel.getContent();
					if(ValidateTool.checkIsNull(content)){
						contentModel.setVideo(JSONObject.parseObject(content, Map.class));
					}
				}
			}
			knowledge.setContent(ReflectTool.getListFromBean(cotentList,new Object[]{"type","content","video"}));
			//查询知识点的问题列表
			List<CfgQuesModel> quesList=this.cfgQuesMapper.loadQuesByKnowledgeId(id);
			//随机选出一道作为题目
			if(quesList.size()>0){
				Random rand = new Random();
				CfgQuesModel ques=quesList.get(rand.nextInt(quesList.size()));
				//获取选项
				List<CfgQuesItemsModel> itemList=this.cfgQuesItemsMapper.loadCfgQuesItems(Long.parseLong(ques.getQid()));
				ques.setItems(ReflectTool.getListFromBean(itemList,new Object[]{"con","value"}));
				knowledge.setQues(ReflectTool.getMapFromBean(ques, new Object[]{"answer","title","type","items","qid"}) );
			}
		}
		//判断章节是否课程的最后一个章节
		String lastChapter="0";
		String courseAnswer="0";
		List<CfgQuesModel> quesList=new ArrayList<CfgQuesModel>();
		List<CfgQuesModel> returnQuesList=new ArrayList<CfgQuesModel>();
		if(course.getChapter()==courseChapterModel.getSort()){
			lastChapter="1";
			//判断此前是否已经回答过问题
			CourseQuesAnswerModel answerParam=new CourseQuesAnswerModel();
			answerParam.setForeignId(courseId);
			answerParam.setPid(pid);
			answerParam.setType(1);//关联课程的问题
			CourseQuesAnswerModel answer=this.courseQuesAnswerMapper.getCourseQuesAnswer(answerParam);
			if(!ValidateTool.checkIsNull(answer)){
				//未回答过问题
				courseAnswer="1";
				//加载课程习题
				quesList=this.cfgQuesMapper.loadQuesByCourseId(courseId);
				for(CfgQuesModel ques : quesList){
					PatientCourseFilesAnswerModel model=new PatientCourseFilesAnswerModel();
					model.setPid(pid);
					model.setQid(ques.getQid().toString());
					if(!ValidateTool.checkIsNull(patientCourseFilesAnswerMapper.getPatientCourseFilesAnswer(model))){
						List<CfgQuesItemsModel> itemList=this.cfgQuesItemsMapper.loadCfgQuesItems(Long.parseLong(ques.getQid()));
						ques.setItems(ReflectTool.getListFromBean(itemList,new Object[]{"con","value"}));
						returnQuesList.add(ques);
					}
				}
			}
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("knowledgeList", knowledgeList);
		map.put("img", courseChapterModel.getImg());
		map.put("lastChapter", lastChapter);
		map.put("courseAnswer", courseAnswer);
		map.put("courseQuesList", ReflectTool.getListFromBean(returnQuesList, new Object[]{"answer","title","type","items","qid"}));
		map.put("status", patiChapterModel.getStatus());
		List<Map<String,Object>> recommendList=new ArrayList<Map<String,Object>>();
		if("1".equals(lastChapter)){
			recommendList=this.getRecommend(pid,courseId);
		}
		map.put("recommend", recommendList);
		return map;
	}



	@Override
	public List<Map<String, Object>> getRecommend(String pid, String courseId) {
		List<CourseModel> courseList=loadCourseRecommend(pid);//个性化课程
		List<CourseModel> list=new ArrayList<CourseModel>();
		if(ValidateTool.checkIsNull(courseId)){
			for(CourseModel course : courseList){
				if(!course.getSid().equals(courseId)&&ValidateTool.checkIsNull(course.getIsLearn())&&course.getIsLearn()==0){
					list.add(course);
				}
			}
		}else{
			for(CourseModel course : courseList){
				if(ValidateTool.checkIsNull(course.getIsLearn())&&course.getIsLearn()==0){
					list.add(course);
				}
			}
		}

		if(list.size()>3){
			list = list.subList(0, new Random().nextInt(3)+1);
		}
		return ReflectTool.getListFromBean(list, new Object[]{"sid","chapter","chapterMemo","name","thumbnail","isLearn"});
	}

	@Override
	public Map<String, Object> submitCourseChapter(String pid,String patientCourseId,String chapterId, String score,String answerJson,
												   String courseId, String startDt, String endDt,String assess,String courseAnswerJson) {
		List<Object> list;//
		List<Object> courseAnswerlist=new ArrayList<Object>();
		try {
			list = JSON.parseArray(answerJson);
			if(ValidateTool.checkIsNull(courseAnswerJson)){
				courseAnswerlist = JSON.parseArray(courseAnswerJson);
			}
		} catch (Exception e) {
			throw new DiabetesMsException(ConstantErrorMessage.FORMAT_TRANSFORMATION_ERROR_CODE, ConstantErrorMessage.FORMAT_TRANSFORMATION_ERROR_MESSAGE, "");
		}
		for (Object obj : list) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			Long qid=Long.parseLong(map.get("qid").toString());
			//获取问题的详情
			CfgQuesModel cfg=this.cfgQuesMapper.loadCfgQuesById(qid);
			if(ValidateTool.checkIsNull(cfg)){
				map.put("title", cfg.getTitle());
				map.put("correctAnswer", cfg.getAnswer());
			}else{
				LOGGER.error("找不到问题，qid="+qid+",pid="+pid+",章节id="+chapterId);
			}
		}
		//获取课程信息
		CourseModel course=this.courseMapper.loadCourseById(Long.parseLong(courseId));
		if(!ValidateTool.checkIsNull(course)){
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_NOT_EXITS_ERROR_MESSAGE, "");
		}

		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", pid);
		param.put("chapterId", chapterId);
		param.put("courseId", courseId);
		param.put("patientCourseId", patientCourseId);
		PatientChapterModel patiChapterModel=this.patientCourseMapper.getPatiChapter(param);
		if(!ValidateTool.checkIsNull(patiChapterModel)){
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_CHAPTER_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_CHAPTER_NOT_EXITS_ERROR_MESSAGE, "");
		}
		//修改用户章节学习进度
		PatientChapterModel patientChapterModel=new PatientChapterModel();
		patientChapterModel.setSid(patiChapterModel.getSid());
//		patientChapterModel.setPid(pid);
//		patientChapterModel.setChapterId(chapterId);
//		patientChapterModel.setPatientCourseId(patientCourseId);
		if(Double.parseDouble(score)>60d){//大于60分章节才算已完成
			patientChapterModel.setStatus(ConstantCourse.CHAPTER_STATUS_TYPE_2);
			nextChapterLearnStatus(patiChapterModel.getSid(),courseId,pid,patientCourseId,chapterId);
		}else{
			if(patiChapterModel.getStatus().equals(ConstantCourse.CHAPTER_STATUS_TYPE_2)){
				//之前已完成的话，就不改成未完成
				patientChapterModel.setStatus(ConstantCourse.CHAPTER_STATUS_TYPE_2);
			}else{
				patientChapterModel.setStatus(ConstantCourse.CHAPTER_STATUS_TYPE_1);
			}
		}
		patientChapterModel.setScore(score);
		patientChapterModel.setAnswerJson(JSON.toJSONString(list));
		this.patientCourseMapper.updatePatientChapter(patientChapterModel);
		//添加用户章节下知识点回答记录
		PatientChapterAnswerModel patientChapterAnswerModel=new PatientChapterAnswerModel();
		patientChapterAnswerModel.setSid(DaoHelper.getSeq());
		patientChapterAnswerModel.setPid(pid);
		patientChapterAnswerModel.setChapterId(chapterId);
		patientChapterAnswerModel.setCourseId(courseId);
		patientChapterAnswerModel.setStartDt(startDt);
		patientChapterAnswerModel.setEndDt(endDt);
		patientChapterAnswerModel.setAnswerJson(JSON.toJSONString(list));
		patientChapterAnswerModel.setScore(score);
		patientChapterAnswerModel.setPatientCourseId(patientCourseId);
		this.patientCourseMapper.addPatientChapterAnswer(patientChapterAnswerModel);

		//如果所有章节都学习完毕，修改课程状态为已完成
		List<CourseChapterModel> courseChapterList=this.courseChapterMapper.listMemberCourseChapter(pid,courseId,patientCourseId);
		boolean isFinish=true;
		if(courseChapterList == null || courseChapterList.size() == 0) {
			isFinish = false;
		}else {
			for(int i=0;i<courseChapterList.size();i++){
				CourseChapterModel model=courseChapterList.get(i);
				String status=model.getStatus();//学习状态
				if(status==null||!ConstantCourse.CHAPTER_STATUS_TYPE_2.equals(status)){//还未开始学习或在学习中
					isFinish=false;
				}
			}
		}

		//所有章节都已完成，修改课程的学习状态
		if(isFinish){
			PatientCourseModel patientCourseModel=new PatientCourseModel();
			patientCourseModel.setSid(patientCourseId);
			patientCourseModel.setPid(pid);
			patientCourseModel.setCourseId(courseId);
			patientCourseModel.setStatus(ConstantCourse.COURSE_STATUS_2);
			this.patientCourseMapper.updatePatientCourse(patientCourseModel);
			this.addMemberCourseBarrHandler(pid,courseId);
		}
		//添加课程习题记录
		if(courseAnswerlist.size()>0){
			CourseQuesAnswerModel answer=new CourseQuesAnswerModel();
			answer.setSid(DaoHelper.getSeq());
			for (Object obj : courseAnswerlist) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) obj;
				Long qid=Long.parseLong(map.get("qid").toString());
				//获取问题的详情
				CfgQuesModel cfg=this.cfgQuesMapper.loadCfgQuesById(qid);
				if(ValidateTool.checkIsNull(cfg)){
					map.put("title", cfg.getTitle());
					if(ValidateTool.checkIsNull(cfg.getFilesCode())&&cfg.getFilesCode()==1){
						//档案问题需要存储到档案表
						PatientCourseFilesAnswerModel model=new PatientCourseFilesAnswerModel();
						model.setSid(DaoHelper.getSeq());
						model.setPid(pid);
						model.setQid(qid.toString());
						model.setTitle(cfg.getTitle());
						model.setValue(map.get("answer").toString());
						this.patientCourseFilesAnswerMapper.addPatientCourseFilesAnswer(model);
					}
				}
			}
			answer.setAnswer(JSON.toJSONString(courseAnswerlist));
			answer.setAssess(assess);
			answer.setForeignId(courseId);
			answer.setPid(pid);
			answer.setType(1);
			this.courseQuesAnswerMapper.addCourseQuesAnswer(answer);
		}

//		//通知丁香园
//		if(Constant.RELEASE){
//			String courserStatus=isFinish?"1":"0";
//			this.dxyOutside.resintegralStatistics(pid, courseId, chapterId, course.getCourseType().toString(), score, startDt, endDt, courserStatus);
//		}
		return null;
	}


	//关卡课程表
	private void addMemberCourseBarrHandler(String memberId,String courseId){
		BarrierCourseQuesCfgPO querryCfgPO = new BarrierCourseQuesCfgPO();
		querryCfgPO.setCourseId(courseId);
		querryCfgPO.setIsValid(1);
		//课程存在闯关题目
		List<BarrierCourseQuesCfgPO> barrierCourseQuesList = barrierCourseQuesCfgMapper.queryAll(querryCfgPO);
		if (barrierCourseQuesList.size()>0){
			MemberCourseBarrierPO memberCourseBarrierPO = new MemberCourseBarrierPO();
			memberCourseBarrierPO.setCourseId(courseId);
			memberCourseBarrierPO.setMemberId(memberId);
			memberCourseBarrierPO.setIsValid(1);
			//未添加过
			List<MemberCourseBarrierPO> memberCourseBarrierList = memberCourseBarrierMapper.queryAll(memberCourseBarrierPO);
			if (memberCourseBarrierList.size() == 0){
				memberCourseBarrierPO.setSid(DaoHelper.getSeq());
				memberCourseBarrierMapper.insert(memberCourseBarrierPO);
			}
		}
	}

	//解锁下一章节
	private void nextChapterLearnStatus(String sid,String courseId,String pid,String patientCourseId,String finishChapterId){
		Map<String, Object> param = new HashMap<>();
		param.put("courseId",courseId);
		List<CourseChapterModel> chapterList = courseChapterMapper.loadCourseChapter(param);//课程章节list
		String chapterId = null;
		if (chapterList != null && chapterList.size()>0){
			for (int i=0;i<chapterList.size();i++){
				if (chapterList.get(i).getSid().equals(finishChapterId) && i != chapterList.size()-1){
					chapterId = chapterList.get(i+1).getSid();
					Map<String, Object> map = new HashMap<>();
					map.put("pid",pid);
					map.put("chapterId",chapterId);
					map.put("patientCourseId",patientCourseId);
					PatientChapterModel patiChapter = patientCourseMapper.getPatiChapter(map);
					if (null != patiChapter && patiChapter.getStatus().equals(ConstantCourse.CHAPTER_STATUS_TYPE_2)){
						finishChapterId = chapterId;
						continue;
					}
				}
			}
			//添加下一章节为待学习
			if (!StringUtils.isBlank(chapterId)){
				PatientChapterModel patiChapterModel=new PatientChapterModel(pid, courseId, chapterId, ConstantCourse.CHAPTER_STATUS_TYPE_3, "0");
				patiChapterModel.setSid(DaoHelper.getSeq());
				patiChapterModel.setPatientCourseId(patientCourseId);

				Map<String, Object> map = new HashMap<>();
				map.put("pid",pid);
				map.put("chapterId",chapterId);
				map.put("patientCourseId",patientCourseId);
				PatientChapterModel patiChapter = patientCourseMapper.getPatiChapter(map);
				//已存在就不添加
				if (null == patiChapter){
					this.patientCourseMapper.addPatientChapter(patiChapterModel);
				}
			}
		}
	}

	@Override
	public Map<String, Object> loadMemberFinishCourseByType(String pid,String isPlan,PageRequestModel pager) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("pid", pid);
		param.put("isPlan", isPlan);
		param.put("status", ConstantCourse.COURSE_STATUS_2);
		PageHelper.startPage(pager.getPage(), pager.getRows());
		List<CourseModel> courseList=this.patientCourseMapper.loadMemberCourse(param);
		//获取课程下的章节
		for(CourseModel courseModel : courseList){
			if(ValidateTool.checkIsNull(courseModel.getSid())){
				String courseId=courseModel.getSid();
				List<CourseChapterModel> chpterList=this.courseChapterMapper.listMemberCourseChapter(pid,courseId,courseModel.getPatientCourseId());
				courseModel.setChapterList(ReflectTool.getListFromBean(chpterList,new Object[]{"chapterName","patientCourseId"}));
			}
		}
		List<Map<String, Object>> returnList=ReflectTool.getListFromBean( courseList,new Object[]{"chapterList","sid","name","thumbnail","patientCourseId"});
		PageResult<CourseModel> pageResult = new PageResult<CourseModel>(courseList);
		PageData<Map<String,Object>> pageData = new PageData<Map<String,Object>>(returnList,(int)pageResult.getTotalRows(),pageResult.getPageNum());
		Map<String,Object> result=new HashMap<String, Object>();
		result.put("pager",pageData);
		return result;
	}

	@Override
	public Map<String, Object> detailKnowledge(String pid, String knowledgeId) {
		KnowledgeModel knowledgeModel=this.knowledgeMapper.loadKnowledgeById(knowledgeId);
		if(ValidateTool.checkIsNull(knowledgeModel)){
			List<KnowledgeContentModel> cotentList=this.knowledgeContentMapper.loadKnowledgeContentByKnowledgeId(knowledgeId);
			knowledgeModel.setContent(ReflectTool.getListFromBean(cotentList,new Object[]{"type","content"}));
		}else{
			throw new DiabetesMsException(ConstantErrorMessage.COURSE_KNOWLEDGE_NOT_EXITS_ERROR_CODE, ConstantErrorMessage.COURSE_KNOWLEDGE_NOT_EXITS_ERROR_MESSAGE, "");
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("knowledge", knowledgeModel);
		return map;
	}

	@Override
	public List<CourseModel> loadCourseByClassifyCode(String classifyCode) {
		List<CourseModel> models = courseMapper.loadCourseByClassifyCode(classifyCode);
		return models;
	}

	@Override
	public Map<String, Object> createStudyPlan(String archives, String memberId, String drugList ,Integer eohType) {
		//课程类型处理
		Integer type = eohType == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP ? 2:1;
		String courseClassify = "";
		if(type == 1){
			courseClassify = "course_classify";
		}else{
			courseClassify = "course_classify_hyp";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type" , type);
		map.put("courseClassify" , courseClassify);
		List<CourseModel> courseList =  this.courseMapper.loadCourse(map);

		Map<String, Object> rsMap = new HashMap<>();
		//rsMap.put("courseList", courseList);
		rsMap.put("knowledges", convertCourse(courseList ,memberId));

		//List<DictModel> courseClassifys = dictMapper.listDictByPcode("course_classify");

		List<String> knowledgeTags = new ArrayList<String>();
		for (Iterator<CourseModel> iterator = courseList.iterator(); iterator.hasNext();) {
			CourseModel courseModel = (CourseModel) iterator.next();
			if(!StringUtils.isBlank(courseModel.getClassifyName())) {
				if(!knowledgeTags.contains(courseModel.getClassifyName())) {
					knowledgeTags.add(courseModel.getClassifyName());
				}
			}
		}
//		if(courseClassifys != null) {
//			for (Iterator iterator = courseClassifys.iterator(); iterator.hasNext();) {
//				DictModel dictModel = (DictModel) iterator.next();
//				knowledgeTags.add(dictModel.getName());
//			}
//		}
		rsMap.put("knowledgeTag", knowledgeTags);
		return rsMap;
	}

	private List<KnowledgePlanModel> convertCourse(List<CourseModel> courseList , String memberId){

		List<KnowledgePlanModel> resList =new ArrayList<KnowledgePlanModel>();
		int total = courseList.size();
		for (int i = 0; i <total ; i++) {
			CourseModel course = courseList.get(i);
			KnowledgePlanModel np = new KnowledgePlanModel();
			np.setId(course.getSid());
			np.setKnowledge(course.getClassifyName());
			np.setTitle(course.getName());
			np.setDay(getDay(total, i) + "");
			np.setTags(course.getApplyCrowd());
			np.setKnowledgeCode(course.getClassifyId());
			courseStatusHandler(np ,memberId);
			resList.add(np);
		}
		return resList;
	}

	/**
	 * 课程状态处理
	 * @param courseModel
	 * @param knowledgePlanModel
	 */
	private void courseStatusHandler(KnowledgePlanModel knowledgePlanModel ,String memberId){
		/**
		 * 学习状态 1 已学习  0 未学习
		 */
		Integer learnStatus = 0;
		/**
		 * 关注状态 1 已关注 0 未关注
		 */
		Integer followStatus = 0;
		/**
		 * 下发状态 1 已下发 0 未下发
		 */
		Integer hairDownStatus = 0;
		List<CourseModel> list = listMemberCourseById(memberId ,knowledgePlanModel.getId());
		if(list != null && !list.isEmpty()){
			for(CourseModel courseModel : list){
				//status = 2 为已学习
				if("2".equals(courseModel.getStatus())){
					learnStatus = 1;
				}
				//当课程是学习计划的内容时，认为已下发
				if(1 == courseModel.getIsPlan()){
					hairDownStatus = 1;
				}
				//当课程存在非学习计划的内容时，认为已关注
				if(0 == courseModel.getIsPlan()){
					followStatus = 1;
				}
			}
		}
		knowledgePlanModel.setFollowStatus(followStatus);
		knowledgePlanModel.setLearnStatus(learnStatus);
		knowledgePlanModel.setHairDownStatus(hairDownStatus);
	}

	/**
	 * 课程状态 1 已 0 未
	 */
	private final static int COURSE_STATUS_YES = 1;
	private final static int COURSE_STATUS_NO = 0;

	private static int getDay(int total,int index){
		int num = 12;
		if(total > num){
			Float day = (index-1) * ( 84.0f/total) +1;
			return day.intValue();
		} else {
			//index第几周+1天
			return (index -1)*7 + 1;
		}
	}

	/**
	 * 学习计划中的课程统计 1 未发送的 2 未学习的
	 */
	private final static int COURSE_COUNT_TYPE_UN_SEND = 1;
	private final static int COURSE_COUNT_TYPE_UN_STUDY = 2;

	@Override
	public Map<String, Object> loadPrescriptionPlans(String pid) {
		Map<String, Object> param = new HashMap<>();
		param.put("unCompleteCnt", 0);
		param.put("memberId", pid);

		List<Map<String, Object>> list = patientCourseMapper.loadPrescriptionPlansForMember(param);
		String prescriptionId;
		String followId;
		String knowledgeType;
		for(Map<String, Object> map : list){
			knowledgeType = map.get("knowledgeType").toString();
			prescriptionId = null;
			followId = null;
			if(PrescriptionConstant.KNOWLEDGE_TYPE_PRE == Integer.parseInt(knowledgeType)){
				prescriptionId = map.get("prescriptionId").toString();
			}else{
				followId = map.get("prescriptionId").toString();
			}
			long courseCnt = this.patientCourseMapper.countCourseInPlan(prescriptionId ,followId ,COURSE_COUNT_TYPE_UN_STUDY);
			long unSendCourse = this.patientCourseMapper.countCourseInPlan(prescriptionId ,followId ,COURSE_COUNT_TYPE_UN_SEND);
			map.put("courseCnt" ,courseCnt);
			map.put("unSendCourse" ,unSendCourse);
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("planNum", list.size());
		map.put("planList", list);
		return map;
	}

	@Override
	public Map<String, Object> loadCoursesByPrescriptionId(String prescriptionId,String knowledgeType ,Integer dataLimit) {
		Map<String,Object> map=new HashMap<String, Object>();
		if ("1".equals(knowledgeType)){  //管理处方
			GetPrescriptionDTO dto = new GetPrescriptionDTO();
			dto.setSid(prescriptionId);
			PrescriptionPO prescriptionPO = prescriptionMapper.getPrescriptionByParam(dto);

			if(prescriptionPO != null) {
				List<CourseModel> list = patientCourseMapper.loadCoursesByPrescriptionId(prescriptionId,"" ,dataLimit);

				for(CourseModel model : list){
					//获取课程已完成的章节数
					Map<String,Object> param1=new HashMap<String, Object>();
					param1.put("patientCourseId", model.getPatientCourseId());
					param1.put("pid", prescriptionPO.getMemberId());
					param1.put("courseId", model.getSid());
					param1.put("status", ConstantCourse.CHAPTER_STATUS_TYPE_2);
					Long finishNum=this.patientCourseMapper.countCourseChapterNum(param1);
					model.setFinishChapter(Integer.parseInt(finishNum.toString()));
					model.setIsLearn(ConstantCourse.COURSE_CHECK_STATUS_1);
				}

				map.put("courseNum", list.size());
				map.put("courseList", list);
			}
		}else if ("2".equals(knowledgeType)){  //首诊
			MainFollowPO follow = this.followService.getMainFollowByFidAndType(prescriptionId, FollowConstant.FOLLOW_TYPE_FIRST);
			if (follow != null){
				List<CourseModel> list = patientCourseMapper.loadCoursesByPrescriptionId("",prescriptionId ,dataLimit);
				for(CourseModel model : list){
					//获取课程已完成的章节数
					Map<String,Object> param1=new HashMap<String, Object>();
					param1.put("patientCourseId", model.getPatientCourseId());
					param1.put("pid", follow.getMemberId());
					param1.put("courseId", model.getSid());
					param1.put("status", ConstantCourse.CHAPTER_STATUS_TYPE_2);
					Long finishNum=this.patientCourseMapper.countCourseChapterNum(param1);
					model.setFinishChapter(Integer.parseInt(finishNum.toString()));
					model.setIsLearn(ConstantCourse.COURSE_CHECK_STATUS_1);
				}

				map.put("courseNum", list.size());
				map.put("courseList", list);
			}
		}

		return map;
	}

	@Override
	public Map<String, Object> delPatientCourse(String patientCourseId) {
		PatientCourseModel model = new PatientCourseModel();
		model.setSid(patientCourseId);
		model.setIsValid(0);
		patientCourseMapper.updateByPrimaryKeySelective(model);

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("rs", "true");
		return map;
	}

	@Override
	public List<CourseModel> listMemberCourseById(String memberId, String courseId) {
		Map<String ,Object> queryMap = new HashMap<>();
		queryMap.put("pid" , memberId);
		queryMap.put("courseId" , courseId);
		return this.patientCourseMapper.loadMemberCourse(queryMap);
	}

	@Override
	@Transactional
	public List<ListCourseVO> wechatSearchCourse(String keyword,String memberId) {
		if (StringUtils.isBlank(keyword)){
			return null;
		}
		//添加搜索历史记录
		CourseSearchHistoryPO courseSearchHistoryPO = new CourseSearchHistoryPO();
		courseSearchHistoryPO.setSid(DaoHelper.getSeq());
		courseSearchHistoryPO.setKeyword(keyword);
		courseSearchHistoryPO.setMemberId(memberId);
		courseMapper.addCourseSearchHistory(courseSearchHistoryPO);
		//搜索课程
		List<ListCourseVO> courseVOS = this.searchCourse(keyword,memberId);

		return courseVOS;
	}

	@Override
	public List<ListCourseVO> searchCourse(String keyword,String memberId) {
		List<ListCourseVO> nameList = courseMapper.searchCourseByName(keyword, memberId);
		List<ListCourseVO> memoList = courseMapper.searchCourseByMemo(keyword, memberId);
		for (ListCourseVO memo : memoList) {
			if (!nameList.contains(memo)){
				nameList.add(memo);
			}
		}
		return nameList;
	}

	@Override
	public List<String> loadCourseSearchHistory(String memberId) {
		List<String> list = courseMapper.loadCourseSearchHistory(memberId);
		return list;
	}

	@Override
	@Transactional
	public void clearCourseSearchHistory(String memberId) {
		courseMapper.delCourseSearchHistory(memberId);
	}

	@Override
	@Transactional
	public List<ListCourseVO> loadRecommendCourseList(String memberId) {
		List<ListCourseVO> listCourseVOS = courseMapper.loadMemberRecommendCourses(memberId);
		if (null != listCourseVOS && listCourseVOS.size() > 0){
			//若已生成直接返回
			return listCourseVOS;
		}
		List<String> list = memberRecommendCourseGenerate(memberId);
		List<ListCourseVO> courseVOS = courseMapper.listCourseVoByIds(list,memberId);
		return courseVOS;

	}

	@Override
	@Transactional
	public void reSetRecommendCourseList() {
		//所有已生成推荐课程的用户list
		List<String> memberList = courseMapper.loadExistRecommendCourseMembers();
		if (null != memberList && memberList.size() > 0){
			for (String memberId : memberList) {
				//往期推荐课程
				List<MemberRecommendCoursePO> listCoursePOS = courseMapper.loadMemberRecommendCoursePOs(memberId);
				if (null != listCoursePOS && listCoursePOS.size() > 0){
					//到达重置日
					if (DateHelper.dateCompareGetDay(DateHelper.getNowDate(),listCoursePOS.get(0).getResetDt()) >= 0){
						LOGGER.info("开始重置用户"+memberId+"推荐课程====>");
						List<String> collect = listCoursePOS.stream().map(MemberRecommendCoursePO::getSid).collect(Collectors.toList());
						courseMapper.batchDelMemberRecommendCourse(collect);//往期失效
						memberRecommendCourseGenerate(memberId);
					}
				}
			}
		}
	}


	//生成用户推荐课程
	public synchronized List<String>  memberRecommendCourseGenerate(String memberId){
		//获取用户学习计划
		Map<String, Object> param = new HashMap<>();
		param.put("unCompleteCnt", 0);
		param.put("memberId", memberId);
		List<Map<String, Object>> planList = patientCourseMapper.loadPrescriptionPlansForMember(param);
		Set<String> courseSet = new LinkedHashSet<>();//课程idSet
		List<CourseModel> planCourseList = new ArrayList<>();
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("memberId",memberId);
		//未选过+未推荐过的课程
		List<String> courseList = courseMapper.loadCourseLimit(queryParam);
		//优先推荐学习计划课程
		if (null != planList && planList.size() > 0){
			for (Map<String, Object> map : planList) {
				String prescriptionId = map.get("prescriptionId").toString();//处方Id
				List<CourseModel> courseModels = patientCourseMapper.loadCoursesByPrescriptionId(prescriptionId, null, null);
				planCourseList.addAll(courseModels);
			}
			if (planCourseList.size() > 0){
				for (int i = 0; i < planCourseList.size(); i++) {
					String courseId = planCourseList.get(i).getSid();
					//学习计划课程未被学习且未被推荐过
					if (courseList.contains(courseId)){
						courseSet.add(courseId);
					}
					if (courseSet.size() >=  10){
						break;
					}
				}
			}
		}
		//最新添加的10条未选过+未推荐过的课程
		if (courseSet.size() <  10){
			for (int i = 0; i < courseList.size(); i++) {
				courseSet.add(courseList.get(i));
				if (courseSet.size() >= 10){
					break;
				}
			}
		}

		List<String> list = new ArrayList<>(courseSet);
		if (list.size() < 10){
			//全推荐过了或不满足10条则补全10条最新添加的
			List<String> newList = courseMapper.loadCourseNew();
			for (String courseId : newList) {
				list.add(courseId);
				if (list.size() == 10){
					break;
				}
			}
		}

		//新增推荐课程
		List<MemberRecommendCoursePO> batchAddMemberRecommendCoursesList = new ArrayList<>();
		int i =0;
		for (String courseId : list) {
			i++;
			if (i == 10){
				break;
			}
			String nowDate = DateHelper.getNowDate();
			String resetDate = DateHelper.plusDate(nowDate, 7)+" 00:00:00";//重置时间为一周
			MemberRecommendCoursePO recommendCoursePO = new MemberRecommendCoursePO();
			recommendCoursePO.setSid(DaoHelper.getSeq());
			recommendCoursePO.setMemberId(memberId);
			recommendCoursePO.setCourseId(courseId);
			recommendCoursePO.setInsertDt(nowDate);
			recommendCoursePO.setModifyDt(nowDate);
			recommendCoursePO.setResetDt(resetDate);
			recommendCoursePO.setSort(i);
			batchAddMemberRecommendCoursesList.add(recommendCoursePO);
		}
		courseMapper.batchAddMemberRecommendCourses(batchAddMemberRecommendCoursesList);
		LOGGER.info("用户"+memberId+"推荐课程生成完成,课程id"+courseSet);
		return list;
	}

}
