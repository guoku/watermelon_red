package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.lidroid.xutils.db.annotation.Id;

public class MineBean implements Serializable {
	@Id
	private String times; // 时间
	private String mid; // 消息id
	private String type; // 信息类型
	private String context; // 消息内容
	private String others; // 其它显示文字
	private String srtImpotContext; // 需要变色的部分字符串
	private String srtImgs; // 头像或者勋章地址字条串
	private List<ChangeColorBean> impotContext = new ArrayList<ChangeColorBean>(); // 需要变色

	private List<UrlBean> imgs = new ArrayList<UrlBean>(); // 头像或者勋章地址

	public String getMid() {
		if(mid == null){
			mid="";
		}
		return mid;
	}

	public String getType() {
		if (type == null) {
			type = "";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getOthers() {
		if (others == null) {
			others = "";
		}
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getTimes() {
		if (times == null||"".equals(times)) {
			times = "0";
		}
		int parTime =0;
		try {
			parTime = (int) Double.parseDouble(times);
		} catch (Exception e) {
			parTime = 0;
		}
		
		return parTime+"";
	}

	public String getSrtImpotContext() {
		if (srtImpotContext == null) {
			srtImpotContext = "";
		}
		return srtImpotContext;
	}

	public void setSrtImpotContext(String srtImpotContext) {
		this.srtImpotContext = srtImpotContext;
	}

	public String getSrtImgs() {
		if (srtImgs == null) {
			srtImgs = "";
		}
		return srtImgs;
	}

	public void setSrtImgs(String srtImgs) {
		this.srtImgs = srtImgs;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getContext() {
		if (context == null) {
			context = "";
		}
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public List<ChangeColorBean> getImpotContext() {
		if (impotContext == null||"".equals(impotContext)) {
			impotContext = new ArrayList<ChangeColorBean>();
		}

		return impotContext;
	}

	public void setImpotContext(List<ChangeColorBean> impotContext) {
		this.impotContext = impotContext;
	}

	public List<UrlBean> getImgs() {
		if (imgs == null||"".equals(imgs)) {
			imgs = new ArrayList<UrlBean>();
		}
		return imgs;
	}

	public void setImgs(List<UrlBean> imgs) {
		this.imgs = imgs;
	}

	@Override
	public String toString() {
		return "MineBean [mid=" + mid + ", type=" + type + ", times=" + times + ", context=" + context + ", others=" + others + ", srtImpotContext="
				+ srtImpotContext + ", srtImgs=" + srtImgs + ", impotContext=" + impotContext + ", imgs=" + imgs + "]";
	}

}
