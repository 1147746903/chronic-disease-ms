package com.comvee.cdms.dybloodsugar.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.dto.DyBloodManagementDTO;
import com.comvee.cdms.dybloodsugar.mapper.DYYPBloodSugarPOMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyApplicationMapper;
import com.comvee.cdms.dybloodsugar.mapper.DyBloodManagementMapper;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.po.DyApplicationMachinePO;
import com.comvee.cdms.dybloodsugar.po.DyApplicationPO;
import com.comvee.cdms.dybloodsugar.po.DyBloodManagementPO;
import com.comvee.cdms.dybloodsugar.service.DyBloodManagementService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DyBloodManagementService")
public class DyBloodManagementImpl implements DyBloodManagementService {

    @Autowired
    private DyBloodManagementMapper dyBloodManagementMapper;
    @Autowired
    private DyApplicationMapper dyApplicationMapper;
    @Autowired
    private DYYPBloodSugarPOMapper dyypBloodSugarPOMapper;

    @Override
    public PageResult<DyBloodManagementPO> listHospitalNameAndEquipmentNo(PageRequest pr, DyBloodManagementDTO dto) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        PageResult<DyBloodManagementPO> pageResult = new PageResult<>();
        List<DyBloodManagementPO> dyBloodManagementPOS = this.dyBloodManagementMapper.getBloodManagementByHospitalIdAndEquipment(dto);
        pageResult =  new PageResult<DyBloodManagementPO>(dyBloodManagementPOS);
        if (pageResult != null && pageResult.getRows().size() > 0){
            for (DyBloodManagementPO managementPO : pageResult.getRows() ){
                DYYPBloodSugarPO dyypBloodSugarPO = this.dyypBloodSugarPOMapper.getMachineNoLastUseDt(managementPO.getEquipmentNo());
                if (dyypBloodSugarPO != null){
                    managementPO.setLastUseDt(DateHelper.dateToString(dyypBloodSugarPO.getRecordTime()));
                }
            }
        }
        return new PageResult<>(dyBloodManagementPOS);
    }

    @Override
    public Result addHospitalNameAndEquipmentNo(DyBloodManagementDTO dto) {
        //根绝设备号查询,如果这个设备号被绑定过,则不能被任何医院再次绑定(绑定期间)
        Result result = new Result();
        DyBloodManagementPO management = this.dyBloodManagementMapper.getManagementBySid(dto.getSid());
        if (management!=null){
            try{
                //编辑修改操作
                management = new DyBloodManagementPO();
                management.setEquipmentNo(dto.getEquipmentNo());
                management.setOutBoundDt(dto.getOutBoundDt());
                management.setRemark(dto.getRemark());
                management.setEquipmentType(dto.getEquipmentType());
                management.setSid(dto.getSid());
                this.dyBloodManagementMapper.updateBloodManagement(management);
                updateBindEquipment(dto);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            //根绝设备号查询,如果这个设备号被绑定过,则不能被任何医院再次绑定(绑定期间)
            DyBloodManagementPO managementPO = this.dyBloodManagementMapper.getBloodManagement(dto);
            if (managementPO != null){
                throw new BusinessException("该设备号已经被绑定! 请重新输入...");
            }else{
                try {
                    //说明没有被绑定过
                    managementPO = new DyBloodManagementPO();
                    BeanUtils.copyProperties(managementPO,dto);
                    String sid = DaoHelper.getSeq();
                    managementPO.setSid(sid);
                    DYYPBloodSugarPO dyypBloodSugarPO = this.dyypBloodSugarPOMapper.getMachineNoLastUseDt(managementPO.getEquipmentNo());
                    if (dyypBloodSugarPO != null){
                        managementPO.setLastUseDt(DateHelper.dateToString(dyypBloodSugarPO.getRecordTime()));
                    }
                    this.dyBloodManagementMapper.addHospitalNameAndEquipmentNo(managementPO);
                    BindEquipment(dto);//绑定操作
                    result.setCode("0000");
                    result.setMessage("医院绑定设备保存成功!");
                }catch (Exception e){
                    result.setCode("8888");
                    result.setMessage("医院绑定设备保存失败!");
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Transactional
    public void updateBindEquipment(DyBloodManagementDTO dto) {
        //对dy_application表做修改操作
        DyApplicationPO dyApplicationPO = new DyApplicationPO();
        dyApplicationPO.setAppId(dto.getHospitalId());
        dyApplicationPO.setDescription(dto.getRemark());
        this.dyApplicationMapper.updateDyApplicationPO(dyApplicationPO);
        //修改dy_application_machine绑定表中的数据.
        DyApplicationMachinePO dyApplicationMachinePO = new DyApplicationMachinePO();
        dyApplicationMachinePO.setMachineNo(dto.getEquipmentNo());
        dyApplicationMachinePO.setAppId(dto.getHospitalId());
        this.dyApplicationMapper.updateDyApplicationMachinePO(dyApplicationMachinePO);
    }

    @Transactional
    public void BindEquipment(DyBloodManagementDTO dto) {
        //对dy_application表做保存操作
        List<DyApplicationPO> list = this.dyApplicationMapper.getDyApplicationPOByAppId(dto.getHospitalId());
        if (list == null || list.size() == 0){ //为空说明新绑定.
            DyApplicationPO dyApplicationPO = new DyApplicationPO();

            dyApplicationPO.setAppId(dto.getHospitalId());
            String secretKey = this.urlArgStringHandler(dto.getEquipmentNo());
            dyApplicationPO.setSecretKey(secretKey);
            dyApplicationPO.setDescription(dto.getRemark());
            if (dto.getWhileList() == null || "".equals(dto.getWhileList())){
                dyApplicationPO.setWhileList("*");
            }else{
                dyApplicationPO.setWhileList(dto.getWhileList());
            }
            dyApplicationPO.setPushUrl(dto.getPushUrl());
            this.dyApplicationMapper.addDyApplication(dyApplicationPO);
        }
        //对dy_application_machine做保存操作
        DyApplicationMachinePO dyApplicationMachinePO = new DyApplicationMachinePO();
        dyApplicationMachinePO.setSid(DaoHelper.getSeq());
        dyApplicationMachinePO.setAppId(dto.getHospitalId());
        dyApplicationMachinePO.setMachineNo(dto.getEquipmentNo());
        this.dyApplicationMapper.addApplicationMachine(dyApplicationMachinePO);
    }

    /**
     * 参数串处理
     * @param machineId
     * @return
     */
    private String urlArgStringHandler(String machineId){
        long times = System.currentTimeMillis();
        String acode = MD5Util.md5("platCode=HLJTRJ0012&innerCode="+machineId+"&machineType=02&timestamp="+times+"&comvee&comvee");
//        String argStr = "innerCode="+machineId+"&platCode=HLJTRJ0012&machineType=02&timestamp="+times+"&acode="+acode;
        return acode;
    }
    @Override
    public DyBloodManagementPO getManagementBySid(String sid) {
        DyBloodManagementPO dyBloodManagementPO = this.dyBloodManagementMapper.getManagementBySid(sid);
        return dyBloodManagementPO;
    }

    @Override
    public void deleteManagementBySid(String sid) {
        this.dyBloodManagementMapper.deleteManagementBySid(sid);
        //查询dy_blood_management表数据
        DyBloodManagementPO dyBloodManagementPO = this.dyBloodManagementMapper.getManagementBySid(sid);
        if (dyBloodManagementPO != null){
            //解绑(物理删除)
            this.dyApplicationMapper.deleteApplicationMachineBySid(dyBloodManagementPO.getEquipmentNo());
        }
    }

    @Override
    public List<DyBloodManagementPO> listHospital() {
        List<DyBloodManagementPO> list = this.dyBloodManagementMapper.listHospitals();
        return list;
    }


}
