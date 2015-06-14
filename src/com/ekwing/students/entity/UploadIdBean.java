package com.ekwing.students.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "voices")
public class UploadIdBean {

	@Id
	private int _id;
	@Column(column = "sid")
	private String sid; // 每道题唯一的id
	@Column(column = "path")
	private String path; // 本地音频地址
	@Column(column = "cid")
	private String cid; // 作业唯一id
	@Column(column = "type")
	private String type; // 1:作业， 2：闯关

	public String getType() {  
		return type;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "UploadIdBean [_id=" + _id + ", sid=" + sid + ", path=" + path + ", cid=" + cid + ", type=" + type + "]";
	}

}
