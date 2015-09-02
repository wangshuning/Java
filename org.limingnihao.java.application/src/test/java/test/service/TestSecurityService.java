package test.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.service.SecurityService;
import org.limingnihao.application.service.exception.UserNullPointerException;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.UserAuthorityBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestSecurityService {

	@Autowired
	private SecurityService securityService;

	@Test
	@Transactional
	public void test_get1() {
		try {
			UserAuthorityBean bean = this.securityService.getUserAuthorityBeanByUserId(10);
			System.out.println(bean);
			for (String name : bean.getAuthorityFlagList()) {
				System.out.println(name);
			}
		} catch (UserNullPointerException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void get_list_map() {
		Map<String, ResourceBean> map = this.securityService.getResourceBeanMap(1);
		for (Entry<String, ResourceBean> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue().getAuthorityFlagString());
		}

		ResourceBean config = map.get("config/property.do");
		System.out.println(config.getResourceName() + config.getAuthorityFlagList());
	}

	@Test
	@Transactional
	public void get_list_user() {
		Integer userId = 1;
		List<ResourceBean> list = this.securityService.getResourceBeanListForMenuAndChildByUserId(3, userId);
		for (ResourceBean bean : list) {
			System.out.println("id=" + bean.getResourceId() + " - sequence=" + bean.getSequence() + ", name=" + bean.getResourceName());
			for (ResourceBean child : bean.getChildrenList()) {
				System.out.println("child - id=" + child.getResourceId() + " - sequence=" + child.getSequence() + ", name=" + child.getResourceName() + ", url=" + child.getResourceUrl());
			}
		}

	}
}
