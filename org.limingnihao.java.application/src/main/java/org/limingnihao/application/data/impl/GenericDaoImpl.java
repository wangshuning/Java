package org.limingnihao.application.data.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	public GenericDaoImpl() {
		clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void deleteEntity(T entity) {
		this.sessionFactory.getCurrentSession().delete(entity);
		this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public T getEntity(PK id) {
		if (id != null) {
			return (T) this.sessionFactory.getCurrentSession().get(clazz, id);
		} else {
			return null;
		}
	}

	@Override
	public List<T> getList() {
		return this.sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	@Override
	public List<T> getList(int firstResult, int maxResults) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from " + clazz.getName());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getList_count() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select count(entity) from " + clazz.getName() + " entity");
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public void saveEntity(T entity) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
		this.sessionFactory.getCurrentSession().flush();
	}

}
