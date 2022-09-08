package com.comvee.cdms.defender.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.wechat.model.ClientBehaviorModel;
import com.comvee.cdms.defender.wechat.model.ClientEventModel;
import com.comvee.cdms.defender.wechat.service.ClientBehaviorServiceI;
import com.comvee.cdms.defender.wechat.service.ClientEventServiceI;

@RestController
@RequestMapping("/wechat/behavior")
public class WechatBehaviorController {
	
	@Autowired
	@Qualifier("clientBehaviorService")
	private ClientBehaviorServiceI clientBehaviorService;
	@Autowired
	@Qualifier("clientEventService")
	private ClientEventServiceI clientEventService;
	

	/** 
     * @api {post} /wechat/behavior/addClientBehavior 001.添加页面访问日志表记录
     * @apiName addClientBehavior
     * @apiGroup behavior 
     * @apiVersion 1.0.0 
     * @apiDescription 添加页面访问日志表记录
     * @apiParam  {String{256}}  cookieId cookie_id
     * 
     * @apiParam  {String{256}}  url 访问的页面url(不带参数)
     * 
     * @apiParam  {String{2,048}}  arg 页面参数
     * 
     * @apiParam  {String{0}}  visitTm 开始访问时间
     * 
     * @apiParam  {String{255}}  pageId 页面编号
     * 
     * @apiParam  {String{255}}  simuid 患者微信simuid
     * 
     * @apiSuccess {String} code 结果码 
     * @apiSuccess {String} msg 消息说明 
     * @apiSuccessExample Success-Response: 
     *  HTTP/1.1 200 OK 
     * {
     * "code":1, 
     * "msg":"添加成功"
     * }
     *
     */
     
	@RequestMapping("/addClientBehavior")
	public ResultModel addClientBehavior(ClientBehaviorModel clientBehaviorModel,HttpServletRequest request)  {
		String userAgent = request.getHeader("user-agent");
//		System.out.println("userAgent:"+userAgent);
//		String referer = request.getHeader("referer");
//		System.out.println("referer:"+referer);
//		clientBehaviorModel.setReferer(referer);
		clientBehaviorModel.setAgent(userAgent);
		this.clientBehaviorService.addClientBehavior(clientBehaviorModel);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
	
	/** 
     * @api {post} /wechat/behavior/addClientBehaviorTime 002.添加页面访问日志表记录
     * @apiName addClientBehaviorTime
     * @apiGroup behavior 
     * @apiVersion 1.0.0 
     * @apiDescription 接口详细描述 
     * 
     * @apiParam  {String{256}}  cookieId cookie_id
     * 
     * @apiParam  {String{256}}  url 访问的页面url(不带参数)
     * 
     * @apiParam  {String{2,048}}  arg 页面参数
     * 
     * @apiParam  {String{0}}  visitTm 开始访问时间
     * 
     * @apiParam  {int{10}}  time 页面停驻时间（单位毫秒）
     * 
     * @apiParam  {String{255}}  pageId 页面编号
     * 
     * @apiParam  {String{255}}  simuid 患者微信simuid
     * 
     * @apiSuccess {String} code 结果码 
     * @apiSuccess {String} msg 消息说明 
     * @apiSuccessExample Success-Response: 
     *  HTTP/1.1 200 OK 
     * {
     * "code":1, 
     * "msg":"添加成功"
     * }
     *
     */
     
	@RequestMapping("/addClientBehaviorTime")
	public ResultModel addClientBehaviorTime(ClientBehaviorModel clientBehaviorModel,HttpServletRequest request)  {
		String userAgent = request.getHeader("user-agent");
//		System.out.println("userAgent:"+userAgent);
//		String referer = request.getHeader("referer");
//		System.out.println("referer:"+referer);
//		clientBehaviorModel.setReferer(referer);
		clientBehaviorModel.setAgent(userAgent);
		this.clientBehaviorService.addClientBehaviorTime(clientBehaviorModel);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
	/** 
     * @api {post} /wechat/behavior/addClientEvent 003.添加用户行为日志埋点记录
     * @apiName addClientEvent
     * @apiGroup behavior 
     * @apiVersion 1.0.0 
     * @apiDescription 添加用户行为日志埋点记录
     * @apiParam  {String{512}}  url 所在的页面url(不带参)
     * @apiParam  {String{0}}  tm 事件触发时间
     * @apiParam  {String{64}}  name 事件名字编码
     * @apiParam  {String{128}}  simuid 患者微信simuid
     * @apiParam  {String{255}}  param 事件处理过程所附带的参数
     * @apiSuccess {String} code 结果码 
     * @apiSuccess {String} msg 消息说明 
     * @apiSuccessExample Success-Response: 
     *  HTTP/1.1 200 OK 
     * {
     * "code":1, 
     * "msg":"添加成功"
     * }
     *
     */
	@RequestMapping("/addClientEvent")
	public ResultModel addClientEvent(ClientEventModel clientEventModel)  {
		this.clientEventService.addClientEvent(clientEventModel);
		ResultModel result = new ResultModel(true);
        return result;
	}
	
}
