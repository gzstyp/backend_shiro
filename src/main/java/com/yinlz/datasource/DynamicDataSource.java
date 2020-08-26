package com.yinlz.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public final class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		final DataSourceType.DataBaseType dataBaseType = DataSourceType.getDataBaseType();
		return dataBaseType;
	}
}