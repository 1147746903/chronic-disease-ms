package com.comvee.cdms.statistics.service.impl;

import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.level.bo.MemberCurrentLeverBO;
import com.comvee.cdms.level.constant.MemberLevelConstant;
import com.comvee.cdms.level.dto.LastLevelDTO;
import com.comvee.cdms.level.helper.DiabetesLevelAnalyseHelper;
import com.comvee.cdms.level.helper.LevelAnalyzeHelper;
import com.comvee.cdms.level.mapper.MemberLevelMapper;
import com.comvee.cdms.level.po.MemberLevelPO;
import com.comvee.cdms.level.service.MemberLevelService;
import com.comvee.cdms.statistics.bo.MemberExceptionBaseBO;
import com.comvee.cdms.statistics.cfg.StatisticsNavigationItemEnum;
import com.comvee.cdms.statistics.mapper.StaticsMapper;
import com.comvee.cdms.statistics.po.MemberExceptionRecordPO;
import com.comvee.cdms.statistics.service.MemberExceptionScreenService;
import com.comvee.cdms.statistics.service.StatisticsService;
import com.comvee.cdms.statistics.vo.ListMemberExceptionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author linr
 * @Date 2022/2/18
 */
@Service
public class MemberExceptionScreenServiceImpl implements MemberExceptionScreenService {

    private final static Logger log = LoggerFactory.getLogger(MemberExceptionScreenServiceImpl.class);

    @Autowired
    private StaticsMapper staticsMapper;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private MemberLevelMapper memberLevelMapper;


    @Override
    public Map<String,Object> loadMemberExceptionDayList(String hospitalId) {
        String today = DateHelper.getToday();
        List<String> hospitalIdList = new ArrayList<>();
        hospitalIdList.add(hospitalId);
        List<String> items = getHospitalExceptionItems(hospitalId);
        Map<String, Object> map = statisticsService.loadNavigationItemData(items,hospitalIdList, today);
        List<ListMemberExceptionVO> listMemberExceptionVOS = staticsMapper.loadMemberExceptionRecordList(today, hospitalId);
        listMemberExceptionVOS.forEach(e->{
            MemberCurrentLeverBO memberCurrentLevel = memberLevelService.getMemberCurrentLevel(e.getMemberId());
            e.setSugarLevel(memberCurrentLevel.getSugarLevelDesc());
            e.setPressureLevel(memberCurrentLevel.getPressureLevelDesc());
            e.setPressureLayer(memberCurrentLevel.getPressureLayerDesc());
            //信息脱敏
            protectMemberInfo(e);
            //处理描述文案
            Map<String, String> detailMap = JsonSerializer.jsonToStringMap(e.getDataJson());
            String detail = MemberExceptionDetailHandler(detailMap);
            e.setDetail(detail);
        });
        map.put("dataList",listMemberExceptionVOS);
        return map;
    }



    //构建数据项
    private List<String> getHospitalExceptionItems(String hospitalId){
        List<String> items = staticsMapper.loadExceptionItemCodeByHospital(hospitalId);
        items.add(StatisticsNavigationItemEnum.TODAY_IN_HOSPITAL_NUM.getCode());//每个医院都有建档人数
        items.add(StatisticsNavigationItemEnum.ABNORMAL_NUM.getCode());//每个医院都有今日异常患者人数
        //存在一种并发症则需要返回并发症统计数据
        if (!items.contains(StatisticsNavigationItemEnum.TODAY_SCREEN_EXCEPTION.getCode())){
            if (items.contains(StatisticsNavigationItemEnum.EYE_SCREEN_EXCEPTION.getCode())||
                    items.contains(StatisticsNavigationItemEnum.VPT_SCREEN_EXCEPTION.getCode())||
                    items.contains(StatisticsNavigationItemEnum.ABI_SCREEN_EXCEPTION.getCode())){
                items.add(StatisticsNavigationItemEnum.TODAY_SCREEN_EXCEPTION.getCode());
            }
        }
        return items;
    }



    private void protectMemberInfo(ListMemberExceptionVO vo){
        String memberName = vo.getMemberName();
        String mobileNumber = vo.getMobilePhone();
        if (!StringUtils.isBlank(memberName)){
            vo.setMemberName(StringUtils.protectedName(memberName));
        }
        if (!StringUtils.isBlank(mobileNumber) && mobileNumber.length() >= 8){
            StringBuilder stringBuilder = new StringBuilder(mobileNumber);
            vo.setMobilePhone(stringBuilder.replace(3,7,"****").toString());
        }
    }

