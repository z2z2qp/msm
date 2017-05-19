package com.will.framework.service

import org.springframework.transaction.annotation.Transactional

/**
 * Created by zoumy on 2017/5/11 14:54.
 */
@Transactional
interface BaseService {


    fun checkToken(token: String): Boolean
}