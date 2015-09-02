package org.limingnihao.application.web;

import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.ApplicationService;
import org.limingnihao.application.service.model.ApplicationBean;
import org.limingnihao.application.service.model.ExtReaderBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("config/")
public class ConfigController {

	public static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping("property")
	public void property() {
	}

	/** --------------------------------配置管理------------------------------------ */
	/**
	 * 重新加载系统配置
	 */
	@RequestMapping("reloadApplicationConfig")
	@ResponseBody
	public ResultBean reloadApplicationConfig() {
		logger.info("reloadApplicationConfig");
		try {
			this.applicationService.initApplicationConfig();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean(false, "重新加载系统配置信息失败");
		}
		return new ResultBean(true, "重新加载系统配置信息成功");
	}

	/**
	 * 修改系统配置
	 */
	@RequestMapping("updateApplicationConfig")
	@ResponseBody
	public ResultBean updateApplicationConfig(String ids, String values) {
		logger.info("updateApplicationConfig - ids=" + ids + ", values=" + values);
		int count = this.applicationService.updateApplicationConfig(ids, values);
		ResultBean result = new ResultBean();
		result.setSuccess(true);
		result.setMessage("成功更新" + count + "条配置信息！");
		if (count > 0) {
			this.applicationService.initApplicationConfig();
		}
		return result;
	}

	/**
	 * 系统配置列表
	 */
	@RequestMapping("getApplicationListBean_grid")
	@ResponseBody
	public ExtReaderBean getApplicationListBean_grid(Integer page, Integer limit) {
		logger.info("getApplicationListBean_grid - page=" + page + ", limit=" + limit + ", currentType=" + ApplicationHelp.SYSTEM_CURRENT_TYPE);
		ListBean<ApplicationBean> listBean = this.applicationService.getListBean(page - 1, limit, PageUtil.NEXT);
		ExtReaderBean render = new ExtReaderBean();
		render.setTotalSize(listBean.getNumberTotal());
		render.setDataArray(listBean.getBeanList().toArray());
		return render;
	}

}
