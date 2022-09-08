package com.comvee.cdms.education.mapper;

import com.comvee.cdms.education.model.po.EduDoctorCollectPO;
import com.comvee.cdms.education.model.vo.ListEduCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 远程教育患者课程收藏(EduDoctorCollectPO)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-27 10:03:13
 */
public interface EduDoctorCollectMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sid 主键
     * @return 实例对象
     */
    EduDoctorCollectPO queryById(String sid);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param eduDoctorCollectPO 实例对象
     * @return 对象列表
     */
    List<EduDoctorCollectPO> queryAll(EduDoctorCollectPO eduDoctorCollectPO);

    List<ListEduCourseVO> loadDoctorCollect(@Param("doctorId") String doctorId);

    /**
     * 新增数据
     *
     * @param eduDoctorCollectPO 实例对象
     * @return 影响行数
     */
    int insert(EduDoctorCollectPO eduDoctorCollectPO);


    /**
     * 修改数据
     *
     * @param eduDoctorCollectPO 实例对象
     * @return 影响行数
     */
    int update(EduDoctorCollectPO eduDoctorCollectPO);

    /**
     * 通过主键删除数据
     *
     * @param sid 主键
     * @return 影响行数
     */
    int deleteById(String sid);

}

