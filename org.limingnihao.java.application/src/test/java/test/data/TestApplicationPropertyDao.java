package test.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestApplicationPropertyDao {

	@Autowired
	private ApplicationDao applicationPropertyDao;

	@Test
	public void test() {
	}

}
