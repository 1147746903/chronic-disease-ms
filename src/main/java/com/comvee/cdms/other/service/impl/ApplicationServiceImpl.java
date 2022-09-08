package com.comvee.cdms.other.service.impl;

import com.comvee.cdms.other.constant.DictConstant;
import com.comvee.cdms.other.service.ApplicationService;
import com.comvee.cdms.other.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private DictService dictService;

    @Override
    @CacheEvict(value = "public" ,key = "'wxMiniVersion'")
    public void updateWxMiniVersion(String version) {
        this.dictService.updateDictValueByCode(DictConstant.WX_MINI_VERSION ,version);
    }

    @Override
    @Cacheable(value = "public" ,key = "'wxMiniVersion'")
    public String getWxMiniVersion() {
        return this.dictService.getDictValue(DictConstant.WX_MINI_VERSION);
    }

    @Override
    //@Cacheable(value = "public" ,key = "'wxQuickLogin'")
    public String getWxQuickLogin() {
        return this.dictService.getDictValue(DictConstant.WX_QUICK_LOGIN);
    }
}
