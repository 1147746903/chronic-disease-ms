package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.po.BarrierQuesCfgPO;
import com.comvee.cdms.defender.wechat.po.BarrierQuesPO;

import java.util.List;
import java.util.Map;

/**
 * 知识挑战-关卡问题表(BarrierQuesPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-03 15:52:46
 */
public interface BarrierQuesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    BarrierQuesPO queryById(String sid);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param barrierQuesPO 实例对象
     * @return 对象列表
     */
    List<BarrierQuesPO> queryAll(BarrierQuesPO barrierQuesPO);

    //根据关卡id获取错误题目列表
    List<BarrierQuesCfgPO> queryErrorQuesByBarrierId(Map param);



    /**
     * 新增数据
     *
     * @param barrierQuesPO 实例对象
     * @return 影响行数
     */
    int insert(BarrierQuesPO barrierQuesPO);


    /**
     * 修改数据
     *
     * @param barrierQuesPO 实例对象
     * @return 影响行数
     */
    int update(BarrierQuesPO barrierQuesPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

