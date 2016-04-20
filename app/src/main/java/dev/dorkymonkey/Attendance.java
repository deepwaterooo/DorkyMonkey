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

	public double getCourseCode() {
		return course_code;
	}

	public void setCourseCode(double courseCode) {
		this.course_code = courseCode;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}
}
