package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestAttributeService {

	@Autowired
	private AttributeService attributeService;

	@Test
	public void test() {

	}
}
