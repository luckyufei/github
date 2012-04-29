package com.facetime.orm.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facetime.orm.common.BusinessObject;
import com.facetime.orm.common.Limitable.OrderBy;
import com.facetime.orm.common.PMLO;
import com.facetime.orm.common.QueryFilter;

public final class HQLBuilder {

	/**
	 *
	 * sql中in(可以包含的最大字段取值个数
	 */
	public static final int MAX_IN_NUMBERS = 255;

	/**
	 * 构建DELETE HQL语句
	 *
	 * @param <T>
	 * @param entityClass
	 * @param filters
	 * @return
	 */
	public static <T extends BusinessObject> String buildDeleteHQL(Class<T> entityClass, QueryFilter[] filters) {
		StringBuilder hql = new StringBuilder();
		hql.append(" delete ").append(entityClass.getName()).append(" as obj ");
		if (filters != null) {
			hql.append(buildQueryFilterSubHQL(filters));
		}
		return hql.toString();
	}

	/**
	 * 构建FROM 子HQL语句
	 *
	 * @param <T>
	 * @param entityClass
	 *            实体类型
	 * @param filters
	 * @param orders
	 * @return
	 */
	public static <T extends BusinessObject> String buildFindSubHQL(Class<T> entityClass, QueryFilter[] filters,
			OrderBy[] orders) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from ").append(entityClass.getName()).append(" as obj ");
		if (filters != null) {
			hql.append(buildQueryFilterSubHQL(filters));
		}
		if (orders != null && orders.length > 0) {
			hql.append(buildOrderBySubHQL(orders));
		}
		return hql.toString();
	}

	/**
	 * 构建QueryFilter查询子语句
	 *
	 * @param filters
	 * @return
	 */
	public static String buildQueryFilterSubHQL(QueryFilter[] filters) {
		StringBuilder conditions = new StringBuilder();
		for (QueryFilter filter : filters) {
			if (!filter.isValid()) {
				continue;
			}
			if (!filter.getOperate().isSingle() && filter.getValue() == null) {
				continue;
			}
			if (filter.getValue().getClass() == String.class && ((String) filter.getValue()).trim().length() == 0) {
				continue;
			}
			conditions.append(conditions.length() == 0 ? " where " : " and ");
			conditions.append(" obj.").append(filter.getProperty()).append(filter.getTranslateOperate());
		}
		return conditions.toString();
	}

	/**
	 * 构建返回属性的子语句
	 *
	 * @param <T>
	 * @param entityClass
	 *            实体类型
	 * @param retAttrs
	 *            要返回的属性名
	 * @param filters
	 *            查询条件
	 * @param orders
	 *            排序
	 * @return
	 */
	public static <T extends BusinessObject> String buildRetAttrSubHQL(Class<T> entityClass, String[] retAttrs,
			QueryFilter[] filters, OrderBy[] orders) {
		StringBuilder hql = new StringBuilder();
		if (retAttrs != null && retAttrs.length > 0) {
			for (String retAttr : retAttrs) {
				hql.append(hql.length() == 0 ? " select " : " , ");
				hql.append(" obj.").append(retAttr);
			}
			hql.append(" ");
		}
		hql.append(buildFindSubHQL(entityClass, filters, orders));
		return hql.toString();
	}

	/**
	 * 构建UPDATE HQL语句
	 *
	 * @param <T>
	 * @param entityClass
	 *            实体类型
	 * @param filters
	 *            查询条件
	 * @param attrNames
	 *            要更新的属性名
	 * @param attrValues
	 *            要更新的属性值
	 */
	public static <T extends BusinessObject> String buildUpdateHQL(Class<T> entityClass, QueryFilter[] filters,
			String[] attrNames, Object[] attrValues) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update ").append(entityClass.getName()).append(" as obj ");
		if (attrNames != null && attrValues != null) {
			hql.append(buildSetSubHQL(attrNames, attrValues));
		}
		if (filters != null && filters.length > 0) {
			hql.append(buildQueryFilterSubHQL(filters));
		}
		return hql.toString();
	}

	/**
	 * 根据字符型字段值组织sql的in条件语句
	 *
	 * @param fieldName
	 *            字段名
	 * @param values
	 *            字段条件取值
	 * @return sql语句
	 */
	public static final String fitStrInCondition(String fieldName, String[] values) {
		StringBuffer sbf = new StringBuffer();
		int row = values.length / MAX_IN_NUMBERS;
		int remain = values.length % MAX_IN_NUMBERS;
		int index = 0;
		for (int i = 1; i <= row; i++) {
			sbf.append(fieldName).append(" in(");
			for (int j = 1; j < MAX_IN_NUMBERS; j++) {
				sbf.append("'").append(values[index++]).append("',");
			}
			sbf.append("'").append(values[index++]).append("')");
			if (row > 1 && i < row) {
				sbf.append(" or ");
			}
		}
		if (remain > 0) {
			if (row > 0) {
				sbf.append(" or ");
			}
			sbf.append(fieldName).append(" in(");
			for (int i = 1; i < remain; i++) {
				sbf.append("'").append(values[index++]).append("',");
			}
			sbf.append("'").append(values[index++]).append("')");
		}
		if (values.length < MAX_IN_NUMBERS) {
			return sbf.toString();
		}
		return "(" + sbf.toString() + ")";
	}

	public static String getCountQueryString(String queryString) {
		String temp = queryString.toUpperCase();
		String prefix = "SELECT COUNT(*) ";
		int ordPos = temp.indexOf("ORDER BY");

		if (temp.trim().startsWith("SELECT")) {
			int pos = temp.indexOf("FROM");

			if (pos > 0) {
				if (ordPos > 0) {
					return prefix + queryString.substring(pos, ordPos);
				}

				return prefix + queryString.substring(pos);
			}
		} else if (temp.trim().startsWith("FROM")) {
			if (ordPos > 0) {
				return prefix + queryString.substring(0, ordPos);
			}

			return prefix + queryString;
		}

		throw new IllegalArgumentException("The query string is not valid." + queryString);
	}

	/**
	 * 构建查询的值数组
	 * @param updateVals 针对UPDATE语句需要更新的值
	 * @param filters
	 * @return
	 */
	public static Object[] getParamterValues(Object[] updateVals, QueryFilter[] filters) {
		List<Object> values = new ArrayList<Object>();
		if (updateVals != null) {
			for (Object obj : updateVals) {
				if (obj != null) {
					values.add(obj);
				}
			}
		}
		if (filters == null) {
			return values.toArray();
		}
		for (QueryFilter filter : filters) {
			if (filter.getValue() == null || !filter.isValid()) {
				continue;
			}

			// 对于PMLO是IN的情况, filter的值是Object[]
			if (filter.getOperate() == PMLO.IN) {
				if (filter.getValue() instanceof ArrayList) {
					values.addAll((ArrayList<?>) filter.getValue());
				}
				if (filter.getValue() instanceof Object[]) {
					values.addAll(Arrays.asList((Object[]) filter.getValue()));
				}
			} else {
				values.add(filter.getValue());
			}
		}
		return values.toArray();
	}

	/**
	 * 构建ORDER BY 子语句
	 *
	 * @param orders
	 *            排序
	 * @return
	 */
	private static String buildOrderBySubHQL(OrderBy[] orders) {
		StringBuilder orderBy = new StringBuilder();
		for (OrderBy order : orders) {
			orderBy.append(orderBy.length() == 0 ? " order by " : " , ");
			orderBy.append(" obj.").append(order.getProperty()).append(" ").append(order.getOrderType().getOrder());
		}
		return orderBy.toString();
	}

	/**
	 * 构建UPDATE SET 子语句
	 *
	 * @param attrNames
	 *            要更新的属性名
	 * @param attrValues
	 *            要更新的属性值
	 * @return
	 */
	private static String buildSetSubHQL(String[] attrNames, Object[] attrValues) {
		StringBuilder hql = new StringBuilder();
		for (int i = 0; i < attrNames.length; i++) {
			hql.append(i == 0 ? " set " : " , ");
			hql.append(" obj.").append(attrNames[i]).append(attrValues[i] == null ? " = null " : " = ? ");
		}
		return hql.toString();
	}
}
