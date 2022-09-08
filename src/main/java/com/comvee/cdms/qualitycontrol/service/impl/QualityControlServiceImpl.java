package com.comvee.cdms.qualitycontrol.service.impl;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.machine.constant.MachineConstant;
import com.comvee.cdms.machine.po.MachinePO;
import com.comvee.cdms.machine.service.MachineService;
import com.comvee.cdms.qualitycontrol.constant.QualityControlConstant;
import com.comvee.cdms.qualitycontrol.mapper.QualityControlLiquidMapper;
import com.comvee.cdms.qualitycontrol.mapper.QualityControlMapper;
import com.comvee.cdms.qualitycontrol.mapper.QualityControlMaterialConfigMapper;
import com.comvee.cdms.qualitycontrol.mapper.QualityControlTestPaperMapper;
import com.comvee.cdms.qualitycontrol.model.bo.MaterialConfigBO;
import com.comvee.cdms.qualitycontrol.model.bo.RangeConfigBO;
import com.comvee.cdms.qualitycontrol.model.param.AddQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.param.ListQualityControlParam;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlLiquidPO;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlMaterialConfigPO;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlPO;
import com.comvee.cdms.qualitycontrol.model.po.QualityControlTestPaperPO;
import com.comvee.cdms.qualitycontrol.model.vo.QualityControlVO;
import com.comvee.cdms.qualitycontrol.service.QualityControlService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("qualityControlService")
public class QualityControlServiceImpl implements QualityControlService {

    @Autowired
    private QualityControlMaterialConfigMapper qualityControlMaterialConfigMapper;

    @Autowired
    private QualityControlTestPaperMapper qualityControlTestPaperMapper;

    @Autowired
    private QualityControlLiquidMapper qualityControlLiquidMapper;

    @Autowired
    private QualityControlMapper qualityControlMapper;

    @Autowired
    private MachineService machineService;

    @Override
    public List<MaterialConfigBO> getMaterialConfig(String hospitalId) {
        QualityControlMaterialConfigPO qualityControlMaterialConfig = this.qualityControlMaterialConfigMapper.getQualityControlMaterialConfig(hospitalId);
        if(qualityControlMaterialConfig != null){
            return JSON.parseArray(qualityControlMaterialConfig.getConfigData() ,MaterialConfigBO.class);
        }
        return getDefaultMaterialConfig();
    }

    @Override
    public void updateMaterialConfig(String doctorId ,String hospitalId ,List<MaterialConfigBO> config) {
        List testPaperList = this.qualityControlTestPaperMapper.listTestPaper(hospitalId);
        if(testPaperList != null && !testPaperList.isEmpty()){
            throw new BusinessException("已经新增了质控试纸，无法修改质控品属性配置");
        }
        QualityControlMaterialConfigPO add = new QualityControlMaterialConfigPO();
        add.setSid(DaoHelper.getSeq());
        QualityControlMaterialConfigPO qualityControlMaterialConfig = this.qualityControlMaterialConfigMapper.getQualityControlMaterialConfig(hospitalId);
        if (null != qualityControlMaterialConfig){
            add.setSid(qualityControlMaterialConfig.getSid());
        }
        add.setOperatorId(doctorId);
        add.setHospitalId(hospitalId);
        add.setConfigData(JSON.toJSONString(config));
        this.qualityControlMaterialConfigMapper.addOrUpdateQualityControlMaterialConfig(add);
    }

    @Override
    public PageResult<QualityControlTestPaperPO> listTestPaper(PageRequest pr, String hospitalId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List list = this.qualityControlTestPaperMapper.listTestPaper(hospitalId);
        return new PageResult<QualityControlTestPaperPO>(list);
    }

    @Override
    public PageResult<QualityControlTestPaperPO> listTestPaperValid(PageRequest pr, String hospitalId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List list = this.qualityControlTestPaperMapper.listTestPaperValid(hospitalId);
        return new PageResult<QualityControlTestPaperPO>(list);
    }

    @Override
    public QualityControlTestPaperPO getTestPaper(String id) {
        return this.qualityControlTestPaperMapper.getTestPaperById(id ,null ,null);
    }

    @Override
    public void updateTestPaper(QualityControlTestPaperPO update) {
        QualityControlTestPaperPO getResult = getTestPaper(update.getSid());
        if(getResult == null){
            throw new BusinessException("质控试纸数据不存在，无法修改");
        }
        this.qualityControlTestPaperMapper.updateTestPaper(update);
    }

    @Override
    public void deleteTestPaper(String id) {
        QualityControlTestPaperPO getResult = getTestPaper(id);
        if(getResult == null){
            throw new BusinessException("质控试纸数据不存在，无法删除");
        }
        QualityControlTestPaperPO update = new QualityControlTestPaperPO();
        update.setSid(id);
        update.setValid(0);
        this.qualityControlTestPaperMapper.updateTestPaper(update);
    }

    @Override
    public String addTestPaper(QualityControlTestPaperPO add) {
        QualityControlTestPaperPO getResult = this.qualityControlTestPaperMapper.getTestPaperById(null ,add.getHospitalId() ,add.getBatchNo());
        if(getResult != null){
            throw new BusinessException("批次号已存在，请确认");
        }
        add.setSid(DaoHelper.getSeq());
        this.qualityControlTestPaperMapper.addTestPaper(add);
        return add.getSid();
    }

    @Override
    public PageResult<QualityControlLiquidPO> listLiquid(PageRequest pr, String hospitalId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<QualityControlLiquidPO> list = this.qualityControlLiquidMapper.listQualityControlLiquid(hospitalId);
        return new PageResult<>(list);
    }

