package com.cw.bookappointment.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

public class SignUtil {

    public static void main(String[] args) {
        JSONObject data = new JSONObject();
        data.put("studentNum","2016215106");
        data.put("timeStamp",1525881600000L);
        System.out.println(generateSign(data,"djVB1R5EwMTWUDTWSqyiInsB"));
    }
    public static String generateSign(JSONObject data, String secret){
//        Set<Map.Entry<String,Object>> keySet = data.entrySet();
        StringBuilder builder = new StringBuilder();
//        for(Map.Entry<String,Object> entry : keySet){
//            if(entry.getKey()!="sign"){
//                builder.append(entry.getKey()).append("=").append((String) entry.getValue());
//            }
//        }
        String studentNum = data.getString("studentNum");
        String timeStamp = data.getString("timeStamp");
        builder.append("studentNum").append("=").append(studentNum);
        builder.append("timeStamp").append("=").append(timeStamp);
        builder.append(secret);
        System.out.println(builder.toString());
        return  MD5Util.generateMD5(builder.toString());
    }

    public static boolean isSignValid(String sign, JSONObject data, String secret){
        return  sign.equals(generateSign(data,secret));
    }

}
