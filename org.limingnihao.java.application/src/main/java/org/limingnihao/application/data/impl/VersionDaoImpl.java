package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.VersionDao;
import org.limingnihao.application.data.model.VersionEntity;
import org.limingnihao.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class VersionDaoImpl extends GenericDaoImpl<VersionEntity, Integer> implements VersionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public VersionEntity getEntityByDeviceTypeAndVersionCode(Integer deviceType, Integer versionCode, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select version from VersionEntity version " + 
		        "where version.systemType=:systemType and version.deviceType=:deviceType and version.versionCode=:versionCode");
		query.setParameter("deviceType", deviceType);
		query.setParameter("versionCode", versionCode);
		query.setParameter("systemType", systemType);
		List<VersionEntity> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public VersionEntity getEntityByDeviceTypeAndVersionName(Integer deviceType, String versionName, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select version from VersionEntity version " + 
		        "where version.systemType=:systemType and version.deviceType=:deviceType and version.versionName=:versionName");
		query.setParameter("deviceType", deviceType);
		query.setParameter("versionName", versionName);
		query.setParameter("systemType", systemType);

		List<VersionEntity> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public VersionEntity getEntityByMaxCodeAndDeviceType(Integer deviceType, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select version from VersionEntity version " + 
		        "where version.systemType=:systemType and version.deviceType=:deviceType order by version.versionCode desc");
		query.setParameter("deviceType", deviceType);
		query.setParameter("systemType", systemType);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<VersionEntity> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<VersionEntity> getListByDeviceType(Integer deviceType, int firstResult, int maxResults, Integer systemType) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select version from VersionEntity version where version.systemType=:systemType");
		params.put("systemType", systemType);
		if (NumberUtil.isSignless(deviceType)) {
			sb.append(" and version.deviceType=:deviceType");
			params.put("deviceType", deviceType);
		}
		sb.append(" order by version.deviceType, version.uploadTime desc");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getListByDeviceType_count(Integer deviceType, Integer systemType) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select count(version) from VersionEntity version where version.systemType=:systemType");
		params.put("systemType", systemType);
		if (NumberUtil.isSignless(deviceType)) {
			sb.append(" and version.deviceType=:deviceType");
			params.put("deviceType", deviceType);
		}
		sb.append(" order by version.deviceType, version.uploadTime desc");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

}
