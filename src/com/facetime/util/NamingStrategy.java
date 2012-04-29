package com.facetime.util;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class NamingStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 8540955950903809611L;
	
	private static final String TABLE_PREFIX = "t_";
	private static final String COLUMN_PREFIX = "c_";

	@Override
	public String classToTableName(String className) {
		return TABLE_PREFIX + super.classToTableName(className);
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return COLUMN_PREFIX + super.propertyToColumnName(propertyName);
	}

}
