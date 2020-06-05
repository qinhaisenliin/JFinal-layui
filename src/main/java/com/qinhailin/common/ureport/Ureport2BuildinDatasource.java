package com.qinhailin.common.ureport;

import java.sql.Connection;
import java.sql.SQLException;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.jfinal.plugin.druid.DruidPlugin;
import com.qinhailin.common.config.MainConfig;

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
