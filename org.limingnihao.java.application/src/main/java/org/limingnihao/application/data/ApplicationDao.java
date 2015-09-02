package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.ApplicationEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ApplicationDao extends GenericDao<ApplicationEntity, Integer> {

	/**
	 * 获取对象 - 根据属性标识
	 * 
	 * @param PropertyFlagType
	 * @return ApplicationPropertyEntity
	 */
	public ApplicationEntity getEntityByFlag(String propertyFlagType);

	/**
	 * 对象列表 - 分页
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return List<ApplicationPropertyEntity>
	 */
	public List<ApplicationEntity> getListBySystemType(Integer systemType, int firstResult, int maxResults);

	public int getListBySystemType_count(Integer systemType);

}
