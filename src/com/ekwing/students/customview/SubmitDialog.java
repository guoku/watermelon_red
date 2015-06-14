//package com.ekwing.students.customview;
//
//import java.util.ArrayList;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ekwing.students.config.Logger;
//import com.ekwing.students.entity.WordsBean;
//import com.guoku.R;
//
///**
// * VipDialog
// * 
// * @author di.chao
// * 
// */
//public class SubmitDialog extends Dialog {
//
//	public static final int UPDATE_AUDIO = 59;
//	public static final int SUBMITAUDIO = 60;
//
//	private TextView tv_progress;
//	private ImageView iv_back;
//	private View v;
//	private int haveFinish;
//	private int haveSubmitAudio;
//	public static boolean iscontinue=true;
//	private ArrayList<WordsBean> articleLists;
//
//	public SubmitDialog(Context context) {
//		super(context, R.style.VipDialog);
//		v = View.inflate(context, R.layout.dialog_submit_audio, null);
//		tv_progress = (TextView) v.findViewById(R.id.tv_current_submit_progress);
//		iv_back = (ImageView) v.findViewById(R.id.current_submit_progress);
//		 SubmitDialog.this.setCancelable(true);
//		 SubmitDialog.this.setCanceledOnTouchOutside(false);
//		getWindow().getAttributes().gravity = Gravity.CENTER;
//		getWindow().setDimAmount(0.75f);
//		// getWindow().setDimAmount(0.4f);
//		WindowManager.LayoutParams wl = getWindow().getAttributes();
//		// wl.width = Utils.getScreenWidth(context)-80;
//		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//		setContentView(v);
//	}
//
//	public boolean need(ArrayList<WordsBean> articleLists) {
//		this.articleLists = articleLists;
//		haveFinish = 0;
//		haveSubmitAudio = 0;
//		for (int i = 0; i < articleLists.size(); i++) {
//			if (articleLists.get(i).getIsSubmit().equals("0") || articleLists.get(i).getIsSubmit().equals("1")
//					|| articleLists.get(i).getIsSubmit().equals("2")) {
//				haveFinish++;
//			}
//			if ("1".equals(articleLists.get(i).getIsSubmit())) {
//				haveSubmitAudio++;
//			}
//		}
//		if (haveFinish == haveSubmitAudio) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public void init(ArrayList<WordsBean> articleLists) {
//		need(articleLists);
//		double pro = haveSubmitAudio * 100 / haveFinish;
//		
//		Logger.e("pro", "haveFinish ======>" + haveFinish);
//		Logger.e("pro", "haveSubmitAudio ======>" + haveSubmitAudio);
//		Logger.e("pro", "pro ======>" + pro);
//		if (pro <= 10) {
//			iv_back.setImageResource(R.drawable.progress1);
//		} else if (pro <= 20) {
//			iv_back.setImageResource(R.drawable.progress2);
//		} else if (pro <= 30) {
//			iv_back.setImageResource(R.drawable.progress3);
//		} else if (pro <= 40) {
//			iv_back.setImageResource(R.drawable.progress4);
//		} else if (pro <= 50) {
//			iv_back.setImageResource(R.drawable.progress5);
//		} else if (pro <= 60) {
//			iv_back.setImageResource(R.drawable.progress6);
//		} else if (pro <= 70) {
//			iv_back.setImageResource(R.drawable.progress7);
//		} else if (pro <= 80) {
//			iv_back.setImageResource(R.drawable.progress8);
//		} else if (pro <= 90) {
//			iv_back.setImageResource(R.drawable.progress9);
//		} else if (pro <= 100) {
//			iv_back.setImageResource(R.drawable.progress10);
//		}
//		tv_progress.setText(pro + "%");
//	}
//
//	public void submitDatas(final Handler mHandler) {
//		new Thread() {
//			public void run() {
//				while (iscontinue) {
//					if (need(articleLists)) {
//						iscontinue = false;
//						mHandler.sendEmptyMessage(SUBMITAUDIO);
//					} else {
//						mHandler.sendEmptyMessage(UPDATE_AUDIO);
//					}
//					SystemClock.sleep(300);
//				}
//			};
//		}.start();
//	}
// }
