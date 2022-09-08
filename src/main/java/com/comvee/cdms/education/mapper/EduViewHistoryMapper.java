package com.comvee.cdms.education.mapper;

import com.comvee.cdms.education.model.po.EduCoursePO;
import com.comvee.cdms.education.model.po.EduViewHistoryPO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 远程教育课程观看历史(EduViewHistoryPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-27 09:59:23
 */
public interface EduViewHistoryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    EduViewHistoryPO queryById(@Param("sid") String sid);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param eduViewHistoryPO 实例对象
     * @return 对象列表
     */
    List<EduViewHistoryPO> queryAll(EduViewHistoryPO eduViewHistoryPO);

    List<ListEduCourseVO> loadDoctorViewHistory(@Param("doctorId") String doctorId);

    /**
     * 新增数据
     *
     * @param eduViewHistoryPO 实例对象
     * @return 影响行数
     */
    int insert(EduViewHistoryPO eduViewHistoryPO);


    /**
     * 修改数据
     *
     * @param eduViewHistoryPO 实例对象
     * @return 影响行数
     */
    int update(EduViewHistoryPO eduViewHistoryPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(@Param("sid") String sid);

}

