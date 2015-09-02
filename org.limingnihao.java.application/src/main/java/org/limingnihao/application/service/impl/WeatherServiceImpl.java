package org.limingnihao.application.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.WeatherDao;
import org.limingnihao.application.data.model.RegionEntity;
import org.limingnihao.application.data.model.WeatherEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.WeatherService;
import org.limingnihao.application.service.model.AutoWeatherBean;
import org.limingnihao.application.service.model.WeatherBean;
import org.limingnihao.util.DateUtil;
import org.limingnihao.util.GsonUtil;
import org.limingnihao.util.HttpUtil;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PathUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

	public static Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	public static Integer BEIJING_CODE = 101010100;

	@Autowired
	private WeatherDao weatherDao;

	@Autowired
	private RegionDao regionDao;

	@Override
	public void autoWeatherByAll() {
		List<RegionEntity> list = this.regionDao.getList();
		for (RegionEntity r : list) {
			if (r.getRegionCode() != null) {
				logger.info("autoWeatherByAll - regionCode=" + r.getRegionCode() + ", regionName=" + r.getRegionName());
				this.autoWeatherSevenByRegionCode(r.getRegionCode());
			}
		}
	}

	@Override
	public void autoWeatherSevenByRegionCode(String regionCode) {
		String json = HttpUtil.sendGetHttpRequest("http://weather.123.duba.net/static/weather_info/" + regionCode + ".html");
		if (json == null || "".equals(json)) {
			logger.info("autoWeatherSevenByRegionCode - regionCode=" + regionCode + " - http请求失败!");
			return;
		}
		json = json.replace("weather_callback({\"weatherinfo\":", "");
		json = json.substring(0, json.length() - 1);
		int index = json.indexOf("},");
		json = json.substring(0, index + 1);
		logger.info("autoWeatherSevenByRegionCode - 开始 - regionCode=" + regionCode + ", json=" + json);
		AutoWeatherBean weather = null;
		try {
			weather = GsonUtil.fromJson(json, AutoWeatherBean.class);
		} catch (Exception e) {
			logger.info("autoWeatherSevenByRegionCode - regionCode=" + regionCode + " - 解析json失败!");
			e.printStackTrace();
			return;
		}
		String city = weather.getCity();
		String cityid = weather.getCityid();
		String pm = weather.getPm();
		String sd = weather.getSd();
		String week = weather.getWeek();
		String chineseDate = weather.getDate();
		String wd = weather.getWd();
		String ws = weather.getWs();
		String weather1 = weather.getWeather1();
		String weather2 = weather.getWeather2();
		String weather3 = weather.getWeather3();
		String weather4 = weather.getWeather4();
		String weather5 = weather.getWeather5();
		String weather6 = weather.getWeather6();

		String temp1 = weather.getTemp1();
		String temp2 = weather.getTemp2();
		String temp3 = weather.getTemp3();
		String temp4 = weather.getTemp4();
		String temp5 = weather.getTemp5();
		String temp6 = weather.getTemp6();

		String img1 = weather.getImg1();
		String img2 = weather.getImg2();
		String img3 = weather.getImg3();
		String img4 = weather.getImg4();
		String img5 = weather.getImg5();
		String img6 = weather.getImg6();
		String img7 = weather.getImg7();
		String img8 = weather.getImg8();
		String img9 = weather.getImg9();
		String img10 = weather.getImg10();
		String img11 = weather.getImg11();
		String img12 = weather.getImg12();

		String date_y = weather.getDate_y();
		Date date_1 = DateUtil.parse(date_y, "yyyy年MM月dd日");
		Date date_2 = DateUtil.getTomorrow(date_1);
		Date date_3 = DateUtil.getTomorrow(date_2);
		Date date_4 = DateUtil.getTomorrow(date_3);
		Date date_5 = DateUtil.getTomorrow(date_4);
		Date date_6 = DateUtil.getTomorrow(date_5);

		String date1 = DateUtil.format(date_1, "yyyy年MM月dd日");
		String date2 = DateUtil.format(date_2, "yyyy年MM月dd日");
		String date3 = DateUtil.format(date_3, "yyyy年MM月dd日");
		String date4 = DateUtil.format(date_4, "yyyy年MM月dd日");
		String date5 = DateUtil.format(date_5, "yyyy年MM月dd日");
		String date6 = DateUtil.format(date_6, "yyyy年MM月dd日");
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date1 + " - " + weather1 + ", " + temp1 + ", img=" + img1 + ", " + img2);
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date2 + " - " + weather2 + ", " + temp2 + ", img=" + img3 + ", " + img4);
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date3 + " - " + weather3 + ", " + temp3 + ", img=" + img5 + ", " + img6);
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date4 + " - " + weather4 + ", " + temp4 + ", img=" + img7 + ", " + img8);
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date5 + " - " + weather5 + ", " + temp5 + ", img=" + img9 + ", " + img10);
		logger.info("城市=" + city + ", " + cityid + ", 日期=" + date6 + " - " + weather6 + ", " + temp6 + ", img=" + img11 + ", " + img12);
		this.saveWeather(regionCode, NumberUtil.parseInt(img1) > 22 ? 0 : NumberUtil.parseInt(img1), NumberUtil.parseInt(img2) > 22 ? 0 : NumberUtil.parseInt(img2), temp1, weather1, date_1,
				NumberUtil.parseInt(pm), chineseDate, wd, ws, sd, week);
		this.saveWeather(regionCode, NumberUtil.parseInt(img3) > 22 ? 0 : NumberUtil.parseInt(img3), NumberUtil.parseInt(img4) > 22 ? 0 : NumberUtil.parseInt(img4), temp2, weather2, date_2,
				NumberUtil.parseInt(pm), "", wd, ws, sd, "");
		this.saveWeather(regionCode, NumberUtil.parseInt(img5) > 22 ? 0 : NumberUtil.parseInt(img5), NumberUtil.parseInt(img6) > 22 ? 0 : NumberUtil.parseInt(img6), temp3, weather3, date_3,
				NumberUtil.parseInt(pm), "", wd, ws, sd, "");
		this.saveWeather(regionCode, NumberUtil.parseInt(img7) > 22 ? 0 : NumberUtil.parseInt(img7), NumberUtil.parseInt(img8) > 22 ? 0 : NumberUtil.parseInt(img8), temp4, weather4, date_4,
				NumberUtil.parseInt(pm), "", wd, ws, sd, "");
		this.saveWeather(regionCode, NumberUtil.parseInt(img9) > 22 ? 0 : NumberUtil.parseInt(img9), NumberUtil.parseInt(img10) > 22 ? 0 : NumberUtil.parseInt(img10), temp5, weather5, date_5,
				NumberUtil.parseInt(pm), "", wd, ws, sd, "");
		this.saveWeather(regionCode, NumberUtil.parseInt(img11) > 22 ? 0 : NumberUtil.parseInt(img11), NumberUtil.parseInt(img12) > 22 ? 0 : NumberUtil.parseInt(img12), temp6, weather6, date_6,
				NumberUtil.parseInt(pm), "", wd, ws, sd, "");
	}

	@Override
	public void autoWeatherTodayByRegionCode(String regionCode) {
		// String json = HttpUtil.sendGetHttpRequest("http://www.weather.com.cn/data/cityinfo/" + regionCode + ".html");
		// logger.info("autoWeatherSevenByRegionCode - regionCode=" + regionCode + ", json=" + json);
		// if (json == null || "".equals(json)) {
		// logger.info("getWeather - regionCode=" + regionCode + " - http请求失败!");
		// return;
		// }
		// JSONObject weatherJson = null;
		// try {
		// JSONObject jsonObj = JSONObject.fromObject(json);
		// weatherJson = JSONObject.fromObject(jsonObj.get("weatherinfo"));
		// } catch (Exception e) {
		// logger.info("getWeather - regionCode=" + regionCode + " - 解析json失败!");
		// // e.printStackTrace();
		// return;
		// }
		// String city = weatherJson.getString("city");
		// String cityid = weatherJson.getString("cityid");
		// String img1 = weatherJson.getString("img1");
		// String img2 = weatherJson.getString("img2");
		// String temp1 = weatherJson.getString("temp1");
		// String temp2 = weatherJson.getString("temp2");
		// String weather = weatherJson.getString("weather");
		// logger.info("城市=" + city + ", " + cityid + " - " + weather + ", " + temp1 + ", img=" + img1 + ", " + img2);
		// this.saveWeather(regionCode, NumberUtil.parseInt(img1), NumberUtil.parseInt(img2), temp1 + "~" + temp2, weather, DateUtil.getToday());
	}

	@Override
	public WeatherBean getWeatherTodayByRegionCode(String regionCode, String serverAddress) {
		WeatherBean bean = new WeatherBean();
		WeatherEntity entity = this.weatherDao.getEntityByRegionDate(regionCode, DateUtil.getToday());
		if (entity != null) {
			bean.setTempDetail(entity.getTemperature());
			bean.setWeatherDate(DateUtil.format(new Date(entity.getForecast().getTime())));
			bean.setWeatherDetail(entity.getDescription());
			bean.setWeatherState(entity.getImageType1());
			bean.setWeatherImage(ApplicationHelp.JSLIB_HTTP_PATH + "app_style" + "/images/weather/b" + entity.getImageType1() + ".png");
			RegionEntity region = this.regionDao.getEntityByRegionCode(entity.getRegionCode());
			if (region != null) {
				bean.setRegionName(region.getRegionName());
			} else {
				bean.setRegionName("本地区");
			}
		}
		logger.info("getWeatherTodayByRegionCode - weatherImage=" + bean.getWeatherImage());
		return bean;
	}

	@Override
	public List<WeatherBean> getWeatherFourDayByRegionCode(String regionCode, String serverAddress) {
		List<WeatherBean> beanList = new ArrayList<WeatherBean>();
		List<WeatherEntity> entityList = new ArrayList<WeatherEntity>();
		WeatherEntity dayOne = this.weatherDao.getEntityByRegionDate(regionCode, DateUtil.getToday());
		WeatherEntity dayTwo = this.weatherDao.getEntityByRegionDate(regionCode, DateUtil.getTomorrow(DateUtil.getToday()));
		WeatherEntity dayThree = this.weatherDao.getEntityByRegionDate(regionCode, DateUtil.getTomorrow(DateUtil.getTomorrow(DateUtil.getToday())));
		WeatherEntity dayFour = this.weatherDao.getEntityByRegionDate(regionCode, DateUtil.getTomorrow(DateUtil.getTomorrow(DateUtil.getTomorrow(DateUtil.getToday()))));
		entityList.add(dayOne);
		entityList.add(dayTwo);
		entityList.add(dayThree);
		entityList.add(dayFour);
		for (WeatherEntity entity : entityList) {
			WeatherBean bean = new WeatherBean();
			bean.setTempDetail(entity.getDescription());
			bean.setWeatherDate(DateUtil.format(new Date(entity.getForecast().getTime())));
			bean.setWeatherDetail(entity.getDescription());
			bean.setWeatherState(entity.getImageType1());
			bean.setWeatherImage(serverAddress + PathUtil.mergeUrl(ApplicationHelp.STYLE_HTTP_PATH, "/images/weather/b") + entity.getImageType1() + ".png");
			RegionEntity region = this.regionDao.getEntityByRegionCode(entity.getRegionCode());
			if (region != null) {
				bean.setRegionName(region.getRegionName());
			} else {
				bean.setRegionName("本地区");
			}
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public void saveWeather(String regionCode, Integer imageType1, Integer imageType2, String tempDetail, String weatherDetail, Date weatherDate, Integer pm, String chineseDate, String windDirection,
			String windSpeed, String humidity, String week) {
		WeatherEntity entity = this.weatherDao.getEntityByRegionDate(regionCode, weatherDate);
		if (entity == null) {
			entity = new WeatherEntity();
		}
		entity.setRegionCode(regionCode);
		if (pm != null && pm >= 0) {
			entity.setPm25(pm);
		}
		if (humidity != null && !StringUtil.isBlank(humidity)) {
			entity.setHumidity(humidity);
		}
		if (windDirection != null && !StringUtil.isBlank(windDirection)) {
			entity.setWindDirection(windDirection);
		}
		if (windSpeed != null && !StringUtil.isBlank(windSpeed)) {
			entity.setWindSpeed(windSpeed);
		}
		entity.setImageType1(imageType1);
		entity.setImageType2(imageType2);
		entity.setDescription(tempDetail);
		entity.setDescription(weatherDetail);
		entity.setForecast(new Timestamp(weatherDate.getTime()));
		entity.setChinese(chineseDate);
		entity.setWeek(week);
		entity.setSaveDate(new Timestamp(System.currentTimeMillis()));
		this.weatherDao.saveEntity(entity);
	}
}
