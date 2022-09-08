
package com.comvee.cdms.defender.wechat.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.defender.wechat.vo.ListCourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comvee.cdms.defender.common.cfg.Constant;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.model.PatientModel;
import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.service.CourseServiceI;
import com.comvee.cdms.defender.wechat.model.CourseRemindModel;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.member.po.MemberPO;

@RestController
@RequestMapping("/wechat/course")
public class MemberCourseController{
	
	@Autowired
	@Qualifier("courseService")
	private CourseServiceI courseService;
	
	/**
     * @api {port}/wechat/course/loadCourseType.do 001课程类型
     * @apiName loadCourseType
     * @apiGroup course
     * @apiParam {String} flag 类型 1找知识2已学课程
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/loadCourseType.do?flag=1&pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"type": [{
					"id": "2",
					"value": "饮食"
				}, {
					"id": "3",
					"value": "运动"
				}, {
					"id": "4",
					"value": "生活方式"
				}, {
					"id": "5",
					"value": "监测"
				}, {
					"id": "6",
					"value": "药物"
				}, {
					"id": "7",
					"value": "并发症"
				}]
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/loadCourseType")
	public ResultModel loadCourseType(String flag,HttpServletRequest request,String pid) {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		Map<String,Object> map=this.courseService.loadCourseType(pid,flag);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/loadCourseByType.do 002找知识
     * @apiName loadCourseByType
     * @apiGroup course
     * @apiParam {String} type 1课程精选2饮食3运动4生活方式5监测6药物7并发症    
     * @apiParam {int} page 页数
     * @apiParam {int} rows 行数
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/loadCourseByType.do?type=7&page=1&rows=10&pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
			"pager": {
			"page": 1,
				"rows": [{
					"applyCrowd": 1, 
					"applyCrowdContent": "饮酒",  //标签
					"chapter": 4,
					"chapterMemo": "平均五分钟",  //章节描述
					"courseType": 2,
					"grade": "1",
					"img": "",  //图片  课程详情页 标题图
					"insertDt": "2018-07-28 21:45:24",
					"isValid": 1,
					"memo": "简历",
					"modifyDt": "2018-07-28 21:45:28",
					"name": "测试",
					"isLearn": 0,  //是否选过
					"sid": 1,
					"type": 2,
					"surfaceImg": "",  //封面图  我的课程 每个课程卡片图
					"thumbnail": "" //缩略图  找知识列表页 每个课程缩略图
				}],
				"total": 1
			}
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/loadCourseByType")
	public ResultModel loadCourseByType(PageRequestModel pager,String type,HttpServletRequest request,String pid) {
		//根据类型获取课程，type为空时默认课程精选分类
		ValidateTool.checkParameterIsNull("课程类型", type);
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		Map<String,Object> map=this.courseService.loadCourseByType(pager,type,pid);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/detailCourse.do 003课程详情
     * @apiName detailCourse
     * @apiGroup course
     * @apiParam {String} sid 课程id
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/detailCourse.do?sid=181226140900054
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "成功",
			"obj": {
			"course": {
			"applyCrowd": 1, 
				"applyCrowdContent": "饮酒",  //标签
				"chapter": 4,
				"chapterMemo": "平均五分钟",  //章节描述
				"courseType": 2,
				"grade": "1",
				"img": "",  //图片  课程详情页 标题图
				"insertDt": "2018-07-28 21:45:24",
				"isValid": 1,
				"memo": "简历",
				"modifyDt": "2018-07-28 21:45:28",
				"name": "测试",
				"sid": 1,
				"type": 2,
				"surfaceImg": "",  //封面图  我的课程 每个课程卡片图
				"thumbnail": "" //缩略图  找知识列表页 每个课程缩略图
			}
				
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/detailCourse")
	public ResultModel detailCourse(String sid,HttpServletRequest request,String pid,String isPlan,String patientCourseId)  {
		ValidateTool.checkParameterIsNull("课程id", sid);
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		
		if(StringUtils.isBlank(isPlan)) {
			isPlan = "0";
		}
		
		Map<String,Object> map = this.courseService.detailCourse(sid,pid,isPlan,patientCourseId);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	
	
	/**
     * @api {port} /wechat/course/loadPrescriptionPlans.do 003查询管理处方知识计划
     * @apiName loadPrescriptionPlans
     * @apiGroup course
     * @apiParam {String} pid 用户id
     * @apiSampleRequest  /intelligent-prescription/wechat/course/loadPrescriptionPlans.do?pid=190619154159100005
     * @apiSuccessExample {json} Success-Response:
     * {
		    "code": "0",
		    "msg": "success!",
		    "obj": {
		        "planList": [{
		            "courseCnt": 8,
		            "insertDt": "2019-07-13 13:05:52",
		            "prescriptionId": 190713130432100000
		        }],
		        "planNum": 1
		    },
		    "success": true
		}
     *
     **/
	@RequestMapping("/loadPrescriptionPlans")
	public ResultModel loadPrescriptionPlans(HttpServletRequest request,String pid)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		Map<String,Object> map = this.courseService.loadPrescriptionPlans(pid);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	
	/**
     * @api {port}/wechat/course/addMemberCourse.do 004.添加课程
     * @apiName addMemberCourse
     * @apiGroup course
     * @apiParam {String} sid 课程id
     * @apiParam {String} origin 0默认 1干预推荐
     * @apiParam {String} isPlan 0不是学习计划  1学习计划
     * @apiParam {String} planId 计划id
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/addMemberCourse.do?sid=181226140900054&pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "成功",
			"obj": null,
			"success": true
		}
     *
     **/
	@RequestMapping("/addMemberCourse")
	public ResultModel addMemberCourse(String sid,HttpServletRequest request,String pid,String origin,String isPlan,Long planId)  {
		ValidateTool.checkParameterIsNull("课程id", sid);
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		if(StringUtils.isBlank(isPlan)) {
			isPlan = "0";
		}
		this.courseService.addMemberCourse(sid,pid,origin,"0",planId);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/loadMemberCourse.do 005.我的课程
     * @apiName loadMemberCourse
     * @apiGroup course
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/loadMemberCourse.do?pid=102&allFlag=1
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"courseList": [{
					"applyCrowd": "code6,code107,code118",
					"applyCrowdContent": "空腹正常,国际警察日,中国青年节",
					"chapter": 31,
					"chapterMemo": "5分钟1",
					"courseType": 2,
					"finishChapter": 0,
					"grade": "L41",
					"img": "没有1",
					"insertDt": "2018-07-28 18:03:11",
					"isValid": 1,
					"memo": "默认课程1默认课程1111",
					"modifyDt": "",
					"name": "推荐",
					"sid": 180728180200001,
					"surfaceImg": "",
					"thumbnail": "",
					"type": 2
				}],
				"courseNum": 6
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/loadMemberCourse")
	public ResultModel loadMemberCourse(HttpServletRequest request,String pid,String allFlag,String isPlan)  {
		if(Constant.RELEASE){
			MemberPO member=ValidateTool.checkPatient();
			pid=member.getMemberId();
		}
		if(StringUtils.isBlank(isPlan)) {
			isPlan = "0"; //默认是已选列表
		}
		Map<String,Object> map=this.courseService.loadMemberCourse(pid,allFlag,isPlan);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	
	/**
     * @api {port}/wechat/course/loadCoursesByPrescriptionId.do 005.查询管理处方下的课程列表
     * @apiName loadCoursesByPrescriptionId
     * @apiGroup course
     * @apiParam {String} prescriptionId 管理处方id
     * @apiSampleRequest  http://localhost:8099/intelligent-prescription/wechat/course/loadCoursesByPrescriptionId?prescriptionId=190713130432100000
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"courseList": [{
					"applyCrowd": "code6,code107,code118",
					"applyCrowdContent": "空腹正常,国际警察日,中国青年节",
					"chapter": 31,
					"chapterMemo": "5分钟1",
					"courseType": 2,
					"finishChapter": 0,
					"grade": "L41",
					"img": "没有1",
					"insertDt": "2018-07-28 18:03:11",
					"isValid": 1,
					"memo": "默认课程1默认课程1111",
					"modifyDt": "",
					"name": "推荐",
					"sid": 180728180200001,
					"surfaceImg": "",
					"thumbnail": "",
					"type": 2
				}],
				"courseNum": 6
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/loadCoursesByPrescriptionId")
	public ResultModel loadCoursesByPrescriptionId(String prescriptionId,String knowledgeType,Integer dataLimit)  {
		ValidateTool.checkParameterIsNull("dataLimit" ,dataLimit);
//		if(Constant.RELEASE){
//			MemberPO patient=ValidateTool.checkPatient();
//			pid=patient.getMemberId();
//		}
//		if(StringUtils.isBlank(isPlan)) {
//			isPlan = "0"; //默认是已选列表
//		}
		Map<String,Object> map=this.courseService.loadCoursesByPrescriptionId(prescriptionId,knowledgeType ,dataLimit);
		ResultModel result = new ResultModel(map);
        return result;
	}

	
	
	/**
     * @api {port}/wechat/course/loadMemberCourseRemind.do 006.获取提醒
     * @apiName loadMemberCourseRemind
     * @apiGroup course
     * 
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/loadMemberCourseRemind.do?pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"courseRemind": {
					"friday": 0, 星期五 1开0关
					"insertDt": "", 插入时间
					"isOpen": "2",  是否开启 1开0关
					"isValid": "1",
					"modifyDt": "",
					"monday": 1,  星期一
					"pid": "1", 
					"remindTime": "09:02:00", 提醒时间
					"saturday": 0,  星期六
					"sid": "180731103300001",
					"sunday": 0, 星期天
					"thursday": 0, 星期四
					"tuesday": 0, 星期二
					"wednesday": 0 星期三
				}
			},
			"success": true
		}
     *
     **/
	@RequestMapping("loadMemberCourseRemind")
	public ResultModel loadMemberCourseRemind(HttpServletRequest request,String pid)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		Map<String,Object> map=this.courseService.loadMemberCourseRemind(pid);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/setMemberCourseRemind.do 007.设置学习提醒
     * @apiName setMemberCourseRemind
     * @apiGroup course
     * @apiParam {String} sid 主键（修改的时候传，新增不传）
     * @apiParam {String} pid 用户id
     * @apiParam {String} isOpen 是否开启 1开0关
     * @apiParam {String} remindTime 提醒时间 格式hh:mm:ss
     * @apiParam {String} monday 星期1 开1关0
     * @apiParam {String} tuesday 星期2 开1关0
     * @apiParam {String} wednesday 星期3 开1关0
     * @apiParam {String} thursday 星期4 开1关0
     * @apiParam {String} friday 星期5 开1关0
     * @apiParam {String} saturday 星期6 开1关0
     * @apiParam {String} sunday 星期7 开1关0
     * 
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/setMemberCourseRemind.do?pid=102&isOpen=1&remindTime=08:01:00&monday=1
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "成功",
			"obj": null,
			"success": true
		}
     *
     **/
	@RequestMapping("setMemberCourseRemind")
	public ResultModel setMemberCourseRemind(CourseRemindModel courseRemindModel,HttpServletRequest request,String pid)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		courseRemindModel.setPid(pid);
		//ValidateTool.checkParameterIsNull("是否启用", courseRemindModel.getIsOpen());
		this.courseService.setMemberCourseRemind(courseRemindModel);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/listCourseChapter.do 008.章节列表
     * @apiName listCourseChapter
     * @apiGroup course
     * @apiParam {String} courseId 课程id
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/listCourseChapter.do?courseId=181226140900054&pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"list": [{
					"chapterMemo": "章节1章节1章节1章节1",  //长度描述
					"chapterName": "章节1",
					"courseId": 0,
					"img": "",    //点开后的场景图
					"insertDt": "",
					"isValid": 0,
					"modifyDt": "",
					"score": "",
					"sid": 180728184800001,
					"sort": 0,
					"status": "1",   //学习状态 1未学习2学习中3已学习4待解锁
					"thumbnail": ""   //缩略图
				}, {
					"chapterMemo": "章节1章节1章节1章节1",
					"chapterName": "章节2",
					"courseId": 0,
					"img": "",
					"insertDt": "",
					"isValid": 0,
					"modifyDt": "",
					"score": "",
					"sid": 180728184800002,
					"sort": 0,
					"status": "4",
					"thumbnail": ""
				}]
			},
			"success": true
		}
     *
     **/
	@RequestMapping("listCourseChapter")
	public ResultModel listCourseChapter(HttpServletRequest request,String pid,String courseId,String patientCourseId)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		ValidateTool.checkParameterIsNull("课程id", courseId);
		Map<String,Object> map=this.courseService.courseChapter(pid,courseId,patientCourseId);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/detailCourseChapter.do 009.章节下的知识点详情
     * @apiName detailCourseChapter
     * @apiGroup course
     * @apiParam {String} chapterId 章节id
     * @apiParam {String} courseId 课程id
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/detailCourseChapter.do?courseId=181226140900054&chapterId=181226141000055&pid=102
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"knowledgeList": [{
					"content": [{
						"content": "asdfasdfadfdfsaf",
						"type": 2   //1图片2语音3大标题4小标题5正文
					}, {
						"content": "5454541",
						"type": 3
					}, {
						"content": "8974848",
						"type": 4
					}],
					"id": "180730111200002",
					"insertDt": "2018-07-30 11:13:09",
					"isValid": 1,
					"modifyDt": "",
					"ques": {
						"answer": "",   //答案
						"items": [{     //选项
							"con": "测试2",
							"value": "2"
						}, {
							"con": "测试1",
							"value": "1"
						}],
						"title": "在哪啊",  //题目
						"type": 2       //选项2单选题，3多选题
					},
					"title": "文章2",
					"type": 1
				}, {
					"content": [],
					"id": "180730111300004",
					"insertDt": "2018-07-30 11:13:27",
					"isValid": 1,
					"modifyDt": "",
					"ques": {
						"answer": "",
						"title": "在哪啊",
						"type": 2
					},
					"title": "文章4",
					"type": 1
				}, {
					"content": [],
					"id": "180730111200003",
					"insertDt": "2018-07-30 11:13:17",
					"isValid": 1,
					"modifyDt": "",
					"ques": null,
					"title": "文章3",
					"type": 1
				}]
			},
			"success": true
		}
     *
     **/
	@RequestMapping("detailCourseChapter")
	public ResultModel detailCourseChapter(HttpServletRequest request,String pid,String chapterId,String courseId,String patientCourseId)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		ValidateTool.checkParameterIsNull("篇章id", chapterId);
		ValidateTool.checkParameterIsNull("课程id", courseId);
		Map<String,Object> map=this.courseService.detailCourseChapter(pid,chapterId,courseId,patientCourseId);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/submitCourseChapter.do 010.提交回答
     * @apiName submitCourseChapter
     * @apiGroup course
     * @apiParam {String} pid 用户id
     * @apiParam {String} patientCourseId 用户课程表id
     * @apiParam {String} chapterId 章节id
     * @apiParam {String} courseId 课程id
     * @apiParam {String} score 分数
     * @apiParam {String} startDt 开始时间
     * @apiParam {String} endDt 提交时间
     * @apiParam {String} answerJson 数组转json，数据里放map，需要传入的key包括 qid(题目id) knowledgeId(知识点id) answer(回答)
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/submitCourseChapter.do?
     * @apiSuccessExample {json} Success-Response:
     * {}
     *
     **/
	@RequestMapping("submitCourseChapter")
	public ResultModel submitCourseChapter(HttpServletRequest request,String pid,String patientCourseId,String chapterId,String score,
			String answerJson,String courseId,String startDt,String endDt,String assess,String courseAnswerJson)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		ValidateTool.checkParameterIsNull("篇章id", chapterId);
		ValidateTool.checkParameterIsNull("分数", score);
		ValidateTool.checkParameterIsNull("回答", answerJson);
		ValidateTool.checkParameterIsNull("课程id", courseId);
		ValidateTool.checkParameterIsNull("章节开始时间", startDt);
		ValidateTool.checkParameterIsNull("章节结束时间", endDt);
		this.courseService.submitCourseChapter(pid,patientCourseId,chapterId,score,answerJson,courseId,startDt,endDt,assess,courseAnswerJson);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/loadMemberFinishCourseByType.do 011.已完成课程
     * @apiName loadMemberFinishCourse
     * @apiGroup course
     * @apiParam {String} type 课程类型
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/loadMemberFinishCourseByType.do
     * @apiSuccessExample {json} Success-Response:
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"courseList": [{
					"chapterList": [{
						"chapterName": "第一讲：空腹正常就没有患糖尿病吗？"
					}, {
						"chapterName": "第二讲：三改一增，击败高血糖"
					}],
					"name": "餐后高血糖怎么破？",
					"sid": "180802145800036"
				}]
			},
			"success": true
		}
     * 
     *
     **/
	@RequestMapping("/loadMemberFinishCourseByType")
	public ResultModel loadMemberFinishCourseByType(HttpServletRequest request,String pid,PageRequestModel pager,String isPlan)  {
		if(Constant.RELEASE){
			MemberPO patient=ValidateTool.checkPatient();
			pid=patient.getMemberId();
		}
		if(StringUtils.isBlank(isPlan)) {
			isPlan = "0";
		}
		//ValidateTool.checkParameterIsNull("课程类别", type);
		Map<String,Object> map=this.courseService.loadMemberFinishCourseByType(pid,isPlan,pager);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/detailKnowledge.do 012.知识点详情
     * @apiName detailKnowledge
     * @apiGroup course
     * @apiParam {String} knowledgeId 知识点id
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/detailKnowledge.do?knowledgeId=181226140100015&pie=102
     * @apiSuccessExample {json} Success-Response:
     * {
		"code": "0",
		"msg": "success!",
		"obj": {
			"knowledge": {
				"content": [{
					"content": "空腹正常，并不代表身体是正常的",
					"type": 3   //1图片2语音3大标题4小标题5正文
				}, {
					"content": "下面这张图，由下而上分别不同颜色的曲线（绿色，红色，蓝色）依次代表随着糖尿病患病时间的延长，血糖逐渐变化的情况。 我们重点来看：",
					"type": 5
				}, {
					"content": "刚患病不久的绿色曲线，可以看到0点-8点这一时段，也就是代表着空腹血糖或是夜间血糖的情况，血糖在6 mmol/L左右（正常人空腹血糖为4.4.-6.1），是正常的。但是早餐后血糖达到了8 mmol/L以上（正常餐后2小时血糖为4.4-7.8）了，已经不正常了。",
					"type": 5
				}, {
					"content": "http://test.izhangkong.com:9091/upload/voice/20180802/180802144800004.png",
					"type": 1
				}, {
					"content": "http://test.izhangkong.com:9091/upload//image/20180802/180802145100022.mp3",
					"type": 2
				}],
				"id": "180802150700039",
				"img": "",
				"insertDt": "2018-08-02 15:08:17",
				"isValid": 1,
				"memo": "",
				"modifyDt": "",
				"ques": null,
				"title": "第一讲：空腹正常就没有患糖尿病吗？",
				"type": 1
			}
		},
		"success": true
	}
     * 
     *
     **/
	@RequestMapping("/detailKnowledge")
	public ResultModel detailKnowledge(HttpServletRequest request,String pid,String knowledgeId)  {
		ValidateTool.checkParameterIsNull("知识点id", knowledgeId);
		Map<String,Object> map=this.courseService.detailKnowledge(null,knowledgeId);
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	/**
     * @api {port}/wechat/course/patientCenter.do 013.个人中心
     * @apiName patientCenter
     * @apiGroup course
     * @apiSampleRequest  http://127.0.0.1:8099/intelligent-prescription/wechat/course/patientCenter.do
     * @apiSuccessExample {json} Success-Response:
     * 
     * {
			"code": "0",
			"msg": "success!",
			"obj": {
				"courseRemind": {
					"courserRemind": "1",
					"friday": 1,
					"isOpen": "0",
					"isValid": "1",
					"lastStudyDate": "2018-09-29 09:58:39.0",
					"monday": 1,
					"pid": "316523",
					"remindNum": "0",
					"remindTime": "09:00",
					"saturday": 1,
					"sid": "180824033500001",
					"sunday": 1,
					"thursday": 1,
					"tuesday": 1,
					"wednesday": 1
				}
			},
			"success": true
		}
     *
     **/
	@RequestMapping("/patientCenter")
	public ResultModel patientCenter(HttpServletRequest request)  {
		MemberPO patient=ValidateTool.checkPatient();
		String pid=patient.getMemberId();
		String patientName= patient.getMemberName();//患者名字";//this.dxyOutside.getPatientInfo(pid);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", patientName);
		map.put("avatar", patient.getPicUrl());
		ResultModel result = new ResultModel(map);
        return result;
	}
	
	
	/**
	 * 删除已选课程
	 * @param request
	 * @param pid
	 * @param patientCourseId
	 * @return
	 */
	@RequestMapping("/delPatientCourse")
	public ResultModel delPatientCourse(HttpServletRequest request,String pid,String patientCourseId) {
		Map<String,Object> map = courseService.delPatientCourse(patientCourseId);
		ResultModel result = new ResultModel(map);
        return result;
	}
	/*---------------v9.1.0患教改版---------------*/
	/**
	 * 搜索课程
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/searchCourse")
	public ResultModel searchCourse(String keyword,String memberId) {
		if(Constant.RELEASE){
			MemberPO patient= ValidateTool.checkPatient();
			memberId=patient.getMemberId();
		}
		ValidateTool.checkParamIsNull(memberId,"memberId");
		if (memberId.equals("-1")){
			throw  new BusinessException("未登录");
		}
		List<ListCourseVO> listCourseVOS = courseService.wechatSearchCourse(keyword, memberId);
		ResultModel result = new ResultModel(listCourseVOS);
		return result;
	}

	/**
	 * 加载课程搜索历史列表
	 * @return
	 */
	@RequestMapping("/loadCourseSearchHistory")
	public ResultModel loadCourseSearchHistory(String memberId) {
		if(Constant.RELEASE){
			MemberPO patient= ValidateTool.checkPatient();
			memberId=patient.getMemberId();
		}
		ValidateTool.checkParamIsNull(memberId,"memberId");
		if (memberId.equals("-1")){
			throw  new BusinessException("未登录");
		}
		List<String> list = courseService.loadCourseSearchHistory(memberId);
		ResultModel result = new ResultModel(list);
		return result;
	}

	/**
	 * 清空课程搜索历史记录
	 * @return
	 */
	@RequestMapping("/clearCourseSearchHistory")
	public ResultModel clearCourseSearchHistory(String memberId) {
		if(Constant.RELEASE){
			MemberPO patient= ValidateTool.checkPatient();
			memberId=patient.getMemberId();
		}
		ValidateTool.checkParamIsNull(memberId,"memberId");
		if (memberId.equals("-1")){
			throw  new BusinessException("未登录");
		}
		courseService.clearCourseSearchHistory(memberId);
		return new ResultModel();
	}

	/**
	 * 推荐课程列表
	 * @return
	 */
	@RequestMapping("/loadRecommendCourseList")
	public ResultModel loadRecommendCourseList(String memberId) {
		if(Constant.RELEASE){
			MemberPO patient= ValidateTool.checkPatient();
			memberId=patient.getMemberId();
		}
		ValidateTool.checkParamIsNull(memberId,"memberId");
		if (memberId.equals("-1")){
			throw  new BusinessException("未登录");
		}
		List<ListCourseVO>  courseVOS = courseService.loadRecommendCourseList(memberId);
		return new ResultModel(courseVOS);
	}
	
}
