package org.limingnihao.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * String常用方法
 * 
 * @author 黎明你好
 * 
 */
public class StringUtil {

	// 汉字的UTF-8编码起始 19968-40869
	public static int ChineseCharStartIndex = 19968;
	public static int ChineseCharEndIndex = 40869;

	/**
	 * 是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || "null".equals(str.toLowerCase())) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否不是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 编码
	 * 
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		return decode(value, "UTF-8");
	}

	public static String decode(String value, String charset) {
		if (StringUtils.isNotBlank(value)) {
			try {
				return URLDecoder.decode(value, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 得到指定长度的，随机英文字符
	 * 
	 * @param length
	 * @return
	 */
	public static String randomChar(int length) {
		String resultString = "";
		for (int i = 0; i < length; i++) {
			int random = (int) (Math.random() * 100);
			int flag = (random % 2) == 0 ? (int) 'a' : (int) 'A';
			System.out.println(flag);
			int charIndex = (int) Math.round(Math.random() * 26) + flag;
			if (charIndex > (flag + 25)) {
				charIndex = flag + 25;
			}
			resultString += (char) charIndex;
		}
		return resultString;
	}

	/**
	 * 得到指定长度的，随机汉字字符
	 * 
	 * @param length
	 * @return String
	 */
	public static String randomChineseChar(int length) {
		String resultString = "";
		for (int i = 0; i < length; i++) {
			int charIndex = (int) Math.round(Math.random() * (ChineseCharEndIndex - ChineseCharStartIndex)) + ChineseCharStartIndex;
			if (charIndex > ChineseCharEndIndex) {
				charIndex = ChineseCharEndIndex;
			}
			resultString += (char) charIndex;
		}
		return resultString;
	}

	/**
	 * 传入unicode，得到字符
	 * 
	 * @param utfString
	 * @return
	 */
	public static String unicodeConvert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	/**
	 * 检测是否是手机号码
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(unicodeConvert("\\u627F\\u5FB7"));
	}
}
