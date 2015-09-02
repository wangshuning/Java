package org.limingnihao.application.help;

import java.util.HashMap;
import java.util.Map;

import org.limingnihao.application.service.model.ResourceBean;

public class ApplicationHelp {

	/** -------------------- 系统配置 -------------------- */
	public static String SYSTEM_CURRENT_VERSION = "v1.0.0";
	public static Integer SYSTEM_CURRENT_TYPE = 1;

	/** -------------------- 控制字段 -------------------- */
	public static String ATTRIBUTE_CURRENT_USER = "ATTRIBUTE_CURRENT_USER";
	public static String ATTRIBUTE_CURRENT_URL = "ATTRIBUTE_CURRENT_URL";
	public static String ATTRIBUTE_RESOURCE_LIST = "ATTRIBUTE_RESOURCE_LIST";
	public static String ATTRIBUTE_ERROR_MESSAGE = "ATTRIBUTE_ERROR_MESSAGE";

	/** -------------------- 权限管理 -------------------- */
	public static Map<String, ResourceBean> RESOURCE_AUTHORITY_MAP = new HashMap<String, ResourceBean>();
	public static String URL_INDEX = "index.do";
	public static String URL_LOGIN = "login.do";
	public static String URL_UNLAW = "error.do?code=" + PageErrorHelp.CODE_SECURITY_UNLAW;
	public static String URL_ERROR = "error.do";

	/** -------------------- 参数配置 -------------------- */
	public static HashMap<String, String> APPLICATION_CONFIG = new HashMap<String, String>();
	public static HashMap<String, HashMap<Integer, String>> APPLICATION_ATTRIBUTE = new HashMap<String, HashMap<Integer, String>>();

	public static String PROJECT_NAME = "";
	public static String AES_PASSWORD = "1234567890abcdef";

	public static String JSLIB_HTTP_PATH = "/org.limingnihao.jslib/";
	public static String STYLE_HTTP_PATH = "/org.limingnihao.jslib/app_style/";

	public static String VERSION_SAVE_PATH = "";
	public static String VERSION_HTTP_PATH = "";
	public static String LOGFILE_SAVE_PATH = "";
	public static String LOGFILE_HTTP_PATH = "";

	/** 是否做同步信息 */
	public static boolean IS_SYN_INFORMATION = true;// 是否同步

	/** -------------------- 方法 -------------------- */

	/**
	 * 获取字段标签名称
	 */
	public static String getAttributeName(String flag, Integer value) {
		if (APPLICATION_ATTRIBUTE.containsKey(flag)) {
			HashMap<Integer, String> attr = APPLICATION_ATTRIBUTE.get(flag);
			if (attr.containsKey(value)) {
				return attr.get(value);
			}
		}
		return "未定义";
	}
}
