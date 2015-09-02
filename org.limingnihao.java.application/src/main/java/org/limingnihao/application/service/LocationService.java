package org.limingnihao.application.service;

import org.limingnihao.application.service.model.LocationBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LocationService {
    public abstract LocationBean getLocationBeanByIpAddress(String ipAddress);
}
