package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.po.BarrierQuesCfgPO;
import com.comvee.cdms.defender.wechat.vo.BarrierQuesCfgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识挑战-题库表(BarrierQuesCfgPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-03 15:53:58
 */
public interface BarrierQuesCfgMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    BarrierQuesCfgPO queryById(@Param("sid") String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param barrierQuesCfgPO 实例对象
     * @return 对象列表
     */
    List<BarrierQuesCfgPO> queryAll(BarrierQuesCfgPO barrierQuesCfgPO);

    List<BarrierQuesCfgVO> listAllQuesCfg(@Param("title") String title);

    BarrierQuesCfgVO getQuesCfgById(@Param("sid") String sid);

    /**
     * 新增数据
     *
     * @param barrierQuesCfgPO 实例对象
     * @return 影响行数
     */
    int insert(BarrierQuesCfgPO barrierQuesCfgPO);


    /**
     * 修改数据
     *
     * @param barrierQuesCfgPO 实例对象
     * @return 影响行数
     */
    int update(BarrierQuesCfgPO barrierQuesCfgPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