    @Override
    @Transactional
    public void memberExceptionInfoHandler() {
        String today = DateHelper.getToday();
        String startDt = today +DateHelper.DEFUALT_TIME_START;
        String endDt = today +DateHelper.DEFUALT_TIME_END;
        Map<String, Map<String, String>> exceptionMap = loadMemberExceptionList(startDt,endDt);
        if (!exceptionMap.isEmpty() && exceptionMap.size()>0){
            for (String memberId : exceptionMap.keySet()) {
                Map<String, String> memberException = exceptionMap.get(memberId);
                MemberExceptionRecordPO getRecordPO = new MemberExceptionRecordPO();
                getRecordPO.setMemberId(memberId);
                getRecordPO.setRecordDt(today);
                List<MemberExceptionRecordPO> memberRecordPO = staticsMapper.queryAll(getRecordPO);
                Map<String, String> map;
                String dataJson = "";
                if (memberRecordPO.size()>0){
                    //今日已存在记录
                    dataJson = memberRecordPO.get(0).getDataJson();
                    map = JsonSerializer.jsonToStringLinkedMap(dataJson);
                }else {
                    //今日不存在记录
                    map = memberAllExceptionDataHandler(memberId);
                }
                //map赋最新值
                for (String itemCode : map.keySet()) {
                    String value = StringUtils.isBlank(memberException.get(itemCode))?"":memberException.get(itemCode);
                    map.put(itemCode, value);
                }
                //入库
                if (!map.isEmpty() && map.size()>0){
                    memberExceptionDBAddOrUpdate(memberId,today,map,dataJson);
                }

            }
        }
    }

    private void memberExceptionDBAddOrUpdate(String memberId,String today,Map<String, String> map,String oldJson){
        String resultMap = JsonSerializer.objectToJson(map);
//        if (!StringUtils.isBlank(oldJson) && oldJson.equals(resultMap)){
//            //未发生变化则不入库
//            return;
//        }
        List<String> hospitalIdList = staticsMapper.getHospitalByMemberId(memberId);
        MemberExceptionRecordPO memberExceptionRecordPO;
        for (String hospitalId : hospitalIdList) {
            memberExceptionRecordPO = new MemberExceptionRecordPO();
            memberExceptionRecordPO.setSid(DaoHelper.getSeq());
            memberExceptionRecordPO.setMemberId(memberId);
            memberExceptionRecordPO.setRecordDt(today);
            memberExceptionRecordPO.setDataJson(resultMap);
            memberExceptionRecordPO.setHospitalId(hospitalId);
            staticsMapper.insert(memberExceptionRecordPO);
        }
    }



