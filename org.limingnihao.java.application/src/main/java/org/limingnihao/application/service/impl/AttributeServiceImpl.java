package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.AttributeDao;
import org.limingnihao.application.data.model.AttributeEntity;
import org.limingnihao.application.service.AttributeService;
import org.limingnihao.application.service.model.AttributeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeServiceImpl implements AttributeService {

	public static Logger logger = LoggerFactory.getLogger(AttributeServiceImpl.class);

	@Autowired
	private AttributeDao attributeDao;

	@Override
	public List<AttributeBean> getListBySystemTypeFlag(Integer systemType, String attributeFlag, boolean state) {
		List<AttributeEntity> attributeEntityList = this.attributeDao.getListBySystemTypeFlag(attributeFlag, systemType, state);
		List<AttributeBean> attributeBeanList = new ArrayList<AttributeBean>();
		for (AttributeEntity attributeTypeEntity : attributeEntityList) {
			AttributeBean attributeBean = new AttributeBean();
			attributeBean.setAttributeValue(attributeTypeEntity.getAttributeValue());
			attributeBean.setAttributeName(attributeTypeEntity.getAttributeName());
			attributeBeanList.add(attributeBean);
		}
		return attributeBeanList;
	}

}
