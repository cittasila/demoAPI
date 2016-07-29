package com.langying.controller.service;

import com.langying.models.RUserBook;
import com.langying.models.TArticleInfo;
import com.langying.models.VArticleInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by thtanghao on 2016/3/16.
 */
public interface IResourceService extends IBaseService<RUserBook>{
    /**
     * 为图书列表设置我的图画标记
     * @param params
     * @param articleInfoL
     * @return
     * @throws Exception
     */
    public List<VArticleInfo> setUserBookSign(Map<String, Object> params,List<TArticleInfo> articleInfoL) throws Exception;

    /**
     * 查询用户图书
     * @param params
     * @return
     * @throws Exception
     */
    public List<RUserBook> findUserBookList(Map<String, Object> params) throws Exception;
}
