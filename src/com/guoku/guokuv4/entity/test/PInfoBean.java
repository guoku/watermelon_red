package com.guoku.guokuv4.entity.test;

import java.io.Serializable;
import java.util.ArrayList;

public class PInfoBean implements Serializable {
	private EntityBean entity;
	private ArrayList<NoteBean> note_list;
	private ArrayList<UserBean> like_user_list;
	private String top_note;

	public EntityBean getEntity() {
		return entity;
	}

	public String getTop_note() {
		if (note_list != null && note_list.size() > 0) {
			return note_list.get(0).getContent();
		}
		return "";
	}

	public void setTop_note(String top_note) {
		this.top_note = top_note;
	}

	public void setEntity(EntityBean entity) {
		this.entity = entity;
	}

	public ArrayList<NoteBean> getNote_list() {
		return note_list;
	}

	public void setNote_list(ArrayList<NoteBean> note_list) {
		this.note_list = note_list;
	}

	public ArrayList<UserBean> getLike_user_list() {
		if (like_user_list.size() > 7) {
			ArrayList<UserBean> list = new ArrayList<UserBean>();
			list.add(like_user_list.get(0));
			list.add(like_user_list.get(1));
			list.add(like_user_list.get(2));
			list.add(like_user_list.get(3));
			list.add(like_user_list.get(4));
			list.add(like_user_list.get(5));
			list.add(like_user_list.get(7));
			return list;
		}
		return like_user_list;
	}

	public void setLike_user_list(ArrayList<UserBean> like_user_list) {
		this.like_user_list = like_user_list;
	}

	@Override
	public String toString() {
		return "PInfoBean [entity=" + entity + ", note_list=" + note_list
				+ ", like_user_list=" + like_user_list + "]";
	}

}
