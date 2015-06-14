package com.ekwing.students.entity;

import java.io.Serializable;

public class Selector implements Serializable{
	private OptionBean A;
	private OptionBean B;
	private OptionBean C;
	private OptionBean D;
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
	@Override
	public String toString() {
		return "Selector [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + "]";
	}
	
	
	

	
	
	
}
