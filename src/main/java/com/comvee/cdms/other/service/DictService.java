package com.comvee.cdms.other.service;

public interface DictService {

    /**
     * 根据code加载字典值
     * @param dictCode
     * @return
     */
    String getDictValue(String dictCode);

    /**
     * 通过code修改字段值
     * @param dictCode
     * @param value
     */
    void updateDictValueByCode(String dictCode, String value);
}
