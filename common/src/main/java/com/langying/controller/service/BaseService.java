package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import com.langying.controller.mapper.IBaseMapper;
import com.langying.toolbox.utils.ClearNullUtil;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 */
@CompileStatic
@TypeChecked
public abstract class BaseService<M> implements IBaseService<M>{

    protected Integer pageSizeDefault = 20;

    protected IBaseMapper baseMapper;

    void setBaseMapper(IBaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    public  abstract <K> K addBasic(M obj) throws Exception;

    public abstract void modifyBasic(M obj) throws Exception;

    public abstract void delBasic(M obj) throws Exception;

    @Override
    public M findObjByKey(Object  seq) throws Exception {
        return (M)baseMapper.selectByPrimaryKey(seq);
    }

    @Override
    public  M  findObj(Map<String, Object> params) throws Exception {
        ClearNullUtil.mapClear(params);
        return (M)baseMapper.selectByParams(params);
    }


    @Override
    public Integer findCountByParams(Map<String, Object> params) {
        return baseMapper.selectCountByParams(params);
    }

    @Override
    public List findListByParams(Map<String, Object> params, Order order) throws Exception {
        ClearNullUtil.mapClear(params);
       return   baseMapper.selectListByParams(params,null,null,order!=null?order.toString():null);
    }

    @Override
    public List findListByParams(Map<String, Object> params, Order order, Integer maxRecord) throws Exception {
        ClearNullUtil.mapClear(params);
       return baseMapper.selectListByParams(params, 0, maxRecord, order==null?null:order.toString());
    }

    @Override
    public RollPage findListPageByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize) throws Exception {
        ClearNullUtil.mapClear(params);
        Integer recordSum= baseMapper.selectCountByParams(params);
        //Integer pageSizeCustom = SysConfigHelper.getSysConfigByCode("manage.pageSize").configValue as Integer
        Integer pageSizeCustom = this.pageSizeDefault;
        RollPage rollPage=new RollPage(recordSum,recordSum,pageSize!=null?pageSize:pageSizeCustom, pageNum!=null?pageNum:1);

        Integer pageOffset=(rollPage.getPageNum() - 1) * rollPage.getPageSize();

        if (recordSum>0) {
            rollPage.setResultList(baseMapper.selectListByParams(params, pageOffset, rollPage.getPageSize(), order==null?null:order.toString()));
        }
        else{
            rollPage.setResultList(new ArrayList());
        }

        return rollPage;
    }
}
