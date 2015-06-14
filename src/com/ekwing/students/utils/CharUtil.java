package com.ekwing.students.utils;

import java.util.HashMap;

public class CharUtil {
	private static HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ih", "ɪ");
		map.put("ax", "ə");
		map.put("oh", "ɒ");
		map.put("uh", "ʊ");
		map.put("ah", "ʌ");
		map.put("eh", "e");
		map.put("ae", "æ");
		map.put("iy", "i:");
		map.put("er", "ɜ:");
		map.put("axr", "ɚ");
		map.put("ao", "ɔ:");
		map.put("uw", "u:");
		map.put("aa", "ɑ:");
		map.put("ey", "eɪ");
		map.put("ay", "aɪ");
		map.put("oy", "ɔɪ");
		map.put("aw", "aʊ");
		map.put("ow", "әʊ");
		map.put("ia", "ɪə");
		map.put("ea", "ɛә");
		map.put("ua", "ʊə");
		map.put("p", "p");
		map.put("b", "b");
		map.put("t", "t");
		map.put("d", "d");
		map.put("k", "k");
		map.put("g", "g");
		map.put("l", "l");
		map.put("r", "r");
		map.put("m", "m");
		map.put("n", "n");
		map.put("ng", "ŋ");
		map.put("hh", "h");
		map.put("s", "s");
		map.put("z", "z");
		map.put("th", "θ");
		map.put("dh", "ð");
		map.put("f", "f");
		map.put("v", "v");
		map.put("w", "w");
		map.put("y", "j");
		map.put("sh", "ʃ");
		map.put("zh", "ʒ");
		map.put("ch", "tʃ");
		map.put("jh", "dʒ");
		return map;
	}

	public static String getChar(String charT) {

		HashMap<String, String> map = getMap();
		String ret;
		if (map.containsKey(charT)) {
			ret = map.get(charT);
		} else {
			ret = "";
		}
		return ret;
	}
}
