/**

 */
package com.guoku.guokuv4.bean;

import com.guoku.guokuv4.entity.test.NoteBean;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月5日 下午5:03:42 
 * 评论
 */
public class CommentsBean {
	
	
	public boolean isAdd;//true:增加  false：更新
	
	public String commentValue;
	
	public String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCommentValue() {
		return commentValue;
	}

	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isUpdata) {
		this.isAdd = isUpdata;
	}
	

}
