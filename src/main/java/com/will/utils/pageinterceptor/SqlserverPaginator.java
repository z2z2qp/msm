package com.will.utils.pageinterceptor;

public class SqlserverPaginator implements Paginator {

	/**
	 * @see com.chuangyuan.util.pageinterceptor.Paginator#getPaginatedSql(String, com.chuangyuan.util.pageinterceptor.Page)
	 * @param sql
	 * @param page
	 * @return 
	 */
	@Override
	public String getPaginatedSql(String sql, Page<?> page) {
		StringBuffer pageStr = new StringBuffer();
		pageStr.append("select * from (").append(sql)
		.append(" ) as st where RowNum >  ")
		.append(page.getStartRow())
		.append(" and RowNum <=  ").append(page.getEndRow());

		return pageStr.toString();
	}

}
