package test.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.VersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestVersionDao {

	@Autowired
	private VersionDao versionDao;

	@Test
	public void test() {

	}

}
