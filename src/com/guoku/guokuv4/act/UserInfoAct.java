package com.guoku.guokuv4.act;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.utils.BitmapUtil;
import com.ekwing.students.utils.DialogUtils;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.my.ChangeEmailAct;
import com.guoku.guokuv4.my.ChangePasswordAct;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class UserInfoAct extends NetWorkActivity {

	public static final int INTENT_REQUEST_CODE = 1001;

	private static final int ADD = 10;
	private static final int NAME = 11;
	private static final int SIGN = 14;
	private static final int SEX = 15;
	private static final int PIC = 16;
	private AccountBean bean;

	@ViewInject(R.id.user_info_tv_add)
	private TextView tv_add;

	@ViewInject(R.id.user_info_tv_email)
	private TextView tv_email;

	@ViewInject(R.id.user_info_tv_name)
	private TextView tv_name;

	@ViewInject(R.id.user_info_pic)
	private ImageView iv_pic;

	@ViewInject(R.id.user_info_tv_sign)
	private TextView tv_sign;

	@ViewInject(R.id.user_info_tv_sex)
	private TextView tv_sex;
	
	@ViewInject(R.id.title_bar_left_iv)
	private ImageView left;
	
	private DisplayImageOptions optionsRound1;
	
	private boolean isUpdata;//是否有刷新

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		setGCenter(true, "编辑个人资料");
		setGLeft(true, R.drawable.back_selector);
	}

	@OnClick(R.id.user_info_pic)
	public void Pic(View v) {
		DialogUtils.listDialgo(mContext, new String[] { "相册", "拍照", "取消" },
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							startActivityForResult(getImage, 0);
							break;
						case 1:
							Intent getImageByCamera = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(getImageByCamera, 1);
							break;

						}
					}
				}).show();
	}

	@OnClick(R.id.user_info_ll_add)
	public void add(View v) {
		final EditText text = new EditText(mContext);
		text.setText(bean.getUser().getLocation());
		DialogUtils.getEDialog(mContext, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (text.getText().toString() != null
						&& !"".equals(text.getText().toString())) {
					sendConnectionPOST(Constant.USERUPDATA,
							new String[] { "location" }, new String[] { text
									.getText().toString() }, ADD, false);
				}
			}
		}, "修改所在地", text).show();
	}

	@OnClick(R.id.user_info_ll_email)
	public void email(View v) {

		openActivityForResult(ChangeEmailAct.class, INTENT_REQUEST_CODE);
	}

	@OnClick(R.id.user_info_ll_name)
	public void name(View v) {
		final EditText text = new EditText(mContext);
		text.setText(bean.getUser().getNickname());
		DialogUtils.getEDialog(mContext, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (text.getText().toString() != null
						&& !"".equals(text.getText().toString())) {

					sendConnectionPOST(Constant.USERUPDATA,
							new String[] { "nickname" }, new String[] { text
									.getText().toString() }, NAME, false);
				}
			}
		}, "修改昵称", text).show();
	}

	@OnClick(R.id.user_info_ll_pass)
	public void pass(View v) {

		startActivity(new Intent(this, ChangePasswordAct.class));
	}

	@OnClick(R.id.user_info_ll_sex)
	public void sex(View v) {
		DialogUtils.listDialgo(mContext, new String[] { "男", "女", "取消" },
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							sendConnectionPOST(Constant.USERUPDATA,
									new String[] { "gender" },
									new String[] { "M" }, SEX, false);
							break;
						case 1:
							sendConnectionPOST(Constant.USERUPDATA,
									new String[] { "gender" },
									new String[] { "F" }, SEX, false);
							break;

						}
					}
				}).show();
	}

	@OnClick(R.id.user_info_ll_sign)
	public void sign(View v) {
		final EditText text = new EditText(mContext);
		text.setText(bean.getUser().getBio());
		DialogUtils.getEDialog(mContext, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (text.getText().toString() != null
						&& !"".equals(text.getText().toString())) {

					sendConnectionPOST(Constant.USERUPDATA,
							new String[] { "bio" }, new String[] { text
									.getText().toString() }, SIGN, false);
				}
			}
		}, "修改简介", text).show();
	}

	@Override
	protected void onSuccess(String result, int where) {
		isUpdata = true;
		bean.setUser(JSON.parseObject(result, UserBean.class));
		Logger.e("USER", bean.toString());
		EkwingApplication.getInstance().login(bean);
		if(where == PIC){
			refreshTv(false);
		}else{
			refreshTv(true);
		}
		switch (where) {
		case ADD:
			ToastUtil.show(mContext, "所在地修改成功");
			break;
		case SEX:
			ToastUtil.show(mContext, "性别修改成功");
			break;
		case NAME:
			ToastUtil.show(mContext, "昵称修改成功");
			break;
		case SIGN:
			ToastUtil.show(mContext, "简介修改成功");
			break;
		case PIC:
			ToastUtil.show(mContext, "头像修改成功");
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		switch (where) {
		case ADD:
			ToastUtil.show(mContext, "所在地修改失败");
			break;
		case SEX:
			ToastUtil.show(mContext, "性别修改失败");
			break;
		case NAME:
			ToastUtil.show(mContext, "昵称修改失败");
			break;
		case SIGN:
			ToastUtil.show(mContext, "简介修改失败");
			break;
		case PIC:
			ToastUtil.show(mContext, "头像修改失败");
			break;
		default:
			break;
		}

	}

	@Override
	protected void setupData() {
		refreshTv(true);
	}
	
	
	private void refreshTv(Boolean isHead){
		
		bean = EkwingApplication.getInstance().getBean();
		tv_add.setText(bean.getUser().getLocation());
		tv_email.setText(bean.getUser().getEmail());
		tv_name.setText(bean.getUser().getNickname());
		tv_sign.setText(bean.getUser().getBio());
		tv_sex.setText(bean.getUser().getGender());
		// BitmapUtil.setRoundImage(imageLoader,
		// bean.getUser().getAvatar_large(),
		// options, iv_pic);

		if(isHead){
			optionsRound1 = new DisplayImageOptions.Builder()
			.bitmapConfig(Bitmap.Config.RGB_565)
			.imageScaleType(ImageScaleType.EXACTLY)
			.displayer(new RoundedBitmapDisplayer(300))
			.showImageForEmptyUri(R.drawable.user100)
			.showImageOnFail(R.drawable.user100)
			.showImageOnLoading(R.drawable.user100).build();

	imageLoader
			.displayImage(bean.getUser().get240(), iv_pic, optionsRound1);

	LayoutParams params = (LayoutParams) iv_pic.getLayoutParams();
	params.topMargin = BitmapUtil.dip2pix(context, 32);
	params.bottomMargin = BitmapUtil.dip2pix(context, 32);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			if (resultCode == INTENT_REQUEST_CODE) {
				bean = EkwingApplication.getInstance().getBean();
				tv_email.setText(bean.getUser().getEmail());
			} else {
				Bitmap ed_Bitmap = null;
				if (requestCode == 0) {
					Uri uri = data.getData();
					ContentResolver cr = this.getContentResolver();
					try {
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = false;
						options.inSampleSize = 4;
						ed_Bitmap = BitmapFactory.decodeStream(
								cr.openInputStream(uri), null, options);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (requestCode == 1) {
					try {
						Bundle bundle = data.getExtras();
						Bitmap buf = (Bitmap) bundle.get("data");

						ed_Bitmap = Bitmap.createBitmap(buf);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String sdState = Environment.getExternalStorageState();
				if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
					return;
				}
				iv_pic.setImageBitmap(ed_Bitmap);
				BitmapUtil.saveBitmap(Constant.IMAGES_PATH + "temp.png",
						ed_Bitmap);
				int d = BitmapUtil.getBitmapDegree(Constant.IMAGES_PATH
						+ "temp.png");
				ed_Bitmap = BitmapUtil.rotateBitmapByDegree(ed_Bitmap, d);
				upPic(Constant.USERUPDATA, ed_Bitmap, PIC);
			}
		}
	}
	
	@Override
	public void leftOnClick() {
		// TODO Auto-generated method stub
		if(isUpdata){
			requestIntent();
		}
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isUpdata) {
				requestIntent();
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void requestIntent() {

		Intent intent = new Intent();
		setResult(PersonalFragment.RESULT_CODE, intent);
		finish();
	}

}
