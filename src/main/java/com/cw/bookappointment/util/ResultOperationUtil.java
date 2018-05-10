package com.cw.bookappointment.util;

import com.alibaba.fastjson.JSONObject;

public class ResultOperationUtil {
	
	public static JSONObject generateCodeResult(Integer code, String msg) {
		JSONObject jObject = new JSONObject();
		jObject.put("errorCode", code);
		jObject.put("message", msg);
		return jObject;
	}
	

	public static JSONObject generateSuccessResult(JSONObject data) {
		JSONObject json = new JSONObject();
		json.put("errorCode", 0);
		json.put("data", data);
		return json;
	}
	
	public static JSONObject generateFailResult(String message) {
		JSONObject json = new JSONObject();
		json.put("errorCode", 1);
		json.put("message", message);
		return json;
	}
	
	public static JSONObject generateSuccessResutl(Object data){
		JSONObject json = new JSONObject();
		json.put("errorCode", 0);
		json.put("data", data);
		return json;
	}
	

}
