package com.comvee.cdms.defender.common.cfg;

import java.util.HashMap;
import java.util.Map;

import com.comvee.cdms.common.cfg.Config;

public class InterveneConstant {
	public static final int  INTERVENE_TYPE_1 =  1;// 极低血糖
	public static final int  INTERVENE_TYPE_2 = 2;// 单次低血糖
	public static final int  INTERVENE_TYPE_3 = 3;// 连续低血糖
	public static final int  INTERVENE_TYPE_4 = 4;// 单次偏低血糖
	public static final int  INTERVENE_TYPE_5 = 5;// 连续偏低血糖
	public static final int  INTERVENE_TYPE_6 = 6;// 高危血糖干预
	public static final int  INTERVENE_TYPE_7 = 7;// 连续高血糖
	public static final int  INTERVENE_TYPE_8 = 8;// 单次高血糖
	public static final int  INTERVENE_TYPE_9 = 9;// 未监测干预
	public static final int  INTERVENE_TYPE_99 = 99;// 无任务状态 1空腹正常;2非空腹正常;3高血糖-非空腹10mmol/L≤血糖＜13.9mmol/L;4高血糖-空腹 7mmol/L≤＜血糖 ＜8.5mmol/L;5偏低 3.9 < n<=4.4;6低  n<=3.9
	public static final int  INTERVENE_TYPE_98 = 98;// 紧急就诊卡类型--血糖记录
	
	public static final int  INTERVENE_TYPE_101 = 101;// 高危血压
	public static final int  INTERVENE_TYPE_102 = 102;// 极高危血压
	
	public static String SUGAR_UN_INTERVENE_TYPE_1="1";//1空腹正常
	public static String SUGAR_UN_INTERVENE_TYPE_2="2";//2非空腹正常
	public static String SUGAR_UN_INTERVENE_TYPE_3="3";//3高血糖-非空腹10mmol/L≤血糖＜13.9mmol/L
	public static String SUGAR_UN_INTERVENE_TYPE_4="4";//4高血糖-空腹 7mmol/L≤＜血糖 ＜8.5mmol/L
	public static String SUGAR_UN_INTERVENE_TYPE_5="5";//5偏低 3.9 < n<=4.4
	public static String SUGAR_UN_INTERVENE_TYPE_6="6";//6低  2.8<n<=3.9
	public static String SUGAR_UN_INTERVENE_TYPE_7="7";//空腹8.5 <= n <10
	public static String SUGAR_UN_INTERVENE_TYPE_8="8";//非空腹 13.9 <=n < 16.7
	public static String SUGAR_UN_INTERVENE_TYPE_9="9";//空腹 n >=10
	public static String SUGAR_UN_INTERVENE_TYPE_10="10";//非空腹 n>=16.7
	public static String SUGAR_UN_INTERVENE_TYPE_11="11";//极低<=2.8
	
	public static final int QUES_TYPE_END=0;//题目终结者
	public static final int QUES_TYPE_SELECT=1;//单选题
	public static final int QUES_TYPE_KNOWLEDGE_CARD=2;//知识卡
	public static final int QUES_TYPE_COURSE=3;//课程推荐
	public static final int QUES_TYPE_TASK=4;//任务卡
	public static final int QUES_TYPE_PATIENT_CARD=5;//就诊卡
	public static final int QUES_TYPE_EMERGENT_CARD=6;//紧急就诊卡
	public static final int QUES_TYPE_JUMP_URL=7;//跳转url
//	public static final int QUES_TYPE_PATIENT_DIAL=8;//转人工服务(用户主动拨）
//	public static final int QUES_TYPE_COMPANY_DIAL=9;//转人工服务（丁香园后台触发）
	public static final int QUES_TYPE_DEFUALT=10;//文类型
	public static final int QUES_TYPE_MULTI_SELECT=11;//多选题
	public static final int QUES_TYPE_INPUT_NUMBER=12;//数值键盘填写
	public static final int QUES_TYPE_INPUT_TEXT=13;//文本内容填空
	public static final int QUES_TYPE_INPUT_MOBILE=14;//数值键盘手机号码填写
	//1､问题/选项、2知识卡、3､课程推荐，4､任务卡、5､就诊卡、6､紧急就诊卡、7转在线咨询、8转人工服务(用户主动拨）、9转人工服务（丁香园后台触发）10文本话术
	
	
	public static String  JUMP_URL_COURSE =Config.getValueByKey("healthdefender_url")+"/defender/html/classroom/detail-curse.jsp";//	课程页面		comvee
	
