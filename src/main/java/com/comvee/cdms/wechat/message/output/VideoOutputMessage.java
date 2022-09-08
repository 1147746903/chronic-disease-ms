package com.comvee.cdms.wechat.message.output;


import com.comvee.cdms.wechat.message.Video;

public class VideoOutputMessage extends OutputMessage
{
    private final String msgType = "video";
    private Video video;

    @Override
    public String getMsgType() {
        return msgType;
    }
    
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}


	@Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>");
        sb.append("<MsgType><![CDATA[" + this.msgType + "]]></MsgType>");
        sb.append("<Video>");
        sb.append("<MediaId><![CDATA[").append(this.getVideo().getMediaId()).append("]]></MediaId>");
        sb.append("<Title><![CDATA[").append(this.getVideo().getTitle()).append("]]></Title>");
        sb.append("<Description><![CDATA[").append(this.getVideo().getDescription()).append("]]></Description>");
        sb.append("</Video>");
        sb.append("</xml>");
        return sb.toString();
    }
}
