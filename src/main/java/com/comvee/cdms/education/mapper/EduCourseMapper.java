package com.comvee.cdms.education.mapper;

import com.comvee.cdms.education.model.dto.ListEduCourseDTO;
import com.comvee.cdms.education.model.po.EduCoursePO;
import com.comvee.cdms.education.model.vo.DetailEduCourseVO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 远程教育课程(EduCoursePO)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-27 09:58:24
 */
public interface EduCourseMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    EduCoursePO queryById(String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param eduCoursePO 实例对象
     * @return 对象列表
     */
    List<EduCoursePO> queryAll(EduCoursePO eduCoursePO);

    List<ListEduCourseVO> listEduCourseVO(ListEduCourseDTO listEduCourseDTO);

    List<ListEduCourseVO> listRecEduCourseVO(@Param("courseId") String courseId, @Param("classId") String classId);

    DetailEduCourseVO loadEduCourseDetail(@Param("courseId") String courseId);

    /**
     * 新增数据
     *
     * @param eduCoursePO 实例对象
     * @return 影响行数
     */
    int insert(EduCoursePO eduCoursePO);


    /**
     * 修改数据
     *
     * @param eduCoursePO 实例对象
     * @return 影响行数
     */
    int update(EduCoursePO eduCoursePO);

    int updateCourse(EduCoursePO eduCoursePO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

