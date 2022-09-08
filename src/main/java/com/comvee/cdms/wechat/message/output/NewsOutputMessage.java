
package com.comvee.cdms.wechat.message.output;


import com.comvee.cdms.wechat.message.Articles;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类实现了<tt>OutputMessage</tt>，用来回复图文消息
 *
 * <p>
 * 提供了获取多条图文消息信息<code>getArticles()</code>等主要方法.</p>
 */

public class NewsOutputMessage extends OutputMessage {
	
    private final String msgType = "news";
    
    //图文消息个数，限制为10条以内
    private Integer articleCount;
    private List<Articles> articles;
    
    @Override
    public String getMsgType() {
        return msgType;
    }

    /**
     * 获取 图文消息个数
     *
     * @return 图文消息个数
     */
    public Integer getArticleCount() {
        return articleCount;
    }

    /**
     * 获取 多条图文消息信息
     *
     * @return 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则只读取前10个
     */
    public List<Articles> getArticles() {
        return articles;
    }

    /**
     * 设置 多条图文消息信息
     *
     * @param articles 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则只读取前10个
     */
    public void setArticles(List<Articles> articles) {
        if (articles != null) {
            if (articles.size() > 10) {
                articles = new ArrayList<Articles>(articles.subList(0, 10));
            }
            articleCount = articles.size();
        }
        this.articles = articles;
    }

    @Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>");
        sb.append("<MsgType><![CDATA[" + this.msgType + "]]></MsgType>");
        sb.append("<ArticleCount>").append(this.articleCount).append("</ArticleCount>");
        sb.append("<Articles>");
        
        for (Articles articleItem : articles) {
            sb.append("<item>");
            sb.append("<Title><![CDATA[").append(articleItem.getTitle()).append("]]></Title>");
            sb.append("<Description><![CDATA[").append(articleItem.getDescription()).append("]]></Description>");
            sb.append("<PicUrl><![CDATA[").append(articleItem.getPicurl()).append("]]></PicUrl>");
            sb.append("<Url><![CDATA[").append(articleItem.getUrl()).append("]]></Url>");
            sb.append("</item>");
        }
        sb.append("</Articles>");
        sb.append("</xml>");
        return sb.toString();
    }
}

