package com.cw.bookappointment.entity;

public class Student {
	private Integer studentId;
	private String studentNum;
	private Integer studentInfoId;
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public Integer getStudentInfoId() {
		return studentInfoId;
	}
	public void setStudentInfoId(Integer studentInfoId) {
		this.studentInfoId = studentInfoId;
	}
	private String password;
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