	public static final String TASK_TYPE_LOWEST_TASK5="lowest_task5";//极低--5分钟测一次血糖 	lowest_task5,v3.0变成15分钟
	public static final String TASK_TYPE_LOWEST_TASK3="lowest_task3";//极低--当天完成一次血糖测试	lowest_task3
	public static final String TASK_TYPE_SINGLE_LOW_TASK1="single_low_task1";//单次低--15分钟测一次血糖	single_low_task1
	public static final String TASK_TYPE_SINGLE_LOW_AGAIN_TASK1="single_low_again_task1";//单次低--15分钟测一次血糖(再次)	single_low_again_task1
	public static final String TASK_TYPE_SINGLE_LOW_TASK3="single_low_task3";//单次低--当天完成一次血糖测试	single_low_task3
	public static final String TASK_TYPE_ONCE_LOW_TASK2="once_low_task2";//单次偏低--测试一次两小时血糖	once_low_task2
	public static final String TASK_TYPE_ONCE_LOW_AGAIN_TASK2="once_low_again_task2";//单次偏低--测试一次两小时血糖	once_low_again_task2
	public static final String TASK_TYPE_HIGH_DANGER_TASK2="high_danger_task2";//高危	high_danger_task2
	public static final String TASK_TYPE_UNTEST_TASK3="untest_task3";//7天未监测--当天完成一次血糖监测	untest_task3
	public static final String TASK_TYPE_WEEK_MONITOR_TASK4="monitor_task4";//单次高-7天内测夜间/睡前血糖(task4) DCG6
	
	public static final String TASK_TYPE_DANGER_PRESSURE="danger_pressure";//高危高血压_5分钟测试一次血压
	public static final String TASK_TYPE_DANGER_PRESSURE_AGAIN="danger_pressure_again";//高危高血压_5分钟测试再次一次血压
	
	public static final String TASK_TYPE_VERY_DANGER_PRESSURE="very_danger_pressure";//极高危高血压_5分钟测试一次血压
	public static final String TASK_TYPE_VERY_DANGER_PRESSURE_AGAIN="very_danger_pressure_again";//极高危高血压_5分钟测试再次一次血压
	
	
	
	
	
//	public static final String TASK_TYPE_PILL_JD28 ="JD28";//请问您监测血糖时，下面哪个描述最符合您的情况？  1最近调整了药量
//	public static final String TASK_TYPE_PILL_DCD42 ="DCD42";//请问您监测血糖时，下面哪个描述最符合您的情况？  1最近调整了药量
//	public static final String TASK_TYPE_PILL_DCG8 ="DCG8";//以我对您的了解，您可能是以下的情况导致的，请选择最符合您的情况？  3:怀疑用药情况有调整
	
	
	
	
	

	
	
	
	public static final String   DOCUMENT_CODE_SEVEN_FIRST_DANGER ="seven_first_danger";//是否7天内首次高危血糖	seven_first_danger	"1 是 0 不是"
	
