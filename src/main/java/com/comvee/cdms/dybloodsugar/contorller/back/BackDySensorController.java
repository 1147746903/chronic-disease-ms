package com.comvee.cdms.dybloodsugar.contorller.back;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.ListDySensorDTO;
import com.comvee.cdms.dybloodsugar.po.DySensorDoctorPO;
import com.comvee.cdms.dybloodsugar.po.DySensorPO;
import com.comvee.cdms.dybloodsugar.service.DySensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("back/sensor")
public class BackDySensorController {

    @Autowired
    private DySensorService dySensorService;

    private final Logger logger = LoggerFactory.getLogger(BackDySensorController.class);
    /**
     * /back/sensor/dySensorExWarehouse.do
     * 探头出库
     * @return
     */
    @RequestMapping("dySensorExWarehouse")
    public Result dySensorExWarehouse(DySensorPO dySensorPO){
        ValidateTool.checkParamIsNull(dySensorPO.getSensorNo(), "sensorNo");
        ValidateTool.checkParamIsNull(dySensorPO.getExWarehouseDt(), "exWarehouseDt");
        if(dySensorPO.getSensorNo().length() != 11)
            throw new BusinessException("探头号格式异常，请输入11位探头号");
        dySensorService.saveDySensor(dySensorPO);
        return Result.ok();
    }


    /**
     * /back/sensor/updateDySensor.do
     * @param dySensorPO
     * @return
     */
    @RequestMapping("updateDySensor")
    public Result updateDySensor(DySensorPO dySensorPO){
        ValidateTool.checkParamIsNull(dySensorPO.getExWarehouseDt(), "exWarehouseDt");
        dySensorService.saveDySensor(dySensorPO);
        return Result.ok();
    }

    /**
     * /back/sensor/listDySensor.do
     * @param listDySensorDTO
     * @param pr
     * @return
     */
    @RequestMapping("listDySensor")
    public Result listDySensor(ListDySensorDTO listDySensorDTO, PageRequest pr){
        return Result.ok(dySensorService.listDySensor(listDySensorDTO, pr));
    }

    /**
     * 使用id查询探头信息
     * /back/sensor/getDySensorById.do
     * @param sid
     * @return
     */
    @RequestMapping("getDySensorById")
    public Result getDySensorById(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        return Result.ok(dySensorService.getDySensorById(sid));
    }

    /**
     * /back/sensor/createDySensorQrCode.do
     * @return
     */
    @RequestMapping("createDySensorQrCode")
    public Result createDySensorQrCode(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        DySensorPO sensorPO = dySensorService.createDySensorQrCode(sid);
        if(sensorPO == null)
            throw new BusinessException("无法获取探头信息");
        return Result.ok(sensorPO.getQrCodeUrl());
    }

    /**
     * /back/sensor/listDySensorDoctor.do
     * @return
     */
    @RequestMapping("listDySensorDoctor")
    public Result listDySensorDoctor(){
        return Result.ok(dySensorService.listDySensorDoctor());
    }

    /**
     * /back/sensor/savaDySensorDoctor.do
     * @param sensorDoctorPO
     * @return
     */
    @RequestMapping("savaDySensorDoctor")
    public Result savaDySensorDoctor(DySensorDoctorPO sensorDoctorPO){
        ValidateTool.checkParamIsNull(sensorDoctorPO.getDoctorId(), "doctorId");
        ValidateTool.checkParamIsNull(sensorDoctorPO.getHospitalId(), "hospitalId");
        dySensorService.saveDySensorDoctor(sensorDoctorPO);
        return Result.ok();
    }

    /**
     * /back/sensor/deleteDySensorDoctorById.do
     * @param sid
     * @return
     */
    @RequestMapping("deleteDySensorDoctorById")
    public Result deleteDySensorDoctorById(String sid){
        ValidateTool.checkParamIsNull(sid, "sid");
        dySensorService.deleteDySensorDoctorById(sid);
        return Result.ok();
    }
    /**
     * /back/sensor/exportDySensorQrCode.do
     * @param listDySensorDTO
     * @return
     */
    @RequestMapping("exportDySensorQrCode")
    public void exportDySensorQrCode(ListDySensorDTO listDySensorDTO, HttpServletResponse response){
        try {
            String fileName = new String("探头二维码图片.zip".getBytes("UTF-8"), "iso-8859-1");
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename="+fileName);
            response.setContentType("application/octet-stream");
            dySensorService.exportDySensorQrCode(listDySensorDTO, output);
        } catch (IOException e) {
           logger.error("生成压缩包失败");
        }
    }

}
