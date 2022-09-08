package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.po.DictPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemDictMapper {

    void addDict(DictPO dict);

    int updateDict(DictPO dict);

    DictPO getDict(@Param("dictCode") String dictCode);

    List<DictPO> listDict(@Param("parentCode") String parentCode);
}
