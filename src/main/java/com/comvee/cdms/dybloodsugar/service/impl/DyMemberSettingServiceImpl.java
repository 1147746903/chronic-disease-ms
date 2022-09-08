package com.comvee.cdms.dybloodsugar.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.dybloodsugar.mapper.DyMemberSettingMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyRecordSettingMapper;
import com.comvee.cdms.dybloodsugar.po.DYMemberSensorPO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DyMemberSettingPO;
import com.comvee.cdms.dybloodsugar.po.DyRecordSettingPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSensorService;
import com.comvee.cdms.dybloodsugar.service.DyMemberSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DyMemberService")
public class DyMemberSettingServiceImpl implements DyMemberSettingService {

    @Autowired
    private DyMemberSettingMapper dyMemberSettingMapper;

    @Autowired
    private DyRecordSettingMapper dyRecordSettingMapper;

    @Autowired
    private DyMemberSensorService dyMemberSensorService;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    @Override
    public void insertSettingRecord(DyMemberSettingPO dyMemberSettingPO, String sensorNo) {
        DyRecordSettingPO dySettingRecord = new DyRecordSettingPO();
        BeanUtils.copyProperties(dySettingRecord,dyMemberSettingPO);
        String sid = DaoHelper.getSeq();
        dySettingRecord.setSid(sid);
        dySettingRecord.setSensorNo(sensorNo);
        this.dyRecordSettingMapper.insertSettingRecord(dySettingRecord);
    }

    @Override
    public DyRecordSettingPO getSettingValues(String sensorNo) {
        DyRecordSettingPO dyRecordSettingPO = this.dyRecordSettingMapper.getSettingValues(sensorNo);
        //1:查询这个探头绑定的信息
        DYYPBloodSugarPO dyypBloodSugarPO = this.dyBloodSugarService.getDyBloodSugar(sensorNo);
        //dyypBloodSugarPO不为null,说明这个探头已经传输过数据,开始进行记录.
        if (dyypBloodSugarPO != null) {
            //2:计算探头从开始时间到现在使用了多少天.
            String startTime = DateHelper.getNowDate();
            String endTime = DateHelper.getDate(dyypBloodSugarPO.getRecordTime(), "yyyy-MM-dd HH:mm:ss");
            int day = DateHelper.dateCompareGetDay(startTime, endTime);
            //3:判断是否超过14天,超过14天说明这个探头失效了
            if (day < 14) {
                DyRecordSettingPO dyRecordSetting = new DyRecordSettingPO();
                //根据探头序列号查找对应的患者id
                DYMemberSensorPO dyMemberSensorPO = this.dyMemberSensorService.getDYMemberSensorPO(sensorNo);
                //不为空则找对应患者设置最新的血糖设置
                if (dyMemberSensorPO != null) {
                    //根据患者id查找最新的设置指标数据
                    DyMemberSettingPO dyMemberSettingPO = this.dyMemberSettingMapper.getSystemSetting(dyMemberSensorPO.getMemberId());
                    if (dyMemberSettingPO == null) {
                        //为空返回默认值
                        DyMemberSettingPO dyMemberSetting = defaultValues();
                        BeanUtils.copyProperties(dyRecordSetting, dyMemberSetting);
                    } else {
                        BeanUtils.copyProperties(dyRecordSetting, dyMemberSettingPO);
                    }
                } else {
                    //为空返回默认值
                    DyMemberSettingPO dyMemberSetting = defaultValues();
                    BeanUtils.copyProperties(dyRecordSetting, dyMemberSetting);
                }
                return dyRecordSetting;
            }

        }

        if (dyRecordSettingPO == null){
            //为空返回默认值
            dyRecordSettingPO = new DyRecordSettingPO();
            dyRecordSettingPO.setBreakfastDt("06:30");
            dyRecordSettingPO.setLunchDt("11:30");
            dyRecordSettingPO.setDinnerDt("18:10");
            dyRecordSettingPO.setSleepDt("22:30");
            dyRecordSettingPO.setBloodSugarMin(3.9D);
            dyRecordSettingPO.setBloodSugarMax(10.0D);
            dyRecordSettingPO.setBloodSugarNorm(70);
            dyRecordSettingPO.setBloodSugarNormLess(4);
            dyRecordSettingPO.setBloodSugarNormThan(25);
            dyRecordSettingPO.setBloodTimeRatioMin(3.0D);
            dyRecordSettingPO.setBloodTimeRatioMax(13.9D);
            dyRecordSettingPO.setBloodSugarMinAfter(7.8D);
            dyRecordSettingPO.setBloodSugarMaxAfter(10.0D);
            dyRecordSettingPO.setBloodSugarNormRatioMax(5);
            dyRecordSettingPO.setBloodSugarNormRatioMin(4);
            dyRecordSettingPO.setMedianTarget(8.0D);
            dyRecordSettingPO.setGlucoseMin("中");
            dyRecordSettingPO.setHbA1cMax(7.0D);
            return dyRecordSettingPO;
        }
        return dyRecordSettingPO;

    }
    @Override
    public void insertSystemSetting(DyMemberSettingPO dyMemberSettingPO) {
        //根据患者查询设置指标表
        if (dyMemberSettingPO.getBloodSugarMax()<=dyMemberSettingPO.getBloodSugarMin()){
            throw new BusinessException("血糖目标范围最大值不能小于等于血糖目标范围最小值");
        }
        if (dyMemberSettingPO.getBloodSugarMaxAfter()<=dyMemberSettingPO.getBloodSugarMinAfter()){
            throw new BusinessException("餐后血糖目标范围最大值不能小于等于餐后血糖目标范围最小值");
        }
        DyMemberSettingPO dyMemberSettingOther = this.dyMemberSettingMapper.getDyMemberSettingPoByMemberId(dyMemberSettingPO.getMemberId());
        if (dyMemberSettingOther ==null){
            dyMemberSettingOther = new DyMemberSettingPO();
            BeanUtils.copyProperties(dyMemberSettingOther,dyMemberSettingPO);
            String sid = DaoHelper.getSeq();
            dyMemberSettingOther.setSid(sid);
            defaultSetting(dyMemberSettingPO,dyMemberSettingOther);
            this.dyMemberSettingMapper.insertSystemSetting(dyMemberSettingOther);
        }else{
            BeanUtils.copyProperties(dyMemberSettingOther,dyMemberSettingPO);
            dyMemberSettingOther.setModifyDt(DateHelper.getNowDate());
            defaultSetting(dyMemberSettingPO,dyMemberSettingOther);
            this.dyMemberSettingMapper.updateSystemSetting(dyMemberSettingOther);
        }

    }


