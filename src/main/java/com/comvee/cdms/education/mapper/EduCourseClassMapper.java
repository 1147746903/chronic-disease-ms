package com.comvee.cdms.education.mapper;

import com.comvee.cdms.education.model.po.EduCourseClassPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 远程教育课程分类(EduCourseClassPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-27 11:49:55
 */
public interface EduCourseClassMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    EduCourseClassPO queryById(String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param eduCourseClassPO 实例对象
     * @return 对象列表
     */
    List<EduCourseClassPO> queryAll(EduCourseClassPO eduCourseClassPO);

    /**
     * 新增数据
     *
     * @param eduCourseClassPO 实例对象
     * @return 影响行数
     */
    int insert(EduCourseClassPO eduCourseClassPO);


    /**
     * 修改数据
     *
     * @param eduCourseClassPO 实例对象
     * @return 影响行数
     */
    int update(EduCourseClassPO eduCourseClassPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

