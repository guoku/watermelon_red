package com.ekwing.students.utils;

//package com.ekwing.students.util;
//
//import java.util.HashMap;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.sina.weibo.SinaWeibo;
//import cn.sharesdk.tencent.qzone.QZone;
//import cn.sharesdk.tencent.qzone.QZone.ShareParams;
//
//import com.ekwing.students.R;
//import com.ekwing.students.share.OnekeyShare;
//
//public class ShareUtil {
//	public static void ShareQQ(Context context) {
//
//		ShareParams sp = new ShareParams();
//		sp.title = "测试分享的标题";
//		sp.titleUrl = "http://sharesdk.cn"; // 标题的超链接
//		sp.text = "测试分享的文本";
//		sp.imageUrl = "http://www.someserver.com/测试图片网络地址.jpg";
//		sp.site = "发布分享的网站名称";
//		sp.siteUrl = "发布分享网站的地址";
//
//		Platform qzone = ShareSDK.getPlatform(context, QZone.NAME);
//		qzone.setPlatformActionListener(new PlatformActionListener() {
//
//			@Override
//			public void onError(Platform arg0, int arg1, Throwable arg2) {
//				// TODO Auto-generated method stub
//				Logger.e("qq", "onError：" + arg2.getMessage());
//
//			}
//
//			@Override
//			public void onComplete(Platform arg0, int arg1,
//					HashMap<String, Object> arg2) {
//				// TODO Auto-generated method stub
//				Logger.e("qq", "onComplete");
//
//			}
//
//			@Override
//			public void onCancel(Platform arg0, int arg1) {
//				// TODO Auto-generated method stub
//				Logger.e("qq", "onCancel");
//
//			}
//		}); // 设置分享事件回调
//		// 执行图文分享
//		qzone.share(sp);
//
//	}
//
//	public static void ShareSina(Context context) {
//		Logger.i("a", "```````````````````````````````");
//
//		ShareParams sp = new ShareParams();
//		sp.title = "测试分享的标题";
//		sp.titleUrl = "http://sharesdk.cn"; // 标题的超链接
//		sp.text = "测试分享的文本";
//		sp.imageUrl = "http://www.someserver.com/测试图片网络地址.jpg";
//		sp.site = "发布分享的网站名称";
//		sp.siteUrl = "发布分享网站的地址";
//
//		Logger.i("c", "```````````````````````````````");
//
//		Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
//		Logger.i("d", "```````````````````````````````");
//
//		weibo.setPlatformActionListener(new PlatformActionListener() {
//
//			@Override
//			public void onError(Platform arg0, int arg1, Throwable arg2) {
//				// TODO Auto-generated method stub
//				Logger.e("sina", "Throwable：" + arg2.toString());
//				Logger.e("sina", "getMessage：" + arg2.getMessage());
//				Logger.e("sina",
//						"getLocalizedMessage：" + arg2.getLocalizedMessage());
//			}
//
//			@Override
//			public void onComplete(Platform arg0, int arg1,
//					HashMap<String, Object> arg2) {
//				// TODO Auto-generated method stub
//				Logger.e("sina", "onComplete：");
//			}
//
//			@Override
//			public void onCancel(Platform arg0, int arg1) {
//				// TODO Auto-generated method stub
//				Logger.e("sina", "onCancel：");
//			}
//		}); // 设置分享事件回调
//		// 执行图文分享
//		Logger.i("e", "```````````````````````````````");
//
//		weibo.share(sp);
//		Logger.i("f", "```````````````````````````````");
//
//	}
//
////	public static void SharWXF(Context context) {
////		ShareParams sp = new ShareParams();
////		sp.title = "测试分享的标题";
////		sp.titleUrl = "http://sharesdk.cn"; // 标题的超链接
////		sp.text = "测试分享的文本";
////		sp.imageUrl = "http://www.someserver.com/测试图片网络地址.jpg";
////		sp.site = "发布分享的网站名称";
////		sp.siteUrl = "发布分享网站的地址";
////
////		Platform qzone = ShareSDK.getPlatform(context, Wechat.NAME);
////		qzone.setPlatformActionListener(new PlatformActionListener() {
////
////			@Override
////			public void onError(Platform arg0, int arg1, Throwable arg2) {
////				// TODO Auto-generated method stub
////				Logger.i("onError", "onError:" + arg2.getMessage());
////				Logger.i("onError", "onError:" + arg2.getLocalizedMessage());
////
////			}
////
////			@Override
////			public void onComplete(Platform arg0, int arg1,
////					HashMap<String, Object> arg2) {
////				// TODO Auto-generated method stub
////				Logger.i("onComplete", "onComplete:");
////
////			}
////
////			@Override
////			public void onCancel(Platform arg0, int arg1) {
////				// TODO Auto-generated method stub
////				Logger.i("onCancel", "onCancel:");
////
////			}
////		}); // 设置分享事件回调
////		// 执行图文分享
////		qzone.share(sp);
////	}
//
//	public static void showShare(Activity context, String title,String text, String imageUrl,String linkUrl) {
//		final OnekeyShare oks = new OnekeyShare(context);
//		oks.setNotification(R.drawable.ic_launcher, "来这游");
////		oks.setAddress("12345678901");
//		oks.setTitle(title);
//		oks.setTitleUrl(linkUrl);
//		oks.setText(text);
//		oks.setImageUrl(imageUrl);
//		oks.setUrl(linkUrl);
////		oks.setComment("");
//		oks.setSite("来这游");
//		oks.setSiteUrl("http://www.lezyo.com");
////		oks.setVenueName("ShareSDK");
////		oks.setVenueDescription("This is a beautiful place!");
////		oks.setLatitude(23.056081f);
////		oks.setLongitude(113.385708f);
//		oks.setSilent(true);
//		oks.show(context);
//	}
//
// }
