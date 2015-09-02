package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestAuthorityService {

	@Autowired
	private AuthorityService authorityService;

	@Test
	@Transactional
	public void save() {
		this.authorityService.createOrUpdate(0, "测试", "test", 1, "1,4");
	}
}
