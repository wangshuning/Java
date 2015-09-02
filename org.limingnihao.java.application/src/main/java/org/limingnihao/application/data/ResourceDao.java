package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.ResourceEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ResourceDao extends GenericDao<ResourceEntity, Integer> {

	/**
	 * 对象列表 - 父ID
	 * 
	 * @param parentId
	 * @return List<ResourceEntity>
	 */
	public abstract List<ResourceEntity> getListByParentId(Integer parentId);

	/**
	 * 对象列表 - 根据用户、父ID
	 * 
	 * @param userId
	 * @param parentId
	 * @return List<ResourceEntity>
	 */
	public abstract List<ResourceEntity> getListByParentIdAndUserId(Integer parentId, Integer userId);

	/**
	 * 对象列表 - 排除parentId为空的
	 * 
	 * @param systemType
	 * @return
	 */
	public abstract List<ResourceEntity> getListByResourceTypeExcludeRoot(Integer resourceType, Integer systemType);

	/**
	 * 对象列表 - 根据角色、资源类型
	 */
	public abstract List<ResourceEntity> getListByRoleIdAndResourceType(Integer roleId, Integer resourceType, Integer systemType);

	/**
	 * 对象列表 - 根据资源类型
	 * 
	 * @param userId
	 * @param resourceType
	 * @return
	 */
	public abstract List<ResourceEntity> getListByType(Integer resourceType, Integer systemType);

	/**
	 * 对象列表 - 根据用户、资源类型
	 * 
	 * @param userId
	 * @param resourceType
	 * @return
	 */
	public abstract List<ResourceEntity> getListByUserIdAndType(Integer userId, Integer resourceType, Integer systemType);

}
