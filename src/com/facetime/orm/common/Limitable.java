package com.facetime.orm.common;

/**
 * OrderBy 和 PageBy等结果集过滤接口的ROOT接口
 *
 * @author YUFEI
 *
 */
public interface Limitable {

	public static enum Order {
		ASC("asc"), DESC("desc");
		private final String order;

		private Order(String order) {
			this.order = order;
		}

		public String getOrder() {
			return order;
		}
	}

	public static class OrderBy implements Limitable {

		private final Order orderType;
		private final String property;

		public OrderBy(String property) {
			this(property, Order.ASC);
		}

		public OrderBy(String property, Order order) {
			super();
			this.property = property;
			orderType = order;
		}

		public static final OrderBy asc(String attrName) {
			return new OrderBy(attrName, Order.ASC);
		}

		public static final OrderBy desc(String attrName) {
			return new OrderBy(attrName, Order.DESC);
		}

		public static final OrderBy valueOf(String attrName) {
			return new OrderBy(attrName, Order.ASC);
		}

		public static final OrderBy valueOf(String attrName, Order orderType) {
			return new OrderBy(attrName, orderType);
		}

		public Order getOrderType() {
			return orderType;
		}

		public String getProperty() {
			return property;
		}

	}

	public static class PageBy implements Limitable {

		private int pageNum = 1;
		private int pageSize = 15;

		public PageBy(int pageNum, int pageSize) {
			super();
			this.pageNum = pageNum;
			this.pageSize = pageSize;
		}

		public static final PageBy valueOf(int pageNum, int pageSize) {
			return new PageBy(pageNum, pageSize);
		}

		public int getPageNum() {
			return pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

	}
}
