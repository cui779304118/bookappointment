package com.cw.bookappointment.entity;

import java.util.Date;

public class StudentInfo {
    private int studentInfoId;
    private int sex;
    private  int age;
    private  String clazz;
    private  String major;
    private Date admissionDate;

    public int getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(int studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentInfoId=" + studentInfoId +
                ", sex=" + sex +
                ", age=" + age +
                ", clazz='" + clazz + '\'' +
                ", major='" + major + '\'' +
                ", admissionDate=" + admissionDate +
                '}';
    }
}
