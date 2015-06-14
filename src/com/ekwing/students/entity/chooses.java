package com.ekwing.students.entity;

public class chooses {
	private OptionBean A, B, C, D;

	public OptionBean getA() {
		return A;
	}

	public void setA(OptionBean a) {
		A = a;
	}

	public OptionBean getB() {
		return B;
	}

	public void setB(OptionBean b) {
		B = b;
	}

	public OptionBean getC() {
		return C;
	}

	public void setC(OptionBean c) {
		C = c;
	}

	public OptionBean getD() {
		return D;
	}

	public void setD(OptionBean d) {
		D = d;
	}

	public chooses(OptionBean a, OptionBean b, OptionBean c, OptionBean d) {
		this.A = a;
		this.B = b;
		this.C = c;
		this.D = d;
	}

	private chooses() {
	}

}
