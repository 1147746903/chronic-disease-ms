package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.model.ImmediatelyTemplateModel;
import com.comvee.cdms.defender.wechat.model.TimingTempalteMsgModel;

/**
 * @File name:   TemplateMsgMapper.java   TODO
 * @Create on:   2018年8月31日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public interface TemplateMsgMapper {

	/**
	 * 添加群发内容
	 * @TODO
	 * @param tempalteMsgModel
	 * @author zqx
	 * @date 2018年8月31日
	 */
	void insertTimingMsg(TimingTempalteMsgModel tempalteMsgModel);

	/**
	 * 添加实时消息模版
	 * @TODO
	 * @param immediatelyTemplateModel
	 * @author zqx
	 * @date 2018年8月31日
	 */
	void insertImmediatelyMsg(ImmediatelyTemplateModel immediatelyTemplateModel);
}
