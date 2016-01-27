/**

 */
package com.guoku.guokuv4.bean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月27日 下午5:31:04 
 * 登录方式
 */
public class LoginInfo {
	
	public static int type;//0:果库用户  1:淘宝用户  2:微博用户  3:微信用户

	
	public static boolean isGuokuUser(){
		if(type == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isTaobaoUser(){
		if(type == 1){
			return true;
		}
		return false;
	}
	
	public static boolean isWeiboUser(){
		if(type == 2){
			return true;
		}
		return false;
	}
	
	public static boolean isWeixinUser(){
		if(type == 3){
			return true;
		}
		return false;
	}

}
