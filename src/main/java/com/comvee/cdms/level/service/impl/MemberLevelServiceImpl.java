package com.comvee.cdms.level.service.impl;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.dto.AddMemberHealthWarningNoticeDTO;
import com.comvee.cdms.doctor.service.DoctorNoticeService;
import com.comvee.cdms.follow.helper.FollowFirstGxyHelper;
import com.comvee.cdms.level.bo.MemberCurrentLeverBO;
import com.comvee.cdms.level.helper.LevelAnalyzeHelper;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.*;
import com.comvee.cdms.level.mapper.MemberLevelMapper;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.level.vo.MemberLevelVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wyc
 * @date 2019/11/19 20:58
 */
@Service("memberLevelService")
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DoctorNoticeService doctorNoticeService;

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public String addMemberLevel(MemberLevelPO levelPO){
        MemberPO member = memberService.getMemberById(levelPO.getMemberId());
        if(member == null){
            throw new BusinessException("患者不存在，请确认");
        }
        String sid = DaoHelper.getSeq();
        levelPO.setSid(sid);
        levelPO.setAge(DateHelper.getAge(member.getBirthday()));
        levelPO.setMemberName(member.getMemberName());
        levelPO.setSex(member.getSex());
        levelPO.setMemberNamePy(member.getMemberNamePy());
        if (levelPO.getConfirm() == MemberLevelConstant.YES){
            levelPO.setConfirmDt(DateHelper.getTime());
        }
        this.memberLevelMapper.addMemberLevel(levelPO);
        return sid;
    }

    /**
     * 忽略患者未确认的提醒
     * @param unSid
     * @param memberId
     * @param hospitalId
     * @param levelType 1 高血压
     */
    private void clearMemberLastUnConfirm(String unSid,String memberId,String hospitalId,String changeDate,Integer confirm,Integer levelType) {
        ListMemberLevelDTO listMemberLevelDTO = new ListMemberLevelDTO();
        listMemberLevelDTO.setMemberId(memberId);
        listMemberLevelDTO.setLevelType(levelType);
        listMemberLevelDTO.setConfirm(confirm);
        listMemberLevelDTO.setChangeDate(changeDate);
        List<MemberLevelPO> list = this.memberLevelMapper.listMemberLevel(listMemberLevelDTO);
        if(list == null || list.isEmpty()){
            return;
        }
        List<String> idList = list.stream().filter(x ->{
            if(!StringUtils.isBlank(unSid)){
                return !x.getSid().equals(unSid);
            }
            return true;
        }).map(MemberLevelPO::getSid).collect(Collectors.toList());
        if(idList == null || idList.isEmpty()){
            return;
        }
        this.memberLevelMapper.clearMemberLastUnConfirm(idList);
    }

    /**
     * 查询上次的分层分级
     * @param memberId
     * @param hospitalId
     * @param changeDate
     * @param flag
     * @param confirm
     * @return
     */
    private MemberLevelPO getLastLevel(String memberId,String hospitalId,String changeDate,Integer confirm,Integer levelType){
        LastLevelDTO dto = new LastLevelDTO();
        dto.setMemberId(memberId);
        dto.setHospitalId(hospitalId);
        dto.setConfirm(confirm);
        dto.setLevelType(levelType);
        dto.setChangeDate(changeDate);
        MemberLevelPO level = this.memberLevelMapper.getLastLevel(dto);
        return level;
    }

    @Override
    public List<MemberLevelPO> listMemberLevel(ListMemberLevelDTO levelDTO) {
        doMemberLevelDTO(levelDTO);
        List<MemberLevelPO> pos = this.memberLevelMapper.listMemberLevel(levelDTO);
        return pos;
    }

    @Override
    public PageResult<MemberLevelVO> pagerMemberLevel(ListMemberLevelDTO levelDTO, PageRequest page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<MemberLevelVO> memberLevelVOS = memberLevelMapper.listMemberNewstLevelVO(levelDTO);
        memberLevelVOS.forEach(e->{
            if (!StringUtils.isBlank(e.getMobilePhone()) && e.getMobilePhone().length() >= 8){
                StringBuilder stringBuilder = new StringBuilder(e.getMobilePhone());
                e.setMobilePhone(stringBuilder.replace(3,7,"****").toString());
            }
        });
        return new PageResult<>(memberLevelVOS);
    }

    @Override
    public Map<String, Object> loadMemberLevelStatics(List<String> hospitalIdList) {
        Map<String, Object> map = statisticsService.loadHospitalDiseaseDataForScreen(hospitalIdList, null);
        return map;
    }

    @Override
    public Map<String, Object> listMemberLevelHistory(String memberId, Integer levelType,String hospitalId,PageRequest page) {
        MemberPO member = memberService.getMemberById(memberId);
        if (!StringUtils.isBlank(member.getMobilePhone()) && member.getMobilePhone().length() >= 8){
            StringBuilder stringBuilder = new StringBuilder(member.getMobilePhone());
            member.setMobilePhone(stringBuilder.replace(3,7,"****").toString());
        }
        PageHelper.startPage(page.getPage(), page.getRows());
        ListMemberLevelDTO param = new ListMemberLevelDTO();
        param.setMemberId(memberId);
        param.setLevelType(levelType);
        //param.setHospitalId(hospitalId);
        List<MemberLevelVO> history = memberLevelMapper.listMemberLevelVO(param);
        Map<String, Object> map = new HashMap<>();
        map.put("member",member);
        map.put("history",new PageResult<>(history));
        return map;
    }

    @Override
    public PageResult<MemberLevelVO> listMemberLevelRemind(ListMemberLevelDTO levelDTO, PageRequest page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<MemberLevelPO> poList = this.memberLevelMapper.listMemberLevelRemind(levelDTO);
        PageResult<MemberLevelPO> result = new PageResult<MemberLevelPO>(poList);
        List<MemberLevelVO> vos = new ArrayList<>();
        List<MemberLevelPO> rows = result.getRows();
        if (null != rows && rows.size() > 0){
            for (MemberLevelPO levelPO : result.getRows()) {
                //查询上次已确认的分层分级提醒
                MemberLevelPO lastLevel = getLastLevel(levelPO.getMemberId(), levelPO.getHospitalId(), levelPO.getChangeDate(), MemberLevelConstant.YES, levelPO.getLevelType());
                MemberLevelVO levelVO = new MemberLevelVO();
                BeanUtils.copyProperties(levelVO,levelPO);
                if (null != lastLevel){
                    levelVO.setLastMemberLevel(lastLevel.getMemberLevel());
                    levelVO.setLastMemberLayer(lastLevel.getMemberLayer());
                }
                MemberPO member = this.memberService.getMemberById(levelPO.getMemberId());
                if (member != null){
                    levelVO.setMobilePhone(ValidateTool.tuoMin(member.getMobilePhone(),3,4,"*"));
                }
                levelVO.setInHos(levelPO.getInHos());
                vos.add(levelVO);
            }
        }
        PageResult<MemberLevelVO> pageResult = new PageResult<MemberLevelVO>(vos);
        pageResult.setTotalPages(result.getTotalPages());
        pageResult.setTotalRows(result.getTotalRows());
        pageResult.setPageNum(result.getPageNum());
        pageResult.setPageSize(result.getPageSize());
        return pageResult;
    }

    @Override
    public MemberLevelPO getMemberLevelById(String sid) {
        MemberLevelPO level = this.memberLevelMapper.getMemberLevelById(sid);
        return level;
    }

    @Override
    public void confirmMemberLevel(ConfirmLevelDTO confirmLevelDTO) {
        MemberLevelPO level = this.memberLevelMapper.getMemberLevelById(confirmLevelDTO.getSid());
        if(level == null){
            throw new BusinessException("记录不存在,操作失败！");
        }
        MemberLevelPO getMemberCurrentLevelParam = new MemberLevelPO();
        getMemberCurrentLevelParam.setMemberId(level.getMemberId());
        getMemberCurrentLevelParam.setLevelType(level.getLevelType());
        MemberLevelPO currentLevel = this.memberLevelMapper.getMemberCurrentLevel(getMemberCurrentLevelParam);
        this.memberLevelMapper.confirmMemberLevel(confirmLevelDTO);
            //清除患者当天非本次已确认的分层分级
        clearMemberLastUnConfirm(level.getSid(),level.getMemberId(),level.getHospitalId(),level.getChangeDate(),MemberLevelConstant.YES,level.getLevelType());
        memberHealthWarningNotice(level ,currentLevel);
    }

    @Override
    public long countMemberLevelRemind(ListMemberLevelDTO levelDTO) {
        return this.memberLevelMapper.countMemberLevelRemind(levelDTO);
    }

    @Override
    public MemberLevelPO getCurrentGxyLevel(CurrentGxyLevelDTO currentGxyLevelDTO) {
        return this.memberLevelMapper.getCurrentGxyLevel(currentGxyLevelDTO);
    }

    @Override
    public MemberLevelPO getLastLevel(LastLevelDTO lastLevelDTO) {
        return this.memberLevelMapper.getLastLevel(lastLevelDTO);
    }

    @Override
    public void addHypertensionLevel(MemberLevelPO levelPO) {
        if (null == levelPO.getMemberLevel() || null == levelPO.getMemberLayer()){
            return;
        }
        MemberLevelPO lastLevel = getLastLevel(levelPO.getMemberId(), levelPO.getHospitalId()
                , levelPO.getChangeDate(),  MemberLevelConstant.YES ,levelPO.getLevelType());
        boolean addFlag = false;
        //已确认并且是系统生成的
        boolean confirmAndOriginSys = (levelPO.getConfirm() == MemberLevelConstant.YES && levelPO.getOrigin() == MemberLevelConstant.ORIGIN_SYS)
                || levelPO.getConfirm() == MemberLevelConstant.NO;
        if(lastLevel == null){
            if(confirmAndOriginSys){
                levelPO.setContrastAnalyze("这是该患者的首次分层分级，并无任何对比分析");
            }
            addFlag = true;
        }else if(!checkNowAndLastLevel(levelPO ,lastLevel)){
            if(confirmAndOriginSys){
                String analyze = LevelAnalyzeHelper.getAnalyze(levelPO, lastLevel);
                levelPO.setContrastAnalyze(analyze);
            }
            addFlag = true;
        }
        if(addFlag){
            resolveHypertensionLevelChangeReason(levelPO);
            String sid = addMemberLevel(levelPO);
            if (levelPO.getConfirm() == 1){  //已确认的
                //清除患者当天非本次已确认的分层分级
                clearMemberLastUnConfirm(sid,levelPO.getMemberId(),levelPO.getHospitalId(),levelPO.getChangeDate(),MemberLevelConstant.YES,levelPO.getLevelType());
                //清除患者未确认的分层分级
                clearMemberLastUnConfirm("",levelPO.getMemberId(),levelPO.getHospitalId(),"",MemberLevelConstant.NO,levelPO.getLevelType());
            }else {  //未确认
                //清除患者非本次未确认的分层分级
                clearMemberLastUnConfirm(sid,levelPO.getMemberId(),levelPO.getHospitalId(),"",MemberLevelConstant.NO,levelPO.getLevelType());
            }
        }
    }

    @Override
    public void updateMemberLevel(MemberLevelPO update) {
        this.memberLevelMapper.updateMemberLevel(update);
    }

    @Override
    public MemberCurrentLeverBO getMemberCurrentLevel(String memberId) {
        MemberLevelPO memberLevelPO = new MemberLevelPO();
        memberLevelPO.setMemberId(memberId);
        memberLevelPO.setLevelType(MemberLevelConstant.TNB_TYPE);
        MemberLevelPO sugarLevel = memberLevelMapper.getMemberCurrentLevel(memberLevelPO);
        memberLevelPO.setLevelType(MemberLevelConstant.GXY_TYPE);
        MemberLevelPO pressureLevel = memberLevelMapper.getMemberCurrentLevel(memberLevelPO);
        MemberCurrentLeverBO result = new MemberCurrentLeverBO();
        String bloodLevelDesc = "暂无";
        String pressureLevelDesc = "暂无";
        String pressureLayerDesc = "暂无";
        if (null != sugarLevel){
            if (sugarLevel.getMemberLevel() == 1){
                bloodLevelDesc = "红标";
            }else if (sugarLevel.getMemberLevel() == 2){
                bloodLevelDesc = "黄标";
            }else if (sugarLevel.getMemberLevel() == 3){
                bloodLevelDesc = "绿标";
            }else if (sugarLevel.getMemberLevel() == 0){
                bloodLevelDesc = "其它";
            }
            result.setSugarLevel(sugarLevel.getMemberLevel());
            result.setSugarLevelDesc(bloodLevelDesc);
            result.setSugarConfirmDt(sugarLevel.getConfirmDt());
        }

        if (null != pressureLevel){
            if (pressureLevel.getMemberLevel() == 1){
                pressureLevelDesc = "1级";
            }else if (pressureLevel.getMemberLevel() == 2){
                pressureLevelDesc = "2级";
            }else if (pressureLevel.getMemberLevel() == 3){
                pressureLevelDesc = "3级";
            } else if (pressureLevel.getMemberLevel() == 0){
                pressureLevelDesc = "其它";
            }
            if (pressureLevel.getMemberLayer() == 1){
                pressureLayerDesc = "低危";
            }else if (pressureLevel.getMemberLayer() == 2){
                pressureLayerDesc = "中危";
            }else if (pressureLevel.getMemberLayer() == 3){
                pressureLayerDesc = "高危";
            }
            result.setPressureLevel(pressureLevel.getMemberLevel());
            result.setPressureLevelDesc(pressureLevelDesc);
            result.setPressureLayer(pressureLevel.getMemberLayer());
            result.setPressureLayerDesc(pressureLayerDesc);
            result.setPressureConfirmDt(pressureLevel.getConfirmDt());
        }
        return result;
    }




    /**
     * 处理日期
     * @param levelDTO
     */
    private void doMemberLevelDTO(ListMemberLevelDTO levelDTO){
        if (!StringUtils.isBlank(levelDTO.getCode())){
            //日期时间工具
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //1 近一个月 2 近三个月
            if ("1".equals(levelDTO.getCode())){
                c.add(Calendar.DATE,-29);
            }else if ("2".equals(levelDTO.getCode())){
                c.add(Calendar.DATE,-89);
            }
            String startDate= DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
            String endDate= DateHelper.getDate(new Date(), "yyyy-MM-dd");
            levelDTO.setStartDt(startDate +" 00:00:00");
            levelDTO.setEndDt(endDate + " 23:59:59");
        }
        levelDTO.setConfirm(1);
    }


    /**
     * 判断本次分层分级和上次是否相等
     * @param nowLevel
     * @param lastLevel
     * @return
     */
    private boolean checkNowAndLastLevel(MemberLevelPO nowLevel,MemberLevelPO lastLevel){
        boolean flag = false;
        if (null != lastLevel && null != nowLevel){
            Integer nowLev = nowLevel.getMemberLevel();
            Integer nowLay = nowLevel.getMemberLayer();
            Integer lastLev = lastLevel.getMemberLevel();
            Integer lastLay = lastLevel.getMemberLayer();
            if (nowLev == lastLev && nowLay == lastLay){
                return true;
            }
        }
        return flag;
    }

    @Override
    public Map<String, MemberLevelPO> getMemberLeverMap(ListMemberLevelDTO levelDTO) {
        Map<String, MemberLevelPO> result = new HashMap<>();
        List<MemberLevelPO> levels = listMemberLevel(levelDTO);
        for( MemberLevelPO po :levels){
/*            if(result.containsKey(po.getMemberId())){
                MemberLevelPO po1 = result.get(po.getMemberId());
                if(Boolean.TRUE.equals(DateHelper.dateAfter(po1.getChangeDate(), DateHelper.DATETIME_FORMAT, po.getChangeDate(), DateHelper.DATETIME_FORMAT))){
                    continue;
                }
            }*/
            result.putIfAbsent(po.getMemberId(), po);
        }
        return result;
    }

    private void memberHealthWarningNotice(MemberLevelPO current ,MemberLevelPO last){
        if(last == null){
            return;
        }
        int levelType = current.getLevelType();
        if(MemberLevelConstant.GXY_TYPE == levelType && (current.getMemberLevel() <= last.getMemberLevel())){
            return;
        }else if(MemberLevelConstant.TNB_TYPE == levelType && current.getMemberLevel() >= last.getMemberLevel()){
            return;
        }
        String warningTitle = "";
        String warningContent = "";
        String messageTemplate = "";
        String changeReason = StringUtils.isBlank(current.getChangeReason()) ? "其他原因" : current.getChangeReason();
        if(MemberLevelConstant.GXY_TYPE == levelType){
            messageTemplate = "当前患者的分级分层由{0}转至{1}，本次因为{2}导致分级分层转变";
            String currentStatus = MemberLevelConstant.getGxyLevelAndLayerText(current.getMemberLevel() ,current.getMemberLayer());
            String lastStatus = MemberLevelConstant.getGxyLevelAndLayerText(last.getMemberLevel() ,last.getMemberLayer());
            warningContent = currentStatus;
            warningTitle = MessageFormat.format(messageTemplate ,lastStatus ,currentStatus ,changeReason);
        }else{
            messageTemplate = "当前患者的分标由{0}转至{1}，{2}导致分标转变";
            String currentStatus = MemberLevelConstant.diabetesLevelText(current.getMemberLevel());
            String lastStatus = MemberLevelConstant.diabetesLevelText(last.getMemberLevel());
            warningContent = currentStatus;
            warningTitle = MessageFormat.format(messageTemplate ,lastStatus ,currentStatus ,changeReason);
        }
        AddMemberHealthWarningNoticeDTO addMemberHealthWarningNoticeDTO = new AddMemberHealthWarningNoticeDTO();
        addMemberHealthWarningNoticeDTO.setMemberId(current.getMemberId());
        addMemberHealthWarningNoticeDTO.setDatetime(DateHelper.getNowDate());
        addMemberHealthWarningNoticeDTO.setWarningTitle(warningTitle);
        addMemberHealthWarningNoticeDTO.setWarningContent(warningContent);
        addMemberHealthWarningNoticeDTO.setForeignId(current.getSid());
        this.doctorNoticeService.memberHealthWarningNotice(addMemberHealthWarningNoticeDTO);
    }

    private void resolveHypertensionLevelChangeReason(MemberLevelPO memberLevel){
        if(!StringUtils.isBlank(memberLevel.getChangeReason())){
            return;
        }
        Map<String,Object> map = FollowFirstGxyHelper.outFollowFirstGxy(JSON.parseObject(memberLevel.getDataJson()));
        Object factorText = map.get("factorText");
        if(factorText != null){
            memberLevel.setChangeReason(factorText.toString());
        }
    }
}
