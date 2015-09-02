package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.AttributeDao;
import org.limingnihao.application.data.model.AttributeEntity;
import org.limingnihao.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ArributeDaoImpl extends GenericDaoImpl<AttributeEntity, Integer> implements AttributeDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AttributeEntity getEntityByTypeAndValue(String attributeFlag, Integer attributeValue, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select att from AttributeEntity att " + 
				"where att.attributeFlag=:attributeType and att.attributeValue=:attributeValue and att.systemType=:systemType");
		query.setParameter("attributeType", attributeFlag);
		query.setParameter("attributeValue", attributeValue);
		query.setParameter("systemType", systemType);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<AttributeEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<AttributeEntity> getListBySystemType(Integer systemType) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select att from AttributeEntity att where att.systemType=:systemType ");
		params.put("systemType", systemType);
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		return query.list();
	}

	@Override
	public List<AttributeEntity> getListBySystemTypeFlag(String attributeFlag, Integer systemType, boolean state) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select att from AttributeEntity att where att.attributeFlag=:attributeFlag ");
		params.put("attributeFlag", attributeFlag);
		if (NumberUtil.isSignless(systemType)) {
			sb.append(" and att.systemType=:systemType ");
			params.put("systemType", systemType);
		}
		if (!state) {
			sb.append(" and att.attributeValue<>-1 ");
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		return query.list();
	}

}
