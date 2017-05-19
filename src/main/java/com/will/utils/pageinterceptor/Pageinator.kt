package com.will.utils.pageinterceptor

import com.will.framework.dao.PageResult

/**
 * Created by zoumy on 2017/5/12 10:53.
 */
interface Pageinator {

    /**

     *
     * Title: getPaginatedSql
     *
     * Description:构建分页语句
     * @author 庄佳彬
     * *
     * @Date 2016年5月9日
     * *
     * @param sql
     * *
     * @param page
     * *
     * @return String
     */
    fun getPaginatedSql(sql: String, page: PageResult<*>): String
}