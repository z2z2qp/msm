package com.will.utils.pageinterceptor;

public interface Paginator {

	/**
	 * 
	 * <p>Title: getPaginatedSql</p>
	 * <p>Description:构建分页语句</p> 
	 * @author 庄佳彬
	 * @Date 2016年5月9日
	 * @param sql
	 * @param page
	 * @return String
	 */
	public String getPaginatedSql(String sql, Page<?> page);
	
}
