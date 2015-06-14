package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

public class NoteBean implements Serializable {
	private String entity_id, post_time, user_id, is_selected, poker_id_list,
			poke_already, updated_time, content, comment_count, note_id,
			poke_count, created_time, title, chief_image;
	private UserBean creator;

	public String getChief_image() {
		return chief_image;
	}

	public void setChief_image(String chief_image) {
		this.chief_image = chief_image;
	}

	public String get50() {
		if (chief_image.contains("images")) {
			return chief_image.replaceFirst("images", "images/50");
		}
		return chief_image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEntity_id() {
		return entity_id;
	}

	@Override
	public String toString() {
		return "NoteBean [entity_id=" + entity_id + ", post_time=" + post_time
				+ ", user_id=" + user_id + ", is_selected=" + is_selected
				+ ", poker_id_list=" + poker_id_list + ", poke_already="
				+ poke_already + ", updated_time=" + updated_time
				+ ", content=" + content + ", comment_count=" + comment_count
				+ ", note_id=" + note_id + ", poke_count=" + poke_count
				+ ", created_time=" + created_time + ", creator=" + creator
				+ "]";
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getIs_selected() {
		return is_selected;
	}

	public void setIs_selected(String is_selected) {
		this.is_selected = is_selected;
	}

	public String getPoker_id_list() {
		return poker_id_list;
	}

	public void setPoker_id_list(String poker_id_list) {
		this.poker_id_list = poker_id_list;
	}

	public String getPoke_already() {
		return poke_already;
	}

	public void setPoke_already(String poke_already) {
		this.poke_already = poke_already;
	}

	public String getUpdated_time() {
		if (updated_time != null) {

		}
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment_count() {
		if (comment_count != null && "0".equals(comment_count)) {
			return "";
		}
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}

	public String getPoke_count() {
		if (poke_count != null && "0".equals(poke_count)) {
			return "";
		}
		return poke_count;
	}

	public void setPoke_count(String poke_count) {
		this.poke_count = poke_count;
	}

	public void setPoke_countADD() {
		if (getPoke_count().equals("")) {
			poke_count = "0";
		}
		int buf = Integer.parseInt(poke_count) + 1;
		poke_count = buf + "";
	}

	public void setPoke_countCut() {
		if (getPoke_count().equals("")) {
			poke_count = "0";
		}
		int buf = Integer.parseInt(poke_count) - 1;
		if (buf < 0) {
			buf = 0;
		}
		poke_count = buf + "";
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public UserBean getCreator() {
		return creator;
	}

	public void setCreator(UserBean creator) {
		this.creator = creator;
	}

}
