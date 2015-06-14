package com.ekwing.students.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;


public class ParamsUtil {
	/**
	 * @param modelName
	 * @param methodName
	 * @return
	 */
    public static String getSign(String modelName,String methodName,long currM) {
    	String sing = modelName + methodName + currM;
    	return MD5.getMD5(sing);
    }
  //判断是否为正确手机号
  	public static boolean isPhoneNumberValid(String phone){
  		if(TextUtils.isEmpty(phone)){
  			return false;
  		}
  		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
  		CharSequence inputStr = phone;
  		Matcher matcher = p.matcher(inputStr);
  		if(matcher.matches()){
  			return true;
  		}
  		return false;
  	}
}
