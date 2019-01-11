package com.qinhailin.portal.core.service;

import com.jfinal.plugin.activerecord.Model;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.DataDictionaryValue;

/**
 * 字典数据值
 * @author qinhailin
 *
 */
public class DataDictionaryValueService extends BaseService {

	private DataDictionaryValue dao=new DataDictionaryValue().dao();

	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}

}
