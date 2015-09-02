package test.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.AuthorityDao;
import org.limingnihao.application.data.model.AuthorityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestAuthorityDao {

	@Autowired
	private AuthorityDao authorityDao;

	@Test
	public void test() {
	}

	@Test
	public void getAllAuthorities() {
		List<AuthorityEntity> list = authorityDao.getList(0, Integer.MAX_VALUE);
		System.out.println("list = " + list.size());
	}

}
