package com.will.utils.tree

import java.util.*

/**
 * Created by zoumy on 2017/5/11 8:35.
 */
data class Tree(var id:String? = null,
                var text:String? = null,
                var state:String = "",
                var pid:String? = null,
                var children:ArrayList<Tree> = ArrayList())