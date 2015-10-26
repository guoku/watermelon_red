package com.guoku.guokuv4.act;

import com.guoku.R;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HexieAct extends NetWorkActivity {

	private static final int COMMENTNOTE = 10;

	@ViewInject(R.id.comment_ed_com)
	private EditText text;

	private String entityId;

	@ViewInject(R.id.hexie_rg)
	private RadioGroup group;

	private int curItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hexie);
		setGCenter(true, "我要举报");
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.send);
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void Push(View v) {
		// if (text.getText().toString() != null
		// && !"".equals(text.getText().toString().trim())) {
		sendConnectionPOST(Constant.COMMENTNOTE + entityId + "/report/",
				new String[] { "comment", "type" }, new String[] {
						text.getText().toString(), curItem + "" }, COMMENTNOTE,
				false);
		// } else {
		// ToastUtil.show(mContext, "总得说点什么吧~");
		// }
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {
		case COMMENTNOTE:
			finish();
			ToastUtil.show(getApplicationContext(), "举报成功");
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
	}

	@Override
	protected void setupData() {
		entityId = getIntent().getStringExtra("data");
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

				curItem = arg1;

			}
		});
	}

}
