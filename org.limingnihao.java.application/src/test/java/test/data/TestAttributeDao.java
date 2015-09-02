package test.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.data.AttributeDao;
import org.limingnihao.application.data.model.AttributeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestAttributeDao {

	@Autowired
	private AttributeDao attributeDao;

	@Test
	public void test() {
	}

	@Test
	public void getAllEnumType() {
		List<AttributeEntity> list = this.attributeDao.getList();
		for (AttributeEntity en : list) {
			System.out.println("en =" + en.getAttributeName());
		}
	}

}
