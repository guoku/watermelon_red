package com.ekwing.students.utils;

public class NumChangeChineUtils {
	static String str = null;

	public static String numToChina(int num) {
		int size = (num + "").length();
		if (size == 1) {
			str = chengNumToChina(num);
		} else if (size == 2) {
			str = chengNumToChina(num / 10) + "十";
			if (num % 10 != 0) {
				str += chengNumToChina(num % 10);
			}
		} else if (size == 3) {
			str = chengNumToChina(num / 100) + "百";
			if (num % 100 != 0) {
				int num1 = num % 100;
				str += chengNumToChina(num1 / 10) + "十";
				if (num % 10 != 0) {
					str += chengNumToChina(num1 % 10);
				}
			}
		}
		return str;
	}

	private static String chengNumToChina(int a) {
		StringBuffer sb = new StringBuffer();
		String[] str = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		return sb.append(str[a]).toString();
	}
}