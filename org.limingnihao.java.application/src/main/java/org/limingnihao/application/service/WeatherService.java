package org.limingnihao.application.service;

import java.util.Date;
import java.util.List;

import org.limingnihao.application.service.model.WeatherBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WeatherService {

	public abstract void autoWeatherByAll();

	public abstract void autoWeatherSevenByRegionCode(String regionCode);

	public abstract void autoWeatherTodayByRegionCode(String regionCode);

	public abstract WeatherBean getWeatherTodayByRegionCode(String regionCode, String serverAddress);

	public abstract List<WeatherBean> getWeatherFourDayByRegionCode(String regionCode, String serverAddress);

	public abstract void saveWeather(String regionCode, Integer imageType1, Integer imageType2, String tempDetail, String weatherDetail, Date weatherDate, Integer pm, String chineseDate, String windDirection, String windSpeed, String humidity, String week);

}