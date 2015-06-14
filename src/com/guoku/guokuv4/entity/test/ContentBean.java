package com.guoku.guokuv4.entity.test;

public class ContentBean {
	private NoteBean note;
	private EntityBean entity;
	private UserBean liker, user, target;

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public UserBean getTarget() {
		return target;
	}

	public void setTarget(UserBean target) {
		this.target = target;
	}

	public UserBean getLiker() {
		return liker;
	}

	public void setLiker(UserBean liker) {
		this.liker = liker;
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

	@Override
	public String toString() {
		return "ContentBean [note=" + note + ", entity=" + entity + "]";
	}

}
