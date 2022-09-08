package com.comvee.cdms.other.service;

import com.comvee.cdms.other.po.AreaPO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface AreaService {

    List<AreaPO> listArea(String areaPid, Integer rank);

    List<String> listAreaName(String areaPid, Integer rank);
}
