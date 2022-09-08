package com.comvee.cdms.checkresult.controller.other;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.checkresult.dto.AddCheckoutWithDetailDTO;
import com.comvee.cdms.checkresult.dto.AddInspectionDTO;
import com.comvee.cdms.checkresult.service.CheckoutServiceI;
import com.comvee.cdms.checkresult.service.InspectionServiceI;
import com.comvee.cdms.common.wrapper.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync/checkResult")
public class SyncCheckResultController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private InspectionServiceI inspectionService;

    @Autowired
    private CheckoutServiceI checkoutServiceI;

    @RequestMapping("syncInspectionReport")
    public Result syncInspectionReport(@RequestBody AddInspectionDTO addInspectionDTO){
        log.info("接收到新的同步检查报告数据，数据内容:{}" , JSON.toJSONString(addInspectionDTO));
        String result = this.inspectionService.addInspection(addInspectionDTO);
        return Result.ok(result);
    }

    @RequestMapping("syncCheckoutReport")
    public Result syncCheckoutReport(@RequestBody AddCheckoutWithDetailDTO add){
        log.info("接收到新的同步检验报告数据，数据内容:{}" , JSON.toJSONString(add));
        String result = this.checkoutServiceI.addCheckout(add);
        return Result.ok(result);
    }

}
