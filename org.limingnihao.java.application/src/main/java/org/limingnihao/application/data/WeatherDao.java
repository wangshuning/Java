package org.limingnihao.application.data;

import java.util.Date;
import java.util.List;

import org.limingnihao.application.data.model.WeatherEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WeatherDao {

	public abstract void deleteEntity(WeatherEntity entity);

	public abstract WeatherEntity getEntity(Integer id);

	public abstract WeatherEntity getEntityByRegionDate(String regionCode, Date weatherDate);

	public abstract List<WeatherEntity> getList();

	public abstract void saveEntity(WeatherEntity entity);

}