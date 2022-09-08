package com.comvee.cdms.other.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.CodecTools;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.XmlUtils;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.other.service.EventMessageService;
import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.message.EventType;
import com.comvee.cdms.wechat.message.MsgType;
import com.comvee.cdms.wechat.message.output.OutputMessage;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import com.comvee.cdms.wechat.spi.MessageConvert;
import com.comvee.cdms.wechat.utils.SHA1;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author: suyz
 * @date: 2018/11/29
 */
@Controller
@RequestMapping("/wechat")
public class WechatCallbackController {

    private final static Logger log = LoggerFactory.getLogger(WechatCallbackController.class);

    private final static String TOKEN = "caa61e91d7ac47da956a0291021c30c1";

    @Autowired
    private EventMessageService eventMessageService;

    /**
     * @param echostr
     * @param timestamp
     * @param nonce
     * @param response
     * @throws IOException
     */
    @GetMapping("callback")
    public void callbackGet(String signature
            , String echostr, String timestamp, String nonce, HttpServletResponse response) throws IOException {
        String[] str = { TOKEN, timestamp, nonce };
        // 字典序排序
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        if(digest.equals(signature)){
            response.getWriter().print(echostr);
        }
    }

    /**
     *  微信公众号 回调事件
     * @param xmlBody
     * @throws DocumentException
     */
    @PostMapping("callback")
    public void callbackPost(@RequestBody(required = false) String xmlBody ,HttpServletResponse response) throws DocumentException, IOException {
        if(StringUtils.isBlank(xmlBody)){
            return;
        }
        log.info("[{}]微信公众号回调xml数据:{}" , Constant.WECHAT_PUBLIC_NAME_PATIENT ,xmlBody);
        JSONObject jsonObject = XmlUtils.documentToJSONObject(xmlBody);
        String msgType = jsonObject.getString("MsgType");
        if(MsgType.Event.toString().equals(msgType)){
            try{
                eventHandler(jsonObject);
            }catch (BusinessException ex){
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(getWechatTextReplayXml(jsonObject ,ex.getMessage()));
                return;
            }
        }
        response.getWriter().print("success");
    }

    /**
     * 获取微信文本返回xml报文
     * @param jsonObject
     * @param text
     * @return
     */
    private String getWechatTextReplayXml(JSONObject jsonObject ,String text){
        String template = "<xml>\n" +
                "  <ToUserName><![CDATA[{0}]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[{1}]]></FromUserName>\n" +
                "  <CreateTime>{2}</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[{3}]]></Content>\n" +
                "</xml>";
        return MessageFormat.format(template ,jsonObject.getString("FromUserName") ,jsonObject.getString("ToUserName") ,String.valueOf(System.currentTimeMillis()) ,text);
    }

    /**
     * 事件处理
     * @param document
     * @return
     */
    private OutputMessage eventHandler(JSONObject jsonObject){
        OutputMessage outputMessage = null;
        String event = jsonObject.getString("Event");
        if(EventType.Subscribe.toString().equals(event)){
            outputMessage = subscribeHandler(jsonObject);
        }else if(EventType.Unsubscribe.toString().equals(event)){

        }else if(EventType.SCAN.toString().equals(event)){
            outputMessage = this.eventMessageService.qrsceneScan(MessageConvert.getQrsceneScanEventMessage(jsonObject));
        }else if(EventType.CLICK.toString().equals(event)){
            outputMessage = this.eventMessageService.click(MessageConvert.getClickEventMessage(jsonObject));
        }
        return outputMessage;
    }


    /**
     * 关注处理
     * @param document
     * @return
     */
    private OutputMessage subscribeHandler(JSONObject jsonObject){
        String eventKey = jsonObject.getString("EventKey");
        if(StringUtils.isBlank(eventKey)){
            return this.eventMessageService.subscribe(MessageConvert.getSubscribeEventMessage(jsonObject));
        }else{
            return this.eventMessageService.qrsceneSubscribe(MessageConvert.getQrsceneSubscribeEventMessage(jsonObject));
        }
    }

    /**
     * 获取微信accessToken
     * @return
     */
    @RequestMapping("/getAccessToken")
    @ResponseBody
    public Result getAccessToken(){
        String accessToken = WechatTokenApi.getAccessToken(WechatAppName.GONG_ZHONG_HAO);
        return Result.ok(CodecTools.encriptDES3(accessToken));
    }

    /**
     * 获取appId
     * @return
     */
    @RequestMapping("/getAppId")
    @ResponseBody
    public Result getAppId(){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.GONG_ZHONG_HAO);
        return Result.ok(wechatAppConfig.getAppId());
    }

}
