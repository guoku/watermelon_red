/**

 */
package com.guoku.guokuv4.bean;

import java.io.Serializable;

import com.guoku.guokuv4.config.Constant;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-18 下午2:46:09 分享实体类数据
 */
public class Sharebean implements Serializable {

	String title;// 标题

	String context;// 内容

	String aricleUrl;// 文章url

	String imgUrl;// 图片url
	
	String aricleId;//文章id
	
	private boolean is_dig;//是否赞过
	
	public boolean isIs_dig() {
		return is_dig;
	}

	public void setIs_dig(boolean is_dig) {
		this.is_dig = is_dig;
	}

	public String getAricleId() {
		return aricleId;
	}

	public void setAricleId(String aricleId) {
		this.aricleId = aricleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getAricleUrl() {
		return aricleUrl;
	}

	public void setAricleUrl(String aricleUrl) {
		this.aricleUrl = aricleUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
