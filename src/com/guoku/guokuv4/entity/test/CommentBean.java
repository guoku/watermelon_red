package com.guoku.guokuv4.entity.test;

public class CommentBean {
	private String replied_comment_id, note_id, comment_id, content, post_time,
			created_time;
	private UserBean creator;

	public String getReplied_comment_id() {
		return replied_comment_id;
	}

	public void setReplied_comment_id(String replied_comment_id) {
		this.replied_comment_id = replied_comment_id;
	}

	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
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

	@Override
	public String toString() {
		return "CommentBean [replied_comment_id=" + replied_comment_id
				+ ", note_id=" + note_id + ", comment_id=" + comment_id
				+ ", content=" + content + ", post_time=" + post_time
				+ ", created_time=" + created_time + ", creator=" + creator
				+ "]";
	}

}
