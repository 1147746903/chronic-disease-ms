package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodsugar.constant.DySensorConstant;
import com.comvee.cdms.dybloodsugar.dto.ListDySensorDTO;
import com.comvee.cdms.dybloodsugar.mapper.DySensorDoctorMapper;
import com.comvee.cdms.dybloodsugar.mapper.DySensorMapper;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DySensorDoctorPO;
import com.comvee.cdms.dybloodsugar.po.DySensorPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import com.comvee.cdms.dybloodsugar.service.DySensorService;
import com.comvee.cdms.other.constant.WechatQrCodeConstant;
import com.comvee.cdms.other.dto.CreateStrQrCodeDTO;
import com.comvee.cdms.other.po.WechatQrcodePO;
import com.comvee.cdms.other.service.WechatQrCodeService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DySensorServiceImpl implements DySensorService {
    @Autowired
    private DySensorMapper dySensorMapper;
    @Autowired
    private WechatQrCodeService qrCodeService;
    @Autowired
    private DySensorDoctorMapper sensorDoctorMapper;
    @Autowired
    private DyBloodSugarService dyBloodSugarService;


    @Override
    public void saveDySensor(DySensorPO dySensorPO) {
        if(StringUtils.isBlank(dySensorPO.getSid())) {
            if (dySensorMapper.hasDySensor(dySensorPO.getSensorNo()))
                throw new BusinessException("已有相同探头号出库");
            dySensorPO.setSid(DaoHelper.getSeq());
            dySensorMapper.addDySensor(dySensorPO);
        }else {
            DySensorPO updatePO = dySensorMapper.getDySensorById(dySensorPO.getSid());
            if(StringUtils.isBlank(dySensorPO.getExWarehouseDt()))
                updatePO.setExWarehouseDt(dySensorPO.getExWarehouseDt());
            if(StringUtils.isBlank(dySensorPO.getDoctorId()))
                updatePO.setDoctorId(dySensorPO.getDoctorId());
            dySensorMapper.updateDySensor(dySensorPO);
        }
    }


    @Override
    public void deleteDySensorById(String sid) {
        dySensorMapper.deleteDySensorById(sid);
    }

    @Override
    public DySensorPO getDySensorById(String sid) {
        return dySensorMapper.getDySensorById(sid);
    }

    @Override
    public PageResult<DySensorPO> listDySensor(ListDySensorDTO listDySensorDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<DySensorPO> poList = dySensorMapper.listDySensor(listDySensorDTO);
        for(DySensorPO po: poList){
            if(po.getSensorStatus() == DySensorConstant.SENSOR_STATUS_UN_START){
                List<DYYPBloodSugarPO> dyypBloodSugarPOS = this.dyBloodSugarService.listDYYPBloodSugarByNoASC(po.getSensorNo());
                String monitoringTimes = "";
                if(dyypBloodSugarPOS!=null&&dyypBloodSugarPOS.size()>0){
                    DYYPBloodSugarPO dyypBloodSugarPO = dyypBloodSugarPOS.get(0);
                    monitoringTimes = DateHelper.getDate(dyypBloodSugarPO.getRecordTime(), DateHelper.DATETIME_FORMAT);
                    po.setSensorStatus(DySensorConstant.SENSOR_STATUS_START);
                    po.setStartDt(monitoringTimes);
                    dySensorMapper.updateDySensor(po);
                }
            }
        }
        return new PageResult<>(poList);
    }



    @Override
    public DySensorPO createDySensorQrCode(String sid) {
        DySensorPO sensorPO = getDySensorById(sid);
        if(sensorPO != null && StringUtils.isBlank(sensorPO.getQrCodeUrl())) {
            CreateStrQrCodeDTO qrCodeDTO = new CreateStrQrCodeDTO();
            qrCodeDTO.setForeignId(sid);
            qrCodeDTO.setUploadOSS(true);
            qrCodeDTO.setDescription("探头二维码");
            JSONObject dataJson = new JSONObject();
            dataJson.put("sensorNo", sensorPO.getSensorNo());
            dataJson.put("doctorId", sensorPO.getDoctorId());
            qrCodeDTO.setDataJson(dataJson.toJSONString());
            qrCodeDTO.setBusinessType(WechatQrCodeConstant.QR_CODE_BUSINESS_TYPE_SENSOR);
            WechatQrcodePO qrcodePO = qrCodeService.createForeverStrQrCode(qrCodeDTO);
            if (qrcodePO != null) {
                sensorPO.setQrCodeUrl(qrcodePO.getQrcodeUrl());
                dySensorMapper.updateDySensor(sensorPO);
            }
        }
        return sensorPO;
    }

    @Override
    public void exportDySensorQrCode(ListDySensorDTO listDySensorDTO, OutputStream output) throws IOException {
        ZipOutputStream zip = new ZipOutputStream(output);
        //将目标文件打包成zip导出
        List<DySensorPO> poList = dySensorMapper.listDySensor(listDySensorDTO);
        if(poList.size() == 0)
            throw new BusinessException("无查询结果！");
        for (DySensorPO po : poList) {
            if(StringUtils.isBlank(po.getQrCodeUrl())){
                try {
                    po = createDySensorQrCode(po.getSid());
                }catch (Exception ignored){
                    continue;
                }
            }
            String filename = po.getSensorNo();
            if (!StringUtils.isBlank(po.getDoctorName())) {
                filename += "-" + po.getDoctorName();
            }
            filename += ".jpg";
            ZipEntry entry = new ZipEntry(filename);
            zip.putNextEntry(entry);
            URL url = new URL(po.getQrCodeUrl());
            InputStream is = url.openConnection().getInputStream();
            IOUtils.copy(is, zip);
            IOUtils.closeQuietly(is);
            zip.flush();
            zip.closeEntry();
        }
        IOUtils.closeQuietly(zip);
        output.flush();
        IOUtils.closeQuietly(output);
    }

    @Override
    public List<DySensorDoctorPO> listDySensorDoctor() {
        return sensorDoctorMapper.listDySensorDoctor();
    }

    @Override
    public void saveDySensorDoctor(DySensorDoctorPO sensorDoctorPO) {
        if(StringUtils.isBlank(sensorDoctorPO.getSid())){
            if(sensorDoctorMapper.hasDySensorDoctor(sensorDoctorPO))
                throw new BusinessException("重复添加医生");
            sensorDoctorPO.setSid(DaoHelper.getSeq());
            sensorDoctorMapper.addDySensorDoctor(sensorDoctorPO);
        }else {
            sensorDoctorMapper.updateDySensorDoctor(sensorDoctorPO);
        }
    }

    @Override
    public void deleteDySensorDoctorById(String sid) {
        sensorDoctorMapper.deleteDySensorDoctorById(sid);
    }
}
