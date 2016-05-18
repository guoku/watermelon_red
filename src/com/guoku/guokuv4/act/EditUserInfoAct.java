package com.guoku.guokuv4.act;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.utils.BitmapUtil;
import com.guoku.guokuv4.utils.DialogUtils;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.LayoutEditItemView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
import de.greenrobot.event.EventBus;

/**
 * 编辑个人资料
 * 
 * @zhangyao
 * @Description: TODO
 * @date 2016年5月12日 下午2:58:59
 */
public class EditUserInfoAct extends NetWorkActivity implements android.view.View.OnClickListener {

	private static final int NET_PIC = 1001;
	private static final int NET_UPDATA = 1002;

	// 省数据集合
	private ArrayList<String> mListProvince = new ArrayList<String>();
	// 市数据集合
	private ArrayList<ArrayList<String>> mListCiry = new ArrayList<ArrayList<String>>();
	// 区数据集合
	private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<ArrayList<ArrayList<String>>>();

	private OptionsPickerView<String> mOpv;
	private JSONObject mJsonObj;

	@ViewInject(R.id.img_head)
	LayoutEditItemView viewHead;

	@ViewInject(R.id.tv_nickname)
	LayoutEditItemView viewNickName;

	@ViewInject(R.id.tv_sex)
	LayoutEditItemView viewSex;

	@ViewInject(R.id.tv_address)
	LayoutEditItemView viewAddress;

	@ViewInject(R.id.tv_profile)
	LayoutEditItemView viewProfile;

