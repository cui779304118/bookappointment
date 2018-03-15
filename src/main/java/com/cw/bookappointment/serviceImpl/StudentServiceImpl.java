package com.cw.bookappointment.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cw.bookappointment.dao.IStudentDao;
import com.cw.bookappointment.entity.Student;
import com.cw.bookappointment.service.StudentService;
import com.cw.bookappointment.util.MD5Util;

public class StudentServiceImpl implements StudentService {

	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private IStudentDao studentDao;
	
	@Override
	public Integer addStudent(Student student) {
		String password = MD5Util.generateMD5(student.getPassword());
		if(StringUtils.isEmpty(password)){
			student.setPassword(password);
		}
		try {
			studentDao.save(student);
		} catch (Exception e) {
			logger.error("studentService: 插入student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return student.getId();
	}

	@Override
	public boolean deleteStudent(Integer id) {
		boolean isDelSuccess = false;
		try {
			studentDao.delete(id);
			isDelSuccess = true;
		} catch (Exception e) {
			logger.error("studentService: 删除student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return isDelSuccess;
	}

	@Override
	public boolean updateStudent(Student student) {
		boolean isUpdSuccess = false;
		try {
			studentDao.update(student);;
			isUpdSuccess = true;
		} catch (Exception e) {
			logger.error("studentService: 更新student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return isUpdSuccess;
	}

	@Override
	public Student queryByNumAndPassword(String studentNum, String password) {
		if(StringUtils.isEmpty(studentNum) || StringUtils.isEmpty(password)){
			return null;
		}
		String passwordMd5 = MD5Util.generateMD5(password);
		Student student = null;
		try {
			student = studentDao.queryByStuNumAndPassword(studentNum, passwordMd5);
		} catch (Exception e) {
			logger.error("studentService: 查询student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return student;
	}

	@Override
	public List<Student> listStudent(Student student) {
		List<Student> studentList = null;
		try {
			studentList = studentDao.list(student);
		} catch (Exception e) {
			logger.error("studentService: 查询student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return studentList;
	}

}
