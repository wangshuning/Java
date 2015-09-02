package org.limingnihao.application.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.limingnihao.application.service.WeatherService;
import org.limingnihao.application.service.model.WeatherBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("weather/")
public class WeatherConrtroller {

	public static final Logger logger = LoggerFactory.getLogger(WeatherConrtroller.class);

	@Autowired
	private WeatherService weatherService;

	@RequestMapping("getFourDayWeather")
	@ResponseBody
	public List<WeatherBean> getFourDayWeather(String regionCode, HttpServletRequest request) {
		logger.info("getFourDayWeather - regionCode=" + regionCode);
		String serverAddress = "http://" + request.getServerName().replaceFirst("http://", "");
		return this.weatherService.getWeatherFourDayByRegionCode(regionCode, serverAddress);
	}
}