    private String MemberExceptionDetailHandler(Map<String, String> map){
        StringBuilder sb = new StringBuilder();
        if (!map.isEmpty()){
            //sb.append("患者");
            for (String itemCode : map.keySet()) {
                String value = map.get(itemCode);
                if (!StringUtils.isBlank(value)){
                    String desc = StatisticsNavigationItemEnum.getDesc(itemCode);
                    String unit = StatisticsNavigationItemEnum.getUnit(itemCode);
                    if (!StringUtils.isBlank(desc)){
                        if (StringUtils.isBlank(unit))
                        {
                            sb.append(desc+"、");
                        }else {
                            sb.append(desc+value+unit+"、");
                        }
                    }
                }
            }
        }
        if (String.valueOf(sb.charAt(sb.length() - 1)).equals("、") ){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    //获取患者异常数据并封装map
    private Map<String, Map<String, String>> loadMemberExceptionList(String startDt,String endDt){
        List<MemberExceptionBaseBO> allList = staticsMapper.loadAllMemberException(startDt,endDt);
        //分标由好变坏的
        MemberLevelPO getLevelParam = new MemberLevelPO();
        getLevelParam.setLevelType(MemberLevelConstant.TNB_TYPE);
        getLevelParam.setConfirmDt(DateHelper.getToday());
        //今日确认分标
        List<MemberLevelPO> memberLevelList = memberLevelMapper.listDayLevel(getLevelParam);
        for (MemberLevelPO memberLevelPO : memberLevelList) {
            MemberLevelPO param = new MemberLevelPO();
            param.setMemberId(memberLevelPO.getMemberId());
            param.setLevelType(MemberLevelConstant.TNB_TYPE);
            param.setConfirmDt(memberLevelPO.getConfirmDt());
            MemberLevelPO memberLast2Level = memberLevelMapper.getMemberLast2Level(param);
            if (null != memberLast2Level && memberLast2Level.getMemberLevel() > memberLevelPO.getMemberLevel()){
                String value = "由"+ DiabetesLevelAnalyseHelper.getLevel(memberLast2Level.getMemberLevel())+
                        "转变为"+DiabetesLevelAnalyseHelper.getLevel(memberLevelPO.getMemberLevel());
                allList.add(new MemberExceptionBaseBO(memberLevelPO.getMemberId(),StatisticsNavigationItemEnum.BLOOD_SUGAR_LEVER_EXCEPTION.getCode(),value));
                allList.add(new MemberExceptionBaseBO(memberLevelPO.getMemberId(),StatisticsNavigationItemEnum.sugar_level_id.getCode(),memberLevelPO.getSid()));
            }
        }

        //分层分级由好变坏的
        getLevelParam.setLevelType(MemberLevelConstant.GXY_TYPE);
        //今日确认分层分级
        List<MemberLevelPO> memberLayerList = memberLevelMapper.listDayLevel(getLevelParam);
        for (MemberLevelPO memberLayerPO : memberLayerList) {
            MemberLevelPO param = new MemberLevelPO();
            param.setMemberId(memberLayerPO.getMemberId());
            param.setLevelType(MemberLevelConstant.GXY_TYPE);
            param.setConfirmDt(memberLayerPO.getConfirmDt());
            MemberLevelPO memberLast2Layer = memberLevelMapper.getMemberLast2Level(param);
            if (null != memberLast2Layer && memberLast2Layer.getMemberLayer() < memberLayerPO.getMemberLayer()){
                String value = "由"+ LevelAnalyzeHelper.getLevel(memberLast2Layer.getMemberLevel())+
                        LevelAnalyzeHelper.getLayer(memberLast2Layer.getMemberLayer())+
                        "转变为"+LevelAnalyzeHelper.getLevel(memberLayerPO.getMemberLevel())+
                        LevelAnalyzeHelper.getLayer(memberLayerPO.getMemberLayer());
                allList.add(new MemberExceptionBaseBO(memberLayerPO.getMemberId(),StatisticsNavigationItemEnum.BLOOD_PRESSURE_LEVER_EXCEPTION.getCode(),value));
                allList.add(new MemberExceptionBaseBO(memberLayerPO.getMemberId(),StatisticsNavigationItemEnum.pressure_level_id.getCode(),memberLayerPO.getSid()));
            }
        }

        Map<String, Map<String, String>> resultMap = new HashMap<>();
            Map<String, List<MemberExceptionBaseBO>> getMemberMap = toMapList(allList, "getMemberId");
            for (String s : getMemberMap.keySet()) {
                Map<String, String> map = new HashMap<>();
                for (MemberExceptionBaseBO po : getMemberMap.get(s)) {
                    map.put(po.getItemCode(), po.getItemValue());
                }
            resultMap.put(s, map);
        }
        return resultMap;
    }


    //格式转换
    private  <T> Map<String, List<T>> toMapList(List<T> list, String key) {
        Map<String, List<T>> map = new LinkedHashMap<>();
        List<T> clist = null;
        if (!list.isEmpty()) {
            for (T t : list) {
                try {
                    Class<? extends Object> obj = t.getClass();
                    Method method = obj.getMethod(key, new Class[0]);
                    String o = null;
                    if (method.getReturnType() == String.class) {
                        o = (String) (method.invoke(t, new Object[0]));
                    } else {
                        Object ojt = method.invoke(t, new Object[0]);
                        o = (ojt == null ? "" : String.valueOf(ojt));
                    }
                    clist = map.computeIfAbsent(o, k -> new ArrayList<T>());
                    clist.add(t);
                }catch (Exception e){
                    log.error("患者异常信息数据转换处理失败",e);
                }
            }
        }
        return map;
    }


    //构造初始map
    private Map<String,String> memberAllExceptionDataHandler(String memberId) {
        Map<String, String> map = new LinkedHashMap<>();
        List<String> codeList = staticsMapper.loadExceptionItemCodeByMemberId(memberId);
        if (codeList.size() > 0){
            codeList.forEach(e->{
                String desc = StatisticsNavigationItemEnum.getDesc(e);
                //有配置文案才需要展示
                if (!StringUtils.isBlank(desc)){
                    map.put(e,"");
                }
            });
        }
        if (map.containsKey(StatisticsNavigationItemEnum.BLOOD_SUGAR_LEVER_EXCEPTION.getCode())){
            map.put(StatisticsNavigationItemEnum.sugar_level_id.getCode(),"");
        }
        if (map.containsKey(StatisticsNavigationItemEnum.BLOOD_PRESSURE_LEVER_EXCEPTION.getCode())){
            map.put(StatisticsNavigationItemEnum.pressure_level_id.getCode(),"");
        }
        return map;
    }

}
