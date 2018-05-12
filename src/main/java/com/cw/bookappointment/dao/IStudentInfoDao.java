package com.cw.bookappointment.dao;

import com.cw.bookappointment.entity.StudentInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentInfoDao extends  IGenericDao<StudentInfo, Integer> {
    public StudentInfo getByStudentNum(@Param("studentNum") String studentNum);
}
