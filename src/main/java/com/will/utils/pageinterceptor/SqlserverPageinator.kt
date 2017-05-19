package com.will.utils.pageinterceptor

import com.will.framework.dao.PageResult

/**
 * Created by zoumy on 2017/5/12 10:57.
 */
class SqlserverPageinator : Pageinator{
    override fun getPaginatedSql(sql: String, page: PageResult<*>): String {
        val pageStr = StringBuilder()
        pageStr.append("select * from (").append(sql)
                .append(" ) as st where RowNum >  ")
                .append(page.firstRow)
                .append(" and RowNum <=  ").append(page.maxRow)

        return pageStr.toString()
    }
}