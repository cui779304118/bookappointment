package com.cw.bookappointment.manually;

import com.cw.bookappointment.dao.IStudentInfoDao;
import com.cw.bookappointment.entity.StudentInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StudentInfoTest {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("/env/dev/applicationContext.xml");
//        IStudentInfoDao infoDao = (IStudentInfoDao) context.getBean(IStudentInfoDao.class);
//        StudentInfo info = new StudentInfo();
//        info.setSex(0);
//        info.setAge(24);
//        info.setClazz("研16-1班");
//        info.setMajor("地球探测与信息技术");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            info.setAdmissionDate(format.parse("2012-11-01"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        infoDao.save(info);
//        System.out.println(info.getStudentInfoId());

//        StudentInfo infoSel = infoDao.get(9);
//        System.out.println(infoSel);

        String da = "2012-11-02";
        System.out.println(da.matches("20[0-1][0-9]-[0-1][0-9]-[0-3][0-9]"));
    }
}
