package com.comvee.cdms.defender.common.cfg;

public class ConstantErrorMessage {
	//通用异常0xxx
	public static String FORMAT_TRANSFORMATION_ERROR_CODE="0001";
	public static String FORMAT_TRANSFORMATION_ERROR_MESSAGE="格式转换异常";
	
	public static String PATIENT_NULL_ERROR_CODE="0002";
	public static String PATIENT_NULL_ERROR_MESSAGE="用户信息已失效";
	
	//课程异常10xx
	public static String COURSE_ADD_REPET_ERROR_CODE="1001";
	public static String COURSE_ADD_REPET_ERROR_MESSAGE="您已添加该课程，无需重复添加";
	
	public static String COURSE_ADD_MAX_ERROR_CODE="1002";
	public static String COURSE_ADD_MAX_ERROR_MESSAGE="您目前已选满10门课了，学完已选课程才能添加新课噢";
	
	public static String COURSE_NOT_EXITS_ERROR_CODE="1003";
	public static String COURSE_NOT_EXITS_ERROR_MESSAGE="您要查找的课程不存在~";
	
	public static String COURSE_CHAPTER_NOT_EXITS_ERROR_CODE="1004";
	public static String COURSE_CHAPTER_NOT_EXITS_ERROR_MESSAGE="您要查找的课程章节不存在~";
	
	public static String COURSE_KNOWLEDGE_NOT_EXITS_ERROR_CODE="1005";
	public static String COURSE_KNOWLEDGE_NOT_EXITS_ERROR_MESSAGE="您要查找的知识点不存在~";
	
	//用户异常11xx
	public static String PATIENT_NOT_EXIST_ERROR_CODE="1101";
	public static String PATIENT_NOT_EXIST_ERROR_MESSAGE="用户信息不存在";
	
	public static String PATIENT_NOT_NOVICE_ERROR_CODE="1102";
	public static String PATIENT_NOT_NOVICE_ERROR_MESSAGE="用户已超过新手任务有效期";
	
	//每日一答异常12xx
	public static String PATIENT_DAILY_QUESTION_REPET_CODE="1201";
	public static String PATIENT_DAILY_QUESTION_REPET_MESSAGE="用户今日已答过题";
	
	public static String DAILY_QUESTION_NOT_EXIT_CODE="1202";
	public static String DAILY_QUESTION_NOT_EXIT_MESSAGE="今日暂无题目";
	
	public static String PATIENT_DAILY_QUESTION_NOT_EXIT_CODE="1203";
	public static String PATIENT_DAILY_QUESTION_NOT_EXIT_MESSAGE="没有该日答题记录";
	
	//监测方案异常13xx
	public static String PATIENT_MONIROT_TEMP_NOT_EXITS_CODE="1301";
	public static String PATIENT_MONIROT_TEMP_NOT_EXITS_MESSAGE="监测方案模板不存在";
	
	public static String PATIENT_MONIROT_NOT_EXITS_CODE="1302";
	public static String PATIENT_MONIROT_NOT_EXITS_MESSAGE="用户监测方案不存在";
	
	public static String PATIENT_MONIROT_NOT_CONFORM_CODE="1303";
	public static String PATIENT_MONIROT_NOT_CONFORM_MESSAGE="监测方案不符合模板要求";
}
