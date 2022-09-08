package com.comvee.cdms.other.mapper;

import com.comvee.cdms.other.po.AreaPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/18
 */
public interface AreaMapper {

    /**
     * 加载区域信息
     * @param areaPid
     * @param rank
     * @return
     */
    List<AreaPO> listArea(@Param("areaPid") String areaPid,@Param("rank") Integer rank);

    List<String> listAreaName(@Param("areaPid") String areaPid,@Param("rank") Integer rank);
}
