package com.comvee.cdms.complication.mapper;

import com.comvee.cdms.complication.model.dto.ScreeningMachineDTO;
import com.comvee.cdms.complication.model.po.ScreeningMachinePO;
import com.comvee.cdms.complication.model.vo.ScreeningMachineVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/6
 */
public interface ScreeningMachineMapper {

    /**
     * 添加筛查设备绑定记录
     * @param screeningMachinePO
     */
    void addScreeningMachine(ScreeningMachinePO screeningMachinePO);

    /**
     * 查找筛查设备绑定记录
     * @param machineSn
     * @param secretKey
     * @return
     */
    ScreeningMachinePO getScreeningMachine(@Param("machineSn") String machineSn ,@Param("secretKey") String secretKey);
    /**
     * 根据医院、医生、机器码进行模糊搜索
     * @param dto
     * @return
     */
    List<ScreeningMachinePO> listHospitalNameOrDoctorIdOrEquipmentNo(ScreeningMachineDTO dto);

    /**
     * 根据sid获取绑定数据
     * @param sid
     * @return
     */
    ScreeningMachinePO getScreeningMachineBySid(@Param("sid") String sid);

    /**
     * 修改授权管理
     * @param screeningMachineVO
     */
    void updateScreeningMachine(ScreeningMachineVO screeningMachineVO);
}
