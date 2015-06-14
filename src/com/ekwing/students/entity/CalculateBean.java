package com.ekwing.students.entity;

import java.io.Serializable;

public class CalculateBean implements Serializable {

	private int count;
	private int sum;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

}
