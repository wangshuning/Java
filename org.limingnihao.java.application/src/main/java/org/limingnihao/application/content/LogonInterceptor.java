package org.limingnihao.application.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.UserAuthorityBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class LogonInterceptor extends HandlerInterceptorAdapter {

	private static final UrlPathHelper PATH_HELPER = new UrlPathHelper();

	/**
	 * 拦截器的前端, 执行控制器之前, 所要处理的方法, 此处用于判断session中是否有用户登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		UserAuthorityBean userBean = (UserAuthorityBean) session.getAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_USER);
		// 截取路径, 去掉 "/"
		String url = request.getRequestURI().replace(request.getContextPath(), "").substring(1);
		ResourceBean resourceBean = ApplicationHelp.RESOURCE_AUTHORITY_MAP.get(url);
		if (resourceBean == null || resourceBean.getAuthorityFlagList() == null || resourceBean.getAuthorityFlagList().isEmpty()) {
			if (userBean == null) {
				if (url.contains("interface/")||url.contains("recordPlay")||url.contains("livePlay")||url.contains("yst-tms/stb/getUpgradeInfor")) {

				} else if (url.endsWith(ApplicationHelp.URL_LOGIN)) {

				} else {
					String header = request.getHeader("X-Requested-With");
					if (StringUtils.isNotBlank(header) && header.equals("XMLHttpRequest")) {
						response.setContentType("application/json;charset=UTF-8");
						response.getWriter().write(new ResultBean(false, "登录超时，请刷新页面！").toJson());
						response.getWriter().flush();
						response.getWriter().close();
					} else {
						response.sendRedirect(PATH_HELPER.getContextPath(request) + "/" + ApplicationHelp.URL_LOGIN);
					}
					return false;
				}
			} else if (url.equals(ApplicationHelp.URL_LOGIN)) {
				String sessionUrl = (String) session.getAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_URL);
				if (StringUtils.isNotBlank(sessionUrl)) {
					response.sendRedirect(PATH_HELPER.getContextPath(request) + "/" + sessionUrl);
					return false;
				}
			}
			return true;
		} else {
			if (userBean == null) {
				// 将本次访问的url记载session中
				if (!url.equals(ApplicationHelp.URL_ERROR)) {
					session.setAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_URL, url);
				}
				response.sendRedirect(PATH_HELPER.getContextPath(request) + "/" + ApplicationHelp.URL_LOGIN);
				return false;
			} else {
				for (String resourceFlag : resourceBean.getAuthorityFlagList()) {
					for (String userFlag : userBean.getAuthorityFlagList()) {
						if (resourceFlag.equals(userFlag)) {
							return true;
						}
					}
				}
				response.sendRedirect(PATH_HELPER.getContextPath(request) + "/" + ApplicationHelp.URL_UNLAW);
				return false;
			}
		}
	}

}