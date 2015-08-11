/**
 * 
 */
package com.guoku.guokuv4.utils;

import com.ekwing.students.config.Constant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

   /**
 * 
 *类描述：公共广播类
 *创建人：zhangyao
 *联系方式：zhangyao@guoku.com
 *创建时间：2015-8-7 下午4:51:53
 *
 */
public class BroadUtil {
	
	public static void sendBroadcast(Context mContext, Bundle bundle){
		
		Intent intent = new Intent();
		if(bundle != null){
			intent.putExtras(bundle);
		}
		intent.setAction(Constant.INTENT_ACTION);
		mContext.sendBroadcast(intent);
	}
	
	public static void setBroadcastInt(Context mContext, String key, int value){
		
		Bundle bundle = new Bundle();
		bundle.putInt(key, value);
		sendBroadcast(mContext, bundle);
	}

}
