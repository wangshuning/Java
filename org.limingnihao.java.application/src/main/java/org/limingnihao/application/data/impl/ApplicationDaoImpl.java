package org.limingnihao.application.data.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.ApplicationDao;
import org.limingnihao.application.data.model.ApplicationEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ApplicationDaoImpl extends GenericDaoImpl<ApplicationEntity, Integer> implements ApplicationDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public ApplicationEntity getEntityByFlag(String propertyFlag) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select ap from ApplicationEntity ap where ap.systemType=:systemType and ap.propertyFlag=:propertyFlag");
		query.setParameter("systemType", ApplicationHelp.SYSTEM_CURRENT_TYPE);
		query.setParameter("propertyFlag", propertyFlag);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<ApplicationEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ApplicationEntity> getListBySystemType(Integer systemType, int firstResult, int maxResults) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select ap from ApplicationEntity ap where ap.systemType=:systemType");
		query.setParameter("systemType", systemType);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getListBySystemType_count(Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select count(ap) from ApplicationEntity ap where ap.systemType=:systemType");
		query.setParameter("systemType", systemType);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

}
