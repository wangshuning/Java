package test.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.ApplicationService;
import org.limingnihao.application.service.model.ResourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestApplicationPropertyService {

	@Autowired
	private ApplicationService propertyService;

	@Test
	@Transactional
	public void test() {
		this.propertyService.initApplicationConfig();

		Iterator<Entry<String, ResourceBean>> entryKeyIterator = ApplicationHelp.RESOURCE_AUTHORITY_MAP.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, ResourceBean> e = entryKeyIterator.next();
			String key = e.getKey();
			ResourceBean value = e.getValue();
			System.out.println("key=" + key + ", value=" + value);
		}

		Iterator<Entry<String, String>> config = ApplicationHelp.APPLICATION_CONFIG.entrySet().iterator();
		while (config.hasNext()) {
			Entry<String, String> e = config.next();
			System.out.println("key=" + e.getKey() + ", value=" + e.getValue());
		}

		Iterator<Entry<String, HashMap<Integer, String>>> attr = ApplicationHelp.APPLICATION_ATTRIBUTE.entrySet().iterator();
		while (attr.hasNext()) {
			Entry<String, HashMap<Integer, String>> e = attr.next();
			System.out.println("key=" + e.getKey() + ", value=" + e.getValue());
			Iterator<Entry<Integer, String>> value = e.getValue().entrySet().iterator();
			while (value.hasNext()) {
				Entry<Integer, String> e2 = value.next();
				System.out.println("key=" + e2.getKey() + ", value=" + e2.getValue());
			}
		}

		System.out.println(ApplicationHelp.getAttributeName("MEETING_STATE", 1));
		System.out.println(ApplicationHelp.getAttributeName("MEETING_STATE", 2));
		System.out.println(ApplicationHelp.getAttributeName("DEVICE_TYPE", 20));
		System.out.println(ApplicationHelp.getAttributeName("DEVICE_TYPE", 21));
		System.out.println(ApplicationHelp.getAttributeName("DEVICE_TYPE", 22));
	}
}
