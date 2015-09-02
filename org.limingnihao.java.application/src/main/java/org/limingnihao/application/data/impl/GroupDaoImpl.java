package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.GroupDao;
import org.limingnihao.application.data.model.GroupEntity;
import org.limingnihao.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class GroupDaoImpl extends GenericDaoImpl<GroupEntity, Integer> implements GroupDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public GroupEntity getEntityByGroupNameParentId(String groupName, Integer parentGroupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select g from GroupEntity g where g.groupName=:groupName ");
		params.put("groupName", groupName);
		if (NumberUtil.isSignless(parentGroupId)) {
			sb.append(" and g.parentEntity.groupId=:parentGroupId ");
			params.put("parentGroupId", parentGroupId);
		} else {
			sb.append(" and g.parentEntity is null ");
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		List<GroupEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<GroupEntity> getListAll(Integer useFlag, int firstResult, int maxResults) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select g from GroupEntity g where 1=1 ");
		if (useFlag != null) {
			sb.append(" and g.useFlag=:useFlag ");
			params.put("useFlag", useFlag);
		}
		sb.append(" order by g.sequence, g.groupId ");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public List<GroupEntity> getListByParentGroupId(Integer parentId, Integer useFlag, int firstResult, int maxResults) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select g from GroupEntity g ");
		if (NumberUtil.isSignless(parentId)) {
			sb.append(" where g.parentEntity.groupId=:parentId");
			params.put("parentId", parentId);
		} else {
			sb.append(" where g.parentEntity is null ");
		}
		if (useFlag != null) {
			sb.append(" and g.useFlag=:useFlag");
			params.put("useFlag", useFlag);
		}
		sb.append(" order by g.sequence, g.groupId ");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public Integer getListByParentGroupId_count(Integer parentId, Integer useFlag) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select count(g) from GroupEntity g ");
		if (NumberUtil.isSignless(parentId)) {
			sb.append(" where g.parentEntity.groupId=:parentId");
			params.put("parentId", parentId);
		} else {
			sb.append(" where g.parentEntity is null ");
		}
		if (useFlag != null) {
			sb.append(" and g.useFlag=:useFlag");
			params.put("useFlag", useFlag);
		}
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<GroupEntity> getListByRoot(Integer firstResult, Integer maxResults) {
		StringBuilder sb = new StringBuilder("select g from GroupEntity g where g.parentEntity is null ");
		sb.append(" order by g.sequence, g.groupId");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public Integer getListByRoot_count() {
		StringBuilder sb = new StringBuilder("select count(g) from GroupEntity g where g.parentEntity is null");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<GroupEntity> getListByParentIdAndGroupType(Integer parentId, Integer groupType) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select g from GroupEntity g where 1=1 ");

		if (parentId != null) {
			sb.append(" and g.parentEntity.groupId=:parentId");
			params.put("parentId", parentId);
		}
		if (groupType != null) {
			sb.append(" and g.groupType=:groupType");
			params.put("groupType", groupType);
		}

		sb.append(" order by g.sequence, g.groupId");
		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		return query.list();
	}

}
