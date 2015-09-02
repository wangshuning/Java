package org.limingnihao.application.data.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.AuthorityDao;
import org.limingnihao.application.data.model.AuthorityEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class AuthorityDaoImpl extends GenericDaoImpl<AuthorityEntity, Integer> implements AuthorityDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<AuthorityEntity> getList(int firstResult, int maxResults) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select au from AuthorityEntity au where au.systemType=:systemType");
		query.setParameter("systemType", ApplicationHelp.SYSTEM_CURRENT_TYPE);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getList_count() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select count(au) from AuthorityEntity au where au.systemType=:systemType");
		query.setParameter("systemType", ApplicationHelp.SYSTEM_CURRENT_TYPE);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

}
