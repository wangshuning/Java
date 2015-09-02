package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.AttributeEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AttributeDao extends GenericDao<AttributeEntity, Integer> {

	/**
	 * 获取对象 - 根据类型、取值
	 */
	public AttributeEntity getEntityByTypeAndValue(String attributeFlag, Integer attributeValue, Integer systemType);

	/**
	 * 获取列表
	 */
	public List<AttributeEntity> getListBySystemType(Integer systemType);

	/**
	 * 获取列表
	 */
	public List<AttributeEntity> getListBySystemTypeFlag(String attributeFlag, Integer systemType, boolean state);

}
