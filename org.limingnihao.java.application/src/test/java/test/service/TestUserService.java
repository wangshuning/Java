package test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.service.ApplicationService;
import org.limingnihao.application.service.UserService;
import org.limingnihao.application.service.exception.*;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.RoleBean;
import org.limingnihao.application.service.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService propertyService;

	@Test
	public void test() {
	}

	@Test
	@Transactional
	public void create() {
		try {
			this.userService.createOrUpdate(null, "new1", "new1", "new1", 1, "1,2", "1,2", 1);
		} catch (UsernameExistsException e) {
			e.printStackTrace();
		} catch (GroupNullPointerException e) {
			e.printStackTrace();
		} catch (RoleNullPointerException e) {
			e.printStackTrace();
		} catch (MessageServiceErrorException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void get() {
		this.userService.getBeanByUserId(1);
	}

	@Test
	@Transactional
	public void login() {
		UserBean userBean = null;
		try {
			userBean = this.userService.userLogin("admin", "123456");
			System.out.println(userBean.toString());
		} catch (PasswordErrorException e) {
			e.printStackTrace();
		} catch (UserNullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RoleBean> roleBeanList = userBean.getRoleBeanList();
		List<GroupBean> groupBeanList = userBean.getGroupBeanList();
		for (RoleBean roleBean : roleBeanList) {
			System.out.println(roleBean.toString());
		}

		for (GroupBean groupBean : groupBeanList) {
			System.out.println(groupBean.toString());
		}
	}

	@Test
	@Transactional
	public void changePasword() {
		try {
			this.userService.userPasswordChange(1, "123456", "123456");
		} catch (PasswordErrorException e) {
			e.printStackTrace();
		} catch (UserNullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void getUserListByGroupId() {
		this.propertyService.initApplicationConfig();

		List<UserBean> beanList = this.userService.getListByGroupId(1, "", 0);
		for (UserBean userBean : beanList) {
			System.out.println(userBean.getUserType() + " - " + userBean.getUserTypeName());
		}
	}

}
