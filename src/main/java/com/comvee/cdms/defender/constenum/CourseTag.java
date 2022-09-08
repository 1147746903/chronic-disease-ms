package com.comvee.cdms.defender.constenum;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章标签
 * @author cjn
 *
 */
public enum CourseTag {

	 //肥胖
	 obesity("3-2"),
	 //重度肥胖
	 severeObesity("3-4"),
	 //中心肥胖
	 centralObesity("3-3"),
	 //有饮酒行为
	 drink("4-3"),
	 //男
	 male("2-1"),
	 //有吸烟行为
	 smoking("4-2"),
	 //青年
	 youngPeople("1-1"),
	 //女
	 female("2-2"),
	 //中年
	 middleAgedPerson("1-2"),
	 //超重
	 overweight("3-1"),
	 //老年
	 oldPeople("1-3"),
	 /**
		* 运动认识不足
	 */
	 inadequateUnderstandingOfSports("10-1"),
	 //低血糖
	 lowBloodSugar("5-1"),
	 //血糖波动
	 bloodSugarFluctuation("5-2"),
	 //无并发症
	 noComplications("6-1"),
	 //糖化血红蛋白（高危）
	 Hba1cHighRisk("9-1"),
	 //监测频率低
	 lowMonitoringFrequency("8-1"),
	 //不到一年
	 lessThanOneYear("7-1"),
	 //不吃晚餐
	notEatDinner("4-9");
	
	/****描述****/
	private static final Map<String,String> descMap = new HashMap<>();
	static{
		descMap.put(obesity.code(), "肥胖");
		descMap.put(severeObesity.code(), "重度肥胖");
		descMap.put(centralObesity.code(), "中心肥胖");
		descMap.put(drink.code(), "有饮酒行为");
		descMap.put(male.code(), "男");
		descMap.put(smoking.code(), "有吸烟行为");
		descMap.put(youngPeople.code(), "中年");
		descMap.put(female.code(), "女");
		descMap.put(middleAgedPerson.code(), "中年");
		descMap.put(overweight.code(), "超重");
		descMap.put(oldPeople.code(), "老年");
		descMap.put(inadequateUnderstandingOfSports.code(), "运动认识不足");
		descMap.put(lowBloodSugar.code(), "低血糖");
		descMap.put(bloodSugarFluctuation.code(), "血糖波动");
		descMap.put(noComplications.code(), "无并发症");
		descMap.put(Hba1cHighRisk.code(), "糖化血红蛋白（高危）");
		descMap.put(lowMonitoringFrequency.code(), "监测频率低");
		descMap.put(lessThanOneYear.code(), "不到一年");
		descMap.put(notEatDinner.code(), "不吃晚餐");
	}
	 
	 private String code;
	 
	 private CourseTag(String code) {
		 this.code = code;
	 }
	 
	 public String code() {
		 return this.code;
	 }

	
}
