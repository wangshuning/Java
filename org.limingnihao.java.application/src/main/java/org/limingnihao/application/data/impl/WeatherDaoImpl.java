package org.limingnihao.application.data.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.WeatherDao;
import org.limingnihao.application.data.model.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class WeatherDaoImpl implements WeatherDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteEntity(WeatherEntity entity) {
		this.sessionFactory.getCurrentSession().delete(entity);
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public WeatherEntity getEntity(Integer id) {
		if (id != null) {
			WeatherEntity entity = (WeatherEntity) this.sessionFactory.getCurrentSession().get(WeatherEntity.class, id);
			return entity;
		} else {
			return null;
		}
	}

	@Override
	public WeatherEntity getEntityByRegionDate(String regionCode, Date weatherDate) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select entity from WeatherEntity entity where entity.regionCode=:regionCode and entity.weatherDate=:weatherDate");
		query.setParameter("regionCode", regionCode);
		query.setParameter("weatherDate", weatherDate);
		List<WeatherEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<WeatherEntity> getList() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select entity from WeatherEntity entity");
		List<WeatherEntity> list = query.list();
		return list;

	}

	@Override
	public void saveEntity(WeatherEntity entity) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
		this.sessionFactory.getCurrentSession().flush();
	}
}
