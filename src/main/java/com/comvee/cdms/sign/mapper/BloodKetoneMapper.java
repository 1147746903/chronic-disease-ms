package com.comvee.cdms.sign.mapper;


import com.comvee.cdms.sign.dto.AddBloodKetoneMapperDTO;
import com.comvee.cdms.sign.dto.GetBloodKetoneDTO;
import com.comvee.cdms.sign.dto.ListBloodKetoneDTO;
import com.comvee.cdms.sign.dto.UpdateBloodKetoneDTO;
import com.comvee.cdms.sign.po.BloodKetonePO;

import java.util.List;

/**
 * @author: 罗伟雄
 * @date: 2020/1/20
 */
public interface BloodKetoneMapper {


    Integer addBloodKetone(AddBloodKetoneMapperDTO dto);

    List<BloodKetonePO> listBloodKetone(ListBloodKetoneDTO listBloodKetoneDTO);

    BloodKetonePO getBloodKetone(GetBloodKetoneDTO getBloodKetoneDTO);

    Integer modifyBloodKetone(UpdateBloodKetoneDTO updateBloodKetoneDTO);

}
