package org.limingnihao.application.help;

/**
 * 错误代码和信息提示
 */
public class PageErrorHelp {

	public static final int CODE_SECURITY_UNLAW = 101;

	public static final int CODE_FILE_NOT_EXISTS = 201;

	public static final int CODE_403 = 403;
	public static final int CODE_404 = 404;
	public static final int CODE_500 = 500;

	public static String getMessage(Integer code) {
		if (code == null) {
			return "对不起，系统出现异常";
		}
		switch (code) {
			case CODE_403: 				return "对不起，页面可能不存在";
			case CODE_404: 				return "对不起，页面可能不存在";
			case CODE_500: 				return "对不起，系统出现了异常";
			case CODE_SECURITY_UNLAW: 	return "对不起，您可能没有权限";
			case CODE_FILE_NOT_EXISTS: 	return "对不起，文件可能不存在";
			default: 					return "对不起，页面可能不存在";
		}
	}
}
