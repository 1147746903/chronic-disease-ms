package com.comvee.cdms.prescription.contorller.mobile;

import com.comvee.cdms.common.wrapper.MobileRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.prescription.dto.PrescriptionDTO;
import com.comvee.cdms.prescription.dto.PrescriptionGetGroup;
import com.comvee.cdms.prescription.dto.PrescriptionIdGroup;
import com.comvee.cdms.prescription.service.PrescriptionServiceI;
import com.comvee.cdms.prescription.vo.PrescriptionDetailVO;
import com.comvee.cdms.prescription.vo.PrescriptionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author wangxy
 *
 */
@RestController
@RequestMapping("mobile/prescription/")
public class MobilePrescriptionContorller {
    private static final Logger logger = LoggerFactory.getLogger(MobilePrescriptionContorller.class);

    @Autowired
    @Qualifier("memberService")
    private MemberService memberService;

    @Autowired
    @Qualifier("prescriptionService")
    private PrescriptionServiceI prescriptionService;

    
    /**
     * 根据处方id，获取管理处方处方
     * @author 李左河
     * @date 2018/8/14 14:39
     * @param mr
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.model.Result
     *
     */
    @RequestMapping("getPrescriptionByIdApp")
    public Result getPrescriptionById(MobileRequest mr, @Validated(value = {PrescriptionIdGroup.class}) PrescriptionDTO prescriptionDTO) {
        PrescriptionVO vo = this.prescriptionService.getPrescriptionById(prescriptionDTO.getPrescriptionId());
        return new Result(vo);
    }
    
    /**
     * 根据模块获取处方模块内容
     * @author 李左河
     * @date 2018/8/14 14:38
     * @param mr
     * @param prescriptionDTO
     * @return com.comvee.cdms.common.model.Result
     *
     */
    @RequestMapping("loadPrescriptionDetailByModuleApp")
    public Result loadPrescriptionDetailByModule(MobileRequest mr, @Validated({PrescriptionGetGroup.class}) PrescriptionDTO prescriptionDTO) {
        PrescriptionDetailVO vo = this.prescriptionService.loadPrescriptionDetailByModule(prescriptionDTO);
        return new Result(vo);
    }

}
