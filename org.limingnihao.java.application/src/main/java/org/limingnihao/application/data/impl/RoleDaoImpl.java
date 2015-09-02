package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.RoleDao;
import org.limingnihao.application.data.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class RoleDaoImpl extends GenericDaoImpl<RoleEntity, Integer> implements RoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<RoleEntity> getListBySystemType(Integer systemType, Integer useFlag, int firstResult, int maxResults) {
		StringBuilder sb = new StringBuilder("select r from RoleEntity r where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (systemType != null) {
			sb.append(" and r.systemType=:systemType");
			params.put("systemType", systemType);
		}
		if (useFlag != null) {
			sb.append(" and r.useFlag=:useFlag");
			params.put("useFlag", useFlag);
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getListBySystemType_count(Integer systemType, Integer useFlag) {
		StringBuilder sb = new StringBuilder("select count(r) from RoleEntity r where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (systemType != null) {
			sb.append(" and r.systemType=:systemType");
			params.put("systemType", systemType);
		}
		if (useFlag != null) {
			sb.append(" and r.useFlag=:useFlag");
			params.put("useFlag", useFlag);
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

}
