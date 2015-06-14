package com.guoku.guokuv4.entity.test;

public class MessageItemBean {
	private NoteBean note;
	private EntityBean entity;
	private UserBean follower, poker, comment_user, liker;
	private CommentBean comment;

	public UserBean getLiker() {
		return liker;
	}

	public void setLiker(UserBean like) {
		this.liker = like;
	}

	public UserBean getComment_user() {
		return comment_user;
	}

	public void setComment_user(UserBean comment_user) {
		this.comment_user = comment_user;
	}

	public CommentBean getComment() {
		return comment;
	}

	public void setComment(CommentBean comment) {
		this.comment = comment;
	}

	public UserBean getPoker() {
		return poker;
	}

	public void setPoker(UserBean poker) {
		this.poker = poker;
	}

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

	public UserBean getFollower() {
		return follower;
	}

	public void setFollower(UserBean follower) {
		this.follower = follower;
	}

	@Override
	public String toString() {
		return "MessageItemBean [note=" + note + ", entity=" + entity
				+ ", follower=" + follower + "]";
	}

}
