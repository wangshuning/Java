package org.limingnihao.application.data.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_weather_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class WeatherEntity extends PersistenceEntity {

	private static final long serialVersionUID = 74770750481572271L;

	@Id
	@Column(name = "weather_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer weatherId;

	@Column(name = "region_code", nullable = false)
	private String regionCode;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "temperature", nullable = true)
	private String temperature;

	@Column(name = "image_type_1", nullable = true)
	private Integer imageType1;

	@Column(name = "image_type_2", nullable = true)
	private Integer imageType2;

	@Column(name = "pm_2_5", nullable = true)
	private Integer pm25;

	@Column(name = "humidity", nullable = true)
	private String humidity;

	@Column(name = "wind_direction", nullable = true)
	private String windDirection;

	@Column(name = "wind_speed", nullable = true)
	private String windSpeed;

	@Column(name = "forecast", nullable = true)
	private Timestamp forecast;

	@Column(name = "week", nullable = true)
	private String week;

	@Column(name = "chinese", nullable = true)
	private String chinese;

	@Column(name = "save_date", nullable = true)
	private Timestamp saveDate;

	public Integer getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Integer getImageType1() {
		return imageType1;
	}

	public void setImageType1(Integer imageType1) {
		this.imageType1 = imageType1;
	}

	public Integer getImageType2() {
		return imageType2;
	}

	public void setImageType2(Integer imageType2) {
		this.imageType2 = imageType2;
	}

	public Integer getPm25() {
		return pm25;
	}

	public void setPm25(Integer pm25) {
		this.pm25 = pm25;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Timestamp getForecast() {
		return forecast;
	}

	public void setForecast(Timestamp forecast) {
		this.forecast = forecast;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public Timestamp getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Timestamp saveDate) {
		this.saveDate = saveDate;
	}

}