    @Override
    public PageResult<QualityControlLiquidPO> listLiquidValid(PageRequest pr, String hospitalId) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<QualityControlLiquidPO> list = this.qualityControlLiquidMapper.listQualityControlLiquidValid(hospitalId);
        return new PageResult<>(list);
    }

    @Override
    public String addLiquid(QualityControlLiquidPO add) {
        QualityControlLiquidPO getResult = this.qualityControlLiquidMapper.getQualityControlLiquidById(null ,add.getHospitalId() ,add.getBatchNo());
        if(getResult != null){
            throw new BusinessException("批次号已存在，请确认");
        }
        add.setSid(DaoHelper.getSeq());
        this.qualityControlLiquidMapper.addQualityControlLiquid(add);
        return add.getSid();
    }

    @Override
    public void updateLiquid(QualityControlLiquidPO update) {
        QualityControlLiquidPO getResult = getLiquid(update.getSid());
        if(getResult == null){
            throw new BusinessException("质控液数据不存在，无法修改");
        }
        this.qualityControlLiquidMapper.updateQualityControlLiquid(update);
    }

    @Override
    public void deleteLiquid(String id) {
        QualityControlLiquidPO getResult = getLiquid(id);
        if(getResult == null){
            throw new BusinessException("质控液数据不存在，无法删除");
        }
        QualityControlLiquidPO update = new QualityControlLiquidPO();
        update.setSid(id);
        update.setValid(0);
        this.qualityControlLiquidMapper.updateQualityControlLiquid(update);
    }

    @Override
    public QualityControlLiquidPO getLiquid(String id) {
        return this.qualityControlLiquidMapper.getQualityControlLiquidById(id ,null ,null);
    }

    @Override
    public PageResult<QualityControlVO> listQualityControl(PageRequest pr, ListQualityControlParam param) {
        PageHelper.startPage(pr.getPage() ,pr.getRows());
        List<QualityControlVO> list = this.qualityControlMapper.listQualityControl(param);
        return new PageResult<>(list);
    }

    @Override
    public void unlockMachine(String machineId) {
        MachinePO update = new MachinePO();
        update.setMachineId(machineId);
        update.setMachineStatus(MachineConstant.MACHINE_STATUS_UN_LOCK);
        this.machineService.updateMachine(update);
    }

    @Override
    public int addQualityControl(AddQualityControlParam param) {
        QualityControlTestPaperPO testPaper = this.qualityControlTestPaperMapper.getTestPaperById(null ,param.getHospitalId() ,param.getPaperBatchNo());
        if(testPaper == null){
            throw new BusinessException("找不到匹配的质控试纸");
        }
        QualityControlLiquidPO liquid = this.qualityControlLiquidMapper.getQualityControlLiquidById(null ,param.getHospitalId() ,param.getLiquidBatchNo());
        if(liquid == null){
            throw new BusinessException("找不到匹配的质控液");
        }
        List<RangeConfigBO> rangeConfigList = JSON.parseArray(testPaper.getRangeConfig() ,RangeConfigBO.class);
        Optional<RangeConfigBO> optional = rangeConfigList.stream().filter(x -> x.getLevelCode() == liquid.getLevelCode()).findFirst();
        if(!optional.isPresent()){
            throw new BusinessException("质控试纸与质控液不匹配");
        }
        RangeConfigBO rangeConfig = optional.get();
        Integer measureResult = QualityControlConstant.MEASURE_RESULT_UN_PASS;
        Double measureValue = Double.parseDouble(param.getMeasureValue());
        if(measureValue >= rangeConfig.getRangeMin() && measureValue <= rangeConfig.getRangeMax()){
            measureResult = QualityControlConstant.MEASURE_RESULT_PASS;
        }
        MachinePO machine = this.machineService.getMachine(null ,param.getMachineSn() ,param.getMachineType());
        if(machine == null){
            throw new BusinessException("未录入的设备号，请确认");
        }
        if (measureResult == QualityControlConstant.MEASURE_RESULT_UN_PASS){
            MachinePO updateMachine = new MachinePO();
            updateMachine.setMachineId(machine.getMachineId());
            updateMachine.setMachineStatus(MachineConstant.MACHINE_STATUS_LOCK);
            machineService.updateMachine(updateMachine);
        }
        QualityControlPO add = new QualityControlPO();
        BeanUtils.copyProperties(add ,param);
        add.setSid(DaoHelper.getSeq());
        add.setMeasureResult(measureResult);
        add.setRangeMax(rangeConfig.getRangeMax().toString());
        add.setRangeMin(rangeConfig.getRangeMin().toString());
        add.setLevelCode(rangeConfig.getLevelCode());
        add.setLevelName(rangeConfig.getLevelName());
        add.setMachineId(machine.getMachineId());
        this.qualityControlMapper.addQualityControl(add);
        return measureResult;
    }

    /**
     * 获取默认的质控品属性配置
     * @return
     */
    private List<MaterialConfigBO> getDefaultMaterialConfig(){
        List<MaterialConfigBO> result = new ArrayList<>();
        MaterialConfigBO config = new MaterialConfigBO();
        config.setLevelCode(1);
        config.setLevelName("低浓度");
        result.add(config);

        config = new MaterialConfigBO();
        config.setLevelCode(2);
        config.setLevelName("中浓度");
        result.add(config);

        config = new MaterialConfigBO();
        config.setLevelCode(3);
        config.setLevelName("高浓度");
        result.add(config);
        return result;
    }
}
