package com.comvee.cdms.prescription.service;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.prescription.bo.PrescriptionBO;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.GetPrescriptionDetailDTO;
import com.comvee.cdms.prescription.dto.GetPrescriptionSugarmonitorDTO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.mapper.PrescriptionMapper;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.vo.PrescriptionForSugarmonitorVO;
import com.comvee.cdms.prescription.vo.PrescriptionSugarmonitorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("prescriptionOfSugarmonitorService")
public class PrescriptionOfSugarmonitorServiceImpl implements PrescriptionOfSugarmonitorServiceI {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionServiceI prescriptionService;

    @Override
    public PrescriptionForSugarmonitorVO intelligentRecommendationOfSugarmonitor(String baseJson) {
        PrescriptionForSugarmonitorVO model = new PrescriptionForSugarmonitorVO();
        GetPrescriptionSugarmonitorDTO sugarmonitorOut = JsonSerializer.jsonToObject(baseJson, GetPrescriptionSugarmonitorDTO.class);
        //智能推荐方案
        List<PrescriptionSugarmonitorVO> list = prescriptionService.loadEohSugarmonitorByItem(sugarmonitorOut);
        if(null!=list && list.size()>0){
        	 PrescriptionSugarmonitorVO vo = list.get(0);
        	 String reJson = JsonSerializer.objectToJson(vo);
        	 model.setSugarmonitorInfo(reJson);
        }
        return model;
    }

    @Override
    public PrescriptionForSugarmonitorVO getPrescriptionSugarmonitor(String prescriptionId) {
    	PrescriptionForSugarmonitorVO model = new PrescriptionForSugarmonitorVO();
        GetPrescriptionDetailDTO dto = new GetPrescriptionDetailDTO();
        dto.setPrescriptionId(prescriptionId);
        dto.setType(PrescriptionConstant.MODULE_TYPE_MONITOR);
        PrescriptionDetailPO detail = this.prescriptionMapper.getPrescriptionDetailByParam(dto);
        model.setSaveState(detail.getSaveState());
        model.setSid(detail.getSid());
        model.setSugarmonitorInfo(detail.getDetailJson());
        return model;
    }


    @Override
    public void modifySugarmonitor(PrescriptionDTO prescriptionDTO){

        PrescriptionBO prescriptionBO = new PrescriptionBO();
        BeanUtils.copyProperties(prescriptionBO,prescriptionDTO);
        prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }

}
