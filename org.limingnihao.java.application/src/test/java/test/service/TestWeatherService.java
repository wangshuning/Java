package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.limingnihao.application.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-servlet.xml")
public class TestWeatherService {

	@Autowired
	private WeatherService weatherService;

	@Test
	public void test() {
		this.weatherService.autoWeatherSevenByRegionCode("101010100");
		// this.weatherService.autoWeatherTodayByRegionCode(101010100);
		// this.weatherService.getWeatherTodayByRegionCode(101010100,"http://127.0.0.1");
	}
}
