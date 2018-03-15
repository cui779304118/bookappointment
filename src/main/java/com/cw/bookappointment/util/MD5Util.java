package com.cw.bookappointment.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

public class MD5Util {
	
	public  static String generateMD5(String str){
 		if(StringUtils.isEmpty(str)){
 			return null;
 		}
		byte [] strBytes=null;
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
			strBytes=md5.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return null;
		}
		return BytesConvertToHexString(strBytes);
 	}
 
 	private static String BytesConvertToHexString(byte[] bytes) {
 		StringBuffer sb=new StringBuffer();
 		for(byte aByte:bytes){
 			String s=Integer.toHexString(0xff&aByte);
 			if(s.length()==1){
				sb.append("0"+s);
 			}else{
 				sb.append(s);
 			}
 		}
 		return  sb.toString();
 	}
}
