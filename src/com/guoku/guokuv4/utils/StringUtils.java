/**

 */
package com.guoku.guokuv4.utils;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-12 下午2:44:00 
 */
public class StringUtils {
	
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0 || "null".equals(str.toLowerCase()));
	}

}
