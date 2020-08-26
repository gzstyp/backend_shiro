package com.yinlz.datasource;

public final class DataSourceType {

	public enum DataBaseType {
        Write,Slave1,Slave2
	}

	// 使用ThreadLocal保证线程安全
	private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();

	// 往当前线程里设置数据源类型
	public static void setDataBaseType(DataBaseType dataBaseType) {
		if (dataBaseType == null) {
			throw new NullPointerException();
		}
		System.err.println("[将当前数据源改为]：" + dataBaseType);
		TYPE.set(dataBaseType);
	}

	// 获取数据源类型
	public static DataBaseType getDataBaseType() {
		final DataBaseType dataBaseType = TYPE.get() == null ? DataBaseType.Write : TYPE.get();
		System.err.println("[获取当前数据源的类型为]：" + dataBaseType);
		return dataBaseType;
	}

	// 清空数据类型
	public static void clearDataBaseType() {
		TYPE.remove();
	}
}