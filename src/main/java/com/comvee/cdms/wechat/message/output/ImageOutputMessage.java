package com.comvee.cdms.wechat.message.output;


import com.comvee.cdms.wechat.message.Image;

/**
 * 这个类实现了<tt>OutputMessage</tt>，用来回复图片消息
 *
 * <p>
 * 提供了获取图片Id<code>getMediaId()</code>等主要方法.</p>
 */
public class ImageOutputMessage extends OutputMessage {
    /**
     * 消息类型:图片消息
     */
    private final String msgType = "image";
    
    //通过上传多媒体文件，得到的id
    private Image image;
    
    @Override
    public String getMsgType() {
        return msgType;
    }
    
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>");
        sb.append("<MsgType><![CDATA[" + this.msgType + "]]></MsgType>");
        sb.append("<Image>");
        sb.append("<MediaId><![CDATA[").append(this.getImage().getMediaId()).append("]]></MediaId>");
        sb.append("</Image>");
        sb.append("</xml>");
        return sb.toString();
    }
}
