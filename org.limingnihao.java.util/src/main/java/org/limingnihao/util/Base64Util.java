package org.limingnihao.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * base64编解码工具类
 * 
 * @author lishiming
 * 
 */
public class Base64Util {

	public static void main(String args[]) {
		// String a = "+++ODA4MDwvcHJvamVjdFBvcnQ+PHByb2plY3ROYW1lPjwvcHJvamVjdE5hbWU+PHVybFZhbHVlPjo4MDgwL3VzZXIvdXNiS2V5VmFsaWRhdGUuZG88L3VybFZhbHVlPjwvVXNiS2V5QmVhbj4=";
		String a = "ZGhjYzoxMjM0NTI2Mg==";
		System.out.println(decoder(a));
	}

	/**
	 * 编码
	 * 
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				BASE64Encoder encoder = new BASE64Encoder();
				return encoder.encode(s.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解码
	 * 
	 * @param s
	 * @return
	 */
	public static String decoder(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] b = decoder.decodeBuffer(s);
				return new String(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
