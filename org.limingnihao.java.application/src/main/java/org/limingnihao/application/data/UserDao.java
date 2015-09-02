package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.UserEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserDao extends GenericDao<UserEntity, Integer> {

	/**
	 * 获取对象 - 根据username
	 */
	public abstract UserEntity getEntityByUsername(String username);

	/**
	 * 对象列表 - 根据groupId,roleId,nickname,userType
	 */
	public abstract List<UserEntity> getListByGroupIdRoleIdNicknameUserType(Integer groupId, Integer roleId, String nickname, Integer userType, Integer useFlag, int firstResult, int maxResults);

	public abstract int getListByGroupIdRoleIdNicknameUserType_count(Integer groupId, Integer roleId, String nickname, Integer userType, Integer useFlag);

}