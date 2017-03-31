package com.will.utils.pageinterceptor;

/**
 * 
 * <li>ClassName: MySqlPaginator</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年5月9日 上午10:36:42</li>
 * @author 庄佳彬
 */
public class MySqlPaginator implements Paginator{

	/**
	 * @see com.chuangyuan.util.pageinterceptor.Paginator#getPaginatedSql(String, com.chuangyuan.util.pageinterceptor.Page)
	 * @param sql
	 * @param page
	 * @return 
	 */
	@Override
	public String getPaginatedSql(String sql, Page<?> page) {
		return (sql) + (" limit " + page.getStartRow() + ", " + page.getEndRow() + " ");
	}

	

}
