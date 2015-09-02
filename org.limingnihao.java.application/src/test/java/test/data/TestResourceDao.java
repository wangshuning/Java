package test.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.model.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestResourceDao {

	@Autowired
	private ResourceDao resourceDao;

	@Test
	public void test() {
	}

	// @Test
	public void get_list_parentId() {
		List<ResourceEntity> list = this.resourceDao.getListByParentIdAndUserId(1, 1);
		for (ResourceEntity e : list) {
			System.out.println(e.getResourceName());
		}
	}

	@Test
	public void list() {
		List<ResourceEntity> entityList = this.resourceDao.getListByParentIdAndUserId(3, 1);
		for (ResourceEntity e : entityList) {
			System.out.println(e.getResourceName());

		}
	}

}
