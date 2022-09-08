package com.comvee.cdms.defender.mapper;

import com.comvee.cdms.defender.model.CourseQuesAnswerModel;

public interface CourseQuesAnswerMapper {

    void addCourseQuesAnswer (CourseQuesAnswerModel record);

    CourseQuesAnswerModel getCourseQuesAnswer(CourseQuesAnswerModel answerParam);

    //int updateByPrimaryKeySelective(CourseQuesAnswerModel record);

}