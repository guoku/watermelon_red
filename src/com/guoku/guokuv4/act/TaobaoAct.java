//package com.guoku.guokuv4.act;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.umeng.socialize.net.utils.Base64;
//
//public class TaobaoAct {
//	public static void main(String[] args) throws Exception {
//		String topParams = URLDecoder.decode(
//				"bmljaz3kuK3mlofmtYvor5VuaWNrJnRzPTEzMzc4MjU0NDM2NzY%3D",
//				"UTF-8");
//		String topSign = URLDecoder.decode("kxQT%2F6j7eblJLORflcz9qw%3D%3D",
//				"UTF-8");
//		String appSecret = "28dbdd21127f438a59db0cb9f8f620f6";
//		// 先校验签名
//		boolean success = verifyTopResponse(topParams, topSign, appSecret);
//		if (success) {
//			// 再解析参数
//			System.out.println(convertBase64StringtoMap(topParams));
//			// TODO 实际使用时建议再校验时间戳，比如时间误差5分钟内视为有效
//		}
//
//	}
//
//	/**
//	 * 验证TOP回调地址的签名是否合法。要求所有参数均为已URL反编码的。
//	 * 
//	 * @param topParams
//	 *            TOP私有参数（未经BASE64解密）
//	 * @param topSign
//	 *            TOP回调签名
//	 * @param appSecret
//	 *            应用密钥
//	 * @return 验证成功返回true，否则返回false
//	 * @throws NoSuchAlgorithmException
//	 * @throws IOException
//	 */
//	public static boolean verifyTopResponse(String topParams, String topSign,
//			String appSecret) throws NoSuchAlgorithmException, IOException {
//		StringBuilder result = new StringBuilder();
//		MessageDigest md5 = MessageDigest.getInstance("MD5");
//		result.append(topParams).append(appSecret);
//		byte[] bytes = md5.digest(result.toString().getBytes("UTF-8"));
//		BASE64Encoder encoder = new BASE64Encoder();
//		return encoder.encode(bytes).equals(topSign);
//	}
//
//	/**
//	 * 把经过BASE64编码的字符串转换为Map对象
//	 * 
//	 * @param str
//	 * @return
//	 * @throws Exception
//	 */
//	private static Map<String, String> convertBase64StringtoMap(String str) {
//		if (str == null)
//			return null;
//		String keyvalues = null;
//		try {
//			keyvalues = new String(Base64.decodeBase64(URLDecoder.decode(str,
//					"UTF-8").getBytes("UTF-8")));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String[] keyvalueArray = keyvalues.split("\\&");
//		Map<String, String> map = new HashMap<String, String>();
//		for (String keyvalue : keyvalueArray) {
//			String[] s = keyvalue.split("\\=");
//			if (s == null || s.length != 2)
//				return null;
//			map.put(s[0], s[1]);
//		}
//		return map;
//	}
//
// }
