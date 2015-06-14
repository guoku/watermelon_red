package com.guoku.guokuv4.entity.test;

public class MessageBean {
	private String created_time, type;
	private MessageItemBean content;

	public String getCreated_time() {
		return created_time;
	}

	public String getEntity_id() {
		if (content.getNote() != null
				&& content.getNote().getEntity_id() != null) {
			return content.getNote().getEntity_id();
		} else if (content.getEntity() != null
				&& content.getEntity().getEntity_id() != null) {
			return content.getEntity().getEntity_id();
		} else
			return null;
	}

	// public void setEntity_id(String entity_id) {
	// this.entity_id = entity_id;
	// }

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MessageItemBean getContent() {
		return content;
	}

	public void setContent(MessageItemBean content) {
		this.content = content;
	}

}
