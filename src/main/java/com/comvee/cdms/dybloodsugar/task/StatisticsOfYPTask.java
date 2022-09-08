package com.comvee.cdms.dybloodsugar.task;

import com.comvee.cdms.dybloodsugar.service.DyStaticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statisticsOfYPTask")
public class StatisticsOfYPTask implements Runnable {
    private DyStaticsService statisticsService;
    private List<String> paramOfRecordDts;
    private String sensorNo;
    private Integer chartType;

    public Integer getChartType() {
        return chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public DyStaticsService getStatisticsService() {
        return statisticsService;
    }

    public void setStatisticsService(DyStaticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public List<String> getParamOfRecordDts() {
        return paramOfRecordDts;
    }

    public void setParamOfRecordDts(List<String> paramOfRecordDts) {
        this.paramOfRecordDts = paramOfRecordDts;
    }

    @Override
    public void run() {
        if(chartType instanceof Integer){
            try{
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 计算血糖日趋势图统计-并保存
            this.statisticsService.statisticsOfYPGAPPHandle(1,this.paramOfRecordDts,this.sensorNo);
            // 计算动态血糖图图-并保存
            this.statisticsService.statisticsOfYPGAPPHandle(2,this.paramOfRecordDts,this.sensorNo);
            // 计算平均日间绝对差-并保存
            this.statisticsService.statisticsOfYPGAPPHandle(3,this.paramOfRecordDts,this.sensorNo);
            // 计算每日血糖总结-并保存
            this.statisticsService.statisticsOfYPGAPPHandle(4,this.paramOfRecordDts,this.sensorNo);
            // 同步更新探头开始时间
            this.statisticsService.updateSensorMonitorTimes(this.sensorNo);
        } else {
            this.statisticsService.statisticsOfYPGAPPHandle(this.chartType,this.paramOfRecordDts,this.sensorNo);
        }
    }
}
