package com.comvee.cdms.defender.wechat.enums;

/**
 * 微信模板datatype
 * @author Administrator
 *
 */
public enum WechatEnum {
	ZNKHQD("301"),  //只能看护启动
	SUGARRESULTMINI("302"), //血糖结果通知--跳小程序
	SUGARREMIND("303"), //血糖提醒
	RETURNVISIT("304"),  //复诊提醒
	PRESSUREREMIND("305"), //血压提醒
	PRESSURERESULTMINI("306"),  //血压结果--跳小程序
	ADDCOURSE("307"),  //添加课程
	ORDERCOURSE("308"), //课程预约
	SUGARRESULT("309"), //血糖异常
	PRESSURERESULT("310"),  //血压异常
	KNOWLEDGE("311");  //课程学习计划

	private final String dataType;

	private WechatEnum(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}
}
