package com.guoku.guokuv4.homepage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;

public class OneFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fargment_one, null);
		
		init(view);
		return view;
	}
	
	private void init(View view){
		
		Uri uri = Uri.parse("http://imgcdn.guoku.com/images/310/dd5196bf406c8b94643e84a207907754.jpg");
		SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.my_image_view);
		draweeView.setImageURI(uri);
	}
}