	private AccountBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_edit_user_info);

		initAddress();
	}

	private void initAddress() {

		// 初始化Json对象
		initJsonData();
		// 初始化Json数据
		initJsonDatas();

		// 创建选项选择器对象
		mOpv = new OptionsPickerView<String>(this);

		// 设置标题
		mOpv.setTitle("选择城市");

		// 设置三级联动效果
		mOpv.setPicker(mListProvince, mListCiry, mListArea, true);

		// 设置是否循环滚动
		mOpv.setCyclic(false, false, false);

		// 设置默认选中的三级项目
		mOpv.setSelectOptions(0, 0, 0);

		// 监听确定选择按钮
		mOpv.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				// 返回的分别是三个级别的选中位置
				String tx = mListProvince.get(options1) + mListCiry.get(options1).get(option2)
						+ mListArea.get(options1).get(option2).get(options3);
				viewAddress.editText.setText(tx);
			}
		});
	}

	@Override
	protected void rightTextOnClick() {
		// TODO Auto-generated method stub
		super.rightTextOnClick();

		if (isEmpty()) {
			String tempGender = "";
			if (viewSex.editText.getText().toString().equals("男")) {
				tempGender = "M";
			} else if (viewSex.editText.getText().toString().equals("女")) {
				tempGender = "F";
			}

			sendConnectionPOST(Constant.USERUPDATA, new String[] { "nickname", "gender", "location", "bio" },
					new String[] { viewNickName.editText.getText().toString(), tempGender,
							viewAddress.editText.getText().toString(), viewProfile.editText.getText().toString() },
					NET_UPDATA, true);
		}
	}

	private boolean isEmpty() {

		if (StringUtils.isEmpty(viewNickName.editText.getText().toString())) {
			ToastUtil.show(mContext, "请输入昵称");
			return false;
		} else if (StringUtils.isEmpty(viewSex.editText.getText().toString())) {
			ToastUtil.show(mContext, "请输入性别");
			return false;
		} else if (StringUtils.isEmpty(viewAddress.editText.getText().toString())) {
			ToastUtil.show(mContext, "请输入所在地");
			return false;
		} else if (StringUtils.isEmpty(viewProfile.editText.getText().toString())) {
			ToastUtil.show(mContext, "请输入简介");
			return false;
		}

		return true;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		bean.setUser(JSON.parseObject(result, UserBean.class));
		GuokuApplication.getInstance().login(bean);
		GuokuApplication.getInstance().getBean().setUser(bean.getUser());
		EventBus.getDefault().post(bean.getUser());
		refresh();
		switch (where) {
		case NET_PIC:
			ToastUtil.show(mContext, "头像修改成功");
			break;
		case NET_UPDATA:
			ToastUtil.show(mContext, "用户信息修改成功");
			finish();
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case NET_PIC:
			ToastUtil.show(mContext, "头像修改失败");
			break;
		case NET_UPDATA:
			ToastUtil.show(mContext, "用户信息修改失败");
			break;
		}
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);

		setGCenter(true, R.string.tv_edit_user_title);
		setGLeft(true, R.drawable.back_selector);
		setGRigthText(true, R.string.tv_save);

		refresh();
	}

	private void refresh() {

		bean = SharePrenceUtil.getUserBean(this);
		viewNickName.editText.setText(bean.getUser().getNick());
		viewSex.editText.setText(bean.getUser().getGender());
		viewAddress.editText.setText(bean.getUser().getLocation());
		viewNickName.editText.setText(bean.getUser().getNick());
		viewProfile.editText.setText(bean.getUser().getBio());
		viewHead.imageVie.setImageURI(Uri.parse(bean.getUser().get240()));

		viewAddress.editText.setOnClickListener(this);
		viewSex.editText.setOnClickListener(this);
	}

	@OnClick(R.id.img_head)
	private void headClick(View view) {
		DialogUtils.listDialgo(mContext, new String[] { "相册", "拍照", "取消" }, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
					getImage.addCategory(Intent.CATEGORY_OPENABLE);
					getImage.setType("image/jpeg");
					startActivityForResult(getImage, 0);
					break;
				case 1:
					Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(getImageByCamera, 1);
					break;

				}
			}
		}).show();
	}

	@OnClick(R.id.tv_sex)
	public void sex(View v) {
		showSex();
	}

	@OnClick(R.id.tv_address)
	private void addressClick(View view) {

		GuokuUtil.hideKeyBoard(this);
		mOpv.show();
	}

	private void showSex() {
		DialogUtils.listDialgo(mContext, new String[] { "男", "女", "取消" }, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					viewSex.editText.setText("男");
					break;
				case 1:
					viewSex.editText.setText("女");
					break;
				}
			}
		}).show();
	}

	/** 从assert文件夹中读取省市区的json文件，然后转化为json对象 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "GBK"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** 初始化Json数据，并释放Json对象 */
	private void initJsonDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 获取每个省的Json对象
				String province = jsonP.getString("name");

				ArrayList<String> options2Items_01 = new ArrayList<String>();
				ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
				JSONArray jsonCs = jsonP.getJSONArray("city");
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
					String city = jsonC.getString("name");
					options2Items_01.add(city);// 添加市数据

					ArrayList<String> options3Items_01_01 = new ArrayList<String>();
					JSONArray jsonAs = jsonC.getJSONArray("area");
					for (int k = 0; k < jsonAs.length(); k++) {
						options3Items_01_01.add(jsonAs.getString(k));// 添加区数据
					}
					options3Items_01.add(options3Items_01_01);
				}
				mListProvince.add(province);// 添加省数据
				mListCiry.add(options2Items_01);
				mListArea.add(options3Items_01);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Bitmap ed_Bitmap = null;
			if (requestCode == 0) {
				Uri uri = data.getData();
				ContentResolver cr = this.getContentResolver();
				try {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 4;
					ed_Bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);

					// viewHead.imageVie.setImageURI(uri);
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
			BitmapUtil.saveBitmap(Constant.IMAGES_PATH + "temp.png", ed_Bitmap);
			int d = BitmapUtil.getBitmapDegree(Constant.IMAGES_PATH + "temp.png");

			if (ed_Bitmap != null) {
				ed_Bitmap = BitmapUtil.rotateBitmapByDegree(ed_Bitmap, d);
				upPic(Constant.USERUPDATA, ed_Bitmap, NET_PIC);
				ed_Bitmap.recycle();
			}
		}
	}

	public void onEventMainThread(String email) {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == viewAddress.editText) {
			GuokuUtil.hideKeyBoard(this);
			mOpv.show();
		}
		if (v == viewSex.editText) {
			showSex();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mOpv.isShowing()){
				mOpv.dismiss();
				return false;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

}
