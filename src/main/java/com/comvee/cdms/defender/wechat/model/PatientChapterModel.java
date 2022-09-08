package com.comvee.cdms.defender.wechat.model;

public class PatientChapterModel {
    private String  sid;

    private String pid;

    private String  courseId;
    
    private String patientCourseId;

    private String chapterId;

    private String status;

    private String score;
    
    private String answerJson;
    
    
    
	public PatientChapterModel() {
		super();
	}

	public PatientChapterModel(String pid, String courseId, String chapterId, String status, String score) {
		super();
		this.pid = pid;
		this.courseId = courseId;
		this.chapterId = chapterId;
		this.status = status;
		this.score = score;
	}



	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAnswerJson() {
		return answerJson;
	}

	public void setAnswerJson(String answerJson) {
		this.answerJson = answerJson;
	}

	public String getPatientCourseId() {
		return patientCourseId;
	}

	public void setPatientCourseId(String patientCourseId) {
		this.patientCourseId = patientCourseId;
	}
	
    
}