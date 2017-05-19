package com.will.utils.pageinterceptor

import com.will.framework.dao.PageResult

/**
 * Created by zoumy on 2017/5/12 10:56.
 */
class OraclePageinator :Pageinator {
    override fun getPaginatedSql(sql: String, page: PageResult<*>): String {
        val pageStr = StringBuilder()
        pageStr.append("select * from ( select row_.*, rownum rownum_ from (")
        pageStr.append(sql)
        pageStr.append(" ) row_ where rownum <= ")
        pageStr.append(page.maxRow)
        pageStr.append(" ) where rownum_ > ")
        pageStr.append(page.firstRow)

        return pageStr.toString()
    }
}