//package com.ekwing.students.customview;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Gravity;
//import android.widget.TextView;
//
//import com.guoku.R;
//
///**
// * 自定义进度对话框
// * 
// * @author di.chao
// * 
// */
//public class CustomGifDialog extends Dialog {
//	// private Context context;
//
//	public CustomGifDialog(Context context, int score) {
//		super(context, R.style.CustomGifDialog);
//		// this.context = context;
//		setContentView(R.layout.gif_image);
//		GifMovieView confirm_gif = (GifMovieView) findViewById(R.id.confirm_gif);
//		if (score < 60) {
//			confirm_gif.setMovieResource(R.drawable.gif_bad);
//		} else {
//			confirm_gif.setMovieResource(R.drawable.gif_good);
//		}
//		getWindow().getAttributes().gravity = Gravity.CENTER;
//		// getWindow().getAttributes().width = Utils.getScreenWidth(context)/3;
//		// getWindow().getAttributes().height = Utils.getScreenWidth(context)/3;
//		this.setCancelable(true);
//		this.setCanceledOnTouchOutside(false);
//	}
//
//	public CustomGifDialog(Context context, int score, int type) {
//		super(context, R.style.CustomGifDialog);
//		// this.context = context;
//		setContentView(R.layout.gif_image);
//		GifMovieView confirm_gif = (GifMovieView) findViewById(R.id.confirm_gif);
//		if (score < 60) {
//			confirm_gif.setMovieResource(R.drawable.gif_bad);
//		} else {
//			confirm_gif.setMovieResource(R.drawable.gif_good);
//		}
//		getWindow().getAttributes().gravity = Gravity.CENTER;
//		// getWindow().getAttributes().width = Utils.getScreenWidth(context)/3;
//		// getWindow().getAttributes().height = Utils.getScreenWidth(context)/3;
//		this.setCancelable(true);
//		this.setCanceledOnTouchOutside(false);
//	}
//
//	public CustomGifDialog(Context context, boolean score) {
//		super(context, R.style.CustomGifDialog);
//		// this.context = context;
//		setContentView(R.layout.gif_image);
//		GifMovieView confirm_gif = (GifMovieView) findViewById(R.id.confirm_gif);
//		if (score == true) {
//			confirm_gif.setMovieResource(R.drawable.gif_right);
//		} else {
//			confirm_gif.setMovieResource(R.drawable.gif_error);
//		}
//		getWindow().getAttributes().gravity = Gravity.CENTER;
//		// getWindow().getAttributes().width = Utils.getScreenWidth(context)/3;
//		// getWindow().getAttributes().height = Utils.getScreenWidth(context)/3;
//		this.setCancelable(true);
//		this.setCanceledOnTouchOutside(false);
//	}
//
//	/**
//	 * 提示内容
//	 * 
//	 * @param strMessage
//	 * @return
//	 * 
//	 */
//	public void setMessage(String strMessage) {
//		TextView tvMsg = (TextView) this.findViewById(R.id.gif_tv_message);
//		tvMsg.setText(strMessage);
//	}
//
// }
