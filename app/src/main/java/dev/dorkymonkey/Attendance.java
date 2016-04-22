package dev.dorkymonkey;

import java.io.Serializable;

public class Attendance implements Serializable {
	private String checkType; 
	private String date;
	private String course_code;
	private String course;

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCourseCode() {
		return course_code;
	}

	public void setCourseCode(String courseCode) {
		this.course_code = courseCode;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}
