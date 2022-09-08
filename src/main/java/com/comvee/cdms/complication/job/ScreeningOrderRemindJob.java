package com.comvee.cdms.complication.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.complication.model.po.ScreeningListPO;
import com.comvee.cdms.complication.service.ScreeningService;
import com.comvee.cdms.complication.tool.ScreeningTool;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.google.common.base.Joiner;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author: suyz
 * @date: 2019/4/8
 */
@Component
public class ScreeningOrderRemindJob{

    private final static Logger log = LoggerFactory.getLogger(ScreeningOrderRemindJob.class);

    private final static int ROWS = 30;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private DoctorServiceI doctorService;

    @XxlJob("ScreeningOrderRemindJob")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<ScreeningListPO> pageResult = null;
        do{
            pageResult = this.screeningService.listScreeningOrderRemindPatient(page ,ROWS);
            start(pageResult.getRows());
            page ++;
        }while (pageResult.getTotalPages() > page);
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理
     * @param list
     */
    private void start(List<ScreeningListPO> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            handler(x);
        });
    }

    /**
     * 处理数据
     * @param screeningListPO
     */
    private void handler(ScreeningListPO screeningListPO){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(screeningListPO.getMemberId());
        addWechatMessageDTO.setForeignId(screeningListPO.getScreeningId());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_SCREENING_ORDER_REMIND);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hospitalName" ,getHospitalName(screeningListPO.getDoctorId()));
        jsonObject.put("date", getOrderDateText(screeningListPO));
        jsonObject.put("screeningItem" ,orderItemText(screeningListPO));
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 筛查项目文案
     * @param screeningListPO
     * @return
     */
    private String orderItemText(ScreeningListPO screeningListPO){
        List<String> itemNameList = new ArrayList<>();
        List<String> items = Arrays.asList(screeningListPO.getModules().split(","));
        items.forEach(x ->{
            Integer type = Integer.parseInt(x);
            itemNameList.add(ScreeningTool.getReportNameByType(type));
        });
        return Joiner.on("、").join(itemNameList);
    }

    /**
     * 预约日期文案
     * @param screeningListPO
     * @return
     */
    private String getOrderDateText(ScreeningListPO screeningListPO){
        return screeningListPO.getOrderDate() + " " + screeningListPO.getOrderTime();
    }

    /**
     * 获取医院名称
     * @param doctorId
     * @return
     */
    private String getHospitalName(String doctorId){
        DoctorPO doctorPO = this.doctorService.getDoctorById(doctorId);
        if(doctorPO == null){
            return "-";
        }
        return Optional.ofNullable(doctorPO.getHospitalName()).orElse("-");
    }
}
