package com.comvee.cdms.sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.ListBloodSugarDTO;
import com.comvee.cdms.sign.mapper.SugarMonthReportMapper;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.po.Hba1cPO;
import com.comvee.cdms.sign.po.SugarMonthReportPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.sign.service.SugarMonthReportService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service("SugarMonthReportService")
public class SugarMonthReportServiceImpl implements SugarMonthReportService {

    @Autowired
    private SugarMonthReportMapper sugarMonthReportMapper;

    @Autowired
    private Hba1cService hba1cService;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Override
    public String addSugarMonthReport(SugarMonthReportPO sugarMonthReportPO) {
        String reportId = DaoHelper.getSeq();
        sugarMonthReportPO.setReportId(reportId);
        this.sugarMonthReportMapper.addSugarMonthReport(sugarMonthReportPO);
        return reportId;
    }

    @Override
    public SugarMonthReportPO getSugarMonthReportById(String reportId) {
        return this.sugarMonthReportMapper.getSugarMonthReportById(reportId);
    }

    @Override
    public PageResult<SugarMonthReportPO> listSugarMonthReport(String memberId, PageRequest pr) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<SugarMonthReportPO> list = this.sugarMonthReportMapper.listSugarMonthReport(memberId);
        return new PageResult<>(list);
    }

    @Override
    public SugarMonthReportPO getSugarMonthReportByIdAndCompleteReport(String reportId) {
        SugarMonthReportPO sugarMonthReportPO = getSugarMonthReportById(reportId);
        if(sugarMonthReportPO == null){
            return null;
        }
        if(sugarMonthReportPO.getReportStatus() == 1){
            String hba1cData = getHba1cData(sugarMonthReportPO.getMemberId() , sugarMonthReportPO.getReportDate());
            String bloodSugarData = getBloodSugarData(sugarMonthReportPO.getMemberId() , sugarMonthReportPO.getReportDate());

            sugarMonthReportPO.setBloodSugarData(bloodSugarData);
            sugarMonthReportPO.setHba1cData(hba1cData);
            sugarMonthReportPO.setReportStatus(2);
            this.sugarMonthReportMapper.updateSugarMonthReport(sugarMonthReportPO);
        }
        return sugarMonthReportPO;
    }

    /**
     * 转化报告日期
     * @param reportDate
     * @return
     */
    private Date parseReportDate(String reportDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(reportDate);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 获取报告糖化记录
     * @param memberId
     * @param reportDate
     * @return
     */
    private String getHba1cData(String memberId ,String reportDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date reportDt = parseReportDate(reportDate);
        String startDt = LocalDateTime.ofInstant(reportDt.toInstant() , ZoneId.systemDefault()).minusMonths(2L).format(dtf);
        String endDt = LocalDateTime.ofInstant(reportDt.toInstant() , ZoneId.systemDefault()).plusMonths(1L).format(dtf);
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Hba1cPO.class, "recordValue" ,"recordLevel" ,"recordDt" ,"rangeMax" );
        List<Hba1cPO> list = this.hba1cService.listMemberHba1c(memberId , startDt, endDt);
        return JSON.toJSONString(list ,filter);
    }

    /**
     * 获取报告血糖记录
     * @param memberId
     * @param reportDate
     * @return
     */
    private String getBloodSugarData(String memberId , String reportDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date reportDt = parseReportDate(reportDate);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(reportDt.toInstant() , ZoneId.systemDefault());
        String startDt = localDateTime.format(dtf);
        String endDt = localDateTime.plusMonths(1L).format(dtf);
        ListBloodSugarDTO listBloodSugarDTO = new ListBloodSugarDTO();
        listBloodSugarDTO.setMemberId(memberId);
        listBloodSugarDTO.setStartDt(startDt);
        listBloodSugarDTO.setEndDt(endDt);
        List<BloodSugarPO> list = this.bloodSugarService.listBloodSugar(listBloodSugarDTO);
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(BloodSugarPO.class, "paramCode","paramValue" ,"paramLevel" ,"recordDt" ,"rangeMin" ,"rangeMax");
        return JSON.toJSONString(list ,filter);
    }

    @Override
    public SugarMonthReportPO getSugarMonthReportByMemberId(String memberId, String month) {
        return this.sugarMonthReportMapper.getSugarMonthReportByMemberId(memberId ,month);
    }

    @Override
    public List<SugarMonthReportPO> listPushSugarMonthReport(int limit) {
        String date = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return this.sugarMonthReportMapper.listPushSugarMonthReport(limit ,date);
    }

    @Override
    public void batchUpdateSugarMonthReportPushStatus(List<String> idList) {
        this.sugarMonthReportMapper.batchUpdateSugarMonthReportPushStatus(idList);
    }
}
