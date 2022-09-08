package com.comvee.cdms.wechat.message;

import java.util.HashMap;

/**
 * @File name:   WechatTemplateData.java   TODO微信小程序消息模版对象
 * @Create on:   2018年2月3日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public class WechatTemplateData {

	private String touser;  //接收者（用户）的 openid
	private String template_id;	  //所需下发的模板消息的id
	private String page;         //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
	private String form_id;      //表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	private TemplateDataItem data = new TemplateDataItem();
	private String emphasis_keyword;//模板需要放大的关键词，不填则默认无放大
	private String color;           //模板内容字体的颜色，不填默认黑色
	

    public WechatTemplateData(String touser, String template_id,String page,String emphasis_keyword ) {
        this.touser = touser;
        this.template_id = template_id;
        this.page = page;
        this.emphasis_keyword = emphasis_keyword;
    }
    
    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }  
	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getForm_id() {
		return form_id;
	}
	
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	
	public TemplateDataItem getData() {
		return data;
	}
	
	public void setData(TemplateDataItem data) {
		this.data = data;
	}
	
	public String getEmphasis_keyword() {
		return emphasis_keyword;
	}
	
	public void setEmphasis_keyword(String emphasis_keyword) {
		this.emphasis_keyword = emphasis_keyword;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}


    public WechatTemplateData push(String key, String value, String color) {
        this.data.addItem(key, value, color);
        return this;
    }

    public WechatTemplateData push(String key, String value) {
        this.data.addItem(key, value);
        return this;
    }


	public class TemplateDataItem extends HashMap<String, Item> {
        private static final long serialVersionUID = 1L;

        public Item getItemInstance(String value) {
            return new Item(value);
        }

        public Item getItemInstance(String value, String color) {
            return new Item(value, color);
        }

        public TemplateDataItem() {
        }

        public TemplateDataItem addItem(String key, String value) {
            this.put(key, new Item(value));
            return this;
        }

        public TemplateDataItem addItem(String key, String value, String color) {
            this.put(key, new Item(value, color));
            return this;
        }
    }

    public class Item {
        private String value;
        private String color = "#000000";

        public Item(String value) {
            this.value = value;
        }

        public Item(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }
    }
}
