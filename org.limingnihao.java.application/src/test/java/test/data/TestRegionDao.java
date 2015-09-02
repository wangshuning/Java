package test.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.model.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestRegionDao {

	@Autowired
	private RegionDao regionDao;

	@Test
	public void test() {
		boolean isOpen = false;
		assert isOpen = true; // 如果开启了断言，会将isOpen的值改为true
		System.out.println(isOpen);// 打印是否开启了断言
	}

	@Test
	public void user() {
		List<RegionEntity> list = this.regionDao.getListByUserId(1);
		for (RegionEntity r : list) {
			System.out.println(r.getRegionName());
		}
	}

	// @Test
	public void create() {
		RegionEntity entity = new RegionEntity();
		entity.setRegionName("测试");
		entity.setParentEntity(null);
		entity.setSequence(0);
		entity.setUseFlag(1);

		RegionEntity entity2 = new RegionEntity();
		entity2.setRegionName("子节点1");
		entity2.setParentEntity(entity);
		entity2.setSequence(0);
		entity2.setUseFlag(1);
		entity.getChildrenList().add(entity2);
		this.regionDao.saveEntity(entity);

		this.regionDao.saveEntity(entity);

		RegionEntity e = this.regionDao.getEntity(entity2.getRegionId());
		e.getParentEntity();
	}

	@Test
	@Transactional
	public void update() {
		RegionEntity e = this.regionDao.getEntity(1);
		System.out.println("parent=" + e.getParentEntity());

	}
}
