package com.cw.bookappointment.constant;

import java.util.HashSet;
import java.util.Set;

public class Constant {
	//重复预约返回码
	public static final int FAIL_REPEAT_APPOINTMENT = 1;

	//库存不够返回码
	public static final int FAIL_LACK_STOCK = 2;
	
	//预约失败返回码
	public static final int FAIL_UNKNOW = -1;
	
	//预约成功
	public static final int APPOINTMENT_SUCCESS = 0;

	//CAS默认尝试时间
	public static  final Long TRY_TIME = 100L;

	public static  final String[] INTERCEPTOR_FILTER_PATH = new String[]{"/login","/register"};

	public static final Set PIC_FORM_SET = new HashSet<String>(){
		{
			add("jpg");
			add("jpeg");
			add("png");
			add("PNG");
			add("JPG");
			add("JPEG");
		}
	};
}
