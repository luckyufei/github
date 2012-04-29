﻿package com.facetime.orm.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * @author yufei
 *
 * @param <T>
 */
public class Page<T> {
	/** 下拉分页列表的递增数量级 */
	private static final int ADD_PAGE_SIZE_RATIO = 10;
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 200;

	private QueryInfo<T> queryInfo = null;
	private List<T> queryResult = null;

	public Page() {
		this(new QueryInfo<T>());
	}

	public Page(QueryInfo<T> queryInfo) {
		this.queryInfo = queryInfo;
		this.queryResult = new ArrayList<T>(15);
	}

	/**
	 * @return 下拉分页列表的递增数量级
	 */
	public final static int getAddPageSize() {
		return ADD_PAGE_SIZE_RATIO;
	}

	/**
	 * @return 默认分页下拉列表的开始值
	 */
	public final static int getBeginPageSize() {
		return 20;
	}

	/**
	 * 默认列表记录显示条数
	 */
	public static final int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

	/**
	 * 默认分页列表显示的最大记录条数
	 */
	public static final int getMaxPageSize() {
		return MAX_PAGE_SIZE;
	}

	public String getBeanName() {
		return this.queryInfo.getBeanName();
	}

	public int getCurrentPageNo() {
		return this.queryInfo.getCurrentPageNo();
	}

	public String getKey() {
		return this.queryInfo.getKey();
	}

	public int getNeedRowNum() {
		return this.getPageSize() - this.getQueryResult().size();
	}

	public long getNextPage() {
		return this.queryInfo.getNextPage();
	}

	public int getPageCount() {
		return this.queryInfo.getPageCount();
	}

	public int getPageSize() {
		return this.queryInfo.getPageSize();
	}

	public List<T> getParams() {
		return this.queryInfo.getParams();
	}

	public int getPreviousPage() {
		return this.queryInfo.getPreviousPage();
	}

	public String[] getProperties() {
		return this.queryInfo.getProperties();
	}

	public List<T> getQueryResult() {
		return this.queryResult;
	}

	public int getRecordCount() {
		return this.queryInfo.getRecordCount();
	}

	public String getSql() {
		return this.queryInfo.getSql();
	}

	public boolean isHasResult() {
		return this.queryResult != null && this.queryResult.size() > 0;
	}

	public void setBeanName(String beanNameValue) {
		this.queryInfo.setBeanName(beanNameValue);
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.queryInfo.setCurrentPageNo(currentPageNo);
	}

	public void setKey(String keyValue) {
		this.queryInfo.setKey(keyValue);
	}

	public void setPageCount(int pageCount) {
		this.queryInfo.setPageCount(pageCount);
	}

	public void setPageSize(int pageSize) {
		this.queryInfo.setPageSize(pageSize);
	}

	public void setParams(List<T> paramsValue) {
		this.queryInfo.setParams(paramsValue);
	}

	public void setProperties(String[] propertiesValue) {
		this.queryInfo.setProperties(propertiesValue);
	}

	public void setQueryResult(List<T> list) {
		this.queryResult = list;
	}

	public void setRecordCount(int count) {
		this.queryInfo.setRecordCount(count);
	}

	public void setSql(String sql) {
		this.queryInfo.setSql(sql);
	}
}