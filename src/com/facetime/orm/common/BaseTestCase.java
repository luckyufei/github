package com.facetime.orm.common;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.facetime.orm.dao.Dao;
import com.facetime.orm.locator.Locator;

@ContextConfiguration(locations = { "classpath*:beans.xml", "classpath*:facetime.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	@Qualifier("beanLocator")
	protected Locator beanLocator;

	@Autowired
	@Qualifier("defaultDao")
	protected Dao defaultDao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@BeforeTransaction
	public void initUser() {
	}

	protected void flush() {
		this.sessionFactory.getCurrentSession().flush();
	}

	protected String getUsername() {
		return null;
	}

	protected String getPassword() {
		return null;
	}

	protected Dao getDefaultDao() {
		return this.defaultDao;
	}

	protected <T> T locate(Class<T> locatingClass) {
		return this.beanLocator.locate(locatingClass);
	}
}