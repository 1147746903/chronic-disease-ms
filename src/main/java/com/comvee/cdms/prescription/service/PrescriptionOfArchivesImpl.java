package com.comvee.cdms.prescription.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.prescription.bo.ApiMemberEverydaySugarBO;
import com.comvee.cdms.prescription.bo.CheckoutResultBO;
import com.comvee.cdms.prescription.bo.PrescriptionBO;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.GetPrescriptionDetailDTO;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.po.PrescriptionDetailPO;
import com.comvee.cdms.prescription.vo.PrescriptionArchivesVO;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李左河
 * @date 2018/8/20 14:50.
 */
@Service("prescriptionOfArchives")
public class PrescriptionOfArchivesImpl implements PrescriptionOfArchivesI {

    @Autowired
    private PrescriptionApiI prescriptionApi;

    @Autowired
    private PrescriptionServiceI prescriptionService;

    @Override
    public PrescriptionDetailVO<PrescriptionArchivesVO> getPrescriptionArchives(PrescriptionDTO prescriptionDTO) {

        PrescriptionDetailVO<PrescriptionArchivesVO> model = new PrescriptionDetailVO<>();
        model.setModuleType(prescriptionDTO.getType());
        model.setModuleName(PrescriptionConstant.MODULE_NAME_ARCHIVES);

        //1、获取患者档案模块
        GetPrescriptionDetailDTO dto = new GetPrescriptionDetailDTO();
        dto.setPrescriptionId(prescriptionDTO.getPrescriptionId());
        dto.setType(prescriptionDTO.getType());
        PrescriptionDetailPO detail = this.prescriptionService.getPrescriptionDetailByParam(dto);

        if (null != detail) {
            PrescriptionArchivesVO vo = new PrescriptionArchivesVO();
            if (0 == detail.getSaveState() || StringUtils.isBlank(detail.getDetailJson())) {
                fillArchives(prescriptionDTO.getPrescriptionId(), vo);
            } else {
                vo = JSONObject.parseObject(detail.getDetailJson(), PrescriptionArchivesVO.class);
            }
            model.setModuleSid(detail.getSid());
            model.setModule(vo);
            model.setSaveState(detail.getSaveState());
        }
        return model;
    }

    @Override
    public void modifyPrescriptionArchives(PrescriptionDTO prescriptionDTO) {
        PrescriptionBO prescriptionBO = new PrescriptionBO();
        BeanUtils.copyProperties(prescriptionBO, prescriptionDTO);
        this.prescriptionService.modifyPrescriptionDetail(prescriptionBO);
    }

    /**
     * 从档案中获取 管理处方患者档案
     * @author 李左河
     * @date 2018/8/20 16:59
     * @param prescriptionId
     * @param vo
     * @return void
     *
     */
    private void fillArchives(String prescriptionId, PrescriptionArchivesVO vo) {
        //1、获取主表信息
        PrescriptionVO prescriptionVO = this.prescriptionService.getPrescriptionById(prescriptionId);
        if (null != prescriptionVO) {
            //2、获取患者 基本体征信息
            CheckoutResultBO memberCheckoutResult = this.prescriptionApi.getMemberCheckoutResultOfEoh(prescriptionVO.getMemberId());
            if (null != memberCheckoutResult) {
                BeanUtils.copyProperties(vo,memberCheckoutResult);
            }

            //3、获取 血糖情况
            ApiMemberEverydaySugarBO sugarPO = this.prescriptionApi.getMemberEverydaySugarNewByMemberId(prescriptionVO.getMemberId());
            if (null != sugarPO) {
                String paramValueJson = sugarPO.getParamValueJson();
                if (!StringUtils.isBlank(paramValueJson)) {
                    List<Map<String, Object>> mapList = JsonSerializer.jsonToMapList(paramValueJson);
                    if (null != mapList && mapList.size() > 0) {
                        for (Map<String,Object> map: mapList) {
                            String paramCode = map.get("paramCode").toString();
                            if (!StringUtils.isBlank(paramCode)) {
                                switch (paramCode) {
                                    case "beforedawn":
                                        vo.setBeforedawn(map.get("value").toString());
                                        continue;
                                    case "beforeBreakfast":
                                        vo.setBeforeBreakfast(map.get("value").toString());
                                        continue;
                                    case "afterBreakfast":
                                        vo.setAfterBreakfast(map.get("value").toString());
                                        continue;
                                    case "beforeLunch":
                                        vo.setBeforeLunch(map.get("value").toString());
                                        continue;
                                    case "afterLunch":
                                        vo.setAfterLunch(map.get("value").toString());
                                        continue;
                                    case "beforeDinner":
                                        vo.setBeforeDinner(map.get("value").toString());
                                        continue;
                                    case "afterDinner":
                                        vo.setAfterDinner(map.get("value").toString());
                                        continue;
                                    case "beforeSleep":
                                        vo.setBeforeSleep(map.get("value").toString());
                                        continue;
                                    default:
                                        continue;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
