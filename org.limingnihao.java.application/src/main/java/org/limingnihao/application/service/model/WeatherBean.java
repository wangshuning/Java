package org.limingnihao.application.service.model;

public class WeatherBean {

	private String weatherDate;
	private String tempDetail;
	private String weatherDetail;
	private Integer weatherState;
	private String weatherImage;
	private String regionName;

	public String getWeatherDate() {
		return weatherDate;
	}

	public void setWeatherDate(String weatherDate) {
		this.weatherDate = weatherDate;
	}

	public String getTempDetail() {
		return tempDetail;
	}

	public void setTempDetail(String tempDetail) {
		this.tempDetail = tempDetail;
	}

	public String getWeatherDetail() {
		return weatherDetail;
	}

	public void setWeatherDetail(String weatherDetail) {
		this.weatherDetail = weatherDetail;
	}

	public Integer getWeatherState() {
		return weatherState;
	}

	public void setWeatherState(Integer weatherState) {
		this.weatherState = weatherState;
	}

	public String getWeatherImage() {
		return weatherImage;
	}

	public void setWeatherImage(String weatherImage) {
		this.weatherImage = weatherImage;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
