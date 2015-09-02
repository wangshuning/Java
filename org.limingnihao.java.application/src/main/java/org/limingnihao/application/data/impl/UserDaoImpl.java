package org.limingnihao.application.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.limingnihao.application.data.UserDao;
import org.limingnihao.application.data.model.UserEntity;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl extends GenericDaoImpl<UserEntity, Integer> implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public UserEntity getEntityByUsername(String username) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select u from UserEntity u where u.username=:username");
		query.setParameter("username", username);
		List<UserEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<UserEntity> getListByGroupIdRoleIdNicknameUserType(Integer groupId, Integer roleId, String nickname, Integer userType, Integer useFlag, int firstResult, int maxResults) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select distinct u from UserEntity u ");
		if (groupId != null && groupId.intValue() > 0) {
			sb.append(" join u.groupList g ");
		}
		if (roleId != null && roleId.intValue() > 0) {
			sb.append(" join u.roleList r ");
		}
		sb.append(" where 1=1 ");
		if (groupId != null && groupId.intValue() > 0) {
			sb.append(" and g.groupId =:groupId ");
			params.put("groupId", groupId);
		}
		if (roleId != null && roleId.intValue() > 0) {
			sb.append(" and r.roleId =:roleId ");
			params.put("roleId", roleId);
		}

		if (!StringUtil.isBlank(nickname)) {
			sb.append(" and u.nickname like :nickname ");
			params.put("nickname", "%" + nickname + "%");
		}

		if (NumberUtil.isSignless(userType)) {
			sb.append(" and u.userType =:userType ");
			params.put("userType", userType);
		} else {
			sb.append(" and u.userType < 10 ");
		}

		sb.append(" order by u.userId");

		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int getListByGroupIdRoleIdNicknameUserType_count(Integer groupId, Integer roleId, String nickname, Integer userType, Integer useFlag) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select count(distinct u) from UserEntity u ");
		if (groupId != null && groupId.intValue() > 0) {
			sb.append(" join u.groupList g ");
		}
		if (roleId != null && roleId.intValue() > 0) {
			sb.append(" join u.roleList r ");
		}
		sb.append(" where 1=1 ");
		if (groupId != null && groupId.intValue() > 0) {
			sb.append(" and g.groupId =:groupId ");
			params.put("groupId", groupId);
		}
		if (roleId != null && roleId.intValue() > 0) {
			sb.append(" and r.roleId =:roleId ");
			params.put("roleId", roleId);
		}

		if (!StringUtil.isBlank(nickname)) {
			sb.append(" and u.nickname like :nickname ");
			params.put("nickname", "%" + nickname + "%");
		}

		if (NumberUtil.isSignless(userType)) {
			sb.append(" and u.userType < 10 ");
			// params.put("userType", userType);
		}

		Query query = this.sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setProperties(params);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

}
