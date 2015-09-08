/**

 */
package com.guoku.guokuv4.utils;

import android.util.Log;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-7 下午3:19:08 
 */
public class LogGK {
	
	/**
     * 是否开启debug
     */
    public static boolean isDebug = true;
    
    public static String TAG = "com.guoku";
 
     
    public static void v(String msg) {
        if(isDebug){
        	Log.v(TAG, msg);
        }
    }
    public static  void d(String msg) {
    	if(isDebug){
        	Log.d(TAG, msg);
        }
    }
     
    public static void i(String msg) {
    	if(isDebug){
        	Log.i(TAG, msg);
        }
    }
     
    public static void w(String msg) {
    	if(isDebug){
        	Log.w(TAG, msg);
        }
     }
 
     public static void e(String msg) {
    	 if(isDebug){
         	Log.e(TAG, msg);
         }
     }
     
     /**
      * LOG分段显示
      * @param veryLongString
      */
     public static void logStr(String veryLongString){
    	 int maxLogSize = 1000;
         for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
             int start = i * maxLogSize;
             int end = (i+1) * maxLogSize;
             end = end > veryLongString.length() ? veryLongString.length() : end;
             LogGK.v(veryLongString.substring(start, end));
         }
     }
}
