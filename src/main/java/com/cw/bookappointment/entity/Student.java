package com.cw.bookappointment.entity;

public class Student {
	private Integer id;
	private String studentNum;
	private Integer studentInfoId;
	private String password;
	
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
