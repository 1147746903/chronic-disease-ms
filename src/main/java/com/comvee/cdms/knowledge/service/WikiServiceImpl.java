package com.comvee.cdms.knowledge.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.knowledge.dto.IncrMemberArticleClicksDTO;
import com.comvee.cdms.knowledge.mapper.WikiMapper;
import com.comvee.cdms.knowledge.po.WikiPO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/3
 */
@Service("wikiService")
public class WikiServiceImpl implements WikiService{

    @Autowired
    private WikiMapper wikiMapper;

    @Autowired
    private KnowledgeServiceI knowledgeService;

    @Override
    public PageResult<WikiPO> listWiki(PageRequest pr, String pid) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<WikiPO> list = this.wikiMapper.listWiki(pid);
        return new PageResult<>(list);
    }

    @Override
    public void updateWikiClick(String sid,String memberId) {
        this.wikiMapper.updateWikiClick(sid);

        //点击埋点
        IncrMemberArticleClicksDTO incrMemberArticleClicksDTO = new IncrMemberArticleClicksDTO();
        incrMemberArticleClicksDTO.setMemberId(memberId);
        incrMemberArticleClicksDTO.setWikiClicks(1);
        this.knowledgeService.incrMemberArticleClick(incrMemberArticleClicksDTO);
    }
}
