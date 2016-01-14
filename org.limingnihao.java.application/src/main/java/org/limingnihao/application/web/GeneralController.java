package org.limingnihao.application.web;

import javax.servlet.http.HttpServletRequest;

import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.help.PageErrorHelp;
import org.limingnihao.model.DateBean;
import org.limingnihao.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralController {

	public static final Logger logger = LoggerFactory.getLogger(GeneralController.class);

	@RequestMapping("test")
	public void test() {
	}

	@RequestMapping("login")
	public void login(HttpServletRequest request) {
		request.setAttribute("systemCurrentVersion", ApplicationHelp.SYSTEM_CURRENT_VERSION);
	}

	@RequestMapping("error")
	public void error(Integer code, HttpServletRequest request) {
		logger.info("error - code=" + code);
		request.setAttribute(ApplicationHelp.ATTRIBUTE_ERROR_MESSAGE, PageErrorHelp.getMessage(code));
	}

	@RequestMapping("getCurrentSystemDateTime")
	@ResponseBody
	public DateBean getCurrentSystemDateTime() {
		logger.info("getCurrentSystemDateTime");
		return DateUtil.getCurrentDateBean();
	}

}
