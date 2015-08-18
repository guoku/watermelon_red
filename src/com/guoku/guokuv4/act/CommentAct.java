package com.guoku.guokuv4.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.LogUtil.log;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ToastUtil;
import com.ekwing.students.utils.Utils;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

public class CommentAct extends NetWorkActivity {

	private static final int COMMENTNOTE = 10;

	@ViewInject(R.id.comment_ed_com)
	private EditText text;

	private PInfoBean productBean;

	private boolean up;

	private String noteid;
	
	public static final String KEY_UPDATA = "KEY_UPDATA";
	public static final String KEY_DATA = "KEY_DATA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		setGCenter(true, "撰写点评");
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.send);
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void Push(View v) {
		if (!StringUtils.isEmpty(text.getText().toString())) {
			if (up) {
				sendConnectionPOST(Constant.COMMENTLIST + noteid + "/update/",
						new String[] { "note" }, new String[] { text.getText()
								.toString() }, COMMENTNOTE, false);
			} else
				sendConnectionPOST(
						Constant.COMMENTNOTE
								+ productBean.getEntity().getEntity_id()
								+ "/add/note/", new String[] { "note" },
						new String[] { text.getText().toString() },
						COMMENTNOTE, false);

		} else {
			ToastUtil.show(mContext, "总得说点什么吧~");
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		Utils.hideKeyboard(context, text);
		switch (where) {
		case COMMENTNOTE:
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
		Utils.hideKeyboard(context, text);
	}

	@Override
	protected void setupData() {
		productBean = JSON.parseObject(getIntent().getStringExtra("data"),
				PInfoBean.class);
		noteid = getIntent().getStringExtra("noteid");
		if (EkwingApplication.getInstance().getBean() != null) {
			ArrayList<NoteBean> list = productBean.getNote_list();
			for (NoteBean bean : list) {
				if (bean.getCreator()
						.getUser_id()
						.equals(EkwingApplication.getInstance().getBean()
								.getUser().getUser_id())) {
					text.setText(bean.getContent());
					up = true;
				}
			}
		}

	}

}
