package com.cw.bookappointment.manually;

import com.cw.bookappointment.entity.Student;
import com.cw.bookappointment.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class StudentDaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/env/dev/applicationContext.xml");
        StudentService studentService = context.getBean("studentService",StudentService.class);
        //测试插入
//        for (int i = 0; i < 7 ; i++) {
//            Student student = new Student();
//            student.setStudentNum("77930411"+ i);
//            student.setPassword("12345678" + i);
//            student.setStudentInfoId(201201123 + i);
//            int id = studentService.addStudent(student);
//            System.out.println(id);
//        }


        //测试查询
//        Student student1 = studentService.queryByNumAndPassword("779304118","123456789");
//        System.out.println(student1.getStudentInfoId());
        //

//        Student studentNew = new Student();
//        studentNew.setStudentNum("779304118");
//        studentNew.setPassword("987654321");
//        System.out.println(studentService.updateStudent(studentNew));

//        System.out.println(studentService.deleteStudent(1));
        List<Student> studentList = studentService.listStudent(new Student());
        System.out.println(Arrays.toString(studentList.toArray()));

    }

}
