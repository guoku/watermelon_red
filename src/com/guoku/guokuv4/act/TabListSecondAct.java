/**

 */
package com.guoku.guokuv4.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.adapter.GridView3vAdapter;
import com.guoku.guokuv4.adapter.SeachCommodityTypeAdapter;
import com.guoku.guokuv4.bean.TagTwo;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.view.HeaderGridView;
import com.guoku.guokuv4.view.HeaderListview;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-24 下午3:02:42 
 * 二级分类商品结果
 */
public class TabListSecondAct extends NetWorkActivity implements OnCheckedChangeListener, OnItemClickListener{
	
	private final int TAG_CATABLIST = 1001;//二级品类商品
	private final int TAG_PROINFO = 1002;//获取商品信息
	
	
	
	@ViewInject(R.id.tab_lv)
	private HeaderListview tab_lv;

	@ViewInject(R.id.tab_gv)
	private HeaderGridView tab_gv;
	
	@ViewInject(R.id.check_box_like_time)
	CheckBox cbLikeTime;// tab上的喜欢、时间切换按钮

	@ViewInject(R.id.check_box_show)
	CheckBox cbShow;// tab上的显示方式按钮
	
	private GridView3vAdapter gvAdapter;
	private EntityAdapter lvAdapter;
	
	TagTwo tagTwo;//从品类分类过来的
	Tab2Bean tab2Bean;//从品类搜索页过来的
	
	String title;//标题
	
	String id;//分类id
	
	
	private ArrayList<EntityBean> listTag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_list_second);
		
		init();
		sendData(id, TIME);
	}
	
	private void init(){
		
		gvAdapter = new GridView3vAdapter(mContext);
		tab_gv.setAdapter(gvAdapter);
		tab_gv.setOnItemClickListener(this);
		
		lvAdapter = new EntityAdapter(mContext);
		tab_lv.setAdapter(lvAdapter);
		tab_lv.setOnItemClickListener(this);
		
		cbLikeTime.setOnCheckedChangeListener(this);
		cbShow.setOnCheckedChangeListener(this);
		
		
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case TAG_CATABLIST:
			listTag = (ArrayList<EntityBean>) JSON.parseArray(result,
					EntityBean.class);
			gvAdapter.setList(listTag);
			lvAdapter.setList(listTag);
			break;
		case TAG_PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		tagTwo = (TagTwo) getIntent().getExtras().getSerializable(TabAct.SECOND_ACT_ONTENT);
		tab2Bean = (Tab2Bean) getIntent().getExtras().getSerializable(SeachCommodityTypeAdapter.SEACH_TAG);
		
		if(tagTwo != null){
			id = String.valueOf(tagTwo.getCategory_id());
			title = tagTwo.getCategory_title();
		}
		if(tab2Bean != null){
			id = tab2Bean.getCategory_id();
			title = tab2Bean.getCategory_title();
		}
		
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
		getTitleLayoutSeach().setVisibility(View.VISIBLE);
	}
	
	private void sendData(String id, String value) {
		
		sendConnection(Constant.CATEGORY + id + "/entity/", new String[] {
				"count", "offset", "reverse", "sort"}, new String[] { "30",
				"0", "0", value}, TAG_CATABLIST, true);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
		if (arg0 == cbLikeTime) {
			if (arg1) {
				sendData(id, LIKE);
			} else {
				sendData(id, TIME);
			}
		}
		
		if (arg0 == cbShow) {
			if (arg1) {
				tab_gv.setVisibility(View.VISIBLE);
				tab_lv.setVisibility(View.GONE);
			} else {
				tab_gv.setVisibility(View.GONE);
				tab_lv.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		sendConnection(Constant.PROINFO + listTag.get(arg2).getEntity_id()
				+ "/", new String[] { "entity_id" },
				new String[] { listTag.get(arg2).getEntity_id() },
				TAG_PROINFO, true);
	}

}
