package com.comvee.cdms.sign.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.sign.dto.AddBloodKetoneServiceDTO;
import com.comvee.cdms.sign.dto.ListBloodKetoneDTO;
import com.comvee.cdms.sign.po.BloodKetonePO;

import java.util.List;

/**
 * @author: 罗伟雄
 * @date: 2021/1/20
 */
public interface BloodKetoneService {

    /**
     * 手动添加血酮值
     * @param dto
     * @return
     */
    String addBloodKetone(AddBloodKetoneServiceDTO dto);

    /**
     * 分页查询血酮记录
     * @param listBloodKetoneDTO
     * @return
     */
    PageResult<BloodKetonePO> listBloodKetone(ListBloodKetoneDTO listBloodKetoneDTO, PageRequest pr);

    /**
     * 查询血酮记录
     * @param listBloodKetoneDTO
     * @return
     */
    List<BloodKetonePO> bloodKetoneList(ListBloodKetoneDTO listBloodKetoneDTO);
}
