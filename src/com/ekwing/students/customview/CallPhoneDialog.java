package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ekwing.students.utils.ToastUtil;
import com.ekwing.students.utils.Utils;
import com.guoku.R;

/**
 * VipDialog
 * 
 * @author di.chao
 * 
 */
public class CallPhoneDialog extends Dialog {
	private Context context;

	public CallPhoneDialog(final Context context) {
		super(context, R.style.CustomProgressDialog);
		this.context = context;
		setContentView(R.layout.call_phone_layout);
		TextView call_phone = (TextView) findViewById(R.id.call_phone);
		TextView call_dialog_confirm_btn = (TextView) findViewById(R.id.call_dialog_confirm_btn);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setDimAmount(0.75f);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
		call_phone.setText("想要体验更多优质功能，请拨打400-015-1011开通帐号。");
		setStringText(call_phone, Color.parseColor("#f74812"));
		// String str =
		// "想要体验更多优质功能，请拨打<font color='#f74812'>400-015-1011</font>开通账号。";
		// TextView fromHtml = (TextView) Html.fromHtml(str);
		// call_phone.setText(Html.fromHtml(str));
		// call_phone.setMovementMethod(LinkMovementMethod.getInstance());
		// CharSequence text = call_phone.getText();
		// if (text instanceof Spannable) {
		// // int end = text.length();
		// Spannable sp = (Spannable) call_phone.getText();
		// URLSpan[] urls = sp.getSpans(15, 31, URLSpan.class);
		// // ForegroundColorSpan blueSpan = new
		// // ForegroundColorSpan(Color.parseColor("#f74812"));
		// SpannableStringBuilder style = new SpannableStringBuilder(text);
		// style.clearSpans(); // should clear old spans
		// for (URLSpan url : urls) {
		// MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
		// style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// }
		// call_phone.setText(style);
		// }

		call_phone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				Logger.e("", "-------------------->"+);
				if (Utils.isTabletDevice(context)) {
					ToastUtil.show(context, "该设备不能打电话~");
				} else {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-015-1011"));
					context.startActivity(intent);
				}
			}
		});
		call_dialog_confirm_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CallPhoneDialog.this.dismiss();
			}
		});
	}

	private class MyURLSpan extends ClickableSpan {
		private String mUrl;

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			// Toast.makeText(ctx, "" + mUrl, Toast.LENGTH_SHORT).show();
			ToastUtil.show(context, "1111111111111111111111");
			ToastUtil.show(context, "1111111111111111111111" + Utils.isTabletDevice(context));
			if (Utils.isTabletDevice(context)) {
				ToastUtil.show(context, "该设备不能打电话~");
			} else {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-015-1011"));
				context.startActivity(intent);
			}
		}
	}

	private void setStringText(TextView tv, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
		ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
		builder.setSpan(blueSpan, 14, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(builder);
	}

}
