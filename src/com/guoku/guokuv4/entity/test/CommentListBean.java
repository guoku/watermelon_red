package com.guoku.guokuv4.entity.test;

import java.util.ArrayList;

public class CommentListBean {
	private NoteBean note;
	private EntityBean entity;
	private String poker_list;
	private ArrayList<CommentBean> commentBean;

	public NoteBean getNote() {
		return note;
	}

	public void setNote(NoteBean note) {
		this.note = note;
	}

	public EntityBean getEntity() {
		return entity;
	}

	public void setEntity(EntityBean entity) {
		this.entity = entity;
	}

	public String getPoker_list() {
		return poker_list;
	}

	public void setPoker_list(String poker_list) {
		this.poker_list = poker_list;
	}

	public ArrayList<CommentBean> getCommentBean() {
		return commentBean;
	}

	public void setCommentBean(ArrayList<CommentBean> commentBean) {
		this.commentBean = commentBean;
	}

	@Override
	public String toString() {
		return "CommentListBean [note=" + note + ", entity=" + entity
				+ ", poker_list=" + poker_list + ", commentBean=" + commentBean
				+ "]";
	}

}
