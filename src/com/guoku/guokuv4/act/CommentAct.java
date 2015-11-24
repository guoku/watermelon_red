package com.guoku.guokuv4.act;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CommentAct extends NetWorkActivity {

	private static final int COMMENTNOTE = 10;

	@ViewInject(R.id.comment_ed_com)
	private EditText text;

	private PInfoBean productBean;

	private boolean up;

	private String noteid;

	public static final String KEY_UPDATA = "KEY_UPDATA";
	public static final String KEY_DATA = "KEY_DATA";

	String strContent = "";
	private boolean isUpData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		setGCenter(true, "撰写点评");
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.send);

		init();
	}

	private void init() {

		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (arg0.length() > 0) {
					if (arg0.toString().equals(strContent)) {
						isUpData = false;
					} else {
						isUpData = true;
					}
				} else {
					isUpData = false;
				}
			}
		});
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void Push(View v) {
		if (!StringUtils.isEmpty(text.getText().toString())) {
			if (up) {
				if (isUpData) {
					sendConnectionPOST(Constant.COMMENTLIST + noteid
							+ "/update/", new String[] { "note" },
							new String[] { text.getText().toString() },
							COMMENTNOTE, true);
				} else {
					finish();
				}
			} else
				sendConnectionPOST(
						Constant.COMMENTNOTE
								+ productBean.getEntity().getEntity_id()
								+ "/add/note/", new String[] { "note" },
						new String[] { text.getText().toString() },
						COMMENTNOTE, true);

		} else {
			ToastUtil.show(mContext, "总得说点什么吧~");
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		GuokuUtil.hideKeyboard(context, text);
		switch (where) {
		case COMMENTNOTE:
			
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_COMMENT);
			
			AVAnalytics.onEvent(this, "poke");
			MobclickAgent.onEvent(this, "poke");

			AVAnalytics.onEvent(mContext, "success");
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString(KEY_DATA, result);
			bundle.putBoolean(KEY_UPDATA, up);
			intent.putExtras(bundle);
			setResult(10086, intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		GuokuUtil.hideKeyboard(context, text);
	}

	@Override
	protected void setupData() {
		productBean = JSON.parseObject(getIntent().getStringExtra("data"),
				PInfoBean.class);
		noteid = getIntent().getStringExtra("noteid");
		if (GuokuApplication.getInstance().getBean() != null) {
			ArrayList<NoteBean> list = productBean.getNote_list();
			for (NoteBean bean : list) {
				if (bean.getCreator()
						.getUser_id()
						.equals(GuokuApplication.getInstance().getBean()
								.getUser().getUser_id())) {
					strContent = bean.getContent();
					text.setText(strContent);
					up = true;
				}
			}
		}

	}

}
