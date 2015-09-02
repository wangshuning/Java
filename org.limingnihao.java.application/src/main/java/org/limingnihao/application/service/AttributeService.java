package org.limingnihao.application.service;

import java.util.List;

import org.limingnihao.application.service.model.AttributeBean;

public interface AttributeService {

	/**
	 * 属性列表
	 * 
	 * @param attributeFlag
	 * @param state
	 * @return List<AttributeBean>
	 */
	public abstract List<AttributeBean> getListBySystemTypeFlag(Integer systemType, String attributeFlag, boolean state);

}