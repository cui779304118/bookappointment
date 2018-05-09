package com.cw.bookappointment.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.entity.Student;
import com.cw.bookappointment.entity.StudentInfo;

public interface StudentService {

	/**
	 * 
	 * @param student
	 * @return
	 */
	public Integer addStudent(Student student);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteStudent(Integer id);
	/**
	 * 
	 * @param student
	 * @return
	 */
	public boolean updateStudent(Student student);
	/**
	 * 
	 * @param sutdentNum
	 * @param password
	 * @return
	 */
	public Student queryByNumAndPassword(String sutdentNum, String password); 
	/**
	 * 
	 * @param student
	 * @return
	 */
	public List<Student> listStudent(Student student);

	/**
	 *
	 * @param studentInfo
	 * @return
	 */
	public int insertStudentInfo(StudentInfo studentInfo);

	/**
	 *
	 * @param studentInfoId
	 * @return
	 */
	public StudentInfo getStudentInfo(int studentInfoId);
}
