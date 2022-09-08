package com.comvee.cdms.visit.mapper;


import com.comvee.cdms.visit.dto.GetListVisitEventDTO;
import com.comvee.cdms.visit.po.VisitEventPO;
import com.comvee.cdms.visit.vo.ListVisitEventVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 就诊事件表(VisitEventPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-25 14:39:27
 */
public interface VisitEventMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    VisitEventPO queryById(@Param("sid") String sid);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param VisitEventPO 实例对象
     * @return 对象列表
     */
    List<VisitEventPO> queryAll(VisitEventPO visitEventPO);

    /**
     * 新增数据
     *
     * @param tVisitEvent 实例对象
     * @return 影响行数
     */
    int insert(VisitEventPO tVisitEvent);


    List<ListVisitEventVO> listVisitEvent(GetListVisitEventDTO getListVisitEventDTO);



}

