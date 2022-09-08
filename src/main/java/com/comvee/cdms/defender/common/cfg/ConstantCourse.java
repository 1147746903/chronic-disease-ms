package com.comvee.cdms.defender.common.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantCourse {
	//选必修课程
	public static Integer COURSE_TYPE_OPTION=1;//选修课程
	public static Integer COURSE_TYPE_REQUIRE=2;//必修课程
	
	
	//课堂类型分类
	public static String COURSE_TYPE_1="1";//课程精选    
	public static String COURSE_TYPE_2="2";//饮食    
	public static String COURSE_TYPE_3="3";//运动  
	public static String COURSE_TYPE_4="4";//生活方式
	public static String COURSE_TYPE_5="5";//监测
	public static String COURSE_TYPE_6="6";//药物
	public static String COURSE_TYPE_7="7";//并发症
	public static String COURSE_TYPE_8="8";//项目介绍
	public static String COURSE_TYPE_9="9";//胰岛素
	
	//用户标签大类
	public static String PATIENT_LABEL_1="1";//入组时间  
	public static String PATIENT_LABEL_2="2";//患者状态  
	public static String PATIENT_LABEL_3="3";//医生状态  
	public static String PATIENT_LABEL_4="4";//医生参与CDS  
	public static String PATIENT_LABEL_5="5";//年龄  
	public static String PATIENT_LABEL_6="6";//性别  
	public static String PATIENT_LABEL_7="7";//病程  
	public static String PATIENT_LABEL_8="8";//糖化血红蛋白  
	public static String PATIENT_LABEL_9="9";//糖尿病类型  
	public static String PATIENT_LABEL_10="10";//身材  
	public static String PATIENT_LABEL_11="11";//胰岛素使用经验  
	public static String PATIENT_LABEL_12="12";//胰岛素  
	public static String PATIENT_LABEL_13="13";//注射方案 
	public static String PATIENT_LABEL_14="14";//胰岛素日总剂量  
	public static String PATIENT_LABEL_15="15";//是否联用口服药  
	public static String PATIENT_LABEL_16="16";//并发症  
	public static String PATIENT_LABEL_17="17";//血压  
	public static String PATIENT_LABEL_18="18";//总胆固醇  
	public static String PATIENT_LABEL_19="19";//高密度脂蛋白胆固醇  
	public static String PATIENT_LABEL_20="20";//甘油三酯  
	public static String PATIENT_LABEL_21="21";//低密度脂蛋白胆固醇  
	public static String PATIENT_LABEL_22="22";//学习意愿  
	public static String PATIENT_LABEL_23="23";//测量频率  
	public static String PATIENT_LABEL_24="24";//试纸兑换  
	public static String PATIENT_LABEL_25="25";//血糖水平  
	public static String PATIENT_LABEL_26="26";//不良习惯  
	public static String PATIENT_LABEL_27="27";//就诊习惯  
	
	//用户标签细分
	public static String PATIENT_LABEL_1_1="1-1";//新入组患者
	public static String PATIENT_LABEL_1_2="1-2";//3个月内患者
	public static String PATIENT_LABEL_1_3="1-3";//半年内患者
	public static String PATIENT_LABEL_1_4="1-4";//1年内患者
	public static String PATIENT_LABEL_1_5="1-5";//1年以上患者
	public static String PATIENT_LABEL_2_1="2-1";//活跃
	public static String PATIENT_LABEL_2_2="2-2";//不活跃
	public static String PATIENT_LABEL_2_3="2-3";//退组
	public static String PATIENT_LABEL_3_1="3-1";//开通了在线问诊
	public static String PATIENT_LABEL_3_2="3-2";//未开通在线问诊
	public static String PATIENT_LABEL_4_1="4-1";//在CDS项目中
	public static String PATIENT_LABEL_4_2="4-2";//不在CDS项目中
	public static String PATIENT_LABEL_5_1="5-1";//青年
	public static String PATIENT_LABEL_5_2="5-2";//中年
	public static String PATIENT_LABEL_5_3="5-3";//老年
	public static String PATIENT_LABEL_6_1="6-1";//男
	public static String PATIENT_LABEL_6_2="6-2";//女
	public static String PATIENT_LABEL_7_1="7-1";//不到1年
	public static String PATIENT_LABEL_7_2="7-2";//1-5年
	public static String PATIENT_LABEL_7_3="7-3";//6-10年
	public static String PATIENT_LABEL_7_4="7-4";//10-20年
	public static String PATIENT_LABEL_7_5="7-5";//20年以上
	public static String PATIENT_LABEL_8_1="8-1";//达标
	public static String PATIENT_LABEL_8_2="8-2";//血糖控制不理想
	public static String PATIENT_LABEL_8_3="8-3";//持续高血糖
	public static String PATIENT_LABEL_9_1="9-1";//1型糖尿病
	public static String PATIENT_LABEL_9_2="9-2";//2型糖尿病
	public static String PATIENT_LABEL_9_3="9-3";//其他糖尿病类型
	public static String PATIENT_LABEL_10_1="10-1";//偏瘦
	public static String PATIENT_LABEL_10_2="10-2";//正常
	public static String PATIENT_LABEL_10_3="10-3";//偏胖
	public static String PATIENT_LABEL_10_4="10-4";//肥胖
	public static String PATIENT_LABEL_10_5="10-5";//重度肥胖
	public static String PATIENT_LABEL_11_1="11-1";//无胰岛素使用经验
	public static String PATIENT_LABEL_11_2="11-2";//无优泌乐使用经验
	public static String PATIENT_LABEL_11_3="11-3";//有优泌乐使用经验
	public static String PATIENT_LABEL_12_1="12-1";//优泌乐
	public static String PATIENT_LABEL_12_2="12-2";//优泌乐25
	public static String PATIENT_LABEL_12_3="12-3";//优泌乐50
	public static String PATIENT_LABEL_13_1="13-1";//每天2针及以下
	public static String PATIENT_LABEL_13_2="13-2";//每天3针
	public static String PATIENT_LABEL_13_3="13-3";//每天4针
	public static String PATIENT_LABEL_13_4="13-4";//胰岛素泵
	public static String PATIENT_LABEL_14_1="14-1";//30单位以下
	public static String PATIENT_LABEL_14_2="14-2";//≥30单位
	public static String PATIENT_LABEL_15_1="15-1";//否
	public static String PATIENT_LABEL_15_2="15-2";//双胍类
	public static String PATIENT_LABEL_15_3="15-3";//α-糖苷酶抑制剂
	public static String PATIENT_LABEL_15_4="15-4";//DPP-4抑制剂
	public static String PATIENT_LABEL_15_5="15-5";//噻唑烷二酮类
	public static String PATIENT_LABEL_15_6="15-6";//其他口服药
	public static String PATIENT_LABEL_16_1="16-1";//无并发症
	public static String PATIENT_LABEL_16_2="16-2";//冠心病
	public static String PATIENT_LABEL_16_3="16-3";//脑血管病
	public static String PATIENT_LABEL_16_4="16-4";//糖尿病足
	public static String PATIENT_LABEL_16_5="16-5";//视网膜病变
	public static String PATIENT_LABEL_16_6="16-6";//神经病变
	public static String PATIENT_LABEL_16_7="16-7";//糖尿病肾病
	public static String PATIENT_LABEL_17_1="17-1";//血压
	public static String PATIENT_LABEL_18_1="18-1";//总胆固醇
	public static String PATIENT_LABEL_19_1="19-1";//高密度脂蛋白胆固醇
	public static String PATIENT_LABEL_20_1="20-1";//甘油三酯
	public static String PATIENT_LABEL_21_1="21-1";//低密度脂蛋白胆固醇
	public static String PATIENT_LABEL_22_1="22-1";//无意愿
	public static String PATIENT_LABEL_22_2="22-2";//低意愿
	public static String PATIENT_LABEL_22_3="22-3";//中等意愿
	public static String PATIENT_LABEL_22_4="22-4";//高意愿
	public static String PATIENT_LABEL_23_1="23-1";//监测频率低
	public static String PATIENT_LABEL_23_2="23-2";//监测频率正常
	public static String PATIENT_LABEL_23_3="23-3";//监测频率高
	public static String PATIENT_LABEL_24_1="24-1";//未申请免费试纸
	public static String PATIENT_LABEL_24_2="24-2";//只申请过免费试纸
	public static String PATIENT_LABEL_24_3="24-3";//使用积分兑换过试纸
	public static String PATIENT_LABEL_25_1="25-1";//低血糖
	public static String PATIENT_LABEL_25_2="25-2";//血糖波动
	public static String PATIENT_LABEL_26_1="26-1";//自行停药/调药
	public static String PATIENT_LABEL_26_2="26-2";//运动认识不足
	public static String PATIENT_LABEL_26_3="26-3";//有饮酒行为
	public static String PATIENT_LABEL_26_4="26-4";//不会操作血糖仪
	public static String PATIENT_LABEL_26_5="26-5";//饮食认识不足
	public static String PATIENT_LABEL_27_1="27-1";//无定期就诊
	public static String PATIENT_LABEL_27_2="27-2";//定期就诊

	
	
	//添加课程最大数
	public static Integer COURSE_ADD_MAX_NUM=10;
	
	//课堂类型分类
	public static List<Map<String,Object>> courseTypeList=new ArrayList<Map<String,Object>>();
	static{
		//courseTypeList.add(new HashMap<String, Object>(){{put(COURSE_TYPE_1,"课程精选");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_2);put("value","饮食");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_3);put("value","运动");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_4);put("value","生活方式");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_5);put("value","监测");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_6);put("value","药物");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_7);put("value","并发症");}});
		courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_9);put("value","胰岛素");}});
		//courseTypeList.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_8);put("value","项目介绍");}});
	}
	
	//课堂类型分类
	public static List<Map<String,Object>> courseTypeListAll=new ArrayList<Map<String,Object>>();
	static{
		//courseTypeList.add(new HashMap<String, Object>(){{put(COURSE_TYPE_1,"课程精选");}});
		//courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_1);put("value","为您推荐");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_2);put("value","饮食");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_3);put("value","运动");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_4);put("value","生活方式");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_5);put("value","监测");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_6);put("value","药物");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_7);put("value","并发症");}});
		courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_9);put("value","胰岛素");}});
		//courseTypeListAll.add(new HashMap<String, Object>(){{put("id",COURSE_TYPE_8);put("value","项目介绍");}});
	}
	
	
	//课程学习状态
	public static String COURSE_STATUS_1="1";//未完成
	public static String COURSE_STATUS_2="2";//已完成
	
	//课程选择状态
	public static Integer COURSE_CHECK_STATUS_1=1;//已选择
	public static Integer COURSE_CHECK_STATUS_0=0;//未选择
	
	//课程提醒状态
	public static String COURSE_REMIND_TYPE_1="1";//开启
	public static String COURSE_REMIND_TYPE_2="2";//关闭
	
	//章节学习状态(前端)
	public static String CHAPTER_STATUS_1="1";//未学习
	public static String CHAPTER_STATUS_2="2";//学习中
	public static String CHAPTER_STATUS_3="3";//已学习
	public static String CHAPTER_STATUS_4="4";//待解锁
	
	//章节学习状态(后台数据库)
	public static String CHAPTER_STATUS_TYPE_1="1";//学习中
	public static String CHAPTER_STATUS_TYPE_2="2";//已学习
	public static String CHAPTER_STATUS_TYPE_3="3";//待学习
	
	
	/**
	 * 知识学习计划知识类型和课程类型对应关系
	 */
	public final static Map<String,String> PLAN_TYPE_LINK_COURSE_TYPE = new HashMap<>();
	static {
		/**
		 * 1.       饮食-糖尿病科学饮食与食物交换份法；
			2.       运动-正确的运动方法与相关注意事项；
			3.       用药-合理用药知识；
			4.       并发症-糖尿病并发症知识；
			5.       监测-合理正确的自我血糖监测；
			6.       胰岛素-正确注射胰岛素知识；
			7.       生活方式-健康生活方式与注意事项；
		 */
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS02", "1");//饮食
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS03", "2");//运动
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS04", "3");//用药
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS05", "4");//并发症
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS07", "5");//监测
		
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS10", "9");//生活方式
		PLAN_TYPE_LINK_COURSE_TYPE.put("LJZS11", "7");//胰岛素
	}
	
	/**
	 * 知识学习计划知识类型和课程类型对应关系
	 */
	public final static Map<String,String> COURSE_TYPE_LINK_PLAN_TYPE = new HashMap<>();
	static {
		/**
		 * 1.       饮食-糖尿病科学饮食与食物交换份法；
			2.       运动-正确的运动方法与相关注意事项；
			3.       用药-合理用药知识；
			4.       并发症-糖尿病并发症知识；
			5.       监测-合理正确的自我血糖监测；
			6.       胰岛素-正确注射胰岛素知识；
			7.       生活方式-健康生活方式与注意事项；
		 */
		COURSE_TYPE_LINK_PLAN_TYPE.put("1", "LJZS02");//饮食
		COURSE_TYPE_LINK_PLAN_TYPE.put("2", "LJZS03");//运动
		COURSE_TYPE_LINK_PLAN_TYPE.put("3", "LJZS04");//用药
		COURSE_TYPE_LINK_PLAN_TYPE.put("4", "LJZS05");//并发症
		COURSE_TYPE_LINK_PLAN_TYPE.put("5", "LJZS07");//监测

		COURSE_TYPE_LINK_PLAN_TYPE.put("9", "LJZS10");//生活方式
		COURSE_TYPE_LINK_PLAN_TYPE.put("7", "LJZS11");//胰岛素
	}

	/**
	 * 知识学习计划知识类型和课程类型对应关系 (高血压)
	 */
	public final static Map<String,String> COURSE_TYPE_LINK_PLAN_TYPE_HYP = new HashMap<>();
	static {
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("1", "GXYZS02");//饮食
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("2", "GXYZS03");//运动
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("3", "GXYZS04");//用药
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("4", "GXYZS05");//并发症相关
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("5", "GXYZS06");//血压监测
		COURSE_TYPE_LINK_PLAN_TYPE_HYP.put("6", "GXYZS07");//生活方式
	}


	/**
	 * 知识学习计划知识类型和课程类型对应关系 (高血压)
	 */
	public final static Map<String,String> PLAN_TYPE_LINK_COURSE_TYPE_HYP = new HashMap<>();
	static {
		for(Map.Entry<String ,String> entry : COURSE_TYPE_LINK_PLAN_TYPE_HYP.entrySet()){
			PLAN_TYPE_LINK_COURSE_TYPE_HYP.put(entry.getValue() ,entry.getKey());
		}
	}

	public final static String COURSE_TYPE_YS = "1";  //饮食
	public final static String COURSE_TYPE_YD = "2";   //运动
	public final static String COURSE_TYPE_YY = "3";   //用药
	public final static String COURSE_TYPE_BSZ = "4";   //并发症
	public final static String COURSE_TYPE_JC = "5";   //监测
	public final static String COURSE_TYPE_SHFS = "9";   //生活方式
	public final static String COURSE_TYPE_YDS = "7";   //胰岛素

	public final static String KNOWLEDGE_TYPE_YS = "LJZS02";  //饮食
	public final static String KNOWLEDGE_TYPE_YD = "LJZS03";   //运动
	public final static String KNOWLEDGE_TYPE_YY = "LJZS04";   //用药
	public final static String KNOWLEDGE_TYPE_BSZ = "LJZS05";   //并发症
	public final static String KNOWLEDGE_TYPE_JC = "LJZS07";   //监测
	public final static String KNOWLEDGE_TYPE_SHFS = "LJZS10";   //生活方式
	public final static String KNOWLEDGE_TYPE_YDS = "LJZS11";   //胰岛素

}