	/*	
		0无监测
		1  n <= 2.8
		2 2.8<n<=3.9
		3 3.9<n<=4.4
		4 4.4<n<16.7
		5 n>=16.7
	*/
	public static final String  DOCUMENT_CODE_SUGAR_GRADE = "sugar_grade";//血糖值范围	sugar_grade	
	public static final String  DOCUMENT_CODE_SUGAR_TIME_AM ="sugar_time_am"; //"判断血糖测量时间是否为8：00-12：00 sugar_time_am	"1 是0 不是"
	public static final String  DOCUMENT_CODE_CONTINUITY_THREE_LOW ="continuity_three_low"   ;//12小时内3次连续低血糖     <=3.9	continuity_three_low	"1 是0 不是"
	public static final String  DOCUMENT_CODE_CONTINUITY_THREE_UNDERPAID ="continuity_three_underpaid"  ;//12小时内3次连续偏低血糖   <=4.4	continuity_three_underpaid	"1 是0 不是"
//	public static final String  DOCUMENT_CODE_HAS_ONLINE_ASK ="has_online_ask"   ;//是否开通在线咨询	has_online_ask	"1 是0 不是"
	/**
		1 是≥5次,并且60%测血点超控制目标
		2 测量次数≥5次，但是低于60%的测血点超过控制目标
		3 测量次数＜5次
	 */
	public static final String  DOCUMENT_CODE_WEEK_MONITOR_STATE ="week_monitor_state"   ;//7天内血糖测试是否≥5次，并且60%测血点大于控制目标	week_monitor_state	
	public static final String  DOCUMENT_CODE_SUGAR_VALUE  ="sugar_value";//血糖值	sugar_value
	public static final String  DOCUMENT_CODE_SUGAR_TIME  ="sugar_time";//记录时间	sugar_time
	public static final String  DOCUMENT_CODE_SUGAR_LEVEL  ="sugar_level";//血糖风险等级	sugar_level
	public static final String  DOCUMENT_CODE_SUGAR_ID  ="sugar_id";//血糖id	sugar_id
	public static final String  DOCUMENT_CODE_SUGAR_MODEL  ="sugar_model";//血糖记录json对像	sugar_model
	public static final String  DOCUMENT_CODE_INSPECT_TYPE  ="inspect_type";//复诊干预型 1 复诊前三天 2 复诊前一天
	public static final String  DOCUMENT_CODE_UNTEST_WEEK  ="untest_week";//第几周没有测试血糖了 1周２周...
	public static final String  DOCUMENT_CODE_SUGAR_UN_INTERVENE  = "sugar_un_intervene";//血糖不触发干预流程	1 空腹正常 2 非空腹正常 3 高血糖-非空腹10mmol/L≤血糖＜13.9mmol/L
	
//	public static final String DOCUMENT_CODE_TREAMENT_HBA1C_STATUS ="treament_hba1c_status";//用户近三个月是否有糖化　0为无糖化数据 ,1有糖化数据且<=7,2有糖化数据且>7(不达标)
//	public static final String DOCUMENT_CODE_TREAMENT_HBA1C ="treament_hba1c";//糖化值
	
	public static final String DOCUMENT_CODE_INTERVENE_TYPE ="intervene_type";//干预类型
	/**
	 *  0: 无监测血压
		1: 90mmHg≤收缩压≤139mmHg，
		   或60mmHg≤舒张压≤89mmHg
		2: 140mmHg≤收缩压≤179mmHg，
		   或90mmHg≤舒张压≤109mmHg
		3: 收缩压≥180mmHg，
		   或舒张压≥110mmHg
		4: 收缩压＜90mmHg，
		   或舒张压＜60mmHg
	 */
	public static final String DOCUMENT_CODE_PRESSURE_GRADE = "pressure_grade";//血压判断
	
	public static final String DOCUMENT_CODE_PRESSURE_SBP= "pressure_sbp";//高压(收缩压)
	public static final String DOCUMENT_CODE_PRESSURE_DBP = "pressure_dbp";//低压(舒张压)
	public static final String DOCUMENT_CODE_PRESSURE_PULSE ="pressure_pulse";
	
