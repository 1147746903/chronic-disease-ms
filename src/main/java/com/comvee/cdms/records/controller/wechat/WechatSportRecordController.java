package com.comvee.cdms.records.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.records.constant.SportRecordConstant;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import com.comvee.cdms.records.service.SportRecordService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/wechat/sportRecord")
public class WechatSportRecordController {
    @Autowired
    private SportRecordService sportRecordService;

    /**
     * 关键字查询运动库
     *
     * @param sportRecordDTO
     * @return
     */
    @RequestMapping("listSportByName")
    Result listSportByName(SportRecordDTO sportRecordDTO) {
        ValidateTool.checkParamIsNull(sportRecordDTO.getSportName(), "sportName");
        return Result.ok(sportRecordService.listSport(sportRecordDTO));
    }

    /**
     * 保存运动记录
     *
     * @param sportRecordPO
     * @return
     */
    @RequestMapping("saveSportRecordForMini")
    Result saveSportRecord(SportRecordPO sportRecordPO) {
        checkSport(sportRecordPO);
        miniSportRecordHandler(sportRecordPO);
        sportRecordService.saveSportRecord(sportRecordPO);
        return Result.ok();
    }


    /**
     * 小程序保存多条运动记录
     * @param sportsJson
     * @return
     */
    @RequestMapping("saveSportRecordsForMini")
    Result saveSportRecord(String sportsJson) {
        List<SportRecordPO> sports = JSON.parseArray(sportsJson, SportRecordPO.class);
        MemberPO memberPO = SessionTool.getWechatSession();
        String mainId = DaoHelper.getSeq();
        for(SportRecordPO sport: sports){
            checkSport(sport);
            sport.setMemberId(memberPO.getMemberId());
            sport.setOperationId(memberPO.getMemberId());
            if(StringUtils.isBlank(sport.getRecordMainId())){
                sport.setRecordMainId(mainId);
            }
            miniSportRecordHandler(sport);
        }
        sportRecordService.saveSportRecords(sports);
        return Result.ok();
    }

    /**
     * 小程序来源运动记录初始化
     * @param sportRecordPO
     */
    private void miniSportRecordHandler(SportRecordPO sportRecordPO){
        sportRecordPO.setOperationId(sportRecordPO.getMemberId());
        sportRecordPO.setOperationType(SportRecordConstant.OPERATION_TYPE_MEMBER);
        sportRecordPO.setOrigin(SportRecordConstant.ORIGIN_MINI_PROGRAM);
        sportRecordPO.setOriginId("");
    }

    /**
     *检查运动记录必填项
     * @param sportRecordPO
     */
    private void checkSport(SportRecordPO sportRecordPO) {
        ValidateTool.checkParamIsNull(sportRecordPO.getRecordTime(), "recordTime");
        ValidateTool.checkParamIsNull(sportRecordPO.getSportState(), "sportState");
        ValidateTool.checkParamIsNull(sportRecordPO.getIntensity(), "intensity");
        ValidateTool.checkParamIsNull(sportRecordPO.getSportName(), "sportName");
        ValidateTool.checkParamIsNull(sportRecordPO.getTotal(), "total");
    }

    @RequestMapping("deleteSportRecordById")
    public Result deleteSportRecordById(String sid){
        sportRecordService.deleteSportRecordById(sid);
        return Result.ok();
    }
    /**
     * 获取最近6条记录
     *
     * @param sportRecordDTO
     * @return
     */
    @RequestMapping("listRecentSportRecord")
    public Result listRecentSportRecord(SportRecordDTO sportRecordDTO) {
        MemberPO memberPO = SessionTool.getWechatSession();
        sportRecordDTO.setMemberId(memberPO.getMemberId());
        return Result.ok(sportRecordService.listRecentSportRecord(sportRecordDTO));
    }


    /**
     * 获取运动记录
     *
     * @param sportRecordDTO
     * @return
     */
    @RequestMapping("listSportRecord")
    public Result listSportRecord(SportRecordDTO sportRecordDTO) {
        MemberPO memberPO = SessionTool.getWechatSession();
        sportRecordDTO.setMemberId(memberPO.getMemberId());
        return Result.ok(sportRecordService.listSportRecord(sportRecordDTO));
    }


    /**
     * 判断是否录入体重信息
     * @return
     */
    @RequestMapping("hasRecordWeight")
    public Result hasRecordWeight(){
        MemberPO memberPO = SessionTool.getWechatSession();
        boolean flag = !StringUtils.isBlank(memberPO.getWeight());
        return Result.ok(flag);
    }
}
