package com.cw.bookappointment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cw.bookappointment.entity.PageModel;
import com.cw.bookappointment.entity.Student;
@Repository
public interface IStudentDao extends IGenericDao<Student, Integer> {
	public List<Student> listByPage(PageModel pageModel);
	public Student queryByStuNumAndPassword(@Param("studentNum") String studentNum,@Param("password")String password);
}
