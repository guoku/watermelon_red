package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

public class AccountBean implements Serializable {
	private String session;
	private UserBean user;

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AccountBean [session=" + session + ", user=" + user + "]";
	}

}
