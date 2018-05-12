package com.cw.bookappointment.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.dao.IStudentInfoDao;
import com.cw.bookappointment.entity.StudentInfo;
import com.cw.bookappointment.util.ResultOperationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cw.bookappointment.dao.IStudentDao;
import com.cw.bookappointment.entity.Student;
import com.cw.bookappointment.service.StudentService;
import com.cw.bookappointment.util.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private IStudentDao studentDao;

	@Autowired
	private IStudentInfoDao studentInfoDao;

	public JSONObject resetPassword(JSONObject data) {
		String studentNum = data.getString("studentNum");
		String clazz = data.getString("clazz");
		String major = data.getString("major");

		StudentInfo infoQue = studentInfoDao.getByStudentNum(studentNum);
		if(null == infoQue || !clazz.equals(infoQue.getClazz()) || !major.equals(infoQue.getMajor())){
			return ResultOperationUtil.generateFailResult("认证失败！");
		}
		return ResultOperationUtil.generateCodeResult(0, "认证成功！");
	}

	public JSONObject updatePassword(JSONObject data, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String studentNum = (String) session.getAttribute("studentNum");
		String password = data.getString("password");
		String newPassword = data.getString("newPassword");

		Student student = queryByNumAndPassword(studentNum, password);
		if(student == null){
			return ResultOperationUtil.generateFailResult("密码错误，请重新输入！");
		}
		student.setPassword(newPassword);
		updateStudent(student);
		return ResultOperationUtil.generateCodeResult(0, "更新成功！");
	}

	public JSONObject login(JSONObject data, HttpServletRequest request) {
		String studentNum = data.getString("studentNum");
		Student student = queryByNumAndPassword(studentNum,data.getString("password"));
		if(null == student){
			return ResultOperationUtil.generateFailResult("用户名或密码错误！");
		}
		HttpSession session = request.getSession();
		session.setAttribute("studentNum", studentNum);
		return ResultOperationUtil.generateCodeResult(0, "恭喜您，登陆成功！");
	}

	public JSONObject register(JSONObject data, HttpServletRequest request) {
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setClazz(data.getString("clazz"));
		studentInfo.setMajor(data.getString("major"));
		studentInfo.setAge(data.getInteger("age"));
		studentInfo.setSex(data.getInteger("sex"));
		studentInfo.setStudentNum(data.getString("studentNum"));
		try {
			studentInfo.setAdmissionDate(parse2Date(data.getString("admissionDate")));
		}catch (ParseException e){
			logger.error("日期解析错误！");
			return  ResultOperationUtil.generateFailResult("参数异常！");
		}
		int studentInfoId = insertStudentInfo(studentInfo);
		if(studentInfoId == 0){
			return ResultOperationUtil.generateFailResult("服务器异常！");
		}
		Student student = new Student();
		student.setStudentNum(data.getString("studentNum"));
		student.setPassword(data.getString("password"));
		student.setStudentInfoId(studentInfoId);
		int studentId = addStudent(student);
		if (studentId == 0){
			return  ResultOperationUtil.generateFailResult("服务器异常！");
		}
		HttpSession session = request.getSession();
		session.setAttribute("studentNum",data.getString("studentNum"));
		return ResultOperationUtil.generateCodeResult(0,"恭喜您，注册成功！");
	}

	public Integer addStudent(Student student) {
		String password = MD5Util.generateMD5(student.getPassword());
		if(StringUtils.isEmpty(password)){
			return  0;
		}
		student.setPassword(password);
		try {
			studentDao.save(student);
			return student.getId();
		} catch (Exception e) {
			logger.error("studentService: 插入student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return  0;
	}

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

	public boolean updateStudent(Student student) {
		boolean isUpdSuccess = false;
		String password = MD5Util.generateMD5(student.getPassword());
		if(!StringUtils.isEmpty(password)){
			student.setPassword(password);
		}
		try {
			studentDao.update(student);;
			isUpdSuccess = true;
		} catch (Exception e) {
			logger.error("studentService: 更新student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return isUpdSuccess;
	}

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

	public List<Student> listStudent(Student student) {
		List<Student> studentList = null;
		try {
			studentList = studentDao.list(student);
		} catch (Exception e) {
			logger.error("studentService: 查询student异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return studentList;
	}

	public int insertStudentInfo(StudentInfo studentInfo) {
		if(null == studentInfo) return  0;
		try{
			studentInfoDao.save(studentInfo);
			return studentInfo.getStudentInfoId();
		}catch (Exception e){
			logger.error("studentService: 插入studentInfo异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return 0;
	}

	public StudentInfo getStudentInfo(int studentInfoId) {
		StudentInfo studentInfo = null;
		try{
			studentInfo = studentInfoDao.get(studentInfoId);
			return studentInfo;
		}catch (Exception e){
			logger.error("studentService: 插入studentInfo异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return studentInfo;
	}

	private Date parse2Date(String dateString) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return  format.parse(dateString);
	}
}
