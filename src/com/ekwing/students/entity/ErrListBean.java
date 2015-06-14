package com.ekwing.students.entity;

import java.io.Serializable;

public class ErrListBean implements Serializable {
	private String err;

	public String getErr() {
		if (err == null) {
			err = "";
		}
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}
}
