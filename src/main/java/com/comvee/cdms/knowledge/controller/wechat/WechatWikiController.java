package com.comvee.cdms.knowledge.controller.wechat;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.knowledge.po.WikiPO;
import com.comvee.cdms.knowledge.service.WikiService;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2018/11/2
 */
@RestController
@RequestMapping("/wechat/wiki")
@RequiresUser
public class WechatWikiController {

    @Autowired
    private WikiService wikiService;

    /**
     * 加载文章列表
     * @param pr
     * @param pid
     * @return
     */
    @RequestMapping("listWiki")
    public Result listWiki(PageRequest pr ,String pid){
        ValidateTool.checkParamIsNull(pid, "pid");
        PageResult<WikiPO> result = this.wikiService.listWiki(pr, pid);
        return new Result(result);
    }

    /**
     * 新增点击数
     * @param sid
     * @return
     */
    @RequestMapping("updateWikiClick")
    public Result updateWikiClick(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        MemberPO memberPO = SessionTool.getWechatSession();
        this.wikiService.updateWikiClick(sid ,memberPO.getMemberId());
        return new Result("");
    }
}
