package com.comvee.cdms.education.mapper;


import com.comvee.cdms.education.model.po.EduCourseCommentPO;
import com.comvee.cdms.education.model.vo.ListEduCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 远程教育课程评论(EduCourseCommentPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-28 09:58:24
 */
public interface EduCourseCommentMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    EduCourseCommentPO queryById(String sid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param eduCourseCommentPO 实例对象
     * @return 对象列表
     */
    List<EduCourseCommentPO> queryAll(EduCourseCommentPO eduCourseCommentPO);

    List<ListEduCommentVO> loadEduCommentVOList(EduCourseCommentPO eduCourseCommentPO);

    List<ListEduCommentVO> loadEduCommentByCourseId(@Param("courseId") String courseId, @Param("lever") Integer lever);

    /**
     * 新增数据
     *
     * @param eduCourseCommentPO 实例对象
     * @return 影响行数
     */
    int insert(EduCourseCommentPO eduCourseCommentPO);

    /**
     * 修改数据
     *
     * @param eduCourseCommentPO 实例对象
     * @return 影响行数
     */
    int update(EduCourseCommentPO eduCourseCommentPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

