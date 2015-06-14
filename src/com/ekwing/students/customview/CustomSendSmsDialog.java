package com.ekwing.students.customview;

import java.io.Serializable;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoku.R;

/**
 * 发送短信验证码的对话框
 * @author di.chao
 */
public class CustomSendSmsDialog extends Dialog {
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int BOTH = 2;
	public final static int NONE = 3;
    private TextView title;
    private EditText edit;
    private Button okBtn;
    private Button cancle;
    private View hLine;
    private View vLine;
    private int btnStatus;
    private LinearLayout btnLayout;
    private WaitTimer waitTimer;
	public CustomSendSmsDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		setContentView(R.layout.dialog_sendsms_layout);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		this.setCancelable(false);
		title = (TextView) this.findViewById(R.id.title);
        cancle = (Button) this.findViewById(R.id.cancle);
        edit = (EditText) this.findViewById(R.id.edit);
		okBtn = (Button) this.findViewById(R.id.ok);
		btnLayout = (LinearLayout) this.findViewById(R.id.dialog_btn_layout);
		edit.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
//					setRightBtnText("");
					if (waitTimer != null) {
						waitTimer.cancel();
						setRightBtnText("确定");
					}
				}
			}
		});
		vLine = this.findViewById(R.id.vline);
		hLine = this.findViewById(R.id.hline);
		setLeftBtnListener("取消",null);

	}
	/**
	 * 设置对话框样式，现实一个按钮，还是现实两个按钮
	 * @param model default BOTH
	 */
	public void setModel (int model) {
		switch (model) {
		case CustomSendSmsDialog.LEFT:
			okBtn.setVisibility(View.GONE);
			cancle.setVisibility(View.VISIBLE);
			cancle.setBackgroundResource(R.drawable.dialog_btn_bg);
			vLine.setVisibility(View.GONE);
			hLine.setVisibility(View.VISIBLE);
			btnLayout.setVisibility(View.VISIBLE);
			break;
		case CustomSendSmsDialog.RIGHT:
			cancle.setVisibility(View.GONE);
			okBtn.setVisibility(View.VISIBLE);
			okBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
			vLine.setVisibility(View.GONE);
			hLine.setVisibility(View.VISIBLE);
			btnLayout.setVisibility(View.VISIBLE);
			break;
		case CustomSendSmsDialog.BOTH:
			cancle.setVisibility(View.VISIBLE);
			okBtn.setVisibility(View.VISIBLE);
			cancle.setBackgroundResource(R.drawable.dialog_chance_bg);
			okBtn.setBackgroundResource(R.drawable.dialog_ok_bg);
			vLine.setVisibility(View.VISIBLE);
			hLine.setVisibility(View.VISIBLE);
			edit.setVisibility(View.VISIBLE);
			btnLayout.setVisibility(View.VISIBLE);
			break;
		case CustomSendSmsDialog.NONE:
			cancle.setVisibility(View.GONE);
			okBtn.setVisibility(View.GONE);
			vLine.setVisibility(View.GONE);
			hLine.setVisibility(View.GONE);
			edit.setVisibility(View.GONE);
			btnLayout.setVisibility(View.GONE);
			break;
		}
	}
	public void channelBtn () {
		if (waitTimer != null) {
		waitTimer.cancel();
		}
	}
	/**
	 * 设置消息提示
	 */
	public void setMessage(String strMessage) {
		title.setText(strMessage);
	}
	/**
	 * 设置左右按钮多标签以及监听起
	 * @param text
	 * @param listener
	 */
	public void setLeftBtnListener (String text ,final  DialogListener listener) {
		cancle.setText(text);
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
				  listener.doClickButton(okBtn, 0 , edit ,CustomSendSmsDialog.this);
				}
				CustomSendSmsDialog.this.dismiss();
			}
		});
	}
	public void setRightBtnListener (String text , final DialogListener listener) {
		okBtn.setText(text);
		waitTimer =  new WaitTimer(60);
		waitTimer.start();
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
				  listener.doClickButton(okBtn,btnStatus , edit,CustomSendSmsDialog.this);
				}
				if (btnStatus == 1) {
					waitTimer = new WaitTimer(60);
					waitTimer.start();
				}
//				CustomSendSmsDialog.this.dismiss();
			}
		});
	}
   public void setSendCodeStauts () {
	   btnStatus = 1;
	   waitTimer.cancel();
	   setRightBtnText("重新发送");
   }
   public void setCreateOrderStatus () {
	   btnStatus = 0;
	   waitTimer.cancel();
	   waitTimer = new WaitTimer(60);
	   waitTimer.start();
   }
	public void setRightBtnText (String text) {
		okBtn.setText(text);
	}
	/**
	 * 设置左右按钮颜色
	 * @param color
	 */
	public void setLeftBtnColor (int color) {
		cancle.setTextColor(color);
	}
	public void setRightBtnColor (int color) {
		okBtn.setTextColor(color);
	}
	/**
	 * 延迟2s消失
	 */
	public void holdDismiss () {
		this.show();
		new HoldTimer(2).start();
	}
	public void setHoldHintMessage (String msg) {
		edit.setText("");
	    edit.setHint(msg);
	    new HintTimer(4).start();
	}
	public int getBtnStatus () {
		return btnStatus;
	}
	public void setBtnStatus (int status) {
		btnStatus = status;
	}
	private class HoldTimer extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;
		public HoldTimer(int second) {
			super(second * 1000l, 1000l);
		}
		@Override
		public void onFinish() {
			this.cancel();
			CustomSendSmsDialog.this.dismiss();
		}
		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
	private class WaitTimer extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;
		private int wait;
		public WaitTimer(int second) {
			super(second * 1000l, 1000l);
			wait = second;
			btnStatus = 0;
		}
		@Override
		public void onFinish() {
			this.cancel();
			btnStatus = 1;
			setRightBtnText("重新发送");
		}
		@Override
		public void onTick(long millisUntilFinished) {
			wait --;
			setRightBtnText("确定("+wait +"s)");
		}
	}
	private class HintTimer extends CountDownTimer implements Serializable {
		private static final long serialVersionUID = 1L;
		public HintTimer(int second) {
			super(second * 1000l, 1000l);
		}
		@Override
		public void onFinish() {
			this.cancel();
			edit.setHint("验证码");
		}
		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
   public interface DialogListener {
	   public void doClickButton(Button btn ,int btnStatus , EditText edit , CustomSendSmsDialog dialog);
   }
}
