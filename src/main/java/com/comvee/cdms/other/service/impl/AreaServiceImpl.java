package com.comvee.cdms.other.service.impl;

import com.comvee.cdms.other.mapper.AreaMapper;
import com.comvee.cdms.other.po.AreaPO;
import com.comvee.cdms.other.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Cacheable(value = "public", key = "'area' + #areaPid?:'86'")
    public List<AreaPO> listArea(String areaPid, Integer rank) {
        return this.areaMapper.listArea(areaPid, rank);
    }

    @Override
    public List<String> listAreaName(String areaPid, Integer rank) {
        return this.areaMapper.listAreaName(areaPid, rank);
    }
}