	/** 
	 *  脉压差
	 *  0   ：否
		1 :＜20mmHg ，或＞60mmhg
	 */
	public static final String DOCUMENT_CODE_PRESSURE_DIFF ="pressure_diff";
	
	
	/**
	1:血糖正常-空腹
	2:血糖正常-非空腹
	3:低血糖
	4:高血糖-空腹
	5:高血糖-非空腹
	6:高危血糖
	 */
	public static final String  DOCUMENT_CODE_SUGAR_EMERGENT_INTERVENE  = "sugar_emergent_intervene";//紧急就诊卡血糖类型
	public static final String  DOCUMENT_CODE_INTERVENE_ID  = "intervene_id";// 紧急就诊卡干预id	
	public static final String  DOCUMENT_CODE_SUGAR_EMERGENT  = "sugar_emergent";//血糖--紧急就诊卡
	public static final String  DOCUMENT_CODE_PRESSURE_EMERGENT  = "pressure_emergent";//血压--紧急就诊卡
	
	

	
	
	public static String  JUMP_URL_PATIENT_CARD =Config.getValueByKey("healthdefender_url")+"/defender/html/intelligent/visitcardDetails.jsp";//	就诊卡页面		comvee
	public static String  JUMP_URL_EMERGENT_CARD =Config.getValueByKey("healthdefender_url")+"/defender/html/intelligent/visitcardDetails.jsp";//	紧急就诊卡页面		comvee
	public static String  JUMP_URL_INTERVENE =Config.getValueByKey("healthdefender_url")+"/defender/html/intelligent/intelligent.jsp";//	干预页面		comvee
	public static String  JUMP_URL_KNOWLEDGE =Config.getValueByKey("healthdefender_url")+"/defender/html/intelligent/knowledgeDetails.jsp";//	知识卡页面		comvee
	public static String  JUMP_URL_SUGAR_HIS =Config.getValueByKey("healthdefender_url")+"/chronic/pages/record-blood-sugar/sugar-history.html";//血糖记录历史
	public static String  JUMP_URL_PRESSURE_HIS =Config.getValueByKey("healthdefender_url")+"/chronic/pages/record-blood-pressure/pressure-history.html";//血压记录历史
//	public static String  JUMP_URL_QUESTIONNARE_SUGAR =Config.getValueByKey("healthdefender_url")+"/chronic/pages/patient-questionnare/patient-questionnare.html";//随访地址_血糖
//	public static String  JUMP_URL_QUESTIONNARE_PRESSURE =Config.getValueByKey("healthdefender_url")+"/chronic/pages/patient-questionnare/patient-questionnare.html";//随访地址_血压
//	public static String  JUMP_URL_SUGAR_MONITORING_LIST =Config.getValueByKey("healthdefender_url")+"/chronic/pages/record-blood-sugar/select-monitoring-plan.html";//血糖监测方案列表
	
	public static int INTERVENE_STATUS_ING = 1;//干预进行中
	public static int INTERVENE_STATUS_FINISH = 2;//干预完成
	public static int INTERVENE_STATUS_OVERDURE = 3;//干预过期
	
	public static String SYMBOL_KEY_HOSPITAL ="#hospital#";
	public static String SYMBOL_KEY_DOCTOR ="#doctor#";
	public static String SYMBOL_KEY_SUGAR ="#sugar#";
	public static String SYMBOL_KEY_MONITOR_TIME ="#monitor_time#";
	public static String SYMBOL_KEY_UN_MONITOR_DAY ="#un_monitor_day#";
	public static String SYMBOL_KEY_SBP ="#sbp#";//高压(收缩压)
	public static String SYMBOL_KEY_DBP ="#dbp#";//低压(舒张压)
//	public static String SYMBOL_KEY_TREAMENT_HBA1C ="#treament_hba1c#";
	
	
	
