package org.limingnihao.util;

import com.google.gson.Gson;

public class GsonUtil {

	private static Gson GSON = new Gson();

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.equals("") || json.equals("null")) {
			return null;
		}
		return GSON.fromJson(json, clazz);
	}

	public static String toJson(Object src) {
		return GSON.toJson(src);
	}

	public static String toJsonTree(Object src) {
		return GSON.toJsonTree(src).toString();
	}
}
