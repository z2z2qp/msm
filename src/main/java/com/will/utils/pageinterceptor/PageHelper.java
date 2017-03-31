/**
 * @Title PageHelper.java
 * @Package com.chuangyuan.util.pageinterceptor
 * @Description
 * @author 庄佳彬
 * @Date 2016年3月1日
 * @Version v1.0
 */
package com.will.utils.pageinterceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <li>ClassName: PageHelper</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年3月1日 上午11:05:46</li>
 * @author 庄佳彬
 */
@Intercepts({@Signature(type = StatementHandler.class,method="prepare",args={Connection.class}),
			@Signature(type=ResultSetHandler.class,method="handleResultSets",args={Statement.class})})
public class PageHelper implements Interceptor {
	
	private Properties properties;
	
	private Paginator paginator;
	
	private static final Logger log = LoggerFactory.getLogger(PageHelper.class);
	
	@SuppressWarnings("rawtypes")
	public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();
	
	@SuppressWarnings("rawtypes")
	public static void startPage(int page,int rows){
		Page p = new Page(page,rows);
		localPage.set(p);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Page endPage(){
		Page page = localPage.get();
		localPage.remove();
		return page;
	}
	/**
	 * @see Interceptor#intercept(Invocation)
	 * @param arg0
	 * @return
	 * @throws Throwable 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if(localPage.get() == null){
			return invocation.proceed();
		}
		if(invocation.getTarget() instanceof StatementHandler){
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
			while(metaObject.hasGetter("h")){
				Object object = metaObject.getValue("h");
				metaObject = SystemMetaObject.forObject(object);
			}
			while(metaObject.hasGetter("target")){
				Object object = metaObject.getValue("target");
				metaObject = SystemMetaObject.forObject(object);
			}
			Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
			String className = configuration.getVariables().getProperty("paginator");
			
			this.paginator = (Paginator) Class.forName(className).newInstance();
			
			MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
			Page page = localPage.get();
			BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
			String sql = boundSql.getSql();
			String pageSql = paginator.getPaginatedSql(sql, page);
			metaObject.setValue("delegate.boundSql.sql", pageSql);
			Connection connection = (Connection) invocation.getArgs()[0];
			setPageParameter(sql, connection, mappedStatement, boundSql, page);
			return invocation.proceed();
		}else if(invocation.getTarget() instanceof ResultSetHandler){
			Object result = invocation.proceed();
			Page page = localPage.get();
			page.setResult((List) result);
			return result;
		}
		return null;
	}

	/**
	 * @see Interceptor#plugin(Object)
	 * @param target
	 * @return 
	 */
	@Override
	public Object plugin(Object target) {
		if(target instanceof StatementHandler || target instanceof ResultSetHandler){
			return Plugin.wrap(target, this);
		}else{
			return target;
		}
	}

	/**
	 * @see Interceptor#setProperties(Properties)
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * 
	 * <p>Title: setPageParameter</p>
	 * <p>Description:</p> 
	 * @author 庄佳彬
	 * @Date 2016年2月26日
	 * @param sql
	 * @param connection
	 * @param statement
	 * @param boundSql
	 * @param page void
	 */
	private void setPageParameter(String sql, Connection connection, MappedStatement statement,BoundSql boundSql,Page<?> page){
		String countSql = " select count(0) from ("+sql+") as tt";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = connection.prepareStatement(countSql);
			BoundSql bs = new BoundSql(statement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			setParameters(stmt,statement,bs,boundSql.getParameterObject());
			rs = stmt.executeQuery();
			long totalCount = 0;
			if(rs.next()){
				totalCount = rs.getLong(1);
			}
			page.setTotal(totalCount);
			int totalPage = (int) (totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1));
			page.setPages(totalPage);
		}catch(SQLException e){
			log.error("异常", e);
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>Title: setParameters</p>
	 * <p>Description:</p> 
	 * @author 庄佳彬
	 * @Date 2016年5月9日
	 * @param stmt
	 * @param statement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException void
	 */
	private void setParameters(PreparedStatement stmt,
			MappedStatement statement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ParameterHandler handler = new DefaultParameterHandler(statement, parameterObject, boundSql);
		handler.setParameters(stmt);
	}

	/**
	 * @return the paginator
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	/**
	 * @param paginator the paginator to set
	 */
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

}
