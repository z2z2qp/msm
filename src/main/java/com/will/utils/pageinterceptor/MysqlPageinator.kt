package com.will.utils.pageinterceptor

import com.will.framework.dao.PageResult

/**
 * Created by zoumy on 2017/5/12 10:55.
 */
class MysqlPageinator :Pageinator {


    override fun getPaginatedSql(sql: String, page: PageResult<*>): String {
        return "$sql  limit ${page.firstRow } , ${page.maxRow}  "
    }
}