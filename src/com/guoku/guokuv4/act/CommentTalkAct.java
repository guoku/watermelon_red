package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.DateUtils;
import com.ekwing.students.utils.Utils;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.CommentBean;
import com.guoku.guokuv4.entity.test.CommentListBean;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

public class CommentTalkAct extends NetWorkActivity implements OnClickListener {

	private static final int COMMENT = 10;
	private static final int PY1 = 12;
	private static final int PY2 = 13;

	@ViewInject(R.id.comment_talk_lv)
	private ListView comment_talk_lv;
	@ViewInject(R.id.comment_talk_ed)
	private EditText ed_text;

	private String reply_to_comment, reply_to_user;

	private ArrayListAdapter<CommentBean> comAdapter;

	private CommentListBean commentListBean;

	// private ArrayList<NoteBean> list;
	private ArrayList<CommentBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_talk);
		setGCenter(true, "评论");
		setGLeft(true, R.drawable.back_selector);
	}

	@Override
	protected void onSuccess(String result, int where) {
		Utils.hideKeyboard(context, ed_text);
		reply_to_comment = null;
		reply_to_user = null;
		switch (where) {
		case COMMENT:
			AVAnalytics.onEvent(this, "poke");
			MobclickAgent.onEvent(this, "poke");

			CommentBean bean = JSON.parseObject(result, CommentBean.class);
			list.add(bean);
			comAdapter.setList(list);
			break;
		case PY1:
			AVAnalytics.onEvent(this, "poke");
			MobclickAgent.onEvent(this, "poke");

			if (commentListBean == null) {
				return;
			}
			commentListBean.getNote().setPoke_already("1");
			commentListBean.getNote().setPoke_countADD();
			comAdapter.notifyDataSetChanged();
			break;
		case PY2:
			if (commentListBean.getNote() == null) {
				return;
			}
			commentListBean.getNote().setPoke_already("0");
			commentListBean.getNote().setPoke_countCut();
			comAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onFailure(String result, int where) {
		Utils.hideKeyboard(context, ed_text);
		reply_to_comment = null;
		reply_to_user = null;
	}

	@OnClick(R.id.comment_talk_btn)
	public void Push(View v) {
		if (ed_text.getText().toString() != null
				&& !"".equals(ed_text.getText().toString().trim())) {
			sendConnectionPOST(Constant.COMMENT
					+ commentListBean.getNote().getNote_id() + "/add/comment/",
					new String[] { "comment", "reply_to_comment",
							"reply_to_user" }, new String[] {
							ed_text.getText().toString(), reply_to_comment,
							reply_to_user }, COMMENT, false);
		}
	}

	@Override
	protected void setupData() {
		commentListBean = new CommentListBean();
		// list = (ArrayList<NoteBean>) JSON.parseArray(getIntent()
		// .getStringExtra("data"), NoteBean.class);
		JSONObject root;
		try {
			root = new JSONObject(getIntent().getStringExtra("data"));
			NoteBean noteBean = JSON.parseObject(root.getString("note"),
					NoteBean.class);
			list = (ArrayList<CommentBean>) JSON.parseArray(
					root.getString("comment_list"), CommentBean.class);
			CommentBean buf = new CommentBean();
			buf.setContent(noteBean.getContent());
			buf.setCreated_time(noteBean.getCreated_time());
			buf.setCreator(noteBean.getCreator());
			buf.setNote_id(noteBean.getNote_id());
			buf.setPost_time(noteBean.getPost_time());

			if (list == null) {
				list = new ArrayList<CommentBean>();
			}
			list.add(0, buf);

			String poker_list = root.getString("poker_list");
			EntityBean entityBean = JSON.parseObject(root.getString("entity"),
					EntityBean.class);
			commentListBean.setCommentBean(list);
			commentListBean.setEntity(entityBean);
			commentListBean.setNote(noteBean);
			commentListBean.setPoker_list(poker_list);
			Logger.e(TAG, "commentListBean--->" + commentListBean.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comAdapter = new ArrayListAdapter<CommentBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.comment_item,
							null);
					holder = new ViewHolder();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
					holder.comment_item_iv_islike.setVisibility(View.GONE);
					holder.comment_item_iv_status.setVisibility(View.GONE);
					holder.comment_item_iv_iscom.setVisibility(View.GONE);
				}
				final CommentBean bean = mList.get(position);

				if (position == 0) {
					convertView.setBackgroundColor(Color.WHITE);
					holder.comment_item_iv_islike.setVisibility(View.VISIBLE);
					holder.comment_item_iv_status.setVisibility(View.VISIBLE);
					if ("1".equals(commentListBean.getNote().getPoke_already())) {
						holder.comment_item_iv_islike
								.setImageResource(R.drawable.good_press);
					} else {
						holder.comment_item_iv_islike
								.setImageResource(R.drawable.good);
					}
					holder.comment_item_iv_islike
							.setOnClickListener(CommentTalkAct.this);

					// if
					// ("0".equals(commentListBean.getNote().getComment_count()))
					// {
					// holder.comment_item_iv_iscom
					// .setImageResource(R.drawable.comment);
					// } else {
					// holder.comment_item_iv_iscom
					// .setImageResource(R.drawable.comment_press);
					// }
					holder.comment_item_iv_iscom.setVisibility(View.VISIBLE);
					holder.comment_item_iv_line.setVisibility(View.VISIBLE);
					if ("0".equals(commentListBean.getNote().getIs_selected())) {
						holder.comment_item_iv_status.setVisibility(View.GONE);
					} else {
						holder.comment_item_iv_status
								.setVisibility(View.VISIBLE);
					}

					holder.comment_item_tv_coms.setText(commentListBean
							.getNote().getComment_count());
					holder.comment_item_tv_likes.setText(commentListBean
							.getNote().getPoke_count());
				} else {
					convertView.setBackgroundColor(Color.argb(250, 250, 250,
							250));
					holder.comment_item_iv_islike.setVisibility(View.GONE);
					holder.comment_item_iv_status.setVisibility(View.GONE);
					holder.comment_item_iv_iscom.setVisibility(View.GONE);
					holder.comment_item_iv_line.setVisibility(View.GONE);
				}
				imageLoader.displayImage(bean.getCreator().get240(),
						holder.comment_item_iv_pic, optionsRound);
				holder.comment_item_iv_pic
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(mContext,
										UserAct.class);
								intent.putExtra("data", bean.getCreator());
								startActivity(intent);
							}
						});
				// BitmapUtil
				// .setRoundImage(imageLoader, bean.getCreator()
				// .getAvatar_small(), options,
				// holder.comment_item_iv_pic);

				holder.comment_item_tv_name.setText(bean.getCreator()
						.getNickname());
				holder.comment_item_tv_context.setText(bean.getContent());
				holder.comment_item_tv_time.setText(DateUtils
						.getStandardDate(bean.getCreated_time()));

				return convertView;
			}
		};

		comment_talk_lv.setAdapter(comAdapter);
		comAdapter.setList(list);
		comment_talk_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				reply_to_comment = list.get(arg2).getComment_id();
				reply_to_user = list.get(arg2).getCreator().getUser_id();
				ed_text.setHint("回复 "
						+ list.get(arg2).getCreator().getNickname());
			}
		});

	}

	private class ViewHolder {

		@ViewInject(R.id.comment_item_iv_pic)
		private ImageView comment_item_iv_pic;

		@ViewInject(R.id.comment_item_iv_status)
		private ImageView comment_item_iv_status;

		@ViewInject(R.id.comment_item_iv_line)
		private ImageView comment_item_iv_line;

		@ViewInject(R.id.comment_item_iv_islike)
		private ImageView comment_item_iv_islike;

		@ViewInject(R.id.comment_item_iv_iscom)
		private ImageView comment_item_iv_iscom;

		@ViewInject(R.id.comment_item_tv_name)
		private TextView comment_item_tv_name;

		@ViewInject(R.id.comment_item_tv_context)
		private TextView comment_item_tv_context;

		@ViewInject(R.id.comment_item_tv_likes)
		private TextView comment_item_tv_likes;

		@ViewInject(R.id.comment_item_tv_coms)
		private TextView comment_item_tv_coms;

		@ViewInject(R.id.comment_item_tv_time)
		private TextView comment_item_tv_time;

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.comment_item_iv_islike:
			if (commentListBean.getNote().getPoke_already().equals("0")) {
				sendConnectionPOST(Constant.TOPY
						+ commentListBean.getEntity().getEntity_id()
						+ "/poke/1/", new String[] {}, new String[] {}, PY1,
						false);
			} else {
				sendConnectionPOST(Constant.TOPY
						+ commentListBean.getEntity().getEntity_id()
						+ "/poke/0/", new String[] {}, new String[] {}, PY2,
						false);
			}
			break;
		case R.id.comment_item_iv_pic:
			NoteBean noteBean = (NoteBean) arg0.getTag();
			Intent intent = new Intent(mContext, UserAct.class);
			intent.putExtra("data", noteBean.getCreator());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
