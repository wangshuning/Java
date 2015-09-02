package test.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.RoleDao;
import org.limingnihao.application.data.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestRoleDao {

	@Autowired
	private RoleDao roleDao;

	@Test
	public void test() {
	}

	public void getRoleById() {
		RoleEntity roleEntity = roleDao.getEntity(1);
		System.out.println("roleEntity = " + roleEntity.getRoleName());
	}

	public void getRoles() {
		List<RoleEntity> list = roleDao.getList(1, 10);
		System.out.println("list.size() = " + list.size());
	}

}
