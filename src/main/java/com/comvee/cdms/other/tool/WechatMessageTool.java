package com.comvee.cdms.other.tool;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.MessageTool;
import com.comvee.cdms.wechat.config.WechatAppName;
import com.comvee.cdms.wechat.config.WechatConfig;
import com.comvee.cdms.wechat.message.TemplateData;
import com.comvee.cdms.wechat.model.WechatAppConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author: suyz
 * @date: 2019/4/25
 */
public class WechatMessageTool {

    /**
     * 获取默认小程序对象
     * @return
     */
    public static TemplateData.MiniProgramData defaultMiniProgramData(){
        WechatAppConfig wechatAppConfig = WechatConfig.getWechatAppConfig(WechatAppName.MINI_PROGRAM);
        TemplateData.MiniProgramData miniProgramData = new TemplateData().new MiniProgramData();
        miniProgramData.setAppid(wechatAppConfig.getAppId());
        return miniProgramData;
    }

    /**
     * 小程序跳转页
     */
    public final static String MINI_PROGRAM_JUMP_PATH = "pages/jump/jump?url={0}&type={1}";
    /**
     * 跳转类型 1 tab 2 页面
     */
    public final static int JUMP_TYPE_TAB = 1;
    public final static int JUMP_TYPE_PAGE = 2;

    /**
     * 小程序路径处理
     * @param path
     * @param type
     * @return
     */
    public static String miniProgramPathHandler(String path, int type){
        String url = null;
        try {
            url = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException("小程序url编码失败", e);
        }
        return MessageTool.format(WechatMessageTool.MINI_PROGRAM_JUMP_PATH, url, type);
    }

}
