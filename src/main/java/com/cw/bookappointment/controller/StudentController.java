package com.cw.bookappointment.controller;


import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.entity.Student;
import com.cw.bookappointment.entity.StudentInfo;
import com.cw.bookappointment.service.StudentService;
import com.cw.bookappointment.util.EnvMyBean;
import com.cw.bookappointment.util.ResultOperationUtil;
import com.cw.bookappointment.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private EnvMyBean envMyBean;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody JSONObject data, HttpServletRequest request){
        String studentNum = data.getString("studentNum");
        String password = data.getString("password");
        Integer age = data.getInteger("age");
        Integer sex = data.getInteger("sex");
        String clazz = data.getString("clazz");
        String major = data.getString("major");
        String admissionDate = data.getString("admissionDate");
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");
        if(StringUtils.isEmpty(studentNum) || StringUtils.isEmpty(password) || StringUtils.isEmpty(clazz)
                || StringUtils.isEmpty(major) || !admissionDate.matches("20[0-1][0-9]-[0-1][0-9]-[0-3][0-9]")
                || StringUtils.isEmpty(sign) || null == age || null == sex || null == timeStamp){
            logger.error("注册接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }
//        //校验秘钥是否通过
//        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
//            logger.error("注册接口：身份认证失败！参数：" + data.toJSONString());
//            return ResultOperationUtil.generateFailResult("身份认证失败！");
//        }
        //验证该学号是否注册过
        Student studentQue = new Student();
        studentQue.setStudentNum(studentNum);
        List<Student> queryResult = studentService.listStudent(studentQue);
        if (queryResult.size() !=0){
            return  ResultOperationUtil.generateFailResult("注册失败！您已经注册过了！");
        }
        return studentService.register(data,request);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody JSONObject data, HttpServletRequest request){
        String studentNum = data.getString("studentNum");
        String password = data.getString("password");
        String sign = data.getString("sign");
        Long timeStamp = data.getLong("timeStamp");

        if(StringUtils.isEmpty(studentNum) || StringUtils.isEmpty("password")
                || StringUtils.isEmpty(sign) || null == timeStamp){
            logger.error("登录接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }

        //校验秘钥是否通过
        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
            logger.error("登录接口:身份认证失败！参数：" + data.toJSONString());
            return ResultOperationUtil.generateFailResult("身份认证失败！");
        }
        return  studentService.login(data,request);
    }


}
