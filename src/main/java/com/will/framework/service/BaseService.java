package com.will.framework.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Will on 2016/8/18 11:19.
 */
@Transactional
public interface BaseService {


    boolean checkToken(String token);
}
