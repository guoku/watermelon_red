package com.guoku.guokuv4.share;

import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.HexieAct;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * 
 */
public class CustomShareBoard extends PopupWindow implements OnClickListener {

	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constant.DESCRIPTOR);
	private Activity mActivity;
	private String entityID;
	private PInfoBean pib;
	private View rootView;
	private String url;
	private String title;
	

	public CustomShareBoard(Activity activity) {
		super(activity);
		this.mActivity = activity;
		initView(activity);
		ProductInfoAct.isRefrech = false;
	}

	private void configPlatforms(String str) {
		
		//限制分享内容长度
		if(!StringUtils.isEmpty(str)){
			if(str.length() > 140){
				str = StringUtils.setSubstring(str, 0, 140);
			}
		}
		
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		addWXPlatform();
	}

	private void addWXPlatform() {
		UMWXHandler wxHandler = new UMWXHandler(mActivity, Constant.WX_APPID,
				Constant.WX_SECRET);
		wxHandler.addToSocialSDK();

		UMWXHandler wxCircleHandler = new UMWXHandler(mActivity,
				Constant.WX_APPID, Constant.WX_SECRET);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	public void setShareContext(String context, UMImage imgUrl, String id,
			String etid, PInfoBean bean) {
		
		pib = bean;
		entityID = etid;
		configPlatforms(context);

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(context + "：" + bean.getTop_note()
				+ Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		weixinContent.setTargetUrl(Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		weixinContent.setShareImage(imgUrl);
		weixinContent.setTitle(context);
		mController.setShareMedia(weixinContent);

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(context + bean.getTop_note()
				+ Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		circleMedia.setShareImage(imgUrl);
		circleMedia.setTitle(context);
		circleMedia.setTargetUrl(Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setShareContent(context + bean.getTop_note()
				+ Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		sinaShareContent.setShareImage(imgUrl);
		sinaShareContent.setTitle(context);
		sinaShareContent.setTargetUrl(Constant.DETAIL
				+ bean.getEntity().getEntity_hash() + "/");
		mController.setShareMedia(sinaShareContent);
		
		url = Constant.DETAIL + pib.getEntity().getEntity_hash();
		title = pib.getEntity().getTitle();
	}

	public void setShareContext(Context mContext, String context, String url,
			String imgUrl, String title) {
		
		configPlatforms(context);
		this.url = url;
		this.title = title;

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTargetUrl(url);
		weixinContent.setShareContent(title + context);
		weixinContent.setShareImage(new UMImage(mContext, imgUrl));
		mController.setShareMedia(weixinContent);

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTargetUrl(url);
		circleMedia.setShareImage(new UMImage(mContext, imgUrl));
		circleMedia.setShareContent(context);
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setShareContent(title + context + "（分享自 @果库）" + url);
		sinaShareContent.setTargetUrl(url);
		sinaShareContent.setShareImage(new UMImage(mContext, imgUrl));
		mController.setShareMedia(sinaShareContent);
	}

	public void setShareContext(String title, String url) {
		configPlatforms(title);
		this.url = url;

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTargetUrl(url);
		weixinContent.setTitle(title);
		mController.setShareMedia(weixinContent);

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle(title);
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setTitle(title);
		sinaShareContent.setTargetUrl(url);
		mController.setShareMedia(sinaShareContent);

	}

	public void setShareContext(String context, UMImage url, String tag,
			String title) {
		configPlatforms(context);

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(context);
		weixinContent.setTargetUrl(tag);
		weixinContent.setShareImage(url);
		weixinContent.setTitle(title);
		mController.setShareMedia(weixinContent);

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(context);
		circleMedia.setShareImage(url);
		circleMedia.setTitle(title);
		circleMedia.setTargetUrl(tag);
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setShareContent(context);
		sinaShareContent.setShareImage(url);
		sinaShareContent.setTitle(title);
		sinaShareContent.setTargetUrl(tag);
		mController.setShareMedia(sinaShareContent);
	}

	public void setShareContext(String context, UMImage url, String tag) {
		configPlatforms(context);

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(context);
		weixinContent.setTargetUrl(tag);
		weixinContent.setShareImage(url);
		weixinContent.setTitle(context);
		mController.setShareMedia(weixinContent);

		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(context);
		circleMedia.setShareImage(url);
		circleMedia.setTitle(context);
		circleMedia.setTargetUrl(tag);
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setShareContent(context);
		sinaShareContent.setShareImage(url);
		sinaShareContent.setTitle(context);
		sinaShareContent.setTargetUrl(tag);
		mController.setShareMedia(sinaShareContent);
	}

	public void shareW() {
		performShare(SHARE_MEDIA.WEIXIN);
	}

	public void shareS() {
		performShare(SHARE_MEDIA.SINA);
	}

	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		rootView = LayoutInflater.from(context).inflate(R.layout.custom_board,
				null);
		rootView.findViewById(R.id.share_llq).setOnClickListener(this);
		rootView.findViewById(R.id.share_sina).setOnClickListener(this);
		rootView.findViewById(R.id.share_wx_1).setOnClickListener(this);
		rootView.findViewById(R.id.share_wx_2).setOnClickListener(this);
		rootView.findViewById(R.id.share_mail).setOnClickListener(this);
		rootView.findViewById(R.id.layout_refresh).setOnClickListener(this);
		rootView.findViewById(R.id.layout_copy).setOnClickListener(this);
		rootView.findViewById(R.id.layout_report).setOnClickListener(this);
		rootView.findViewById(R.id.cancel).setOnClickListener(this);
		setContentView(rootView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setBackgroundDrawable(new BitmapDrawable());

		setTouchable(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.share_sina:
			if (pib != null) {

				AVAnalytics.onEvent(mActivity, "share to entity to weibo", pib
						.getEntity().getTitle());
				MobclickAgent.onEvent(mActivity, "share to entity to weibo");

			}
			performShare(SHARE_MEDIA.SINA);
			break;
		case R.id.share_wx_1:
			performShare(SHARE_MEDIA.WEIXIN);
			if (pib != null) {

				MobclickAgent.onEvent(mActivity, "share entity to moments");
				AVAnalytics.onEvent(mActivity, "share entity to moments", pib
						.getEntity().getTitle());
			}
			break;
		case R.id.share_wx_2:
			performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
			if (pib != null) {

				MobclickAgent.onEvent(mActivity, "share entity to wechat");

				AVAnalytics.onEvent(mActivity, "share entity to wechat", pib
						.getEntity().getTitle());
			}
			break;
		case R.id.share_llq:
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			mActivity.startActivity(intent);
			break;
		case R.id.share_mail:
			sendMail();
			break;
		case R.id.layout_refresh:
			ProductInfoAct.isRefrech = true;
			break;
		case R.id.layout_copy:
			ClipboardManager cmb = (ClipboardManager)mActivity.getSystemService(Context.CLIPBOARD_SERVICE);  
			cmb.setText(url);  
			ToastUtil.show(mActivity, mActivity.getString(R.string.tv_already_copy));

			break;
		case R.id.layout_report:
			if (GuokuApplication.getInstance().getBean() != null) {
				Intent intent2 = new Intent(mActivity, HexieAct.class);
				intent2.putExtra("data", entityID);
				mActivity.startActivity(intent2);
			} else {
				mActivity.startActivity(new Intent(mActivity, LoginAct.class));
				mActivity.overridePendingTransition(R.anim.push_up_in,
						R.anim.push_up_out);
			}
			break;
		default:
			break;
		}
		
		dismiss();
	}
	
	   /**
	    * 发送邮件
	    * @param emailBody
	    */
	   private void sendMail(){
	        Intent email = new Intent(android.content.Intent.ACTION_SEND);
	        email.setType("plain/text");
	        //邮件主题
	        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mActivity.getResources().getString(R.string.tv_static_title));
	        //邮件内容
	        email.putExtra(android.content.Intent.EXTRA_TEXT, title + url);  
	        mActivity.startActivity(email); 
	    }
	    

	private void performShare(final SHARE_MEDIA platform) {
		mController.postShare(mActivity, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
				String showText = platform.toString();
				if (arg1 == StatusCode.ST_CODE_SUCCESSED) {
					showText += "平台分享成功";
				} else {
					showText += "平台分享失败";
				}
				Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();

			}
		});
	}

}
