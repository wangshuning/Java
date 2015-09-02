package org.limingnihao.application.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GenericDao<T, PK extends Serializable> {

	public abstract void deleteEntity(T entity);

	public abstract T getEntity(PK id);

	public abstract List<T> getList();

	public abstract List<T> getList(int firstResult, int maxResults);

	public abstract int getList_count();

	public abstract void saveEntity(T entity);

}