package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.RoleEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleDao extends GenericDao<RoleEntity, Integer> {

	public abstract List<RoleEntity> getListBySystemType(Integer systemType, Integer useFlag, int firstResult, int maxResults);

	public abstract int getListBySystemType_count(Integer systemType, Integer useFlag);

}
