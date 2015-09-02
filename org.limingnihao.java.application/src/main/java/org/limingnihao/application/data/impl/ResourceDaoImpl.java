package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.model.ResourceEntity;
import org.limingnihao.application.type.UseFlagType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ResourceDaoImpl extends GenericDaoImpl<ResourceEntity, Integer> implements ResourceDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ResourceEntity> getListByParentId(Integer parentId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select resource from ResourceEntity resource join resource.parentEntity parent where parent.resourceId=:parentId order by resource.sequence");
		query.setParameter("parentId", parentId);
		return query.list();
	}

	@Override
	public List<ResourceEntity> getListByParentIdAndUserId(Integer parentId, Integer userId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select distinct resource from ResourceEntity resource join resource.parentEntity parent join resource.authorityList authority join authority.roleList role join role.userList user "
						+ " where user.userId=:userId and parent.resourceId=:parentId and role.useFlag=:useFlag order by resource.sequence");
		query.setParameter("userId", userId);
		query.setParameter("parentId", parentId);
		query.setParameter("useFlag", UseFlagType.ENABLED.value());
		return query.list();
	}

	@Override
	public List<ResourceEntity> getListByResourceTypeExcludeRoot(Integer resourceType, Integer systemType) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select resource from ResourceEntity resource join resource.parentEntity parent where parent is not null");
		if (resourceType != null) {
			sb.append(" and resource.resourceType=:resourceType");
			map.put("resourceType", resourceType);
		}
		if (systemType != null) {
			sb.append(" and resource.systemType=:systemType");
			map.put("systemType", systemType);
		}
		sb.append(" order by resource.resourceId");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(map);
		return query.list();
	}

	@Override
	public List<ResourceEntity> getListByRoleIdAndResourceType(Integer roleId, Integer resourceType, Integer systemType) {
		Query query = this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct resource from ResourceEntity resource join resource.authorityList au join au.roleList role where role.roleId=:roleId and resource.resourceType=:resourceType and resource.systemType=:systemType");
		query.setParameter("roleId", roleId);
		query.setParameter("resourceType", resourceType);
		query.setParameter("systemType", systemType);
		return query.list();
	}

	@Override
	public List<ResourceEntity> getListByType(Integer resourceType, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select distinct resource from ResourceEntity resource where resource.resourceType=:resourceType and resource.systemType=:systemType order by resource.sequence");
		query.setParameter("resourceType", resourceType);
		query.setParameter("systemType", systemType);
		return query.list();
	}

	@Override
	public List<ResourceEntity> getListByUserIdAndType(Integer userId, Integer resourceType, Integer systemType) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"select distinct resource from ResourceEntity resource join resource.authorityList authority join authority.roleList role join role.userList user "
						+ " where user.userId=:userId and resource.resourceType=:resourceType and resource.systemType=:systemType order by resource.sequence");
		query.setParameter("userId", userId);
		query.setParameter("resourceType", resourceType);
		query.setParameter("systemType", systemType);
		return query.list();
	}

}
