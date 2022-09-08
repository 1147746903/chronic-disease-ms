package com.comvee.cdms.defender.mapper;

import com.comvee.cdms.defender.model.PatientCourseFilesAnswerModel;

public interface PatientCourseFilesAnswerMapper {

    void addPatientCourseFilesAnswer(PatientCourseFilesAnswerModel record);

    PatientCourseFilesAnswerModel getPatientCourseFilesAnswer(PatientCourseFilesAnswerModel model);

}