    @Override
    public DyMemberSettingPO getSystemSetting(String memberId) {
        DyMemberSettingPO dyMemberSettingPo = this.dyMemberSettingMapper.getSystemSetting(memberId);
        return dyMemberSettingPo;
    }


    @Override
    public DyMemberSettingPO restoreDefault(String id) {
        DyMemberSettingPO dyMemberSetting = new DyMemberSettingPO();
        if ("1".equals(id)) {
            dyMemberSetting.setBreakfastDt("06:50");
            dyMemberSetting.setLunchDt("11:30");
            dyMemberSetting.setDinnerDt("18:10");
            dyMemberSetting.setSleepDt("22:30");
            return dyMemberSetting;
        }else if ("2".equals(id)) {
            dyMemberSetting.setBloodSugarMin(3.9D);
            dyMemberSetting.setBloodSugarMax(7.8D);
            dyMemberSetting.setBloodSugarMinAfter(7.8D);
            dyMemberSetting.setBloodSugarMaxAfter(10.0D);
            dyMemberSetting.setBloodSugarNorm(70);
            dyMemberSetting.setBloodSugarNormLess(4);
            dyMemberSetting.setBloodSugarNormThan(25);
            dyMemberSetting.setMedianTarget(8.0D);
            dyMemberSetting.setGlucoseMin("中");
            dyMemberSetting.setHbA1cMax(7.0D);
            return dyMemberSetting;
        }else{
            dyMemberSetting.setBloodTimeRatioMin(3.0D);
            dyMemberSetting.setBloodTimeRatioMax(13.9D);
            dyMemberSetting.setBloodSugarNormRatioMax(5);
            dyMemberSetting.setBloodSugarNormRatioMin(4);
            return dyMemberSetting;
        }
    }


    @Override
    public DyMemberSettingPO showSystemSetting(String memberId) {
        //根据患者id查询设置表中设置的最新指标数据
        DyMemberSettingPO dyMemberSettingPo = getSystemSetting(memberId);
        //如果不为null,说明有更改过默认设置,则将查询到的设置数据返回给前端
        if (dyMemberSettingPo == null){
            DyMemberSettingPO dyMemberSettingPO1 = defaultValues();
            return dyMemberSettingPO1;
        }
        //查询到的设置数据返回给前端
        return dyMemberSettingPo;
    }

