package com.comvee.cdms.defender.wechat.mapper;

import com.comvee.cdms.defender.wechat.po.MemberCourseBarrierPO;

import java.util.List;

/**
 * 知识挑战-用户课程表(MemberCourseBarrierPO)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-30 10:22:48
 */
public interface MemberCourseBarrierMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    MemberCourseBarrierPO queryById(String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param memberCourseBarrierPO 实例对象
     * @return 对象列表
     */
    List<MemberCourseBarrierPO> queryAll(MemberCourseBarrierPO memberCourseBarrierPO);

    /**
     * 新增数据
     *
     * @param memberCourseBarrierPO 实例对象
     * @return 影响行数
     */
    int insert(MemberCourseBarrierPO memberCourseBarrierPO);


    /**
     * 修改数据
     *
     * @param memberCourseBarrierPO 实例对象
     * @return 影响行数
     */
    int update(MemberCourseBarrierPO memberCourseBarrierPO);


}

