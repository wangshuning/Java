package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.VersionEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VersionDao extends GenericDao<VersionEntity, Integer> {

	public abstract VersionEntity getEntityByDeviceTypeAndVersionCode(Integer deviceType, Integer versionCode, Integer systemType);

	public abstract VersionEntity getEntityByDeviceTypeAndVersionName(Integer deviceType, String versionName, Integer systemType);

	public abstract VersionEntity getEntityByMaxCodeAndDeviceType(Integer deviceType, Integer systemType);

	public abstract List<VersionEntity> getListByDeviceType(Integer deviceType, int firstResult, int maxResults, Integer systemType);

	public abstract int getListByDeviceType_count(Integer deviceType, Integer systemType);

}