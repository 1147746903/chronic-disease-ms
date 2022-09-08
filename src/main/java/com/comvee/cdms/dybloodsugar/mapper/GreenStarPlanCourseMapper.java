package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.GreenStarPlanCoursePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GreenStarPlanCourseMapper {
    void addCourse(GreenStarPlanCoursePO po);
    void updateCourse(GreenStarPlanCoursePO po);
    List<GreenStarPlanCoursePO> listCourse();
    List<GreenStarPlanCoursePO> listCourseByDate(Integer dateIndex);
    GreenStarPlanCoursePO getCourseById(String sid);
    Integer countDailyCourse(Integer dateIndex);
}