	public static final String TYPE_TASK_FIFTEN="task1";//15分钟录一次血糖
	public static final String TYPE_TASK_TWO_HOUR="task2";//两小时录一下血糖
	public static final String TYPE_TASK_ONE_DAY="task3";//当天录一下血糖
	public static final String TYPE_TASK_WEEK_YJSQ="task4";//7天内测夜间/睡前血糖(task4)
	public static final String TYPE_TASK_FIVE_SUGAR="task5";//15分钟后再测试一下血糖
	public static final String TYPE_TASK_EMERGENT="task6";//血糖--紧急就诊卡任务
	public static final String TYPE_TASK_PRESSURE="task_pressure";//5分钟后再测试一下血糖
	public static final String TYPE_TASK_PRESSURE_CARD="task_pressure_card";//血压紧急就诊卡
	public static final String TYPE_TASK_WAIT_5="task_wait_5";//5分钟后再测试一下血糖_模板消息
	
	
	
	public static final int TASK_STATUS_ING = 0;//任务状态:未完成
	public static final int TASK_STATUS_FINISH = 1;//任务状态:完成
	
//	public static String EMERGENT_TEL="4008219875";//主户主动拨打
//	public static String ONLINE_TEL="057126201089";//丁香园主动拨打
	
	
	public static Map<String,String> TASK_TYPE_MAP = new HashMap<String,String>();
	static{
		TASK_TYPE_MAP.put(TASK_TYPE_LOWEST_TASK5, TYPE_TASK_FIVE_SUGAR);
		TASK_TYPE_MAP.put(TASK_TYPE_LOWEST_TASK3, TYPE_TASK_ONE_DAY);
		TASK_TYPE_MAP.put(TASK_TYPE_SINGLE_LOW_TASK1, TYPE_TASK_FIFTEN);
		TASK_TYPE_MAP.put(TASK_TYPE_SINGLE_LOW_AGAIN_TASK1, TYPE_TASK_FIFTEN);
		TASK_TYPE_MAP.put(TASK_TYPE_SINGLE_LOW_TASK3, TYPE_TASK_ONE_DAY);
		TASK_TYPE_MAP.put(TASK_TYPE_ONCE_LOW_TASK2, TYPE_TASK_TWO_HOUR);
		TASK_TYPE_MAP.put(TASK_TYPE_ONCE_LOW_AGAIN_TASK2, TYPE_TASK_TWO_HOUR);
		TASK_TYPE_MAP.put(TASK_TYPE_HIGH_DANGER_TASK2, TYPE_TASK_TWO_HOUR);
		TASK_TYPE_MAP.put(TASK_TYPE_UNTEST_TASK3, TYPE_TASK_ONE_DAY);
		TASK_TYPE_MAP.put(TASK_TYPE_WEEK_MONITOR_TASK4, TYPE_TASK_WEEK_YJSQ);
		TASK_TYPE_MAP.put(TASK_TYPE_DANGER_PRESSURE, TYPE_TASK_PRESSURE);
		TASK_TYPE_MAP.put(TASK_TYPE_DANGER_PRESSURE_AGAIN, TYPE_TASK_PRESSURE);
		TASK_TYPE_MAP.put(TASK_TYPE_VERY_DANGER_PRESSURE, TYPE_TASK_PRESSURE);
		TASK_TYPE_MAP.put(TASK_TYPE_VERY_DANGER_PRESSURE_AGAIN, TYPE_TASK_PRESSURE);
	}
	
	public static final String QUES_SON_TYPE_TASK1="task1";//15分钟录一次血糖
	public static final String QUES_SON_TYPE_TASK2="task2";//两小时录一下血糖
	public static final String QUES_SON_TYPE_TASK3="task3";//当天录一下血糖
	public static final String QUES_SON_TYPE_TASK4="task4";//7天内测夜间/睡前血糖(task4)
	public static final String QUES_SON_TYPE_TASK5="task5";//5分钟后测试一次血糖
	public static final String QUES_SON_TYPE_PRESSURE="task_pressure";//5分钟后测试一次血压
	public static final String QUES_SON_TYPE_SUGARHIS="sugarhis";//血糖历史
	public static final String QUES_SON_TYPE_DEFAULT_JUMP="defaultjump";//默认跳转

}
