package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.AuthorityEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthorityDao extends GenericDao<AuthorityEntity, Integer> {

	public abstract List<AuthorityEntity> getList(int firstResult, int maxResults);

	public abstract int getList_count();

}
