package com.comvee.cdms.tcm.mapper;

import com.comvee.cdms.tcm.model.dto.TcmCollectQueDTO;
import com.comvee.cdms.tcm.model.vo.TcmCollectQueVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TcmCollectQueMapper {
    List<TcmCollectQueVO> listTcmCollectQue(TcmCollectQueDTO dto);
}
