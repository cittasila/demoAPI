package com.langying.controller.service;

import com.langying.models.RUserBook;

/**
 * Created by yucy on 2016/3/16.
 */
public interface IUserBookService  extends IBaseService<RUserBook>{
    Integer purchase(String bookId,Integer userId) throws Exception;
}
