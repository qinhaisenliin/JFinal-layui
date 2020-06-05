/**
 * Copyright 2019-2020 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.qinhailin.common.ureport;

import java.sql.Connection;
import java.sql.SQLException;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.jfinal.plugin.druid.DruidPlugin;
import com.qinhailin.common.config.MainConfig;

/**
 * ureport2数据源
 * @author QinHaiLin
 * @date 2020-06-06
 */
public class Ureport2BuildinDatasource implements BuildinDatasource {

	@Override
	public String name() {
		return "mainDataSource";
	}

	@Override
	public Connection getConnection() {
		try {
			DruidPlugin druidplugin=MainConfig.createDruidPlugin();
			druidplugin.start();
			return druidplugin.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
