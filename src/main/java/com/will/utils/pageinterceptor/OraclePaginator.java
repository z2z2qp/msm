package com.will.utils.pageinterceptor;

/**
 * @author http://www.blogjava.net/oneeyewolf
 */
public class OraclePaginator implements Paginator {

	/**
	 * @see com.chuangyuan.util.pageinterceptor.Paginator#getPaginatedSql(String, com.chuangyuan.util.pageinterceptor.Page)
	 * @param sql
	 * @param page
	 * @return 
	 */
	@Override
	public String getPaginatedSql(String sql, Page<?> page) {
		StringBuffer pageStr = new StringBuffer();
		pageStr.append("select * from ( select row_.*, rownum rownum_ from (");
		pageStr.append(sql);
		pageStr.append(" ) row_ where rownum <= ");
		pageStr.append(page.getEndRow());
		pageStr.append(" ) where rownum_ > ");
		pageStr.append(page.getStartRow());

		return pageStr.toString();
	}

}
