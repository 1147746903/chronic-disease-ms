package com.comvee.cdms.other.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.other.mapper.SystemDictMapper;
import com.comvee.cdms.other.po.DictPO;
import com.comvee.cdms.other.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private SystemDictMapper dictMapper;

    @Override
    public String getDictValue(String dictCode) {
        DictPO dict = this.dictMapper.getDict(dictCode);;
        if(dict == null){
            return null;
        }
        return dict.getDictValue();
    }

    @Override
    public void updateDictValueByCode(String dictCode, String value) {
        DictPO result = this.dictMapper.getDict(dictCode);
        if(result == null){
            throw new BusinessException("找不到指定的字典配置");
        }
        result.setDictValue(value);
        this.dictMapper.updateDict(result);
    }
}
