package com.comvee.cdms.wechat.message.output;


import com.comvee.cdms.wechat.message.Voice;

public class VoiceOutputMessage extends OutputMessage {

    private final String msgType = "voice";
    private Voice voice;    //通过上传多媒体文件，得到的id
    
    @Override
    public String getMsgType() {
        return msgType;
    }

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	
	@Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>");
        sb.append("<MsgType><![CDATA[" + this.msgType + "]]></MsgType>");
        sb.append("<Voice>");
        sb.append("<MediaId><![CDATA[").append(this.getVoice().getMediaId()).append("]]></MediaId>");
        sb.append("</Voice>");
        sb.append("</xml>");
        return sb.toString();
    }
}