package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.model.RegionEntity;
import org.limingnihao.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class RegionDaoImpl extends GenericDaoImpl<RegionEntity, Integer> implements RegionDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<RegionEntity> getListByRoot() {
		StringBuilder hql = new StringBuilder("select entity from RegionEntity entity ");
		hql.append("where entity.parentEntity is null");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<RegionEntity> list = query.list();
		return list;
	}

	@Override
	public RegionEntity getEntityByRegionCode(String regionCode) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select entity from RegionEntity entity where entity.regionCode=:regionCode");
		query.setParameter("regionCode", regionCode);
		List<RegionEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RegionEntity getEntityByRegionName(String regionName, Integer parentRegionId) {
		StringBuilder sb = new StringBuilder("select r from RegionEntity r where r.regionName=:regionName ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionName", regionName);
		if (NumberUtil.isSignless(parentRegionId)) {
			sb.append(" and r.parentEntity.regionId=:parentRegionId ");
			params.put("parentRegionId", parentRegionId);
		} else {
			sb.append(" and r.parentEntity is null ");
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<RegionEntity> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<RegionEntity> getListByHasMapFile() {
		String hql = "select r from RegionEntity r where r.mapName is not null ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<RegionEntity> getListByParentId(Integer parentId, Integer useFlag, int firstResult, int maxResults) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select r from RegionEntity r ");
		if (NumberUtil.isSignless(parentId)) {
			sb.append(" where r.parentEntity.regionId=:parentId ");
			params.put("parentId", parentId);
		} else {
			sb.append(" where r.parentEntity is null");
		}
		if (useFlag != null) {
			sb.append(" and r.useFlag=:useFlag");
			params.put("useFlag", useFlag);
		}
		sb.append(" order by r.sequence, r.regionId");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public Integer getListByParentId_count(Integer parentId, Integer useFlag) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select count(r) from RegionEntity r");
		if (NumberUtil.isSignless(parentId)) {
			sb.append(" where r.parentEntity.regionId=:parentId ");
			params.put("parentId", parentId);
		} else {
			sb.append(" where r.parentEntity is null");
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

	@Override
	public List<RegionEntity> getListByUserId(Integer userId) {
		StringBuilder sb = new StringBuilder("select r from RegionEntity r join r.groupList g join g.userList u where u.userId=:userId");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("userId", userId);
		return query.list();
	}

	@Override
	public RegionEntity getEntityByLocalName(String localName) {
		StringBuilder sb = new StringBuilder("select r from RegionEntity r where r.regionName like:regionName ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionName", "%" + localName + "%");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<RegionEntity> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
