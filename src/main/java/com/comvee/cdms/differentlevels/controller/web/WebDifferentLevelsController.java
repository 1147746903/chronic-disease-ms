package com.comvee.cdms.differentlevels.controller.web;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsDTO;
import com.comvee.cdms.differentlevels.dto.ListDiffLevelsOfMemberDTO;
import com.comvee.cdms.differentlevels.dto.MemberDiffLevelSureDTO;
import com.comvee.cdms.differentlevels.service.DifferentLevelsService;
import com.comvee.cdms.differentlevels.vo.DiffLevelsForMemberVO;
import com.comvee.cdms.differentlevels.vo.DifferentLevelsForWorkVO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 林雨堆
 * https://www.json.cn/
 *
 */
@RestController
@RequestMapping("/web/differentLevels")
//@RequiresUser
public class WebDifferentLevelsController {

    private final static Logger logger = LoggerFactory.getLogger(WebDifferentLevelsController.class);

    @Autowired
    private DifferentLevelsService differentLevelsService;

    @Autowired
    private MemberService memberService;

    /**
     * 分页获取工作台分层分级列表
     * http://192.168.7.120:9080/intelligent-prescription/web/differentLevels/pagerDiffLevels.do
     * @return
     */
    @RequestMapping("pagerDiffLevels")
    public Result pagerDiffLevels(ListDiffLevelsDTO dto, PageRequest pager){
        if(StringUtils.isBlank(dto.getOriginId())){
            dto.setOriginId(SessionTool.getWebSession().getHospitalId());
        }
        PageResult<DifferentLevelsForWorkVO> vos = this.differentLevelsService.pagerDiffLevels(dto,pager);
        return new Result(vos);
    }

    /**
     * 获取患者的历史分级结果
     * http://192.168.7.120:9080/intelligent-prescription/web/differentLevels/getDiffLevelsForMember.do?memberId=190629144040100001
     * @param dto
     * @return
     */
    @RequestMapping("getDiffLevelsForMember")
    public Result getDiffLevelsForMember(@Validated ListDiffLevelsOfMemberDTO dto){
        if(StringUtils.isBlank(dto.getOriginId())){
            dto.setOriginId(SessionTool.getWebSession().getHospitalId());
        }
        DiffLevelsForMemberVO vo = this.differentLevelsService.getDiffLevelsForMember(dto);
        return new Result(vo);
    }

    /**
     * 分级确认
     * @param dto
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/web/differentLevels/sureUnReadDifferentLevels.do
     */
    @RequestMapping("sureUnReadDifferentLevels")
    public Result sureUnReadDifferentLevels(@Validated MemberDiffLevelSureDTO dto){
        this.differentLevelsService.sureUnReadDifferentLevels(dto);
        return Result.ok();
    }

    /**
     * 分层分级测试
     * http://192.168.7.120:9080/intelligent-prescription/web/differentLevels/memberDifferentLevelsHandle.do
     * @return
     */
    @RequestMapping("memberDifferentLevelsHandle")
    public Result memberDifferentLevelsHandle(){
        logger.info("患者分层分级处理准备（"+ DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT)+"）,检索分层分级的患者档案...");
        long nowTimes = System.currentTimeMillis();
        int page = 1;
        PageResult<Map<String,Object>> pageResult = this.memberService.listAllMemberNearlyYearArchives(page,100);
        long totalRow = pageResult.getTotalRows();
        long totalPage = pageResult.getTotalPages();
        logger.info("患者分层分级处理开始（"+ DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT)+"）,共有"+totalRow+"条记录需要处理...");
        int handleCount = 0;
        do{
            List<Map<String,Object>> rows = pageResult.getRows();
            if(rows!=null && rows.size()>0){
                handleCount+= rows.size();
                this.differentLevelsService.differentLevelsHandle(rows);
                logger.info("患者分层分级处理中（"+ DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT)+"）,已经处理"+handleCount+"条记录...");
            }
            if(page<totalPage){
                pageResult = this.memberService.listAllMemberNearlyYearArchives(++page,100);
            }
        }while (page<totalPage);
        logger.info("患者分层分级处理结束（"+ DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT)+"）,耗时："+(System.currentTimeMillis()-nowTimes)+"ms");
        return Result.ok();
    }
}