    private DyMemberSettingPO defaultValues(DyMemberSettingPO dyMemberSettingPO){
        dyMemberSettingPO.setBreakfastDt("06:30");
        dyMemberSettingPO.setLunchDt("11:30");
        dyMemberSettingPO.setDinnerDt("18:10");
        dyMemberSettingPO.setSleepDt("22:30");
        dyMemberSettingPO.setBloodSugarMin(3.9D);
        dyMemberSettingPO.setBloodSugarMax(10.0D);
        dyMemberSettingPO.setBloodSugarNorm(70);
        dyMemberSettingPO.setBloodSugarNormLess(4);
        dyMemberSettingPO.setBloodSugarNormThan(25);
        dyMemberSettingPO.setBloodTimeRatioMin(3.0D);
        dyMemberSettingPO.setBloodTimeRatioMax(13.9D);
        return dyMemberSettingPO;
    }
    private DyMemberSettingPO defaultValues(){
        DyMemberSettingPO dyMemberSettingPO = new DyMemberSettingPO();
        dyMemberSettingPO.setBreakfastDt("06:30");
        dyMemberSettingPO.setLunchDt("11:30");
        dyMemberSettingPO.setDinnerDt("18:10");
        dyMemberSettingPO.setSleepDt("22:30");
        dyMemberSettingPO.setBloodSugarMin(3.9D);
        dyMemberSettingPO.setBloodSugarMax(7.8D);
        dyMemberSettingPO.setBloodSugarNorm(70);
        dyMemberSettingPO.setBloodSugarNormLess(4);
        dyMemberSettingPO.setBloodSugarNormThan(25);
        dyMemberSettingPO.setBloodTimeRatioMin(3.0D);
        dyMemberSettingPO.setBloodTimeRatioMax(13.9D);
        dyMemberSettingPO.setBloodSugarMinAfter(7.8D);
        dyMemberSettingPO.setBloodSugarMaxAfter(10.0D);
        dyMemberSettingPO.setBloodSugarNormRatioMax(5);
        dyMemberSettingPO.setBloodSugarNormRatioMin(4);
        dyMemberSettingPO.setMedianTarget(8.0D);
        dyMemberSettingPO.setGlucoseMin("中");
        dyMemberSettingPO.setHbA1cMax(7.0D);
        return dyMemberSettingPO;
    }

    private void defaultSetting(DyMemberSettingPO dyMemberSettingPO,DyMemberSettingPO dyMemberSettingOther){
        if (dyMemberSettingPO.getBreakfastDt() == null){
            dyMemberSettingOther.setBreakfastDt("06:50");
        }
        if (dyMemberSettingPO.getLunchDt() == null){
            dyMemberSettingOther.setLunchDt("11:30");
        }
        if (dyMemberSettingPO.getDinnerDt() == null){
            dyMemberSettingOther.setDinnerDt("18:10");
        }
        if (dyMemberSettingPO.getSleepDt() == null){
            dyMemberSettingOther.setSleepDt("22:30");
        }
        if (dyMemberSettingPO.getBloodSugarMax() == null){
            dyMemberSettingOther.setBloodSugarMax(7.8D);
        }
        if (dyMemberSettingPO.getBloodSugarMin() == null){
            dyMemberSettingOther.setBloodSugarMin(3.9D);
        }
        if (dyMemberSettingPO.getBloodSugarNorm() == null){
            dyMemberSettingOther.setBloodSugarNorm(70);
        }
        if (dyMemberSettingPO.getBloodSugarNormThan() == null){
            dyMemberSettingOther.setBloodSugarNormThan(25);
        }
        if (dyMemberSettingPO.getBloodSugarNormLess() == null){
            dyMemberSettingOther.setBloodSugarNormLess(4);
        }
        if (dyMemberSettingPO.getBloodTimeRatioMax() == null){
            dyMemberSettingOther.setBloodTimeRatioMax(13.9D);
        }
        if (dyMemberSettingPO.getBloodTimeRatioMin() == null){
            dyMemberSettingOther.setBloodTimeRatioMin(3.0D);
        }
        if (dyMemberSettingPO.getBloodSugarMaxAfter() == null){
            dyMemberSettingOther.setBloodSugarMaxAfter(10.0D);
        }
        if (dyMemberSettingPO.getBloodSugarMinAfter() == null){
            dyMemberSettingOther.setBloodSugarMinAfter(7.8D);
        }
        if (dyMemberSettingPO.getBloodSugarNormRatioMax() == null){
            dyMemberSettingOther.setBloodSugarNormRatioMax(5);
        }
        if (dyMemberSettingPO.getBloodSugarNormRatioMin() == null){
            dyMemberSettingOther.setBloodSugarNormRatioMin(4);
        }
        if (dyMemberSettingPO.getGlucoseMin() == null){
            dyMemberSettingOther.setGlucoseMin("中");
        }
        if (dyMemberSettingPO.getHbA1cMax() == null){
            dyMemberSettingOther.setHbA1cMax(7.0D);
        }
        if (dyMemberSettingPO.getMedianTarget() == null){
            dyMemberSettingOther.setMedianTarget(8.0D);
        }

    }



}
