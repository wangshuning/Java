package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.GroupEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupDao extends GenericDao<GroupEntity, Integer> {

	/**
	 * 根据groupName获取用户组
	 */
	public abstract GroupEntity getEntityByGroupNameParentId(String groupName, Integer parentGroupId);

	/**
	 * 对象列表 - 全部
	 */
	public abstract List<GroupEntity> getListAll(Integer useFlag, int firstResult, int maxResults);

	/**
	 * 对象列表 - 根据parentGroupId
	 */
	public abstract List<GroupEntity> getListByParentGroupId(Integer parentId, Integer useFlag, int firstResult, int maxResults);

	public abstract Integer getListByParentGroupId_count(Integer parentId, Integer useFlag);

	/**
	 * 对象列表 - 根节点
	 */
	public abstract List<GroupEntity> getListByRoot(Integer firstResult, Integer maxResults);

	public abstract Integer getListByRoot_count();

	/**
	 * 对象列表 - 根据parentId和groupType
	 */
	public abstract List<GroupEntity> getListByParentIdAndGroupType(Integer parentId, Integer groupType);

}
