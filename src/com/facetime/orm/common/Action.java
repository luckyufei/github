package com.facetime.orm.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.facetime.orm.dao.Dao;
import com.facetime.orm.locator.Locator;
import com.facetime.orm.logic.Logic;

/**
 * SpringMVC 框架的控制器层的ROOT抽象类
 *
 * @author YUFEI
 *
 */
public class Action {

	@Autowired
	@Qualifier("beanLocator")
	protected Locator beanLocator;

	@Autowired
	@Qualifier("defaultLogic")
	protected Logic defaultLogic;

	public <T extends Dao> T locate(Class<T> beanClass) {
		T logic = beanLocator.locate(beanClass);
		if (Dao.class.isAssignableFrom(beanClass)) {
			return logic;
		}
		throw new AssertionError("you can only locate logic class.");
	}
}
