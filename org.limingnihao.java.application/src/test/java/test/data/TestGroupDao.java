package test.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.GroupDao;
import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.model.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestGroupDao {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private RegionDao regionDao;

	@Autowired
	private ResourceDao resourceDao;

	@Test
	@Transactional
	public void create() {
		GroupEntity group = new GroupEntity();
		group.setGroupName("nihao");
		group.setDescription("");
		group.setRegionEntity(this.regionDao.getEntity(1));
		group.setSequence(1);
		group.setParentEntity(null);
		group.setUseFlag(1);
		this.groupDao.saveEntity(group);
		System.out.println(group.getGroupId());
		this.groupDao.getEntity(group.getGroupId());
		group.setGroupName("hello");
		this.groupDao.saveEntity(group);

		for (GroupEntity g : this.groupDao.getListByParentGroupId(null, 1, 0, 1000)) {
			System.out.println(g.getGroupId() + "-" + g.getGroupName());
		}
	}
}
