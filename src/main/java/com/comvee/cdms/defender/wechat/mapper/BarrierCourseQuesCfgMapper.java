package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.po.BarrierCourseQuesCfgPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识挑战-题库配置表(BarrierCourseQuesCfgPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-03 15:50:57
 */
public interface BarrierCourseQuesCfgMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    BarrierCourseQuesCfgPO queryById(String sid);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param barrierCourseQuesCfgPO 实例对象
     * @return 对象列表
     */
    List<BarrierCourseQuesCfgPO> queryAll(BarrierCourseQuesCfgPO barrierCourseQuesCfgPO);

    //获取除了quesIdList外的关联courseIdList的题库
    List<String> loadLeftCourseQues(@Param("courseIdList") List<String> courseIdList, @Param("quesIdList") List<String> quesIdList);

    /**
     * 新增数据
     *
     * @param barrierCourseQuesCfgPO 实例对象
     * @return 影响行数
     */
    int insert(BarrierCourseQuesCfgPO barrierCourseQuesCfgPO);

    /**
     * 修改数据
     *
     * @param barrierCourseQuesCfgPO 实例对象
     * @return 影响行数
     */
    int update(BarrierCourseQuesCfgPO barrierCourseQuesCfgPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

    int deleteByQId(@Param("barrierId") String barrierId);

}

