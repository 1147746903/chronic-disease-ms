/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.comvee.cdms.wechat.message.output;

/**
 * 微信发送被动响应消息的抽象类
 *
 * <p>
 * 应用程序需要定义一个子类，来实现具体方法</p>
 */

public abstract class OutputMessage implements java.io.Serializable {
	
    //接收方帐号（收到的OpenID）
    private String toUserName;
    //开发者微信号
    private String fromUserName;
    
   	//消息创建时间 （整型）
    private Long createTime;
    

    /**
     * 获取 消息类型
     *
     * @return 消息类型
     */
    public abstract String getMsgType();
    

    /**
     * 将对象转换为xml字符串
     *
     * @return 对象xml字符串
     */
    public abstract String toXML();


	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}


	public String getFromUserName() {
		return fromUserName;
	}


	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}


	public Long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
  

}
