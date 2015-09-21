package com.ekwing.students.customview;

import java.io.Serializable;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guoku.R;

/**
 * 支付对话框
 * 
 * @author di.chao
 */
public class CustomPayDialog extends Dialog {
	private Context context;
	private TextView title;
	private Button okBtn;
	private Button cancle;
	private PayDialogListener listener;

	private View vLine, hLine;
	private static final int WAIT_TIME = 32;
	private TimerCount timer;

	public CustomPayDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		this.context = context;
		setContentView(R.layout.dialog_layout);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		this.setCancelable(false);
		title = (TextView) this.findViewById(R.id.title);
		title.setText("感谢您对来这游的支持,点击确认后进行付款?");
		cancle = (Button) this.findViewById(R.id.cancle);
		okBtn = (Button) this.findViewById(R.id.ok);
		vLine = this.findViewById(R.id.vline);
		hLine = this.findViewById(R.id.hline);
		okBtn.setText("确认");
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomPayDialog.this.dismiss();
			}
		});
	}

	public void setBtnListener(PayDialogListener listener) {
		this.listener = listener;
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (CustomPayDialog.this.listener != null) {
					CustomPayDialog.this.listener.doPayButton(okBtn,
							CustomPayDialog.this);
					// 确定按钮被点击
					timer = new TimerCount(WAIT_TIME);
					timer.start();
					CustomPayDialog.this.setMessage("付款中...");
					setButtonHide();
				}
			}
		});
	}

	/**
	 * 延迟2s消失
	 */
	public void holdDismiss() {
		new HoldTimer(2).start();
	}

	public void setButtonHide() {
		okBtn.setVisibility(View.INVISIBLE);
		cancle.setVisibility(View.INVISIBLE);
		hLine.setVisibility(View.INVISIBLE);
		vLine.setVisibility(View.INVISIBLE);
	}

	public void setButtonShow() {
		okBtn.setVisibility(View.VISIBLE);
		cancle.setVisibility(View.VISIBLE);
		hLine.setVisibility(View.VISIBLE);
		vLine.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置消息提示
	 */
	public void setMessage(String strMessage) {
		title.setText(strMessage);
	}

	public interface PayDialogListener {
		public void doPayButton(Button btn, CustomPayDialog dialog);
	}

	private class TimerCount extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;
		private int count = 0;

		public TimerCount(int second) {
			super(second * 1000l, 1000l);
		}

		@Override
		public void onFinish() {
			count = 0;
			CustomPayDialog.this.dismiss();
			setMessage("感谢您对来这游的支持,点击确认后进行付款?");
			setButtonShow();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			count++;
			if (count == 30) {
				setMessage("网络有问题，请稍后再试！");
			}
		}
	}

	private class HoldTimer extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;

		public HoldTimer(int second) {
			super(second * 1000l, 1000l);
		}

		@Override
		public void onFinish() {
			if (timer != null) {
				timer.cancel();
			}
			CustomPayDialog.this.dismiss();
			setMessage("感谢您对来这游的支持,点击确认后进行付款?");
			setButtonShow();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}
