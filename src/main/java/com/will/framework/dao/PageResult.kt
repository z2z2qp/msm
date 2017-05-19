package com.will.framework.dao

import com.will.framework.entity.IEntity

/**
 * Created by zoumy on 2017/5/11 12:36.
 */
class PageResult<T : IEntity>() {

    /**
     * 当前页
     */
    var page: Int = 0
    /**
     * 选取行
     */
    var rows: Int = 0
    /**
     * 总行数
     */
    var total: Int = 0

    /**
     * 总页数
     */
    var pages: Int = 0
        get() {
        return if(total % rows == 0) total / rows else total / rows + 1
    }
    /**
     * 数据
     */
    var data: List<T>? = null
    /**
     * 起始行
     */
    var firstRow: Int = 0
    /**
     * 结束行
     */
    var maxRow: Int = 0

    constructor(page:Int,rows:Int):this(){
        this.page = page
        this.rows = rows

        this.firstRow = if (page > 0) (page - 1) * rows else 1
        this.maxRow = if(page > 0) page * rows else 10
    }


}