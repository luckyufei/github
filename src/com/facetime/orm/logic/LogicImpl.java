package com.facetime.orm.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facetime.orm.common.Limitable.Order;
import com.facetime.orm.common.Limitable.OrderBy;
import com.facetime.orm.common.Limitable.PageBy;
import com.facetime.orm.common.PMLO;
import com.facetime.orm.common.QueryFilter;
import com.facetime.orm.dao.Dao;
import com.facetime.orm.dao.DaoImpl;
import com.facetime.orm.locator.Locator;

/**
 * 默认的业务层接口实现类
 *
 * @author YUFEI
 */
@Service("defaultLogic")
public class LogicImpl extends DaoImpl implements Logic {

	private Locator locator;

	public static final QueryFilter filterby(String property, Object value) {
		return QueryFilter.valueOf(property, value);
	}

	public static final QueryFilter[] toArray(List<QueryFilter> filters) {
		return filters.toArray(new QueryFilter[] {});
	}

	public static final QueryFilter filterby(String property, Object value, boolean valid) {
		return QueryFilter.valueOf(property, value, valid);
	}

	public static final QueryFilter filterby(String property, PMLO pmlo) {
		return QueryFilter.valueOf(property, pmlo);
	}

	public static final QueryFilter filterby(String property, PMLO pmlo, Object value) {
		return QueryFilter.valueOf(property, pmlo, value);
	}

	public static final QueryFilter filterby(String property, PMLO pmlo, Object value, boolean valid) {
		return QueryFilter.valueOf(property, pmlo, value, valid);
	}

	public static final OrderBy orderAsc(String attrName) {
		return OrderBy.valueOf(attrName, Order.ASC);
	}

	public static final OrderBy orderby(String attrName) {
		return OrderBy.valueOf(attrName, Order.ASC);
	}

	public static final OrderBy orderby(String attrName, Order orderType) {
		return OrderBy.valueOf(attrName, orderType);
	}

	public static final OrderBy orderDesc(String attrName) {
		return OrderBy.valueOf(attrName, Order.DESC);
	}

	public static final PageBy pageby(int pageNum, int pageSize) {
		return new PageBy(pageNum, pageSize);
	}

	public Locator getLocator() {
		return locator;
	}

	public <T extends Dao> T locate(Class<T> clazz) {
		return getLocator().locate(clazz);
	}

	public void setLocator(Locator locator) {
		this.locator = locator;
	}
}
