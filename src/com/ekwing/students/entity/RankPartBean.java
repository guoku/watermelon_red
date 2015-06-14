package com.ekwing.students.entity;

public class RankPartBean {
	private String numb;
	private String pic;
	private String name;
	private String gread;

	@Override
	public String toString() {
		return "RankPartBean [numb=" + numb + ", pic=" + pic + ", name=" + name + ", gread=" + gread + "]";
	}

	public String getNumb() {
		if (numb == null) {
			numb = "";
		}
		return numb;
	}

	public void setNumb(String numb) {
		this.numb = numb;
	}

	public String getPic() {
		if (pic == null) {
			pic = "";
		}
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGread() {
		if (gread == null) {
			gread = "0";
		}
		return gread;
	}

	public void setGread(String gread) {
		this.gread = gread;
	}

}
